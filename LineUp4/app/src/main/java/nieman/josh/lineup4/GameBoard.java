package nieman.josh.lineup4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by joshnieman on 10/14/16.
 */
public class GameBoard extends View {

    //board attributes
    private int numRows;
    private int numColumns;
    private Paint player1;
    private Paint player2;
    private int player1Color;
    private int player2Color;
    private String player1Name;
    private String player2Name;
    //grid for board
    //say 0 is uncolored, 1 is player1, 2 is player2
    private int[][] board;
    private double boxWidth;
    private double boxHeight;

    //see whos turn it is
    //if it's not player1's turn then it's player2's turn
    boolean player1sTurn = true;

    //make a variable that will be an int of the winning player
    private int winner;

    //some constructors
    public GameBoard(Context context){
        this(context,null);
    }

    public GameBoard(Context context, AttributeSet attr){
        super(context,attr);

        player1Color = Color.BLACK;
        player1 = new Paint();
        player1.setStyle(Paint.Style.FILL);
        player1.setColor(player1Color);
        player1Name = "Player1";

        player2Color = Color.RED;
        player2 = new Paint();
        player2.setStyle(Paint.Style.FILL);
        player2.setColor(player2Color);
        player2Name = "Player2";

        //since there isn't a winner yet
        winner = 0;

    }

    //add some properties to set the players color
    //set player1 color
    public void setPlayer1Color(int color){
        player1Color = color;
        player1.setColor(player1Color);

    }

    public int getPlayer1Color(){
        return player1Color;
    }

    //set player2 color
    public void setPlayer2Color(int color){
        player2Color = color;
        player2.setColor(player2Color);
    }

    public int getPlayer2Color(){
        return player2Color;
    }

    //set and get player names
    public void setPlayer1Name(String name){
        player1Name = name;
    }

    public String getPlayer1Name(){
        return player1Name;
    }

    public void setPlayer2Name(String name){
        player2Name = name;
    }

    public String getPlayer2Name(){
        return player2Name;
    }

    public String getPlayerName(int n){
        if(n == 1){
            return getPlayer1Name();
        }
        else if(n == 2){
            return getPlayer2Name();
        }
        else {
            return null;
        }
    }

    //set which player's turn
    public void setPlayerTurn(int playerNumber){
        if(playerNumber == 1){
            player1sTurn = true;
        }
        else{
            player1sTurn = false;
        }
    }

    //see which player's turn it is
    public int getPlayerTurn(){
        if(player1sTurn){
            return 1;
        }
        else{
            return 2;
        }
    }

    public boolean isPlayer1sTurn(){
        return player1sTurn;
    }

    public void setPlayer1sTurn(){
        player1sTurn = true;
    }

    public boolean isPlayer2sTurn(){
        return !player1sTurn;
    }

    public void setPlayer2sTrun(){
        player1sTurn = false;
    }

    //activity must call this too
    public void setSizeOfBoard(int numColumns, int numRows){
        this.numColumns = numColumns;
        this.numRows = numRows;

    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        super.onSizeChanged(width,height,oldWidth,oldHeight);

        //once the view is made, get the size and lay it out
        setBoard();
    }

    //set up the board based off of the number of rows and columns
    private void setBoard(){
        if(numColumns >= 1 && numRows >=1){

            boxHeight = this.getHeight()/numRows;
            boxWidth = getWidth() / numColumns;

            board = new int[numColumns][numRows]; //java initializes this to 0 i think?
        }
    }


    @Override
    protected void onDraw(Canvas canvas){
        //make the screen yellow
        canvas.drawColor(Color.YELLOW);
        if(numRows > 0 && numColumns > 0){

            //get the height and width of the container
            int width = getWidth();
            int height = getHeight();

            //find what the radius of the circles will be
            double radius;
            if(boxWidth < boxHeight){
                radius = .5 * boxWidth;
            }
            else{
                radius = .5 * boxHeight;
            }

            //draw some grid lines
            for(int c = 0; c < numColumns; c++){
                canvas.drawLine((float)(c*boxWidth),0,(float)(c*boxWidth),height,new Paint(Color.BLACK));
            }
            for(int r = 0; r < numRows; r++){
                canvas.drawLine(0, (float)(r* boxHeight), width,(float)(r* boxHeight), new Paint(Color.BLACK));
            }

            //fill in the boxes
            for(int c = 0; c < numColumns; c++){
                for(int r = 0; r < numRows; r++){
                   if(board[c][r] != 0) // so there is something in the box
                   {
                       if(board[c][r] == 1) //this is player1's color
                       {
                           canvas.drawCircle((float)(boxWidth *c+.5* boxWidth), (float)(boxHeight*r+.5*boxHeight),(float)radius,player1);
                       }
                       else if(board[c][r] == 2)//player2's color
                       {
                           canvas.drawCircle((float)(boxWidth *c+.5* boxWidth), (float)(boxHeight*r+.5*boxHeight),(float)radius,player2);
                       }
                       else{
                           //something went wrong
                       }
                   }
                }
            }

        }
    } // and of onDraw


