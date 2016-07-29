package lcs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.Configuration;

public class Lcs {

	/**
	 * @param args
	 * Find LCS similarity between 3 fragments in the xml file
	 */
	
	private static final int NEITHER     = 0;
	private static final int UP          = 1;
	private static final int LEFT        = 2;
	private static final int UP_AND_LEFT = 3;
	
	
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub

		

		Configuration config=Configuration.loadFromFile();
		//StarterPublisher.start(config);
		System.out.println("no filter");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+").xml");
		}
		
		System.out.println(" Filter 1");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1.xml");
		}
		
		System.out.println(" Filter 1_4");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4.xml");
		}
		
		System.out.println(" Filter 1_4_2");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_2.xml");
		}
		
		System.out.println(" Filter 1_4_5");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_5.xml");
		}
		
		System.out.println(" Filter 1_4_6");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_6.xml");
		}
		
		System.out.println(" Filter 1_4_7");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_7.xml");
		}
		
		System.out.println(" Filter 1_4_3");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_3.xml");
		}

		System.out.println(" Filter 1_4_8");
		for(int i=1;i<=25;i++){
		readFiles(config.disassebledAddress+"\\f ("+i+")_f1_4_8.xml");
		}
	}
	public static void readFiles(String rawFunctionsFileName) throws IOException{


		boolean error = false;

		File fileName = new File(rawFunctionsFileName);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fileName);
			doc.getDocumentElement().normalize();

			Element root = doc.getDocumentElement();

			NodeList nl = root.getElementsByTagName("name");
			if(nl.getLength()>0){
				//projectName = nl.item(0).getFirstChild().getNodeValue();
			}else{
				error = true;
			}

			nl = root.getElementsByTagName("description");
			if(nl.getLength()>0){
				//projectDesc = nl.item(0).getChildNodes().item(0).getNodeValue();
			}else{
				error = true;
			}

			nl = root.getElementsByTagName("source_elements");
			if(nl.getLength()>0){
				NodeList sourceList = nl.item(0).getChildNodes();

				long items =0;
				
				String[] codes= new String[6];
				
				for(int i =0; i < sourceList.getLength(); i++){
					Node source = sourceList.item(i);
					if (source.getNodeType() != Node.ELEMENT_NODE) 
						continue;

					String file = source.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					String startline = source.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
					String endline = source.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
					String content = source.getFirstChild().getTextContent();
					codes[i]=source.getFirstChild().getTextContent();

					items++;
					//  System.out.println(i);

				}
				//System.out.println(codes[0].length());
				  double max= (codes[1].length()>codes[3].length())? codes[1].length():codes[3].length();
				  
				  String []vbA = codes[1].split("\n");
				  String []csA = codes[3].split("\n"); 				  
				  String []jsA = codes[5].split("\n");
				  
				  ArrayList<String> vb=new ArrayList<String>();
				  ArrayList<String> cs=new ArrayList<String>();
				  ArrayList<String> js=new ArrayList<String>();
				  
				  for(String t:vbA){
					  
				  if(t.trim()==null &&t.trim().isEmpty())
					  continue;
					  vb.add(t.trim());
				  }
					  
				  
				  for(String t:csA){
					  if(t.trim()==null &&t.trim().isEmpty())
						  continue;
					  cs.add(t.trim());
				  }
				  
				  for(String t:jsA){
					  if(t.trim()==null &&t.trim().isEmpty())
						  continue;
					  js.add(t.trim());
				  }
					  

				  
		//		  System.out.println("VB------------C#---------SIM: ");
				  
		//		  System.out.println(LCSAlgorithm(vb,cs));
				
		
				  System.out.println((double)LCSAlgorithm(vb,cs).size()*2/(vb.size()+cs.size())+" ");
			      System.out.println((double)LCSAlgorithm(vb,js).size()*2/(vb.size()+js.size())+" ");
				  System.out.println((double)LCSAlgorithm(cs,js).size()*2/(cs.size()+js.size()));
				 



			} }
		
			catch (Exception e) {
				e.printStackTrace();
			}
		

	
	}

	public static ArrayList<String> longestSubstring(ArrayList<String> str1, ArrayList<String> str2) {

		ArrayList<String> sb1=new ArrayList<String>();

		if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
			return sb1;


		// ignore case i
		// is not important in our case
		//	str1 = str1.toLowerCase();
		//	str2 = str2.toLowerCase();

		// java initializes them already with 0

		int[][] num = new int[str1.size()][str2.size()];
		int maxlen = 0;
		int lastSubsBegin = 0;

		for (int i = 0; i < str1.size(); i++) {
			for (int j = 0; j < str2.size(); j++) {
				if (str1.get(i).equals(str2.get(j))) {
					if ((i == 0) || (j == 0))
						num[i][j] = 1;
					else
						num[i][j] = 1 + num[i - 1][j - 1];

					if (num[i][j] > maxlen) {
						maxlen = num[i][j];
						// generate substring from str1 => i
						int thisSubsBegin = i - num[i][j] + 1;
						if (lastSubsBegin == thisSubsBegin) {
							//if the current LCS is the same as the last time this block ran
							//sb.append(str1.charAt(i));
							sb1.add(str1.get(i));
						} else {
							//this block resets the string builder if a different LCS is found
							lastSubsBegin = thisSubsBegin;

							sb1.clear();
							for(int k=lastSubsBegin;k<=i+1;k++){
								sb1.add(str1.get(k));
							}


						}
					}
				}
			}}

		return sb1;
	}

	
	public static int longestSubstr(ArrayList<String> first, ArrayList<String> second) {
	    if (first.size()==0 || second.size() == 0) {
	        return 0;
	    }
	 
	    int maxLen = 0;
	    int fl = first.size();
	    int sl = second.size();
	    int[][] table = new int[fl][sl];
	 
	    for (int i = 0; i < fl; i++) {
	        for (int j = 0; j < sl; j++) {
	            if (first.get(i).equals(second.get(j))) {
	                if (i == 0 || j == 0) {
	                    table[i][j] = 1;
	                }
	                else {
	                    table[i][j] = table[i - 1][j - 1] + 1;
	                }
	                if (table[i][j] > maxLen) {
	                    maxLen = table[i][j];
	                }
	            }
	        }
	    }
	    return maxLen;
	}

	
	
	
	/*
	 * the implemantation of this method taken from
	 * http://bix.ucsd.edu/bioalgorithms/downloads/code/LCS.java
	 * 
	 */
	public static ArrayList<String> LCSAlgorithm(ArrayList<String> a, ArrayList<String> b) {
		int n = a.size();
		int m = b.size();
		int S[][] = new int[n+1][m+1];
		int R[][] = new int[n+1][m+1];
		int ii, jj;

		// It is important to use <=, not <.  The next two for-loops are initialization
		for(ii = 0; ii <= n; ++ii) {
			S[ii][0] = 0;
			R[ii][0] = UP;
		}
		for(jj = 0; jj <= m; ++jj) {
			S[0][jj] = 0;
			R[0][jj] = LEFT;
		}
		

		// This is the main dynamic programming loop that computes the score and
		// backtracking arrays.
		for(ii = 1; ii <= n; ++ii) {
			for(jj = 1; jj <= m; ++jj) { 
	
				if( a.get(ii-1).equals(b.get(jj-1)) ) {
						
					S[ii][jj] = S[ii-1][jj-1] + 1;
					R[ii][jj] = UP_AND_LEFT;
				}

				else {
					S[ii][jj] = S[ii-1][jj-1] + 0;
					R[ii][jj] = NEITHER;
				}

				if( S[ii-1][jj] >= S[ii][jj] ) {	
					S[ii][jj] = S[ii-1][jj];
					R[ii][jj] = UP;
				}

				if( S[ii][jj-1] >= S[ii][jj] ) {
					S[ii][jj] = S[ii][jj-1];
					R[ii][jj] = LEFT;
				}
			}
		}

		// The length of the longest substring is S[n][m]
		ii = n; 
		jj = m;
		int pos = S[ii][jj] - 1;
		ArrayList<String> lcs = new ArrayList<String>();

		// Trace the backtracking matrix.
	
		
		while( ii > 0 || jj > 0 ) {
			if( R[ii][jj] == UP_AND_LEFT ) {
				ii--;
				jj--;
				lcs.add(a.get(ii));
				
			}
	
			else if( R[ii][jj] == UP ) {
				ii--;
			}
	
			else if( R[ii][jj] == LEFT ) {
				jj--;
			}
		}

		ArrayList<String> lcs2 = new ArrayList<String>();
		for(int k=lcs.size()-1; k>=0;k--)
			lcs2.add(lcs.get(k));
	
		return lcs2;
	}
	
}


