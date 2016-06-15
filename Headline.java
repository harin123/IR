import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Headline {	

	private String title, headline;
	private InputStream fileinputstream;
	static Map<Integer,String> Headline = (Map<Integer, String>) new HashMap<Integer,String>();
	
	public void readTitle(File file , int docno) throws IOException{
		
		fileinputstream = new FileInputStream(file);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fileinputstream));
		//System.out.println(file.getAbsolutePath());
		while((title=br1.readLine())!=null){
			if(title.startsWith("<TITLE>")){
			headline = br1.readLine().concat(br1.readLine()).replace("</TITLE>", "").replace(".", "");
			//System.out.println(headline);
			Headline.put(docno, headline);
			return;
		}
		}
	}
}