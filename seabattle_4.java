import java.util.Random;
import java.util.Scanner;

public class Main {
	char hit = 'X';
	char miss = 'O';
	char drowned = 'b';
	char taken = '+';
	static char empty = ' ';
	int fieldSize = 7;
	char[][] field = new char[fieldSize][fieldSize];

	public static void main(String[] args) {
		Main game = new Main();
		game.runGame();
	}

	public void runGame() {
		Scanner scanner = new Scanner(System.in);
		int shots = 0;
		System.out.println("Please enter your name:");
		String playersName = scanner.nextLine();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				field[i][j] = empty;
			}
		}

		placeShips();
		System.out.println("Ships are placed. Let`s start!");
		showField();
		System.out.println("Enter yout hit:");
		String userHit = scanner.nextLine();
		takeHit(userHit);
	}

	public void cleanConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void showField() {
		System.out.println(" A B C D E F G");
		for (int i = 0; i < 7; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < 7; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void placeShips() {
		placeShip(1, 3);
		placeShip(2, 2);
		placeShip(4, 1);
	}

	public void placeShip(int count, int size) {
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			boolean placed = false;
			while (!placed) {
				int row = random.nextInt(fieldSize);
				int columns = random.nextInt(fieldSize);
				boolean horizontal = random.nextBoolean();

				placed = checkPlace(row, columns, size, horizontal);
			}
		}
	}

	public boolean checkPlace(int row, int column, int size, boolean horizontal) {
		if (horizontal) {
			if (column + size > fieldSize) {
				return false;
			}
		} else {
			if (row + size > fieldSize) {
				return false;
			}
		}
		for (int i = -1; i <= size; i++) {
			for (int j = -1; j <= 1; j++) {
				int newRow = row + i;
				int newCol = column + j;
				if (newRow >= 0 && newRow < fieldSize && newCol >= 0 && newCol < fieldSize) {
					if (field[newRow][newCol] != empty) {
						return false;
					}
				}
			}
		}
		if (horizontal) {
			for (int i = 0; i < size; i++) {
				field[row][column + i] = taken;
			}
		} else {
			for (int i = 0; i < size; i++) {
				field[row + i][column] = taken;
			}
		}

		return true;
	}


	public void takeHit(String userHit) {
		char letter = userHit.charAt(0);
		int i = userHit.charAt(1) - '0';
		int j = 0;
		if (letter == 'A') {
			j = 0;
		} else if (letter == 'B') {
			j = 1;
		} else if (letter == 'C') {
			j = 2;
		} else if (letter == 'D') {
			j = 3;
		} else if (letter == 'E') {
			j = 4;
		} else if (letter == 'F') {
			j = 5;
		} else if (letter == 'G') {
			j = 6;
		}
		else {
			System.out.println("Invalid input! Try again.");
			System. exit(0);
		}
		if (field[i - 1][j] == miss || field[i - 1][j] == hit) {
			System.out.println("You already shot at this cell. Try again.");
		}
		else if (field[i - 1][j] == taken) {
			field[i - 1][j] = hit;
			System.out.println("Hit!");
		} else {
			field[i - 1][j] = miss;
			System.out.println("Miss!");
		}
	}

}