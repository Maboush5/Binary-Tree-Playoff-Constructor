import java.util.Iterator;

public class Playoffs {



























	
	
	
	

	
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
		pl.updateRound(1);

		// Uncomment each pair of lines when you are ready to run the subsequent rounds. 
		
		//pl.addNewStandings(7, 8, 11); // Ensure you execute this line before calling updateRound(2).
		//pl.updateRound(2);

		
		//pl.addNewStandings(3, 12, 13); // Ensure you execute this line before calling updateRound(3).
		//pl.updateRound(3);

		
		//pl.addNewStandings(1, 14, 14); // Ensure you execute this line before calling updateRound(4).
		//pl.updateRound(4);
	}

}
