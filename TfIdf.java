import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TfIdf {
	//int NofDocs = 1400;
	int sum=0;
	int Tcount=0;
	float avgdoc=0;
	Posting postobj = new Posting();
	Map<String, LinkedList<Double>> tfDocmap = new TreeMap <String, LinkedList<Double>>();
	public LinkedList<Double> tf_file;
	public LinkedList<Double> w1_file;
	Map<String, LinkedList<Double>> w1map = new TreeMap <String, LinkedList<Double>>();
	public LinkedList<Double> w2_file;
	Map<String, LinkedList<Double>> w2map = new TreeMap <String, LinkedList<Double>>();
	public LinkedList<Double> wdoc1_file;
	Map<Integer, LinkedList<Double>> wdoc1map = new TreeMap <Integer, LinkedList<Double>>();
	public LinkedList<Double> wdoc2_file;
	Map<Integer, LinkedList<Double>> wdoc2map = new TreeMap <Integer, LinkedList<Double>>();
	public TreeMap<String, Double> IdfDocmap = new TreeMap<String, Double>();
	public TreeMap<String,LinkedList<Double>> TfIdfmap = new TreeMap<String,LinkedList<Double>>();
	public LinkedList<Double> TfIdflist;

	public Map<Integer, Double> termTFval1 = new TreeMap<Integer, Double>();
	public LinkedList<Map<Integer, Double>> termTFvalList1 = new LinkedList<Map<Integer, Double>>();
	public Map<Integer, Double> termTFval2 = new TreeMap<Integer, Double>();
	public LinkedList<Map<Integer, Double>> termTFvalList2 = new LinkedList<Map<Integer, Double>>();
	public static TreeMap<String, LinkedList<Map<Integer, Double>>> wt1 = new TreeMap<String,LinkedList<Map<Integer, Double>>>();
	public static TreeMap<String, LinkedList<Map<Integer, Double>>> wt2 = new TreeMap<String,LinkedList<Map<Integer, Double>>>();
	public static TreeMap<String, Map<Integer, Double>> newwt1 = new TreeMap<String,Map<Integer, Double>>();
	public static TreeMap<String, Map<Integer, Double>> newwt2 = new TreeMap<String,Map<Integer, Double>>();
	public static TreeMap<Integer,Double> len1 = new TreeMap<Integer,Double>();
	public static TreeMap<Integer,Double> len2 = new TreeMap<Integer,Double>();

	public Map<Integer, Double> qtermTFval1 = new TreeMap<Integer, Double>();
	public LinkedList<Map<Integer, Double>> qtermTFvalList1 = new LinkedList<Map<Integer, Double>>();
	public Map<Integer, Double> qtermTFval2 = new TreeMap<Integer, Double>();
	public LinkedList<Map<Integer, Double>> qtermTFvalList2 = new LinkedList<Map<Integer, Double>>();
	public static TreeMap<String, LinkedList<Map<Integer, Double>>> qwt1 = new TreeMap<String,LinkedList<Map<Integer, Double>>>();
	public static TreeMap<String,LinkedList<Map<Integer, Double>>> qwt2 = new TreeMap<String,LinkedList<Map<Integer, Double>>>();
	public static TreeMap<String, Map<Integer, Double>> newqwt1 = new TreeMap<String,Map<Integer, Double>>();
	public static TreeMap<String, Map<Integer, Double>> newqwt2 = new TreeMap<String,Map<Integer, Double>>();
	public static TreeMap<Integer,Double> qlen1 = new TreeMap<Integer,Double>();
	public static TreeMap<Integer,Double> qlen2 = new TreeMap<Integer,Double>();
	
	public void TfCalculator(){
		double tf =0;		
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : Posting.Posting_TF.entrySet()){	
			String key = entrymap.getKey();		
			//System.out.println("key= "+key);
			tf_file = new LinkedList<Double>();
			for (Map<Integer, Integer> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					int value = entry.get(doc);						
					//System.out.println("doc = " + doc);
					//System.out.println("value = " + value);
					tf = 1+Math.log10(value);
					//System.out.println("tf "+ tf);
					tf_file.add(tf);
				}
			}	
			tfDocmap.put(key, tf_file);
			//System.out.println("tf "+tfDocmap.get(key));
		}
	}	

	public void IdfCalculator(){
		double Idf =0;
		for (Entry<String, Integer> entrymap : Posting.DocFreq.entrySet()){	
			String key = entrymap.getKey();			
			int value = entrymap.getValue();
			Idf = Math.log10(Tokenise.DocId/value);
			IdfDocmap.put(key, Idf);
		}
	}

	public void TfIdfCalculator(){
		double TfIdf;

		for (Entry<String, LinkedList<Double>> entrymap : tfDocmap.entrySet()){	
			String key = entrymap.getKey();
			//System.out.println (key);
			TfIdflist = new LinkedList<Double>();
			LinkedList<Double> tfvalues = entrymap.getValue();
			for(double val :tfvalues){
				TfIdf = val * IdfDocmap.get(key);
				/*System.out.println("val "+ val);
				System.out.println("idf "+IdfDocmap.get(key));
				System.out.println("tfidf " + TfIdf);*/
				TfIdflist.add(TfIdf);
			}	
			TfIdfmap.put(key, TfIdflist);
			//System.out.println("Tfidf "+TfIdfmap.get(key));
		}
	}

	public void WeightCalculator(){
		Double w1 = 0.0;
		Double w2 = 0.0;
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : Posting.Posting_TF.entrySet()){	
			String key = entrymap.getKey();	
			termTFvalList1 = new LinkedList<Map<Integer, Double>>();
			termTFvalList2 = new LinkedList<Map<Integer, Double>>();
			termTFval1= new TreeMap<Integer, Double>();
			termTFval2= new TreeMap<Integer, Double>();
			//w1_file = new LinkedList<Double>();
			
			for (Map<Integer, Integer> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					int value = entry.get(doc);
					w1=(0.4+0.6*Math.log(value+0.5)/Math.log(Tokenise.maxtfval.get(doc)+10))*(Math.log(Tokenise.DocId/Posting.DocFreq.get(key))/Math.log(Tokenise.DocId));
					w2=(0.4+0.6*(value/(value+0.5+1.5*(Posting.Docwordcount.get(doc)/avgdoclen())))*Math.log(Tokenise.DocId/Posting.DocFreq.get(key))/Math.log(Tokenise.DocId));
					//w1_file.add(w1);
				/*	System.out.println("val "+value);
					System.out.println("doclen "+Posting.Docwordcount.get(doc));
					System.out.println("df "+Posting.DocFreq.get(key));
					System.out.println(Tokenise.DocId);
					System.out.println(doc);
					System.out.println("w2 "+w2);*/
					
					termTFval1.put(doc, w1);					
					termTFval2.put(doc, w2);
					
				}
			}
			termTFvalList1.add(termTFval1);
			termTFvalList2.add(termTFval2);
			//w1map.put(key, w1_file);
			wt1.put(key, termTFvalList1);
			wt2.put(key, termTFvalList2);
			newwt1.put(key, termTFval1);
			newwt2.put(key, termTFval2);
			//System.out.println(key);
			//System.out.println(Posting.postingmap.get(key));
		}
	}

	public void WeightCalculator2(){
		Double w2 = 0.0;
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : Posting.Posting_TF.entrySet()){	
			String key = entrymap.getKey();	
			termTFvalList2 = new LinkedList<Map<Integer, Double>>();
			for (Map<Integer, Integer> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					//wdoc2_file = new LinkedList<Double>();
					int value = entry.get(doc);
					w2=(0.4+0.6*(value/(value+0.5+1.5*(Posting.Docwordcount.get(doc)/avgdoclen())))*Math.log(Tokenise.DocId/Posting.DocFreq.get(key))/Math.log(Tokenise.DocId));
					/*System.out.println("val "+value);
					System.out.println("doclen "+Posting.Docwordcount.get(doc));
					System.out.println("df "+Posting.DocFreq.get(key));
					System.out.println(Tokenise.DocId);
					System.out.println(doc);
					System.out.println("w2 "+w2);
					w2_file.add(w2);
					wdoc2_file.add(w2);*/
					termTFval2.put(doc, w2);
					termTFvalList2.add(termTFval2);
				}
			}
			wt2.put(key, termTFvalList2);
			//System.out.println(key);
			//System.out.println(wt2.get(key));
		}
	}

	public void lengthforW1(){
		Double len =0.0;
		for (Entry<String, LinkedList<Map<Integer, Double>>> entrymap : wt1.entrySet()){
			String key = entrymap.getKey();	
			for (Map<Integer, Double> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					Double value = entry.get(doc);
					len = Math.sqrt(value);
					addtolen1(doc, len);
					//System.out.println(len1.get(doc));
				}
			}
		}
	}
	
	public void lengthforW2(){
		Double len =0.0;
		for (Entry<String, LinkedList<Map<Integer, Double>>> entrymap : wt2.entrySet()){
			String key = entrymap.getKey();	
			for (Map<Integer, Double> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					Double value = entry.get(doc);
					len = Math.sqrt(value);
					addtolen2(doc, len);
				}
			}
		}
	}

	public double avgdoclen(){
		for (int values : Posting.Docwordcount.values()){
			sum=sum+values;
		}
		for(int i : Posting.Dictionary.values()){
			Tcount  = Tcount + i;
		}
		return avgdoc = sum/Tokenise.DocId;
	}
	
	public void addtolen1(int doc,double val){
		if(len1.containsKey(doc)){
			double valold=len1.get(doc);
			double valnew = valold + val;
			len1.put(doc, valnew);
		}
		else{
			len1.put(doc, val);
		}
	}
	
	public void addtolen2(int doc,double val){
		if(len2.containsKey(doc)){
			double valold=len2.get(doc);
			double valnew = valold + val;
			len2.put(doc, valnew);
		}
		else{
			len2.put(doc, val);
		}
	}
	
	public int maxtf(Map<String, Integer> qmaxtfmap){
		int maxValueInMap=(Collections.max(qmaxtfmap.values()));
		return maxValueInMap;
	}
	
	public void QueryWeightCalculator(){
		Double qw1 = 0.0;
		Double qw2 = 0.0;
		for (Entry<String, LinkedList<Map<Integer, Integer>>> entrymap : QueryVector.qPosting_TF.entrySet()){	
			String key = entrymap.getKey();	
			qtermTFvalList1 = new LinkedList<Map<Integer, Double>>();
			qtermTFvalList2 = new LinkedList<Map<Integer, Double>>();
			qtermTFval1= new TreeMap<Integer, Double>();
			qtermTFval2= new TreeMap<Integer, Double>();
			//w1_file = new LinkedList<Double>();
			for (Map<Integer, Integer> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					int value = entry.get(doc);
					//System.out.println("val "+ value);
					qw1=(0.4+0.6*Math.log(value+0.5)/Math.log(QueryParser.qmaxtfval.get(doc)+10))*(Math.log(QueryParser.qnum/QueryVector.qDocFreq.get(key))/Math.log(QueryParser.qnum));
					qw2=(0.4+0.6*(value/(value+0.5+1.5*(QueryVector.qDocwordcount.get(doc)/1)))*Math.log(QueryParser.qnum/QueryVector.qDocFreq.get(key))/Math.log(QueryParser.qnum));
					//w1_file.add(w1);		
					/*System.out.println("val "+value);
					System.out.println("doclen "+QueryVector.qDocwordcount.get(doc));
					System.out.println("df "+QueryVector.qDocFreq.get(key));
					System.out.println(QueryParser.qnum);
					System.out.println("qnum "+doc);*/
					
					qtermTFval1.put(doc, qw1);
					qtermTFval2.put(doc, qw2);					
				}
			}
			qtermTFvalList1.add(qtermTFval1);
			qtermTFvalList2.add(qtermTFval2);
			//w1map.put(key, w1_file);
			qwt1.put(key, qtermTFvalList1);
			qwt2.put(key, qtermTFvalList2);
			newqwt1.put(key, qtermTFval1);
			newqwt2.put(key, qtermTFval2);
			/*System.out.println(key);
			System.out.println(qwt1.get(key));
			System.out.println(QueryVector.qpostingmap.get(key));*/
		}
	}
	
	public void qlengthforW1(){
		Double len =0.0;
		for (Entry<String, LinkedList<Map<Integer, Double>>> entrymap : qwt1.entrySet()){
			String key = entrymap.getKey();	
			for (Map<Integer, Double> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					Double value = entry.get(doc);
					len = Math.sqrt(value);
					addtoqlen1(doc, len);
					//System.out.println("doc "+doc + " "+qlen1.get(doc));
				}
			}
		}
	}	
	
	public void qlengthforW2(){
		Double len =0.0;
		for (Entry<String, LinkedList<Map<Integer, Double>>> entrymap : qwt2.entrySet()){
			String key = entrymap.getKey();	
			for (Map<Integer, Double> entry : entrymap.getValue()) {
				Set<Integer> Docs = entry.keySet();										
				for (int doc : Docs) {
					Double value = entry.get(doc);
					len = Math.sqrt(value);
					addtoqlen2(doc, len);
				}
			}
		}
	}
	
	public void addtoqlen1(int doc,double val){
		if(qlen1.containsKey(doc)){
			double valold=qlen1.get(doc);
			double valnew = valold + val;
			qlen1.put(doc, valnew);
		}
		else{
			qlen1.put(doc, val);
		}
	}
	
	public void addtoqlen2(int doc,double val){
		if(qlen2.containsKey(doc)){
			double valold=qlen2.get(doc);
			double valnew = valold + val;
			qlen2.put(doc, valnew);
		}
		else{
			qlen2.put(doc, val);
		}
	}
	
}
