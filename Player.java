import java.util.*;

public class Player {
	protected Hand hand;
	protected int ID;
	protected boolean isDone;
	protected String location;
	protected Set<String> known;
	
	public Player(int ident) {
		hand = new Hand();
		known = new HashSet<String>();
		ID = ident;
		isDone = false;
		location = "";
	}
	
	public String toString() {
		// game specific
		switch (ID) {
			case 0:
				return "the player";
			case 1:
				return "AI 1";
			case 2:
				return "AI 2";
			default:
				return "";
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public Set<String> getKnown() {
		return known;
	}
	
	public String getLocation() {
		return location;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setLocation(String newLocation) {
		location = newLocation;
	}
	
	public void setDone(boolean newVal) {
		isDone = newVal;
	}
 
}
