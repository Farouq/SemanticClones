package filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Filter1 {

	/**
	 * @param args
	 * To filter the prefix IL_xxxx: from IL statements 
	 */

	public static void filter1(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_f1.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("IL_"))
			{
				int i= lin.indexOf(":")+1;
				lin = lin.substring(i);
			}

			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
	//	System.out.println("Filter1 done");
	}

}
