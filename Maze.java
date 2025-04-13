import java.util.*;
import java.io.*;

public class Maze {

    static int size;
    static char[][] maze;
    static List<int[]> path = new ArrayList<>();
    static Random rand = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter square maze size (odd number >= 11): ");
        size = scanner.nextInt();
        if (size < 11 || size % 2 == 0) {
            System.out.println("Invalid size. Must be an odd number >= 11.");
            return;
        }

        maze = new char[size][size];
        generateMaze();

        int startX = 1, startY = 1;
        int endX = size - 2, endY = size - 2;

        maze[startX][startY] = 'S'; // Start
        maze[endX][endY] = 'E';     // Exit

        if (solve(startX, startY, endX, endY)) {
            System.out.println("Path found!");
            printPath();
        } else {
            System.out.println("No path found.");
        }

        System.out.println("\nMaze with path:");
        printMaze();

        // CSV file
        saveMazeToCSV("maze.csv");
    }

    private static void generateMaze() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(maze[i], '#');
        }
        carve(1, 1);
    }

    private static void carve(int x, int y) {
        int[] dx = {0, 0, 2, -2};
        int[] dy = {-2, 2, 0, 0};
        maze[x][y] = ' ';

        Integer[] dirs = {0, 1, 2, 3};
        Collections.shuffle(Arrays.asList(dirs), rand);

        for (int dir : dirs) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx > 0 && ny > 0 && nx < size - 1 && ny < size - 1 && maze[nx][ny] == '#') {
                maze[x + dx[dir] / 2][y + dy[dir] / 2] = ' ';
                carve(nx, ny);
            }
        }
    }

    private static boolean solve(int x, int y, int endX, int endY) {
        if (x < 0 || y < 0 || x >= size || y >= size) return false;
        if (maze[x][y] == 'E') {
            path.add(new int[]{x, y});
            return true;
        }
        if (maze[x][y] != ' ' && maze[x][y] != 'S') return false;

        if (maze[x][y] != 'S') maze[x][y] = '*';
        path.add(new int[]{x, y});

        if (solve(x + 1, y, endX, endY) || solve(x - 1, y, endX, endY) ||
                solve(x, y + 1, endX, endY) || solve(x, y - 1, endX, endY)) {
            return true;
        }

        path.remove(path.size() - 1);
        if (maze[x][y] != 'S') maze[x][y] = ' ';
        return false;
    }

    private static void printMaze() {
        for (char[] row : maze) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static void printPath() {
        System.out.println("Path coordinates:");
        for (int[] p : path) {
            System.out.println("(" + p[0] + ", " + p[1] + ")");
        }
    }

    private static void saveMazeToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int i = 0; i < size; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < size; j++) {
                    line.append(maze[i][j]);
                    if (j < size - 1) line.append(",");
                }
                writer.println(line);
            }
            System.out.println("Maze saved to: " + filename);
        } catch (Exception e) {
            System.out.println("Error saving maze to file: " + e.getMessage());
        }
    }
}
