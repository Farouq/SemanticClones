package simhash;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.usask.cs.srlab.util.XMLUtil;


public class SimhashCloneTest6e {

	public static int simThreshold = 13;

	static boolean strictOnMembership = false;
	static double clusterMembershipRatio = 0.9;	//for a candidate item, it is the ratio of how many items are matched are matched vs total items in a cluster 
	static double locTolerance = 1;

	static int minClusterSize = 2;
	static int minSizeOfGranularity = 5;
	static int simThreshold2;

	static int nGroup = 0;
	static int nFragment =0; //fragments

	static Map<Integer, HashMap<Byte, ArrayList<SourceItem>>> itemListFirstLevelMap = new HashMap<Integer, HashMap<Byte, ArrayList<SourceItem>>>();
	static public List<CloneGroup> bucketList = new ArrayList<CloneGroup>();

	static String granularity = "functions";/*"blocks"*/
	String transformation[] = {""/*,"-consistent","-blind"*/};

	static boolean applySubsumeFiltering = granularity.equals("blocks") ? true : false;

	String projects[]={"apache-mina-2.0.3","linux-2.6.38","apache-ant-1.8.2","apache-tomcat-7.0.11","eclipse-3.6.2_jdt.core","jboss-5.1.0.GA-src",/*"linux-2.6.38",*/"firefox-2.0.0.4"/*,"httpd-2.2.17","mysql-5.5.10"*/};
	String langs[]={"java","c","java","java","java","java",/*"c",*/"c"/*,"c","c"*/};

	private static int unprocessedTotalCloneLoc = 0; //includes comments and spaces in actual source code
	private static int processedTotalCloneLoc = 0; //from pretty printed code not including comments and unnecessary spaces

	//@Before
	public static void findClone(String rawFunctionsFileName) throws IOException{

		nGroup = 0;
		nFragment =0;
		itemListFirstLevelMap.clear();
		bucketList.clear();

		boolean error = false;
		File fileName = new File(rawFunctionsFileName);
		String logFileName =  rawFunctionsFileName.subSequence(0, rawFunctionsFileName.lastIndexOf('_'))+"-secco_clones-multi_index-"+simThreshold+".log";
		PrintWriter out = new PrintWriter(new FileWriter(logFileName));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fileName);
			doc.getDocumentElement().normalize();

			Element root = doc.getDocumentElement();

			NodeList nl = root.getElementsByTagName("name");
			if(nl.getLength()>0){
				//projectName = nl.item(0).getFirstChild().getNodeValue();
			}else{
				error = true;
			}

			nl = root.getElementsByTagName("description");
			if(nl.getLength()>0){
				//projectDesc = nl.item(0).getChildNodes().item(0).getNodeValue();
			}else{
				error = true;
			}

