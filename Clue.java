import java.util.*;

public class Clue {
	protected static Solution solution;
	public static Set<String> rooms, weapons, people;
	protected Player player, comp1, comp2, turnOrder[];
	protected Scanner scan;
	protected boolean isDone;
	
	public Clue() {
		// set up variables
		Random gen = new Random();
		scan = new Scanner(System.in);
		turnOrder = new Player[4];
		isDone = false;
		rooms = new HashSet<String>();
		weapons = new HashSet<String>();
		people = new HashSet<String>();
		
		// populating sets
		for (int num=0; num<9; num++) {
			weapons.add(Solution.genWeapon(num));
			people.add(Solution.genPerson(num));
			rooms.add(Solution.genRoom(num));
		}
		
		// get solution
		solution = new Solution();
		
		// deal three hands: two computer hands and one player hand
		Hand.refreshDeck(solution);
		player = new Player(0);
		comp1 = new Player(1);
		comp2 = new Player(2);
		
		// decide who goes first
		int roll = gen.nextInt(3);
		switch(roll) {
			case 0: turnOrder[0] = player; turnOrder[1] = comp1; turnOrder[2] = comp2; break;
			case 1: turnOrder[0] = comp1; turnOrder[1] = comp2; turnOrder[2] = player; break;
			case 2: turnOrder[0] = comp2; turnOrder[1] = player; turnOrder[2] = comp1; break;
		}
		
		// start playing
		do {
			if (turnOrder[0].equals(player)) {
				player();
				setTurnOrder();
			}
			else {
				computer(turnOrder[0]);
				setTurnOrder();
			}
		} while (!isDone);
		
		System.out.println("The solution was: " + solution);
		
	}
	
	public void computer(Player whichOne) {
		Random gen = new Random();
		
		// the computer moves
		whichOne.setLocation(Solution.genRoom(gen.nextInt(9)));
		
		// asks a question
		question(whichOne.getLocation(), Solution.genWeapon(gen.nextInt(6)), Solution.genPerson(gen.nextInt(9)));
		
		// perhaps guess
		if (whichOne.getKnown().size() > 9) {
			String room = "", weapon = "", killer = "";
			for (String card : rooms) {
				if (!whichOne.getKnown().contains(card) && !whichOne.getHand().getHand().contains(card))
					room = card;
			}
			for (String card : weapons) {
				if (!whichOne.getKnown().contains(card) && !whichOne.getHand().getHand().contains(card))
					weapon = card;
			}
			for (String card : people) {
				if (!whichOne.getKnown().contains(card) && !whichOne.getHand().getHand().contains(card))
					killer = card;
			}
			
			if (checkSolution(room, weapon, killer)) {
				System.out.println("You lost...");
				System.out.println(whichOne + " won the game.");
				isDone = true;
			}
			else
				whichOne.setDone(true);
		}
		
	}
	
	public void player() {
		String weapon = "NONE", killer = "NONE";
		
		System.out.println("\nHere is your hand: ");
		System.out.println(player.getHand());
		System.out.println("\nHere are the things that have been revealed to you: ");
		System.out.println(player.getKnown()+"\n");
		
		// allow the player to move
		System.out.print("It's your turn. Do you want to move? Enter Y for yes and N for no: ");
		if (scan.next().equalsIgnoreCase("y")) {
			System.out.println("Where do you want to move to?");
			System.out.print("1: Conservatory\t2: Ball Room\t3: Kitchen\n4: Dining Room\t5: Hall");
			System.out.println("\t\t6: Study\n7: Lounge\t8: Library\t9: Billiards Room");
			player.setLocation(Solution.genRoom(scan.nextInt()));
		}
		
		// get questions
		System.out.println("\nAsk a question. Choose the weapon:");
		do {
			try {
				System.out.println("1: knife\t2: lead pipe\t3: revolver\n4: rope\t\t5: wrench\t6: candlestick");
				weapon = Solution.genWeapon(scan.nextInt());
			}
			catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("Try again please. Enter the number for the weapon:");
			}
		} while (weapon.equals("NONE"));
		
