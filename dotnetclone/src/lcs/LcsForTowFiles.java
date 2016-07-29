package lcs;

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

public class LcsForTowFiles {

	private static ArrayList<ArrayList<String>> vbcode=new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> cscode=new ArrayList<ArrayList<String>>();

	private static ArrayList<ArrayList<String>> vbdata=new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> csdata=new ArrayList<ArrayList<String>>();

	private static final int NEITHER     = 0;
	private static final int UP          = 1;
	private static final int LEFT        = 2;
	private static final int UP_AND_LEFT = 3;

	/**
	 * @param args
	 * Read two xml files and measure similarity between their nodes
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Configuration config=Configuration.loadFromFile();

		File filesnames = new File("C:/Users/faa634/Documents/similarityunfiltered.txt");
		FileWriter out = new FileWriter(filesnames);

		int nine=0;
		int eighth=0;
		int seventh=0;
		int sixth=0;
		int fifth=0;
		int fourth=0;
		int other=0;


		//parse: parsing two xml files to build array lists vbcode and cscode
		parse(config.disassebledAddress+"\\cs.xml",config.disassebledAddress+"\\vb.xml");




		for(int v =0; v<200;v++){
			for(int c =0; c<200;c++){

				//System.out.print(v+"    ");				//		if(vbcode.get(v).size()<1000 && cscode.get(c).size()<1000)
				{

					double d=(double)LCSAlgorithm(vbcode.get(v),cscode.get(c)).size()*2/(vbcode.get(v).size()+cscode.get(c).size());
					//	if(Integer.parseInt(vbdata.get(v).get(2))-Integer.parseInt(vbdata.get(v).get(1) )> 20 && d>0.4 &&	(Integer.parseInt(csdata.get(c).get(2))-Integer.parseInt(csdata.get(c).get(1))>20 ))



					System.out.println("==========================================================================================================================================");

					System.out.println(d );
					// 				    out.write(i+"	"+d+"\n");

				}

			}	 
		}

		System.out.println("nine "+nine);
		System.out.println("eighth "+eighth);
		System.out.println("seventh "+seventh);
		System.out.println("sixth "+sixth);
		System.out.println("fifth "+fifth);
		System.out.println("fourth "+fourth);
		System.out.println("other "+other);
		//		 out.close();



	}

	public static void lcs (String filename)throws Exception	
	{ 

		int l10=0;
		int l9=0;
		int l8=0;
		int l7=0;
		int l6=0;
		int l5=0;
		int l4=0;
		int l3=0;
		int l2=0;
		int l1=0;
		int l0=0;

		int lc10=0;
		int lc9=0;
		int lc8=0;
		int lc7=0;
		int lc6=0;
		int lc5=0;
		int lc4=0;
		int lc3=0;
		int lc2=0;
		int lc1=0;
		int lc0=0;

		int lv10=0;
		int lv9=0;
		int lv8=0;
		int lv7=0;
		int lv6=0;
		int lv5=0;
		int lv4=0;
		int lv3=0;
		int lv2=0;
		int lv1=0;
		int lv0=0;

		/*Configuration config=Configuration.loadFromFile();
		String outputFileAddress=config.reportAddress+"\\LCSSameLanguageCloneReport"+ config.threshold+".xml";
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileAddress));
		bufferedWriter.write("<clones>");
		bufferedWriter.newLine();

		String outputFileAddress2=config.reportAddress+"\\LCSCrossLanguageCloneReport"+ config.threshold+".xml";
		BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(outputFileAddress2));
		bufferedWriter2.write("<clones>");
		bufferedWriter2.newLine();*/


		parse(filename,filename);

		int nSameLanguage=0;
		int nCrossLanguage=0;



