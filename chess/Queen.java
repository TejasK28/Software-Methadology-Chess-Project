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
        this.color = pieceType.toString().toUpperCase().substring(0, 1);
        moves = new HashMap<String, ReturnPiece>();

        if(this.color.equals(white))
            this.bishop = new Bishop(PieceType.WB, this.pieceFile, this.pieceRank);
        else
            this.bishop = new Bishop(PieceType.BB, this.pieceFile, this.pieceRank);


        if(this.color.equals(white))
            this.rook = new Rook(PieceType.WR, this.pieceFile, this.pieceRank);
        else
            this.rook = new Rook(PieceType.BR, this.pieceFile, this.pieceRank); 
    }

    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        if(this.pieceRank == 9)
            return new HashMap<String, ReturnPiece>();

        moves.clear();
        // set new position for the bishop and rook
        this.bishop.setPosition(this.pieceFile, this.pieceRank);
        this.rook.setPosition(this.pieceFile, this.pieceRank);
        // leverage the bishop and rook classes to get the moves
        // concatenate the two maps
        moves.putAll(this.bishop.populateRegularAndKillMoves());
        // print the rook moves
        //System.out.println("rook moves: " + rookMoves);
        moves.putAll(this.rook.populateRegularAndKillMoves());
        return moves;
    }
}