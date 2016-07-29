package nicad;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import configuration.Configuration;

public class ClonePairsCounter {



	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Configuration config=Configuration.loadFromFile();
	
		parseNiCadPairs("Monoo-2.10_functions-clones-0.30.xml");
		parseMyReportPairs(config.reportAddress+"\\LevenshtienAndNiCad_binary0.75.xml");


		
	}

	public static void parseNiCadPairs( String rawFunctionsFileName) throws IOException{
		int st=0;
		int en=0;

		String c=new String();
		c="c:/";
		
		String c2="C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\mcs\\";


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



				for(int file=1;file<sourceList.getLength();file+=2){
						
						String file1= sourceList.item(file).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
    					int startline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue() );
						int endline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue() );
						
						st=startline;
						en=endline;

					}
				}

				System.out.println("Done");

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void parseMyReportPairs( String rawFunctionsFileName) throws IOException{
		int st=0;
		int en=0;

		String c=new String();
		c="c:/";
		
		String c2="C:\\Users\\faa634\\Desktop\\mono\\mono-2.10\\mcs\\";


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
						
						st=startline;
						en=endline;

					}
				}

				System.out.println("Done");

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
