package nicad;


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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;



/*
 * this file reads the output report that generated from SimCad and generate our report
 */
public class nicadXmlReader {

	static float [][] blockAvgSize=new float[10][10];
	static float[][] blockStd = new float [10][10];
	static ArrayList<int[]> blockLoc= new ArrayList<int[]>();

	static ArrayList<String> out =new ArrayList<String>();

	public static void main(String[] args) throws Exception{

	


		parseNiCadPairs("Monoo-2.10_functions-blind-clones-0.30.xml");
	//	write();





		//        File filesnames = new File("C:/Users/faa634/Documents/similarityunfiltered.txt");
		//       FileWriter out = new FileWriter(filesnames);
	}



	// parse nicad results and convert it to windows

	public static void parseNiCadPairs( String rawFunctionsFileName) throws IOException{



		ArrayList<int[]> classesFreq= new ArrayList<int[]>();

		int[] classFreq= new int[10];
		int[] gameLoc =new int[10];

		int st=0;
		int en=0;
		String file2=new String();
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
		//	allc=nl.getLength();

					System.out.println(nl.getLength()+"**************************");

			int w=0;

			if(nl.getLength()>0){

				for (int group=0;group<nl.getLength();group++)
				{
					NodeList sourceList = nl.item(group).getChildNodes();

			//		System.out.println( sourceList.getLength()+"----------------------");





					for(int file=1;file<sourceList.getLength();file+=2){
						
						String file1= sourceList.item(file).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
				//			System.out.println(file1);
					

						int startline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue() );
						int endline=Integer.parseInt(sourceList.item(file).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue() );
				//			System.out.println(startline+"  "+endline);
						
						
//						String file11= sourceList.item(1).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
//						String startline11 = sourceList.item(1).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
//						String endline11 = sourceList.item(1).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
//						
//						String file22= sourceList.item(3).getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
//						String startline22 = sourceList.item(3).getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
//						String endline22 = sourceList.item(3).getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
	
						

					
						st=startline;
						en=endline;

						w++;
						file1=file1.replace(".ifdefed","");
						file1=file1.replace("/","\\");
						file1=file1.substring(42);
						file1=c2.concat(file1);
						System.out.println(w+"*****   "+file1+"  "+st+"  "+en);



					}

			

				
						
			//			readFile(file2,st,en);






				}




				System.out.println("Done");



			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
	}




	private static boolean crossGame(int[] freq){
		int c=0;
		for(int i=0;i<10;i++){
			if(freq[i]>0) c++;
		}
		if(c>1) 
			return true;
		else
			return false;


	}


	private static void readFile(String file, int s, int en){

		BufferedReader br = null;

		try {


			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			//			while ((sCurrentLine = br.readLine()) != null) {
			//				System.out.println(sCurrentLine);
			//			}
			out.add("-------------------------------------------------------------------------------------------------------------------------");	
			out.add(file);
			for(int i=1;i<s;i++){
				br.readLine();
			}

			for(int i=s;i<=en;i++){
				sCurrentLine=br.readLine();
				System.out.println(sCurrentLine);
				out.add(sCurrentLine);
			}



		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void write() throws Exception
	{
		try {



			File file=new File("c:/game/Java.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);


			for(String line:out)
			{

				//System.out.println(line);
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}






