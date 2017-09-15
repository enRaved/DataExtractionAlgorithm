import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.opencsv.CSVReader;

/*This is the starting point of the program. We read the straming data from here and tokenise the data.
 After this we use JARO WINKLER distance to find the similarity between a word and a group. Follow the code in order to understand the flow.
 */

//graph imports
import java.util.HashMap;
import java.util.ArrayList;

import org.jgrapht.*;
public class RandomSelect {
	
	
	public static String[] stopWordsofwordnet = {
			"without", "see", "unless", "due", "also", "must", "might", "like", "]", "[", "}", "{", "<", ">", "?", "\"", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
			"couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
			"he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
			"isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
			"out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
			"that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
			"this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
			"were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
			"why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves",
			"without", "see", "unless", "due", "also", "must", "might", "like", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
			"couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
			"he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
			"isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
			"out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
			"that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
			"this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
			"were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
			"why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves"
			};
	public static ArrayList<String> wordsList = new ArrayList<String>();
	
	public static void main(String[] args){
		
		long startTimeExisting = System.currentTimeMillis();
		readFromCSV();
		long endTimeExisting = System.currentTimeMillis();
		long totsExisting=endTimeExisting-startTimeExisting;
		
		System.out.println("Time taken by Exi Program: "+totsExisting);
		
		long startTime = System.currentTimeMillis();
		readFromCSV();
		long endTime = System.currentTimeMillis();
		long tots=endTime-startTime;
		
		System.out.println("Time taken by My Program: "+tots);
		
		
		
		}
	
	public static void readFromCSV()
	{
		//CSV file read, tokenise and split words
	    String csvFile = "./src/twitterMessages.csv";
	    String[] line;
	    String cvsSplitBy = ",";
	    String s="";
	    
	    try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
	    	PrintStream out = new PrintStream(new FileOutputStream("./src/Lines.txt"));
	    	
	        while ((line = reader.readNext()) != null) {

	            //for a single line
	        	s=line[6].toLowerCase();
	        	
	            s=s.trim().replaceAll("\\s+", " ");
	            String[] words = s.split(" ");

	            for (String word : words) {
	            if(!((word.contains("@")) || (word.contains("http"))))
	            
	            	
	            {
	            	word = word.replaceAll("[#-+.^:,\"?;=!]","");
	            	wordsList.add(word);
	            }
	            }

	            //remove stop words here from the temp list
	            
	            for (int i = 0; i < wordsList.size(); i++) {
	            // get the item as string 
	            	
	            for (int j = 0; j < stopWordsofwordnet.length; j++) {
	            	//System.out.println("RSize: "+wordsList.size());
	            	if(i<wordsList.size())
	            if (stopWordsofwordnet[j].contains(wordsList.get(i))) {
	            wordsList.remove(i);
	            }
	            }
	            }
	            for (String str : wordsList) {

	            //Jaro Winkler
	            
	            out.write(str.getBytes());
	            out.write(" ".getBytes());
	            
	            }
	            
	            //create a Graphatize object
	            //pass the list wordList in it the make graph function
	            Graphatize G= new Graphatize();
	            G.makeGraph(wordsList);
	            
	            
	         
	           // wordsList.removeAll(wordsList);
	            out.write("...........*End of Line*............".getBytes());
	            out.write("\n".getBytes());
	            wordsList.clear();
	            
	            
	            
	        }
	       
	        

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		}
	
public static void readFromCSVExisting(){

	//CSV file read, tokenise and split words
    String csvFile = "./src/twitterMessages.csv";
    String[] line;
    String cvsSplitBy = ",";
    String s="";
    
    try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
    	PrintStream out = new PrintStream(new FileOutputStream("./src/Lines.txt"));
    	
        while ((line = reader.readNext()) != null) {

            //for a single line
        	s=line[6].toLowerCase();
        	
            s=s.trim().replaceAll("\\s+", " ");
            String[] words = s.split(" ");

            for (String word : words) {
            if(!((word.contains("@")) || (word.contains("http"))))
            
            	
            {
            	word = word.replaceAll("[#-+.^:,\"?;=!]","");
            	wordsList.add(word);
            }
            }

            //remove stop words here from the temp list
            
            for (int i = 0; i < wordsList.size(); i++) {
            // get the item as string 
            	
            for (int j = 0; j < stopWordsofwordnet.length; j++) {
            	//System.out.println("RSize: "+wordsList.size());
            	if(i<wordsList.size())
            if (stopWordsofwordnet[j].contains(wordsList.get(i))) {
            wordsList.remove(i);
            }
            }
            }
            for (String str : wordsList) {

            //Jaro Winkler
            
            out.write(str.getBytes());
            out.write(" ".getBytes());
            
            }
            
            //create a Graphatize object
            //pass the list wordList in it the make graph function
            GraphatizeNew G= new GraphatizeNew();
            G.makeGraph(wordsList);
            
            
         
           // wordsList.removeAll(wordsList);
            out.write("...........*End of Line*............".getBytes());
            out.write("\n".getBytes());
            wordsList.clear();
            
            
            
        }
       
        

    } catch (IOException e) {
        e.printStackTrace();
    }
	
}

}
