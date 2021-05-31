package lbs.harvard.IFSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.compiler.CoInflowCompiler;
import lbs.harvard.coinflow.util.rifl.RIFLParser;

public class IFSpecCompilation {		
	
	
	// public static File originalFolder = new File("/Users/llama_jian/Develop/IFSPEC_Coinflow/");
	// public static File generatedFolder = new File("/Users/llama_jian/Documents/workspace/IFSpecCoInflowGenerated/src/");
	public static File originalFolder = new File("pathto/source/folder");
	public static File generatedFolder = new File("pathto/output/folder");
	
	public static void main(String[] args) {	
		// transfer files from IFSPEC to this folder		
		File original = originalFolder;
		File dest = generatedFolder;
		for(File dir : original.listFiles()) {			
			if(dir.isDirectory()) {		
				File created = createFolder(dir, dest);	
				if(created != null) {
					if(created.isDirectory()) {
						if(!created.getName().startsWith("SecuriBench")) {
							addPackageInfo(created);	
						}
					}
				}
			}
		}
	}				
	
	public static File createFolder(File orginialFolder, File destfolder) {	
		String folderName = orginialFolder.getName();		
		String packageName = folderName.replaceAll("-", "");		
		String path = destfolder.getAbsolutePath() + "/" + packageName;		
		if(!new File(path).exists()) {			
			if(new File(orginialFolder.getAbsolutePath() + "/rifl.xml").exists()) {
				// We need to improve the handling of the deepcall samples
				if(!orginialFolder.getAbsolutePath().contains("Deep")) {
					System.out.println("===generating Co-Inflow code for testing case: " + orginialFolder.getAbsolutePath());
					generateCoInflowCode(orginialFolder.getAbsolutePath(), path);
					// create 'RunSample.java' file into each benchmark 
					generateRunSampleFile(orginialFolder.getAbsolutePath(), packageName, new File(path));
					System.out.println("===Testing case: " + orginialFolder.getAbsolutePath() +"finished");
					return new File(path);
				}
			}
			
		}	
		return null;
	}
	
	public static void generateRunSampleFile(String originalTestCaseFolder, String packageName, File newlyCreatedFolder) {
	      FileWriter writer;
		  try {
			  String filePath = "RunSample.txt";
		      String fileContents = readAllContents(filePath);
		      String folderPlaceHolder = "originalTestCaseFolderPlaceHolder";
		      fileContents = fileContents.replaceAll(folderPlaceHolder, originalTestCaseFolder);
		      String mainProgramName = "";
		      for (File f : newlyCreatedFolder.listFiles()) {
		    	  String tmp = readAllContents(f.getAbsolutePath());
		    	  if (f.getAbsolutePath().endsWith(".java") && tmp.contains("public static void main")) {
		    		  mainProgramName = "new "+ f.getName().replace(".java", "") + "().main(args)";
		    	  }
		      }
		      fileContents = fileContents.replace("mainMethodInvocation", mainProgramName);
    		  
		      String outputFilePath = newlyCreatedFolder + FileSystems.getDefault().getSeparator() + "RunSample.java";
		      writer = new FileWriter(outputFilePath);
			  writer.append(fileContents);
		      writer.flush();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	}
	
	public static String readAllContents(String filePath) throws FileNotFoundException {
		  Scanner sc = new Scanner(new File(filePath));
	      StringBuffer buffer = new StringBuffer();
	      while (sc.hasNextLine()) {
	         buffer.append(sc.nextLine()+System.lineSeparator());
	         
	      }
	      String fileContents = buffer.toString();
	      sc.close();
	      return fileContents;
	}
	
	public static void generateCoInflowCode(String testing_case_folder, String targetFolder) {
		String input_folder = testing_case_folder + "/program";
		String riflFile = 
				testing_case_folder + "/rifl.xml";
		CoInFlowUserAPI.initilize(RIFLParser.buildLattice(riflFile));
		CoInFlowUserAPI.setLeakReportOption(CoInFlowUserAPI.RUNTIME_EXCEPTION);
		CoInflowCompiler.main(new String[] {input_folder, targetFolder, riflFile});
	}
	
	
	public static void addPackageInfo(File folderPath) {		
		String packageName = folderPath.getName();		
		// add package info to Java files. 		
		for(File f : folderPath.listFiles()) {	
			if(f.getName().endsWith(".java")) {	
				BufferedReader br;				
				try {					
					br = new BufferedReader(new FileReader(f));		
					String result = "";					
					String line = "";					
					while( (line = br.readLine()) != null){						
						result = result + "\n" + line; 					
					}					
					f.delete();					
					result = "package "+ packageName + ";" + "\n" +  result;		
					try {						
						FileOutputStream fos = new FileOutputStream(f);	
						fos.write(result.getBytes());	
						System.out.println("creating new source file: " + f.getAbsolutePath());
						fos.flush();					
						fos.close();
						br.close();
					} catch (IOException e){	
							e.printStackTrace();					
					}
				} catch (FileNotFoundException e1) {					
					e1.printStackTrace();				
				} catch (IOException e1) {					
					e1.printStackTrace();				
				}			
			}
		}
	}	
	
	
	public static void copyFiles (File source, File target) {	
		if(source.exists()) {			
			for(File f : source.listFiles()) {	
				if(f.getName().endsWith(".java")) {		
					try {						
						Path t = new File(target.getAbsolutePath() + "/" + f.getName()).toPath();	
						Files.copy(f.toPath(), t);					
					}catch (IOException e) {						
							e.printStackTrace();						
					}				
				}
				
			}
		}	
	}
}
