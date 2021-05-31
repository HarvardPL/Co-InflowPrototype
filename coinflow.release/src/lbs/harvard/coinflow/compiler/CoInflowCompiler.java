package lbs.harvard.coinflow.compiler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.util.rifl.RIFLParser;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtVariableReference;

/**
 * Co-Inflow compiler is implemented as a series of spoon processors
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class CoInflowCompiler {

	// counter for local variables created
	public static int counter = 0;
	public static List<String> classesProcessed = new ArrayList<>();
	
	// record all methods that have been rewritten
	public static List<String> methodProcessed = new ArrayList<>();
	
	private static List<String> getAllJavaFiles(String topFolder){
		List<String> result = new ArrayList<>();
		File d = new File(topFolder);
		if (d.getAbsolutePath().endsWith(".java")) {
			result.add(d.getAbsolutePath());
			return result;
		}
		for (File f : d.listFiles()) {
			if(f.isDirectory()) {
				result.addAll(getAllJavaFiles(f.getAbsolutePath()));
			}else {
				if (f.getAbsolutePath().endsWith(".java")) {
					result.add(f.getAbsolutePath());
				}
			}
		}
		return result;
	}
	
	private static void copyInputOutputFolder(String inputFolder, String outputFolder) {
		try {
			FileUtils.copyDirectory(new File(inputFolder), new File(outputFolder), new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return !pathname.getAbsolutePath().endsWith(".java");
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean logging = true;
	
	public static String latticeFile = null;
	
	public static void main(String[] args) {
		String outputFolder = "pathto/output/folder";
		
		Launcher launcher = new Launcher();
		// path should be a folder
		String inputFolder = "pathto/source/folder";
		
		String channelsFile = null;
		if(args.length < 2) {
			System.out.println("Typical command for running Co-Inflow: \n" + "1. java -jar coinflow.jar 'location of sources files' 'location for output files'" );
			System.out.println("2. java -jar coinflow.jar 'location of sources files' 'location for output files' 'location of file that specifies sinks and source channels'" );
			return;
		}	
		
		if(args.length == 3) {
			channelsFile = args[2];
		}
		
		inputFolder = args[0];
		outputFolder = args[1];
		
		System.out.println("processing files in: " + inputFolder);
		System.out.println("Output fold is: " + outputFolder);
		//System.out.println("Start processing source files");
		List<String> allJavaFiles = getAllJavaFiles(inputFolder);
		
		// do not process the lattice builder file
		//String latticeFile = findLatticeBuilderFile(allJavaFiles);
		
		copyInputOutputFolder(inputFolder, outputFolder);
		allJavaFiles.remove(latticeFile);
		for(String f : allJavaFiles) {
			launcher.addInputResource(f);
		}
		copyLatticeBuilderFile(latticeFile, outputFolder);
		// if true, the pretty-printed code is readable without fully-qualified names
		launcher.getEnvironment().setAutoImports(true); // optional
		// set to false to get a clean file structures; copy resources may disrupt the file organization
		launcher.getEnvironment().setCopyResources(false);

		// if true, the model can be built even if the dependencies of the analyzed source code are not known or incomplete
		// the classes that are in the current classpath are taken into account
		launcher.getEnvironment().setNoClasspath(true); // optional
		launcher.buildModel();
		
		CtModel model = launcher.getModel();
		
		if(channelsFile != null) {
			coInFlowProcessing(model, channelsFile);
		}else {
			coInFlowProcessing(model);
		}
		
		// record all classes that will be processed
		model.processWith(new ClassVisitedProcessor());
		launcher.setSourceOutputDirectory(outputFolder);
		launcher.prettyprint();
		
		// launcher.prettyprint();
		System.out.println("Processing finished");
	}
	
	
	public static void coInFlowProcessing(CtModel model){
		
		// check if has been processed, if yes, then stop
		model.processWith(new CheckIfProcessedProcessor());
		if(CheckIfProcessedProcessor.processed) {
			System.err.println("The input files have been rewritten, we cannot proceed.");
			return;
		}
		
		// Preprocess switch case 
		model.processWith(new SwitchCaseProcessor());
		
		// Preprocess tenary expression
		model.processWith(new TenaryExprProcessor());
		
		// process || and &&
		while(true) {
			LogicalAndProcessor la = new LogicalAndProcessor();
			model.processWith(la);
			if(!la.isProcessedAndOp()) {
				break;
			}
		}
		model.processWith(new LambdaBlockProcessor());
		
		// reorganize the initializations of fields, by inserting blocks after these fields
		// TODO: change modifiers of final static fields into static fields
		model.processWith(new ReorganizeInitilizationProcessor());
		
		// preprocess all arguments towards this() or super() constructor calls				
		model.processWith(new SuperArguProcessor());
		
		// create local variables for return expressions				
		// model.processWith(new ReturnProcessor());
		
		// record all outputChannels
		model.processWith(new RecordChannelProcessor());
		
		// process loop
		model.processWith(new CtForEachProcessor());
		model.processWith(new WhileLoopProcessor());
		model.processWith(new ForLoopProcessor());
		
		// preprocess the unary operators
		model.processWith(new UnaryOperatorProcessor1());
		
		// Step 1a: create local variables for method calls
		model.processWith(new ElementToBeRewrittenProcessor());
		
		if(logging) {
			System.out.println("Creating field labels and object labels Processor");
		}
		
		// rewrite object label calls to field access
		model.processWith(new ObjectLabelProcessor());
		
		if(logging) {
			System.out.println("Entering DataFlow Analyzer");
		}
		
		// using a static analyzer to get data flow graph between local variables
		StaticDataFlowAnalyzerProcessor analyzer = new StaticDataFlowAnalyzerProcessor();
		model.processWith(analyzer);
		Map<CtExecutableReference, Map<CtVariableReference, Set<CtVariableReference>>> affectingLinks 
			= analyzer.getAffectingLinks();
		Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars = 
				analyzer.getOpaqueLabeledVars();
		analyzer.fullPropagation(affectingLinks, opaqueLabeledVars);
		analyzer.deleteCtForEachLocals(opaqueLabeledVars);
		
		
		if(logging) {
			System.out.println("Entering Local Variable Processor");
		}
		// Step 2: rewrite all local variables' types to OpaqueLabeled<T>, including all parameters
		model.processWith(new LocalVariableProcessor(opaqueLabeledVars));		
		
		
		if(logging) {
			System.out.println("Entering Parameter Rewritting Processor");
		}
		// add an additional parameter to every method
		model.processWith(new ParameterRewritingProcessor());
		// relink references of old parameters to newly created local variables which are opaqueLabeled
		model.processWith(new ParameterRefProcessor());
				
		if(logging) {
			System.out.println("Entering Argument Processor");
		}
		// rewrite all arguments from for example x to toOpaqueLabeled(x) 
		model.processWith(new ArgumentProcessor());
		
		model.processWith(new ConstructorCallProcessor());
		
		// rewrite all contructors to setup object labels
		model.processWith(new ConstructorObjFieldLabelsProcessor());
		
		if(logging) {
			System.out.println("Entering Container Processor");
		}
		// Transforming method calls, field reads, and field writes that add 'newContainer' and 'endContainer' around the statements
		model.processWith(new ContainerProcessor());
		
		if(logging) {
			System.out.println("Entering Source Channel Processor");
		}
		
		// join the labels specified in sources channels
		model.processWith(new SourceChannelProcessor());
		
		if(logging) {
			System.out.println("Entering Label Check Processor");
		}
		
		
		// Check if return statement has unlabelOpaque()		
		model.processWith(new ReturnProcessor());
				
				
		// Check flow requirements for field write; and raise context label for field reads
		model.processWith(new LabelCheckProcessor());
		
		
		
		
		// insert label controls for library calls
		model.processWith(new LibraryCallProcessor());
		
		// change the code for raising object label
		model.processWith(new RaiseObjLabelProcessor());
		
		// insert check for output channels 
		model.processWith(new OutputChannelProcessor());
		
		
		// add a field in every class for field label / object label
		model.processWith(new AddFieldObjectLabelFieldProcessor());
		
		// remove unnecessary code
		model.processWith(new RemoveUnnecessaryCode());
		
	}
	
	
	public final static void copyLatticeBuilderFile(String latticeFile, String outputFolder) {
		if(latticeFile!=null) {
			Launcher launcher = new Launcher();
			launcher.addInputResource(latticeFile);
			launcher.buildModel();
			launcher.setSourceOutputDirectory(outputFolder);
			launcher.prettyprint();
		}
	}
	
	public static void coInFlowProcessing(CtModel model, String pathToRTFLFile) {
		RIFLParser.parsing(pathToRTFLFile);
		for(String s : IFCPolicyInternal.staticSinkAnnotations.keySet()) {
			RecordChannelProcessor.annotatedStaticSinkLabels.put(
					s, IFCPolicyInternal.staticSinkAnnotations.get(s));
		}
		for(String s : IFCPolicyInternal.staticSourceAnnotations.keySet()) {
			RecordChannelProcessor.annotatedStaticSourceLabels.put(
					s, IFCPolicyInternal.staticSourceAnnotations.get(s));
		}
		coInFlowProcessing(model);
	}

}
