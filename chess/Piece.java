/*
 * The idea of this is to have all the pieces classes extend
 * this Piece class which will house the methods all the pieces will need
*/

package chess;
import java.util.*;


public class Piece extends ReturnPiece
{
    HashMap<String, ReturnPiece> moves;
    int moveCount = 0;
    String color;
    String white = "W";
    String black = "B";

    HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        return null;
    };

    public boolean isValidMove(PieceFile newFile, int newRank) {
        // check if the move is valid for the piece
        // return true if the move is valid, false otherwise
        HashMap<String, ReturnPiece> moves = this.populateRegularAndKillMoves();
        if (moves == null) {
            return false;
        }
        return moves.containsKey(getStringOfPosition(newFile, newRank));
    }

    public void move(PieceFile newFile, int newRank) 
    {
        HashMap<String, ReturnPiece> moves = populateRegularAndKillMoves();

        if(moves.containsKey(getStringOfPosition(newFile, newRank)))//moves the piece if it is included in the moves hashmap
        {
            if(moves.get(getStringOfPosition(newFile, newRank)) != null) // movement is not null so we remove
            {
                Chess.returnPlay.piecesOnBoard.remove(moves.get(getStringOfPosition(newFile, newRank)));
            }

            //actual movements
            this.pieceFile = newFile;
            this.pieceRank = newRank;
            //increment move count
            moveCount++;
        }
        else
        {
            Chess.returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
        }
    }

    public static String getStringOfPosition(PieceFile file, int rank)
    {
        return "" + file + rank;
    }

    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }
    /*
     * Getter method that will return the first letter of the piece color
     * 
     * ex. "W"
     * ex. "B"
     * 
     * Expect uppercase letter
     */
    public String getColor()
    {
        return this.color;
    }

    /*
     * Takes in the current piece position and the checking piece position
     * 
     * will return true if the 2 colors differ
     * 
     * will return false if the 2 colors are the the same
     */
    public boolean isEnemyForThisPiece(String thisPosition, String thatPosition)
    {
        if(!(((Piece) Chess.getPieceFromPosition(thisPosition)).getColor().equals(((Piece) Chess.getPieceFromPosition(thatPosition)).getColor())))
            return true;
        
        return false;
    }

    
}