import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Stemmer {

	//Posting post=new Posting();
	//Porter pt = new Porter();
	static int oncestem=0;
	static int Tstem=0;
	
	boolean DESC =false;
	
	public static Map<String, Integer> stemmap = new TreeMap<String,Integer>();
	public LinkedList<Integer> stemposting_file ;
	public TreeMap<Integer,Integer> stemTermFreq = new TreeMap<Integer, Integer>();
	public Map<String, Integer> stemDocFreq = new TreeMap<String, Integer>();
	public TreeMap<String, TreeMap<Integer, Integer>> stempostfile_tf= new TreeMap<String, TreeMap<Integer,Integer>>();
	Map<String, LinkedList<Integer>> stempostingmap = new TreeMap <String, LinkedList<Integer>>();

	public static void addToDictionary(String stem,int freq,Map<String,Integer> stemmap) {
		if (stem.length() > 0) {
			// check if stem already exits
			if (stemmap.containsKey(stem)) {
				//add 1 to stem count in stemmap
				stemmap.put(stem, stemmap.get(stem) + 1);
			} else {
				//add new stem to stemmap with freq count
				stemmap.put(stem, freq);
			}
		}
	}

	public static void stemTermFreq(){
		for (int k : stemmap.values()){
			if(k == 1){
				oncestem = oncestem + 1;
				//System.out.println(i);
			}
			//System.out.println(i);
			Tstem  = Tstem + k;
		}
		System.out.println ("The number of stems that occur only once in the Cranfield text collection : "+ oncestem);
		System.out.println ("The number of distinct stems in the Cranfield text collection : "+ stemmap.size());
	}
		
	public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
	{

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>()
				{
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
				});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Map<Integer, Double> sortByComparator1(Map<Integer, Double> unsortMap, final boolean order)
	{

		List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<Integer, Double>>()
				{
			public int compare(Entry<Integer, Double> o1,
					Entry<Integer, Double> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
				});

		// Maintaining insertion order with the help of LinkedList
		Map<Integer,Double> sortedMap = new LinkedHashMap<Integer,Double>();
		for (Entry<Integer, Double> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void AddtoStemPostingmap(Map<String, LinkedList<Integer>> stempostmap,String term, int DocId){

		stempostingmap=stempostmap;
		//System.out.println(postingmap.get(term));
		//System.out.println(term + Doc);
		if(stempostingmap.get(term)==null){
			stemposting_file = new LinkedList<Integer>();
			AddToStemPosting_file(DocId);
			stempostingmap.put(term, stemposting_file);
		}
		stempostingmap.get(term).add(DocId);
		//postingmap.put(term, post );
		//System.out.println("im Adding to postingmap");
		//System.out.println(Dictionary.get(term));
		//System.out.println(postingmap.get(term));
	}

	public void addStemTopostfile_tf(TreeMap<String, TreeMap<Integer,Integer>> stemPost_tf , String term, int DocId, int tf){

		stempostfile_tf = stemPost_tf;
		
		if(stempostfile_tf.get(term)==null){
			//System.out.println("im here in if");
			stemTermFreq= new TreeMap<Integer, Integer>();
			stemTermFreq.put(DocId, tf);
			stempostfile_tf.put(term, stemTermFreq);
		}
		else{
			if(stempostfile_tf.get(term).get(DocId)==null){
				stemTermFreq.put(DocId, tf);
				stempostfile_tf.put(term, stemTermFreq);
			}
			else{
				stemTermFreq.put(DocId, stempostfile_tf.get(term).get(DocId)+1);
				stempostfile_tf.put(term, stemTermFreq);
			}
	}
	}
	
	@SuppressWarnings("rawtypes")
	public void getStemDocFrequency(){
		Set<String> term1 = stempostingmap.keySet();
		for(String key : term1)
		{
		   int size = ((List)stempostingmap.get(key)).size()-1;
		   stemDocFreq.put(key,size);
		}		
	}


	public LinkedList<Integer> GetStemPosting(String stemterm){

		return stempostingmap.get(stemterm);
	}

	public void AddToStemPosting_file(int DocId){

		stemposting_file.add(DocId);
	}

	public int getStemCount (String term) {

		if (stemmap.containsKey(term))
		{  // The word has occurred before, so get its count from the map
			return stemmap.get(term);
		}
		return 0;
	}

	public Integer getStemTermFreqCount (String stemterm, int DocId) {
		//System.out.println(stemterm + "---" + DocId + "---" + stempostfile_tf.get(stemterm).get(DocId));
		return stempostfile_tf.get(stemterm).get(DocId);
	}
	
	public boolean IsInStemPostfile_tf(String term){
		if(stempostfile_tf.containsKey(term)){
			return true;
		}
		return false;
	}
	
	public boolean checkStemDocID (String stemterm, int DocId) {
		
		if(stempostingmap.get(stemterm).contains(DocId)){
			return true;
		}		
		return false;
	}
	
	
}
