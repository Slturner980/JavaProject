/******************************************************************************
* Author:      Daniel Portillo & Sarah Turner
* Label:       Main
* Title:       Programing Project
* Course:      CMPS 4143 
* Semester:    Fall 2021
* Date:        10/3/2021
*  
* Description: This program is abbout loading  a sudoku problem into an array
*              before solving it through a problem solving algorithim to replicate
*              the way that most normal individuals typcially solve them. The program
*              then prints out the solved sudoku puzzle.
*
*
* Solution:    After loading the sudoku puzzle into an array we use a recursive
*              function to go through each entry to see if it is missing an entry.
*              Then, while still inside the recusive function, we then make a function
*              call to a checker that checks to see what number may legally go within the
*              the entry. We continue on throughout the puzzle while occasionally
*              backtracking to rectify any inconsistencies in the previous entries.
*              Afterwords, it prints out the table.
*
*
* Files:   
*              Main.java 
* Instructions: 
*              Open this file with VS Code. Hit "Debug Java".
********************************************************************************/
public class Main {
 
    // N is the size of the 2D matrix   N*N
    int []grid = new int[81];
    int index = 0;

    /* Takes a partially filled-in grid and attempts
    to assign values to all unassigned locations in
    such a way to meet the requirements for
    Sudoku solution (non-duplication across rows,
    columns, and boxes) */
    static boolean solveSuduko(int []grid, int index)
    {
 
        /*if we have reached the 8th
           row and 9th column (0
           indexed matrix) ,
           we are returning true to avoid further
           backtracking       */
        if (index == 81)
        {
            return true;
        }    

 
        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (grid[index] != 0)
        {
            return solveSuduko(grid, index + 1);
        }

        for (int num = 1; num < 10; num++) {
 
            // Check if it is safe to place
            // the num (1-9)  in the
            // given row ,col ->we move to next column
            if (legalInput(grid, index, num)) {
 
                /*  assigning the num in the current
                (row,col)  position of the grid and
                assuming our assigned num in the position
                is correct */
                grid[index] = num;
 
                // Checking for next
                // possibility with next column
                if (solveSuduko(grid, index + 1))
                    return true;
            }
            /* removing the assigned num , since our
               assumption was wrong , and we go for next
               assumption with diff num value   */
            grid[index] = 0;
        }
        return false;
    }
 
    /* A utility function to print grid */
    static void print(int[] grid, int index)
    {
        for (int i = 0; i < 81; i = (i +9)) 
        {
            for (int j = 0; j < 9; j++)
            {
                System.out.print(grid[j+i] + " ");
            }
            System.out.println();
        }
    }
 
    // Check whether it will be legal
    // to assign num to the
    // given row, col
    static boolean legalInput(int[] grid, int index, int num)
    {
 
        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (grid[((index / 9)*9)+x] == num)
                return false;
 
        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (grid[index % 9 + 9 * x] == num)
                return false;
 
        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int indexBoxCornerCol = index % 9 /3 *3;
        int indexBoxCornerRow = index / 27 *27;
        int indexBoxCorner = indexBoxCornerCol + indexBoxCornerRow;

        for (int i = 0; i < 27; i = i +9)
            for (int j = 0; j < 3; j++)
                if (grid[indexBoxCorner + i + j] == num)
                    return false;
 
        return true;
    }
  
    // Driver Code
    public static void main(String[] args)
    {
        int index = 0;
        int grid[] = {    3, 0, 6, 5, 0, 8, 4, 0, 0 ,
                          5, 2, 0, 0, 0, 0, 0, 0, 0 ,
                          0, 8, 7, 0, 0, 0, 0, 3, 1 ,
                          0, 0, 3, 0, 1, 0, 0, 8, 0 ,
                          9, 0, 0, 8, 6, 3, 0, 0, 5 ,
                          0, 5, 0, 0, 9, 0, 6, 0, 0 ,
                          1, 3, 0, 0, 0, 0, 2, 5, 0 ,
                          0, 0, 0, 0, 0, 0, 0, 7, 4 ,
                          0, 0, 5, 2, 0, 6, 3, 0, 0  };
 
        if (solveSuduko(grid, 0))
            print(grid, index);
        else
            System.out.println("No Solution exists");
    }
    
}
