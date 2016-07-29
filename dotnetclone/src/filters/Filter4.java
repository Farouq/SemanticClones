package filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Filter4 {
	
	/**
	 * @param args
	 * Filtering branching statements ( these statments followed by target"IL0001: ")
	 * 
	 */

	public static void filter4(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_4.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("br.s")||
					lin.trim().startsWith("brtrue.s")||
					lin.trim().startsWith("leave.s")||
					lin.trim().startsWith("blt.s")||
					lin.trim().startsWith("bge.s")||
					lin.trim().startsWith("beq")||
					lin.trim().startsWith("beq.s")||
					lin.trim().startsWith("bge")||
					lin.trim().startsWith("bge.un")||
					lin.trim().startsWith("bge.un.s ")||
					lin.trim().startsWith("bgt")||
					lin.trim().startsWith("bgt.s")||
					lin.trim().startsWith("bgt.un")||
					lin.trim().startsWith("bgt.un.s")||
					lin.trim().startsWith("ble")||
					lin.trim().startsWith("ble.s")||
					lin.trim().startsWith("ble.un")||
					lin.trim().startsWith("ble.un.s")||
					lin.trim().startsWith("blt")||
					lin.trim().startsWith("blt.un")||
					lin.trim().startsWith("blt.un.s")||
					lin.trim().startsWith("bne.un")||
					lin.trim().startsWith("bne.un.s")||			
					lin.trim().startsWith("br")||
					lin.trim().startsWith("brfalse")||	
					lin.trim().startsWith("brfalse.s")||
					lin.trim().startsWith("brnull")||	
					lin.trim().startsWith("brnull.s")||
					lin.trim().startsWith("brzero")||	
					lin.trim().startsWith("brzero.s")||
					lin.trim().startsWith("brtrue")||	
					lin.trim().startsWith("brinst")||
					lin.trim().startsWith("brinst.s")||	
					lin.trim().startsWith("leave"))
				
			{
				String[] temp=lin.trim().split(" ");
				lin = temp[0];
			}
			

			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
	//	System.out.println("Filter4 done");
	}
	
	
	/*
	 * This is a number filter
	 * these represent arguments number
	 */

	public static void filter5(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_5.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("starg")||
					lin.trim().startsWith("starg.s")||
					lin.trim().startsWith("ldrag")||
					lin.trim().startsWith("ldrag.s")||			
					lin.trim().startsWith("ldraga")||
					lin.trim().startsWith("ldraga.s"))
			{
				String[] temp=lin.trim().split(" ");
				lin = temp[0];
			}
			
			

			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
		System.out.println("Filter5 done");
	}
	
	
	/*
	 * this filter to filter constant numbers
	 * 
	 */
	public static void filter6(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_6.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("ldc.i4")||
					lin.trim().startsWith("ldc.i8")||
					lin.trim().startsWith("ldc.r4")||
					lin.trim().startsWith("ldc.r8")||
					lin.trim().startsWith("ldc.i4")||
					lin.trim().startsWith("ldc.i4.s"))
			{
				String[] temp=lin.trim().split(" ");
				lin = temp[0];
			}
			
			

			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
		System.out.println("Filter6 done");
	}
	
	/*
	 * this to filter out numbers represent index of local variable
	 * 
	 */
	public static void filter7(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_7.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

			if (lin.trim().startsWith("ldloc")||
					lin.trim().startsWith("ldloc.s")||
					lin.trim().startsWith("ldloca")||
					lin.trim().startsWith("ldloca.s")||
					lin.trim().startsWith("stloc")||
					lin.trim().startsWith("stloc.s"))
			{
				String[] temp=lin.trim().split(" ");
				lin = temp[0];
			}
			
			

			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
		System.out.println("Filter7 done");
	}
	
	
	
	
	public static void filter8(String fileName) throws Exception {

		String newFileName = fileName.subSequence(0, fileName.lastIndexOf('.'))+ "_8.xml";
		File newf = new File(newFileName);
		if (newf.exists())
			newf.delete();

		BufferedReader in = new BufferedReader(new FileReader(fileName));
		PrintWriter out = new PrintWriter(new FileWriter(newf));

		String lin;
	

		while ((lin = in.readLine()) != null)
		{

		
			lin=lin.trim();
			if(!lin.startsWith("<"))
			{
				
			int k= lin.indexOf(".");
			if(k>0)
				lin = lin.substring(0,k);

			k=lin.indexOf(" ");
			if(k>0)
					lin = lin.substring(0,k);

				if(lin.trim().startsWith("int")||
						lin.trim().startsWith("float")||
						lin.trim().startsWith("string")||
						lin.trim().startsWith("bool")||
						lin.trim().startsWith("uint")||
						lin.trim().startsWith("IL_")

						)
					continue;
			
			}
			out.println(lin);
		//	System.out.println(lin);
		}

		in.close();
		out.close();
	//	System.out.println("Filter8 done");
	}
	
	
}
