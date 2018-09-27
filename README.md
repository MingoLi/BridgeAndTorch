# Bridge and Torch
### Background 
There are some number of people on one side of a bridge waiting to cross to the other. But it is dark, and they only have one flashlight between them. Furthermore, the bridge is old and it is only safe for one or two people to cross at a time. Each person may also move at a different speed, and when a pair of people cross, they move at the speed of the slower of the pair.

### Instruction
- To compile:
    `javac *.java`  
- To run:
    `java BridgeAndTorch [time] [speed 1] [speed 2] [speed 3] [speed 4]`  
    e.g. `java BridgeAndTorch 28 5 7 3 10`  
    Where [time] is the minimum acceptable solution time, which means the program will try to solve it in 28 units of time or less.

### Sample output
```
$ java BridgeAndTorch 28 5 7 3 10
Heuristic - use Beam Search
[5, 7, 3, 10]        /|BRIDGE|\                   []
[7, 10]              /|BRIDGE|\               [5, 3]
[7, 10, 3]           /|BRIDGE|\                  [5]
[3]                  /|BRIDGE|\           [5, 7, 10]
[]                   /|BRIDGE|\        [7, 10, 3, 5]
Total time cost is: 28
Total number of states: 15



Breath-first Search

[5, 7, 3, 10]        /|BRIDGE|\                   []
[7, 10]              /|BRIDGE|\               [5, 3]
[7, 10, 5]           /|BRIDGE|\                  [3]
[5]                  /|BRIDGE|\           [3, 7, 10]
[]                   /|BRIDGE|\        [7, 10, 5, 3]
Total time cost is: 28
Total number of states: 181



Depth-first Search

[5, 7, 3, 10]        /|BRIDGE|\                   []
[7, 10]              /|BRIDGE|\               [5, 3]
[7, 10, 5]           /|BRIDGE|\                  [3]
[5]                  /|BRIDGE|\           [3, 7, 10]
[]                   /|BRIDGE|\        [7, 10, 5, 3]
Total time cost is: 28
Total number of states: 50



Iterative Deepening

[5, 7, 3, 10]        /|BRIDGE|\                   []
[7, 10]              /|BRIDGE|\               [5, 3]
[7, 10, 5]           /|BRIDGE|\                  [3]
[5]                  /|BRIDGE|\           [3, 7, 10]
[]                   /|BRIDGE|\        [7, 10, 5, 3]
Total time cost is: 28
Total number of states: 290
End of Processing
```