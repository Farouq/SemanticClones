package ca.usask.cs.srlab.util;

import java.io.*;
//import java.lang.String;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class ManualAnalysisDiff{

    public static void main (String args []){

     PrintStream out = System.out;

   // if (args.length != 1) 
     //    out.println("Pls enter the xml file as the parameter");
      //         return;
   // }
   
  
                      
    try {
            String xMLfileName =args[0] ;
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(xMLfileName));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            // out.println ("Root element of the doc is " + 
              //   doc.getDocumentElement().getNodeName());
             // out.println("Threshold used : " + doc.getDocumentElement().getNodeName().  );
                  
            File currentDir = new File(".");
            String cdirPath=currentDir.getCanonicalPath(); 
           // out.println("The current Dir is: " + cdirPath);
            NodeList listOfClasses = doc.getElementsByTagName("class");
            int totalClasses = listOfClasses.getLength();
           out.println("<clones tclass=\"" + totalClasses + "\">");
           int len1=0;
           int len2=0;
            for(int i=0; i<listOfClasses.getLength() ; i++){

                 Node firstClassNode = listOfClasses.item(i);
              
                if(firstClassNode.getNodeType() == Node.ELEMENT_NODE){

                    Element firstClassElement = (Element)firstClassNode ; 
                    String classID = firstClassElement.getAttribute("id"); //getting the contents of the id attribute of the current element, class.
                    out.println("   <class id=\"" + classID + "\">");
                    //-------
                     NodeList firstCloneList = firstClassElement.getElementsByTagName("pcid");
                     int nClones = firstCloneList.getLength(); 
                     for (int j=0; j<nClones-1; j++){  //Not last clone file because the inner loop will access the last one. 
                         for (int k=j+1; k<nClones; k++){                         
                            // if (i!=k) {   
	    		                 //for first element
	            	          Element firstNameElement = (Element)firstCloneList.item(j);                   
    	            	           // String cloneSize1 =firstNameElement.getAttribute("size");  
                                 // int len1 = Integer.parseInt(cloneSize1.trim());	             	 
                                  NodeList textCloneList1=firstNameElement.getChildNodes();
                                  String cloneFileName1=  ((Node)textCloneList1.item(0)).getNodeValue().trim();                       
				                      String pcBaseName1=cloneFileName1.substring(0, (cloneFileName1.length()-5));  
                                  String fullCloneFileName1 = cdirPath + "/pcFiles/" + pcBaseName1 + ".pc"; 

                                  len1 =getCloneLen(fullCloneFileName1);  
                            
                                  //for second element   
  		   		                    Element secondNameElement = (Element)firstCloneList.item(k); //acces the secnd one, so j+i or we can say k.                  

                                 // String cloneSize2 = secondNameElement.getAttribute("size");  
                                  //int len2 = Integer.parseInt(cloneSize2.trim());	             	 
 
                     	          NodeList textCloneList2=secondNameElement.getChildNodes();
	                               String cloneFileName2=  ((Node)textCloneList2.item(0)).getNodeValue().trim();                       
						     	      // String fullCloneFileName2 = cdirPath + "/pcFiles/" + cloneFileName2; 	
                               
                                  String pcBaseName2=cloneFileName2.substring(0, (cloneFileName2.length()-5));  
                                  String fullCloneFileName2 = cdirPath + "/pcFiles/" + pcBaseName2 + ".pc"; 
                                  len2 =getCloneLen(fullCloneFileName2);  
                          String[] temp=getSimilarity(fullCloneFileName1, fullCloneFileName2, len1, len2).split("@@@@@");
                               // out.println(getSimilarity(fullCloneFileName1, fullCloneFileName2, len1, len2));    
                      // double clonePairSimilarity = getSimilarity(fullCloneFileName1, fullCloneFileName2, len1, len2);      
 
     	      //   out.println("The Clone Pair is:" + "(" + fullCloneFileName1 + "Size: " + cloneSize1 + "," + fullCloneFileName2 + "Size:"+  cloneSize2  + ")" );                                                // } //inner if loop 
                    
            // out.println("     <pair id1=\"" + pcBaseName1 + "\" len1=\"" + len1 + "\"  id2=\""  +  pcBaseName2 + "\" len2=\"" + len2 + "\" psim=\"" + clonePairSimilarity + "\"/>" );                                                // } //inner if loop 
              
out.println("     <pair id1=\"" + pcBaseName1 + "\" id2=\""  +  pcBaseName2 + "\" len1=\"" + len1 + "\" len2=\"" + len2 + "\" psim=\"" + temp[0] + "\">" );                                                // } //inner if loop 
out.println(temp[1]);
out.println("     </pair>");    

                         } //end of inner k for loop
                      
                     }  //end of inner j for loop                      
                out.println("   </class>");

                } //end of if
              } //end of outer i for loop   
          
             out.println("</clones>");

                    /*
                            
                       NodeList textFNList = firstNameElement.getChildNodes();
                    System.out.println("First Name : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());
                             
                    //-------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("last");
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    System.out.println("Last Name : " + 
                           ((Node)textLNList.item(0)).getNodeValue().trim());

                    //----
                    NodeList ageList = firstPersonElement.getElementsByTagName("age");
                    Element ageElement = (Element)ageList.item(0);

                    NodeList textAgeList = ageElement.getChildNodes();
                    System.out.println("Age : " + 
                           ((Node)textAgeList.item(0)).getNodeValue().trim());

                    //------
                 

                }//end of if clause

           
           }//end of for loop with s var
          */

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


public static int getCloneLen(String fileName) {
int countRec=0;
//long lastRec;
try
{
   RandomAccessFile randFile = new RandomAccessFile(fileName,"r");
  long lastRec=randFile.length();
   randFile.close();
   //System.out.println("Last lien:" + lastRec); 
   FileReader fileRead = new FileReader(fileName);
   LineNumberReader lineRead = new LineNumberReader(fileRead);
   lineRead.skip(lastRec);
   countRec=lineRead.getLineNumber();
   fileRead.close();
   lineRead.close();
}
catch(IOException e)
{ 
  e.printStackTrace();
  } 
 return countRec;
// return lastRec;
}



public static String getSimilarity(String fileName1, String fileName2, int fileSize1, int fileSize2) {
 String diffOutput=null; 
 double simValue=0.0;
try {

   // Process p = Runtime.getRuntime().exec("diff data1.xml data2.xml"); //or just "ls" or any commands
   //Process p = Runtime.getRuntime().exec("diff -bi " + args[0] + " " + args[1]); //takes two files and output their diff. 
	    //		  Process p = Runtime.getRuntime().exec("diff -cb data1.xml data2.xml");
     
            Process p = Runtime.getRuntime().exec("diff -bi " + fileName1 + " " + fileName2); //takes two files and output their diff. 

             PrintStream out = System.out;
               int leftDistinct=0; 
              // int rightDistinct=0;

             BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      // String diffOutput=null;
	      String line = null;
	      while ((line = in.readLine()) != null) {
	  
         diffOutput = "\t\t" + diffOutput + line + "\n";
         if (line.substring(0,1).contains("<")){ 
                         // out.println(line);  
               leftDistinct++;
                 }
        
         }          
    	   // out.println("Diff:" + diffOutput);  
           // if (line.contains("> "))	                 	                                        		                                
              //  rightDistinct++;                                        		                                                 
              //  System.out.println(line); //just output to the consol.           
    	         // out.println("Unique in left file:" + leftDistinct + " \n" );
              // out.println("Unique in right file:" + rightDistinct + " \n" );
                int sequenceLength = fileSize1 - leftDistinct;  
               // out.println("seq is: "  + sequenceLength + "file1 " + fileSize1 + "left " + leftDistinct);  
                simValue =(double) ((double)sequenceLength/((double)fileSize1 + (double)fileSize2 -(double) sequenceLength));
               }  
  catch (IOException e) {	                 	                                        		       
     e.printStackTrace() ;
   }
  return simValue+"";// + "@@@@@" + diffOutput;              
  }


public static double getUPI(String fileName1, String fileName2, int fileSize1, int fileSize2) {
 String diffOutput=null; 
 double simValue=0.0;
try {

   // Process p = Runtime.getRuntime().exec("diff data1.xml data2.xml"); //or just "ls" or any commands
   //Process p = Runtime.getRuntime().exec("diff -bi " + args[0] + " " + args[1]); //takes two files and output their diff. 
	    //		  Process p = Runtime.getRuntime().exec("diff -cb data1.xml data2.xml");
     
            Process p = Runtime.getRuntime().exec("diff -bi " + fileName1 + " " + fileName2); //takes two files and output their diff. 

             PrintStream out = System.out;
               int leftDistinct=0; 
              // int rightDistinct=0;

             BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      // String diffOutput=null;
	      String line = null;
	      while ((line = in.readLine()) != null) {
	  
         diffOutput = "\t\t" + diffOutput + line + "\n";
         if (line.substring(0,1).contains("<")){ 
                         // out.println(line);  
               leftDistinct++;
                 }
        
         }          
    	   // out.println("Diff:" + diffOutput);  
           // if (line.contains("> "))	                 	                                        		                                
              //  rightDistinct++;                                        		                                                 
              //  System.out.println(line); //just output to the consol.           
    	         // out.println("Unique in left file:" + leftDistinct + " \n" );
              // out.println("Unique in right file:" + rightDistinct + " \n" );
                int sequenceLength = fileSize1 - leftDistinct;  
               // out.println("seq is: "  + sequenceLength + "file1 " + fileSize1 + "left " + leftDistinct);  
                simValue =(double) ((double)leftDistinct*100/(double)fileSize1);
               }  
  catch (IOException e) {	                 	                                        		       
     e.printStackTrace() ;
   }
  return simValue;// + "@@@@@" + diffOutput;              
  }

public static double getUPIForTextBlock(String text1, String text2, int length1, int length2) {
	
	FileOutputStream fo1 = null, fo2 = null;
	File file1 = null, file2 = null; 
	try{
		file1 = new File("src/ca/usask/cs/srlab/util/tmp1.txt");
		fo1 = new FileOutputStream(file1);
		fo1.write(text1.getBytes());
	
		file2 = new File("src/ca/usask/cs/srlab/util/tmp2.txt");
		fo2 = new FileOutputStream(file2);
		fo2.write(text2.getBytes());
	}catch(IOException e){
		e.printStackTrace();
	}

	finally{
		try {
			fo1.close();
			fo2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	return getUPI(file1.getPath(), file2.getPath(), length1, length2);
	
}


}
