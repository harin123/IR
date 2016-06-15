import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenise {

	static String line =null;
	static String Titleline =null;
	static String Tline = null;
	static String Title=null;
	static String sub;
	static String Doc;
	static String term1,token1,token2,term;
	String word = null;
	static String[] words, words1;
	//String[] tokens=null;
	static int count =0;

	static int frequency = 1;
	static int tf =1;
	static int DocId;
	static boolean DES =false;

	static String stemword ;
	static String lem_term;
	static String Strline;
	static char ch;
	int max_tf=0;
	static Map<Integer,Integer> maxtfval = new HashMap<Integer,Integer>();
	static Map<String, Integer> maxtfmap = new HashMap<String,Integer>();
	//static List<String> keys = Collections.unmodifiableList(Arrays.asList("criterion","develop","show","empirical","validity","flow","solution","chemical","react","gas","mixture","base","simplify","assumption","instantaneous","local", "chemical", "equilibrium"));
	
	@SuppressWarnings({ "static-access" })
	public static void main(String [] args) throws Exception {
		Posting ps = new Posting();
		Porter port = new Porter();
		Stemmer st = new Stemmer();
		Stopwords stw = new Stopwords();
		lemmatizer lemma = new lemmatizer();
		QueryParser query = new QueryParser();
		QueryVector qobj =new QueryVector();
		TfIdf TfIdfObj = new TfIdf();
		CosineSimilarity cosobj = new CosineSimilarity();
		Headline title = new Headline();
		String path = "/people/cs/s/sanda/cs6322/Cranfield";

		// creating stoplist
		stw.stoplist();
		//File f = new File(path);
		File dir = new File(path);
		File[] files= dir.listFiles();
		for(File f: files){
			DocId++;
			
			maxtfmap = new HashMap<String,Integer>();
			int wordcount =0;
			//int line_count=0;
			//System.out.println(f);
			FileInputStream fileinputstream;
			fileinputstream = new FileInputStream(f);
			title.readTitle(f, DocId);
			@SuppressWarnings("resource")	
			BufferedReader br = new BufferedReader(new InputStreamReader(fileinputstream));
															
			while((line=br.readLine())!=null){
				
				if(!line.startsWith("<")){
					if(!line.isEmpty()){						
						sub = line;
						String regexp ="[\\s,+=?*()/;\\n\t]+|[0-9]+";
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

									if(!stw.IsStopword(term)){
										String lemterm = lemma.getLemma(term).trim();
										lem_term = lemterm.toLowerCase();

										for (int ind = 0; ind < term.length();ind++) {
											ch = term.charAt(ind);
											port.add(ch);
										}
										port.stem();
										String stemterm = port.toString().trim();
										stemword = stemterm.toLowerCase();
										if (!ps.Dictionary.containsKey(lem_term)){
											wordcount++;
											ps.AddtoDict(ps.Dictionary, lem_term, frequency);
											ps.AddtoPostingmap(ps.postingmap, lem_term, DocId);
											//ps.addToPostfile_tf(ps.postfile_tf, lem_term, DocId, tf);											
											ps.addToPosting_TF(ps.Posting_TF, lem_term, DocId, tf);
											maxtfmap.put(lem_term,frequency);
										}
										else{											
											count = ps.getCount(lem_term) + 1;
											int maxcount = ps.getmaxCount(lem_term) + 1;
											ps.AddtoDict(ps.Dictionary, lem_term, count);
											if(!ps.checkDocID(lem_term, DocId)){
												ps.AddtoPostingmap(ps.postingmap, lem_term, DocId);
											}
											//ps.addToPostfile_tf(ps.postfile_tf, lem_term, DocId, tf);
											ps.addToPosting_TF(ps.Posting_TF, lem_term, DocId, tf);
											maxtfmap.put(lem_term,maxcount);
										}		

									/*	if (!st.stemmap.containsKey(stemword)){
											st.addToDictionary(stemword,frequency, st.stemmap);
											st.AddtoStemPostingmap(st.stempostingmap, stemword, DocId);
											st.addStemTopostfile_tf(st.stempostfile_tf, stemword, DocId, tf);
										}
										else{
											st.addToDictionary(stemword,frequency, st.stemmap);
											if(!st.checkStemDocID(stemword, DocId)){
												st.AddtoStemPostingmap(st.stempostingmap, stemword, DocId);	
											}
											st.addStemTopostfile_tf(st.stempostfile_tf, stemword, DocId, tf);
										}*/
									}
								}
							}
						}
					}
				}
			}
			ps.Docwordcount.put(DocId, wordcount);
			Integer maxtf= TfIdfObj.maxtf(maxtfmap);
			maxtfval.put(DocId, maxtf);
		}
		ps.getDocFrequency();
		
		
		query.querylist();
		qobj.getqueryFrequency();
		TfIdfObj.WeightCalculator();
		TfIdfObj.QueryWeightCalculator();
		TfIdfObj.lengthforW1();
		TfIdfObj.lengthforW2();
		TfIdfObj.qlengthforW1();
		TfIdfObj.qlengthforW2();
		cosobj.CosineSimilarityCalculator();
		
		/*for(Entry<Integer, LinkedList<String>> entrymap : QueryParser.queryTerm.entrySet()){
			int q = entrymap.getKey();
			System.out.println(q);
			System.out.println(entrymap.getValue());
		}
		for (Entry<String, LinkedList<Integer>> entrymap : ps.postingmap.entrySet()) {
			String key = entrymap.getKey();	
			if(keys.contains(key)){
			System.out.println(key);
			System.out.println(entrymap.getValue());
			}
		}
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : QueryVector.qPosting_TF.entrySet()) {
			String key = entrymap.getKey();			
			System.out.println(key);
			System.out.println("tf "+entrymap.getValue());
			//ps.getTermCount(key);
		}
		for (Entry<Integer, LinkedList<TreeMap<Integer, Double>>> entrymap : cosobj.cossimval.entrySet()){	
			Integer key = entrymap.getKey();			
					System.out.println("qnum "+key);
					System.out.println(entrymap.getValue());
		}

		for(Entry<Integer,Double> entry : TfIdf.len1.entrySet()){
			int key = entry.getKey();
			double value = entry.getValue();
			System.out.println(key + " "+value);
		}

		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : ps.Posting_TF.entrySet()) {
			String key = entrymap.getKey();			
			System.out.println(key);
			System.out.println("tf "+entrymap.getValue());
			//ps.getTermCount(key);
		}*/
	}
}