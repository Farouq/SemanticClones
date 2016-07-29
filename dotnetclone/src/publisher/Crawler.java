package publisher;

import java.io.File;
import java.util.ArrayList;
 
public class Crawler {

	
	 private String __targetFileType="exe";
	 
	 public Crawler(String targetFileType)
	 {
		 __targetFileType=targetFileType;
	 }
	 
	private ArrayList<String> exeFileList=null;
	
	public ArrayList<String> findExeFiles(String rootAddress )
	{
		exeFileList=new ArrayList<String>( );
		findRecursively(new File(rootAddress));
		return(exeFileList);
	}
	 
	 
	    private void findRecursively(File current)// throws Exception 
	    {

	    	if (current.isDirectory() && !current.isHidden()
	    			&& current.getName() != ".svn") {
	    		for (File f : current.listFiles())
	    			try {

	    				this.findRecursively(f);
	    			} catch (Exception ex) {

	    		 
	    			} catch (Error e) {
	    		 	}
	    	} else if (current.isFile()) {
	    		detectExeFile(current);
	    	}
	    }

	    
	    private void detectExeFile(File current)
	    {

	    	try {
	    		String fileName = current.getName();
	    		int dotPos = fileName.lastIndexOf('.', fileName.length());
	    		if (dotPos > -1) {
	    			String extension = fileName.substring(dotPos + 1);
	    			if (extension.equalsIgnoreCase(__targetFileType)) {

	    				exeFileList.add(current.getPath());

	    			}
	    			
	    		}
	    	}catch(Exception ee)
	    	{
	 	} 
	    }
}
