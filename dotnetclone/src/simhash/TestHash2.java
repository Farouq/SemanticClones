package simhash;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class TestHash2 {

	public static long[] simhash(String data, boolean fineGrainedToken, int loc){
		int v1[]= new int [64];
		int v2[]= new int [64];
		//System.out.println(data);
		int method = 3;
		int seconderyHashMinFreq = 1;
				
		Map<String, Short> tokenMap = new 
		TreeMap<String, Short>();

		if(method == 1){
			String[] parts = data.split("\\W+");//"(?<=\\G...)");
			for(String token : parts){
		    	short num = tokenMap.get(token) == null ? 0 : (short)(tokenMap.get(token));
				tokenMap.put(token, ++num);
		    }
		}
		else if(method == 2){
			String[] parts = data.split("(?<=\\G...)");
			for(String token : parts){
		    	short num = tokenMap.get(token) == null ? 0 : (short)(tokenMap.get(token));
				tokenMap.put(token, ++num);
		    }
		}if(method == 3){
			
			String tokenSeparator;
			tokenSeparator = fineGrainedToken && loc < 100 ? " \t\n\r\f":"\n";  //if block is big, split with line only!
			
			StringTokenizer stToken = new StringTokenizer(data, tokenSeparator);
			
			int lineNumberTag = 1234; //any number to start with  
			
		    while(stToken.hasMoreElements()) {
		    	
		    	String superToken = stToken.nextToken();
		    	//if(superToken.length() == 0){System.out.println("################gotcha!##############");continue;}
		    	
		    	if(fineGrainedToken){
		    		
			    	if(/*superToken.length() > 13 && */superToken.contains(".")){
			    		String[] subParts = superToken.split ("(?=[.])"); //split by .
						for(String subToken : subParts){
							if(subToken.length() <= 10){
					    		short num = tokenMap.get(subToken) == null ? 0 : (short)(tokenMap.get(subToken));
//System.out.println(subToken);
					    		tokenMap.put(subToken, ++num);
					    	}else{  //if string is greater than 10, break it more 
					    		String[] subSubTokens = subToken.split ("(?=[>])"); //split by camel case NOTE: removed split by _
								for(String subSubToken : subSubTokens){
							    	short num = tokenMap.get(subSubToken) == null ? 0 : (short)(tokenMap.get(subSubToken));
//System.out.println(subToken);
									tokenMap.put(subSubToken, ++num);
							    }
					    	}
					    }
			    	}else if(superToken.length() <= 10){
			    		short num = tokenMap.get(superToken) == null ? 0 : (short)(tokenMap.get(superToken));
//System.out.println(superToken);
			    		tokenMap.put(superToken, ++num);
			    	}else{  //if string is greater than 7, break it more 
						
						String[] subParts; 
						if(superToken.matches("[A-Z0-9|_|-|\\W]+")){ //uppercase constant declaration
							subParts = superToken.split ("(?=[_|.|>])"); //NOTE: removed split by _
						}else
							if(loc < 7)
								subParts = superToken.split ("(?=[[A-Z]|_|.|>])"); //split by camel case. NOTE: removed split by [A-Z] _
							else
								subParts = superToken.split ("(?=[_|.|>])"); //split by camel case. NOTE: removed split by [A-Z] _
						
						for(String subToken : subParts){
					    	short num = tokenMap.get(subToken) == null ? 0 : (short)(tokenMap.get(subToken));
//System.out.println("subToken);
							tokenMap.put(subToken, ++num);
					    }
			    
			    	}
			    	//if(loc<6)
			    		//seconderyHashMinFreq = 0; //overriding the default one
		    	}else{  
		    		String modToken = superToken.trim() + lineNumberTag++ ; //to fix the bug of reordering
		    		short num = tokenMap.get(modToken) == null ? 0 : (short)(tokenMap.get(modToken));
			        tokenMap.put(modToken, ++num);
			        seconderyHashMinFreq = 0; //overriding the default one
		    	}
		    }
		}else if(method == 4){
			/*
			if(fineGrainedToken){
				Lexer lexer = JavaLexer.build();
				List<Token> tokenList = lexer.lex(data);
				short num; 
				for(Token token : tokenList){
					num = tokenMap.get(token.getOriginalContent()) == null ? 0 : (short)(tokenMap.get(token.getOriginalContent()));
					tokenMap.put(token.getOriginalContent(), ++num);
				}
	    	}else{
				StringTokenizer stToken = new StringTokenizer(data, "\n");
				int lineNumberTag = 1234; //any number to start with  
				while(stToken.hasMoreElements()) {
					String superToken = stToken.nextToken();
					String modToken = superToken.trim() + lineNumberTag++ ; //to fix the bug of reordering
					short num = tokenMap.get(modToken) == null ? 0 : (short)(tokenMap.get(modToken));
					tokenMap.put(modToken, ++num);
				}
	    	}*/
			
		}
		/*
		System.out.println("########################");
		for(Entry<String, Short> e: tokenMap.entrySet()){
			System.out.println(e.getKey());
		}
		System.out.println("########################\n");
		*/
		for(Map.Entry<String, Short> entry : tokenMap.entrySet()) {
			int freq = entry.getValue();
			int tempFreq = freq;
			
			long hash = ApacheHash.lookup3ycs64(entry.getKey(), 0, entry.getKey().length(), 0);
				
				//System.out.println(entry.getValue()+" : " + hash+" : " + entry.getKey());
				 				
				for (int c=0; c<64; c++){
					
					if(fineGrainedToken)
						tempFreq = freq > 5 ? freq/*/2*/ : freq;
					
					v1[c] += (hash & (1l << c)) == 0 ? -tempFreq : tempFreq;
				}
			
			//secondary hash for better precision
			if(freq > seconderyHashMinFreq) {
				hash = ApacheHash.lookup3ycs64(entry.getKey(), 0, entry.getKey().length(), 32767l);
				
				//System.out.println(entry.getValue()+" : " + hash+" : " + entry.getKey());
				 				
				for (int c=0; c<64; c++){		
					v2[c] += (hash & (1l << c)) == 0 ? -tempFreq : tempFreq;
				}
			}
	    }

		long simhash[]={0,0};
		for (int c=0; c<64; c++)
			simhash[0] |= (v1[c]>0 ? 1l : 0l) << c;
		
		for (int c=0; c<64; c++)
			simhash[1] |= (v2[c]>0 ? 1l : 0l) << c;
		
		return simhash;
		
/*		if(true)
		return "";
		
		for(String substr : parts){
			if(substr.contains(" ")) continue;
			//int hash = substr.hashCode();
			//long hash = hash64(substr);
			long hash = ApacheHash.lookup3ycs64(substr, 0, substr.length(), 0);
			String hstr = Long.toBinaryString(hash);
			for(int j=64-hstr.length(),k=0; k<hstr.length(); j++,k++){
				if(hstr.charAt(k)== '1')
					v[j]++;
				else
					v[j]--;
			}
		}
		
		StringBuffer sb = new StringBuffer(); 
		for(int i =0; i<64; i++){
			sb.append(v[i]>0?1:0);
		}
		return sb.toString();
*/
		
	}
	
}