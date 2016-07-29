package simhash;

import java.util.ArrayList;
import java.util.List;

public class SourceItem {
	public String fileName;
	public String fromLine;
	public String toLine;
	public String codeBlock;
	public Long simhash;
	public Long simhash2;
	public Integer pcid;
	boolean isProceessed;
	public int loc;
	public byte oneBitCount;
	public String cloneClassId;
	
	int friendCount = 0; 
	boolean isTempFriend = false;
	//List<SourceItem> friendlist = new ArrayList<SourceItem>();
	
	public SourceItem(String fileName, String fromLine, String toLine,
			String codeBlock, Integer pcid, Long simhash) {
		super();
		this.fileName = fileName;
		this.fromLine = fromLine;
		this.toLine = toLine;
		this.codeBlock = codeBlock;
		this.pcid = pcid; 
		this.simhash = simhash;
		isProceessed = false;
		loc = Integer.valueOf(toLine)-Integer.valueOf(fromLine)+1;
	}

	public SourceItem(String fileName, String fromLine, String toLine,
			String codeBlock, Integer pcid, Long simhash, int loc) {
		super();
		this.fileName = fileName;
		this.fromLine = fromLine;
		this.toLine = toLine;
		this.codeBlock = codeBlock;
		this.pcid = pcid; 
		this.simhash = simhash;
		this.isProceessed = false;
		this.loc = loc;
	}
	
	public SourceItem(String fileName, String fromLine, String toLine,
			String codeBlock, Integer pcid, Long simhash, int loc, byte oneBitCount) {
		super();
		this.fileName = fileName;
		this.fromLine = fromLine;
		this.toLine = toLine;
		this.codeBlock = codeBlock;
		this.pcid = pcid; 
		this.simhash = simhash;
		this.isProceessed = false;
		this.loc = loc;
		this.oneBitCount=oneBitCount;
	}
	
	public SourceItem(String fileName, String fromLine, String toLine,
			String codeBlock, Integer pcid, Long simhash, int loc, byte oneBitCount, Long simhash2) {
		super();
		this.fileName = fileName;
		this.fromLine = fromLine;
		this.toLine = toLine;
		this.codeBlock = codeBlock;
		this.pcid = pcid; 
		this.simhash = simhash;
		this.isProceessed = false;
		this.loc = loc;
		this.oneBitCount=oneBitCount;
		this.simhash2 = simhash2;
	}
	
	public SourceItem(String fileName, String fromLine, String toLine,
			String codeBlock, Integer pcid, Long simhash, String cloneClassId) {
		super();
		this.fileName = fileName;
		this.fromLine = fromLine;
		this.toLine = toLine;
		this.codeBlock = codeBlock;
		this.pcid = pcid; 
		this.simhash = simhash;
		this.isProceessed = false;
		this.loc = loc;
		this.oneBitCount=oneBitCount;
		this.cloneClassId = cloneClassId;
	}
	
	@Override
	public int hashCode() {
		//return pcid.hashCode();
		return (fileName + fromLine + toLine).hashCode();
	} 
	
	@Override
	public boolean equals(Object obj) {
		//return pcid.equals(((SourceItem)obj).pcid)
		return fileName.equals(((SourceItem)obj).fileName)
		&& fromLine.equals(((SourceItem)obj).fromLine)
		&& toLine.equals(((SourceItem)obj).toLine);
	}
}
