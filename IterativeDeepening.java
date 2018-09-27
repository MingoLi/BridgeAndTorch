import java.util.ArrayList;
import java.util.Stack;

public class IterativeDeepening {

    private int maxTime;
    private static int stateNum = 0;
//    private int maxDepth;   // maximum depth allowed
    private boolean found = false;
    private boolean finished = false;  // to check if have genetated all possible states

    private ArrayList<Integer> peopleList;

    private Stack<State> stack;

    public IterativeDeepening(int t, ArrayList<Integer> list) {
        maxTime = t;
        peopleList = list;
//        maxDepth = d;
    }

    public void doIDHelper() {
        if(peopleList.size() > 0) {
            int depth = 0;
            stack = new Stack<>();
            ID(depth);
        }
        else {
            System.out.println( "No people found near bridge." );
        }

        if( !found ) {
            System.out.println( "Failed to find a solution!");
            System.out.println( "Total number of states: " + stateNum );
        }
    }

    private void ID(int depth) {

        stack.push(new State(Util.copy(peopleList), new ArrayList<>(), 0, null));

        if( found || finished ) {
            // do nothing, we are done
        }
        else {
            cross(depth);
            ID(depth + 1);
        }
    }

    private void cross(int depth) {

        int i;
        int j;
        int tempA;
        int tempB;
        int timeRecord, timeCost;

        State state = stack.pop();

        ArrayList<Integer> templList;
        ArrayList<Integer> temprList;

        timeRecord = state.timeCost;

        if( depth > 0 ) {
            // base cases
            // only one person
            if(state.lList.size() == 1 && !found) {
                timeCost = timeRecord;
                timeCost += state.lList.get(0);
                stateNum++;
                state.rList.add(state.lList.remove(0));

                if(timeCost <= maxTime) {
                    found = true;
                    Util.printState(state);
                    Util.printStatistic(timeCost, stateNum);
                }

                finished = true;
            } // only two people
            else if(state.lList.size() == 2 && !found) {
                timeCost = timeRecord;
                timeCost += Math.max(state.lList.get(0), state.lList.get(1));
                stateNum++;
                state.rList.add(state.lList.remove(0));
                state.rList.add(state.lList.remove(0));

                if(timeCost <= maxTime) {
                    found = true;
                    Util.printState(state);
                    Util.printStatistic(timeCost, stateNum);
                }

                finished = true;
            }
            else {// more than two people
                // iterate the whole list, find all possible combinations
                for (i = 0; i < state.lList.size() - 1 && !found; i++) {
                    for( j = i+1; j < state.lList.size() && !found; j++ ) {
                        timeCost = timeRecord;
                        templList = Util.copy(state.lList);
                        temprList = Util.copy(state.rList);
                        tempA = templList.remove(i);
                        tempB = templList.remove(j-1);

                        temprList.add(tempA);
                        temprList.add(tempB);

                        timeCost += Math.max(tempA, tempB);
                        stateNum++;

                        stack.push(new State(templList, temprList, timeCost, state));

                        // Util.printState(templList.toString(), temprList.toString());
//                        Util.printStatistic(timeCost, stateNum);

                        depth--;
                        if( depth > 0 )
                            back(depth);
                        depth++;
                    }
                }
            } // else case
        }
    } // cross

    private void back(int depth) {

        int i;
        int temp;
        int timeRecord, timeCost;

        ArrayList<Integer> templList;
        ArrayList<Integer> temprList;

        State state = stack.pop();
        timeRecord = state.timeCost;

        for( i = 0; i < state.rList.size() && !found; i++ ) {
            timeCost = timeRecord;
            templList = Util.copy(state.lList);
            temprList = Util.copy(state.rList);
            temp = temprList.remove(i);

            templList.add(temp);

            timeCost += temp;
            stateNum++;

            stack.push(new State(templList, temprList, timeCost, state));

            // Util.printState(templList.toString(), temprList.toString());
//            Util.printStatistic(timeCost, stateNum);

            depth--;
            if( depth > 0 )
                cross(depth);
            depth++;
        }
    } // back

}
