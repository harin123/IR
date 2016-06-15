import java.util.*;
import java.util.Map.Entry;

public class Posting {

	public static Map <String, Integer> Dictionary = new TreeMap<String, Integer>();
	public LinkedList<Integer> posting_file ;//= Tokenise.DocNo;
	public LinkedList<Map<Integer,Integer>> Tfreq;
	public static TreeMap<String, LinkedList<Map<Integer,Integer>>> Posting_TF = new TreeMap<String,LinkedList<Map<Integer,Integer>>>();
	public static Map<Integer,Integer> Docwordcount = new HashMap<Integer,Integer>();
	public Map<Integer,Integer> TermFreq = new HashMap<Integer, Integer>();
	public static TreeMap<String, Integer> DocFreq = new TreeMap<String, Integer>();
	public TreeMap<String, TreeMap<Integer, Integer>> postfile_tf= new TreeMap<String, TreeMap<Integer,Integer>>();
	static Map<String, LinkedList<Integer>> postingmap = new TreeMap <String, LinkedList<Integer>>();
	//Map <Map<String, Integer>, LinkedList<String>> Posting = new TreeMap <Map<String, Integer>, LinkedList<String>>();
	public HashSet <String> Stopwords = new HashSet<String>();
	//Map <Map<String, Integer>, LinkedList<String>> Posting = new TreeMap <Map<String, Integer>, LinkedList<String>>();

	public void AddtoDict(Map<String, Integer> dict,String term, int freq){

		Dictionary=dict;
		Dictionary.put(term, freq);
		//System.out.println("im Adding to dict");
		//System.out.println("Frequency of " +term+ " : " + Dictionary.get(term));
	}

	public void AddtoPostingmap(Map<String, LinkedList<Integer>> postingmap,String term, int DocId){

		Posting.postingmap=postingmap;
		//System.out.println(postingmap.get(term));
		//System.out.println(term + Doc);
		if(postingmap.get(term)==null){
			posting_file = new LinkedList<Integer>();
			AddToPosting_file(DocId);
			postingmap.put(term, posting_file);
		}
		else{
			postingmap.get(term).add(DocId);
		}
	}

	public void addToPosting_TF(TreeMap<String, LinkedList<Map<Integer, Integer>>> Posting_TF,String term, int DocId, int tf){
		Posting.Posting_TF=Posting_TF;
		LinkedList<Map<Integer, Integer>> temptf = Posting_TF.get(term);		
			if(Posting_TF.get(term)==null){
				TermFreq= new HashMap<Integer, Integer>();
				Tfreq = new LinkedList<Map<Integer,Integer>>();
				TermFreq.put(DocId, tf);
				Tfreq.add(TermFreq);
				Posting_TF.put(term, Tfreq);
				//System.out.println(term);
				//System.out.println("if " +term+" "+ Posting_TF.get(term));
			}
			else{
				Map<Integer, Integer> element = temptf.getLast();
				//TreeMap<Integer, Integer> temptf = postfile_tf.get(term);
				//System.out.println("temptf "+ temptf);			
				if(!element.containsKey(DocId)){
					//System.out.println("false");
					//System.out.println("else " + temptf.element());
					TermFreq= new TreeMap<Integer, Integer>();
					//Tfreq = new LinkedList<TreeMap<Integer,Integer>>();			
					TermFreq.put(DocId, tf);
					//Posting_TF.get(term).remove(Tfreq.get(DocId));
					Posting_TF.get(term).addLast(TermFreq);
					//Posting_TF.put(term, Tfreq);
					//System.out.println("else-if "+term+ " "+Posting_TF.get(term));
				}
				else{
					//System.out.println("true");
					tf= element.get(DocId);
					//tf = Posting_TF.get(term).listIterator().next().get(DocId);
					//System.out.println("tf "+ tf);
					TermFreq.put(DocId, tf+1);
					//System.out.println("Termfre "+TermFreq.get(DocId));
					//System.out.println("removed "+ term+ " "+Posting_TF.get(term).removeLast());
					Posting_TF.get(term).removeLast();
					Posting_TF.get(term).add(TermFreq);
					//System.out.println("else-else "+Posting_TF.get(term));
				}
			}
		}
	
