package nicad;

import java.io.BufferedWriter;
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

import simhash.CloneGroup;
import simhash.SourceItem;
import simhash.TestHash2;
import ca.usask.cs.srlab.util.XMLUtil;
import configuration.Configuration;;

public class LevenshteinForOneFile {

	private static ArrayList<String> vbcode=new ArrayList<String>();
	private static ArrayList<String> cscode=new ArrayList<String>();
	private static ArrayList<String> methodcode=new ArrayList<String>();

	private static ArrayList<ArrayList<String>> vbdata=new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> csdata=new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> methoddata=new ArrayList<ArrayList<String>>();

	private static final int NEITHER     = 0;
	private static final int UP          = 1;
	private static final int LEFT        = 2;
	private static final int UP_AND_LEFT = 3;

	/**
	 * @param args
	 * Read two xml files and measure similarity between their nodes
	 * 
	 * 
	 */
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		Configuration config=Configuration.loadFromFile();
		
		//on mac
		//String address= config.disassebledAddress;
		//lev(address+"/allFiles.xml_0_binary.xml");

		// on windows
		lev(config.disassebledAddress+"\\allFiles.xml_0_binary.xml");

	}


	public static void lev(String filename)throws Exception	
	{ 


		Configuration config=Configuration.loadFromFile();

		String outputFileAddress=config.reportAddress+"\\LevenshtienNotinNiCad"+ config.threshold+".xml";
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileAddress));
		bufferedWriter.write("<clones>");
		bufferedWriter.newLine();

		String outputFileAddress2=config.reportAddress+"\\LevenshtienAll"+ config.threshold+".xml";
		BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(outputFileAddress2));
		bufferedWriter2.write("<clones>");
		bufferedWriter2.newLine();
		
		String outputFileAddress3=config.reportAddress+"\\LevenshtienAndNiCad"+ config.threshold+".xml";
		BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter(outputFileAddress3));
		bufferedWriter3.write("<clones>");
		bufferedWriter3.newLine();
		
		String outputFileAddress4=config.reportAddress+"\\LevenshtienNotNicad_Binary"+ config.threshold+".xml";
		BufferedWriter bufferedWriter4 = new BufferedWriter(new FileWriter(outputFileAddress4));
		bufferedWriter4.write("<clones>");
		bufferedWriter4.newLine();
		
		String outputFileAddress5=config.reportAddress+"\\LevenshtienAndNiCad_binary"+ config.threshold+".xml";
		BufferedWriter bufferedWriter5 = new BufferedWriter(new FileWriter(outputFileAddress5));
		bufferedWriter5.write("<clones>");
		bufferedWriter5.newLine();
		

		parse(filename,filename);

		int nSameLanguage=0;
		int nCrossLanguage=0;

		System.out.println( vbcode.size());
		System.out.println( cscode.size());



		for(int v =0; v<vbcode.size()-1;v++){
			//System.out.print(v);
			for(int c =v+1; c<cscode.size(); c++){ //cscode.size();c++){

				if(vbcode.get(v).length()<2000 && cscode.get(c).length()<2000)
				{
					double max= (vbcode.get(v).length()>cscode.get(c).length())? vbcode.get(v).length():cscode.get(c).length();
					double d=(double)1-(getLevenshteinDistance(vbcode.get(v),cscode.get(c))/max);

					//out.write(i+"	"+d+"\n");




					if (d>=config.threshold){
		//				System.out.print(config.threshold + "  <  ");

		//				System.out.println(d+"------------");
		//				System.out.println(vbdata.get(v).get(0));
		//				System.out.println(csdata.get(c).get(0));

						// write to report  / all detected cloned using binarcode 					
						bufferedWriter2.write( "<clone_pair>");
						bufferedWriter2.newLine();
						//System.out.println(d );
						// first fragment
						bufferedWriter2.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
						bufferedWriter2.newLine();
						bufferedWriter2.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
						bufferedWriter2.newLine();
						bufferedWriter2.write("</clone_fragment>");
						bufferedWriter2.newLine();
						//second fragment
						bufferedWriter2.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
						bufferedWriter2.newLine();
						bufferedWriter2.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
						bufferedWriter2.newLine();
						bufferedWriter2.write("</clone_fragment>");
						bufferedWriter2.newLine();
						//close pair
						bufferedWriter2.write("</clone_pair>");
						bufferedWriter2.newLine();

						// check if this clone pairs  Did not detected in nicad	then save these clones in a seperate file
						if(!parseNiCadPairs( config, vbdata.get(v).get(0), vbdata.get(v).get(1), vbdata.get(v).get(2),csdata.get(c).get(0), csdata.get(c).get(1), csdata.get(c).get(2)  )){
							//System.out.println(parseNiCadPairs( config, vbdata.get(v).get(0), vbdata.get(v).get(1), vbdata.get(v).get(2),csdata.get(c).get(0), csdata.get(c).get(1), csdata.get(c).get(2)  ));
							bufferedWriter.write( "<clone_pair>");
							bufferedWriter.newLine();
							//System.out.println(d );
							// first fragment
							bufferedWriter.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							bufferedWriter.newLine();
							bufferedWriter.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
							bufferedWriter.newLine();
							bufferedWriter.write("</clone_fragment>");
							bufferedWriter.newLine();
							//second fragment
							bufferedWriter.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							bufferedWriter.newLine();
							bufferedWriter.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
							bufferedWriter.newLine();
							bufferedWriter.write("</clone_fragment>");
							bufferedWriter.newLine();
							//close pair
							bufferedWriter.write("</clone_pair>");
							bufferedWriter.newLine();
							
							// write the same report but include binary code not source code
							bufferedWriter4.write( "<clone_pair>");
							bufferedWriter4.newLine();
							//System.out.println(d );
							// first fragment
							bufferedWriter4.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							bufferedWriter4.newLine();
							bufferedWriter4.write("<![CDATA["+vbcode.get(v)+"]]>");
							bufferedWriter4.newLine();
							bufferedWriter4.write("</clone_fragment>");
							bufferedWriter4.newLine();
							//second fragment
							bufferedWriter4.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							bufferedWriter4.newLine();
							bufferedWriter4.write("<![CDATA["+cscode.get(c)+"]]>");
							bufferedWriter4.newLine();
							bufferedWriter4.write("</clone_fragment>");
							bufferedWriter4.newLine();
							//close pair
							bufferedWriter4.write("</clone_pair>");
							bufferedWriter4.newLine();
							
						}else{
							//System.out.println(parseNiCadPairs( config, vbdata.get(v).get(0), vbdata.get(v).get(1), vbdata.get(v).get(2),csdata.get(c).get(0), csdata.get(c).get(1), csdata.get(c).get(2)  ));

							bufferedWriter3.write( "<clone_pair>");
							bufferedWriter3.newLine();
							//System.out.println(d );
							// first fragment
							bufferedWriter3.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							bufferedWriter3.newLine();
							bufferedWriter3.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
							bufferedWriter3.newLine();
							bufferedWriter3.write("</clone_fragment>");
							bufferedWriter3.newLine();
							//second fragment
							bufferedWriter3.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							bufferedWriter3.newLine();
							bufferedWriter3.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
							bufferedWriter3.newLine();
							bufferedWriter3.write("</clone_fragment>");
							bufferedWriter3.newLine();
							//close pair
							bufferedWriter3.write("</clone_pair>");
							bufferedWriter3.newLine();
							//////////////////////////////
							// write the same report but include binary code not source code
							bufferedWriter5.write( "<clone_pair>");
							bufferedWriter5.newLine();
							//System.out.println(d );
							// first fragment
							bufferedWriter5.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							bufferedWriter5.newLine();
							bufferedWriter5.write("<![CDATA["+vbcode.get(v)+"]]>");
							bufferedWriter5.newLine();
							bufferedWriter5.write("</clone_fragment>");
							bufferedWriter5.newLine();
							//second fragment
							bufferedWriter5.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							bufferedWriter5.newLine();
							bufferedWriter5.write("<![CDATA["+cscode.get(c)+"]]>");
							bufferedWriter5.newLine();
							bufferedWriter5.write("</clone_fragment>");
							bufferedWriter5.newLine();
							//close pair
							bufferedWriter5.write("</clone_pair>");
							bufferedWriter5.newLine();
							
							
						}


					}

				}

			}	 
			if( v% ((int)(vbcode.size()/100))==0){
				int k=v /( (int)(vbcode.size()/100));
				System.out.println("so far "+k+"% Done");
			
			}
		}


		// close file		
		bufferedWriter.write("</clones>");
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();		

		bufferedWriter2.write("</clones>");
		bufferedWriter2.newLine();
		bufferedWriter2.flush();
		bufferedWriter2.close();
		
		bufferedWriter3.write("</clones>");
		bufferedWriter3.newLine();
		bufferedWriter3.flush();
		bufferedWriter3.close();
		
		bufferedWriter4.write("</clones>");
		bufferedWriter4.newLine();
		bufferedWriter4.flush();
		bufferedWriter4.close();
		
		bufferedWriter5.write("</clones>");
		bufferedWriter5.newLine();
		bufferedWriter5.flush();
		bufferedWriter5.close();
		
		

		System.out.println("Reports Generated look for files in folder : 5_Report");

	}

	// chick if passed clone pair is detected by NiCad
	public static boolean parseNiCadPairs( Configuration config, String fileA, String stA, String enA, String fileB, String stB, String enB) throws IOException{

		boolean found=false;
		int st=0;
		int en=0;
		String c="C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\";
		String rawFunctionsFileName="Monoo-2.10_functions-blind-clones-0.30.xml";
		File fileName = new File(rawFunctionsFileName);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(fileName);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();

				//	System.out.println(root.getNodeName()+"--------");

			NodeList nl = root.getElementsByTagName("cloneinfo");

					//	System.out.println(nl.getLength());
					//	System.out.println(nl.item(0).getAttributes().getNamedItem("npairs").getFirstChild().getNodeValue());


					//	nl = root.getElementsByTagName("class");  //in case of xml classes report
			nl = root.getElementsByTagName("clone");
		//	allc=nl.getLength();

			if(nl.getLength()>0){
		

				

				//for (int group=0;group<nl.getLength();group++)
				int group=0;
				
				while(!found && group<nl.getLength())
				{
					NodeList sourceList = nl.item(group).getChildNodes();

			//		System.out.println( sourceList.getLength()+"----------------------");

					//		for(int file=1;file<sourceList.getLength();file+=2){
						
				//		String file1= sourceList.item(file).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
				//		int startline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue() );
				//		int endline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue() );
					
						
						String file1= sourceList.item(1).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
						String startline1 = sourceList.item(1).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
						String endline1 = sourceList.item(1).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();

						
						String file22= sourceList.item(3).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
						String startline22 = sourceList.item(3).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
						String endline22 = sourceList.item(3).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
	

					
						file1=file1.replace(".ifdefed","");
						file1=file1.replace("/","\\");
						file1=file1.substring(42);
						file1=c.concat(file1);
						
						file22=file22.replace(".ifdefed","");
						file22=file22.replace("/","\\");
						file22=file22.substring(42);
						file22=c.concat(file22);
						
						
					
					//	System.out.println("----------------------------------------------------------------------------------------------------------------");
					//	System.out.println(file22+"  "+startline22+"  "+endline22);
					//	System.out.println(file1+"  "+startline1+"  "+endline1);
//	C:\Users\faa634\Desktop\mono\mono-2.10\mcs\mcs\class\IKVM.Reflection\Writer\ByteBuffer.cs  107  110


						if( ((fileA.equals(file1) && enA.equals(endline1)) &&(fileB.equals(file22) && enB.equals(endline22) )) ||
							((fileA.equals(file22) && enA.equals(endline22)) &&(fileB.equals(file1) && enB.equals(endline1) ))    )
						
						
						
						{
						//	System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
							//System.out.print(" clone pair found    ");
							
							//System.out.println(fileA+"  "+stA+"  "+enA);
							//System.out.println(fileB+"  "+stB+"  "+enB);
							//System.out.println("-------------------------------------------------------------------------------------------------");
							//System.out.println(file1+"  "+startline1+"  "+endline1);
						//	System.out.println(file22+"  "+startline22+"  "+endline22);
						
							found=true;
						}
						
						group++;


			//		}


				}

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}



	public static String getsource(Configuration config, String fileName, String startLine) throws IOException{

		String source=null;
		DocumentBuilderFactory dbfs = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder dbs = dbfs.newDocumentBuilder();

			Document docs = dbs.parse(config.disassebledAddress+"/allFiles.xml_0_source.xml");
			docs.getDocumentElement().normalize();


			Element roots = docs.getDocumentElement();



			NodeList nls = roots.getElementsByTagName("source_elements");

			//		System.out.println(nls.getLength());

			if(nls.getLength()>0){
				NodeList sourceLists = nls.item(0).getChildNodes();



				boolean found=false;
				int k=0;


				while(!found && k<sourceLists.getLength()){
					Node sources = sourceLists.item(k);
					k++;
					if (sources.getNodeType() != Node.ELEMENT_NODE) 
						continue;

					String files = sources.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					String startlines = sources.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
					//String endlines = sources.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
					String contents = sources.getFirstChild().getTextContent();

					if(fileName.equals(files) && startLine.equals(startlines))
					{
						found=true;
						source=contents;

					}

				}
			}



		}

		catch (Exception e) {
			e.printStackTrace();

		}
		return source;
	}


	public static void parse(String cs, String vb ) throws IOException{



		boolean error = false;
		File fileName = new File(cs);
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

				long start = System.currentTimeMillis();
				long items =0;
				for(int i =0; i < sourceList.getLength(); i++){
					Node source = sourceList.item(i);
					if (source.getNodeType() != Node.ELEMENT_NODE) 
						continue;

					String file = source.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					String startline = source.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
					String endline = source.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
					String content = source.getFirstChild().getTextContent();

					//	int loc = computeLoc(content);

					/*if(content.split("\n").length-1 != loc){
						System.out.println("gotcha!");
					}*/

					ArrayList<String> csd=new ArrayList<String>();
					csd.add(file);
					csd.add(startline);
					csd.add(endline);
					csdata.add(csd);


					String []csA = content.split("\n");
					String temp=null;

					ArrayList<String> csl=new ArrayList<String>();

					for(String t:csA){

						if(t.trim()==null || t.trim().isEmpty())
							continue;
						csl.add(t.trim());
						temp+=t.trim();
					}

					cscode.add(temp);

					/*
					items++;


					System.out.println(i+"================================ "+file);
					System.out.println(" start line = "+startline );
					System.out.println(csl.toString());
					 */
					//System.out.println("node size "+loc);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fileNamevb = new File(vb);


		DocumentBuilderFactory dbfvb = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder dbvb = dbfvb.newDocumentBuilder();
			Document docvb = dbvb.parse(fileNamevb);
			docvb.getDocumentElement().normalize();

			Element rootvb = docvb.getDocumentElement();

			NodeList nlvb = rootvb.getElementsByTagName("name");
			if(nlvb.getLength()>0){
				//projectName = nl.item(0).getFirstChild().getNodeValue();
			}else{
				error = true;
			}

			nlvb = rootvb.getElementsByTagName("description");
			if(nlvb.getLength()>0){
				//projectDesc = nl.item(0).getChildNodes().item(0).getNodeValue();
			}else{
				error = true;
			}

			nlvb = rootvb.getElementsByTagName("source_elements");
			if(nlvb.getLength()>0){
				NodeList sourceListvb = nlvb.item(0).getChildNodes();



				for(int j =0; j < sourceListvb.getLength(); j++){
					Node sourcevb = sourceListvb.item(j);
					if (sourcevb.getNodeType() != Node.ELEMENT_NODE) 
						continue;

					String filevb = sourcevb.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					String startlinevb = sourcevb.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
					String endlinevb = sourcevb.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
					String contentvb = sourcevb.getFirstChild().getTextContent();

					//int loc = computeLoc(contentvb);

					/*if(content.split("\n").length-1 != loc){
						System.out.println("gotcha!");
					}*/


					ArrayList<String> vbd=new ArrayList<String>();
					vbd.add(filevb);
					vbd.add(startlinevb);
					vbd.add(endlinevb);
					vbdata.add(vbd);



					String []vbA = contentvb.split("\n");

					ArrayList<String> vbl=new ArrayList<String>();
					String temp=null;

					for(String t:vbA){

						if(t.trim()==null || t.trim().isEmpty())
							continue;
						vbl.add(t.trim());
						temp+=t.trim();
					}

					vbcode.add(temp);


					//	double d= (double)LCSAlgorithm(vbl,csl).size()*2/(vbl.size()+csl.size());

					//	if (d>0.8)
					/*		{

						System.out.println(j+"================================ "+filevb);
						System.out.println(" start line = "+startlinevb );
						System.out.println(vbl.toString());
					}

					 */

					//System.out.println("node size "+loc);


				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}







	}

	private static int computeLoc(String content) {
		String []line = content.split("\n");
		int loc=0;
		for(String ln : line){
			if(ln.length() > 0)
				loc++;
		}

		return loc;
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

	private static void mkdir(String address)
	{
		String strManyDirectories=address;
		boolean success =  (new File(strManyDirectories)).mkdirs();


	}
}
