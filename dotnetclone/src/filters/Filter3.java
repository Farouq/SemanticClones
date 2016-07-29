package filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Filter3 {
	/**
	 * @param args
	 * Filter out stloc.n, ldloc.n, and ldc.i4.n
	 * stloc.n  —stores a value from the stack to local variable number n (n from 0 to 3)
	 * ldc.i4.n—loads a 32-bit constant (n from 0 to 8) onto the stack
	 * ldloc.n— Loada a variable into the stack
	 */

	public static void filter3(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_3.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("ldloc."))
			{
				int i= lin.indexOf(".");
				lin = lin.substring(0,i);
			}
			
			if (lin.trim().startsWith("stloc."))
			{
				int i= lin.indexOf(".");
				lin = lin.substring(0,i);
			}
			
			if ((lin.trim().startsWith("ldc.i4.")) && !(lin.trim().startsWith("ldc.i4.s")))
			{
				int i= lin.lastIndexOf(".");
				lin = lin.substring(0,i);
			}


			out.println(lin);
			//System.out.println(lin);
		}

		in.close();
		out.close();
		System.out.println("Filter3 done");
	}
}
