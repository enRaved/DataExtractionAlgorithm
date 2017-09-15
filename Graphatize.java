import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.opencsv.CSVReader;

/**
 We use graphs for doing the below:
This class implements the way to take in (the apparent) streaming data from TWITTER data set and assigns an unknown word to a relevant dictionary after predicting to which category it would belong.
 * 
 */

public class GraphatizeNew {
   // private static final Object[] WeightArray = null;
	Graph g= new Graph();
    ArrayList<String> WeightArray= new ArrayList <String>();
    void makeGraph(ArrayList<String> WordsOfALine) throws IOException{
    	
    	ArrayList<String> A= new ArrayList<String>();
    	A.addAll(WordsOfALine);
    	
    	 Vertex[] v= new Vertex[A.size()];
         for(int i=0; i<A.size();i++){
         	v[i]=new Vertex(A.get(i));
         	
         	g.addVertex(v[i], false);
         }
         
   ArrayList<String> WeightArray= new ArrayList<String>();
   
  /* int i1=0;
        for(String word: A) {
        	if(assignGroup(word)!="")
    WeightArray.add(assignGroup(word)); // change this
    i1++;}*/
   
   //assignGroup("Extortion");
   WeightArray.add(assignGroup("worrying"));
    	
    System.out.println("Weightarray size:"+WeightArray.toString());
         	Random rn= new Random();
         	
         	
         	JaroWinkler J= new JaroWinkler();
         	
         for(int i=0;i<v.length;i++) //assign weights and add edges to graph 
         	for(int j=i+1;j<v.length;j++){
         		//System.out.println("Baby between: "+i+"and "+j+ "is "+J.getSimilarity(v[i].getLabel(), v[j].getLabel()));
         		g.addEdge(v[i], v[j], J.getSimilarity(v[i].getLabel(), v[j].getLabel()));
         	}
         		
         
         ArrayList<Edge> N= new ArrayList<Edge>(); //list of neighbors
       
         for(int i=0;i<v.length;i++){ //for all vertices
         	
         		N.addAll(v[i].getNeighbors());
         	double BestWeight=-100;
         	String BestNeighbor= new String(); //best neighbor= neighbor with highest weight
         	 BestWeight=-100;
         		for(int j=0;j<N.size();j++){ //get the best neighbor for each vertex
         			if(BestWeight<N.get(j).getWeight()){
         				BestWeight=N.get(j).getWeight();
         				BestNeighbor=N.get(j).toString();
         				}
         		}
         	System.out.println("For  "+v[i]+" the best neighbor is: "+BestNeighbor+"with weight"+BestWeight+"Group: "+WeightArray.get(0));
         	N.removeAll(v[i].getNeighbors());
         }
       	
         
        // System.out.println("Graph"+g.getEdges());
         System.out.println("\n\n");
         
    }
    
