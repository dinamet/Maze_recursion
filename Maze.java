import java.util.*;
import java.io.*;

public class Maze {

    static int size;
    static char[][] maze;
    static List<int[]> path = new ArrayList<>();
    static Random rand = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Ask user for maze size (must be odd and at least 11)
        System.out.print("Enter square maze size (odd number >= 11): ");
        size = scanner.nextInt();
        if (size < 11 || size % 2 == 0) {
            System.out.println("Invalid size. Must be an odd number >= 11.");
            return;
        }

        maze = new char[size][size];

        // Generate the maze
        generateMaze();

        // Define start and end points
        int startX = 1, startY = 1;
        int endX = size - 2, endY = size - 2;
        maze[startX][startY] = 'S'; // Start point
        maze[endX][endY] = 'E';     // Exit point

        // Find path using recursion (DFS)
        if (solve(startX, startY, endX, endY)) {
            System.out.println("Path found!");
            printPath(); // Print coordinates of the path
        } else {
            System.out.println("No path found.");
        }

        // Print final maze with path
        System.out.println("\nMaze with path:");
        printMaze();

        // Save the maze to a CSV file
        saveMazeToCSV("maze.csv");
    }

    // Generate the maze using recursive backtracking
    private static void generateMaze() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(maze[i], '#'); // Fill entire maze with walls
        }
        carve(1, 1); // Start carving paths from (1,1)
    }

    // Recursive method to carve paths in the maze
    private static void carve(int x, int y) {
        int[] dx = {0, 0, 2, -2}; // Directions: down, up, right, left
        int[] dy = {-2, 2, 0, 0};
        maze[x][y] = ' '; // Mark current cell as a path

        Integer[] dirs = {0, 1, 2, 3}; // Randomize directions to make maze unpredictable
        Collections.shuffle(Arrays.asList(dirs), rand);

        for (int dir : dirs) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // If next cell is inside bounds and is a wall, carve a path
            if (nx > 0 && ny > 0 && nx < size - 1 && ny < size - 1 && maze[nx][ny] == '#') {
                // Remove wall between current and next cell
                maze[x + dx[dir] / 2][y + dy[dir] / 2] = ' ';
                carve(nx, ny); // Recurse to next cell
            }
        }
    }

    // Recursive DFS algorithm to find a path from start to end
    private static boolean solve(int x, int y, int endX, int endY) {
        // Check boundaries
        if (x < 0 || y < 0 || x >= size || y >= size) return false;

        // If we reached the exit
        if (maze[x][y] == 'E') {
            path.add(new int[]{x, y});
            return true;
        }

        // If cell is not walkable or already visited
        if (maze[x][y] != ' ' && maze[x][y] != 'S') return false;

        if (maze[x][y] != 'S') maze[x][y] = '.'; // Mark as visited with '*'
        path.add(new int[]{x, y}); // Add to current path

        // Try moving in 4 directions
        if (solve(x + 1, y, endX, endY) || solve(x - 1, y, endX, endY) ||
                solve(x, y + 1, endX, endY) || solve(x, y - 1, endX, endY)) {
            return true; // Found the path
        }

        // Backtrack if no direction works
        path.remove(path.size() - 1);
        if (maze[x][y] != 'S') maze[x][y] = ' ';
        return false;
    }

    // Print the maze in the console
    private static void printMaze() {
        for (char[] row : maze) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    // Print the list of coordinates in the path
    private static void printPath() {
        System.out.println("Path coordinates:");
        for (int[] p : path) {
            System.out.println("(" + p[0] + ", " + p[1] + ")");
        }
    }

    // Save the maze as a CSV file
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
