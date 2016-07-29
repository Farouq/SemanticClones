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

	public String detectionMethod="";
	public String ReportingMethod="";
	public String Filterusage="";



	// default was !!!
	public float threshold=0.5f;

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
		String projectAddress=items.get(4);

		config.sourceCodeAddress=projectAddress+"/0_SourceCode";
		config.byteCodeAddress=projectAddress+"/1_ByteCodes";
		config.disassebledAddress=projectAddress+"/2_Disassembled";
		config.disassebledAddress_lineNo=projectAddress+"/2_Disassembled_LineNo";
		config.ontologyAddress=projectAddress+"/3_Ontology";
		config.sparqlAddress=projectAddress+"/4_Sparql";
		config.reportAddress=projectAddress+"/5_Report";

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
