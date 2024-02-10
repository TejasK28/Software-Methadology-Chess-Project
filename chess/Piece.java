/*
 * The idea of this is to have all the pieces classes implement
 * this Piece interface which will house the methods all the pieces will need
 * 
 * Interface will update as we go
 */

package chess;
import chess.ReturnPiece.PieceFile;

public interface Piece
{
    /*
     * Methods already included prior to the interface
     */
    void move(PieceFile newFile, int newRank);

    String getPosition();
}