/*
 * The idea of this is to have all the pieces classes implement
 * this Piece interface which will house the methods all the pieces will need
 * 
 * Interface will update as we go
 */

package chess;
import chess.ReturnPiece.PieceFile;
import java.util.*;

public interface Piece
{


    String getPosition();

    String getStringOfPosition(PieceFile file, int rank);

    Map<String, ReturnPiece> populateRegularAndKillMoves();

    public default boolean isValidMove(PieceFile newFile, int newRank) {
        // check if the move is valid for the piece
        // return true if the move is valid, false otherwise
        Map<String, ReturnPiece> moves = this.populateRegularAndKillMoves();
        return moves.containsKey(getStringOfPosition(newFile, newRank));
    }
}