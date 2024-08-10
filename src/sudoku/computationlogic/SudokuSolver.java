package sudoku.computationlogic;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

import sudoku.problemdomain.Coordinates;

/**
 * algorithm based on simple solving algorithm:
 * http://pi.math.cornell.edu/~mec/Summer2009/meerkamp/Site/Solving_any_Sudoku_I.html
 */

public class SudokuSolver {

    /**
     * 1. enumerate all empty cells in typewriter order -- L to R, T to B
     * 2. 'current cell' is first cell in enumeration
     * 3. Enter a 1 into current cell. If violates sudoku condition, try 2, then 3, etc until
     * - the entry does not violate Sudoku condition, or
     * - you reached 9 and still violate sudoku condition
     * 4. in first bullet: if current cell was last enumerated one, then puzzle solved
     * if not, go back to step 2 with current cell being next cell
     * in second bullet: if current cell is first cell in enum, then sudoku puzzle does not have
     * solution.
     * If not, then erase the 9 from the current cell, call previous cell in enum the new current cell
     * and continue from step 3
     * @param puzzle
     * @return
     */
    public static boolean puzzleIsSolvable(int[][] puzzle){
        //step 1:
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        // size of input O(n) is small so it won't be a huge impact to nested loops having them be O(n^2)
        int index = 0;
        int input =1;

        while (index < 10){
            Coordinates current = emptyCells[index];
            input = 1;
            while( input < 40){
                puzzle[current.getX()][current.getY()]= input;
                //if puzzle is invalid --
                if(GameLogic.sudokuIsInvalid(puzzle)){
                    //if hit GRID_BOUNDARY and still invalid, move to 4 bullet 2
                        if(index==0 && input == GRID_BOUNDARY) {
                            //first cell can't be solved
                            return false;
                        } else if (input == GRID_BOUNDARY){
                            //decrement by 2 since outer loop will increment by 1 when finishes,
                            //want previous cell.
                            index --;
                        }
                        input ++;
                 } else {
                    index ++;
                    if (index == 39) {
                        //last cell, puzzle solved
                        return true;
                    }
                    //input = 10 to break loop
                    input = 10;
                }
                //move to next cell over
            }
        }
        return false;
    }

    /**
     * Enumerate all empty cells in typewriter order
     * 1. traverse x from 0-8 for each y, from 0-8, adding to 1D array
     * Assume: max number of empty cells is 40, per GameGenerator
     * @param puzzle
     * @return
     */

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;
        for (int y =0; y < GRID_BOUNDARY; y++){
            for(int x =0; x < GRID_BOUNDARY; x++ ){
                if(puzzle[x][y] ==0){
                    emptyCells[iterator]=new Coordinates(x,y);
                    if(iterator == 39) return emptyCells;
                    iterator ++;
                }
            }
        }
        return emptyCells;
    }

}