	/*	public void getTermCount (String term) {
		double tf=0;
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : Posting_TF.entrySet()){	
			String key1 = entrymap.getKey();			
				for (Map<Integer, Integer> entry : entrymap.getValue()) {
					Set<Integer> keys = entry.keySet();
					for (int key : keys) {	        	
						int value = entry.get(key);	            
						System.out.println("key = " + key);
						System.out.println("value = " + value);
						tf = 1+Math.log10(value);
						//System.out.println("tf "+ tf);
					}
				}			
		}
		for (Entry<String, LinkedList<TreeMap<Integer, Integer>>> entrymap : Posting_TF.entrySet()) {
			String key = entrymap.getKey();
			if(key == term){
				LinkedList<TreeMap<Integer, Integer>> tcount = entrymap.getValue();
				TreeMap<Integer, Integer> map = tcount.listIterator().next();
				while(tcount.iterator().hasNext()){
					doc1 = 
					Set<Integer> docs = map.keySet();
					for(TreeMap<Integer, Integer> submap : tcount){
						for(int d: docs){
							if(d == docs.iterator().next()){
								count = count +1;
								doc = d;
								System.out.println("d "+d);				
							}
							System.out.println("count "+ count);
						}
						//System.out.println("doc "+doc + "count "+ count);
					}	
				}
			}
		}*/

	

	/*	public void addToPostfile_tf(TreeMap<String, TreeMap<Integer,Integer>> Postfile_tf,String term, int DocId, int tf){
		this.postfile_tf=Postfile_tf;
		TreeMap<Integer, Integer> temptf = postfile_tf.get(term);
		//System.out.println(postingmap.get(term));
		//System.out.println(term + Doc);
		if(postfile_tf.get(term)==null){
			TermFreq= new HashMap<Integer, Integer>();
			TermFreq.put(DocId,tf);
			postfile_tf.put(term, TermFreq);
			//System.out.println(term);
			//System.out.println("if " + postfile_tf.get(term));
		}
		else{
			//TreeMap<Integer, Integer> temptf = postfile_tf.get(term);
			if(!temptf.keySet().contains(DocId)){
				//System.out.println("false");
				TermFreq= new HashMap<Integer, Integer>();
				TermFreq.put(DocId,tf);
				postfile_tf.put(term, TermFreq);
				//System.out.println("else-if "+postfile_tf.get(term));
			}
			else{
				//System.out.println("true");
				tf = postfile_tf.get(term).get(DocId);
				TermFreq.put(DocId, tf+1);
				postfile_tf.put(term, TermFreq);
				//System.out.println("else-else "+postfile_tf.get(term));
			}

		}
	}

		public void addTopostfile_tf(TreeMap<String, TreeMap<Integer,Integer>> Post_tf , String term, int DocId, int tf){

		postfile_tf=Post_tf;

		if(postfile_tf.get(term)==null){
			//System.out.println("im here in if");
			TermFreq= new TreeMap<Integer, Integer>();
			TermFreq.put(DocId, tf);
			postfile_tf.put(term, TermFreq);
		}
		else{
			//System.out.println("im here in else");
			//int tf_count= getTermFreqCount(term, DocId) + 1;
			//System.out.println(term +"-----tf_count ----" + tf_count);
			if(postfile_tf.get(term).get(DocId)==null){
				TermFreq.put(DocId, tf);
				postfile_tf.put(term, TermFreq);
			}
			else{
				TermFreq.put(DocId, postfile_tf.get(term).get(DocId)+1);
				postfile_tf.put(term, TermFreq);
			}

			//System.out.println(term + "----" + postfile_tf.get(term).get(DocId));
		}

		} */

	@SuppressWarnings("rawtypes")
	public void getDocFrequency(){
		Set<String> term = postingmap.keySet();
		for(String key : term)
		{
			int size = ((List)postingmap.get(key)).size();
			DocFreq.put(key,size);
		}		
	}


	public LinkedList<Integer> GetPosting(String term){

		return postingmap.get(term);
	}

	public void AddToPosting_file(int DocId){

		posting_file.add(DocId);
	}

	public void AddToTfreq(int DocId, int tf){
		TermFreq.put(DocId, tf);
		Tfreq.add(TermFreq);
	}

	public int getCount (String term) {

		if (Dictionary.containsKey(term))
		{  // The word has occurred before, so get its count from the map
			return Dictionary.get(term);
		}
		return 0;
	}

	public Integer getTermFreqCount (String term, int DocId) {
		//System.out.println(term + "---" + DocId + "---" + postfile_tf.get(term).get(DocId));
		//System.out.println(term + "---" + DocId + "---" + TermFreq.get(DocId));
		return postfile_tf.get(term).get(DocId);
	}

	public boolean IsInPostfile_tf(String term){
		if(postfile_tf.containsKey(term)){
			return true;
		}
		return false;
	}

	public boolean checkDocID (String term, int DocId) {

		if(postingmap.get(term).contains(DocId)){
			return true;
		}		
		return false;
		/*Set<String> postKey = postingmap.keySet();
		for(String key : postKey)
		{
			postingmap.get(key).contains(DocId);
			return true;
		}
		return false; */
	}

	public void addtoStopword(String word){
		Stopwords.add(word);
	}
	
	public int getmaxCount (String term) {

		if (Tokenise.maxtfmap.containsKey(term))
		{  // The word has occurred before, so get its count from the map
			return Tokenise.maxtfmap.get(term);
		}
		return 0;
	}

}


