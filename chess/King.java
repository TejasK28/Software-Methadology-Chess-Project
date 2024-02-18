package chess;
import java.util.*;


//using Return Piece in Chess.java implement king class
public class King extends Piece{

    // constructor
    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wk or Bk
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        color = this.pieceType.toString().substring(0,1).toUpperCase();
        moves = new HashMap<String, ReturnPiece>();
    }

    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        // populate the moves hashmap with the appropriate moves for standard/kill plays
        // consider 8 directions: up, down, left, right, up-right, up-left, down-right, down-left
        for (int fileChange = -1; fileChange <= 1; fileChange++) {
            for (int rankChange = -1; rankChange <= 1; rankChange++) {
                // Skip the case where fileChange and rankChange are both 0, as this corresponds to the King's current position
                if (fileChange == 0 && rankChange == 0) {
                    continue;
                }

                PieceFile newFile = PieceFile.values()[this.pieceFile.ordinal() + fileChange];
                int newRank = this.pieceRank + rankChange;

                // Check if the new file and rank are within the board's boundaries
                if (newFile.ordinal() > 0 && newFile.ordinal() < 8 && newRank > 0 && newRank < 8) {
                    String newPosition = getStringOfPosition(newFile, newRank);
                    // If the new position is not occupied, add it to the moves with null as value
                    if (!Chess.pieceExistsAt(newPosition)) {
                        moves.put(newPosition, null);
                    }         
                    // If the new position is occupied by an opponent's piece, add it to the moves
                    else if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(newFile, newRank)).equals(this.color)) {
                        moves.put(newPosition, Chess.getPieceFromPosition(newPosition));
                    }
                }
            }
        }
        return moves;
    }
}
