package lbs.harvard.coinflow;

import java.util.function.Supplier;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.Labeled;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.lattice.principal.Principal;
import lbs.harvard.coinflow.lattice.principal.PrincipalFactory;

public class CoInFlowUserAPI {
	
	/**
	 * construct a labeled value
	 * @param data
	 * @param label
	 * @return a labeled value 
	 */
	public static <K> Labeled<K> labelData(K data, IFCLabel targetLabel){
		IFCUtil.checkToLabeledFlow(targetLabel);
		return IFCUtil.labelData(data, targetLabel);
	}
	
	/**
	 * unlabel a labeled value => retrieve the content and raise the context level 
	 * @param vl
	 * @return
	 */
	public static <K> K unlabel(Labeled<K> vl) {
		return IFCUtil.unlabel(vl);
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
		return IFCUtil.labelOf(vl);
	}
	
	
	public static boolean raiseObjLabel(Object o, IFCLabel l_prime) {
		return IFCUtil.raiseObjLabel(o, l_prime);
	}
	
	public static IFCLabel fieldLabelOf(Object o) {
		return IFCUtil.getFieldLabel(o);
	}
	
	public static IFCLabel getCurrentLevel() {
		return IFCUtil.getCurrentLabel();
	}
	
	public static <K> Labeled<K> toLabeled(Supplier<K> computation, IFCLabel label){
		return IFCUtil.labelData(computation.get(), label);
	}
	
	public static <K> Labeled<K> toLabeled(K computation, IFCLabel label){
		return IFCUtil.labelData(computation, label);
	}
	
	public static IFCLattice getLattice() {
		return IFCUtil.currentLattice;
	}
	
	/**
	 * setting a field to be sink.
	 */
	public static void markFieldAsSink(String classQName, String fieldName, IFCLabel sinkLabel) {
		IFCPolicyInternal.addFieldAsSink(classQName, fieldName, sinkLabel);
	}
	
	/**
	 * 
	 * @param classQName
	 * @param methodName
	 * @param parameterPosition first index is 1.
	 * @param sinkLabel
	 */
	public static void markMethodParameterAsSink(String classQName, String methodName, int parameterPosition, IFCLabel sinkLabel) {
		IFCPolicyInternal.addParameterAsSink(classQName, methodName, parameterPosition, sinkLabel);
	}
	
	public static String sinkLabelName = "";
	
	public static void markMethodParameterAsSink(String classQName, String methodName, int parameterPosition) {
		Principal p = PrincipalFactory.makePrincipal(sinkLabelName);
		IFCPolicyInternal.addParameterAsSink(classQName, methodName, parameterPosition, getLattice().lookup(p));
	}
	
	public static void markMethodReturnAsSink(String classQName, String methodName, IFCLabel sinkLabel) {
		IFCPolicyInternal.addReturnValAsSink(classQName, methodName, sinkLabel);
	}
	
	/**
	 * setting a field to be sink.
	 */
	public static void markFieldAsSource(String classQName, String fieldName, IFCLabel sinkLabel) {
		IFCPolicyInternal.addFieldAsSource(classQName, fieldName, sinkLabel);
	}
	
	public static void markMethodParameterAsSource(String classQName, String methodName, int parameterPosition, IFCLabel sinkLabel) {
		IFCPolicyInternal.addParameterAsSource(classQName, methodName, parameterPosition, sinkLabel);
	}
	
	public static void markMethodReturnAsSource(String classQName, String methodName, IFCLabel sinkLabel) {
		IFCPolicyInternal.addReturnValAsSource(classQName, methodName, sinkLabel);
	}
	
	
	public static int PRINT = 0;
	public static int TERMINATE = 2;
	public static int RUNTIME_EXCEPTION = 1;
	
	public static void setLeakReportOption(int option) {
		IFCUtil.leakConfig = option;
	}
	
	public static void initilize(IFCLattice lattice) {
		IFCUtil.initilize(lattice);
	}
	
	public static void checkFlowtoSink(IFCLabel sinkLabel) {
		IFCUtil.checkIfFlowToSink(sinkLabel);
	}
	
	public static void checkFlowtoSink(IFCLabel sourceLabel, IFCLabel sinkLabel) {
		IFCUtil.joinTopLabel(sourceLabel);
		checkFlowtoSink(sinkLabel);
	}
}
