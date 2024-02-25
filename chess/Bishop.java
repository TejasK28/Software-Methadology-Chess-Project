package chess;
import java.util.*;

public class Bishop extends Piece {


    // constructor
    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wb or Bb
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        this.color = pieceType.toString().toUpperCase().substring(0, 1);

        moves = new HashMap<String, ReturnPiece>();
    }


    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        if(this.pieceRank == 9)
            return new HashMap<String, ReturnPiece>();
        moves.clear();
        // populate the moves hashmap with the appropriate moves for standard/kill plays
        // consider 4 directions: up-right, up-left, down-right, down-left
        // once hit a piece, stop
        // if the piece is of the same color, stop
        // if the piece is of the opposite color, add to the moves hashmap and stop
        // if the piece is empty, add to the moves hashmap and continue

        // get the file and rank of the piece
        PieceFile file = this.pieceFile;
        int rank = this.pieceRank;

        // up-right
        while (file.ordinal() < 7 && rank < 8) {
            file = PieceFile.values()[file.ordinal() + 1];
            rank++;
            if (Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                if (Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    break;
                }
                if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    moves.put(getStringOfPosition(file, rank), Chess.getPieceFromPosition(getStringOfPosition(file, rank)));
                    break;
                }
            }
            if (!Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                moves.put(getStringOfPosition(file, rank), null);
            }
        }

        // up-left
        file = this.pieceFile;
        rank = this.pieceRank;
        while (file.ordinal() > 0 && rank < 8) {
            file = PieceFile.values()[file.ordinal() - 1];
            rank++;
            if (Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                if (Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    break;
                }
                if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    moves.put(getStringOfPosition(file, rank), Chess.getPieceFromPosition(getStringOfPosition(file, rank)));
                    break;
                }
            }
            if (!Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                moves.put(getStringOfPosition(file, rank), null);
            }
        }

        // down-right
        file = this.pieceFile;
        rank = this.pieceRank;
        while (file.ordinal() < 7 && rank > 1) {
            file = PieceFile.values()[file.ordinal() + 1];
            rank--;
            if (Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                if (Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    break;
                }
                if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    moves.put(getStringOfPosition(file, rank), Chess.getPieceFromPosition(getStringOfPosition(file, rank)));
                    break;
                }
            }
            if (!Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                moves.put(getStringOfPosition(file, rank), null);
            }
        }

        // down-left
        file = this.pieceFile;
        rank = this.pieceRank;
        while (file.ordinal() > 0 && rank > 1) {
            file = PieceFile.values()[file.ordinal() - 1];
            rank--;
            if (Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                if (Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    break;
                }
                if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(file, rank)).equals(this.color)) {
                    moves.put(getStringOfPosition(file, rank), Chess.getPieceFromPosition(getStringOfPosition(file, rank)));
                    break;
                }
            }
            if (!Chess.pieceExistsAt(getStringOfPosition(file, rank))) {
                moves.put(getStringOfPosition(file, rank), null);
            }
        }

        return moves;
    }
}