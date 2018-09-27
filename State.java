import java.util.ArrayList;

public class State {

    public ArrayList<Integer> lList;
    public ArrayList<Integer> rList;
    State previous;  // parent state
    int timeCost;
    int priority;
    int subPriority;

    public State(ArrayList<Integer> l, ArrayList<Integer> r, int t, State ps) {
        lList = l;
        rList = r;
        timeCost = t;
        priority = 0;
        previous = ps;
    }

    public State(ArrayList<Integer> l, ArrayList<Integer> r, int t, int p, int sp, State ps) {
        lList = l;
        rList = r;
        timeCost = t;
        priority = p;
        subPriority = sp;
        previous = ps;
    }

    public String toString() {
        String str = "";
        if(previous != null) {
            str += previous.toString();
        }
        str += String.format( "%-20s /|BRIDGE|\\ %20s \n", lList.toString(), rList.toString());
        

        return str;
    }
}
