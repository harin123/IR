import java.util.*;

public class Posting {
	
	public Map <String, Integer> Dictionary = new TreeMap<String, Integer>();
	public List<Integer> posting_file ;//= Tokenise.DocNo;
	Map<String, List<Integer>> postingmap = new TreeMap <String, List<Integer>>();
	//Map <Map<String, Integer>, LinkedList<String>> Posting = new TreeMap <Map<String, Integer>, LinkedList<String>>();
	
	
	public void AddtoDict(Map<String, Integer> dict,String term, int freq){
		
		this.Dictionary=dict;
		Dictionary.put(term, freq);
		//System.out.println("im Adding to dict");
		//System.out.println("Frequency of " +term+ " : " + Dictionary.get(term));
	}
	
	public void AddtoPostingmap(Map<String, List<Integer>> postmap,String term, int DocId){
		
		this.postingmap=postmap;
		//System.out.println(postingmap.get(term));
		//System.out.println(term + Doc);
		if(postingmap.get(term)==null){
			posting_file = new LinkedList<Integer>();
			AddToPosting_file(DocId);
			postingmap.put(term, posting_file );
		}
		postingmap.get(term).add(DocId);
		//postingmap.put(term, post );
		//System.out.println("im Adding to postingmap");
		//System.out.println(Dictionary.get(term));
		//System.out.println(postingmap.get(term));
	}
	
	public List<Integer> GetPosting(String term){
		
		return postingmap.get(term);
		
	}
	
	public void AddToPosting_file(Integer DocId){
		
		posting_file.add(DocId);
	}
	
	public int getCount (String term) {
		
	      if (Dictionary.containsKey(term))
	      {  // The word has occurred before, so get its count from the map
	    	  return Dictionary.get(term);
	      }
		return 0;
	   }
}
