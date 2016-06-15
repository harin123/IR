import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Tokenise {

	static String line =null;
	static String sub;
	static String Doc;
	static String term1,token1,token2,term;
	String word = null;
	static String[] words, words1;
	String[] tokens=null;
	static List<String> Docs;
	static List<Integer> DocNo;
	static int line_count=0;
	static int count =0;
	static int frequency=1;
	static int DocId =0;
	static boolean DES =false;
	//Posting ps =new Posting();

	static String stemword ;
	static String Strline;
	static char ch;

	@SuppressWarnings("static-access")
	public static void main(String [] args) throws Exception {
		Posting ps = new Posting();
		Porter port = new Porter();
		Stemmer st = new Stemmer();
		//public void Tokeniser() throws NumberFormatException, IOException {

		String path = "/people/cs/s/sanda/cs6322/Cranfield";
		//String path = "F:\\IV semester\\IR\\Cranfield" ;
		//File f = new File(path);
		File dir = new File(path);
		File[] files= dir.listFiles();
		for(File f: files){
			DocId++;
			//System.out.println(f);
			FileInputStream fileinputstream;
			fileinputstream = new FileInputStream(f);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(fileinputstream));
			//System.out.println(DocId);
			while((line=br.readLine())!=null){
				if(!line.startsWith("<")){
					if(!line.isEmpty()){
						line_count++;
						sub = line;
						//String regexp = "[\\s,'?=(-/;\\n\\t]+|[0-9x]+";
						//String regexp2 = "[A-Za-z]+|[0-9x0-9]+";
						String regexp ="[\\s,+?=()/;\\n\t]+|[0-9x]+";
						//System.out.println(sub);
						final Pattern p = Pattern.compile(regexp);
						words = p.split(sub);
						//words = sub.split("\\s");

						for(int i=0;i<words.length;i++) {
							term1 = words[i];
							token2 = term1.replace("'","");
							if((token2.length()>0) && !token2.matches("[0-9]+")) {
								token1 = token2.replace(".","");									
								if(token1.length()>0){									
									term=token1.replace("-","");
									//System.out.println(term);
									if (!ps.Dictionary.containsKey(term)){
										//System.out.println("im here!!");
										ps.AddtoDict(ps.Dictionary, term, frequency);
										//ps.AddtoPostingmap(ps.postingmap, term, DocId);
									}
									else{
										//frequency = ps.getCount(term) + 1;
										count = ps.getCount(term) + 1;
										//if(!ps.Posting.get(term).contains(Doc)){																	
										ps.AddtoDict(ps.Dictionary, term, count);
									}
								}

							}
						}
					}
				}
			}

		}

		int oncetoken=0, Tcount=0;
		double avg= 0.0;
		double Savg =0.0;
		
		@SuppressWarnings("unused")
		String maxTokens;
		//System.out.println("Dictionary information" + ps.Dictionary.get("the"));
		for(int i : ps.Dictionary.values()){
			if(i == 1){
				oncetoken = oncetoken + 1;
				//System.out.println(i);
			}
				Tcount  = Tcount + i;
		}
		avg = Tcount/DocId ;
				
		System.out.println("The number of tokens in the Cranfield text collection : " + Tcount);
		System.out.println("The number of unique tokens in the Cranfield text collection : " + ps.Dictionary.size());
		System.out.println("The number of tokens that occur only once in the Cranfield text collection : " + oncetoken );
		System.out.println("The average number of word tokens per document : " + avg);
	    System.out.println ("The 30 most frequent word tokens : ");
	    
	    Map<String, Integer> sortedMapDesc = st.sortByComparator(ps.Dictionary, DES);
		Iterator<Entry<String, Integer>> it1 = sortedMapDesc.entrySet().iterator();
		
		for(int j=0;j<30 &&it1.hasNext();j++){
			System.out.println(it1.next()); 
		}

		for (Entry<String, Integer> entry : ps.Dictionary.entrySet()) {
			String key = entry.getKey();
			Integer values = entry.getValue();
			
			Strline = key;
			for (int ind = 0; ind < Strline.length();ind++) {
				ch = Strline.charAt(ind);
				port.add(ch);
			}
			port.stem();
			stemword = port.toString();
			st.addToDictionary(stemword,values, st.stemmap);
		}
		
		System.out.println("========================Stemming=============================");
		st.stemTermFreq();
		Savg = (st.Tstem/DocId);
		System.out.println ("The average number of word stems per document : "+ Savg);
		st.mostFreqStem();
	}

}