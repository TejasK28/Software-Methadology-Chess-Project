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

    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        if(this.pieceRank == 9)
            return new HashMap<String, ReturnPiece>();
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
    
    public HashMap<String, ReturnPiece> getMoves()
    {
        return moves;
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
            incrementMoveCount();
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
        //System.out.println("MY FILE IS: " + pieceFile + " " + pieceRank);
        if(this.pieceFile == null)
            return null;
        
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
    // setter method for position
    public void setPosition(PieceFile file, int rank) {
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public void incrementMoveCount()
    {
        ++moveCount;
    }
    


}