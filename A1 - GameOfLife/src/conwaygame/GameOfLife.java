package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);

        int row = StdIn.readInt();
        int col = StdIn.readInt();
        grid = new boolean[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c] = StdIn.readBoolean() ? ALIVE : DEAD;
                if(grid[r][c]) totalAliveCells++;
            }
        }

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE 
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (getCellState(r, c)) return true;
            }
        }

        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) { 

        // WRITE YOUR CODE HERE
        int count = 0, rmod = grid.length, cmod = grid[0].length;

        if (grid[((row - 1) + rmod) % rmod][col]) count++;
        if (grid[(row + 1) % rmod][col]) count++;
        if (grid[row][((col - 1) + cmod) % cmod]) count++;
        if (grid[row][(col + 1) % cmod]) count++;
        if (grid[((row - 1) + rmod) % rmod][((col - 1) + cmod) % cmod]) count++;
        if (grid[(row + 1) % rmod][((col - 1) + cmod) % cmod]) count++;
        if (grid[((row - 1) + rmod) % rmod][(col + 1) % cmod]) count++;
        if (grid[(row + 1) % rmod][(col + 1) % cmod]) count++;

        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c]) {
                    if (numOfAliveNeighbors(r, c) <= 1 || numOfAliveNeighbors(r, c) >= 4) {
                        newGrid[r][c] = DEAD;
                    } else {
                        newGrid[r][c] = ALIVE;
                    }
                } else if (numOfAliveNeighbors(r, c) == 3) {
                    newGrid[r][c] = ALIVE;
                }

            }
        }

        return newGrid; // update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        grid = computeNewGrid();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for (int gen = 0; gen < n; gen++) {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        int rmod = grid.length, cmod = grid[0].length;
        WeightedQuickUnionUF u = new WeightedQuickUnionUF(rmod, cmod);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col]) {
                    if (grid[((row - 1) + rmod) % rmod][col]) u.union(row, col, ((row - 1) + rmod) % rmod, col);
                    if (grid[(row + 1) % rmod][col]) u.union(row, col, (row + 1) % rmod, col);
                    if (grid[row][((col - 1) + cmod) % cmod]) u.union(row, col, row, ((col - 1) + cmod) % cmod);
                    if (grid[row][(col + 1) % cmod]) u.union(row, col, row, (col + 1) % cmod);
                    if (grid[((row - 1) + rmod) % rmod][((col - 1) + cmod) % cmod]) u.union(row, col, ((row - 1) + rmod) % rmod, ((col - 1) + cmod) % cmod);
                    if (grid[(row + 1) % rmod][((col - 1) + cmod) % cmod]) u.union(row, col, (row + 1) % rmod, ((col - 1) + cmod) % cmod);
                    if (grid[((row - 1) + rmod) % rmod][(col + 1) % cmod]) u.union(row, col, ((row - 1) + rmod) % rmod, (col + 1) % cmod);
                    if (grid[(row + 1) % rmod][(col + 1) % cmod]) u.union(row, col, (row + 1) % rmod, (col + 1) % cmod);
                }
            }
        }    
        
        ArrayList<Integer> arr = new ArrayList();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col]) arr.add(u.find(row, col) );

            }
        }  

        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i) == arr.get(j)) arr.remove(j--);
            }
        }

        return arr.size(); // update this line, provided so that code compiles
    }
}
