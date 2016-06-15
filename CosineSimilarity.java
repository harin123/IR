import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

public class CosineSimilarity {
	static boolean DES =false;
	TfIdf tfobj = new TfIdf();
	Stemmer st1 = new Stemmer();
	public TreeMap<String, LinkedList<Map<Integer, Double>>> wt1,wt2;
	public TreeMap<String, Map<Integer, Double>> newwt1, newwt2;
	public TreeMap<Integer,Double> len1,len2;
	public TreeMap<String, LinkedList<Map<Integer, Double>>> qwt1 ,qwt2;
	public TreeMap<String, Map<Integer, Double>> newqwt1, newqwt2;
	public TreeMap<Integer,Double> qlen1,qlen2 ;
	public LinkedList<TreeMap<Integer,Double>> cossimlist;
	public TreeMap<Integer,Double> cossimmap;
	public TreeMap<Integer,Map<Integer,Double>> cossimval = new TreeMap<Integer,Map<Integer,Double>>();
	public LinkedList<TreeMap<Integer,Double>> cossimlist2;
	public TreeMap<Integer,Double> cossimmap2;
	public TreeMap<Integer,Map<Integer,Double>> cossimval2 = new TreeMap<Integer,Map<Integer,Double>>();


	public void CosineSimilarityCalculator(){
		/*getQueryVector1();
		System.out.println("-----------------------------------------");
		getQueryVector2();
		System.out.println("-----------------------------------------");*/
		for(Entry<Integer, LinkedList<String>> map : QueryParser.queryTerm.entrySet()){
			cossimlist = new LinkedList<TreeMap<Integer,Double>>();
			cossimlist2 = new LinkedList<TreeMap<Integer,Double>>();
			cossimmap = new TreeMap<Integer,Double>();
			cossimmap2 = new TreeMap<Integer,Double>();
			int q = map.getKey();
			LinkedList<String> terms = QueryParser.queryTerm.get(q);	
			System.out.println("Query - " +q);
			System.out.println("-----------------------------------------");
			for(int docno=1;docno<=Tokenise.DocId;docno++){
				double numerator = 0.0;
				double denominator = 0.0;
				double cossimi = 0.0;
				double numerator2 = 0.0;
				double denominator2 = 0.0;
				double cossimi2 = 0.0;
				for(String term : terms){
					numerator = numerator + (getwt1value(term,docno) * getqwt1value(term, q));
					denominator = getlen1(docno) * getqlen1(q);
					cossimi = numerator/denominator;
					
					numerator2 = numerator2 + (getwt2value(term,docno) * getqwt2value(term, q));
					denominator2 = getlen2(docno) * getqlen2(q);
					cossimi2 = numerator2/denominator2;
				}
				cossimmap.put(docno, cossimi);
				//cossimlist.add(cossimmap);
				//System.out.println(cossimmap.get(docno));
				cossimmap2.put(docno, cossimi2);
				//System.out.println(cossimmap2.get(docno));
				//cossimlist2.add(cossimmap2);
				/*System.out.println(term);
			//numerator = numerator + (getwt1value(term,q) * getqwt1value(term, q));
			//System.out.println(getwt1value(term,q));
			//System.out.println(term + "----" + q +"---"+getqwt1value(term, q));
			//getqwt1value(term,q);
			//System.out.println("qnum--"+q);*/		
			}
			cossimval.put(q, cossimmap);
			System.out.println("Scores Using Weight 1");
			System.out.println(cossimval.get(q));			
			cossimval2.put(q, cossimmap2);
			System.out.println("Scores Using Weight 2");
			System.out.println(cossimval2.get(q));
			System.out.println("Using Weight 1");
			System.out.println("-----------------------------------------");
			Map<Integer, Double> sortedMapDesc1 = st1.sortByComparator1(cossimmap, DES);
			Iterator<Entry<Integer, Double>> it1 = sortedMapDesc1.entrySet().iterator();
	/*		for(int j=1;j<=5 &&it1.hasNext();j++){
				Entry<Integer, Double> data = it1.next();
				System.out.println(data.getKey());
			}*/
			//System.out.println("Top 5 Documents for Query "+ q +" and the Document Vector");
			System.out.println("Query Weight of : " + q + " using Weighing Scheme 1");
			getQueryVector1(q);
			System.out.println("Query Weight of : " + q + " using Weighing Scheme 2");
			getQueryVector2(q);
			
			for(int j=1;j<=5 &&it1.hasNext();j++){
				Entry<Integer, Double> data = it1.next();
				int docid = data.getKey();
				double score = data.getValue();
				System.out.println("Rank " + j +" External DocumentId : "+ docid);
				//getHeadline(docid);
				System.out.println("Score : ");
				System.out.println(score);
				System.out.println("Document Vector of Document " + docid);
				getDocumentVector(docid);
				System.out.println("Headline of Document");
				System.out.println(Headline.Headline.get(docid));				
			}
			System.out.println("Using Weight 2");
			System.out.println("-----------------------------------------");
			Map<Integer, Double> sortedMapDesc2 = st1.sortByComparator1(cossimmap2, DES);
			Iterator<Entry<Integer, Double>> it2 = sortedMapDesc2.entrySet().iterator();
			for(int k=1;k<=5 &&it1.hasNext();k++){
				Entry<Integer, Double> data = it2.next();
				int docid = data.getKey();
				double score = data.getValue();
				System.out.println("Rank " + k +" External DocumentId : "+ docid);
				System.out.println("Score : ");
				System.out.println(score);
				System.out.println("Document Vector of Document " + docid);
				getDocumentVector(docid);
				System.out.println("Headline of Document");
				System.out.println(Headline.Headline.get(docid));
			}
		}
	}


