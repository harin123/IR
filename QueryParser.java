import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class QueryParser {
	static String qline =null;
	int linecount=0;
	private String lem_term;
	static String subline;
	static String[] qwords;
	static String term,token;
	static char ch;
	static int qnum;
	int max_tf=0;
	int qfrequency =1;
	int qtf=1;
	int qcount =0;
	int qwordcount;
	
	//public Map<String,Integer> querymap = new HashMap<String,Integer>();
	static Map<Integer,Integer> qmaxtfval = new HashMap<Integer,Integer>();
	static Map<String, Integer> qmaxtfmap = new HashMap<String,Integer>();
	static LinkedList<String> qwordlist;
	static TreeMap<Integer, LinkedList<String>> queryTerm = new TreeMap<Integer,LinkedList<String>>();
	
	Porter portobj = new Porter();
	Stopwords stopobj =new Stopwords();
	lemmatizer lemobj = new lemmatizer();
	QueryVector qvobj = new QueryVector();
	TfIdf tfobj =new TfIdf();
	//public TreeMap<String,Integer>querymap
	public void querylist() throws IOException {
		stopobj.stoplist();
		String querypath = "/people/cs/s/sanda/cs6322/hw3.queries";
		File file = new File(querypath);
		FileInputStream fileinputstream;
		fileinputstream = new FileInputStream(file);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(fileinputstream));
		
		while((qline=br.readLine())!=null){
			if(qline.startsWith("Q")){
				if(qnum!=0&&qwordcount!=0){
					Integer qmaxtf= tfobj.maxtf(qmaxtfmap);
					qmaxtfval.put(qnum, qmaxtf);
					qmaxtfval.put(18, 1);
					qmaxtfval.put(20, 1);
					//System.out.println(qmaxtfval.get(20));
				}
				//System.out.println(qnum + " " +qmaxtfval.get(qnum));
				qnum++;
				qwordcount =0;
				qmaxtfmap = new HashMap<String,Integer>();
				qwordlist = new LinkedList<String>(); 
			}
			else{
				QueryVector.qDocwordcount.put(qnum, qwordcount);
			}
			if(!qline.endsWith(":")){
				if(!qline.isEmpty()){
					linecount++;
					subline = qline;
					String regexp ="[\\s,()/\\n\t]+|[0-9]+";
					final Pattern p = Pattern.compile(regexp);
					qwords = p.split(subline);
					for(int i=0;i<qwords.length;i++) {
						term = qwords[i];
						if((term.length()>0)) {				
							token=term.replace("-","");								
							if(!stopobj.IsStopword(token)){
								String lemterm = lemobj.getLemma(token).trim();
								lem_term = lemterm.toLowerCase();
								//System.out.println(lem_term);
								//System.out.println("qnum" + qnum);
								
								if (!QueryVector.qDictionary.containsKey(lem_term)){
									qwordcount++;
									qvobj.qAddtoDict(QueryVector.qDictionary, lem_term, qfrequency);
									qvobj.qAddtoPostingmap(QueryVector.qpostingmap, lem_term, qnum);
									//ps.addToPostfile_tf(ps.postfile_tf, lem_term, DocId, tf);											
									qvobj.qaddToPosting_TF(QueryVector.qPosting_TF, lem_term, qnum, qtf);
									qmaxtfmap.put(lem_term,qfrequency);									
									qwordlist.add(lem_term);
									queryTerm.put(qnum, qwordlist);
								}
								else{
									qcount = qvobj.qgetCount(lem_term) + 1;
									int qmaxcount = qvobj.qgetmaxCount(lem_term) + 1;
									qvobj.qAddtoDict(QueryVector.qDictionary, lem_term, qcount);
									if(!qvobj.qcheckqnum(lem_term, qnum)){
										qvobj.qAddtoPostingmap(QueryVector.qpostingmap, lem_term, qnum);
									}
									//ps.addToPostfile_tf(ps.postfile_tf, lem_term, DocId, tf);
									qvobj.qaddToPosting_TF(QueryVector.qPosting_TF, lem_term, qnum, qtf);
									qmaxtfmap.put(lem_term,qmaxcount);
									if(qnum ==18){
										qwordlist.add(lem_term);
										queryTerm.put(qnum, qwordlist);
									}
								}	
								//System.out.println("qwordcount" + qwordcount);
								
								//System.out.println(QueryVector.qDocwordcount.get(qnum));
								//Integer qmaxtf= tfobj.maxtf(qmaxtfmap);
								//qmaxtfval.put(qnum, qmaxtf);
							}
						}
					}
				}
			}
			//Integer qmaxtf= tfobj.maxtf(qmaxtfmap);
			//System.out.println(lem_term);
			//System.out.println("qnum" + qnum);
			//System.out.println("qwordcount" + qwordcount);
			//qmaxtfval.put(qnum, qmaxtf);
		}		
		/*QueryVector.qDocwordcount.put(qnum, qwordcount);
		Integer qmaxtf= tfobj.maxtf(qmaxtfmap);
		qmaxtfval.put(qnum, qmaxtf);
		qvobj.getqueryFrequency();*/
	}
	
}
