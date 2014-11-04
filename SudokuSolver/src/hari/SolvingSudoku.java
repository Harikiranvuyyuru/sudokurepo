/*
 * Program Name:solvingSudoku.java
 * Date Created: 11/01/14
 * Author: Hari kiran Vuyyuru
 * Purpose: Solving the sudoku puzzle 
 * Last submitted: 11/03/14
 * Program input: input.csv present in the directory structure
 * Program output:output.csv created in the directory structure

*/

/*
 * This program is written with an assumption that the sudoku puzzles are of difficulty level is more i.e open ended puzzles with lot of cells set to zero
 * If Sudoku puzzle's difficulty level is less, i.e puzzle is mostly filled and requires to fill only one or two cells in row,columns or 3*3 blocks,then
 * It is efficient to solve the program by checking for these special conditions and prefill the  cells with those values
 * 
 * 
 * */
package hari;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class SolvingSudoku
{
	 // Exception class that will be invoked if input format is not matching the desired format
	  public final static class invalidFormatException extends Exception {
		  invalidFormatException(){
				System.out.println("Your input present in the input file isn't in the correct format or numbers present in it doesn't match the sudoku criteria");
				System.out.println(
						"\nFor example, Your input should be like: \n"
						+"0,3,5,2,9,0,8,6,4\n"
						+"0,8,2,4,1,0,7,0,3\n"
						+"7,6,4,3,8,0,0,9,0\n"
						+"2,1,8,7,3,9,0,4,0\n"
						+"0,0,0,8,0,4,2,3,0\n"
						+"0,4,3,0,5,2,9,7,0\n"
						+"4,0,6,5,7,1,0,0,9\n"
						+"3,5,9,0,2,8,4,1,7\n"
						+"8,0,0,9,0,0,5,2,6"
					);
			  
		  }

	        private static final long serialVersionUID = -984824902420L;
	    }
	  
	public static void main(String[] args) throws invalidFormatException, IOException {
		
		int[][] board = new int[9][9];
		
		// inputting the values from csv file to initialize the sudoku board
		board=readBoard();
		
		System.out.println("----------------------------------------------------");

		System.out.println("Sudoko puzzle to be solved is :");
		
		Print_Matrix(board);
		
		System.out.println("----------------------------------------------------");

		long beginTime = System.currentTimeMillis();
		
		// solving the problem by backtracking and recursion.
		
		if(solveSudoku(0, 0, board)) {
			System.out.println("One of the Solutions for the sudoku puzzle is :");
		} else {
			System.out.println("No solution is possible for this puzzle.");
		}
		long endTime = System.currentTimeMillis();
		
		// printing the solved sudoku to console
		Print_Matrix(board);
		
		//printing the solved sudoku board to output.csv file stored under our project folder
		Write_Matrix(board);

		
		System.out.println("----------------------------------------------------");
		
		System.out.println("Soduko is solved in : " + (endTime - beginTime) + " milliseconds");
	}
	
	
	/* 
	*  this method reads the input.csv file and returns the initial Sudoku board
	*  if you want to try invalid input format, change value in the FileReader from input.csv to invalid_format_input.csv
	*  if you want to try invalid numbers in the input file ,change value  in the FileReader from input.csv to invalid_number_input.csv
	*/
	
	 private  static int[][] readBoard() throws invalidFormatException, IOException {
		 final int[][] board = new int[9][9];
         BufferedReader input =  new BufferedReader(new FileReader("input.csv"));

        try {
            String line = null; //not declared within while loop
            int lineIndex=0;
            
            while (( line = input.readLine()) != null) {
                if (line.length() != 17 || lineIndex > 8) {//including commas each line should have 17 characters and  9 rows else throw exception
                    throw new invalidFormatException();
                }
              
                
                StringTokenizer st = new StringTokenizer(line, ","); // removing the commmas  
                int tokennumber=0;
        		while (st.hasMoreElements()) {
                    final int value = Integer.parseInt(st.nextToken());
                    
                    if (value < 0 || value > 9) {
                        throw new invalidFormatException();
                    }
                    board[lineIndex][tokennumber] = value;
                    tokennumber++;
        		}
             
                lineIndex++;
            }
            
            if (lineIndex != 9) {
                throw new invalidFormatException();
            }
        }
        finally {
            input.close();
        }
        return board;
      
   
    }
	 
	 
	// this method prints the initial sudoku board and output soduko board to the console
   public static void Print_Matrix(int[][] input) {
			for(int[] line : input) {
				for(int cell : line) {
					System.out.print(cell + " ");
				}
				System.out.println();
			}
		}
	 
   // this is the core program that fills the sudoku board with valid values and if there are no valid values it backtracks and tries with the incremented value
	public static boolean solveSudoku(int rownum, int colnum, int[][] board) {
		
		if(rownum == 9) {
			//row number 9 doesn't exist and we might get indexoutofbound exception while accessing the board elements if we use this num, hence resetting it to 0
			rownum = 0; 
			if(++colnum == 9) { 
				// if colnum=9 it means that you have reached the end of the board
					return true; // By right, that must be the solution.
				}
		}

		if(board[rownum][colnum] != 0) { // if board element not equal to zero it means that element is already filled,proceed with other elements
			return solveSudoku(rownum + 1, colnum, board);
		}

		// fill the blank values as long as the numbers are satisfying the criteria
		
		for(int value = 1; value <= 9; value++) {
			
			if(isvalid(rownum, colnum, value, board)) {
				
				board[rownum][colnum] = value;
			
				// Proceed to the next node
				if(solveSudoku(rownum + 1, colnum, board)) {
					return true;
				}
			}
		}

		// IF the solution failed, backtrack to the previous step and try the next value
		board[rownum][colnum] = 0;
		return false;
	}
	
	// this program checks if the value to be filled is a valid value or not
	
	public static boolean isvalid(int i, int j, int value, int[][] board) {

		// check if the value to be filled matches any of the elements already in that row 
		for(int colIndex = 0; colIndex < board[0].length; colIndex++) {
			if(value == board[i][colIndex]) {
				return false;
			}
		}

		// check if the value to be filled matches any of the elements already in that column 
		
		for(int rowIndex = 0; rowIndex < board.length; rowIndex++) {
			if(value == board[rowIndex][j]) {
				return false;
			}
		}

		// 3x3 box check
		
		int boxRow = (i / 3) * 3;
		
		int boxCol = (j / 3) * 3;
		// looping through the 3*3 box
		for(int rowIndex = 0; rowIndex < board.length / 3; rowIndex++) {
			for(int colIndex = 0; colIndex < board[0].length / 3; colIndex++) {
				if(value == board[rowIndex+boxRow][colIndex+boxCol]) {
					return false;
				}
			}
		}

		// if the element passes through all checks, element is a valid choice
		return true;
	}

	// this is the code that writes the solved sudoku puzzle to the output.csv file
	public static void Write_Matrix(int[][] input) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("output.csv", "UTF-8");
        String out="";
		for(int[] line : input) {
			for(int j=0;j<line.length;j++) {
				out=out+line[j];
	                    if (j!=line.length-1)
			               { 
	    				out=out+",";
			                 }
		                else
		                {
		    				writer.println(out);  
		    				out="";
		                }
			}
		}
		writer.close();
	}
	
	

	
   
}