    @Override
    public boolean onTouchEvent(MotionEvent e){
        //check to make sure no one has won the game yet
        if(e.getAction() == MotionEvent.ACTION_DOWN && winner == 0){

            //see which column they clicked on
            int column = (int) (e.getX() / boxWidth);

            //now we need to find the lowest spot in that column for the row
            int row = numRows - 1;
            boolean foundRow = false;

            while(!foundRow && row >= 0){
                if(board[column][row] == 0){
                    //we have found an empty box to color
                    foundRow = true;
                }else{
                    row--;
                }
            }

            if(row >=0){
                if(player1sTurn){
                    board[column][row] = 1; //player1's number
                }else{
                    board[column][row] = 2; //player2's number
                }
            }

            //check to see if that was a winning move
            winner = checkForWinner(column,row);
            if(winner != 0){
                Toast toast = Toast.makeText(getContext(),getPlayerName(winner)+ " won the game!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                Log.d("FOUND_WINNER", getPlayerName(winner) + " won the game");
            }

            //toggle whose trun it is
            player1sTurn = !player1sTurn;

            //make sure it's drawn
            invalidate();
        } else if(e.getAction() == MotionEvent.ACTION_DOWN && winner != 0){
            Toast toast = Toast.makeText(getContext(),"Game Over!\nPlayer" + winner + " won!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        return true;
    }

    //check if there is a winner, if there are four in a row for that player
    //return 0 for no winner
    //return 1 if player 1 wins
    //return 2 if player 2 wins
    private int checkForWinner(int column, int row){
        int winnerHere = 0;

        //check the various directions
        if(fourHorizontal(column,row) || fourVertical(column,row) || fourDiagonalLeft(column,row) || fourDiagonalRight(column,row)){
            winnerHere = board[column][row];
        }

        return winnerHere;
    }

    private boolean fourHorizontal(int column, int row){
        int numberFound = 1;
        int valToCheck = board[column][row];

        int columnToCheck = column - 1;
        //check left
        //keep going left while you still see the same val
        while(columnToCheck >= 0 && board[columnToCheck][row] == valToCheck){
            numberFound++;
            columnToCheck--;
        }
        //plus check right
        columnToCheck = column +1;
        while(columnToCheck < numColumns && board[columnToCheck][row] == valToCheck){
            numberFound++;
            columnToCheck++;
        }

        if(numberFound >= 4){
            //we have found 4 horizontally
            return true;
        }
        return false;
    }

    private boolean fourVertical(int column, int row){
        int numberFound = 1;
        int valToCheck = board[column][row];

        int rowToCheck = row - 1;
        //check left
        //keep going left while you still see the same val
        while(rowToCheck >= 0 && board[column][rowToCheck] == valToCheck){
            numberFound++;
            rowToCheck--;
        }
        //plus check right
        rowToCheck = row +1;
        while(rowToCheck < numRows && board[column][rowToCheck] == valToCheck){
            numberFound++;
            rowToCheck++;
        }

        if(numberFound >= 4){
            //we have found 4 horizontally
            return true;
        }
        return false;
    }

    private boolean fourDiagonalRight(int col, int row){
        int numberFound = 1;
        int valeToCheck = board[col][row];

        //go down and left first
        int rowToCheck = row - 1;
        int colToCheck = col - 1;

        while(rowToCheck >= 0 && colToCheck >= 0 && board[colToCheck][rowToCheck] == valeToCheck){
            numberFound++;
            rowToCheck--;
            colToCheck--;
        }

        //now lets go up and to the right
        rowToCheck = row + 1;
        colToCheck = col + 1;

        while (rowToCheck < numRows && colToCheck < numColumns && board[colToCheck][rowToCheck] == valeToCheck) {
            numberFound++;
            rowToCheck++;
            colToCheck++;
        }

        if(numberFound >= 4){
            //found a winner
            return true;
        }
        return false;
    }

    private boolean fourDiagonalLeft(int col, int row){
        int numberFound = 1;
        int valeToCheck = board[col][row];

        //go down and right
        int rowToCheck = row - 1;
        int colToCheck = col + 1;

        while(rowToCheck >= 0 && colToCheck < numColumns && board[colToCheck][rowToCheck] == valeToCheck){
            numberFound++;
            rowToCheck--;
            colToCheck++;
        }

        //now lets go up and to the left
        rowToCheck = row + 1;
        colToCheck = col - 1;

        while (rowToCheck < numRows && colToCheck > 0 && board[colToCheck][rowToCheck] == valeToCheck) {
            numberFound++;
            rowToCheck++;
            colToCheck--;
        }

        if(numberFound >= 4){
            //found a winner
            return true;
        }
        return false;
    }

}
