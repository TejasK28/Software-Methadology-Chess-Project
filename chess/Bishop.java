package chess;
import java.util.*;

public class Bishop extends ReturnPiece implements Piece{

    ArrayList<String> validMoves;
    int moveCount;
    String color;

    //TODO TEST FIELDS
    Map<String, ReturnPiece> moves;
    // constructor
    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wb or Bb
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        validMoves = new ArrayList<String>();
        moveCount = 0;
        color = this.pieceType.toString().substring(0,1).toUpperCase();
        moves = new HashMap<String, ReturnPiece>();
    }

    public void move(PieceFile newFile, int newRank) {
        populateRegularAndKillMoves(); // populates moves hashmap with the appropriate moves for standard/kill plays

        System.out.println("THE VALID MOVES ARE: " + this.moves);

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
    

    public void populateRegularAndKillMoves() {
        // clear the moves hashmap
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
    }

    public String getStringOfPosition(PieceFile file, int rank)
    {
        return "" + file + rank;
    }

    @Override
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