package sudoku.computationlogic;

import java.util.ArrayList;
import java.util.Collections;
import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    public static SudokuGame getNewGame() {
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );

    }

    /**
     * Check to see if incoming state of the game is either active or complete (unsolved/solved)
     *
     * @param grid A virtual rep of a sudoku puzzle, which may or may not be solved.
     * @return Either GameState.Active or GameState.Complete, based on solvedSudoku.
     *
     * Rules--
     * 1. a number cannot be repeated among rows
     * 2. a number may not be repeated among cols
     * 3. a number may not be repeated within respective GRID_BOUNDARY x GRID_BOUNDARY
     */

    public static GameState checkForCompletion(int[][] grid) {
        if (sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if (tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    /**
     * Traverse all tiles and determine if any are not 0
     * @param grid
     * @return
     */

    public static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] == 0)
                    return true;
            }
        }
        return false;

    }

    /**
     * Checks if current complete or incomplete state of game is still a valid state in regard to
     * rows, cols, squares
     * @param grid
     * @return
     */

    static boolean sudokuIsInvalid(int[][] grid) {
        if (rowsAreInvalid(grid)) return true;
        if (columnsAreInvalid(grid)) return true;
        if (squaresAreInvalid(grid)) return true;
        else return false;
    }

    public static boolean squaresAreInvalid(int[][] grid) {
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;
        if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;
        if (rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) return true;

        return false;
    }

    /**
     * a "square" is considered one of the 3X3 portion of the puzzle containing GRID_BOUNDARY "tiles"
     * select each square and compare them individually
     * ranges:
     * [0][0] - [2][2], [3][0] - [5][2], [6][0] - [8][2]
     * [0][3] - [2][2], [3][3] - [5][5], [6][3] - [8][5]
     * [0][6] - [2][2], [3][0] - [5][2], [6][0] - [8][8]
     * @param grid a copy of the sudoku game's grid state to compare against
     * @return
     */



    private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
        switch(value){
            case TOP:
                //first = 0
                if(squareIsInvalid(0,0,grid)) return true;
                // second = 3
                if(squareIsInvalid(0,3,grid)) return true;
                //third = 6
                if(squareIsInvalid(0,6,grid)) return true;
                // otherwise squares seem valid
                return false;
            case MIDDLE:
                if(squareIsInvalid(3,0,grid)) return true;
                if(squareIsInvalid(3,3,grid)) return true;
                if(squareIsInvalid(3,6,grid)) return true;
                return false;
            case BOTTOM:
                if(squareIsInvalid(6,0,grid)) return true;
                if(squareIsInvalid(6,3,grid)) return true;
                if(squareIsInvalid(6,6,grid)) return true;
                return false;
            default:
                return false;
        }

    }

    public static boolean squareIsInvalid(int xIndex, int yIndex, int[][] grid) {
        int yIndexEnd = yIndex + 3;
        int xIndexEnd = xIndex + 3;

        ArrayList<Integer> square = new ArrayList<Integer>();
        while (yIndex < yIndexEnd){
            while (xIndex < xIndexEnd){
                square.add(grid[xIndex][yIndex]);
                xIndex++;
            }
            //reset x to original value by subtracting by 2
            xIndex -= 3;
            yIndex++;
        }
        //if square has repeats --> true
        if (collectionHasRepeats(square)) return true;
        return false;
    }

    public static boolean rowsAreInvalid(int[][] grid) {
        for (int yIndex=0; yIndex<GRID_BOUNDARY;yIndex++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int xIndex=0; xIndex < GRID_BOUNDARY; xIndex++){
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }

    public static boolean columnsAreInvalid(int[][] grid) {
        for (int xIndex=0; xIndex<GRID_BOUNDARY;xIndex++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int yIndex=0; yIndex < GRID_BOUNDARY; yIndex++){
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }



    public static boolean collectionHasRepeats(ArrayList<Integer> collection) {
        //count occurances of ints from 1-GRID_BOUNDARY
        // if Collections.frequency returns a value greater than 1, then a non-zero number has been
        // repeated in a square
        for (int index = 1; index <= GRID_BOUNDARY; index++){
            if(Collections.frequency(collection, index) > 1) return true;
        }
        return false;

    }



}
