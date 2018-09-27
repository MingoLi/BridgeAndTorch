import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private int maxTime;
    private static int stateNum;  // Number of states generated
    private boolean found = false; // If fing the solution under maximun time

    private ArrayList<Integer> peopleList;  // List of people in the beginning

    private Queue<State> crossQueue = new LinkedList<>();  // Queue of people on the left side
    private Queue<State> backQueue = new LinkedList<>();  // Queue of people on the right side

    public BFS(int t, ArrayList<Integer> list) {
        maxTime = t;
        peopleList = list;
    }

    // Helper method
    public void doBFSHelper() {
        if(peopleList.size() > 0) {
            crossQueue = new LinkedList<>();
            backQueue = new LinkedList<>();
            crossQueue.add(new State(Util.copy(peopleList), new ArrayList<>(), 0, null));
            cross();
        }
        else {
            System.out.println( "No people found near the bridge." );
        }

        // fail case
        if( !found ) {
            System.out.println( "Failed to find a solution!");
            System.out.println( "Total number of states: " + stateNum );
        }
    }

    // Recursivly call function cross() and back() until find solution
    
    // cross()
    //   Method to check if I can move people on left side to right side or I am done.
    private void cross() {

        int i, j;  // for loop counter
        int tempA, tempB;   // temporary var to store a person's time cost
        int timeRecord, timeCost;   // Total time used so far

        ArrayList<Integer> templList;   // temporary list of people on left side.
        ArrayList<Integer> temprList;   // temporary list of people on right side.

        while( !crossQueue.isEmpty() && !found ) {
            State state = crossQueue.remove();
            timeRecord = state.timeCost;

            // base case: only one people
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


            } // base case: only two people
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


            }
            else {// more than two people
                // iterate the whole list, find all possible combinations
                for (i = 0; i < state.lList.size() - 1 && !found; i++) {
                    for( j = i+1; j < state.lList.size() && !found; j++ ) {
                        timeCost = timeRecord;
                        templList = Util.copy(state.lList);
                        temprList = Util.copy(state.rList);
                        tempA = templList.remove(i);    // get the speed of first person
                        tempB = templList.remove(j-1);  // get the speed of second person

                        temprList.add(tempA);   // add above two people to the right side
                        temprList.add(tempB);

                        timeCost += Math.max(tempA, tempB);
                        stateNum++;

                        backQueue.add(new State(templList, temprList, timeCost, state));

                        // Util.printState(state);
//                        Util.printStatistic(timeCost, stateNum);
                    }
                }
                while( !backQueue.isEmpty() ) {
                    back();
                }
            } // else case
        }
    } // cross

    // back()
    //   go back when torch is on right side
    private void back() {

        int i;
        int temp;
        int timeRecord, timeCost;

        ArrayList<Integer> templList;
        ArrayList<Integer> temprList;

        State state = backQueue.remove();
        timeRecord = state.timeCost;

        for( i = 0; i < state.rList.size() && !found; i++ ) {
            timeCost = timeRecord;
            templList = Util.copy(state.lList);
            temprList = Util.copy(state.rList);

            temp = temprList.remove(i);
            templList.add(temp);

            timeCost += temp;
            stateNum++;

            crossQueue.add( new State(templList, temprList, timeCost, state) );

            // Util.printState(state);

//            Util.printStatistic(timeCost, stateNum);
        }

    } // back
}
