sudokurepo
==========
 * Author: Hari kiran Vuyyuru
 * Purpose: Solving the sudoku puzzle 
 * Last submitted: 11/03/14
========== 
This is a Sudoku solver implemented in java.

This Algorithm is efficient for difficult level sudoku puzzles i.e more open ended sudoku problems with multiple solutions
==========
 Input for the program needs to provided in input.csv present in the directory structure.
 output for the program is available in output.csv created in your directory structure
==========
 readBoard() method in solvingSudoku.java reads the input from the csv file and returns the board multidimensional array board representing the sudoku puzzle
 
 Print_Matrix() method prints the initial sudoku board and output soduko board to the console
 
 solveSudoku() is the core method that fills the sudoku board with valid values and if there are no valid values it back tracks and tries with the incremented value
 
 isvalid() is the method that checks if the value to be filled is a valid value or not
 
 Write_Matrix() is the code that writes the solved sudoku puzzle to the output.csv file




