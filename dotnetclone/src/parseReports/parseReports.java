package parseReports;

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

import simhash.SimhashCloneTest6e;
import configuration.Configuration;


public class parseReports {
	



	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
				Configuration config=Configuration.loadFromFile();
		
		readXml(config, config.disassebledAddress+"\\allFiles.xml_0-secco_clones-multi_index-"+SimhashCloneTest6e.simThreshold+".xml");
		

	}

	
	public static void readXml(Configuration config, String fileName){
		
		System.out.println(fileName);
		
	}
	
}