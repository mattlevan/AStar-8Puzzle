import java.util.*;
import java.io.*;

/* EightPuzzle uses EightNode objects as states. */
public class EightPuzzle {
    /* Global variables. */
    private final EightNode goal;
    private final EightNode start;
    private final ArrayList<Integer> goalState;
    private final ArrayList<Integer> startState;

    /* Default constructor. */
    public EightPuzzle() {
        /* Calculate values for constructing the start node. */
        goalState = genGoal();
        startState = genStart();
        //int numTiles = tilesOutOfPlace(startState);
        int manDist = manhattanDist(startState); 

        /* Construct start node. */ 
        start = new EightNode(0, manDist, startState);
        
        /* Construct goal node. */
        goal = new EightNode(0, 0, goalState);
    }

    /* Getters. */
    public EightNode getGoal() {
        return goal;
    }

    public EightNode getStart() {
        return start;
    }

    /* Generates a random start state. */
    public ArrayList<Integer> genStart() {
        ArrayList<Integer> tempStartState = new ArrayList<Integer>();

        /* Populate ArrayList with the goal state. */
		tempStartState.addAll(goalState);
		int tempStartH = tilesOutOfPlace(tempStartState);		
		EightNode tempStart = new EightNode(0, tempStartH, tempStartState);

        /* Generate random start state by making a random number of */
        /* moves, and randomly selected from child moves each time. */ 
		Random rand = new Random();
		int randMoves = rand.nextInt(36)+5; // Number of moves to make.
		for(int i=0; i<randMoves; i++){
		    Random rand2 = new Random(); // Which move to select.
			ArrayList<EightNode> moves = new ArrayList<EightNode>();
			moves = getMoves(tempStart);
			int moveSelect = rand2.nextInt(moves.size());
			tempStart = moves.get(moveSelect);	
		}		

        /* Ensure the start state isn't the goal state. */
        if (tempStart.equals(goal)) {
            genStart();
        }
		
        return tempStart.getState();
    }

    /* Generates the goal state. */
    public ArrayList<Integer> genGoal() {
        /* Generate goal ArrayList<Integer>. */
        ArrayList<Integer> goalList = new ArrayList<Integer>();
        
        goalList.add(0,1);
        goalList.add(1,2);
        goalList.add(2,3);
        goalList.add(3,4);
        goalList.add(4,5);
        goalList.add(5,6);
        goalList.add(6,7);
        goalList.add(7,8);
        goalList.add(8,0);

        return goalList;
    }

    /* Returns the legal moves from the given input node. */
    public ArrayList<EightNode> getMoves(EightNode parent) {
        /* Declare a list of possible moves. */
        ArrayList<EightNode> children = new
            ArrayList<EightNode>();

        /* Gather attributes. */
        ArrayList<Integer> parentState = 
            new ArrayList<Integer>(parent.getState().size()); 
        parentState.addAll(parent.getState());
        int parent_g = parent.getG(); // Distance from start.

        /* Apply the rules of the game and return a legal moves index. */
        ArrayList<Integer> movesIndex = 
            new ArrayList<Integer>(4); 
        movesIndex.addAll(findMoves(parentState));

        /* Generate the moves and add them to children. */
        for (int i = 0; i < movesIndex.size(); i++) {
            /* Get index of blank tile, or zero tile. */
            int zeroIndex = parentState.indexOf(0);
        
            /* Re-initialize childState. */
            ArrayList<Integer> childState = 
                new ArrayList<Integer>(parentState.size());
            childState.addAll(parentState);

            /* Gather the child's values. */ 
            int tileIndex = movesIndex.get(i);
            int tile = childState.get(tileIndex);
            
            /* Generate possible moves by swapping tiles. */
            childState.set(zeroIndex, tile);
            childState.set(tileIndex, 0);

            /* Calculate child's heuristics for construction. */
            int child_g = parent_g + 1; // One level deeper than parent.
            // int child_h = manhattanDist(childState);
            int child_h = tilesOutOfPlace(childState); 

            /* Construct child and set the child's state. */
            EightNode child = new EightNode(child_g, child_h, childState);

            /* Add the child to the list of children. */
            children.add(child);
        }

        return children;
    }

    /* Applies the rules of the game and returns a list of legal move 
     * indices. */
    private ArrayList<Integer> findMoves(ArrayList<Integer> state) {
        int zeroIndex = state.indexOf(0); 
        ArrayList<Integer> movesIndex = new ArrayList<Integer>();
        
        /* Rules of the game. */
        if (zeroIndex % 3 > 0)
            movesIndex.add(zeroIndex-1);
        if (zeroIndex % 3 < 2)
            movesIndex.add(zeroIndex+1);
        if (zeroIndex > 2)
            movesIndex.add(zeroIndex-3);
        if (zeroIndex < 6)
            movesIndex.add(zeroIndex+3);

        return movesIndex; 
    }

    /* Calculates number of tiles out of place. */
    public int tilesOutOfPlace(EightNode node) {
        /* Declare, initialize counter for numTiles out of place. */
        int numTiles = 0;

        /* Get the state from the node. */
        ArrayList<Integer> nodeState = node.getState();
        /* Get the goal state from the goal node. */
        ArrayList<Integer> goalState = goal.getState();
        
        /* Calculate numTiles out of place. */
        for (int i = 1; i < nodeState.size(); i++) {
            if (nodeState.get(i) != goalState.get(i))
                numTiles++; 
        }

        return numTiles;
    }
    
    /* Calculates number of tiles out of place, overloaded. */
    public int tilesOutOfPlace(ArrayList<Integer> nodeState) {
        /* Declare, initialize counter for numTiles out of place. */
        int numTiles = 0;


        /* Calculate numTiles out of place. */
        for (int i = 1; i < nodeState.size(); i++) {
            if (nodeState.indexOf(i) != goalState.indexOf(i))
                numTiles++; 
        }

        return numTiles;
    }

	/* Calculates sum of distances of each tile from goal position. */
	public int manhattanDist(ArrayList<Integer> nodeState){
		int totalDist = 0; // Sum of the individual distances.
		int currentTile;
		int goalTileIndex; // The index of the 
		int rowDist;
		int colDist;
		/* Loop through each nodeState element, and calc M. Dist. */
		for(int i=0; i<nodeState.size(); i++){
			currentTile = nodeState.get(i);  
			goalTileIndex = goalState.indexOf(currentTile);
			/* Calculate the col. distance (mod distance) from goal. */
			colDist = Math.abs((i%3) - (goalTileIndex%3));	
			/* Calculate the row distance (div. distance) from goal. */
			rowDist = Math.abs((i/3) - (goalTileIndex/3));
			totalDist += colDist + rowDist;
			// System.out.println("currentTile Index: "+i+" goalIndex: " 
            // + goalTileIndex + " Col: " + colDist +" Row: " + rowDist + 
            // " MDist: " + (colDist + rowDist)); 
		}
		return totalDist;
	}
}
