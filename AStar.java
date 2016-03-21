import java.util.*;
import java.io.*;

public class AStar {
    /* Global variables. */
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

        if (search())
            printPath();
        else
            System.out.println("Goal not found!");
    }

    /* Search method. */
    public static boolean search() {
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
                /* Eureka! */
                return true;
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
                        /* Discard adjNode move from open list. */
                        open.remove(adjNode);
                        
                        /* Add openNode to open list. */
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

        /* Goal not found! */
        return false;
    }

    /* Get best node by evaluating each node's f(n). */
    public static EightNode getBestNode() {
        /* Remove and return 0th element in the PriorityQueue. */
        return open.poll(); 
    }

    /* Print the shortest path. */
    public static void printPath() {
        /* Print some stuff. */
        System.out.println("PATH: ");

        /* Declare an optimal path list. */
        ArrayList<EightNode> path = new ArrayList<EightNode>();

        /* Find the goal node in closed list. */
        int goalIndex = 0;
        for (int i = 0; i < closed.size(); i++) {
            if (closed.get(i).getState().equals(goalState)) {
                goalIndex = i;
            }
        }

        /* Set the tempNode to the goal. */
        EightNode tempNode = closed.get(goalIndex);

        /* Add shortest path by iterating thru parents. */
        while (tempNode.parent != null) {
            path.add(tempNode);
            tempNode = tempNode.parent;
        }

        /* Print it. */
        for (int i = path.size()-1; i > -1; i--) {
            if (i == 0)
                System.out.println("GOAL:");
            path.get(i).printState();
        }
    }
}
