package chess;
import java.util.*;

public class Queen extends Piece{
    Bishop bishop;
    Rook rook;

    // constructor
    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wq or Bq
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.bishop = new Bishop(this.pieceType, this.pieceFile, this.pieceRank);
        this.rook = new Rook(this.pieceType, this.pieceFile, this.pieceRank);

        this.color = pieceType.toString().toUpperCase().substring(0, 1);
    }

    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        // leverage the bishop and rook classes to get the moves
        // concatenate the two maps
        moves = new HashMap<String, ReturnPiece>();
        moves.putAll(this.bishop.populateRegularAndKillMoves());
        HashMap<String, ReturnPiece> rookMoves = this.rook.populateRegularAndKillMoves();
        // print the rook moves
        //System.out.println("rook moves: " + rookMoves);
        moves.putAll(this.rook.populateRegularAndKillMoves());
        return moves;
    }
}