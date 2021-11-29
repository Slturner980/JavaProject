/******************************************************************************
* Author:      Daniel Portillo & Sarah Turner
* Label:       Main
* Title:       Programming Project
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
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this classes to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; // File IO
import java.io.BufferedWriter;

// import java.awt.Graphics2D; // Graphics library for UI
import javax.swing.*;       // UI library
import java.awt.event.*;    // Event for UI such as clicking



public class Main {

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


    // SReader Reads Puzzle input, Solves Puzzles, and Output times.

    public float Sreader(int[] grid,int index) throws IOException

    {
        float count = 0; // Counts how many Puzzles it runs though
        float accum = 0; //keeps track of overall times 
        try
        {
            BufferedWriter writer = new BufferedWriter (new FileWriter("Times.txt"));  // Output for individual solve time for puzzles

            BufferedWriter writer2 = new BufferedWriter (new FileWriter("SolvedCases.txt"));  //output for solved puzzles

            File myObj = new File("TestCase1000_17.txt"); // Input for Test Cases

            Scanner TestCaseReader = new Scanner(myObj); // Reads the input



           while(TestCaseReader.hasNext()) // While the input has something in it.

           {
                //Reads one Sudoku board
                for (int i =0; i < 81; i++) 
                {
                    int data = TestCaseReader.nextInt();
                    
                    grid[i] = data;
                }
                
                // Starts individual timer and solve the puzzles
                long startTime = System.currentTimeMillis();
                solveSuduko(grid, 0);
                long endTime = System.currentTimeMillis();

                float timeElapsed = endTime - startTime; // total time for one puzzle
                
                String TE = String.valueOf(timeElapsed); //Conversion to string

                accum = accum + timeElapsed;             // Accumulator for times of each puzzle


                for (int i = 0; i < 81; i = (i +9)) 
                {
                    for (int j = 0; j < 9; j++)
                    {
                        writer2.write(grid[j+i] + " ");
                    }
                    writer2.write('\n');
                }
                writer2.write('\n');
                

                // Output the time for puzzle
                writer.write(TE);
                writer.write("\n");

               count++;
                
            }
            //closes various files
            writer.close();
            writer2.close();

            System.out.print("did " + count + " puzzles :) ");
                
            TestCaseReader.close();
            
        }

        catch (FileNotFoundException e) 
        {
             System.out.println("An error occurred.");
             e.printStackTrace();
        }

        return (count/accum);

    }

    static void windowUi()
    {
        //creates new frame where Ui takes place
        JFrame frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,150);
        
        //Creates button to exit out of pop-up
        JButton button1 = new JButton("Press button to exit pop-up");
        button1.setBounds(25,75,225,20);

        //Creates text discribing what is happening
        JLabel label =new JLabel("This program will display the average timing of puzzles");
        label.setBounds(10,20, 320, 20);

        JLabel label2 =new JLabel("after completion. It is currently running . . . ");
        label2.setBounds(10,30, 320, 40);
        
        //adds all panels to the main frame
        frame.getContentPane().add(button1);
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);

        //makes everything visible in frame
        frame.setLayout(null);
        frame.setVisible(true);
        label.setOpaque(true);

        //action-listener to excape out of frame by clicking button1
        button1.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                frame.dispose();
            }
        });

    }

    static void endScreenUi(float avg)
    {
        String Timing = String.valueOf(avg);

        //creates new frame where Ui takes place
        JFrame frame = new JFrame("Sudoku Solver Summery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,150);

        //Creates button to exit out of pop-up
        JButton buttonOk = new JButton("Ok");
        buttonOk.setBounds(260,80,50,20);

        //creates text with average timing results
        JLabel label =new JLabel("Average Time to Solve each one: " + Timing + " Seconds");
        label.setBounds(10,10, 320, 20);
        
        //adds all panels to the main frame
        frame.getContentPane().add(buttonOk);
        frame.getContentPane().add(label);

        //makes everything visible in frame
        frame.setLayout(null);
        frame.setVisible(true);
        label.setOpaque(true);

        //action-listener to excape out of frame by clicking buttonOk
        buttonOk.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                frame.dispose();
            }
        });
    }
            
    // Main driver that makes initial call and original array of the
    // Sudoku
    public static void main(String[] args) throws IOException
    {
        // initialize the grid, index, and number of puzzles solved
        int []grid = new int[81];
        int index = 0;
        float Avg = 0;

        // Calls for the initial UI window
        windowUi();

        Main S = new Main(); // Make an Object instance

        long TotalstartTime = System.currentTimeMillis(); // Starts the total run time

        Avg = (S.Sreader(grid, index));                //Reads/writes to files, and fetches average time

        long TotalendTime = System.currentTimeMillis();  // Ends total run time
 
        float TotaltimeElapsed = TotalendTime - TotalstartTime; // How long the program ran

        float TotaltimeSec = (TotaltimeElapsed/1000);  // Same in seconds
 
        System.out.println("Execution time in milliseconds: " + TotaltimeElapsed);
        System.out.println("Execution time in seconds: " + TotaltimeSec);

        endScreenUi(Avg);
        
 
    }
    
}
