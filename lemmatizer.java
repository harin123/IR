import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class lemmatizer {
	static lemmatizer m_lemmatizer;
	Properties props;
	StanfordCoreNLP pipeline;

	 lemmatizer() {
		props = new Properties();
		props.put("annotators", "tokenize,ssplit, pos,  lemma");
		pipeline = new StanfordCoreNLP(props, false);
	}

	public static lemmatizer getInstance() {
		if (m_lemmatizer == null) {
			m_lemmatizer = new lemmatizer();
		}
		return m_lemmatizer;

	}

	public String getLemma(String text) {
		String lemma = "";
		Annotation document = pipeline.process(text);
		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				lemma += token.get(LemmaAnnotation.class) + " ";
			}
		}
		return lemma;
	}
	/*public static String replaceSpecialCharacters(StringBuffer str)
	{
		
	   String strbuff;
		strbuff = str.toString().replaceAll("<.*?>", "");
		strbuff = strbuff.replaceAll("\\.", "");
		strbuff = strbuff.replaceAll("'s", "");
		
		strbuff = strbuff.replaceAll("[0-9]+", "");
		strbuff = strbuff.replaceAll("[*+^:,?';=%#&~`$!@_)/(}{]", "\t");
		strbuff = strbuff.replaceAll("-", "\t");
		
		return strbuff;
		
		
	}*/
	public static String parseFiles(File path) throws IOException
	{
		String tok, final_str=null;StringBuffer buff = new StringBuffer();
		Scanner sc= null;
		try{
			//for(int index=0;index<file.length;index++)
			//{
				sc = new Scanner(new BufferedReader(new FileReader(path)));
				while(sc.hasNext())
				{
					
					tok = sc.next();
					
					if(tok.equalsIgnoreCase("<TEXT>"))
					{
						while(sc.hasNext())
						{
							tok = sc.next();
							if(!(tok.equalsIgnoreCase("</TEXT>"))){		
								buff.append("\t" + tok);
							}
							else
								break;
						}
						break;
					}
					else
					{
						continue;
					}
					
				}
				
			//} 
			
			
			
			//HashMap<String, Integer> map = hashMapUpdation(buff);
			//System.out.println("buff="+buff);

			
		 }
		
		finally
		{
			if(sc!=null)
			{
				sc.close();
			}
		}
		//final_str = replaceSpecialCharacters(buff);
		return final_str;

	}
	public static String lemmaWrapper(String token) {
		return (lemmatizer.getInstance().getLemma(token));
	}
}
