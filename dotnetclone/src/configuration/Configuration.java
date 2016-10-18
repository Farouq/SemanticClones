package configuration;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Configuration {


	public String disassembeler_EXE_path="C:\\Program Files\\Microsoft SDKs\\Windows\\v7.0A\\bin\\NETFX 4.0 Tools\\ildasm.exe"; 
	public String sourceCodeAddress="";
	public String byteCodeAddress="";
	public String disassebledAddress="";
	public String disassebledAddress_lineNo="";
	public String ontologyAddress="";
	public String sparqlAddress="";
	public String reportAddress="";
	public String projectAddress="";
	public String projectClone="";
	
	public String xmlByteCode="";
	public String xmlSourceCode="";
	public String xmlCalledMethods="";

	public String detectionMethod="";
	public String ReportingMethod="";
	public String Filterusage="";



	// default was !!! Levenshtien on byte code //instructions
	public float threshold=0.5f;
	
	public float  jaccByteThreshold=0.9f;

	// threshold for method calls
	//1- LCS threshold
	public float callsLCSthreshold=0.75f;
	//2- Jaccard threshold
	public float callsJaccthreshold=0.75f;
	
	
	//default !!!
	public float min_threshold=5.0f;

	/*  default comparison method is SimCad
	 *  1 for SimCad
	 *  2 for LCS (Longest Common Subsequent)
	 *  3 for levenshtien distance
	 */
	public int comparisonMethod=1;


	public static Configuration loadFromFile() throws Exception
	{
		Configuration config=new Configuration();
		ArrayList<String> items=new ArrayList<String>();
		readFile(items);
		config.disassembeler_EXE_path=items.get(0);
		config.threshold=Float.parseFloat(items.get(1));
		config.min_threshold=Float.parseFloat(items.get(2));
		config.comparisonMethod=Integer.parseInt(items.get(3));
		config.projectAddress=items.get(4);

		config.projectClone=config.projectAddress+"_Clone";
		config.sourceCodeAddress=config.projectClone+"/1-SourceCode";
		config.byteCodeAddress=config.projectClone+"/2-ByteCodes";
		config.disassebledAddress=config.projectClone+"/3-Disassembled";
	//	config.disassebledAddress_lineNo=projectAddress+"/2_Disassembled_LineNo";
		config.ontologyAddress=config.projectClone+"/4-Ontology";
	//	config.sparqlAddress=projectAddress+"/4_Sparql";
		config.reportAddress=config.projectClone+"/5_Report";
	//	System.out.println(config.projectAddress);
		return(config);
	}

/*	public static Configuration loadFromFile2() throws Exception
	{
		Configuration config=new Configuration();
		ArrayList<String> items=new ArrayList<String>();
		readFile(items);
		config.detectionMethod=items.get(0);
		config.threshold=Float.parseFloat(items.get(1));
		config.Filterusage=items.get(2);
		config.ReportingMethod=items.get(3);

		config.disassebledAddress=System.getProperty( "user.dir" );

		config.reportAddress=System.getProperty( "user.dir" );


		return(config);
	}*/

	private static void readFile(ArrayList<String> items) throws Exception {
		FileReader fr = new FileReader("config.txt");
		BufferedReader br = new BufferedReader(fr);

		String s;
		while ((s = br.readLine()) != null) {
			items.add(s);
		}
		fr.close();


	}

}
