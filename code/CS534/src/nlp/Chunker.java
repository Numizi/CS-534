package nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class Chunker {


	public static ArrayList<String[]> chunks=new ArrayList<String[]>();
	
	public static ArrayList<String[]> chunk(ArrayList<String[]> tokens, ArrayList<String[]> tags) throws IOException {
		InputStream is = new FileInputStream("models/en-chunker.bin");
		ChunkerModel cModel = new ChunkerModel(is);	 
		ChunkerME chunker = new ChunkerME(cModel);
	 
		for(int i=0;i<tokens.size();i++) {	 
			String result[] = chunker.chunk(tokens.get(i),tags.get(i));
			chunks.add(result);
		}
		return chunks;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ArrayList<String[]> tokens=new ArrayList<String[]>();
			String[] sentence={"I","like","surface","book","!"};
			tokens.add(sentence);
			ArrayList<String[]> tags=POStagger.tag(tokens);
			ArrayList<String[]> chunks=chunk(tokens,tags);
			for(int i=0;i<chunks.size();i++){
				for(int j=0;j<chunks.get(i).length;j++){
					System.out.println(tokens.get(i)[j]+" "+tags.get(i)[j]+" "+chunks.get(i)[j]);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
