package filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Filter2 {
	/**
	 * @param args
	 * To filter out the printed constants
	 */

	public static void filter2(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_2.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
		boolean inside=false;

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("ldstr"))
			{
				lin = lin.trim().substring(0,6);
				out.println(lin);
				lin = in.readLine();
				inside=true;

				/*
				while(lin.trim().startsWith("+ \""));
				{
					lin = in.readLine();
					System.out.println("in looooooop"+lin);
				}*/
			}

			if(!(lin.trim().startsWith("+ \"")&& inside))
			{	inside=true;		
				out.println(lin);
			//	System.out.println(lin);
			}
		}

		in.close();
		out.close();
		System.out.println("Filter2 done");
	}

}
