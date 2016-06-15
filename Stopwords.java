import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stopwords {	
	static String stopline =null;
	Posting pst = new Posting();
	public void stoplist() throws IOException {
	//public static void main(String [] args)throws IOException{
		String Stoppath = "/people/cs/s/sanda/cs6322/resourcesIR/stopwords";
		File file = new File(Stoppath);
		FileInputStream fileinputstream;
		fileinputstream = new FileInputStream(file);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(fileinputstream));
		while((stopline=br.readLine())!=null){
			//System.out.println (stopline);
			pst.addtoStopword(stopline);
		}
	}
	public boolean IsStopword(String word) {
		
		if(pst.Stopwords.contains(word)){
			return true;
		}
		return false;		
	}
}
//}
