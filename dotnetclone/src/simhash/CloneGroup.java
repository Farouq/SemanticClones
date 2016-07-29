package simhash;

import java.util.ArrayList;
import java.util.List;

public class CloneGroup {
	
	private boolean subsumed;
	private List<SourceItem> groupMember;  
	
	public CloneGroup(){
		subsumed = false;
		groupMember = new ArrayList<SourceItem>();  
	}

	public CloneGroup(List newCluster) {
		this(false, newCluster);
	}

	private CloneGroup(boolean subsumed, List newCluster) {
		this.subsumed = subsumed;
		groupMember = newCluster;
	} 
	
	public void removeMember(SourceItem member){
		groupMember.remove(member);
	}

	public void removeMemberByIndex(int index){
		groupMember.remove(index);
	}

	public void addMember(SourceItem member){
		groupMember.add(member);
	}


	public SourceItem getMember(int i) {
		return groupMember.get(i);
	}

	public boolean isSubsumed() {
		return subsumed;
	}

	public void setSubsumed(boolean subsumed) {
		this.subsumed = subsumed;
	}

	public List<SourceItem> getGroupMember() {
		return groupMember;
	}

	public void setGroupMember(List<SourceItem> groupMember) {
		this.groupMember = groupMember;
	}

	public int size() {
		return groupMember.size();
	}


}
