import java.util.*;
import java.io.*;

public class EightPuzzle {
    /* Global variables. */
    public static ArrayList<Integer> goal;
    public static ArrayList<Integer> start;


    /* Default constructor. */
    public EightPuzzle() {
        /* Declare and initialize goal with appropriate tiles, in order. */    
        ArrayList<Integer> goal = new ArrayList<Integer>();
        goal.add(0, 1);
        goal.add(1, 2);
        goal.add(2, 3);
        goal.add(3, 8);
        goal.add(4, 0);
        goal.add(5, 4);
        goal.add(6, 7);
        goal.add(7, 6);
        goal.add(8, 5);

        /* Declare and initialize a start state. */
        ArrayList<Integer> start = genStart();
    }


    /* Generates a random start state. */
    public ArrayList<Integer> genStart() {
        ArrayList<Integer> start = new ArrayList<Integer>();

        /* Populate ArrayList with integers 0-8, 0 is blank tile. */
        for (int i = 0; i < 10; i++) {
            start.add(i);
        }

        /* Shuffle it! */
        Collections.shuffle(start);

        /* Ensure the start state isn't the goal state. */
        if (start == goal) {
            genStart();
        }
            
        return start;
    }


    /* Returns the legal moves from the given input state. */
    public ArrayList<ArrayList<Integer>> genMoves(ArrayList<Integer> parent) {
        /* Declare a list of possible moves. */
        ArrayList<ArrayList<Integer>> children = new
            ArrayList<ArrayList<Integer>>();

        /* Apply the rules of the game and return a legal moves index. */
        ArrayList<Integer> movesIndex = findMoves(parent);

        /* Generate the moves and add them to children. */
        for (int i = 0; i < movesIndex.size(); i++) {
            /* The child starts by inheriting its parent's state. */
            ArrayList<Integer> child = parent;

            /* Then, the child is altered by swapping. */
            int zeroIndex = child.indexOf(0);
            int tile = child.get(movesIndex.get(i));
            int tileIndex = child.indexOf(tile);

            child.set(zeroIndex, tile);
            child.set(tileIndex, 0);

            children.add(child);
        }

        return children;
    }


    /* Applies the rules of the game and returns a list of legal move 
     * indices. */
    public ArrayList<Integer> findMoves(ArrayList<Integer> state) {
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
}
