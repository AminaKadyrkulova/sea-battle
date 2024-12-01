import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static char hit = '·';
    static char miss = 'O';
    static char drowned = 'X';
    static char taken = '\u00A0';
    static char empty = ' ';
    int fieldSize = 7;
    char[][] field = new char[fieldSize][fieldSize];
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> playersList = new ArrayList<>();
    static ArrayList<Integer> shotsList = new ArrayList<>();
    List<List<int[]>> ships = new ArrayList<>();

    public static void main(String[] args) {
        Main game = new Main();
        while (true) {
            game.runGame();
            System.out.println("Do you want to start another game? Type 'Yes' or 'No'.");
            String usersChoice = scanner.nextLine();
            if (usersChoice.equalsIgnoreCase("No")) {
                game.showPlayersList();
                break;
            }
        }
    }

    public void runGame() {
        ships.clear();
        Scanner scanner = new Scanner(System.in);
        int shots = 0;
        System.out.println("Please enter your name:");
        String playersName = scanner.nextLine();
        playersList.add(playersName);
        shotsList.add(shots);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = empty;
            }
        }

        placeShips();
        System.out.println("Ships are placed. Let's start!");
        showField();
        while (true) {
            System.out.println("Enter your hit: ");
            String userHit = scanner.nextLine();
            cleanConsole();
            if (takeHit(userHit)) {
                shots++;
            }
            showField();
            if (gameIsOver()) {
                System.out.println("Congratulations! You've sunk all ships!");
                shotsList.set(shotsList.size() - 1, shots);
                break;
            }
        }
    }

    public void cleanConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showField() {
        System.out.println("  A B C D E F G");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < fieldSize; j++) {
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
                int col = random.nextInt(fieldSize);
                boolean horizontal = random.nextBoolean();
                placed = checkPlace(row, col, size, horizontal);
            }
        }
    }

    public boolean checkPlace(int row, int column, int size, boolean horizontal) {
        if (horizontal) {
            if (column + size > fieldSize) return false;
        } else {
            if (row + size > fieldSize) return false;
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
        List<int[]> shipCells = new ArrayList<>();
        if (horizontal) {
            for (int i = 0; i < size; i++) {
                field[row][column + i] = taken;
                shipCells.add(new int[]{row, column + i});
            }
        } else {
            for (int i = 0; i < size; i++) {
                field[row + i][column] = taken;
                shipCells.add(new int[]{row + i, column});
            }
        }
        ships.add(shipCells);
        return true;
    }

    public boolean takeHit(String userHit) {
        if (userHit.length() < 2) {
            System.out.println("Invalid input! Try again.");
            return false;
        }

        char letter = userHit.charAt(0);
		int row = userHit.charAt(1) - '1';
		int col = 0;
		if (letter == 'A') {
			col = 0;
		} else if (letter == 'B') {
			col = 1;
		} else if (letter == 'C') {
			col = 2;
		} else if (letter == 'D') {
			col = 3;
		} else if (letter == 'E') {
			col = 4;
		} else if (letter == 'F') {
			col = 5;
		} else if (letter == 'G') {
			col = 6;
		}

        if (row < 0 || row >= fieldSize || col < 0 || col >= fieldSize) {
            System.out.println("Invalid coordinates! Try again.");
            return false;
        }

        if (field[row][col] == miss || field[row][col] == hit || field[row][col] == drowned) {
            System.out.println("You already shot at this cell. Try again.");
            return false;
        }

        if (field[row][col] == taken) {
            field[row][col] = hit;
            System.out.println("Hit!");
            checkShip();
            return true;
        } else {
            field[row][col] = miss;
            System.out.println("Miss!");
            return true;
        }
    }

    public void checkShip() {
    for (int i = 0; i < ships.size(); i++) {
        List<int[]> ship = ships.get(i);
        boolean sunk = true;

        for (int j = 0; j < ship.size(); j++) {
            int[] cell = ship.get(j);
            int row = cell[0];
            int col = cell[1];
            if (field[row][col] != hit) {
                sunk = false;
                break;
            }
        }

        if (sunk) {
            for (int j = 0; j < ship.size(); j++) {
                int[] cell = ship.get(j);
                field[cell[0]][cell[1]] = drowned;
            }
            System.out.println("Ship sunk!");
        }
    }
}


    public boolean gameIsOver() {
        for (List<int[]> ship : ships) {
            for (int[] cell : ship) {
                int row = cell[0];
                int col = cell[1];
                if (field[row][col] != drowned) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showPlayersList() {
        for (int i = 0; i < playersList.size(); i++) {
            for (int j = 0; j < playersList.size() - 1; j++) {
                if (shotsList.get(j) > shotsList.get(j + 1)) {
                    int tempShots = shotsList.get(j);
                    shotsList.set(j, shotsList.get(j + 1));
                    shotsList.set(j + 1, tempShots);
                    String tempName = playersList.get(j);
                    playersList.set(j, playersList.get(j + 1));
                    playersList.set(j + 1, tempName);
                }
            }
        }

        System.out.println("Leaderboard:");
        for (int i = 0; i < playersList.size(); i++) {
            System.out.println(playersList.get(i) + " - " + shotsList.get(i) + " shots");
        }
    }
}
