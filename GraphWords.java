import java.util.ArrayList;

import org.jgrapht.WeightedGraph;

/*Sample creating graphs out of new words
 */

class NodeQ {
    String label;
    //ArrayList<NodeQ> adjacencyList;
    NodeQ next;
    
}
class EdgeQ {
    int weight;
    //ArrayList<NodeQ> adjacencyList;
    NodeQ from;
    NodeQ to;
    
}
public class GraphWords {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NodeQ n1= new NodeQ();
		NodeQ n2= new NodeQ();
		
		n1.label="1";
		n2.label="2";
	
		n1.next=n2;
		
		System.out.println(n1.next);
		System.out.println(n2);
		
		EdgeQ E1 = new EdgeQ();
		E1.from=n1;
		E1.to=n2;
		E1.weight=21;
		
		System.out.println("From: "+E1.from.label+ "---"+E1.weight+"---"+E1.to.label);
	//	System.out.println("Node 1 label "+n1.label+ " Node 1 address "+n1);
		// WeightedGraph W= new WeightedGraph<n1,>();
		ArrayList<String> SampleList= new ArrayList<String>();
		SampleList.add("word1");
		SampleList.add("word2");
		SampleList.add("addme");
		
		
		
		 
		 

	}
void createList(NodeQ n,ArrayList<String> SampleList)
{
	
		
	for(int i=0;i<3;i++)
	{
		
		NodeQ n1= new NodeQ();
		n1.label=SampleList.get(i);
		
		
		
	}
}

}
