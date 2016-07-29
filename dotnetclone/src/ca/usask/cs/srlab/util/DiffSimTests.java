package ca.usask.cs.srlab.util;

import java.io.File;

import org.junit.Test;

public class DiffSimTests {
	

	//@Test
	public void testFileSimilarity(){
		
		String fileName1 = "src/ca/usask/cs/srlab/util/f1.txt";
		String fileName2 = "src/ca/usask/cs/srlab/util/f2.txt";
		int fileSize1 = ManualAnalysisDiff.getCloneLen(fileName1);
		int fileSize2 = ManualAnalysisDiff.getCloneLen(fileName2);
		System.out.println(ManualAnalysisDiff.getUPI(fileName1, fileName2, fileSize1, fileSize2));
		System.out.println(ManualAnalysisDiff.getUPI(fileName2, fileName1, fileSize2, fileSize1));
	}	
	
	
	@Test
	public void testTextSimilarity(){
		
		String text1 = "a\nb\nc\nd\ne\nf";
		String text2 = "a\nb\nc\nd\ne\nf\ng\nx";
		
		System.out.println(ManualAnalysisDiff.getUPIForTextBlock(text1, text2, 5, 7));
		System.out.println(ManualAnalysisDiff.getUPIForTextBlock(text2, text1, 7, 5));
		
	}	
	
	
}
