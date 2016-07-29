package nicad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import configuration.Configuration;

public class NiCadNotLevenshtein {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Configuration config=Configuration.loadFromFile();

		parseNiCadPairs("Monoo-2.10_functions-blind-clones-0.30.xml");

		//parseMyReportPairs(config.reportAddress+"\\LevenshtienAndNiCad_binary0.75.xml");


	}

	//print the number of clone pairs
	public static void parseNiCadPairs( String rawFunctionsFileName) throws Exception{
		int st=0;
		int en=0;

		Configuration config=Configuration.loadFromFile();

		String c2="C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\mcs\\";
		String c="C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\";



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


			System.out.println(nl.getLength()+"**************************");


			if(nl.getLength()>0){

				for (int group=0;group<nl.getLength();group++)
				{
					NodeList sourceList = nl.item(group).getChildNodes();

					//		System.out.println( sourceList.getLength()+"----------------------");


					//
					//				for(int file=1;file<sourceList.getLength();file+=2){
					//						
					//						String file1= sourceList.item(file).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					//    					int startline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue() );
					//						int endline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue() );
					//						
					//						st=startline;
					//						en=endline;
					//
					//					}

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

					if(!foundInLevenshtien( config, file1, startline1, endline1,file22, startline22,endline22 )){


					}
					System.out.println(file1+"  "+startline1);

				}

				System.out.println("Done");

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static Boolean foundInLevenshtien(Configuration config, String fileA, String stA, String enA, String fileB, String stB, String enB) throws IOException{
		boolean found = false;

		String rawFunctionsFileName=config.reportAddress+"\\LevenshtienAll"+ config.threshold+".xml";	
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

			nl = root.getElementsByTagName("clone_pair");


			System.out.println(nl.getLength()+"**************************");


			if(nl.getLength()>0){

				for (int group=0;group<nl.getLength();group++)
				{
					NodeList sourceList = nl.item(group).getChildNodes();
					//				System.out.println( sourceList.getLength()+"----------------------");



					for(int file=1;file<sourceList.getLength();file+=2){

						String file1= sourceList.item(file).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
						int startline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue() );
						int endline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue() );



					}


					System.out.println("Done");
				}
			}


		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return found;


	}

}





