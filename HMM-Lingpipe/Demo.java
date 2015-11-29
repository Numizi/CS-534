
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

import com.aliasi.chunk.CharLmHmmChunker;
import com.aliasi.chunk.CharLmRescoringChunker;
import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.NBestChunker;
import com.aliasi.chunk.RescoringChunker;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.tag.Tagging;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Strings;

public class Demo {

	static void trainHMMChunker(String modelFilename, String trainFilename) throws IOException{
		File modelFile = new File(modelFilename);
		File trainFile = new File(trainFilename);
		int numChunkingsRescored = 64;
		int maxNgram = 12;
		int numChars = 256;
		double lmInterpolation = maxNgram; 
		TokenizerFactory factory
			= IndoEuropeanTokenizerFactory.INSTANCE;
		CharLmRescoringChunker chunkerEstimator
			= new CharLmRescoringChunker(factory,numChunkingsRescored,
				maxNgram,numChars,
				lmInterpolation);
		Conll2002ChunkTagParser parser = new Conll2002ChunkTagParser();
		parser.setHandler(chunkerEstimator);
		parser.parse(trainFile);
		AbstractExternalizable.compileTo(chunkerEstimator,modelFile);
	}

	static void printChunk(RescoringChunker<CharLmRescoringChunker> chunker, String line){
		Chunking it = chunker.chunk(line);
        
        
        Set<Chunk> pharses = it.chunkSet();
        Iterator<Chunk> iterator = pharses.iterator();
        
        while (iterator.hasNext()){
        	Chunk chunk = iterator.next();
        	int start = chunk.start();
            int end = chunk.end();
            String phrase = line.substring(start,end);
            
            System.out.println(phrase);
        }
        System.out.println("");
	}    
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String modelFilename = "Conll-train"; //name the model
		String trainFilename = args[0]; //the train file location
		
		File modelFile = new File(modelFilename); //creating the model 
		System.out.println("Training HMM Chunker on data from: " + trainFilename);
		trainHMMChunker(modelFilename, trainFilename);
		System.out.println("Output model written to : " + modelFilename);
		
		@SuppressWarnings("unchecked")
		RescoringChunker<CharLmRescoringChunker> chunker 
			= (RescoringChunker<CharLmRescoringChunker>) AbstractExternalizable.readObject(modelFile);
		String line = "";
		
		InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);
        while (true) {
            System.out.print("\n\nINPUT> ");
            System.out.flush();
            line = bufReader.readLine();
            if (line == null || line.length() < 1 
                || line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit"))
                break;
            
		    Chunking it = chunker.chunk(line);
		        
		    Set<Chunk> pharses = it.chunkSet();//the set of all phrases
		    Iterator<Chunk> iterator = pharses.iterator();// helper to help iterating the set
		    printChunk(chunker,line); //This is for printing the complete chunk 
		        
	    }
			
	}
} 