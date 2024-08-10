package sudoku.constants;

/**
 * Provides better legibility for logic required to check if each square in the puzzle contains
 * a valid value. GameLogic.java has usage
 * top, middle, bottom rows for each square (a square being a 3x3 tile with 9 squares total in a full
 * sudoku puzzle)
 * The values represent the Y coordinates of each tile.
 */

public enum Rows {
    TOP, MIDDLE, BOTTOM
}
