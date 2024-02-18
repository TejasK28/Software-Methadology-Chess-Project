/*
 * The idea of this is to have all the pieces classes implement
 * this Piece interface which will house the methods all the pieces will need
 * 
 * Interface will update as we go
 */

package chess;
import chess.ReturnPiece.PieceFile;
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

    public void move(PieceFile newFile, int newRank) {
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
}