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
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public Main()
    {
        //Initializes array and index
       
    }
 
    
    

    // takes array and makes function calls to check
    // each entry to see if a certain number may be
    // placed in it.
    static boolean solveSuduko(int []grid, int index)
    {
 
        // should the array rach index 81 (the end of
        // the array) it will jump out of the function
        if (index == 81)
        {
            return true;
        }    

 
        // Checks to see if there is already an entry in
        // the soduko grid and skips over it in case it is
        if (grid[index] != 0)
        {
            return solveSuduko(grid, index + 1);
        }

        //for loop goes through every number 1-9 to place 
        //a legal entry.
        for (int num = 1; num < 10; num++) {
 
            // Function call checks to see if
            // the entry is legal
            if (legalInput(grid, index, num)) {
 
                // Assigns entry
                grid[index] = num;
 
                // Recursive call to continue onto the next entry
                if (solveSuduko(grid, index + 1))
                    return true;
            }
            //resets index
            grid[index] = 0;
        }
        return false;
    }
 
    // Prints out the table
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
        System.out.println();
    }
 
    // Checks to see if the entry is legal
    static boolean legalInput(int[] grid, int index, int num)
    {
 
        // Row checker
        for (int x = 0; x <= 8; x++)
            if (grid[((index / 9)*9)+x] == num)
                return false;
 
        // Colom checker
        for (int x = 0; x <= 8; x++)
            if (grid[index % 9 + 9 * x] == num)
                return false;
 
        // Finds the Corner of each 3 by 3 subsection
        int indexBoxCornerCol = index % 9 /3 *3;
        int indexBoxCornerRow = index / 27 *27;
        int indexBoxCorner = indexBoxCornerCol + indexBoxCornerRow;

        // Goes through the 3 by 3 box to check for duplicate entries
        for (int i = 0; i < 27; i = i +9)
            for (int j = 0; j < 3; j++)
                if (grid[indexBoxCorner + i + j] == num)
                    return false;
 
        return true;


    }

    



    public void Sreader(int[] grid,int index) throws IOException
        {
         int count = 0;
            
        try
        {
            BufferedWriter writer = new BufferedWriter (new FileWriter("Times.txt"));
            File myObj = new File("testCase50000_17.txt");

            Scanner myReader = new Scanner(myObj);



           while(myReader.hasNext())
           {
                for (int i =0; i < 81; i++) 
                {
                    int data = myReader.nextInt();
                    
                    grid[i] = data;
                }
                

                long startTime = System.currentTimeMillis();
                solveSuduko(grid, 0);
                long endTime = System.currentTimeMillis();


                
                
             float timeElapsed = endTime - startTime;
                
             String TE = String.valueOf(timeElapsed);

                writer.write(TE);
                writer.write("\n");


                System.out.println("P"+ count +"Solved in milliseconds: " + timeElapsed);

               // print(grid, index);
                count++;
                
               
           }

           writer.close();

            System.out.print("did " + count + " puzzles :) ");
                
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
         }

            }

            
   

    
  
    // Main driver that makes initial call and original array of the
    // soduko
    public static void main(String[] args) throws IOException
    {

        

        int []grid = new int[81];
        int index = 0;

         
        // int grid[] = {    3, 0, 6, 5, 0, 8, 4, 0, 0 ,
        //                   5, 2, 0, 0, 0, 0, 0, 0, 0 ,
        //                   0, 8, 7, 0, 0, 0, 0, 3, 1 ,
        //                   0, 0, 3, 0, 1, 0, 0, 8, 0 ,
        //                   9, 0, 0, 8, 6, 3, 0, 0, 5 ,
        //                   0, 5, 0, 0, 9, 0, 6, 0, 0 ,
        //                   1, 3, 0, 0, 0, 0, 2, 5, 0 ,
        //                   0, 0, 0, 0, 0, 0, 0, 7, 4 ,
        //                   0, 0, 5, 2, 0, 6, 3, 0, 0  };

        Main S = new Main();

        long TotalstartTime = System.currentTimeMillis();

        S.Sreader(grid, index);

        long TotalendTime = System.currentTimeMillis();
 
        float TotaltimeElapsed = TotalendTime - TotalstartTime;

        float TotaltimeSec = (TotaltimeElapsed/1000);
 
        System.out.println("Execution time in milliseconds: " + TotaltimeElapsed);
        System.out.println("Execution time in seconds: " + TotaltimeSec);
        
 
    }
    
}