   public static String assignGroup(String word) throws IOException{
	   
    	ArrayList<String> bullyRandWords = new ArrayList<String>();
    	ArrayList<String> PhilRandWords = new ArrayList<String>();
    	ArrayList<String> listToRandom = new ArrayList<String>();
    	ArrayList<String> randomFinalLine = new ArrayList<String>();
    	
    	//Updated Lists for Dictionaries after calculating threshold
    	ArrayList<String> updatedBullyList = new ArrayList<String>();
    	ArrayList<String> updatedPhilList = new ArrayList<String>();
    	ArrayList<String> updatedListToRandomise = new ArrayList<String>();
    	ArrayList<String> updatedRandomFinalLine = new ArrayList<String>();
    	
    	File bullyFile= new File("./src/bullying.csv");
    	File philanthrophyFile= new File("./src/philanthrophy.csv");
    	
    	
    	bullyRandWords=randomizeInput(bullyFile);
    	PhilRandWords=randomizeInput(philanthrophyFile);
    	

    	listToRandom.addAll(bullyRandWords); //concatenating all lists together //list for threshold
    	listToRandom.addAll(PhilRandWords); //ready to randomise 
    	//randomFinalLine=randomizeFinalInput(listToRandom); //creating a random final Line
    	
    	//create new lists of bullylists and phillists which donot
    	//contain words (which are used for calculating threshold)
    	updatedBullyList=newList(bullyFile, bullyRandWords);
    	updatedPhilList=newList(philanthrophyFile, PhilRandWords);
    	updatedListToRandomise.addAll(updatedBullyList);
    	updatedListToRandomise.addAll(updatedPhilList);
    	updatedRandomFinalLine=randomizeFinalInput(updatedListToRandomise);
    	
    	
    	//Take one word from the stream of words one by one
    	//Calculate threshold by comparing the Jaro Winkler to all words in randomFinalLine
    	//Set threshold
    	//Randomise the remaining inputs of the file and create a 
    	//new array list containing list.size()/4 words 
    	//compare Jaro Winkler for each word in the newly created array list and stop if 
    	//the Jaro Winkler is greater than the threshold
    	//Assign that word to the group to which the existing word belongs.
    	
    	//CSVReader reader = new CSVReader(new FileReader("./src/words.txt"));
    	
    	/* from here
    	String[] line= {""};
    	line[0]=WordsOfALine.get(0);
    	//line=reader.readNext();
    	String best=line[0];
    	System.out.println(best);
    	*///to here
    	String[] line= {""};
    	line[0]=word;
    	String best="435";
    	
    	JaroWinkler J=new JaroWinkler();
    
    		line[0]=word;
    		int index=0;
    		
    		//compare one word from words.txt to the each word in randomFinalLine
    		while(index<randomFinalLine.size()){
    			
    		if(J.getSimilarity(line[0], randomFinalLine.get(index))>J.getSimilarity(best, randomFinalLine.get(index)))
    			best=randomFinalLine.get(index);
    			index++;
    		}
    		
    		 String GroupOf=new String();
    		 int i=0;
    	
    		 int flag=0;
    		 
    		for(i=index;i<updatedRandomFinalLine.size();i++){  //compare J(updatedRandomFinalLine&line[0]) and J(best&line[0])
    			if(J.getSimilarity(best, line[0])<J.getSimilarity(updatedRandomFinalLine.get(i), line[0])){
    				flag=1;
    				if(updatedBullyList.contains(updatedRandomFinalLine.get(i))) {GroupOf="Bullying";System.out.println("Ravee "+i);}
    				
    				else if(updatedPhilList.contains(updatedRandomFinalLine.get(i))) {GroupOf="Philanthrophy";System.out.println("Sahil: "+i);}
    					
    				System.out.println("MY final word : "+line[0]+" should belong to the group of "+updatedRandomFinalLine.get(i)+ " GROUP: "+GroupOf);
    				//break;
    				
    				}	
    			
    		} //System.out.println("The i is:"+J.getSimilarity(updatedRandomFinalLine.get(i-1), line[0])); 
    		
    	//	return(J.getSimilarity(updatedRandomFinalLine.get(i-1), line[0]));
    		if(flag==1)
    		return GroupOf;
    		return "General";
    		
    }
    
    public static ArrayList<String> randomizeInput(File F) throws IOException{
    	String[] line;
    	ArrayList<String> lineToRandom = new ArrayList<String>();
    	ArrayList<String> randomLine = new ArrayList<String>();
    	
    	 Random randomGenerator;
    	 randomGenerator = new Random();
    	 
    	CSVReader reader = new CSVReader(new FileReader(F));
    	
    	
    	while ((line = reader.readNext()) != null){
    		
    		int index=0;
    		while(index<line.length)
    		{
    			lineToRandom.add(line[index]);
    			index++;
    			
    		}
    		
    		
    		
    		
    	}
    	
    	
    	for(int i=0;i<lineToRandom.size()/3;i++)
    		{int randomIndex = randomGenerator.nextInt(lineToRandom.size());
    		randomLine.add(lineToRandom.get(randomIndex));}
    	
    	//System.out.println("\nrandomLine: "+randomLine);
    	
    	return lineToRandom;
    }

    public static ArrayList<String> randomizeFinalInput(ArrayList<String> finalList) throws IOException{
    	Random randomGenerator;
    	 randomGenerator = new Random();
    	 ArrayList<String> randomFinalLine = new ArrayList<String>();
    	 
    	for(int i=0;i<finalList.size();i++)	
    	{
    		int randomIndex = randomGenerator.nextInt(finalList.size());
    	randomFinalLine.add(finalList.get(randomIndex));
    	
    	}
    	return randomFinalLine;
    }

    //create new lists of bullylists and phillists which donot
    //contain words (which are used for calculating threshold)
    public static ArrayList<String> newList(File F, ArrayList<String> RandWords) throws IOException{
    	ArrayList<String> tempList= new ArrayList<String> ();
    	String[] line;
    	CSVReader reader = new CSVReader(new FileReader(F));
    	while((line=reader.readNext())!=null)
    	{
    		int index=0;
    		while(index<line.length){ 
    			tempList.add(line[index]);
    			index++;
    		}
    	}
    	tempList.removeAll(RandWords);
    	return tempList;
    }
    
}
