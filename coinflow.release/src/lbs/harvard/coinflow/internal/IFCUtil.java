package lbs.harvard.coinflow.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;

import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.lattice.impl.IFCLatticeGraphImpl;
import lbs.harvard.coinflow.lattice.principal.Principal;
import lbs.harvard.coinflow.lattice.principal.PrincipalFactory;

/**
 * Co-Inflow runtime library. The main component is a stack of context labels. 
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class IFCUtil {
	
	// Programmers should setup their desired lattices; the default lattice is a lattice for confidentiality	
	public static IFCLattice currentLattice = IFCLatticeGraphImpl.makeDefaultLattice();
		
	// must be called first;
	public static void initilize(IFCLattice lattice) {
		currentLattice = lattice;
		// The stack may be filled with the default bot before the right lattice is initialized:
		// it needs to be reset to the bot of current lattice
		for (int i =0; i <= labelArrayIdx.get(); i++) {
			labelArray.get()[i] = lattice.bot();
		}
	}

	private static int max_stack_size = 25000;
	private static ThreadLocal<IFCLabel[]> labelArray = new ThreadLocal<IFCLabel[]>() {
		@Override
        protected IFCLabel[] initialValue()
        {
			IFCLabel[] tmp = new IFCLabel[max_stack_size];
			tmp[0] = currentLattice.bot();
			return tmp;
        }
	};
	
	private static ThreadLocal<CallerLabelList[]> callLabelStack = new ThreadLocal<CallerLabelList[]>() {
		@Override
        protected CallerLabelList[] initialValue()
        {
			CallerLabelList[] callerLabels = new CallerLabelList[max_stack_size];
			return callerLabels;
        }
	};
	
	private static ThreadLocal<Integer> labelArrayIdx = new ThreadLocal<Integer>() {
		@Override
        protected Integer initialValue()
        {
			return 0;
        }
	};
	
	public static <K> Labeled<K> labelData(K data, IFCLabel label){
		checkToLabeledFlow(label);
		return new Labeled(data, label);
	}
	
	/**
	 * This method is for programmers to unlabel data. This method will return the
	 * data and raise the label of the current execution context
	 * 
	 * @param vl
	 *            the labeled data
	 * @return the value of the protected data
	 */
	public static <K> K unlabel(Labeled<K> vl) {
		joinTopLabel(vl.getLabel());
		return vl.getV();
	}

	/**
	 * This method is for programmers to check the label of the labeled data
	 * 
	 * @param <K>
	 * @param vl
	 *            the labeled data
	 * @return the label of the data
	 */
	public static <K> IFCLabel labelOf(Labeled<K> vl) {
		return vl.getLabel();
	}

	public static <K> K unlabelOpaque(OpaqueLabeled<K> vl_prime) {
		joinTopLabel(vl_prime.getLabel());
		return vl_prime.getV();
	}
	
	
	public static <K> OpaqueLabeled<K> toOpaqueLabeled(K methodCall){
		IFCLabel l = getCurrentLabel();
		return new OpaqueLabeled<K>(methodCall, l);
	}
	
	public static <K> OpaqueLabeled<K> toOpaqueLabeledSource(K methodCall, IFCLabel sourceLabel){
		IFCLabel l = currentLattice.join(getCurrentLabel(), sourceLabel);
		return new OpaqueLabeled<K>(methodCall, l);
	}

	public static void joinTopLabel(IFCLabel l) {
		IFCLabel l1 = labelArray.get()[labelArrayIdx.get()];
		IFCLabel joinLabel = currentLattice.join(l1, l);
		labelArray.get()[labelArrayIdx.get()] = joinLabel;
	}
	
	static List<String> outputChannels = new ArrayList<String>();
	
	public static void addOutputChannel(String path) {
		outputChannels.add(path);
	}

	/**
	 * we use this function to process legacy classes where we don't have access to source code. 
	 * If we have access to source code, the transformer shall rewrite them into label checks 'checkRaiseObjLabelFlow'. 
	 * 
	 * For these legacy classes, we use a hashmap indexed by weakreference to track these objects
	 * @param o
	 * @param l_prime
	 * @throws CIFCException 
	 */
	public static ConcurrentMap<Object, IFCLabel[]> objectLabelMap = new MapMaker().weakKeys().makeMap();
	
	
	public static boolean raiseObjLabel(Object input, IFCLabel l_prime) {
		Object o = getRuntimeVal(input);
		IFCLabel lo = getObjLabel(o);
		IFCLabel lf = getFieldLabel(o);
		IFCLabel l = getCurrentLabel();
		IFCLabel ell = getRuntimeLabel(o);
		IFCLabel join_l = currentLattice.join(l, ell);
		if (currentLattice.flowsTo(join_l, lo) && currentLattice.flowsTo(lf, l_prime)){
			setObjectLabels(o, lo, l_prime);
			return true;
		}else {
			processInformationLeak();
			return false;
		}
	}
	
	private static void setObjectLabels(Object o, IFCLabel lo, IFCLabel lf_prime) {
		objectLabelMap.put(o, new IFCLabel[] {lo, lf_prime});
	}
	
	
	public static IFCLabel getRuntimeLabel(Object o) {
		if(o instanceof OpaqueLabeled) {
			return ((OpaqueLabeled) o).getLabel();
		}else {
			return getCurrentLabel();
		}
	}
	
	public static Object getRuntimeVal(Object o) {
		if(o instanceof OpaqueLabeled) {
			return ((OpaqueLabeled) o).getV();
		}else {
			return o;
		}
	}
	
	public static void newObject(Object input) {
		Object o = getRuntimeVal(input);
		IFCLabel lb = getCurrentLabel();
		objectLabelMap.put(input, new IFCLabel[] {lb, lb});
	}
	
	public static IFCLabel getObjLabel(Object input) {
		if(input == null) {
			return getCurrentLabel();
		}
		Object o = getRuntimeVal(input);
        IFCLabel[] lbs = objectLabelMap.get(o);
        if(lbs != null) {
        		return lbs[0];
        }else {
	        	return setDefaultObjLabels(o)[0];
        }
	}
	
	public static IFCLabel[] setDefaultObjLabels(Object o) {
		IFCLabel ctxLbl = getCurrentLabel();
		IFCLabel[] defaults = new IFCLabel[] {ctxLbl, ctxLbl};
		if(o == null) {
			return defaults;
		}
//		if(! (o instanceof String)) {
			objectLabelMap.put(o, defaults);
//		}
		return defaults;
	}
	
	public static IFCLabel getFieldLabel(Object o) {
		if(o == null) {
			return getCurrentLabel();
		}
        IFCLabel[] lbs = objectLabelMap.get(o);
        if(lbs != null) {
        		return lbs[1];
        }else {
        		return setDefaultObjLabels(o)[1];
        }
	}
	
	
	public static <K> K opaqueFieldWrite(K data){
		return data;
	}
	
	public static <K> OpaqueLabeled<K> toOpaqueLabeled(K methodCall, IFCLabel l){
		return new OpaqueLabeled<K>(methodCall, l);
	}
	
	public static IFCLabel opaqueLabelOf(OpaqueLabeled opl) {
		return opl.getLabel();
	}
	
	public static <K> K opaqueValueOf(OpaqueLabeled<K> opl) {
		return opl.getV();
	}
	
	public static void newContainer() {
		int idx = labelArrayIdx.get();
		IFCLabel l = labelArray.get()[idx];
		labelArray.get()[++idx] = l;
		labelArrayIdx.set(idx);
		callLabelStack.get()[idx] = new CallerLabelList(new IFCLabel[0]);
	}
	

	public static void endContainer() {
		labelArrayIdx.set(labelArrayIdx.get()-1);
	}

	public static IFCLabel getCurrentLabel() {
		int idx = labelArrayIdx.get();
		IFCLabel curL = labelArray.get()[idx];
		return curL;
	}
	
	/**
	 * This is for toOpaqueLabeled; the callee's context label should not rise util the arguments are used inside the body. 
	 */
	public static void resetToCallerContextLabel(IFCLabel targetObjLabel) {
		int idx = labelArrayIdx.get();
		labelArray.get()[idx] = labelArray.get()[idx-1];
	}

	public static boolean checkFieldWriteFlow(IFCLabel objL, IFCLabel opaqueLabel) {
		if(!(currentLattice.flowsTo(currentLattice.join(getCurrentLabel(), opaqueLabel), objL))){
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	public static boolean checkFieldWriteFlow(IFCLabel objL) {
		if(!(currentLattice.flowsTo(getCurrentLabel(), objL))){
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	public static boolean checkRaiseObjLabelFlow(IFCLabel objectL, IFCLabel fieldL, IFCLabel targetLabel) {
		IFCLabel label = getCurrentLabel();
		int idx = labelArrayIdx.get();
		if(!currentLattice.flowsTo(label, objectL) ||  !currentLattice.flowsTo(fieldL, targetLabel)){
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	public static boolean checkToLabeledFlow(IFCLabel targetLabel) {
		if(!currentLattice.flowsTo(getCurrentLabel(), targetLabel)){
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	public static <K> Labeled<K> toLabeled(K data, IFCLabel label){
		return new Labeled(data, label);
	}
	
	public static IFCLabel getLabelById(String name) {
		return currentLattice.lookup(PrincipalFactory.makePrincipal(name));
	}
	
	/*
	 * config what should be done when information leak is found: 
	 * if leakConfig == 1, then raise an exception
	 * if leakConfig == 2, then stop the program
	 * or print an error message
	 */
	public static int leakConfig = 1;
	
	public static void processInformationLeak() {
		
		if(leakConfig == 1) {
			throw new CIFCException();
		}else if (leakConfig == 2) {
			System.exit(0);
		}else {
			System.err.println("INFORMATION FLOW LEAK");
		}
	}

	/**
	 * 
	 * @return if information leak, return false; otherwise return true
	 */
	public static boolean checkIfFlowToSink(IFCLabel sinkLabel) {
		if(!currentLattice.flowsTo(getCurrentLabel(), sinkLabel)) {
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	public static boolean checkIfFlowToSink(IFCLabel argumentLabel, IFCLabel sinkLabel) {
		IFCLabel l = getCurrentLabel();
		l = currentLattice.join(l, argumentLabel);
		if(!currentLattice.flowsTo(l, sinkLabel)) {
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	/**
	 * Check if the join of arguments' field label flows to the output channel
	 * @return if information leak, return false; otherwise return true
	 */
	public static boolean checkIfFlowToSink(List<IFCLabel> arguments, IFCLabel sinkLabel) {
		IFCLabel l = getCurrentLabel();
		for(IFCLabel argu : arguments) {
				IFCLabel t = (IFCLabel)argu;
				l = currentLattice.join(l, t);
		}
		if(!currentLattice.flowsTo(l, sinkLabel)) {
			processInformationLeak();
			return false;
		}
		return true;
	}
	
	/**
	 * Check sink 
	 */
	
	public static String callerMethodSignature = "";
	
	public static void setCurrentCallStack(IFCLabel[] callerLabels, String callerSignature) {
		callLabelStack.get()[labelArrayIdx.get()] = new CallerLabelList(callerLabels);
		callerMethodSignature = callerSignature;
	}
	
	public static IFCLabel[] getCurrentCallerLabels(String currentMethodSignature, int argumentCount) {
		if(currentMethodSignature.equals(callerMethodSignature)) {
			int idx = labelArrayIdx.get();
			return callLabelStack.get()[idx-1].callerLabels;
		}else {
			IFCLabel[] res = new IFCLabel[argumentCount];
			for(int i = 0; i < res.length; i++) {
				res[i] = getCurrentLabel();
			}
			return res;
		}
	}
	
	public static IFCLabel joinLabel(IFCLabel l1, IFCLabel l2) {
		return currentLattice.join(l1, l2);
	}
	
}
class CallerLabelList{
	final IFCLabel[] callerLabels;
	public CallerLabelList(IFCLabel[] inputs) {
		this.callerLabels = inputs;
	}
}
