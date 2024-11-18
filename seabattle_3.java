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
                placed = checkPlace(row, columns, size);
            }
        }
    }

    public boolean checkPlace(int row, int columns, int size) {
        boolean horizontal = false;
        int sizeNow = 0;
        if (columns + size <= fieldSize) {
            horizontal = true;
            while (sizeNow < size) {
                if (field[row][columns + sizeNow] != empty) {
                    return false;
                }
                sizeNow++;
            }
            sizeNow = 0;
            while (sizeNow < size) {
                field[row][columns + sizeNow] = taken;
                sizeNow++;
            }
            return true;
        }

        if (row + size <= fieldSize) {
            sizeNow = 0;
            while (sizeNow < size) {
                if (field[row + sizeNow][columns] != empty) {
                    return false;
                }
                sizeNow++;
            }
            sizeNow = 0;
            while (sizeNow < size) {
                field[row + sizeNow][columns] = taken;
                sizeNow++;
            }
            return true;
        }

        return false;
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

    if (field[i - 1][j] == taken) {
        field[i - 1][j] = hit;
        System.out.println("Hit!");
    } else {
        field[i - 1][j] = miss;
        System.out.println("Miss!");
    }
}

}