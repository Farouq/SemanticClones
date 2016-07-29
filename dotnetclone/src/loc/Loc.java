package loc;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Loc {

	/**
	 * @param args
	 * @throws Exception
	 */
	static ArrayList<String> filesName = new ArrayList<String>();  // .exe files list
	static ArrayList<String> filesFullPath = new ArrayList<String>();


	static int nof = 0;


	public static void main(String[] args) throws Exception {
		try {
			getFiles("C:/Users/........"); // path of system to study
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		int fileLoc=0;
		int systemLoc=0;

		for(int i=0;i<nof;i++){
			

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

	private static void writeToFile(ArrayList<String> lines, String fileAddress) throws Exception {
		BufferedWriter bufferedWriter = null;
		bufferedWriter = new BufferedWriter(new FileWriter(fileAddress));

		for (String s : lines) {


			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}

		bufferedWriter.flush();
		bufferedWriter.close();
	}


	private  static void getFiles(String path) throws IOException {
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();


		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".exe")){// || files.endsWith(".dll")) {
					filesFullPath.add(path+files);
					filesName.add( files);
					nof++;
				}
			}
			if (listOfFiles[i].isDirectory()) {
				files = listOfFiles[i].getName();
				getFiles(path + files + "/");
			}
		}
	}
}
