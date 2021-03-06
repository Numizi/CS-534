package reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class Reader {
	
	// maps from sentence number to line number to data
	HashMap<Integer, HashMap<Integer, String>> data = new HashMap<Integer, HashMap<Integer, String>>();

	public Reader(String filename) {

		
		// read in sentences
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			System.out.println("file could not be read");
			e.printStackTrace();
		}
		BufferedReader textReader = new BufferedReader(fr);
		
		String currentLine;
		
		try {

			int sentenceIndex = 0;
			int wordIndex = 0;
			
			while ( (currentLine = textReader.readLine()) != null) {

				// empty line signifies end of sentence
				if (currentLine.equals("")) {
					sentenceIndex += 1;
					wordIndex = 0;
					continue;	// don't add empty line
				}

				// create sentence entry for new sentence
				if (!data.containsKey(sentenceIndex)) {
					data.put(sentenceIndex, new HashMap<Integer, String>());
				}

				data.get(sentenceIndex).put(wordIndex, currentLine);
				wordIndex += 1;
			}

		} catch (IOException e) {
			System.out.println("line could not be read");
			e.printStackTrace();
		}
		try {
			textReader.close();
		} catch (IOException e) {
			System.out.println("Could not close BufferedReader.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 */
	public void createData(String regDataFilename, String splitDataFilename, double bootstrapMultiplier) {

		// maps from sentence number to line number to data
		HashMap<Integer, HashMap<Integer, String>> regData = new HashMap<Integer, HashMap<Integer, String>>();
		HashMap<Integer, HashMap<Integer, String>> splitData = new HashMap<Integer, HashMap<Integer, String>>();

		int numExamples = data.size();
		Random rand = new Random();

		// bootstrap numExamples
		for (int i = 0; i < Math.round(numExamples*bootstrapMultiplier); ++i) {
			int randSentenceIndex = rand.nextInt(numExamples);
			HashMap<Integer, String> origSentence = data.get(randSentenceIndex);

			// for regular data, just make copy of randomly chosen sentence
			regData.put(i, origSentence);

			// for new data, choose random part of sentence to split on and copy all data up until split
			int numWords = origSentence.size();

			int randSplit = 1;
			if (numWords > 1) {
				randSplit = rand.nextInt(numWords-1) + 1;		// always at least include first word, don't include last word
			}
			HashMap<Integer, String> newSentence = new HashMap<Integer, String>();
			for (int j = 0; j < randSplit; ++ j) {
				newSentence.put(j, origSentence.get(j));
			}
			splitData.put(i, newSentence);
		}



		try( PrintWriter writer = new PrintWriter(regDataFilename, "UTF-8") ){
			// loop over all new sentences
			for (int i = 0; i < Math.round(numExamples*bootstrapMultiplier); ++i) {
				HashMap<Integer, String> sentence = regData.get(i);
				int numWords = sentence.size();

				// write each line to file
				for (int j = 0; j < numWords; ++ j) {
					writer.write(sentence.get(j));
					writer.write("\n");
				}
				// write empty line after each sentence
				writer.write("\n");
			}
			writer.close();
		} catch (IOException ex) {}



		// // write reg data to file
		// try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
  //             	new FileOutputStream(regDataFilename), "utf-8"))) {

		// 	// loop over all new sentences
		// 	for (int i = 0; i < numExamples*bootstrapMultiplier; ++i) {
		// 		HashMap<Integer, String> sentence = regData.get(i);
		// 		int numWords = sentence.size();

		// 		// write each line to file
		// 		for (int j = 0; j < numWords; ++ j) {
		// 			writer.write(sentence.get(j));
		// 			writer.write("\n");
		// 		}
		// 		// write empty line after each sentence
  //  				writer.write("\n");
  //  			}
		// }

		// // write split data to file
		// try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
  //     			new FileOutputStream(splitDataFilename), "utf-8"))) {
	
		// 	// loop over all new sentences
		// 	for (int i = 0; i < numExamples*bootstrapMultiplier; ++i) {
		// 		HashMap<Integer, String> sentence = splitData.get(i);
		// 		int numWords = sentence.size();

		// 		// write each line to file
		// 		for (int j = 0; j < numWords; ++ j) {
		// 			writer.write(sentence.get(j));
		// 			writer.write("\n");
		// 		}
		// 		// write empty line after each sentence
  //  				writer.write("\n");
  //  			}
		// }


		try( PrintWriter writer = new PrintWriter(splitDataFilename, "UTF-8") ){
			// loop over all new sentences
			for (int i = 0; i < Math.round(numExamples*bootstrapMultiplier); ++i) {
				HashMap<Integer, String> sentence = splitData.get(i);
				int numWords = sentence.size();

				// write each line to file
				for (int j = 0; j < numWords; ++ j) {
					writer.write(sentence.get(j));
					writer.write("\n");
				}
				// write punctuation and empty line after each sentence
				writer.write(". . O\n");
				writer.write("\n");
			}
			writer.close();
		} catch (IOException ex) {}

	}
}