		for(int v =0; v<vbcode.size()-1;v++){
			for(int c =v+1; c<cscode.size();c++){

			//	if(vbcode.get(v).size()<3500 && cscode.get(c).size()<3500)
				{
					double d=(double)LCSAlgorithm(vbcode.get(v),cscode.get(c)).size()*2/(vbcode.get(v).size()+cscode.get(c).size());
					//System.out.println(d );
					//out.write(i+"	"+d+"\n");

				if(vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".java") || vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".vb")){
						if (d==1)
							l10++;
						 if(d>=0.9)
							l9++;
						 if(d>=0.8)
							l8++;
						 if (d>=0.7)
							l7++;
						 if(d>=0.6)
							l6++;
						 if(d>=0.5)
							l5++;
						 if(d>=0.4)
							l4++;
						 if(d>=0.3)
							l3++;
						 if(d>=0.2)
							l2++;
						 if(d>=0.1)
							l1++;
						 if(d>=0.0)
							l0++;

					}
					else{
						if(vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".cs") ){

							if (d==1)
								lc10++;
							 if(d>=0.9)
								lc9++;
							 if(d>=0.8)
								lc8++;
							if (d>=0.7)
								lc7++;
							 if(d>=0.6)
								lc6++;
							 if(d>=0.5)
								lc5++;
							 if(d>=0.4)
								lc4++;
							 if(d>=0.3)
								lc3++;
							 if(d>=0.2)
								lc2++;
							 if(d>=0.1)
								lc1++;
							 if(d>=0.0)
								lc0++;
						}else
						{
							if (d==1)
								lv10++;
							 if(d>=0.9)
								lv9++;
							 if(d>=0.8)
								lv8++;
							if (d>=0.7)
								lv7++;
							 if(d>=0.6)
								lv6++;
							 if(d>=0.5)
								lv5++;
							 if(d>=0.4)
								lv4++;
							 if(d>=0.3)
								lv3++;
							 if(d>=0.2)
								lv2++;
							 if(d>=0.1)
								lv1++;
							 if(d>=0.0)
								lv0++;

						}
					}

