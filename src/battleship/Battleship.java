package battleship;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Battleship {

    List<Location> list = new ArrayList();
    char[][] board = new char[5][5];
    Scanner sh = new Scanner(System.in);
    List<Location> listFire = new ArrayList();
    boolean repeatFire;

    public static void main(String[] args) {
        Battleship bt = new Battleship();
        bt.createBoard();
        bt.createShips();

        int turn = 1;
        do {
            System.out.println("Turn " + turn + " of 6" + "\t" + "Ships: " + bt.list.size());
            System.out.print("Guess a row:");
            String row = bt.sh.nextLine();
            while (!row.matches("^[0-9]+")) {
                System.out.println("Incorrect data");
                row = bt.sh.nextLine();
            }
            System.out.print("Guess a column:");
            String column = bt.sh.nextLine();
            while (!column.matches("^[0-9]+")) {
                System.out.println("Incorrect data");
                column = bt.sh.nextLine();
            }

            for (Location l : bt.listFire) {
                bt.repeatFire = false;
                if (Integer.parseInt(row) == l.getRow() && Integer.parseInt(column) == l.getColumn()) {
                    bt.repeatFire = true;
                    break;
                }
            }
            bt.listFire.add(new Location(Integer.parseInt(row), Integer.parseInt(column)));
            if (bt.repeatFire) {
                System.out.println("You've already guessed that one");
            } else if (Integer.parseInt(row) >= 5 || Integer.parseInt(row) < 0 || Integer.parseInt(column) >= 5 || Integer.parseInt(column) < 0) {
                System.out.println("That's not even in the ocean!");
            } else if (bt.fire(row, column)) {
                System.out.println("You sunk my battleship!");
                bt.board[Integer.parseInt(row)][Integer.parseInt(column)] = '*';
            } else {
                System.out.println("You've missed my battleship.");
                bt.board[Integer.parseInt(row)][Integer.parseInt(column)] = 'X';
            }

            bt.printBoard();

            if (bt.list.isEmpty()) {
                System.out.println("You are win!!!");
                break;
            }
            turn++;
        } while ((turn < 7));

        if (!bt.list.isEmpty()) {
            System.out.println("You are loss");
            for (Location l : bt.list) {
                bt.board[l.getRow()][l.getColumn()] = '*';
            }
        }
        bt.printBoard();

    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public void createBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 'O';
            }
        }
    }

    public boolean fire(String row, String column) {
        boolean fire = false;
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(row) == list.get(i).getRow() && Integer.parseInt(column) == list.get(i).getColumn()) {
                fire = true;
                list.remove(list.get(i));
            }
        }
        return fire;
    }

    public void createShips() {
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int rand = r.nextInt(5);
            int rand1 = r.nextInt(5);
            list.add(new Location(rand, rand1));
        }

    }
}
