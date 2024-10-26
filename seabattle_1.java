import java.util.Scanner;
import java.util.Random;

class Main {
    char hit = 'X';
    char miss = 'O';
    char drowned = '■';
    static char empty = ' ';
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fieldSize = 7;
        int shots = 0;
        System.out.println("Please enter your name:");
        String playersName = scanner.nextLine();
        char [][] field = new char[fieldSize][fieldSize];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                field[i][j] = empty;
            }
        }

    }
    static void cleanConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    static void showField(char[][] field ){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
     void placeShip(int count, int size, int fieldSize, char[][] field){
        Random random = new Random();
        for(int i = 0; i < count; i++){
            boolean placed = false;
            while(!placed){
                int row = random.nextInt(fieldSize);
                int columns = random.nextInt(fieldSize);
                placed = checkPlace(row, columns, field);
            }
        }
    }
    boolean checkPlace(int row, int columns, char[][] field) {
        if(field[row][columns] != empty ){
            return false;
        }
        else if(field[row+1][columns+1] != empty && field[row+1][columns-1] != empty && field[row-1][columns-1] != empty && field[row-1][columns+1] != empty){
            return false;
        }
        else if(field[row][columns-1] != empty && field[row][columns+1] != empty){
            return false;
        }
        else if(field[row+1][columns] != empty && field[row-1][columns] != empty){
            return false;
        }
        else return true;
    }
}
