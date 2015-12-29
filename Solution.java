import java.util.Random;

public class Solution {
	protected Random gen;
	protected String room, weapon, killer;
	
	public Solution() {
		gen = new Random();
		room = genRoom(gen.nextInt(9));
		weapon = genWeapon(gen.nextInt(6));
		killer = genPerson(gen.nextInt(6));
		
	}
	
	public String toString() {
		return killer + " killed Mr. Boddy in the " + room + " with the " + weapon + "!";
	}
	
	public String getRoom() {
		return room;
	}
	
	public String getWeapon() {
		return weapon;
	}
	
	public String getKiller() {
		return killer;
	}
	
	public static String genRoom(int seed) {
		switch (seed) {
			case 1:
				return "conservatory";
			case 2:
				return "ball room";
			case 3:
				return "kitchen";
			case 4:
				return "dining room";
			case 5:
				return "hall";
			case 6:
				return "study";
			case 7:
				return "lounge";
			case 8:
				return "library";
			default:
				return "billiards room";
		}
	}
	
	public static String genWeapon(int seed) {
		switch (seed) {
			case 1:
				return "knife";
			case 2:
				return "lead pipe";
			case 3:
				return "revolver";
			case 4:
				return "rope";
			case 5:
				return "wrench";
			default:
				return "candlestick";
		}
	}
	
	public static String genPerson(int seed) {
		switch (seed) {
			case 1:
				return "Mrs. White";
			case 2:
				return "Ms. Scarlet";
			case 3:
				return "Prof. Plum";
			case 4:
				return "Mrs. Peacock";
			case 5:
				return "Col. Mustard";
			default:
				return "Mr. Green";
		}
	}
}
