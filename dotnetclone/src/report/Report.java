package report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.Configuration;
import simhash.SimhashCloneTest6e;


/*
 * this file reads the output report that generated from SimCad and generate our report
 */
public class Report {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Configuration config=Configuration.loadFromFile();

		parseSimCad(config, config.disassebledAddress+"\\allFiles.xml_0-secco_clones-multi_index-"+SimhashCloneTest6e.simThreshold+".xml");



		//        File filesnames = new File("C:/Users/faa634/Documents/similarityunfiltered.txt");
		//       FileWriter out = new FileWriter(filesnames);






	}


	public static void parseSimCad(Configuration config, String rawFunctionsFileName) throws IOException{


		String outputFileAddress=config.reportAddress+"\\SimCadSameLanguageClone"+SimhashCloneTest6e.simThreshold+".xml";
		String outputFileAddress2=config.reportAddress+"\\SimCadCrossLanguageClone"+SimhashCloneTest6e.simThreshold+".xml";


		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileAddress));
		BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(outputFileAddress2));

		File fileName = new File(rawFunctionsFileName);

		int nCrossLanguage=0;
		int nVb=0;
		int nCs=0;

		DocumentBuilderFactory dbfs = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			DocumentBuilder dbs = dbfs.newDocumentBuilder();

			Document docs = dbs.parse(config.disassebledAddress+"\\allFiles.xml_0_source.xml");
			docs.getDocumentElement().normalize();
			Document doc = db.parse(fileName);
			doc.getDocumentElement().normalize();

			Element roots = docs.getDocumentElement();
			Element root = doc.getDocumentElement();


			bufferedWriter.write("<clones  >");
			bufferedWriter.newLine();

			bufferedWriter2.write("<clones  >");
			bufferedWriter2.newLine();

		//	System.out.println("number of fragments= "+root.getAttribute("nfragments")+"   ngroups= "+root.getAttribute("ngroups"));
		//	System.out.println("Csharpe        "+" vb\t"+"Jsharpe\t "+ "CPP\t"+" Fsharpe");

			// start file

			/*	NodeList nl = root.getElementsByTagName("name");
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
			}*/

			NodeList nls = roots.getElementsByTagName("source_elements");
			if(nls.getLength()>0){
				NodeList sourceLists = nls.item(0).getChildNodes();
				NodeList nl = root.getElementsByTagName("clone_group");

				if(nl.getLength()>0){

					for (int group=0;group<nl.getLength();group++)
					{

						Node groupNode=nl.item(group);


						String groupid = groupNode.getAttributes().getNamedItem("groupid").getFirstChild().getNodeValue();
						String nfragments = groupNode.getAttributes().getNamedItem("nfragments").getFirstChild().getNodeValue();



						NodeList sourceList = nl.item(group).getChildNodes();


						long items =0;

						int cs=0;
						int vb=0;
						int cpp=0;
						int fs=0;
						int js=0;

						ArrayList<ArrayList<String>> sourceCodeData=new ArrayList<ArrayList<String>>();


						for(int i =0; i < sourceList.getLength(); i++){

							Node source = sourceList.item(i);
							if (source.getNodeType() != Node.ELEMENT_NODE) 
								continue;

							String file = source.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
							String startline = source.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
							String endline = source.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
							String content = source.getFirstChild().getTextContent();
							String g_id = source.getAttributes().getNamedItem("pcid").getFirstChild().getNodeValue();

							if( file.endsWith(".cs")) cs++;
							if( file.endsWith(".vb")) vb++;
							if( file.endsWith(".cpp")) cpp++;
							if( file.endsWith(".fs")) fs++;
							if( file.endsWith(".java")) js++;

							boolean found=false;
							int k=0;

							ArrayList<String> current=new ArrayList<String>();

							while(!found && k<sourceLists.getLength()){
								Node sources = sourceLists.item(k);
								k++;
								if (sources.getNodeType() != Node.ELEMENT_NODE) 
									continue;

								String files = sources.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
								String startlines = sources.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
								String endlines = sources.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
								String contents = sources.getFirstChild().getTextContent();
								if(file.equals(files) && startline.equals(startlines))
								{
									found=true;

									current.add(files);
									current.add(startlines);
									current.add(endlines);
									current.add(contents);

									// to show the size of byte code vs. source code
									//String []bytecode = content.split("\n");
									//String []sourcecode = contents.split("\n");

									//System.out.println(bytecode.length+"	"+sourcecode.length);
								}
							}


							sourceCodeData.add(current);
							items++;




						}
						// write group	


						if(sourceCodeData.get(0).get(0).endsWith(".cs")&&sourceCodeData.get(1).get(0).endsWith(".vb") || sourceCodeData.get(0).get(0).endsWith(".vb")&&sourceCodeData.get(1).get(0).endsWith(".cs")){


							bufferedWriter2.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							bufferedWriter2.newLine();
							//	System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								bufferedWriter2.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								bufferedWriter2.newLine();
								bufferedWriter2.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								bufferedWriter2.newLine();
								bufferedWriter2.write("</clone_fragment>");
								bufferedWriter2.newLine();


							}

							nCrossLanguage++;
							bufferedWriter2.write("</clone_pair>");
							bufferedWriter2.newLine();
						} else
						{
							bufferedWriter.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							bufferedWriter.newLine();
							//		System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								bufferedWriter.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								bufferedWriter.newLine();
								bufferedWriter.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								bufferedWriter.newLine();
								bufferedWriter.write("</clone_fragment>");
								bufferedWriter.newLine();

							}


							if(sourceCodeData.get(0).get(0).endsWith(".cs")&&sourceCodeData.get(1).get(0).endsWith(".cs"))
								nCs++;
							else
								nVb++;


							//	nSameLanguage++;
							bufferedWriter.write("</clone_pair>");
							bufferedWriter.newLine();


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

	/*				System.out.println("The number of cross-language clone pairs are: "+nCrossLanguage );
					System.out.println("The number of same-language Vb-clone pairs are : "+nVb);
					System.out.println("The number of same-language CS-clone pairs are : "+nCs);


					System.out.println("Report genarated look for the file: SimCadCloneReport.xml");*/

					System.out.println(nCrossLanguage+"\t"+nCs+"\t"+nVb);

				}
			}



		}

		catch (Exception e) {
			e.printStackTrace();
		}



	}

	public static void parseSimCad3Languages(Configuration config, String rawFunctionsFileName) throws IOException{


		String outputFileAddress=config.reportAddress+"\\SimCadSameLanguageClone"+SimhashCloneTest6e.simThreshold+".xml";
		String outputFileAddress2=config.reportAddress+"\\SimCadCSVBClone"+SimhashCloneTest6e.simThreshold+".xml";
		String outputFileAddress3=config.reportAddress+"\\SimCadCSJSClone"+SimhashCloneTest6e.simThreshold+".xml";
		String outputFileAddress4=config.reportAddress+"\\SimCadVBJSClone"+SimhashCloneTest6e.simThreshold+".xml";



		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileAddress));
		BufferedWriter CSVBWriter = new BufferedWriter(new FileWriter(outputFileAddress2));
		BufferedWriter CSJSWriter = new BufferedWriter(new FileWriter(outputFileAddress3));
		BufferedWriter VBJSWriter = new BufferedWriter(new FileWriter(outputFileAddress4));

		File fileName = new File(rawFunctionsFileName);

		int nSameLanguage=0;
		int CSVB=0;
		int CSJS=0;
		int VBJS=0;

		DocumentBuilderFactory dbfs = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			DocumentBuilder dbs = dbfs.newDocumentBuilder();

			Document docs = dbs.parse(config.disassebledAddress+"\\allFiles.xml_0_source.xml");
			docs.getDocumentElement().normalize();
			Document doc = db.parse(fileName);
			doc.getDocumentElement().normalize();

			Element roots = docs.getDocumentElement();
			Element root = doc.getDocumentElement();


			bufferedWriter.write("<clones  >");
			bufferedWriter.newLine();

			CSVBWriter.write("<clones  >");
			CSVBWriter.newLine();

			CSJSWriter.write("<clones  >");
			CSJSWriter.newLine();

			VBJSWriter.write("<clones  >");
			VBJSWriter.newLine();






			/*			System.out.println("number of fragments= "+root.getAttribute("nfragments")+"   ngroups= "+root.getAttribute("ngroups"));
			System.out.println("Csharpe        "+" vb\t"+"Jsharpe\t "+ "CPP\t"+" Fsharpe");*/

			// start file

			/*	NodeList nl = root.getElementsByTagName("name");
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
			}*/

			NodeList nls = roots.getElementsByTagName("source_elements");
			if(nls.getLength()>0){
				NodeList sourceLists = nls.item(0).getChildNodes();
				NodeList nl = root.getElementsByTagName("clone_group");

				if(nl.getLength()>0){

					for (int group=0;group<nl.getLength();group++)
					{

						Node groupNode=nl.item(group);


						String groupid = groupNode.getAttributes().getNamedItem("groupid").getFirstChild().getNodeValue();
						String nfragments = groupNode.getAttributes().getNamedItem("nfragments").getFirstChild().getNodeValue();



						NodeList sourceList = nl.item(group).getChildNodes();


						long items =0;

						int cs=0;
						int vb=0;
						int cpp=0;
						int fs=0;
						int js=0;

						ArrayList<ArrayList<String>> sourceCodeData=new ArrayList<ArrayList<String>>();


						for(int i =0; i < sourceList.getLength(); i++){

							Node source = sourceList.item(i);
							if (source.getNodeType() != Node.ELEMENT_NODE) 
								continue;

							String file = source.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
							String startline = source.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
							String endline = source.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
							String content = source.getFirstChild().getTextContent();
							String g_id = source.getAttributes().getNamedItem("pcid").getFirstChild().getNodeValue();

							if( file.endsWith(".cs")) cs++;
							if( file.endsWith(".vb")) vb++;
							if( file.endsWith(".cpp")) cpp++;
							if( file.endsWith(".fs")) fs++;
							if( file.endsWith(".java")) js++;

							boolean found=false;
							int k=0;

							ArrayList<String> current=new ArrayList<String>();

							while(!found && k<sourceLists.getLength()){
								Node sources = sourceLists.item(k);
								k++;
								if (sources.getNodeType() != Node.ELEMENT_NODE) 
									continue;

								String files = sources.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
								String startlines = sources.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
								String endlines = sources.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
								String contents = sources.getFirstChild().getTextContent();
								if(file.equals(files) && startline.equals(startlines))
								{
									found=true;

									current.add(files);
									current.add(startlines);
									current.add(endlines);
									current.add(contents);

									// to show the size of byte code vs. source code
									//String []bytecode = content.split("\n");
									//String []sourcecode = contents.split("\n");

									//System.out.println(bytecode.length+"	"+sourcecode.length);
								}
							}


							sourceCodeData.add(current);
							items++;




						}
						// write group	


						if(sourceCodeData.get(0).get(0).endsWith(".cs")&&sourceCodeData.get(1).get(0).endsWith(".vb") || sourceCodeData.get(0).get(0).endsWith(".vb")&&sourceCodeData.get(1).get(0).endsWith(".cs")){


							CSVBWriter.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							CSVBWriter.newLine();
							//	System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								CSVBWriter.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								CSVBWriter.newLine();
								CSVBWriter.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								CSVBWriter.newLine();
								CSVBWriter.write("</clone_fragment>");
								CSVBWriter.newLine();


							}

							CSVB++;
							CSVBWriter.write("</clone_pair>");
							CSVBWriter.newLine();
						} 



						if(sourceCodeData.get(0).get(0).endsWith(".cs")&&sourceCodeData.get(1).get(0).endsWith(".java") || sourceCodeData.get(0).get(0).endsWith(".java")&&sourceCodeData.get(1).get(0).endsWith(".cs")){


							CSJSWriter.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							CSJSWriter.newLine();
							//	System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								CSJSWriter.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								CSJSWriter.newLine();
								CSJSWriter.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								CSJSWriter.newLine();
								CSJSWriter.write("</clone_fragment>");
								CSJSWriter.newLine();


							}

							CSJS++;
							CSJSWriter.write("</clone_pair>");
							CSJSWriter.newLine();
						} 

		
						if(sourceCodeData.get(0).get(0).endsWith(".vb")&&sourceCodeData.get(1).get(0).endsWith(".java") || sourceCodeData.get(0).get(0).endsWith(".java")&&sourceCodeData.get(1).get(0).endsWith(".vb")){


							VBJSWriter.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							VBJSWriter.newLine();
							//	System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								VBJSWriter.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								VBJSWriter.newLine();
								VBJSWriter.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								VBJSWriter.newLine();
								VBJSWriter.write("</clone_fragment>");
								VBJSWriter.newLine();


							}

							VBJS++;
							VBJSWriter.write("</clone_pair>");
							VBJSWriter.newLine();
						} 

						
						if(sourceCodeData.get(0).get(0).endsWith(".vb")&&sourceCodeData.get(1).get(0).endsWith(".vb") ||
								sourceCodeData.get(0).get(0).endsWith(".cs")&&sourceCodeData.get(1).get(0).endsWith(".cs") ||
								sourceCodeData.get(0).get(0).endsWith(".java")&&sourceCodeData.get(1).get(0).endsWith(".java")){


							bufferedWriter.write( "<clone_pair groupid=\""+groupid+"\" nfragments=\""+ nfragments +"\" Csharpe_files=\""+ cs+"\" vb_files=\""+ vb+"\" Jsharpe_files=\""+ js+"\" CPP_files=\""+ cpp+"\" Fsharpe_files=\""+ fs+"\">");
							bufferedWriter.newLine();
							//	System.out.println( cs+" \t\t"+ vb+" \t "+ js+" \t\t"+ cpp+" \t "+ fs);

							for(int z=0;z<sourceCodeData.size();z++)
							{

								bufferedWriter.write( "<clone_fragment file=\""+sourceCodeData.get(z).get(0)+"\" startline=\""+ sourceCodeData.get(z).get(1) +"\" endline=\""+ sourceCodeData.get(z).get(2)+"\">");
								bufferedWriter.newLine();
								bufferedWriter.write("<![CDATA["+sourceCodeData.get(z).get(3)+"]]>");
								bufferedWriter.newLine();
								bufferedWriter.write("</clone_fragment>");
								bufferedWriter.newLine();


							}

							nSameLanguage++;
							bufferedWriter.write("</clone_pair>");
							bufferedWriter.newLine();
						} 
						
					}

					// close file		
					bufferedWriter.write("</clones>");
					bufferedWriter.newLine();
					bufferedWriter.flush();
					bufferedWriter.close();		

					CSVBWriter.write("</clones>");
					CSVBWriter.newLine();
					CSVBWriter.flush();
					CSVBWriter.close();	
					
					CSJSWriter.write("</clones>");
					CSJSWriter.newLine();
					CSJSWriter.flush();
					CSJSWriter.close();	

					VBJSWriter.write("</clones>");
					VBJSWriter.newLine();
					VBJSWriter.flush();
					VBJSWriter.close();	
					
				/*	System.out.println("The number of same-language clone pairs are: "+nSameLanguage );
					System.out.println("The number of CS-VB clone pairs are : "+CSVB);
					System.out.println("The number of CS-JS clone pairs are : "+CSJS);
					System.out.println("The number of VB-JS clone pairs are : "+VBJS);

			


					System.out.println("Report genarated look for the file: SimCadCloneReport.xml");*/

					System.out.println(nSameLanguage+"\t"+CSVB+"\t"+CSJS+"\t"+VBJS);

				}
			}



		}

		catch (Exception e) {
			e.printStackTrace();
		}



	}



}
