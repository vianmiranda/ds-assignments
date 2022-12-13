package avengers;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        
        // read file names from command line
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int numOfGens = StdIn.readInt();
        int[] genVals = new int[numOfGens];
        double[] funcVals = new double[numOfGens];
        int[][] edgeVals = new int[numOfGens][numOfGens];

        for (int ii = 0; ii < numOfGens; ii++) {
            genVals[ii] = StdIn.readInt();
            funcVals[ii] = StdIn.readDouble();
        }

        for (int rr = 0; rr < numOfGens; rr++) {
            for (int cc = 0; cc < numOfGens; cc++) {
                edgeVals[rr][cc] = (int) (StdIn.readInt()/(funcVals[rr]*funcVals[cc]));

            }
        }

        int[] minCost = dijkstra(edgeVals);

        StdOut.print(minCost[numOfGens - 1]);
        
    }

    private static int[] dijkstra (int[][] ev) {
        int[] minCost = new int[ev.length];
        boolean[] dSet = new boolean[ev.length];
        
        for (int ii = 1; ii < minCost.length; ii++) minCost[ii] = Integer.MAX_VALUE;

        for (int ii = 0; ii < minCost.length; ii++) {
            int curr = getMinCostNode(minCost, dSet);
            dSet[curr] = true;

            for (int jj = 0; jj < ev.length; jj++) {
                int cumSum = minCost[curr] + ev[curr][jj];
                if (ev[curr][jj] != 0 && !dSet[jj] && cumSum < minCost[jj]) {
                    minCost[jj] = cumSum;
                }
            }

        }
        
        return minCost;
    }

    private static int getMinCostNode (int[] mc, boolean[] ds) {
        int min = Integer.MAX_VALUE, minNode = -1;

        for (int ii = 0; ii < mc.length; ii++) {
            if (!ds[ii] && mc[ii] < min) {
                min = mc[ii];
                minNode = ii;
            }
        }

        return minNode;
    }

}
