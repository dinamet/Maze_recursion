import java.util.*;

public class Maze {
    private static final int SIZE = 21; // odd number to generate
    private static char[][] maze;

    public static void main(String[] args) {
        maze = new char[SIZE][SIZE];
        generateMaze();
        maze[1][1] = 'S';
        maze[SIZE - 2][SIZE - 2] = 'E';

        if (solve(1, 1)) {
            System.out.println("Path found!");
        } else {
            System.out.println("No path found.");
        }

        printMaze();
    }

    private static void generateMaze() {
        // initialization and start of recursive generation
    }

    private static boolean solve(int x, int y) {
        // recursive pathfinding
        return false;
    }

    private static void printMaze() {
        for (char[] row : maze) {
            System.out.println(new String(row));
        }
    }
}

