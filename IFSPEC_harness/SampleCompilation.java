
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.RIFLParser;
import processors.outsidemethod.MainProcessor;


public class SampleCompilation {		
	
	public static File generatedFolder = new File("/Users/llama_jian/Documents/workspace/IFSpecCoInflowGenerated/src/");
	public static File originalFolder = new File("/Users/llama_jian/Develop/IFSPEC_Coinflow/");
	
	public static void main(String[] args) {	
		// transfer files from IFSPEC to this folder		
		File original = originalFolder;
		File dest = generatedFolder;
		for(File dir : original.listFiles()) {			
			if(dir.isDirectory()) {				
				File created = createFolder(dir, dest);	
				if(created != null) {
					if(created.isDirectory()) {
						addPackageInfo(created);	
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
//			new File(path).mkdir();			
			System.out.println("generating Co-Inflow code for testing case: " + orginialFolder.getAbsolutePath());
			if(new File(orginialFolder.getAbsolutePath() + "/rifl.xml").exists()) {
				if(!orginialFolder.getAbsolutePath().contains("Deep")) {
					generateCoInflowCode(orginialFolder.getAbsolutePath(), path);
					return new File(path);
				}
				else if(orginialFolder.getAbsolutePath().contains("Deepcall2")) {
					generateCoInflowCode(orginialFolder.getAbsolutePath(), path);
					return new File(path);
				}
			}
		}	
		return null;
	}
	
	public static void generateCoInflowCode(String testing_case_folder, String targetFolder) {
		String input_folder = testing_case_folder + "/program";
		String riflFile = 
				testing_case_folder + "/rifl.xml";
		CoInFlowUserAPI.setLattice(RIFLParser.buildLattice(riflFile));
		CoInFlowUserAPI.setLeakReportOption(CoInFlowUserAPI.RUNTIME_EXCEPTION);
		MainProcessor.main(new String[] {input_folder, riflFile, targetFolder});
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
