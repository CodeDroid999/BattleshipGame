import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Read the board size (always square)
        System.out.print("Enter board size: ");
        int boardSize = scanner.nextInt();
        char[][] player1Board = new char[boardSize][boardSize];
        char[][] player2Board = new char[boardSize][boardSize];

        // Step 2: Initialize all coordinates of the board with "-"
        initializeBoard(player1Board);
        initializeBoard(player2Board);

        // Step 3: Read the number of boats each player will add to their board
        System.out.print("Enter the number of boats for each player: ");
        int numBoats = scanner.nextInt();

        // Step 4: Add boats for each player (one player after the other)
        addBoats(player1Board, numBoats, scanner, 1);
        addBoats(player2Board, numBoats, scanner, 2);

        // Step 5: Print both player's boards
        System.out.println("Initial Boards:");
        printBoards(player1Board, player2Board);

        // Step 6: Read the number of shots to fire
        System.out.print("Enter the number of shots: ");
        int numShots = scanner.nextInt();

        // Step 7: Read and process shots, alternating players' turns
        for (int shotNum = 1; shotNum <= numShots; shotNum++) {
            int currentPlayer = (shotNum % 2 == 1) ? 1 : 2;
            char[][] currentBoard = (currentPlayer == 1) ? player2Board : player1Board;

            System.out.print("Player " + currentPlayer + " enter coordinates for shot " + shotNum + ": ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            // Check if the shot is within the board's bounds
            if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
                System.out.println("Shot coordinates are outside the board. Ignoring the shot.");
                continue;
            }

            // Process the shot
            char cell = currentBoard[x][y];
            if (cell == 'B') {
                System.out.println("Player " + currentPlayer + " hit a boat!");
                currentBoard[x][y] = 'X'; // Hit a boat
            } else if (cell == '-') {
                System.out.println("Player " + currentPlayer + " hit water.");
                currentBoard[x][y] = 'X'; // Hit water
            }

            // Step 8: Print both player's boards after each shot
            System.out.println("After Shot " + shotNum + ":");
            printBoards(player1Board, player2Board);
        }

        // Step 9: Determine the game result and print it
        String winner = determineWinner(player1Board, player2Board);
        System.out.println("Game Result: " + winner);

        // Close the scanner
        scanner.close();
    }

    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void addBoats(char[][] board, int numBoats, Scanner scanner, int player) {
        for (int boat = 1; boat <= numBoats; boat++) {
            System.out.println("Player " + player + " enter boat coordinates for boat " + boat + ":");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int size = scanner.nextInt();
            int orientation = scanner.nextInt();

            for (int i = 0; i < size; i++) {
                if (orientation == 0 && y + i < board.length) {
                    board[x][y + i] = 'B';
                } else if (orientation == 1 && x + i < board.length) {
                    board[x + i][y] = 'B';
                }
            }
        }
    }

    private static void printBoards(char[][] player1Board, char[][] player2Board) {
        for (int i = 0; i < player1Board.length; i++) {
            for (int j = 0; j < player1Board[i].length; j++) {
                System.out.print(player1Board[i][j]);
            }
            System.out.print("\t");
            for (int j = 0; j < player2Board[i].length; j++) {
                System.out.print(player2Board[i][j]);
            }
            System.out.println();
        }
        System.out.println(); // Add a newline after printing both boards
    }

    private static String determineWinner(char[][] player1Board, char[][] player2Board) {
        boolean player1HasBoats = false;
        boolean player2HasBoats = false;

        for (int i = 0; i < player1Board.length; i++) {
            for (int j = 0; j < player1Board[i].length; j++) {
                if (player1Board[i][j] == 'B') {
                    player1HasBoats = true;
                }
                if (player2Board[i][j] == 'B') {
                    player2HasBoats = true;
                }
            }
        }

        if (player1HasBoats && player2HasBoats) {
            return "Draw!";
        } else if (player1HasBoats) {
            return "P1 Win!";
        } else if (player2HasBoats) {
            return "P2 Win!";
        } else {
            return "All destroyed!";
        }
    }
}
