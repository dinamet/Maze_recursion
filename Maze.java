import java.util.*;

public class Maze {
    private static char[][] maze;
    private static final Random rand = new Random();
    private static int size;
    private static List<int[]> path = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter square maze size (>= 11, recommended odd): ");
        size = scanner.nextInt();
        if (size < 11) {
            System.out.println("Size too small. Using 11.");
            size = 11;
        } else if (size % 2 == 0) {
            System.out.println("Even size detected. Increasing to next odd: " + (size + 1));
            size++;
        }

        maze = new char[size][size];
        generateMaze();

        int startX = 1, startY = 1;
        int endX = size - 2, endY = size - 2;
        maze[startX][startY] = 'S';
        maze[endX][endY] = 'E';

        System.out.println("\nGenerated Maze:");
        printMaze();

        System.out.println("\nSolving the maze...");

        if (solve(startX, startY, endX, endY)) {
            System.out.println("Path found!");
            printPath();
        } else {
            System.out.println("No path found.");
        }

        System.out.println("\nMaze with path:");
        printMaze();
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

        Integer[] directions = {0, 1, 2, 3};
        Collections.shuffle(Arrays.asList(directions), rand);

        for (int dir : directions) {
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

        maze[x][y] = '.';
        path.add(new int[]{x, y});

        if (solve(x + 1, y, endX, endY) || solve(x - 1, y, endX, endY) ||
                solve(x, y + 1, endX, endY) || solve(x, y - 1, endX, endY)) {
            return true;
        }

        path.remove(path.size() - 1);
        maze[x][y] = ' ';
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
}
