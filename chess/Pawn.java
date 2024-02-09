package chess;

import java.util.ArrayList;

// using Return Piece in Chess.java implement pawn class
public class Pawn extends ReturnPiece implements Piece
{
   /*
    * Removed shadowed vairables because we were referencing the
    wrong variables resulting in improper placement
    */

    /*
     * ArrayList validMoves will hold a string of 
     */
    ArrayList<String> validMoves;
    int moveCount;
    boolean canMove;

    // constructor initializes the position + arraylist of valid moves
    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wp or Bp
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        validMoves = new ArrayList<String>();
        moveCount = 0;
    }

    /*
     * Instance method that will add the valid moves based on the first move
     * Will also take care of the edge case of exceeding the board max size
     * 
     * TODO implement the enpessant
     * TODO doesn't recognize enemies yet, just a straight line
     */
    public void popoulateValidMoves()
    {
        //Clears arraylist from old valid moves
        validMoves.clear();

        //First move ; 2 or 1 move
        if(moveCount == 0)
        {
            for(int i = 1; i <= 2; i++)
            if((this.pieceRank + i) <= 8)
                validMoves.add("" + this.pieceFile + (this.pieceRank + i));
        }
        else
        {
            validMoves.add("" + this.pieceFile + (this.pieceRank + 1));
        }

        //remove the moves that are not valid due to other pieces on the board
        for(int i = 0; i < Chess.returnPlay.piecesOnBoard.size(); i++)
        {
            //This will get the positon of a piece on the board
            //Will cross reference the initial available moves & remove occupied spaces
            String otherPosition = Chess.returnPlay.piecesOnBoard.get(i).toString().split(":")[0];
            if(validMoves.contains(otherPosition))
                validMoves.remove(otherPosition);

        }

    }

    /*
     * Instance method that will
     *  1) populate valid moved in arraylist
     *  2) update the canMove boolean
     *  3) actually move
     */
    public void move(PieceFile newFile, int newRank) 
    {
        // check if the move is valid
        //TODO fix this method
        popoulateValidMoves();

        //update canmove boolean
        updateCanMove();
            if(canMove)
            {
                this.pieceFile = newFile;
                this.pieceRank = newRank;
                moveCount++;

                System.out.println(validMoves);
            }
            else
                System.out.println("[ERROR] : can't move");     
    }

    /*
     * TODO Check as well if there is another piece on the board position we move to
     * TODO implement this later 
     */
    public boolean isValidMove(PieceFile newFile, int newRank) 
    {
        return false;
    }

    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }

    public void updateCanMove()
    {
        if(validMoves.size() == 0)
            canMove = false;
        else
            canMove = true;
    }


}