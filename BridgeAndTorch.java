import java.util.ArrayList;
import java.util.Arrays;

public class BridgeAndTorch {

    public static void main(String[] args) {
        // For test usage
//        String[] testData = {"28", "5", "7", "3", "10"};
//        String[] testData = {"15", "1", "8", "5", "2"};
//        String[] testData = {"100", "10", "5", "7", "3"};
//        args = testData;

        // Reading in data from command line
        int maxTime = Integer.parseInt(args[0]);

        String[] restArgs = Arrays.copyOfRange(args,1, args.length);
        int[] intArray = Arrays.stream(restArgs).mapToInt(Integer::parseInt).toArray();

        ArrayList<Integer> peopleList = new ArrayList<>();
        for( int i = 0; i < intArray.length; i++ ) {
            peopleList.add(intArray[i]);
        }

        // Do and print out the search result
        System.out.println("Heuristic - use Beam Search ");
        // System.out.println("For crossing the bridge(left to right), \n" +
        //         "Find out all the possible combination of two people per group first, \n" +
        //         "and then try the group that the people in the group have the most nearest speed first.\n" +
        //         "For coming back the bridge(right to left), \n" +
        //         "Iterate through all the people on the right side and try the person with shortest time first. \n" +
        //         "Until all the people have crossed the bridge. \n");
        Heuristic h = new Heuristic(maxTime, peopleList);
        h.doHeuristicHelper();
        System.out.println("\n\n");

        System.out.println("Breath-first Search\n");
        BFS bfs = new BFS(maxTime, peopleList);
        bfs.doBFSHelper();
        System.out.println("\n\n");

        System.out.println("Depth-first Search\n");
        DFS dfs = new DFS(maxTime, peopleList);
        dfs.doDFSHelper();
        System.out.println("\n\n");

        System.out.println("Iterative Deepening\n");
        IterativeDeepening id = new IterativeDeepening(maxTime, peopleList);
        id.doIDHelper();
        System.out.println("End of Processing");

    }




}
