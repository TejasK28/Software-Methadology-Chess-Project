package chess;
import java.util.*;
// using Return Piece in Chess.java implement pawn class
public class Pawn extends ReturnPiece implements Piece
{
   /*
    * Removed shadowed vairables because we were referencing the
    wrong variables resulting in improper placement
    */

    /*
     * ArrayList validMoves will hold a string of valid moves that the piece can perform based on the moveCount
     * 
     * TODO think about how to add kill pieces
     */
    ArrayList<String> validMoves;
    int moveCount;

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


        if(Chess.whosPlaying == Chess.Player.white)
        {
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
        }
        else
        {
            //First move ; 2 or 1 move
            if(moveCount == 0)
            {
                for(int i = 1; i <= 2; i++)
                if((this.pieceRank - i) >= 0)
                    validMoves.add("" + this.pieceFile + (this.pieceRank - i));
            }
            else
            {
                validMoves.add("" + this.pieceFile + (this.pieceRank - 1));
            }
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

        //TODO test this method
        identifyPossibleKills();
        
        /*
         * If current piece can move,
            * then change the position of this piece,
            * and increment the moveCount integer to keep track of the count
         * 
         * else
            * change the static returnPlay object message enum to ILLEGAL MOVE
         * 
         * TODO implement the identification of kill pieces
         */
            if(validMoves.contains("" + newFile + newRank))
            {
                this.pieceFile = newFile;
                this.pieceRank = newRank;
                moveCount++;      
                //A legal move return a null message
                Chess.returnPlay.message = null;
            }
            else
                Chess.returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;

            // TODO delete this print message
            System.out.println("VALID MOVES: " + validMoves + "\nMOVE COUNT: " + moveCount);
    }

    /*
     * Overriden method from the Piece interface
     * 
     * Will return a string representing Filerank+rank
     * 
     * ex. "e4"
     */
    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }


    /*
     * Helper method to add any pieces that are within kill range
     * to get added to the validMoves arrayList
     */

     // TODO I never differentiated between black/white pieces while indeitfying kill pieces
    public void identifyPossibleKills()
    {
        String pieceFileString = "" + this.pieceFile;
        int nextIndex = ReturnPiece.PieceFile.valueOf(pieceFileString).ordinal() + 1;
        int previousIndex = ReturnPiece.PieceFile.valueOf(pieceFileString).ordinal() - 1;
        
        /*
         * Will change based on black/white
         */
        int targetRank;
        if(Chess.whosPlaying == Chess.Player.white)
            targetRank = this.pieceRank + 1;
        else
            targetRank = this.pieceRank - 1;


        if(nextIndex < 8 && targetRank <= 8 && targetRank >= 0)
        {

            if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank))
            {
                validMoves.add("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank);
            }
                
        }   
        if(previousIndex >= 0 && targetRank <= 8 && targetRank >= 0)
        {
            if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank))
            {
                validMoves.add("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank);
            }
        }
    }
}