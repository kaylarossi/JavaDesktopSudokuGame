package sudoku.computationlogic;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

import java.util.ArrayList;
import java.util.Random;
import sudoku.problemdomain.Coordinates;

public class GameGenerator {
    public static int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    /**
     * 1. Generate a new 9x9 2D array
     * 2. For each value in range 1..9, allocate that value 9 times based on the following constraints:
     * - A random coordinate on the grid is selected. If it is empty, a random value is allocated.
     * - The resulting allocation must not produce invalid rows, cols, or squares.
     * - if the allocation does produce an invalid game
     */

    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        // Value represents potential values for each square. Each value must be allocated 9 times.
        for (int value = 1; value <= GRID_BOUNDARY; value++){
            // allocations refers to the number of times in which a square has been given a value.
            int allocations = 0;
            // if too many allocation attempts are made which end in an invalid game, we grab the most recent
            // allocations stored in the List below, and reset them all to 0 - empty.
            int interrupt = 0;
            //keep track of what has been allocated in the current frame of the loop
            ArrayList<Coordinates> allocTracker = new ArrayList<>();

            //failsafe - if keep rolling back allocations on most recent frame, the game still
            // keeps breaking, after 500 times we reset the board entirely and start again
            int attempts =0;

            while(allocations < GRID_BOUNDARY){
                if (interrupt > 200) {
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500){
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                //if value results in an invalid game, re-assign that element to 0 and try again
                if (newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value;

                    if (GameLogic.sudokuIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate] =0 ;
                        interrupt ++;
                    }
                    // otherwise indicate that a value has been allocated, add it to alloc tracker
                    else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }

        return newGrid;
    }

    /**
     * This function takes a game which ahs already been solved (i.e. a solvable game) and randomly
     * assigns a certain number of tiles to be equal to 0. No clear straight forward way to check if a
     * puzzle is still solvable after removing the tiles.
     * 1. Copy values of solvedGame to new Array (helper)
     * 2. Remove 40 values randomly from new Array
     * 3. test new array for solvability
     * 4a. solvable -> return new array
     * 4b. return to step 1
     */

    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = false;
        //not actually solvable until the algorithm below finishes...
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while(solvable == false){
            //take values from solvedGame and write to new unsolved; i.e. reset to init state
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);
            // remove 40 random numbers

            int index = 0;
            while (index < 40){
                int xCoordinate=random.nextInt(GRID_BOUNDARY);
                int yCoordinate =random.nextInt(GRID_BOUNDARY);

                if(solvableArray[xCoordinate][yCoordinate] != 0){
                    solvableArray[xCoordinate][yCoordinate]= 0;
                    index++;
                }
            }
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
            //check if result is solvable
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);

        }
        return solvableArray;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex =0; xIndex < GRID_BOUNDARY; xIndex ++){
            for(int yIndex =0; yIndex < GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
