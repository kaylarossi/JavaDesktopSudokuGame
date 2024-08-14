package sudoku.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import sudoku.computationlogic.GameLogic;
import sudoku.constants.GameState;


public class GameLogicTest {
    //Start with the basic logic to validate a valid sudoku puzzle
    @Test
    public void onValidateValidPuzzle(){
        assert (GameState.COMPLETE == GameLogic.checkForCompletion(TestData.getSolved().getCopyOfGridState()));
    }
    @Test
    public void onValidateActivePuzzle(){
        assert (GameState.ACTIVE == GameLogic.checkForCompletion(TestData.getValidStart().getCopyOfGridState()));
    }

    //Expected value: True -- squares are not all filled
    @Test
    public void gameSquaresAreNotFilled(){
        assert (GameLogic.tilesAreNotFilled(TestData.getValidStart().getCopyOfGridState()));
    }

    //expected value: false
    @Test
    public void gameSquaresAreValid(){
        int[][] valid = TestData.getSolved().getCopyOfGridState();

        boolean isInvalid = GameLogic.squaresAreInvalid(valid);
        assert(!isInvalid);
    }
    //expected false
    @Test
    public void gameColumnsAreInvalid(){
        int[][] invalid = TestData.getInvalid().getCopyOfGridState();
        boolean isInvalid = GameLogic.columnsAreInvalid(invalid);
        assert (isInvalid);
    }
    //expected true
    @Test
    public void gameRowsAreInvalid(){
        int[][] invalid = TestData.getInvalid().getCopyOfGridState();
        boolean isInvalid = GameLogic.columnsAreInvalid(invalid);
        assert (isInvalid);
    }

    //expected false
    @Test
    public void gameRowsAreValid(){
        int[][] invalid = TestData.getSolved().getCopyOfGridState();
        boolean isInvalid = GameLogic.columnsAreInvalid(invalid);
        assert (!isInvalid);
    }

    //Collection does have repeated integer values expected value -- true
    @Test
    public void collectionHasRepeats(){
        List<Integer> testList = Arrays.asList(0,0,0,1,1,0,0,0,0);
        boolean hasRepeats = GameLogic.collectionHasRepeats(testList);
        assert (hasRepeats);
    }

    //expected true
    @Test
    public void collectionHasNoRepeats(){

        List<Integer> testListOne = Arrays.asList(0,0,0,0,0,0,0,0,0);
        List<Integer> testListTwo = Arrays.asList(1,2,3,4,5,6,7,8,9);
        boolean hasRepeatsOne = GameLogic.collectionHasRepeats(testListOne);
        boolean hasRepeatsTwo = GameLogic.collectionHasRepeats(testListTwo);

        assert (!hasRepeatsOne);
        assert (!hasRepeatsTwo);
    }

}