package nicad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.Configuration;

import simhash.SimhashCloneTest6e;

/*
 * test github
 * what problem is here
 */


public class Leven {

	public static void main(String[] args) throws Exception {
		
		Configuration config=Configuration.loadFromFile();
		//StarterPublisher.start(config);
		
		for(int i=1;i<=22;i++){
		//filters.Filter1.filter(config.disassebledAddress+"\\F ("+i+").xml");
		//filters.Filter4.filter(config.disassebledAddress+"\\F ("+i+")_f1.xml");
		System.out.println(" file number iiiii"+i+"------------------------------------------------------"+i);
		//readFiles(config.disassebledAddress+"\\F ("+i+")_f1_f4.xml");
		readFiles(config.disassebledAddress+"\\chap ("+i+")_f1_f4.xml");
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
				  System.out.print("VB------------C#---------SIM: ");
				  System.out.println(1-(getLevenshteinDistance(codes[1],codes[3])/max));
				  
				  max= (codes[1].length()>codes[5].length())? codes[1].length():codes[5].length();
				  System.out.print("VB------------J#---------SIM: ");
				  System.out.println(1-(getLevenshteinDistance(codes[1],codes[5])/max));
				  
				  max= (codes[3].length()>codes[5].length())? codes[3].length():codes[5].length();
				  System.out.print("C#------------J#---------SIM: ");
				  System.out.println(1-(getLevenshteinDistance(codes[3],codes[5])/max));


			} }
		
			catch (Exception e) {
				e.printStackTrace();
			}
		

	
	}
	

	
	 public static int getLevenshteinDistance (String s, String t) {
		  if (s == null || t == null) {
		    throw new IllegalArgumentException("Strings must not be null");
		  }



		  int n = s.length(); // length of s
		  int m = t.length(); // length of t

		  if (n == 0) {
		    return m;
		  } else if (m == 0) {
		    return n;
		  }

		  int p[] = new int[n+1]; //'previous' cost array, horizontally
		  int d[] = new int[n+1]; // cost array, horizontally
		  int _d[]; //placeholder to assist in swapping p and d

		  // indexes into strings s and t
		  int i; // iterates through s
		  int j; // iterates through t

		  char t_j; // jth character of t

		  int cost; // cost

		  for (i = 0; i<=n; i++) {
		     p[i] = i;
		  }

		  for (j = 1; j<=m; j++) {
		     t_j = t.charAt(j-1);
		     d[0] = j;

		     for (i=1; i<=n; i++) {
		        cost = s.charAt(i-1)==t_j ? 0 : 1;
		        // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
		        d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
		     }

		     // copy current distance counts to 'previous row' distance counts
		     _d = p;
		     p = d;
		     d = _d;
		  }

		  // our last action in the above loop was to switch d and p, so p now
		  // actually has the most recent cost counts
		  return p[n];
		}



}