	public double getlen1(int doc){
		double val = 0.0;
		for(Entry<Integer,Double> entry : TfIdf.len1.entrySet()){
			int key = entry.getKey();
			double value = entry.getValue();
			//System.out.println(key);
			//System.out.println(value);
			if(key==doc){
				val =value;
			}
		}	
		return val;
	}

	public double getqlen1(int doc){
		double val =0.0;
		for(Entry<Integer,Double> entry : TfIdf.qlen1.entrySet()){
			int key = entry.getKey();
			double value = entry.getValue();
			if(key ==doc)
				val = value;
		}
		return val;
	}

	public Double getwt1value(String term, int doc){		
		Double value =0.0;		
		for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.wt1.entrySet()){
			String key = docentry.getKey();
			if(key.toLowerCase().trim().equals(term.toLowerCase().trim())){
				for (Map<Integer, Double> entry : docentry.getValue()) {
					Set<Integer> Docs = entry.keySet();

					for(int d:Docs){
						if(d==doc){							
							value = entry.get(d);
							//System.out.println(key+ " "+d);
							//System.out.println(value);
						}
					}
				}
			}
		}
		return value;
	}

	public Double getqwt1value(String term, int doc){
		Double value =0.0;
		//System.out.println("doc "+ doc);
		for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.qwt1.entrySet()){
			String key = docentry.getKey();
			if(doc==18){
				for (Map<Integer, Double> entry : docentry.getValue()) {
					Set<Integer> Docs = entry.keySet();
					for(int d:Docs){
						if(d==doc){							
							value = entry.get(doc);
							//System.out.println(key +" qnum " + d + " doc " + doc +"val "+ value);
							//return value;
						}
					}
				}
			}
			else{
				if(key.equals(term)){
					//System.out.println(term + " doc "+ doc);
					for (Map<Integer, Double> entry : docentry.getValue()) {
						Set<Integer> Docs = entry.keySet();
						//System.out.println("Docs" + Docs);
						for(int d:Docs){
							//System.out.println(key +" qnum " + d + " doc " + doc);
							if(d==doc){							
								value = entry.get(doc);
								//System.out.println(key+ " "+doc);
								//System.out.println(value);
							}
						}
					}
				}
			}
		}
		return value;
	}

	public double getlen2(int doc){
		double val = 0.0;
		for(Entry<Integer,Double> entry : TfIdf.len2.entrySet()){
			int key = entry.getKey();
			double value = entry.getValue();
			//System.out.println(key);
			//System.out.println(value);
			if(key==doc){
				val =value;
			}
		}	
		return val;
	}

	public double getqlen2(int doc){
		double val =0.0;
		for(Entry<Integer,Double> entry : TfIdf.qlen2.entrySet()){
			int key = entry.getKey();
			double value = entry.getValue();
			if(key ==doc)
				val = value;
		}
		return val;
	}

	public Double getwt2value(String term, int doc){		
		Double value =0.0;		
		for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.wt2.entrySet()){
			String key = docentry.getKey();
			if(key.toLowerCase().trim().equals(term.toLowerCase().trim())){
				for (Map<Integer, Double> entry : docentry.getValue()) {
					Set<Integer> Docs = entry.keySet();
					for(int d:Docs){
						if(d==doc){							
							value = entry.get(d);
							//System.out.println(key+ " "+d);
							//System.out.println(value);
						}
					}
				}
			}
		}
		return value;
	}

	public Double getqwt2value(String term, int doc){
		Double value =0.0;
		//System.out.println("doc "+ doc);
		for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.qwt2.entrySet()){
			String key = docentry.getKey();
			if(doc==18){
				for (Map<Integer, Double> entry : docentry.getValue()) {
					Set<Integer> Docs = entry.keySet();
					for(int d:Docs){
						if(d==doc){							
							value = entry.get(doc);
							//System.out.println(key +" qnum " + d + " doc " + doc +"val "+ value);
							//return value;
						}
					}
				}
			}
			else{
				if(key.equals(term)){
					//System.out.println(term + " doc "+ doc);
					for (Map<Integer, Double> entry : docentry.getValue()) {
						Set<Integer> Docs = entry.keySet();
						//System.out.println("Docs" + Docs);
						for(int d:Docs){
							//System.out.println(key +" qnum " + d + " doc " + doc);
							if(d==doc){							
								value = entry.get(doc);
								//System.out.println(key+ " "+doc);
								//System.out.println(value);
							}
						}
					}
				}
			}
		}
		return value;
	}

