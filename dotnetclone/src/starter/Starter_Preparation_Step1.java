package starter;
 
import java.io.File;

import configuration.Configuration;
 
public class Starter_Preparation_Step1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	
		Configuration config=Configuration.loadFromFile();

		mkdir(config.sourceCodeAddress);
		mkdir(config.byteCodeAddress);
		mkdir(config.disassebledAddress);
		mkdir(config.disassebledAddress_lineNo);
		mkdir(config.ontologyAddress);
		mkdir(config.sparqlAddress);
		mkdir(config.reportAddress);	
		
		
		System.out.println("Now copy SourceCode files into: " +config.sourceCodeAddress); 
		System.out.println("Now copy Bytecode files into: " +config.byteCodeAddress); 
		//System.out.println(config.comparisonMethod);
		
	}
	
	
	
	
	private static void mkdir(String address)
	{
		 String strManyDirectories=address;
		  boolean success =  (new File(strManyDirectories)).mkdirs();
		  if (success) {
		  System.out.println("Directories: " 
		   + strManyDirectories + " created");
		  }
		
	}
	
	
	

}
