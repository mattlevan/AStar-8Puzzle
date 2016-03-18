import java.util.*;
import java.io.*;

/* EightPuzzle uses EightNode objects as states. */
public class EightNode implements Comparable<EightNode> {
    /* Global variables. */

    /* State as an ArrayList<Integer>. */
    private ArrayList<Integer> state;

    /* Heuristics. */
    
    /* f(n) = g(n) + h(n). */
    private int f; 
    /* g(n) is the distance from state to start. */
    private int g;
    /* h(n) is the heuristic value. */
    private int h; 

    /* Constructors. */
    public EightNode(int g, int h) {
        setF(g+h);
        setG(g);
        setH(h);
    }
    
    public EightNode(int g, int h, ArrayList<Integer> state) {
        setF(g+h);
        setG(g);
        setH(h);
        setState(state);
    }

    /* Override Comparable method. */
    public int compareTo(EightNode other) {
        /* Get f(n) values for each node. */
        int f_one = getF();
        int f_two = other.getF();

        if (f_one < f_two)
            return -1;
        else if (f_one > f_two)
            return 1;
        else if (f_one == f_two)
            return 0;
        else
            return 9;
    }

    /* Print state method. */
    public void printState() {
        for (int i = 0; i < state.size(); i++) {
            System.out.print(state.get(i));

            if ((i+1)%3 == 0)
                System.out.println("");
        }
    }

    /* Getters. */
    public ArrayList<Integer> getState() {
        if (state != null) {
            return state;
        }
        else {
            System.out.println("No state exists!");
            return null;
        }
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    /* Setters. */
    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }
    
    public void setState(ArrayList<Integer> state) {
        this.state = state;
    }
}
