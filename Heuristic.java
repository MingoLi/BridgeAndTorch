import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Heuristic {

    private int maxTime;    // maximum time available for people to cross the bridge
    private static int stateNum;    // total number of states generated
    private boolean found = false;  // check if find the solution under time limit

    private ArrayList<Integer> peopleList;  // list of people waiting to cross

    Comparator<State> comparator = new HeuristicComparator();   // used to compare the priority of each state

    public Heuristic(int t, ArrayList<Integer> list) {
        maxTime = t;
        peopleList = list;
    }

    // helper method
    public void doHeuristicHelper() {
        if(peopleList.size() > 0) {
            Queue<State> queue = new PriorityQueue<>(comparator);
            queue.add(new State(Util.copy(peopleList), new ArrayList<>(), 0, 0, 0, null));
            cross(queue);
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
    //
    //   @para: queue: queue of states waiting to be executed
    private void cross( Queue<State> queue ) {

        int i, j;  // for loop counter
        int tempA, tempB;   // temporary var to store a person's time cost
        int timeRecord, timeCost, time; 
        int priority;   // priority of each state

        ArrayList<Integer> templList;   // temporary list of people on left side.
        ArrayList<Integer> temprList;   // temporary list of people on right side.

        while( !queue.isEmpty() && !found ) {
            State state = queue.remove();
            timeRecord = state.timeCost;

            // base case: only one person
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
                
                // creat a priority queue to store all the children generated this level
                Queue<State> subQueue = new PriorityQueue<>(comparator);

                for (i = 0; i < state.lList.size() - 1 && !found; i++) {
                    for( j = i+1; j < state.lList.size() && !found; j++ ) {
                        timeCost = timeRecord;
                        templList = Util.copy(state.lList);
                        temprList = Util.copy(state.rList);
                        tempA = templList.remove(i);    // get the speed of first person
                        tempB = templList.remove(j-1);  // get the speed of second person

                        temprList.add(tempA);   // add above two people to the right side
                        temprList.add(tempB);
                        
                        // priority is the difference between two people.
                        // people with nearer speed will have higher priority.
                        priority = Math.abs(tempA - tempB);

                        // If two group of people have same priority, then compare their speed,
                        // Higher speed will have higher priority.
                        time = Math.max(tempA, tempB);
                        timeCost += time;
                        stateNum++;

                        subQueue.add(new State(templList, temprList, timeCost, priority, time, state));

                        // Util.printState(templList.toString(), temprList.toString());
//                        Util.printStatistic(timeCost, stateNum);
                    }
                }
                back(subQueue);
            } // else case
        }
    } // cross
    
    // back()
    //   go back when torch is on right side
    //
    //   @para: queue: queue of states waiting to be executed
    private void back( Queue<State> queue) {

        int i;
        int temp;
        int timeRecord, timeCost;

        while( !queue.isEmpty() && !found ) {
            Queue<State> subQueue = new PriorityQueue<>(comparator);

            ArrayList<Integer> templList;
            ArrayList<Integer> temprList;

            State state = queue.remove();
            timeRecord = state.timeCost;

            for( i = 0; i < state.rList.size() && !found; i++ ) {
                timeCost = timeRecord;
                templList = Util.copy(state.lList);
                temprList = Util.copy(state.rList);

                temp = temprList.remove(i);  // get the speed of a person
                templList.add(temp);

                timeCost += temp;
                stateNum++;

                // In this case, priority is their speed.
                subQueue.add( new State(templList, temprList, timeCost, temp, 0, state) );

                // Util.printState(templList.toString(), temprList.toString());
//                Util.printStatistic(timeCost, stateNum);
            }
            cross(subQueue);
        }
    } // back

}
