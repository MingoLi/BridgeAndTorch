
import java.util.ArrayList;

public class Util {

    // public static void printState(String l, String r) {
    //     System.out.printf( "%-20s /|BRIDGE|\\ %20s \n", l, r);
    // }

    public static void printState(State s) {
        System.out.printf( s.toString() );
    }

    public static void printStatistic(int timeCost, int stateNum) {
        System.out.println( "Total time cost is: " + timeCost );
        System.out.println( "Total number of states: " + stateNum );
    }

    public static ArrayList<Integer> copy(ArrayList<Integer> list) {

        ArrayList<Integer> newList = new ArrayList<>();
        for( int i = 0; i < list.size(); i++ ) {
            newList.add(list.get(i));
        }
        return newList;
    }
}
