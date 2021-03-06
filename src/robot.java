// robot
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class robot {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("robot.in");
        File outputFile = new File("robot.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            int rows = Integer.valueOf(line[0]);
            int columns = Integer.valueOf(line[1]);
            int startingRow = Integer.valueOf(line[2]) - 1;
            int startingColumn = Integer.valueOf(line[3]) - 1;

            if (rows == 0 && columns == 0 && startingRow == -1 && startingColumn == -1) {
                break;
            }

//            System.out.println(String.format("%s %s %s %s", rows, columns, startingRow, startingColumn));

            int[][] distance = new int[rows][columns];
            char[][] grid = new char[rows][columns];

            for (int row = 0; row < rows; row++) {
                char[] chars = scanner.nextLine().toCharArray();
                for (int column = 0; column < columns; column++) {
//                    System.out.println(String.format("r %s   c %s   l %s", row, column, Arrays.toString(chars)));
                    grid[row][column] = chars[column];
                }
            }

//            System.out.println(Arrays.deepToString(grid));

            int moves = 0;

            Solution solution = null;

            int currentRow = startingRow;
            int currentColumn = startingColumn;

            while (currentRow >= 0 && currentColumn >= 0 && currentRow < rows && currentColumn < columns) {
                moves += 1;

                char nextMove = grid[currentRow][currentColumn];
                int distanceWhenLastVisited = distance[currentRow][currentColumn];

                if (distanceWhenLastVisited != 0) {
                    // we're in a loop
                    int loopSize = moves - distanceWhenLastVisited;
                    solution = new Solution(moves - loopSize - 1, loopSize);
                    break;
                }

                distance[currentRow][currentColumn] = moves;

                switch (nextMove) {
                    case 'N':
                        currentRow -= 1;
                        break;
                    case 'E':
                        currentColumn += 1;
                        break;
                    case 'S':
                        currentRow += 1;
                        break;
                    case 'W':
                        currentColumn -= 1;
                        break;
                    default:
                        throw new IllegalArgumentException(nextMove + " is not a valid move");
                }
            }

            if (solution == null) {
                solution = new Solution(moves);
            }

            String s;
            if (solution.loop == 0) {
                if (solution.moves == 1) {
                    s = "1 instruction before exit";
                } else {
                    s = String.format("%s instructions before exit", solution.moves);
                }
            } else {
                if (solution.moves == 1) {
                    s = String.format("1 instruction before a loop of %s instructions", solution.loop);
                } else {
                    s = String.format("%s instructions before a loop of %s instructions", solution.moves, solution.loop);
                }
            }

            printWriter.println(s);
            System.out.println(s);

        }
        printWriter.close();
    }

    public static class Solution {
        int moves;
        int loop;

        public Solution(int moves) {
            this.moves = moves;
        }

        public Solution(int moves, int loop) {
            this.moves = moves;
            this.loop = loop;
        }

        @Override
        public String toString() {
            return "Solution{" +
                    "moves=" + moves +
                    ", loop=" + loop +
                    '}';
        }
    }
}

