import java.util.Iterator;


public class Playoffs {

	private LinkedBinaryTree<String> tree;
	private HockeySeries[] standings;

	
	public Playoffs() {
	
		String [] tempArray = new String[31];
		
		for (int x = 0; x < 15; x++) {	// loop through first 15 indices and set as TBD (unknown winners)
			
			tempArray[x] = ("TBD " + x);
		
			}
			
		 MyFileReader nameFile = new MyFileReader("teams.txt");	 
		 
		 while (nameFile.endOfFile() == false) {	// go until end of file is reached
			
			 for (int x = 15; x < 31; x++) {
				
				 String name = nameFile.readString();	// read line of file and add to array
				 tempArray[x] = name;		 
			 
			 } // end for loop
			 
			 
		 	} // end while loop
			
		 standings = new HockeySeries[15];
		 
		 int counter = 0;
		 for (int x = 15; x < 31; x++) {		// team names start at index 15	 - 30	 	 
			 HockeySeries newSeries = new HockeySeries(tempArray[x], tempArray[x + 1], 0, 0);	// create a hockey series object for every 2 teams	 
			 standings[counter] = newSeries;	//update standings array
			 counter++;
			 x++;
			 
		 	}
		 
		 TreeBuilder<String> binaryTree = new TreeBuilder<String>(tempArray); // build new tree with array
		 this.tree = binaryTree.getTree();
		
	} // end Playoffs method

	
	public LinkedBinaryTree<String> getTree() {	// return tree
		
		return tree;
		
	}
		
	public HockeySeries[] getStandings() {	// return standings
		
		return standings;
		
		
	}
	
	public String updateStandings(String team1, String team2, int wins1, int wins2) {
		int TeamIndex = 0;
		
		for (int x = 0; x < standings.length; x++) {	// loop through standings array and find team 1 and 2
			
			if (standings[x] != null && team1.equals(standings[x].getTeamA()) && team2.equals(standings[x].getTeamB())){
				TeamIndex = x;	// return index of team
				break;		
				}
									
			}
			
		
		if (wins1 > wins2) {	// increment 1 point for team1

			standings[TeamIndex].incrementWins(1, 0);			
		}
		
		if (wins2 > wins1) {	// increment 1 point for team2
		
			standings[TeamIndex].incrementWins(0, 1);
						
		}
			
		if (standings[TeamIndex].getTeamAWins() == 4)  { // if wins = 4, return team name
		
			return standings[TeamIndex].getTeamA();
			
		}
		
		if (standings[TeamIndex].getTeamBWins() == 4)  {	// if wins = 4, return team name
			
			return standings[TeamIndex].getTeamB();
			
			}
							
		else {
		
			return null;	// return null if neither team reached 4 points or team name was not found	
		}
	}
	
	 	
	public void updateRound(int roundNumber) {
		
		 MyFileReader scores = null;
	      
		 // open text file with corresponding roundNumber parameter
	      if (roundNumber == 1){
	    	scores = new MyFileReader("scores1.txt");	
	      		}
	      
	      else if (roundNumber == 2){
		    	scores = new MyFileReader("scores2.txt");	
		      }
	      else if (roundNumber == 3){
		    	scores = new MyFileReader("scores3.txt");	
		      }
	      
	      else if (roundNumber == 4){
		    	scores = new MyFileReader("scores4.txt");	
		      }	      
	
	while (scores.endOfFile() == false) {	// go to end of file
	
		String [] tempArray = scores.readString().split(",");	// split line into array of 4 strings
		String winner = updateStandings(tempArray[0], tempArray[1], Integer.parseInt(tempArray[2]), Integer.parseInt(tempArray[3]));
		
		try {
		
			if (winner != null) {	// if updateStandings returns a team name, update parent node with team name
			
			BinaryTreeNode<String> parent = findParent(tempArray[0], tempArray[1]);	// find parent of team name
			parent.setData(winner);	
//		
			}
				
		} catch (Exception e)	{
			
			
			}	// end catch
		
		} // end While
	
	}	// end updateRound
	
	
	
	public BinaryTreeNode<String> findParent(String team1, String team2){
		
		// Traverse through tree and find left and right child nodes
		LinkedQueue<BinaryTreeNode<String>> Q = new LinkedQueue<BinaryTreeNode<String>>();
			Q.enqueue(tree.getRoot());
			BinaryTreeNode<String> v;
			
			// while there are nodes in the queue:
			while (!Q.isEmpty()) {
			v = Q.dequeue();
			
			
			if (v.getLeft() != null && v.getRight() != null) {
                
				// check if left and right nodes match team names 
				if (v.getLeft().getData().equals(team1) && v.getRight().getData().equals(team2)) {
                    
					return v; // return parent node
                }
            }
			
			// traverse through children nodes
			if (v.getLeft() != null)
				Q.enqueue(v.getLeft());
			
			if (v.getRight() != null)
				Q.enqueue(v.getRight());
			}
		
			return null;
		}
	
	/**
	 * This method adds the new series to the standings array before a new round begins. It does this using an iterator
	 * from the tree and skipping over the nodes that are still unknown until it gets to the nodes from the new round.
	 * It then takes two teams at a time from the iterator and creates a new series in the standings array for those
	 * two teams. The series standings (number of wins for each team) are set to 0 by default. 
	 */
	public void addNewStandings (int numSkips, int sIndex, int eIndex) {
		Iterator<String> iter = tree.iteratorLevelOrder();
		int i;
		String team1, team2;
		for (i = 0; i < numSkips; i++) {
			iter.next();
		}
		for (i = sIndex; i <= eIndex; i++) {
			team1 = iter.next();
			team2 = iter.next();
			standings[i] = new HockeySeries(team1, team2, 0, 0);
		}
	}
	
	/**
	 * This method simply prints out the standings table in a cleanly formatted table structure.
	 */
	public void printStandings () {
		String str;
		for (int k = 0; k < standings.length; k++) {
			if (standings[k] != null) {
				str = String.format("%-15s\t%-15s\t%3d-%d", standings[k].getTeamA(), standings[k].getTeamB(), standings[k].getTeamAWins(), standings[k].getTeamBWins());
				System.out.println(str);
			}
		}
	}
	
	
	public static void main(String[] args) {
		Playoffs pl = new Playoffs();
		
//		pl.updateRound(1);
//////
//////		
//////		// Uncomment each pair of lines when you are ready to run the subsequent rounds. 
//////		
//		pl.addNewStandings(7, 8, 11); // Ensure you execute this line before calling updateRound(2).
//		pl.updateRound(2);
//////
////		
////		pl.addNewStandings(3, 12, 13); // Ensure you execute this line before calling updateRound(3).
////		pl.updateRound(3);
////
////		
////		pl.addNewStandings(1, 14, 14); // Ensure you execute this line before calling updateRound(4).
////		pl.updateRound(4);
	}

}