			nl = root.getElementsByTagName("source_elements");
			if(nl.getLength()>0){
				NodeList sourceList = nl.item(0).getChildNodes();

				long start = System.currentTimeMillis();
				long items =0;
				for(int i =0; i < sourceList.getLength(); i++){
					Node source = sourceList.item(i);
					if (source.getNodeType() != Node.ELEMENT_NODE) 
						continue;

					String file = source.getAttributes().getNamedItem("file").getFirstChild().getNodeValue();
					String startline = source.getAttributes().getNamedItem("startline").getFirstChild().getNodeValue();
					String endline = source.getAttributes().getNamedItem("endline").getFirstChild().getNodeValue();
					String content = source.getFirstChild().getTextContent();

					int loc = computeLoc(content);

					/*if(content.split("\n").length-1 != loc){
						System.out.println("gotcha!");
					}*/

					if(loc < minSizeOfGranularity)
						continue;

					long simhash[] = TestHash2.simhash(content, simThreshold > 0, loc);

					SourceItem item = new SourceItem(file, startline, endline, content, i, simhash[0], loc, (byte) Long.bitCount(simhash[0]), simhash[1]);

					//implementation of two level map
					if(itemListFirstLevelMap.containsKey(item.loc)){
						Map<Byte, ArrayList<SourceItem>> itemListSecondLevelMap = itemListFirstLevelMap.get(item.loc);
						if(itemListSecondLevelMap.containsKey(item.oneBitCount)){
							itemListSecondLevelMap.get(item.oneBitCount).add(item);
						}else{
							ArrayList<SourceItem> itemList = new ArrayList<SourceItem>();
							itemList.add(item);
							itemListSecondLevelMap.put(item.oneBitCount, itemList);
						}
					}else{
						HashMap<Byte, ArrayList<SourceItem>> itemListSecondLevelMap = new HashMap<Byte, ArrayList<SourceItem>>();
						itemListFirstLevelMap.put(item.loc, itemListSecondLevelMap);			
						ArrayList<SourceItem> itemList = new ArrayList<SourceItem>();
						itemList.add(item);
						itemListSecondLevelMap.put(item.oneBitCount, itemList);
					}
					items++;
				}

			//	System.out.println("Total items processed: "+items);
				out.println("Total items processed: "+items);

				long end = System.currentTimeMillis();
				long diff  = end - start;
		//		System.out.println("Pre-Processing time : "+ (diff/1000)/60 + " min, " + (diff/1000)%60 + " sec, " + (diff %1000) + " ms"  );
				out.println("Pre-Processing time : "+ (diff/1000)/60 + " min, " + (diff/1000)%60 + " sec, " + (diff %1000) + " ms"  );

				if(simThreshold == 0)
					locTolerance = 1.0;

				start = System.currentTimeMillis();

				//DBSCAN(D, eps, MinPts)
				for(Entry<Integer, HashMap<Byte, ArrayList<SourceItem>>> itemListFirstLevelMapEntry : itemListFirstLevelMap.entrySet()){
					Map<Byte, ArrayList<SourceItem>> itemListSecondLevelMap = itemListFirstLevelMapEntry.getValue();
					for(Entry<Byte, ArrayList<SourceItem>> itemList : itemListSecondLevelMap.entrySet()){
						for(SourceItem item : itemList.getValue()){
							if(item.isProceessed) continue;
							List<SourceItem> newCluster = getNeighbors (item);
							
							if (newCluster.size() >= minClusterSize){
								for(int zz=1; zz<newCluster.size();zz++){
									List<SourceItem> clonePair= new ArrayList<SourceItem>();
									clonePair.add(newCluster.get(0));
									clonePair.add(newCluster.get(zz));
									
									bucketList.add(new CloneGroup(clonePair));
								}
								
								//							nGroup++;
								//							nFragment += newCluster.size(); //fragments
								//itemList.removeAll(newCluster);
							}/*else
							item.isProceessed=false;*/
						}
					}
				}
				end = System.currentTimeMillis();
				diff  = end - start;
			//	System.out.println("Detection time : "+ (diff/1000)/60 + " min, " + (diff/1000)%60 + " sec, " + (diff %1000) + " ms"  );
				out.println("Detection time : "+ (diff/1000)/60 + " min, " + (diff/1000)%60 + " sec, " + (diff %1000) + " ms"  );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		//filter subsumed clone
		int subsumedCluster = 0;
		int subsumedFragment = 0;

		if(applySubsumeFiltering){
			for(int i = 0; i < bucketList.size(); i++){
				CloneGroup sourceGroup = bucketList.get(i);


				if(!sourceGroup.isSubsumed()){
					for(int j = 0; j< bucketList.size(); j++){
						if (i==j) continue;

						CloneGroup targetGroup = bucketList.get(j);


						if(!targetGroup.isSubsumed() && 
								sourceGroup.size() <= targetGroup.size()){

							//check if every item of targetgroup is subsumed by sourcegroup 
							boolean fullSubsume = true;
							List<SourceItem> subsumedItem = new ArrayList<SourceItem>();
							int subsumeCount = 0;
							for(int t = 0; t <targetGroup.size(); t++){
								SourceItem itemT = targetGroup.getMember(t);

								//check itemT is subsumed by any of itemS
								boolean partialSubsume = false;
								for(int s = 0; s < sourceGroup.size(); s++){
									SourceItem itemS = sourceGroup.getMember(s);
									if(itemS.fileName.equals(itemT.fileName)
											&&  Integer.valueOf(itemS.fromLine) <= Integer.valueOf(itemT.fromLine)
											&&  Integer.valueOf(itemS.toLine) >= Integer.valueOf(itemT.toLine)){
										partialSubsume = true;
										subsumedItem.add(itemT);
										subsumeCount++;
										break;
									}
								}

								if(!partialSubsume){
									fullSubsume = false;
									//break;
								}
							}

							if(fullSubsume){
								targetGroup.setSubsumed(true);

								//							nGroup--;
								//							nFragment -= targetGroup.size(); //fragments

								subsumedCluster++;
								subsumedFragment += targetGroup.size();  
							}else{
								if(subsumeCount+1 == targetGroup.size()){
									System.out.println("A very rare case happened...");
									//test
									/*								
								targetGroup.setSubsumed(true);

//								nGroup--;
//								nFragment -= targetGroup.size(); //fragments

								subsumedCluster++;
								subsumedFragment += targetGroup.size();  
									 */								
									//test

								}else if(subsumeCount > 1){

									System.out.println("An usual case happened...");
									//								targetGroup.getGroupMember().removeAll(subsumedItem);
									//								for(SourceItem st: subsumedItem){
									//									System.out.println(st.fileName);
									//									System.out.println(st.fromLine +" "+st.toLine);
									//								}

								}/*else if(subsumeCount == 1) {
								System.out.println("A rare case happened...");
							}*/
							}

						}
					}
				}
			}
		}

		/*System.out.println("\nGenerating output file...");*/
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("clones");
		doc.appendChild(root);

		for(CloneGroup cg: bucketList){
			if(cg.isSubsumed()) {
				//System.out.println("ignored subsumed clone group...");
				continue;
			}
			//if(cg.size() >= minClusterSize)
			//{
			//cb.totalBucket++;
			//System.out.println("<clone_group groupindex=\""+ ++index +"\" nfragments=\""+cg.size()+"\">");

			Node child = doc.createElement("clone_group");
			((Element)child).setAttribute("groupid", String.valueOf(nGroup++));
			((Element)child).setAttribute("nfragments", String.valueOf(cg.size()));

			nFragment += cg.size();

			SourceItem lastdisplayed = cg.getMember(0) ;
			for(SourceItem i : cg.getGroupMember()){
				//cb.totalItem++;
				//System.out.println("\t<clone_fragment file=\""+i.fileName+"\" startline= \""+i.fromLine+"\" endline=\""+i.toLine+"\" hamdist=\""+hamming_dist(i.simhash, lastdisplayed.simhash)+"\"  >");

				Node child2 = doc.createElement("clone_fragment");
				((Element)child2).setAttribute("file", i.fileName);
				((Element)child2).setAttribute("startline", i.fromLine);
				((Element)child2).setAttribute("endline", i.toLine);
				((Element)child2).setAttribute("pcid", i.pcid.toString());
				((Element)child2).setAttribute("hamdist", String.valueOf(hamming_dist(i.simhash, lastdisplayed.simhash)));
				//((Element)child2).setAttribute("simhash", String.valueOf(Long.toBinaryString(i.simhash)));

				//System.out.print(""+i.codeBlock);
				child2.appendChild(doc.createCDATASection(i.codeBlock));

				//System.out.println("\t</clone_fragment>");
				child.appendChild(child2);

				lastdisplayed = i;
				//nFragment++;



				unprocessedTotalCloneLoc += Integer.valueOf(i.toLine) - Integer.valueOf(i.fromLine) + 1;
				processedTotalCloneLoc += i.loc;
			}
			//System.out.println("</clone_group>\n\n");
			root.appendChild(child);
			//}
		}

		root.setAttribute("ngroups", Integer.toString(nGroup));
		root.setAttribute("nfragments", Integer.toString(nFragment));
		root.setAttribute("hamthreshold",  Integer.toString(simThreshold));

		System.out.println("Total clone class : "+ nGroup );
		System.out.println("Total clone frag : "+ nFragment );

		System.out.println("Total raw clone LOC : "+ unprocessedTotalCloneLoc );
		System.out.println("Total processed clone LOC : "+ processedTotalCloneLoc );

		out.println("Total clone class : "+ nGroup );
		out.println("Total clone frag : "+ nFragment );	

		out.println("Total raw clone LOC : "+ unprocessedTotalCloneLoc );
		out.println("Total processed clone LOC : "+ processedTotalCloneLoc );

		System.out.println("Subsumed clusters: "+subsumedCluster);
		System.out.println("Subsumed fragments: "+subsumedFragment);

		System.out.println("Done!\n");
		out.close();

		String outputfile = rawFunctionsFileName.subSequence(0, rawFunctionsFileName.lastIndexOf('_'))+"-secco_clones-multi_index-"+simThreshold+".xml";
		XMLUtil.writeXmlFile(doc, outputfile);
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

	private static List<SourceItem> getNeighbors (SourceItem item){
		List<SourceItem> cluster = new ArrayList<SourceItem>();
		Set<Long> capturedHash = new HashSet<Long>();

		int deviation = 0; 

		int dynamicSimThreshold1;// = simThreshold + deviation;
		int dynamicSimThreshold2;// = simThreshold2 + deviation;

		item.isTempFriend = false;
		cluster.add(item);


		item.isProceessed=true;

		int length = cluster.size();
		for(int i=0; i<length; i++) {
			SourceItem searchItem = cluster.get(i);
			//an additional check to save more computation
			if(capturedHash.contains(searchItem.simhash)) {
				continue;// its result already picked up by someone else earlier, so just ignore
			}

			Set<Integer> lockeySet = itemListFirstLevelMap.keySet();

			//dynamic threshold update

			if(simThreshold != 0){

				simThreshold2 = simThreshold;

				switch(simThreshold){

				case 6:
					simThreshold2 = 5;
					break;

				case 7:
					if(searchItem.loc < 6){
						deviation = -1;
					}else if(searchItem.loc < 8){
						deviation = -1;
					}
					simThreshold2 = 6;
					break;

				case 8:
					if(searchItem.loc < 6){
						deviation = -2;
					}else if(searchItem.loc < 8){
						deviation = -1;
					}
					simThreshold2 = 7;
					break;

				case 9:
					if(searchItem.loc < 6){
						deviation = -3;
					}else if(searchItem.loc < 8){
						deviation = -2;
					}else if(searchItem.loc < 10){
						deviation = -1;
					}
					simThreshold2 = 8;
					break;

				case 10:
					if(searchItem.loc < 6){
						deviation = -3;
					}else if(searchItem.loc < 8){
						deviation = -2;
					}else if(searchItem.loc < 10){
						deviation = -2;
					}else if(searchItem.loc < 20){
						deviation = -1;
					}
					simThreshold2 = 8;
					break;

				case 11:
					if(searchItem.loc < 6){
						deviation = -4;
					}else if(searchItem.loc < 8){
						deviation = -3;
					}else if(searchItem.loc < 10){
						deviation = -2;
					}else if(searchItem.loc < 20){
						deviation = -1;
					}
					simThreshold2 = 9;
					break;

				case 12:
					if(searchItem.loc < 6){
						deviation = -5;
					}else if(searchItem.loc < 8){
						deviation = -4;
					}else if(searchItem.loc < 10){
						deviation = -3;
					}else if(searchItem.loc < 20){
						deviation = -2;
					}else if(searchItem.loc < 30){
						deviation = -1;
					}	
					simThreshold2 = 12;
					break;
				case 13:
					if(searchItem.loc < 6){
						deviation = -5;
					}else if(searchItem.loc < 8){
						deviation = -4;
					}else if(searchItem.loc < 10){
						deviation = -3;
					}else if(searchItem.loc < 20){
						deviation = -2;
					}else if(searchItem.loc < 30){
						deviation = -1;
					}	
					simThreshold2 = 13;
					break;
				case 20:
					if(searchItem.loc < 6){
						deviation = -13;
					}else if(searchItem.loc < 8){
						deviation = -12;
					}else if(searchItem.loc < 10){
						deviation = -11;
					}else if(searchItem.loc < 20){
						deviation = -10;
					}else if(searchItem.loc < 30){
						deviation = -9;
					}else if(searchItem.loc < 40){
						deviation = -8;
					}else if(searchItem.loc < 50){
						deviation = -7;
					}else if(searchItem.loc < 100){
						deviation = -6;
					}else if(searchItem.loc < 200){
						deviation = -5;
					}else if(searchItem.loc < 400){
						deviation = -4;
					}
					simThreshold2 = 20;
					break;
				}

				/*else if(item.loc > 40){
					deviation = 2;
				}else if(item.loc > 30){
					deviation = 1;
				}*/

				dynamicSimThreshold1 = simThreshold + deviation;
				dynamicSimThreshold2 = simThreshold2 + deviation;

				for(Integer locKey : lockeySet){ // 1st level index iterator
					if(searchItem.loc - (searchItem.loc * locTolerance) < locKey && searchItem.loc + (searchItem.loc+locTolerance) > locKey){ //first level filter
						Map<Byte, ArrayList<SourceItem>> itemListSecondLevelMap = itemListFirstLevelMap.get(locKey);
						Set<Byte> bitKeySet = itemListSecondLevelMap.keySet();
						for(Byte bitKey : bitKeySet){ // 2nd level index iterator
							if(searchItem.oneBitCount - dynamicSimThreshold1 <= bitKey && searchItem.oneBitCount + dynamicSimThreshold1 >= bitKey){ //2nd level filter
								for(SourceItem matchCandidate : itemListSecondLevelMap.get(bitKey)){

									if(!matchCandidate.isProceessed && ((hamming_dist(searchItem.simhash, matchCandidate.simhash) <= dynamicSimThreshold1
											&& hamming_dist(searchItem.simhash2, matchCandidate.simhash2) <= dynamicSimThreshold2))){

										//check if at least clusterMembershipRatio times the existing members in the cluster are cool with this guy
										int minFriendCount = (int) (cluster.size() * clusterMembershipRatio);
										minFriendCount = minFriendCount < 1 ? 1: minFriendCount;

										boolean coolDude = false;
									for(SourceItem eMember:cluster){

											//set initial friendship to false
											eMember.isTempFriend = false;

											if(hamming_dist(matchCandidate.simhash, eMember.simhash) <= dynamicSimThreshold1
													&& hamming_dist(matchCandidate.simhash2, eMember.simhash2) <= dynamicSimThreshold2){

												matchCandidate.friendCount ++;
												//matchCandidate.friendlist.add(eMember);
												eMember.isTempFriend = true;

												if(	matchCandidate.friendCount == minFriendCount){ //target reached, he got the ticket to join in friends club
													coolDude = true;
													if(!strictOnMembership) break;
												}
											}
										} //done with friendship checking
										if(coolDude){ //now add him to friends club

											matchCandidate.isProceessed=true;
											cluster.add(matchCandidate);
											length++;

											//move temp friends count to real count
											for(Iterator<SourceItem> it  = cluster.iterator(); it.hasNext();){
												SourceItem eMember = it.next();
												if(eMember.isTempFriend) {
													//eMember.friendlist.add(matchCandidate);
													eMember.friendCount ++;
												}
												eMember.isTempFriend=false;
											}

										}//new member add done							

									}
								}
							}// 2nd level filter
						}//for: 2nd level index iterator	
					}//first level filter
				}


				//cleanup noise from the friends club based on new friendship
				if(cluster.size() > 1 && strictOnMembership){

					//List<SourceItem> removedMember = new ArrayList<SourceItem>();

					//do{

					int minFriendCount = (int) (cluster.size() * clusterMembershipRatio);
					minFriendCount = minFriendCount < 1 ? 1: minFriendCount;

					//removedMember.clear();

					for(Iterator<SourceItem> it  = cluster.iterator(); it.hasNext();){
						SourceItem eMember = it.next();

						if(eMember.friendCount/*eMember.friendlist.size()*/ < minFriendCount){ 
							//get him outta here!
							eMember.isProceessed = false;

							eMember.friendCount = 0;
							//eMember.friendlist.clear();
							eMember.isTempFriend = false;
							//dismissedItemList.add(eMember);
							it.remove();
							length--;

							//removedMember.add(eMember);
							//System.out.println("Member removed from club!");
						}
					}

					/*
					 * 
						if(removedMember.size() > 0) { 
							System.out.println("gotcha black sheep!");
						}

						//remove this member from other member's friendslist
						for(Iterator<SourceItem> it2  = cluster.iterator(); it2.hasNext();){
							SourceItem eMember = it2.next();
							eMember.friendlist.removeAll(removedMember);
						}

					}while(removedMember.size() > 0);*/
				}


			}else{
				Map<Byte, ArrayList<SourceItem>> itemListSecondLevelMap = itemListFirstLevelMap.get(searchItem.loc);
				for(SourceItem matchCandidate : itemListSecondLevelMap.get(searchItem.oneBitCount)){
					if(!matchCandidate.isProceessed && searchItem.simhash.equals(matchCandidate.simhash)
							&& searchItem.simhash2.equals(matchCandidate.simhash2)){
						cluster.add(matchCandidate);
						length++;
						matchCandidate.isProceessed=true;
					}
				}
			}
			//finished neighbor search of current item and now record it
			capturedHash.add(searchItem.simhash);
		}

		return cluster;
	}


	@Test
	public void testSearchWithinThreshold() throws IOException{

		//findClone("/home/saeed/test_system/apache-ant-1.8.2_functions-clones/apache-ant-1.8.2_functions_fix.xml");
		findClone("C:/Users/faa634/Desktop/SimCad/SimCad-1/simcad/aa_fix.xml");
	}

	static int hamming_dist( long a1, long a2){		
		return Long.bitCount(a1^ a2);
	}

}
