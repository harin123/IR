import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class Stemmer {

	//Posting post=new Posting();
	//Porter pt = new Porter();
	static int oncestem=0;
	static int Tstem=0;
	
	boolean DESC =false;
	
	public static Map<String, Integer> stemmap = new TreeMap<String,Integer>();


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
		//System.out.println(stemmap.get(stem));

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

	public void mostFreqStem(){
		Map<String, Integer> sortedMapDesc = sortByComparator(stemmap, DESC);
		Iterator<Entry<String, Integer>> it = sortedMapDesc.entrySet().iterator();
		
		System.out.println ("The 30 most frequent stems : ");
		for(int j=0;j<30 &&it.hasNext();j++){
			System.out.println(it.next()); 
		}
	}
		
	public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
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
	
}