		System.out.println("\nChoose the killer:");
		do {
			try {
				System.out.println("1: Mrs. White\t2: Ms. Scarlet\t3: Prof. Plum\n4: Mrs. Peacock\t5: Col. Mustard\t6: Mr. Green");
				killer = Solution.genPerson(scan.nextInt());
			}
			catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("Try again please. Enter the number for the person: ");
			}
		} while (killer.equals("NONE"));
		
		System.out.println("You discover: " + question(player.getLocation(), weapon, killer));
		
		
		// allow guessing the solution
		System.out.print("\nDo you want to guess the solution? Enter Y for yes and N for no: ");
		if (scan.next().equalsIgnoreCase("y")) {
			String location = "NONE";
			killer = "NONE"; weapon = "NONE";
			
			System.out.println("\nChoose the location:");
			do {
				try {
					System.out.print("1: Conservatory\t2: Ball Room\t3: Kitchen\n4: Dining Room\t5: Hall");
					System.out.println("\t\t6: Study\n7: Lounge\t8: Library\t9: Billiards Room");
					location = Solution.genRoom(scan.nextInt());
				}
				catch (InputMismatchException e) {
					scan.nextLine();
					System.out.println("Try again. Enter the number for the room:");
				}
			} while (location == "NONE");
			
			System.out.println("\nChoose the weapon:");
			do {
				try {
					System.out.println("1: knife\t2: lead pipe\t3: revolver\n4: rope\t\t5: wrench\t6: candlestick");
					weapon = Solution.genWeapon(scan.nextInt());
				}
				catch (InputMismatchException e) {
					scan.nextLine();
					System.out.println("Try again please. Enter the number for the weapon:");
				}
			} while (weapon.equals("NONE"));
			
			System.out.println("\nChoose the killer:");
			do {
				try {
					System.out.println("1: Mrs. White\t2: Ms. Scarlet\t3: Prof. Plum\n4: Mrs. Peacock\t5: Col. Mustard\t6: Mr. Green");
					killer = Solution.genPerson(scan.nextInt());
				}
				catch (InputMismatchException e) {
					scan.nextLine();
					System.out.println("Try again please. Enter the number for the person: ");
				}
			} while (killer.equals("NONE"));
			
			if (checkSolution(location, weapon, killer)) {
				System.out.println("\nYou won!");
				isDone = true;
			}
			else {
				System.out.println("\nYou lost...");
				isDone = true;
				player.setDone(true);
			}
		}
	}
	
	public String question(String room, String weapon, String killer) {
		// given a suspect, a room, and a weapon, this checks to see if the player next to them has a card
		// if not, it checks the other other player
		String whichever;
		if (turnOrder[1].getHand().getHand().contains(room)) {
			turnOrder[0].getKnown().add(room);
			whichever = room;
		}
		else if (turnOrder[1].getHand().getHand().contains(weapon)) {
			turnOrder[0].getKnown().add(weapon);
			whichever = weapon;
		}
		else if (turnOrder[1].getHand().getHand().contains(killer)) {
			turnOrder[0].getKnown().add(killer);
			whichever = killer;
		}
		else {
			if (turnOrder[2].getHand().getHand().contains(room)) {
				turnOrder[0].getKnown().add(room);
				whichever = room;
			}
			else if (turnOrder[2].getHand().getHand().contains(weapon)) {
				turnOrder[0].getKnown().add(weapon);
				whichever = weapon;
			}
			else if (turnOrder[2].getHand().getHand().contains(killer)) {
				turnOrder[0].getKnown().add(killer);
				whichever = killer;
			}
			else
				whichever = "nothing";
		}

		return whichever;
	}
	
	public boolean checkSolution(String room, String weapon, String killer) {
		if (room.equals(solution.getRoom()) && weapon.equals(solution.getWeapon()) && killer.equals(solution.getKiller())) {
			isDone = true;
			return true;
		}
		else
			return false;
	}
	
	public void setTurnOrder() {
		if (!turnOrder[1].isDone()) {
			turnOrder[3] = turnOrder[0];
			turnOrder[0] = turnOrder[1];
			turnOrder[1] = turnOrder[2];
			turnOrder[2] = turnOrder[3];
		}
		else {
			if (!turnOrder[2].isDone()) {
				turnOrder[3] = turnOrder[0];
				turnOrder[0] = turnOrder[2];
				turnOrder[2] = turnOrder[1];
				turnOrder[1] = turnOrder[3];
			}
			else {
				System.out.println("You won by default.");
				isDone = true;
			}
		}
	}
	
	public static void main(String[] args) {
		new Clue();
	}

}
