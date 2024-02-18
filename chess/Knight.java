package chess;
import java.util.*;

public class Knight extends Piece {

    /*
     * Knight constructor
     * 
     * Initializes the pieceType, PieceFile, and PieceRank
     */
    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wn or Bn
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    /*
     * populateRegularAndKillMoves
     * 
     * Populates the moves hashmap with the appropriate moves for standard/kill plays
     * 
     * @return moves hashmap
     */
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        // TODO: populate the moves hashmap with the appropriate moves for standard/kill plays
        return null;
    }
    
}