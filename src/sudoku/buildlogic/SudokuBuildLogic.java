package sudoku.buildlogic;

import java.io.IOException;
import sudoku.computationlogic.GameLogic;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;
import sudoku.persistence.LocalStorageImpl;

public class SudokuBuildLogic {

    /**
     * This class takes in the uiImpl object which is tightly coupled to JavaFX framework,
     * binds that object to the various other objects necessary for application to funciton
     * @param userInterface
     * @throws IOException if no game data is found in local storage also if cannot update
     * game data - app is considered unrecoverable
     */

    public static void build(IUserInterfaceContract.View userInterface) throws IOException{
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try{
            initialState = storage.getGameData();

        } catch (IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }
        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }

}