/*	
	public void getQueryVector1(){
		System.out.println("Query Vector under Weighing scheme 1");
		Vector<Double> Queryvector ;
		for(Entry<Integer, LinkedList<String>> map : QueryParser.queryTerm.entrySet()){
			int q = map.getKey();
			LinkedList<String> terms = QueryParser.queryTerm.get(q);
			Queryvector = new Vector<Double>(1,1);
			for(String term : terms){
				for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.qwt1.entrySet()){
					String key = docentry.getKey();
					if(term.equals(key)){
						for (Map<Integer, Double> entry : docentry.getValue()) {
							Set<Integer> Docs = entry.keySet();
							for(int d:Docs){
								if(d==q){									
									Queryvector.addElement(entry.get(d));					
								}
							}
						}
					}
				}
			}
			Enumeration<Double> vEnum = Queryvector.elements();
			System.out.println("Query " + q);
		    while(vEnum.hasMoreElements())
		    System.out.print(vEnum.nextElement() + " ");
		    System.out.println();
		}
	}
	public void getQueryVector2(){
		System.out.println("Query Vector under Weighing scheme 2");
		Vector<Double> Queryvector ;
		for(Entry<Integer, LinkedList<String>> map : QueryParser.queryTerm.entrySet()){
			int q = map.getKey();
			LinkedList<String> terms = QueryParser.queryTerm.get(q);
			Queryvector = new Vector<Double>(1,1);
			for(String term : terms){
				for(Entry<String, LinkedList<Map<Integer, Double>>> docentry : TfIdf.qwt2.entrySet()){
					String key = docentry.getKey();
					if(term.equals(key)){
						for (Map<Integer, Double> entry : docentry.getValue()) {
							Set<Integer> Docs = entry.keySet();
							for(int d:Docs){
								if(d==q){									
									Queryvector.addElement(entry.get(d));					
								}
							}
						}
					}
				}
			}
			Enumeration<Double> vEnum = Queryvector.elements();
			System.out.println("Query " + q);
		    while(vEnum.hasMoreElements())
		    System.out.print(vEnum.nextElement() + ", ");
		    System.out.println();
		}
	}
	
	public void W1Top5Doc(){

		for (Entry<Integer, LinkedList<TreeMap<Integer, Double>>> entrymap : cossimval.entrySet()){	
			Integer key = entrymap.getKey();
			for (Map<Integer, Double> entry : entrymap.getValue()) {
				Map<Integer, Double> sortedMapDesc = st1.sortByComparator1(entry, DES);
				Iterator<Entry<Integer, Double>> it1 = sortedMapDesc.entrySet().iterator();
				for(int j=0;j<5 &&it1.hasNext();j++){
					System.out.println(it1.next().getKey());
				}
			}
		}

	}*/
	
	public void getDocumentVector(int doc){
		Vector<Double> docVector = new Vector<Double>(1,1);
		for (Entry<String, Map<Integer, Double>> entrymap : TfIdf.newwt1.entrySet()){	
			//String key = entrymap.getKey();
			Map<Integer, Double> submap = entrymap.getValue();
			for(Entry<Integer, Double> docvec : submap.entrySet()){
				int doc1 = docvec.getKey();
				if(doc1==doc){
					docVector.addElement(submap.get(doc1));
				}
			}
		}
		Enumeration<Double> vEnum = docVector.elements();
		
		while(vEnum.hasMoreElements())
		    System.out.print(vEnum.nextElement() + " ");
		    System.out.println();
	}
	
	public void getQueryVector1(int qnum){
		Vector<Double> Queryvector = new Vector<Double>(1,1);
		for (Entry<String, Map<Integer, Double>> entrymap : TfIdf.newqwt1.entrySet()){	
			//String key = entrymap.getKey();
			Map<Integer, Double> submap = entrymap.getValue();
			for(Entry<Integer, Double> qvec : submap.entrySet()){
				int doc1 = qvec.getKey();
				if(doc1==qnum){
					Queryvector.addElement(submap.get(doc1));
				}
			}
		}
		Enumeration<Double> vEnum = Queryvector.elements();
		
		while(vEnum.hasMoreElements())
		    System.out.print(vEnum.nextElement() + " ");
		    System.out.println();
	
	}
	
	public void getQueryVector2(int qnum){
		Vector<Double> Queryvector = new Vector<Double>(1,1);
		for (Entry<String, Map<Integer, Double>> entrymap : TfIdf.newqwt2.entrySet()){	
			//String key = entrymap.getKey();
			Map<Integer, Double> submap = entrymap.getValue();
			for(Entry<Integer, Double> qvec : submap.entrySet()){
				int doc1 = qvec.getKey();
				if(doc1==qnum){
					Queryvector.addElement(submap.get(doc1));
				}
			}
		}
		Enumeration<Double> vEnum = Queryvector.elements();
		
		while(vEnum.hasMoreElements())
		    System.out.print(vEnum.nextElement() + " ");
		    System.out.println();
	
	}

}
