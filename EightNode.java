import java.util.*;
import java.io.*;

/* EightPuzzle uses EightNode objects as states. */
public class EightNode implements Comparable<EightNode>, Comparator<EightNode> {
    /* Global variables. */

    /* Parent node. */
    private EightNode parent;
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
    public EightNode(EightNode node) {
        ArrayList<Integer> state = node.getState();
        EightNode parent = node.getParent();
        int f = node.getF();
        int g = node.getG();
        int h = node.getH();

        setState(state);
        setParent(parent);
        setF(f);
        setG(g);
        setH(h);
    }
        
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

    @Override
    /* Override Comparator method. */
    public int compare(EightNode one, EightNode two) {
        /* Get f(n) values for each node. */
        int f_one = one.getF();
        int f_two = two.getF();

        return f_two - f_one;
    }

    @Override
    /* Override Comparable method. */
    public int compareTo(EightNode other) {
        /* Get f(n) values for each node. */
        int f_one = this.getF();
        int f_two = other.getF();

        return f_two - f_one;
    }

    @Override
    /* Override equals method. */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        EightNode node = new EightNode((EightNode)obj);

        return node.getState().equals(this.getState());
    }

    @Override
    /* Override hashCode method. */
    public int hashCode() {
        int code = 1;
        ArrayList<Integer> state = this.getState();

        for (int i = 0; i < state.size(); i++) {
            code *= state.get(i);
            code *= i;
            code /= (i+1);
        }

        return code;
    }

    /* Print state method. */
    public void printState() {
        for (int i = 0; i < state.size(); i++) {
            System.out.print(state.get(i));

            if ((i+1) == 3) {
                System.out.print("\tf: " + getF());
                System.out.println("");
            }
            else if ((i+1) == 6) {
                System.out.print("\tg: " + getG());
                System.out.println("");
            }
            else if ((i+1) == 9) {
                System.out.print("\th: " + getH());
                System.out.println("");
            }
        }

        System.out.println("");
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

    public EightNode getParent() {
        return parent;
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
        this.state = new ArrayList<Integer>(state);
    }

    public void setParent(EightNode parent) {
        this.parent = parent;
    }
}