				/*	if (d>=config.threshold){
						// write to report
						//check if cross-language clones
						if(vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".java") || vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".cs")){

							//write to a separate file for cross language clone
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
							nCrossLanguage++;

						}else
						{
							// same language clones
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
							nSameLanguage++;
						}
					}*/

				}

			}	 
			
			if( v% ((int)(vbcode.size()/100))==0){
				int k=v /( (int)(vbcode.size()/100));
				System.out.println("so far "+k+"% Done");}
		}



		// close file		
		/*bufferedWriter.write("</clones>");
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();		

		bufferedWriter2.write("</clones>");
		bufferedWriter2.newLine();
		bufferedWriter2.flush();
		bufferedWriter2.close();	

		System.out.print("Report Generated look for files:  LCSSameLanguageCloneReport.xml");
		System.out.println("and   LCSCrossLanguageCloneReport.xml");
		System.out.println("The number of cross-language clone pairs are: "+nCrossLanguage );
		System.out.println("The number of same-language clone pairs are : "+nSameLanguage);*/


				System.out.println("####################################################################################");
		System.out.println("Cross Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+l10);
		System.out.println("0.9\t"+l9);
		System.out.println("0.8\t"+l8);
		System.out.println("0.7\t"+l7);
		System.out.println("0.6\t"+l6);
		System.out.println("0.5\t"+l5);
		System.out.println("0.4\t"+l4);
		System.out.println("0.3\t"+l3);
		System.out.println("0.2\t"+l2);
		System.out.println("0.1\t"+l1);
		System.out.println("0.0\t"+l0);

		System.out.println("####################################################################################");
		System.out.println("CS  Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+lc10);
		System.out.println("0.9\t"+lc9);
		System.out.println("0.8\t"+lc8);
		System.out.println("0.7\t"+lc7);
		System.out.println("0.6\t"+lc6);
		System.out.println("0.5\t"+lc5);
		System.out.println("0.4\t"+lc4);
		System.out.println("0.3\t"+lc3);
		System.out.println("0.2\t"+lc2);
		System.out.println("0.1\t"+lc1);
		System.out.println("0.0\t"+lc0);

		System.out.println("####################################################################################");
		System.out.println("VB Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+lv10);
		System.out.println("0.9\t"+lv9);
		System.out.println("0.8\t"+lv8);
		System.out.println("0.7\t"+lv7);
		System.out.println("0.6\t"+lv6);
		System.out.println("0.5\t"+lv5);
		System.out.println("0.4\t"+lv4);
		System.out.println("0.3\t"+lv3);
		System.out.println("0.2\t"+lv2);
		System.out.println("0.1\t"+lv1);
		System.out.println("0.0\t"+lv0);

		 
	}

	public static void lcsPairs (String filename)throws Exception	
	{ 

		int l10=0;
		int l9=0;
		int l8=0;
		int l7=0;
		int l6=0;
		int l5=0;
		int l4=0;
		int l3=0;
		int l2=0;
		int l1=0;
		int l0=0;

		int lc10=0;
		int lc9=0;
		int lc8=0;
		int lc7=0;
		int lc6=0;
		int lc5=0;
		int lc4=0;
		int lc3=0;
		int lc2=0;
		int lc1=0;
		int lc0=0;

		int lv10=0;
		int lv9=0;
		int lv8=0;
		int lv7=0;
		int lv6=0;
		int lv5=0;
		int lv4=0;
		int lv3=0;
		int lv2=0;
		int lv1=0;
		int lv0=0;

		int lj10=0;
		int lj9=0;
		int lj8=0;
		int lj7=0;
		int lj6=0;
		int lj5=0;
		int lj4=0;
		int lj3=0;
		int lj2=0;
		int lj1=0;
		int lj0=0;

		parse(filename,filename);



		for(int v =0; v<vbcode.size()-1;v++){
			for(int c =v+1; c<cscode.size();c++){

				if(vbcode.get(v).size()<2000 && cscode.get(c).size()<2000)
				{
					double d=(double)LCSAlgorithm(vbcode.get(v),cscode.get(c)).size()*2/(vbcode.get(v).size()+cscode.get(c).size());
					//System.out.println(d );
					//out.write(i+"	"+d+"\n");

					if( vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".vb") 
							|| vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".java")
							|| vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".cs")
							|| vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".java")
							|| vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".cs")
							|| vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".vb")) {

						if (d==1)
							l10++;
						if(d>=0.9)
							l9++;
						if(d>=0.8)
							l8++;
						if (d>=0.7)
							l7++;
						if(d>=0.6)
							l6++;
						if(d>=0.5)
							l5++;
						if(d>=0.4)
							l4++;
						if(d>=0.3)
							l3++;
						if(d>=0.2)
							l2++;
						if(d>=0.1)
							l1++;
						if(d>=0.0)
							l0++;

					}
					else{ if(vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".cs") ){

						if (d==1)
							lc10++;
						if(d>=0.9)
							lc9++;
						if(d>=0.8)
							lc8++;
						if (d>=0.7)
							lc7++;
						if(d>=0.6)
							lc6++;
						if(d>=0.5)
							lc5++;
						if(d>=0.4)
							lc4++;
						if(d>=0.3)
							lc3++;
						if(d>=0.2)
							lc2++;
						if(d>=0.1)
							lc1++;
						if(d>=0.0)
							lc0++;
					}else if(vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".vb") )
					{
						if (d==1)
							lv10++;
						if(d>=0.9)
							lv9++;
						if(d>=0.8)
							lv8++;
						if (d>=0.7)
							lv7++;
						if(d>=0.6)
							lv6++;
						if(d>=0.5)
							lv5++;
						if(d>=0.4)
							lv4++;
						if(d>=0.3)
							lv3++;
						if(d>=0.2)
							lv2++;
						if(d>=0.1)
							lv1++;
						if(d>=0.0)
							lv0++;

					}else
					{

						if (d==1)
							lj10++;
						if(d>=0.9)
							lj9++;
						if(d>=0.8)
							lj8++;
						if (d>=0.7)
							lj7++;
						if(d>=0.6)
							lj6++;
						if(d>=0.5)
							lj5++;
						if(d>=0.4)
							lj4++;
						if(d>=0.3)
							lj3++;
						if(d>=0.2)
							lj2++;
						if(d>=0.1)
							lj1++;
						if(d>=0.0)
							lj0++;

					}
					}
				}

			}	 
			
			if( v% ((int)(vbcode.size()/100))==0){
				int k=v /( (int)(vbcode.size()/100));
				System.out.println("so far "+k+"% Done");
			
			}
		}




		System.out.println("Report Generated look for file:  CloneReport.txt");

		System.out.println("####################################################################################");
		System.out.println("Cross Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+l10);
		System.out.println("0.9\t"+l9);
		System.out.println("0.8\t"+l8);
		System.out.println("0.7\t"+l7);
		System.out.println("0.6\t"+l6);
		System.out.println("0.5\t"+l5);
		System.out.println("0.4\t"+l4);
		System.out.println("0.3\t"+l3);
		System.out.println("0.2\t"+l2);
		System.out.println("0.1\t"+l1);
		System.out.println("0.0\t"+l0);

		System.out.println("####################################################################################");
		System.out.println("CS  Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+lc10);
		System.out.println("0.9\t"+lc9);
		System.out.println("0.8\t"+lc8);
		System.out.println("0.7\t"+lc7);
		System.out.println("0.6\t"+lc6);
		System.out.println("0.5\t"+lc5);
		System.out.println("0.4\t"+lc4);
		System.out.println("0.3\t"+lc3);
		System.out.println("0.2\t"+lc2);
		System.out.println("0.1\t"+lc1);
		System.out.println("0.0\t"+lc0);

		System.out.println("####################################################################################");
		System.out.println("VB Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+lv10);
		System.out.println("0.9\t"+lv9);
		System.out.println("0.8\t"+lv8);
		System.out.println("0.7\t"+lv7);
		System.out.println("0.6\t"+lv6);
		System.out.println("0.5\t"+lv5);
		System.out.println("0.4\t"+lv4);
		System.out.println("0.3\t"+lv3);
		System.out.println("0.2\t"+lv2);
		System.out.println("0.1\t"+lv1);
		System.out.println("0.0\t"+lv0);

		System.out.println("####################################################################################");
		System.out.println("Java Language Clones");
		System.out.println("Sim\t"+"Number of Clone Pairs");
		System.out.println("1.0\t"+lj10);
		System.out.println("0.9\t"+lj9);
		System.out.println("0.8\t"+lj8);
		System.out.println("0.7\t"+lj7);
		System.out.println("0.6\t"+lj6);
		System.out.println("0.5\t"+lj5);
		System.out.println("0.4\t"+lj4);
		System.out.println("0.3\t"+lj3);
		System.out.println("0.2\t"+lj2);
		System.out.println("0.1\t"+lj1);
		System.out.println("0.0\t"+lj0);

		String outputFileAddress=System.getProperty( "user.dir" )+"/CloneReport.txt";
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileAddress));

		bufferedWriter.write("####################################################################################"+"\n");
		bufferedWriter.write("Cross Language Clones"+"\n");
		bufferedWriter.write("Sim\t"+"Number of Clone Pairs"+"\n");
		bufferedWriter.write("1.0\t"+l10+"\n");
		bufferedWriter.write("0.9\t"+l9+"\n");
		bufferedWriter.write("0.8\t"+l8+"\n");
		bufferedWriter.write("0.7\t"+l7+"\n");
		bufferedWriter.write("0.6\t"+l6+"\n");
		bufferedWriter.write("0.5\t"+l5+"\n");
		bufferedWriter.write("0.4\t"+l4+"\n");
		bufferedWriter.write("0.3\t"+l3+"\n");
		bufferedWriter.write("0.2\t"+l2+"\n");
		bufferedWriter.write("0.1\t"+l1+"\n");
		bufferedWriter.write("0.0\t"+l0+"\n");

		bufferedWriter.write("####################################################################################"+"\n");
		bufferedWriter.write("CS  Language Clones"+"\n");
		bufferedWriter.write("Sim\t"+"Number of Clone Pairs"+"\n");
		bufferedWriter.write("1.0\t"+lc10+"\n");
		bufferedWriter.write("0.9\t"+lc9+"\n");
		bufferedWriter.write("0.8\t"+lc8+"\n");
		bufferedWriter.write("0.7\t"+lc7+"\n");
		bufferedWriter.write("0.6\t"+lc6+"\n");
		bufferedWriter.write("0.5\t"+lc5+"\n");
		bufferedWriter.write("0.4\t"+lc4+"\n");
		bufferedWriter.write("0.3\t"+lc3+"\n");
		bufferedWriter.write("0.2\t"+lc2+"\n");
		bufferedWriter.write("0.1\t"+lc1+"\n");
		bufferedWriter.write("0.0\t"+lc0+"\n");

		bufferedWriter.write("####################################################################################"+"\n");
		bufferedWriter.write("VB Language Clones"+"\n");
		bufferedWriter.write("Sim\t"+"Number of Clone Pairs"+"\n");
		bufferedWriter.write("1.0\t"+lv10+"\n");
		bufferedWriter.write("0.9\t"+lv9+"\n");
		bufferedWriter.write("0.8\t"+lv8+"\n");
		bufferedWriter.write("0.7\t"+lv7+"\n");
		bufferedWriter.write("0.6\t"+lv6+"\n");
		bufferedWriter.write("0.5\t"+lv5+"\n");
		bufferedWriter.write("0.4\t"+lv4+"\n");
		bufferedWriter.write("0.3\t"+lv3+"\n");
		bufferedWriter.write("0.2\t"+lv2+"\n");
		bufferedWriter.write("0.1\t"+lv1+"\n");
		bufferedWriter.write("0.0\t"+lv0+"\n");

		bufferedWriter.write("####################################################################################"+"\n");
		bufferedWriter.write("Java Language Clones"+"\n");
		bufferedWriter.write("Sim\t"+"Number of Clone Pairs"+"\n");
		bufferedWriter.write("1.0\t"+lj10+"\n");
		bufferedWriter.write("0.9\t"+lj9+"\n");
		bufferedWriter.write("0.8\t"+lj8+"\n");
		bufferedWriter.write("0.7\t"+lj7+"\n");
		bufferedWriter.write("0.6\t"+lj6+"\n");
		bufferedWriter.write("0.5\t"+lj5+"\n");
		bufferedWriter.write("0.4\t"+lj4+"\n");
		bufferedWriter.write("0.3\t"+lj3+"\n");
		bufferedWriter.write("0.2\t"+lj2+"\n");
		bufferedWriter.write("0.1\t"+lj1+"\n");
		bufferedWriter.write("0.0\t"+lj0+"\n");


		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();

	}

	
	// measure similarityies between every pairs and generate a report for each cross and same language clones
	public static void lcsReport (String filename)throws Exception	
	{ 

		String userDir = System.getProperty( "user.dir" );
		mkdir(userDir+"/Reports");
		String ReportPath=userDir+"/Reports";

		Configuration config=Configuration.loadFromFile();

		String csoutputFileAddress=ReportPath+"/levenshtienCSCloneReport"+ config.threshold+".xml";
		BufferedWriter csWriter = new BufferedWriter(new FileWriter(csoutputFileAddress));
		csWriter.write("<clones>");
		csWriter.newLine();

		String vboutputFileAddress=ReportPath+"/levenshtienVBCloneReport"+ config.threshold+".xml";
		BufferedWriter vbWriter = new BufferedWriter(new FileWriter(vboutputFileAddress));
		vbWriter.write("<clones>");
		vbWriter.newLine();

		String javaoutputFileAddress=ReportPath+"/levenshtienJavaCloneReport"+ config.threshold+".xml";
		BufferedWriter javaWriter = new BufferedWriter(new FileWriter(javaoutputFileAddress));
		javaWriter.write("<clones>");
		javaWriter.newLine();

		String outputFileAddress2=ReportPath+"/levenshtienCrossLanguageCloneReport"+ config.threshold+".xml";
		BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(outputFileAddress2));
		bufferedWriter2.write("<clones>");
		bufferedWriter2.newLine();

		parse(filename,filename);


		int nCSPairs=0;
		int nVBPairs=0;
		int nJavaPairs=0;
		int nCrossLanguage=0;



		for(int v =0; v<vbcode.size()-1;v++){
			for(int c =v+1; c<cscode.size();c++){

				if(vbcode.get(v).size()<1000 && cscode.get(c).size()<1000)
				{
					double d=(double)LCSAlgorithm(vbcode.get(v),cscode.get(c)).size()*2/(vbcode.get(v).size()+cscode.get(c).size());
					//System.out.println(d );
					//out.write(i+"	"+d+"\n");

					if (d>=config.threshold){
						// write to report
						//check if cross-language clones
						if( vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".vb") 
								|| vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".java")
								|| vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".cs")
								|| vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".java")
								|| vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".cs")
								|| vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".vb")){

							//write to a separate file for cross language clone
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
							nCrossLanguage++;

						}else if(vbdata.get(v).get(0).endsWith(".cs")&&csdata.get(c).get(0).endsWith(".cs"))
						{
							csWriter.write( "<clone_pair>");
							csWriter.newLine();
							//System.out.println(d );
							// first fragment
							csWriter.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							csWriter.newLine();
							csWriter.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
							csWriter.newLine();
							csWriter.write("</clone_fragment>");
							csWriter.newLine();
							//second fragment
							csWriter.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							csWriter.newLine();
							csWriter.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
							csWriter.newLine();
							csWriter.write("</clone_fragment>");
							csWriter.newLine();
							//close pair
							csWriter.write("</clone_pair>");
							csWriter.newLine();
							nCSPairs++;
						}else if(vbdata.get(v).get(0).endsWith(".vb")&&csdata.get(c).get(0).endsWith(".vb"))
						{
							vbWriter.write( "<clone_pair>");
							vbWriter.newLine();
							//System.out.println(d );
							// first fragment
							vbWriter.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							vbWriter.newLine();
							vbWriter.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
							vbWriter.newLine();
							vbWriter.write("</clone_fragment>");
							vbWriter.newLine();
							//second fragment
							vbWriter.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							vbWriter.newLine();
							vbWriter.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
							vbWriter.newLine();
							vbWriter.write("</clone_fragment>");
							vbWriter.newLine();
							//close pair
							vbWriter.write("</clone_pair>");
							vbWriter.newLine();
							nVBPairs++;
						}else if(vbdata.get(v).get(0).endsWith(".java")&&csdata.get(c).get(0).endsWith(".java"))
						{
							javaWriter.write( "<clone_pair>");
							javaWriter.newLine();
							//System.out.println(d );
							// first fragment
							javaWriter.write( "<clone_fragment file=\""+vbdata.get(v).get(0)+"\" startline=\""+ vbdata.get(v).get(1) +"\" endline=\""+ vbdata.get(v).get(2)+"\">");
							javaWriter.newLine();
							javaWriter.write("<![CDATA["+ getsource( config, vbdata.get(v).get(0), vbdata.get(v).get(1))+"]]>");
							javaWriter.newLine();
							javaWriter.write("</clone_fragment>");
							javaWriter.newLine();
							//second fragment
							javaWriter.write( "<clone_fragment file=\""+csdata.get(c).get(0)+"\" startline=\""+ csdata.get(c).get(1) +"\" endline=\""+ csdata.get(c).get(2)+"\">");
							javaWriter.newLine();
							javaWriter.write("<![CDATA["+getsource( config, csdata.get(c).get(0), csdata.get(c).get(1))+"]]>");
							javaWriter.newLine();
							javaWriter.write("</clone_fragment>");
							javaWriter.newLine();
							//close pair
							javaWriter.write("</clone_pair>");
							javaWriter.newLine();
							nJavaPairs++;
						}
					}

				}

			}	
			
			if( v% ((int)(vbcode.size()/100))==0){
				int k=v /( (int)(vbcode.size()/100));
				System.out.println("so far "+k+"% Done");
			
			}

		}

		bufferedWriter2.write("</clones>");
		bufferedWriter2.newLine();
		bufferedWriter2.flush();
		bufferedWriter2.close();

		// close file		
		csWriter.write("</clones>");
		csWriter.newLine();
		csWriter.flush();
		csWriter.close();	

		// close file		
		vbWriter.write("</clones>");
		vbWriter.newLine();
		vbWriter.flush();
		vbWriter.close();

		// close file		
		javaWriter.write("</clones>");
		javaWriter.newLine();
		javaWriter.flush();
		javaWriter.close();

		System.out.println("Reports Generated look for files in Repots folder");

		System.out.println("The number of cross-language clone pairs are: "+nCrossLanguage );
		System.out.println("The number of CSharp clone pairs are : "+nCSPairs);
		System.out.println("The number of VB clone pairs are : "+nVBPairs);
		System.out.println("The number of J# clone pairs are : "+nJavaPairs);


	}


	
	// return the source code for a method
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
					String endlines = sources.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
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

					ArrayList<String> csl=new ArrayList<String>();

					for(String t:csA){

						if(t.trim()==null || t.trim().isEmpty())
							continue;
						csl.add(t.trim());
					}

					cscode.add(csl);



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

					for(String t:vbA){

						if(t.trim()==null || t.trim().isEmpty())
							continue;
						vbl.add(t.trim());
					}

					vbcode.add(vbl);


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


	private static void mkdir(String address)
	{
		String strManyDirectories=address;
		boolean success =  (new File(strManyDirectories)).mkdirs();


	}
}
