package ca.usask.cs.srlab.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import simhash.SimhashCloneTest6e;
import configuration.Configuration;

public class ProjectSize {

	
	 private static String __targetFileType="cs";
	 private static int totalLoc=0;
	 private static ArrayList<String> fileList=null;
	 
		public static void main(String[] args) throws Exception{
			// TODO Auto-generated method stub
			fileList=findFiles("C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\mcs\\mcs\\");

			String lin;
			for(String s:fileList){
				System.out.println(s);
				 BufferedReader in = new BufferedReader(new FileReader(s));
				 int sum=0;
				 while ((lin = in.readLine()) != null) {
					 sum++;
				 }
				 totalLoc+=sum;
				 System.out.println(sum);
				
			}

			 System.out.println(totalLoc);


		}

	 
	
	private static ArrayList<String> findFiles(String rootAddress )
	{
		fileList=new ArrayList<String>( );
		findRecursively(new File(rootAddress));
		return(fileList);
	}
	 
	 
	    private static void findRecursively(File current)// throws Exception 
	    {

	    	if (current.isDirectory() && !current.isHidden()
	    			&& current.getName() != ".svn") {
	    		for (File f : current.listFiles())
	    			try {

	    				findRecursively(f);
	    			} catch (Exception ex) {

	    		 
	    			} catch (Error e) {
	    		 	}
	    	} else if (current.isFile()) {
	    		detectExeFile(current);
	    	}
	    }

	    
	    private static void detectExeFile(File current)
	    {

	    	try {
	    		String fileName = current.getName();
	    		int dotPos = fileName.lastIndexOf('.', fileName.length());
	    		if (dotPos > -1) {
	    			String extension = fileName.substring(dotPos + 1);
	    			if (extension.equalsIgnoreCase(__targetFileType)) {

	    				fileList.add(current.getPath());

	    			}
	    			
	    		}
	    	}catch(Exception ee)
	    	{
	 	} 
	    }

}
