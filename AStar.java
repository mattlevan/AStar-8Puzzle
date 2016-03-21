import java.util.*;
import java.io.*;

public class AStar {
    /* Global variables. */
    /* Debug statement flag. */
    private static final boolean debug = false;
    /* Puzzle. */
    private static EightPuzzle ep = new EightPuzzle();
    /* Nodes. */
    private static final EightNode start = ep.getStart();
    private static final EightNode goal = ep.getGoal();
    /* States. */
    private static final ArrayList<Integer> startState = start.getState();
    private static final ArrayList<Integer> goalState = goal.getState();
    /* State lists. */
    private static PriorityQueue<EightNode> open =
        new PriorityQueue<EightNode>();
    private static ArrayList<EightNode> closed = 
        new ArrayList<EightNode>();

    /* Main method. */
    public static void main(String[] args) {
        open.add(start);
        
        System.out.println("START: ");
        start.printState();

        System.out.println("GOAL: ");
        goal.printState();

        search();
        
        printPath();
    }

    /* Search method. */
    public static void search() {
        /* Perform search while there are more nodes to be processed. */
        while (open.size() > 0) {
            /* Remove the best node (parent) from open list (least f(n)). */
            EightNode parent = getBestNode();
            /* Get state. */
            ArrayList<Integer> parentState = 
                new ArrayList<Integer>();
            parentState.addAll(parent.getState());
            
            /* Check if parent is the goal. */
            if (parentState.equals(goalState)) {
                /* Add it to closed so it prints in path. */
                closed.add(parent);
                /* Done. */
                break;
            }
            
            /* Add parent to closed list. */
            closed.add(parent);
            
            /* Expand parent to all adjacent nodes. */
            ArrayList<EightNode> moves = ep.getMoves(parent); 

            /* Loop through the adjacent nodes (moves). */
            for (int i = 0; i < moves.size(); i++) {
                /* Assign adjNode. */
                EightNode adjNode = moves.get(i);
                /* Assign adjNodeState. */
                ArrayList<Integer> adjNodeState = 
                    new ArrayList<Integer>();
                adjNodeState.addAll(adjNode.getState());

                /* If adjacent node is already in closed set... */
                if (closed.contains(adjNode)) {
                    /* Discard move. */ 
                    moves.remove(adjNode);
                    /* Continue next iteration of for loop. */
                    continue;
                }
                /* Else, if adjacent node is already in open set... */
                else if (open.contains(adjNode)) {
                    /* Assign openNode. */
                    EightNode openNode = new EightNode(adjNode);

                    /* Get g(n) for adjNode and open nodes. */
                    int adjNode_g = adjNode.getG();
                    int openNode_g = openNode.getG();

                    /* If adjNode g(n) < openNode g(n)... */
                    if (openNode_g < adjNode_g) {
                        /* Discard openNode move from open list. */
                        open.remove(adjNode);
                        
                        /* Add adjNode to open list. */
                        open.add(openNode);
                        continue;
                    }
                }
                else {
                    /* Set adjNode's parent. */
                    adjNode.setParent(parent);

                    /* Add adjNode to open list. */
                    open.add(adjNode);
                }
            }
        }
    }

    /* Get best node by evaluating each node's f(n). */
    public static EightNode getBestNode() {
        /* Remove and return 0th element in the PriorityQueue. */
        return open.poll(); 
    }

    /* Print the shortest path. */
    public static void printPath() {
        System.out.println("PATH (" + closed.size() + "): ");
        for (int i = 0; i < closed.size(); i++) {
            closed.get(i).printState();
        }
    }
}
