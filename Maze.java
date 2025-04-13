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

        System.out.println("\nMaze with path:");
        printMaze();
    }

    // Maze generation using recursive backtracking
    private static void generateMaze() {
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(maze[i], '█');
        }

        carve(1, 1);
    }

    private static void carve(int x, int y) {
        int[] dx = {0, 0, 2, -2};
        int[] dy = {-2, 2, 0, 0};

        maze[x][y] = ' ';

        Integer[] directions = {0, 1, 2, 3};
        Collections.shuffle(Arrays.asList(directions), rand);

        for (int dir : directions) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx > 0 && ny > 0 && nx < SIZE - 1 && ny < SIZE - 1 && maze[nx][ny] == '█') {
                maze[x + dx[dir] / 2][y + dy[dir] / 2] = ' ';
                carve(nx, ny);
            }
        }
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

