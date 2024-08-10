package sudoku.problemdomain;

import java.io.Serializable;
import sudoku.computationlogic.SudokuUtilities;
import sudoku.constants.GameState;

public class SudokuGame implements Serializable {
    private final GameState gameState;
    //game board is a 2x2 grid
    private final int[][] gridState;

    //Grid coordinates will be represented with x and y index values ranging from 0 to 8 both inclusive
    public static final int GRID_BOUNDARY = 9;

    /**
     * Need an active state and a complete state. Game will start with active state and when a complete
     * state is achieved (based on GameLogic.java) then a special UI screen will display by user interface
     *
     * This class is immutable to avoid shared mutable state. Each time gridState changes, a new SudokuGame
     * object is created by taking the old one and applying some methods to each and generate a new one
     * @param gameState enum
     *                  - GameState.Complete
     *                  - GameState.Active
     * @param gridState state of sudoku game. If all locations in gridstate are filled with
     *                  proper values, GameLogic must change gameState.
     */

    public SudokuGame(GameState gameState, int[][] gridState){
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState(){
        return gameState;
    }

    public int[][] getCopyOfGridState(){
        // protects gridstate overtime
        return SudokuUtilities.copyToNewArray(gridState);
    }





}
