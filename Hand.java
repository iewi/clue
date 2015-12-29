import java.util.*;

public class Hand {
	protected Set<String> hand;
	protected static ArrayList<String> deck;
	
	public Hand() {
		hand = new HashSet<String>();
		Random gen = new Random();
		for (int i=0; i<6; i++) {
			int index = gen.nextInt(deck.size());
			hand.add(deck.remove(index));
		}
	}
	
	public String toString() {
		return hand.toString();
	}
	
	public Set<String> getHand() {
		return hand;
	}
	
	public static void refreshDeck(Solution sol) {
		deck = new ArrayList<String>();
		for (int i=0; i<21; i++) {
			if (i < 9)
				deck.add(Solution.genRoom(i));
			else if (i < 15) {
				deck.add(Solution.genPerson(i-9));
			}
			else {
				deck.add(Solution.genWeapon(i-15));
			}
				
		}
		deck.remove(sol.getKiller());
		deck.remove(sol.getRoom());
		deck.remove(sol.getWeapon());
	}
}
