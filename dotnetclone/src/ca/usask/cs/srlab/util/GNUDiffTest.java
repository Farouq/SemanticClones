package ca.usask.cs.srlab.util;

import java.io.IOException;

import org.junit.Test;

import ca.usask.cs.srlab.util.DiffPrint.NormalPrint;


public class GNUDiffTest {

	@Test
	public void testDiff() throws IOException{
		/*String filea = "src/ca/usask/cs/srlab/util/f1.txt";
		String fileb = "src/ca/usask/cs/srlab/util/f2.txt";
		
		String[] a = DiffPrint.slurp(filea);
		String[] b = DiffPrint.slurp(fileb);
		*/    
		
		String text1 = "a\nb\nc\nd\ne\nf";
		String text2 = "a\nb\nc\nd\ne\nf\ng\nx";
		
	    String[] a = text1.split("\n");
	    String[] b = text2.split("\n");
	    
	    //Diff d = new Diff(a,b);
	    Diff d = new Diff(b,a);
	    
	    Diff.change script = d.diff_2(false);
	    
	    NormalPrint p = new NormalPrint(a,b);
	    
	    System.out.println(p.process_script_lessCount(script));
	}
	
	
	@Test
	public void testUPI() throws IOException{
		/*String filea = "src/ca/usask/cs/srlab/util/f1.txt";
		String fileb = "src/ca/usask/cs/srlab/util/f2.txt";
		
		String[] a = DiffPrint.slurp(filea);
		String[] b = DiffPrint.slurp(fileb);
		*/    
		
		String text1 = "a\nb\nc\nd\ne\nf";
		String text2 = "a\nb\nc\nd\ne\nf\ng\nx";
		
	    double simValue = getUPI(text1, text2);
	    System.out.println(simValue);
         
	}


	private double getUPI(String text1, String text2) {
		String[] a = text1.split("\n");
	    String[] b = text2.split("\n");
	    
	    Diff d = new Diff(a,b);
	    //Diff d = new Diff(b,a);
	    
	    Diff.change script = d.diff_2(false);
	    
	    NormalPrint p = new NormalPrint(a,b);
	    
	    int leftDistinct = p.process_script_lessCount(script);
	    
	    int sequenceLength = a.length - leftDistinct;  
        // out.println("seq is: "  + sequenceLength + "file1 " + fileSize1 + "left " + leftDistinct);  
	    return ((double)leftDistinct*100/(double)a.length);
	}
	
}
