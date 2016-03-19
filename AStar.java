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
            ArrayList<Integer> parentState = parent.getState();
            
            /* Check if parent is the goal. */
            if (parentState.equals(goalState)) {
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
                EightNode adjNode = new EightNode(moves.get(i));
                /* Assign adjNodeState. */
                ArrayList<Integer> adjNodeState = adjNode.getState();

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
                    Iterator<EightNode> openItr = open.iterator();
                    EightNode openNode = null;
                    while (openItr.hasNext()) {
                        EightNode tempNode = new EightNode(openItr.next());
                        if (tempNode.equals(adjNode))
                            openNode = new EightNode(tempNode);
                    }

                    /* Get f(n) for adjNode and open nodes. */
                    int adjNode_f = adjNode.getF();
                    int openNode_f = openNode.getF();

                    /* If adjNode g(n) < open g(n)... */
                    if (adjNode_f < openNode_f) {
                        /* Discard openNode move from open list. */
                        open.remove(openNode);
                        
                        /* Link adjNode to the parent. */
                        adjNode.setParent(parent);
    
                        /* Add adjNode to open list. */
                        open.add(adjNode);
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
        ArrayList<EightNode> path = new ArrayList<EightNode>();
        EightNode node = new EightNode(closed.get(closed.size()-1));

        while (node != null) {
            path.add(node);
            node = node.getParent();
        }

        System.out.println("PATH (" + path.size() + "):");

        for (int i = path.size()-1; i >= 0; i--) {
            path.get(i).printState();
        }
    }
}
