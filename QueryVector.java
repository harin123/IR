import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class QueryVector {
	public static Map <String, Integer> qDictionary = new TreeMap<String, Integer>();
	public LinkedList<Integer> qposting_file ;
	public LinkedList<Map<Integer,Integer>> qTfreq;
	public static TreeMap<String, LinkedList<Map<Integer,Integer>>> qPosting_TF = new TreeMap<String,LinkedList<Map<Integer,Integer>>>();
	public static Map<Integer,Integer> qDocwordcount = new HashMap<Integer,Integer>();
	public Map<Integer,Integer> qTermFreq = new HashMap<Integer, Integer>();
	public static TreeMap<String, Integer> qDocFreq = new TreeMap<String, Integer>();
	public TreeMap<String, TreeMap<Integer, Integer>> qpostfile_tf= new TreeMap<String, TreeMap<Integer,Integer>>();
	static Map<String, LinkedList<Integer>> qpostingmap = new TreeMap <String, LinkedList<Integer>>();
	//Map <Map<String, Integer>, LinkedList<String>> Posting = new TreeMap <Map<String, Integer>, LinkedList<String>>();
	
	public void qAddtoDict(Map<String, Integer> qdict,String qterm, int freq){

		qDictionary=qdict;
		qDictionary.put(qterm, freq);
		//System.out.println("im Adding to dict");
		//System.out.println("Frequency of " +term+ " : " + Dictionary.get(term));
	}
	
	public void qAddtoPostingmap(Map<String, LinkedList<Integer>> qpostingmap,String term, int qnum){

		QueryVector.qpostingmap=qpostingmap;
		//System.out.println(postingmap.get(term));
		//System.out.println(term + Doc);
		if(qpostingmap.get(term)==null){
			qposting_file = new LinkedList<Integer>();
			qAddToPosting_file(qnum);
			qpostingmap.put(term, qposting_file);
		}
		else{
			qpostingmap.get(term).add(qnum);
		}
	}	
	
	public void qAddToPosting_file(int DocId){

		qposting_file.add(DocId);
	}
	
	public void qaddToPosting_TF(TreeMap<String, LinkedList<Map<Integer, Integer>>> qPost_TF,String term, int qnum, int qtf){
		QueryVector.qPosting_TF=qPost_TF;
		LinkedList<Map<Integer, Integer>> temptf = qPosting_TF.get(term);		
			if(qPosting_TF.get(term)==null){
				qTermFreq= new HashMap<Integer, Integer>();
				qTfreq = new LinkedList<Map<Integer,Integer>>();
				qTermFreq.put(qnum, qtf);
				qTfreq.add(qTermFreq);
				qPosting_TF.put(term, qTfreq);
				//System.out.println(term);
				//System.out.println("if " +term+" "+ Posting_TF.get(term));
			}
			else{
				Map<Integer, Integer> element = temptf.getLast();
				//TreeMap<Integer, Integer> temptf = postfile_tf.get(term);
				//System.out.println("temptf "+ temptf);			
				if(!element.containsKey(qnum)){
					//System.out.println("false");
					//System.out.println("else " + temptf.element());
					qTermFreq= new TreeMap<Integer, Integer>();
					//Tfreq = new LinkedList<TreeMap<Integer,Integer>>();			
					qTermFreq.put(qnum, qtf);
					//Posting_TF.get(term).remove(Tfreq.get(DocId));
					qPosting_TF.get(term).addLast(qTermFreq);
					//Posting_TF.put(term, Tfreq);
					//System.out.println("else-if "+term+ " "+Posting_TF.get(term));
				}
				else{
					//System.out.println("true");
					qtf= element.get(qnum);
					//tf = Posting_TF.get(term).listIterator().next().get(DocId);
					//System.out.println("tf "+ tf);
					qTermFreq.put(qnum, qtf+1);
					//System.out.println("Termfre "+TermFreq.get(DocId));
					//System.out.println("removed "+ term+ " "+Posting_TF.get(term).removeLast());
					qPosting_TF.get(term).removeLast();
					qPosting_TF.get(term).add(qTermFreq);
					//System.out.println("else-else "+Posting_TF.get(term));
				}
			}
		}
	
	public int qgetCount (String term) {
		if (qDictionary.containsKey(term))
		{  // The word has occurred before, so get its count from the map
			return qDictionary.get(term);
		}
		return 0;
	}
	
	public int qgetmaxCount (String term) {

		if (QueryParser.qmaxtfmap.containsKey(term))
		{  // The word has occurred before, so get its count from the map
			return QueryParser.qmaxtfmap.get(term);
		}
		return 0;
	}
	
	public boolean qcheckqnum (String term, int DocId) {

		if(qpostingmap.get(term).contains(DocId)){
			return true;
		}		
		return false;
	}
	
	public void getqueryFrequency(){
		Set<String> term = qpostingmap.keySet();
		for(String key : term)
		{
			@SuppressWarnings("rawtypes")
			int size = ((List)qpostingmap.get(key)).size();
			qDocFreq.put(key,size);
		}		
	}
}
