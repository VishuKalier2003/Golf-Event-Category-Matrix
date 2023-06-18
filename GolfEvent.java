/* You are asked to cut off all the trees in a forest for a golf event. The forest is represented as an m x n matrix. 
In this matrix:
1)- 0 means the cell cannot be walked through.
2)- 1 represents an empty cell that can be walked through.
3)- A number greater than 1 represents a tree in a cell that can be walked through, and this number is the tree's 
height. In one step, you can walk in any of the four directions: north, east, south, and west. If you are standing 
in a cell with a tree, you can choose whether to cut it off. 
You must cut off the trees in order from shortest to tallest. When you cut off a tree, the value at its cell becomes 
1 (an empty cell). Starting from the point (0, 0), return the minimum steps you need to walk to cut off all the 
trees. If you cannot cut off all the trees, return -1. Note: The input is generated such that no two trees have the 
same height, and there is at least one tree needs to be cut off.
* Eg 1 :  grid = [[1,2,3],[0,0,4],[5,6,7]]       Output = 6  
* Eg 2 :  grid = [[1,2,3],[0,0,0],[4,8,0]]       Output = -1 
* Eg 3 :  grid = [[2,3,4],[0,0,5],[7,8,9]]       Output = 8  
* Eg 4 :  grid = [[2,3,4],[0,0,5],[9,8,7]]       Output = 6  
* Eg 5 :  grid = [[2]]                           Output = 0  
*/
import java.util.*;
public class GolfEvent
{
      public static boolean possibility = true;
      int MinimumStepsToCutTrees(int grid[][])
      {
            possibility = true;
            Map<Integer, Integer> treeMap = new HashMap<Integer, Integer>();   //* HashMap -> O(N x M)
            // HashMap used to store the steps for every tree...
            PriorityQueue<Integer> MinHeap = new PriorityQueue<Integer>();     //* MinHeap -> O(N x M)
            // Priority Queue used to store the tree in ascending order of their heights...
            boolean canVisit[][] = new boolean[grid.length][grid[0].length];   //* Matrix -> O(N x M)
            for(int i = 0; i < canVisit.length; i++)     //! Initialising the Visit Grid -> O(N x M)
                  Arrays.fill(canVisit[i], false);
            IfAllTreesCanBeCut(grid, canVisit, 0, 0);        //! Function Call -> O(N x M)
            for(int i = 0; i < grid.length; i++)      //! Grid Traversal -> O(N x M)
            {
                  for(int j = 0; j < grid[0].length; j++)
                  {
                        if(grid[i][j] > 1)    // Adding the Trees to the MinHeap...
                              MinHeap.add(grid[i][j]);
                  }
            }
            MinimumStepsToReachNthTree(1, grid, treeMap);     //! Function Call -> O(N x M)
            if((possibility == false) || (grid[0][0] == 0))      return -1;     // If a tree cannot be reached...
            int val = MinHeap.size(), distance = 0, first = MinHeap.peek();
            while(!MinHeap.isEmpty())     //! Emptying MinHeap -> O(N x M)
            {
                  if(val == 1)    // If only one element is left in the MinHeap...
                        break;
                  int x = MinHeap.poll();
                  distance = distance + Math.abs(treeMap.get(x) - treeMap.get(MinHeap.peek()));   // Getting distance...
                  val--;    // Decrementing size of the MinHeap...
            }
            distance = distance + treeMap.get(first);
            return distance;      // Returning the distance as the number of Steps...
      }
      public void MinimumStepsToReachNthTree(int moves, int grid[][], Map<Integer, Integer> map)
      {
            Queue<int[]> searchQueue = new LinkedList<int[]>();    //* Queue Declared -> O(N x M)
            searchQueue.add(new int[]{0, 0});
            if(grid[0][0] > 1)      map.put(grid[0][0], 0);    // base condition...
            grid[0][0] = 1;
            while(!searchQueue.isEmpty())    //! Breadth First Search -> O(N x M)
            {     // The Values will be stored in the HashMap...
                  for(int i = 0; i < searchQueue.size(); i++)    // Iterating the Queue...
                  {
                        int temp[] = searchQueue.poll();
                        if((temp[0] > 0) && (grid[temp[0] - 1][temp[1]] > 1))   // Checking Upwards...
                        {
                              map.put(grid[temp[0] - 1][temp[1]], moves);
                              searchQueue.add(new int[]{temp[0] - 1, temp[1]});
                              grid[temp[0] - 1][temp[1]] = 1;    // Cutting the tree...
                        }
                        if((temp[0] < grid.length - 1) && (grid[temp[0] + 1][temp[1]] > 1))  // Checking Downwards...
                        {
                              map.put(grid[temp[0] + 1][temp[1]], moves);
                              searchQueue.add(new int[]{temp[0] + 1, temp[1]});
                              grid[temp[0] + 1][temp[1]] = 1;     // Cutting the tree...
                        }
                        if((temp[1] > 0) && (grid[temp[0]][temp[1] - 1] > 1))    // Checking Backwards...
                        {
                              map.put(grid[temp[0]][temp[1] - 1], moves);
                              searchQueue.add(new int[]{temp[0], temp[1] - 1});
                              grid[temp[0]][temp[1] - 1] = 1;    // Cutting the tree...
                        }
                        if((temp[1] < grid[0].length - 1) && (grid[temp[0]][temp[1] + 1] > 1))  // Checking Forwards...
                        {
                              map.put(grid[temp[0]][temp[1] + 1], moves);
                              searchQueue.add(new int[]{temp[0], temp[1] + 1});
                              grid[temp[0]][temp[1] + 1] = 1;      // Cutting the tree...
                        }
                  }
                  moves++;     // Incrementing the Depth count of the Breadth First Search...
            }
            return;
      }
      public void IfAllTreesCanBeCut(int grid[][], boolean canVisit[][], int row, int col)
      {
            if((row < 0) || (col < 0) || (row == grid.length) || (col == grid[0].length) || (grid[row][col] == 0)
            || (canVisit[row][col] == true))    // Base condition for Recursion...
                  return;
            canVisit[row][col] = true;    // Setting the visited grid cell as true...
            IfAllTreesCanBeCut(grid, canVisit, row + 1, col);     // Downward Recursion...
            IfAllTreesCanBeCut(grid, canVisit, row, col + 1);     // Forward Recursion...
            IfAllTreesCanBeCut(grid, canVisit, row - 1, col);     // Upward Recursion...
            IfAllTreesCanBeCut(grid, canVisit, row, col - 1);     // Backward Recursion...
            for(int i = 0; i < canVisit.length; i++)     //! Grid Traversal -> O(N x M)
            {
                  for(int j = 0; j < canVisit[0].length; j++)
                  {
                        if((canVisit[i][j] == false) && (grid[i][j] > 1))   // If there is a tree which cannot be visited...
                              possibility = false;     // Static member value changed to false...
                  }
            }
      }
      public static void main(String args[])
      {
            //? Test Case - I
            int array1[][] = {{1,2,3}, {0,0,4}, {7,6,5}};
            //? Test Case - II
            int array2[][] = {{1,2,3}, {0,0,0}, {7,6,5}};
            //? Test Case - III
            int array3[][] = {{2,3,4}, {0,0,5}, {7,8,9}};
            //? Test Case - IV
            int array4[][] = {{2,3,4}, {0,0,5}, {9,8,7}};
            //? Test Case - V
            int array5[][] = {{2}};
            GolfEvent golfEvent = new GolfEvent();     // Object creation...
            System.out.println("Minimum Steps To Cut Trees (Ist Case) : "+golfEvent.MinimumStepsToCutTrees(array1));
            System.out.println("Minimum Steps To Cut Trees (IInd Case) : "+golfEvent.MinimumStepsToCutTrees(array2));
            System.out.println("Minimum Steps To Cut Trees (IIIrd Case) : "+golfEvent.MinimumStepsToCutTrees(array3));
            System.out.println("Minimum Steps To Cut Trees (IVth Case) : "+golfEvent.MinimumStepsToCutTrees(array4));
            System.out.println("Minimum Steps To Cut Trees (Vth Case) : "+golfEvent.MinimumStepsToCutTrees(array5));
      }
}



//! Time Complexity - O(N x M)
//* Space Complexity - O(N x M)