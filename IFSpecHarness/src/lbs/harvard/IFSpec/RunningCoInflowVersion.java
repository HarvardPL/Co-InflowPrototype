package lbs.harvard.IFSpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.CIFCException;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.util.rifl.RIFLParser;

public class RunningCoInflowVersion {
	public static void main(String[] args) throws Exception {
		
		List<String> folders = new ArrayList<>();
		int testing_suite_count = 0;
		
		/*
		for(File testing_suite : originalFolder.listFiles()) {
			if(testing_suite.isDirectory()) {
				String packageName = testing_suite.getName().replaceAll("-", "");		
				String testing_case = testing_suite.getAbsolutePath();
				String riflFile = testing_case + "/rifl.xml";
				
				if(new File(riflFile).exists()) {
					String groundTruthFile = testing_case + "/ground-truth.txt";
					BufferedReader in = new BufferedReader(new FileReader(new File(groundTruthFile)));
					String groundTruth = in.readLine();
					System.out.println(groundTruth);
					in.close();
				}
			}
		}
		*/
		File originalFolder = IFSpecCompilation.originalFolder;
		for(File testing_suite : originalFolder.listFiles()) {
			if(testing_suite.isDirectory()) {
				String packageName = testing_suite.getName().replaceAll("-", "");		
				String testing_case = testing_suite.getAbsolutePath();
				String riflFile = testing_case + "/rifl.xml";
				
				if(new File(riflFile).exists()) {
					testing_suite_count++;
					System.out.println("Working on testing case #: " +testing_suite_count + " : " + testing_case);
					
					String groundTruthFile = testing_case + "/ground-truth.txt";
					BufferedReader in = new BufferedReader(new FileReader(new File(groundTruthFile)));
					String groundTruth = in.readLine();
					System.out.println("Ground truth of "+ testing_suite + " is: " + groundTruth);
					in.close();
					folders.add(packageName);
					IFCLattice lattice = RIFLParser.buildLattice(riflFile);
					CoInFlowUserAPI.initilize(lattice);
					CoInFlowUserAPI.setLeakReportOption(CoInFlowUserAPI.PRINT);
					List<String> exceptionCases = new ArrayList<>();
					try {
						
						// True Positive: contains leak, tool reports leak 
						// False Positive: sample contains no leak, tool reports leak
						// True Negative: sample contains no leak, tool reports no leak 
						// False Negative: sample contains leak, tool reports no leak
						
						
	 					switch (packageName) {
	 					
//	 						case "HighConditionalIncrementalLeaksecure":  // True Negative
//	 							Class clazz = Class.forName("HighConditionalIncrementalLeaksecure.Eg2");
//	 				            Method method = clazz.getMethod("main", String[].class);
//	 				            System.out.println(method.getParameterCount());
//	 				            final Object[] argus = new Object[1];
//	 				            argus[0] = new String[] { "1", "2"};
//	 				            method.invoke(null, argus);
//	 							break;
//	 						case "AliasingControlFlowsecure":  // False Positive: we cannot handle
//	 							Class clazz = Class.forName("AliasingControlFlowsecure.Main");
//	 				            Method method = clazz.getMethod("main", String[].class);
//	 				            final Object[] argus = new Object[1];
//	 				            argus[0] = new String[] { "1", "2"};
//	 				            method.invoke(null, argus);
//	 							break;
//	 						case "Webstore2":				// True Negative
//	 							Webstore2.Webstore.main(args);
//	 							break;
//	 						case "AliasingInterProceduralInsecure":   // true positive
//	 							AliasingInterProceduralInsecure.Main.main(args);
//	 							break;
//	 						case "LostInCast":			// false Positive
//	 							LostInCast.lostInCast.main(args);
//	 							break;
//	 						case "simpleRandomErasure1":			// True negative
//	 							simpleRandomErasure1.simpleRandomErasure.main(args);
//	 							break;
//	 						case "DirectAssignment":			// true negative
//	 							DirectAssignment.DirectAssignment.main(args);
//	 							break;
//	 						case "simpleArraySize":			// true negative
//	 							simpleArraySize.simpleArraySize.main(args);
//	 							break;
//	 						case "AliasingInterProceduralsecure":   // false positive: raiseFieldLabel fix it
//	 							AliasingInterProceduralsecure.Main.main(args);
//	 							break;
//	 						case "Webstore3":		// false positive
//	 							Webstore3.Webstore.main(args);
//	 							break;
//	 						case "Webstore4":		// true positive
//	 							Webstore4.Webstore.main(args);
//	 							break;
//	 						case "ImplicitListSizeLeak":      // true negative
//	 							ImplicitListSizeLeak.simpleListSize.main(args);
//	 							break;
//	 						case "ScenarioPasswordInsecure":   // True Negative
//	 							ScenarioPasswordInsecure.Main.main(args);
//	 							break;
//	 						case "StaticDispatching":			 // true negative
//	 							StaticDispatching.StaticDispatching.main(args);
//	 							break;
//	 						case "ReflectionSetSecretPrivateFieldInsecure":		// false negative
//	 							ReflectionSetSecretPrivateFieldInsecure.Main.main(args);
//	 							break;	 							
//	 						case "StaticInitializersNoLeak":		// to be done
//	 							StaticInitializersNoLeak.Static_Initializers_NoLeak.main(args);
//	 							break;
//	 						case "ExceptionalControlFlow1secure":   // false Positive
//	 							ExceptionalControlFlow1secure.program.main(args);
//	 							break;
//	 						case "StringIntern":			// true negative
//	 							StringIntern.program.main(args);
//	 							break;
//	 						case "StaticInitializersArrayAccesssecure":    // to be done
//	 							StaticInitializersArrayAccesssecure.Main.main(args);
//	 							break;
//	 						case "StaticInitializersNotCalled":			// true positive
//	 							StaticInitializersNotCalled.Main.main(args);
//	 							break;
//	 						case "IFLoop":    // false positive
//	 							IFLoop.IFLoop.main(args);
//	 							break;
//	 						case "simpleReflectionAccessPrivateField":		// false negative
//	 							simpleReflectionAccessPrivateField.SimpleReflection.main(args);
//	 							break;
//	 						case "AliasingNestedInsecure":			// False Negative
//	 							AliasingNestedInsecure.Main.main(args);
//	 							break;
//	 						case "timebomb":				// True Positive
//	 							timebomb.Main.main(args);
//	 							break;
//	 						case "ArraysImplicitLeaksecure":				// True Positive
//	 							ArraysImplicitLeaksecure.Main.main(args);
//	 							break;
//	 						case "ScenarioBankingInsecure":				// True Negative
//	 							ScenarioBankingInsecure.Main.main(args);
//	 							break;
//	 						case "HighConditionalIncrementalLeakInsecure":		// True Negative
//	 							HighConditionalIncrementalLeakInsecure.Eg2.main(args);
//	 							break;
//	 						case "ArrayIndexExceptionInsecure":		// This program exits(0)
//	 							ArrayIndexExceptionInsecure.Main.main(args);
//	 							break;
//	 						case "AliasingSimpleInsecure":			// True Negative
//	 							AliasingSimpleInsecure.Aliasing.main(args);
//	 							break;
//	 						case "BooleanOperationsInsecure":		// True Negative
//	 							BooleanOperationsInsecure.BooleanOperations.leakyMethod(true);
//	 						case "ExceptionDivZero":					// False Negative or True Negative
//	 							ExceptionDivZero.ExceptionDivZero.main(args);
//	 							break;
//	 						case "ObjectSensLeak":						// False Positive
//	 							ObjectSensLeak.ObjectSensLeak.main(args);
//	 							break;
//	 						case "DirectAssignmentsecure":				// True Positive
//	 							DirectAssignmentsecure.DirectAssignment.main(args);
//	 							break;
//	 						case "ConditionalLekage":					// True Negative
//	 							ConditionalLekage.DivisionByZero.main(args);
//	 							break;
//	 						case "ScenarioPasswordSecure":				// To be finished
//									ScenarioPasswordSecure.Main.main(args);
//	 							break;
//	 						case "StaticInitializersArrayAccessInsecure":	// To be finished
//	 								StaticInitializersArrayAccessInsecure.Main.main(args);
//	 							break;
//	 						case "simpleReflectionAccessPrivateFieldsecure":		// True Negative
//									simpleReflectionAccessPrivateFieldsecure.SimpleReflection.main(args);
//	 							break;
//	 						case "Webstore":				// False Positive
//	 							Webstore.Webstore.main(args);
//	 							break;
//	 						case "ReviewerAnonymityLeak":			// True Negative
//	 							ReviewerAnonymityLeak.ReviewProcess.main(args);
//	 							break;
//	 						case "simpleRandomErasure2":			// True Positive
//	 							simpleRandomErasure2.simpleRandomErasure.main(args);
//	 							break;
//	 						case "simpleConditionalAssignmentEqual":		// False Positive
//	 							simpleConditionalAssignmentEqual.simpleConditionalAssignmentEqual.main(args);
//	 							break;
//	 						case "StaticInitializersLeak":			// to be finished
//	 							StaticInitializersLeak.Static_Initializers_Leak.main(args);
//	 							break;					
//	 						case "Deepalias1":
//	 							Deepalias1.program.main(null);
//	 							break;
//	 						case "Deepalias2":	// true negative : toLabeled fix it
//	 							Deepalias2.program.main(null);
// 							break;
//	 						case "Deepcall1":
//	 							Deepcall1.program.main(null);
//	 							break;
//	 						case "Deepcall2":
//	 							Deepcall2.program.main(null);
//	 							break;
//	 						case "StaticInitializersHighAccessInsecure":   // to be finished
//								StaticInitializersHighAccessInsecure.Main.main(args);
//								break;
//	 						case "simpleTypesCastingError":			// True Negative
//	 							simpleTypesCastingError.simpleTypesCastingError.main(args);
//	 							break;
//	 						case "ArrayIndexExceptionsecure":     // True Negative
//	 							ArrayIndexExceptionsecure.Main.main(args);
//	 							break;
//	 						case "simpleClassLoading":			// False Negative
//	 							simpleClassLoading.simpleClassLoading.main(args);
//	 							break;
//	 						case "ReflectionSetSecretPrivateFieldsecure":   // True Positive
//	 							ReflectionSetSecretPrivateFieldsecure.Main.main(args);
//	 							break;
//	 						case "DirectAssignmentLeak":			// True Negative
//	 							DirectAssignmentLeak.Eg1.main(args);
//	 							break;
//	 						case "ExceptionalControlFlow2secure":			// False Positive
//	 							ExceptionalControlFlow2secure.program.main(args);
//	 							break;	
//	 						case "ArraySizeStrongUpdate":			// True Positive
//	 							ArraySizeStrongUpdate.Main.main(args);
//	 							break;
//	 						case "simpleListToArraySize":			// True Negative
//	 							simpleListToArraySize.simpleListToArraySize.main(args);
//	 							break;
//	 						case "CallContext":			// False Positive
//	 							CallContext.program.main(args);
//	 							break;
	 					// deepcall2
//	 						case "ArrayIndexSensitivitysecure":			// False Positive
//	 							ArrayIndexSensitivitysecure.program.foo(0);
//	 							break;
//	 						case "ArraysImplicitLeakInsecure":			// True Negative
//	 							ArraysImplicitLeakInsecure.Main.main(args);
//	 							break;
//	 						case "AliasingStrongUpdatesecure":			// True Negative
//	 							AliasingStrongUpdatesecure.Main.main(args);
//	 							break;
//	 						case "AliasingSimplesecure":						// False Positive
//	 							AliasingSimplesecure.Aliasing.main(args);	
//	 							break;
//	 						case "BooleanOperationssecure":				// False Positive
//	 							BooleanOperationssecure.BooleanOperations.leakyMethod(true);
//	 							break;
//	 						case "PasswordChecker":     // cannot run properly
//	 							PasswordChecker.PasswordChecker.main(args);
//	 							break;
//	 						case "ExceptionalControlFlow1Insecure":		// True Negative
//	 							ExceptionalControlFlow1Insecure.program.main(args);
//	 							break;
//	 						case "simpleListSize":					// True Negative
//	 							simpleListSize.simpleListSize.main(args);
//	 							break;
//	 						case "AliasingNestedsecure":				// False Negative; raiseLabelOf can fix
//	 							AliasingNestedsecure.Main.main(args);
//	 							break;
//	 						case "ReflectionAccessibilityModificationSecure":	// True Positive
//	 							ReflectionAccessibilityModificationSecure.SimpleReflection.main(args);
//	 							break;
//	 						case "AliasingControlFlowInsecure":		// True Negative
//	 							AliasingControlFlowInsecure.Main.main(args);
//	 							break;
//	 							Deepcall2
//	 						case "IFLoop2":						// True Negative
//	 							IFLoop2.IFLoop.main(args);
//	 							break;
//	 						case "ScenarioBankingSecure":		// False Positive
//	 							ScenarioBankingSecure.Main.main(args);
//	 							break;
//	 						case "simpleTypes":				// True Negative
//	 							simpleTypes.simpleTypes.main(args);
//	 							break;
//	 						case "ImplicitListSizeNoLeak":		// False Positive; helper method fix it
//	 							ImplicitListSizeNoLeak.simpleListSize.main(args);
//	 							break;
//	 						case "ExceptionHandling":			// True Negative
//	 							ExceptionHandling.TryCatch.main(args);
//	 							break;
//	 						case "StaticInitializersHighAccesssecure":  // False Positive
//	 							StaticInitializersHighAccesssecure.Main.main(args);
//	 							break;
//	 						case "IFMethodContract2":			// True Positive
//	 							IFMethodContract2.IFMethodContract.main(args);
//	 							break;
//	 						case "Polynomial":			// False Positive
//	 							Polynomial.MyClass.main(args);
//	 							break;
//	 						case "simpleErasureByConditionalChecks":		// False Positive
//	 							simpleErasureByConditionalChecks.SimpleErasureByConditionalChecks.main(args);
//	 							break;
//	 						case "ReflectionAccessibilityModification":	// False Negative
//								ReflectionAccessibilityModification.SimpleReflection.main(args);
//	 							break;
//	 						case "ArrayCopyDirectLeak":   // to be finished
//	 							ArrayCopyDirectLeak.Eg4.main(args);
//	 							break;
//	 						case "IFMethodContract":		// false Positive
//	 							IFMethodContract.IFMethodContract.main(args);
//	 							break;
//	 						case "ReviewerAnonymityNoLeak":		// false Positive
//	 							ReviewerAnonymityNoLeak.ReviewProcess.main(args);
//	 							break;
	 					}
					}catch (CIFCException e) {
						exceptionCases.add(packageName);
//						System.out.println("Error reported in "+ packageName);
					}
				}
			}
		}
		System.out.println("Total number of testing cases is " + testing_suite_count);
		
	}
}
