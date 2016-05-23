package edu.unc.dominno.assignment1;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int[][] board;
    private int[][] solution;
    private int[] columns;
    private ArrayList<int[][]> answers;
    private int answerIndex;
    private int request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Displayed Chessboard
            // int[col][row]
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }

        // Initialize Solution Chessboard
        solution = board;

        // Initialize Column Checking Array
        columns = new int[8];
        for (int i = 0; i < 8; i++) {
            columns[i] = 0;
        }

        answers = new ArrayList<int[][]>();
        answerIndex = 0;

        Button buttonContextMenu = (Button)findViewById(R.id.button3);
        registerForContextMenu(buttonContextMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Choose a solution");
        inflater.inflate(R.menu.menu_solutions, menu);
        answers.clear();
        answerIndex = 0;
        request = -1;
        getAllSolutions(board, 0);
        showPop("There are " + answerIndex + " possible solutions");
        for (int i = 0; i < answers.size(); i++) {
            menu.add(Integer.toString(i));
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        /*answers.clear();
        getAllSolutions(board, 0);*/
        Log.v("Item Title: ", item.getTitle().toString());
        /*for (int i = 0; i < answers.size(); i++) {
            if (item.getTitle().toString().equals(Integer.toString(i))) {
                solution = answers.get(i);
                showSolution();
                return true;
            }
        }*/
        Log.v("# of Solutions: ", Integer.toString(answerIndex));
        /*solution = answers.get(Integer.valueOf(item.getTitle().toString()));*/
        Log.v("Answer: ", Arrays.deepToString(answers.get(Integer.valueOf(item.getTitle().toString()))));
        Log.v("Solution: ", Arrays.deepToString(solution));
        getAnswer(board, 0, Integer.valueOf(item.getTitle().toString()));
        /*showPickedSolution(answers.get(Integer.valueOf(item.getTitle().toString())));*/
        showSolution();
        return super.onContextItemSelected(item);
    }

    public void onPopup(View v) {
        openContextMenu(v);
    }

    public void onClick(View v) {
        // Selecting the button pressed
        ImageButton b = (ImageButton) findViewById(v.getId());

        // Finding the associated array index in board
            // Convert column letter to array index
        String s = v.getResources().getResourceEntryName(v.getId());
        String colletter = s.substring(0,1);

        int col = 10;
        switch(colletter) {
            case "A": col = 0;
                break;
            case "B": col = 1;
                break;
            case "C": col = 2;
                break;
            case "D": col = 3;
                break;
            case "E": col = 4;
                break;
            case "F": col = 5;
                break;
            case "G": col = 6;
                break;
            case "H": col = 7;
                break;
        }

            // Convert row number to array index
        int row = Integer.valueOf(s.substring(1,2)) - 1;

        // Place/Remove Queen
        if (col < 8) {
            if (board[col][row] == 0) { // No queen, placeable
                if (goodMove(col, row, board)) { // Check if possible
                    // Place Queen
                        // Make the change on the board
                    board[col][row] = 2;

                        // Make note in checking columns array
                    columns[col] = 1;

                        // Change button background image
                    if (v.getTag().equals("w")) {
                        b.setBackgroundResource(R.drawable.wq);
                    } else if (v.getTag().equals("b")) {
                        b.setBackgroundResource(R.drawable.bq);
                    }

                    // Eliminate Other Choices
                    eliminateChoices(board, col, row);

                    Log.v("board: ", Arrays.deepToString(board));
                } else {
                    // Error Message
                        // Tell user bad move
                    showPop("You cannot do that");
                }
            } else if (board[col][row] == 1) { // No queen, not placeable
                // Cannot Place Queen
                    // Attacking Queen, tell user bad move
                showPop("This spot attacks a queen. Try again.");
            } else if (board[col][row] == 2) { // Queen
                // Remove Queen
                    // Make the change on the board
                board[col][row] = 0;

                    // Make note in the checking columns array
                columns[col] = 0;

                    // Change button background image
                if (v.getTag().equals("w")) {
                    b.setBackgroundResource(R.drawable.white);
                } else if (v.getTag().equals("b")) {
                    b.setBackgroundResource(R.drawable.black);
                }

                // Reset Other Choices
                resetChoices(board, col, row);

                Log.v("board: ", Arrays.deepToString(board));
            }
        }

        // Check if puzzle is complete
        if (countQueens(board) == 8) {
            showPop("CONGRATS!!! You solved the puzzle!");

            Log.v("board: ", Arrays.deepToString(board));
        }

        // Check if the board is no longer solvable
        /*if (!solve(board, 0)) {
            showPop("There's no more possible moves! Remove queen(s) and try again.");
        }*/
    }

    public boolean goodMove(int col, int row, int[][] temp) {
        // Check column
        for (int i = 0; i < 8; i++) {if (i != row) {if (temp[col][i] == 2) {return false;}}}
        // Check row
        for (int i = 0; i < 8; i++) {if (i != col) {if (temp[i][row] == 2) {return false;}}}
        // Check diagonals
            // NW
        for (int i = 1; i < 8; i++) {
            if ((col - i) >= 0 && (row - i) >= 0) {
                if (temp[col - i][row - i] == 2) {
                    return false;
                }
            }
        }
            // NE
        for (int i = 1; i < 8; i++) {
            if ((col + i) < 8 && (row - i) >= 0) {
                if (temp[col + i][row - i] == 2) {
                    return false;
                }
            }
        }
            // SW
        for (int i = 1; i < 8; i++) {
            if ((col - i) >= 0 && (row + i) < 8) {
                if (temp[col - i][row + i] == 2) {
                    return false;
                }
            }
        }
            // SE
        for (int i = 1; i < 8; i++) {
            if ((col + i) < 8 && (row + i) < 8) {
                if (temp[col + i][row + i] == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean done() {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 2) {
                    count++;
                }
            }
        }

        if (count == 8) {
            return true;
        }

        return false;
    }

    public void showPop(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

    public void showSolution() {
        ImageButton b;

        b = (ImageButton) findViewById(R.id.A1);
        if (solution[0][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A2);
        if (solution[0][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A3);
        if (solution[0][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A4);
        if (solution[0][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A5);
        if (solution[0][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A6);
        if (solution[0][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A7);
        if (solution[0][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A8);
        if (solution[0][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B1);
        if (solution[1][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B2);
        if (solution[1][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B3);
        if (solution[1][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B4);
        if (solution[1][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B5);
        if (solution[1][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B6);
        if (solution[1][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B7);
        if (solution[1][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B8);
        if (solution[1][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C1);
        if (solution[2][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C2);
        if (solution[2][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C3);
        if (solution[2][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C4);
        if (solution[2][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C5);
        if (solution[2][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C6);
        if (solution[2][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C7);
        if (solution[2][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C8);
        if (solution[2][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D1);
        if (solution[3][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D2);
        if (solution[3][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D3);
        if (solution[3][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D4);
        if (solution[3][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D5);
        if (solution[3][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D6);
        if (solution[3][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D7);
        if (solution[3][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D8);
        if (solution[3][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E1);
        if (solution[4][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E2);
        if (solution[4][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E3);
        if (solution[4][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E4);
        if (solution[4][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E5);
        if (solution[4][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E6);
        if (solution[4][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E7);
        if (solution[4][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E8);
        if (solution[4][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F1);
        if (solution[5][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F2);
        if (solution[5][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F3);
        if (solution[5][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F4);
        if (solution[5][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F5);
        if (solution[5][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F6);
        if (solution[5][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F7);
        if (solution[5][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F8);
        if (solution[5][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G1);
        if (solution[6][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G2);
        if (solution[6][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G3);
        if (solution[6][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G4);
        if (solution[6][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G5);
        if (solution[6][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G6);
        if (solution[6][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G7);
        if (solution[6][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G8);
        if (solution[6][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H1);
        if (solution[7][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H2);
        if (solution[7][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H3);
        if (solution[7][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H4);
        if (solution[7][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H5);
        if (solution[7][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H6);
        if (solution[7][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H7);
        if (solution[7][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H8);
        if (solution[7][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}
    }

    public void showPickedSolution(int[][] s) {
        ImageButton b;

        b = (ImageButton) findViewById(R.id.A1);
        if (s[0][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A2);
        if (s[0][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A3);
        if (s[0][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A4);
        if (s[0][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A5);
        if (s[0][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A6);
        if (s[0][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.A7);
        if (s[0][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.A8);
        if (s[0][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B1);
        if (s[1][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B2);
        if (s[1][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B3);
        if (s[1][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B4);
        if (s[1][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B5);
        if (s[1][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B6);
        if (s[1][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.B7);
        if (s[1][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.B8);
        if (s[1][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C1);
        if (s[2][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C2);
        if (s[2][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C3);
        if (s[2][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C4);
        if (s[2][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C5);
        if (s[2][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C6);
        if (s[2][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.C7);
        if (s[2][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.C8);
        if (s[2][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D1);
        if (s[3][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D2);
        if (s[3][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D3);
        if (s[3][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D4);
        if (s[3][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D5);
        if (s[3][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D6);
        if (s[3][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.D7);
        if (s[3][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.D8);
        if (s[3][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E1);
        if (s[4][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E2);
        if (s[4][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E3);
        if (s[4][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E4);
        if (s[4][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E5);
        if (s[4][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E6);
        if (s[4][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.E7);
        if (s[4][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.E8);
        if (s[4][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F1);
        if (s[5][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F2);
        if (s[5][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F3);
        if (s[5][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F4);
        if (s[5][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F5);
        if (s[5][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F6);
        if (s[5][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.F7);
        if (s[5][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.F8);
        if (s[5][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G1);
        if (s[6][0] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G2);
        if (s[6][1] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G3);
        if (s[6][2] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G4);
        if (s[6][3] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G5);
        if (s[6][4] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G6);
        if (s[6][5] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.G7);
        if (s[6][6] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.G8);
        if (s[6][7] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H1);
        if (s[7][0] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H2);
        if (s[7][1] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H3);
        if (s[7][2] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H4);
        if (s[7][3] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H5);
        if (s[7][4] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H6);
        if (s[7][5] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}

        b = (ImageButton) findViewById(R.id.H7);
        if (s[7][6] == 2) {b.setBackgroundResource(R.drawable.bq);} else {b.setBackgroundResource(R.drawable.black);}

        b = (ImageButton) findViewById(R.id.H8);
        if (s[7][7] == 2) {b.setBackgroundResource(R.drawable.wq);} else {b.setBackgroundResource(R.drawable.white);}
    }

    public void giveUp(View v) {
        int[][] copy = board;
        if (solve(copy,0)) {
            showSolution();
            board = solution;
        } else {
            showPop("This board has no possible solutions. Please remove queen(s) and try again.");
        }
    }

    public int countQueens(int[][] q) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (q[i][j] == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean queenInColumn(int[][] c, int col) {
        for (int i = 0; i < 8; i++) {
            if (c[col][i] == 2) {
                return true;
            }
        }
        return false;
    }

    public void eliminateChoices(int[][] e, int col, int row) {
        // Eliminate Rest of Column
        for (int i = 0; i < 8; i++) {if (i != row) {e[col][i] = 1;}}
        // Eliminate Rest of Row
        for (int i = 0; i < 8; i++) {if (i != col) {e[i][row] = 1;}}
        // Eliminate Diagonals
        // NW
        for (int i = 1; i < 8; i++) {
            if (((col - i) >= 0) && ((row - i) >= 0)) {
                e[col - i][row - i] = 1;
            }
        }
        // NE
        for (int i = 1; i < 8; i++) {
            if (((col + i) < 8) && ((row - i) >= 0)) {
                e[col + i][row - i] = 1;
            }
        }
        // SW
        for (int i = 1; i < 8; i++) {
            if (((col - i) >= 0) && ((row + i) < 8)) {
                e[col - i][row + i] = 1;
            }
        }
        // SE
        for (int i = 1; i < 8; i++) {
            if (((col + i) < 8) && ((row + i) < 8)) {
                e[col + i][row + i] = 1;
            }
        }
    }

    public void resetChoices(int[][] e, int col, int row) {
        // Reset Rest of Column
        for (int i = 0; i < 8; i++) {if (i != row) {e[col][i] = 0;}}
        // Reset Rest of Row
        for (int i = 0; i < 8; i++) {if (i != col) {e[i][row] = 0;}}
        // Reset Diagonals
        // NW
        for (int i = 1; i < 8; i++) {
            if (((col - i) >= 0) && ((row - i) >= 0)) {
                e[col - i][row - i] = 0;
            }
        }
        // NE
        for (int i = 1; i < 8; i++) {
            if (((col + i) < 8) && ((row - i) >= 0)) {
                e[col + i][row - i] = 0;
            }
        }
        // SW
        for (int i = 1; i < 8; i++) {
            if (((col - i) >= 0) && ((row + i) < 8)) {
                e[col - i][row + i] = 0;
            }
        }
        // SE
        for (int i = 1; i < 8; i++) {
            if (((col + i) < 8 && (row + i) < 8)) {
                e[col + i][row + i] = 0;
            }
        }
    }

    public boolean solve(int[][] b, int x) {
        // If there are 8 queens, solved
        if (countQueens(b) == 8) {
            return true;
        }

        int temp = x;

        while (queenInColumn(b, temp)) {
            temp++;
        }
        int col = temp;


        for (int row = 0; row < 8; row++) {
            if (goodMove(col, row, b)) {
                int[][] newb = b;
                newb[col][row] = 2;
                eliminateChoices(newb, col, row);

                boolean ans = solve(newb, col+1);
                if (ans) {
                    solution = newb;
                    return true;
                }
                newb[col][row] = 0;
                resetChoices(newb, col, row);
            }
        }
        return false;
    }

    public boolean getAnswer(int[][] b, int x, int y) {
        // If there are 8 queens, solved
        if (countQueens(b) == 8) {
            request++;
            if (request == y) {
                solution = b;
            }
            return true;
        } else {
            int temp = x;

            while (queenInColumn(b, temp)) {
                temp++;
            }
            int col = temp;


            for (int row = 0; row < 8; row++) {
                if (goodMove(col, row, b)) {
                    int[][] newb = b;
                    newb[col][row] = 2;
                    eliminateChoices(newb, col, row);

                    getAllSolutions(newb, col+1);

                    newb[col][row] = 0;
                    resetChoices(newb, col, row);
                }
            }
        }
        return false;
    }

    public boolean getAllSolutions(int[][] b, int x) {
        // If there are 8 queens, solved
        if (countQueens(b) == 8) {
            Log.v("Added board: ", Arrays.deepToString(b));
            answers.add(answerIndex, b);
            answers.set(answerIndex, b);
            answerIndex++;

            if (request >= 0) {
                solution = b;
            }
            return true;
        } else {
            int temp = x;

            while (queenInColumn(b, temp)) {
                temp++;
            }
            int col = temp;


            for (int row = 0; row < 8; row++) {
                if (goodMove(col, row, b)) {
                    int[][] newb = b;
                    newb[col][row] = 2;
                    eliminateChoices(newb, col, row);

                    getAllSolutions(newb, col+1);

                    newb[col][row] = 0;
                    resetChoices(newb, col, row);
                }
            }
        }
        return false;


    }

    public void resetBoard(View v) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                solution[i][j] = 0;
                board[i][j] = 0;
            }
        }
        showSolution();
    }
}
