
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
	
	  public final static class invalidFormatException extends Exception {
		  invalidFormatException(){
				System.out.println("Your input present in the input file isn't in the correct format or numbers present in it doesn't match the sudoku criteria");
			  
		  }

	        private static final long serialVersionUID = -984824902420L;
	    }
	  
	public static void main(String[] args) throws invalidFormatException, IOException {
		int[][] board = new int[9][9];
		
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
		
		// printing the output to console
		Print_Matrix(board);
		
		//printing the output to output.csv file stored underyour project folder
		Write_Matrix(board);

		
		System.out.println("----------------------------------------------------");
		
		System.out.println("Soduko is solved in : " + (endTime - beginTime) + " milliseconds");
	}

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
	
	public static void Print_Matrix(int[][] input) {
		for(int[] line : input) {
			for(int cell : line) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}


	
    private  static int[][] readBoard() throws invalidFormatException, IOException {
		 final int[][] board = new int[9][9];
         BufferedReader input =  new BufferedReader(new FileReader("input.csv"));

        try {
            String line = null; //not declared within while loop
            int lineIndex=0;
            
            while (( line = input.readLine()) != null) {
                if (line.length() != 17 || lineIndex > 8) {
                    throw new invalidFormatException();
                }
              
                
                StringTokenizer st = new StringTokenizer(line, ",");
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
}