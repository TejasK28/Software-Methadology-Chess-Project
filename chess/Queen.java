package chess;
import java.util.*;

public class Queen extends ReturnPiece implements Piece{
    ArrayList<String> validMoves;
    int moveCount;
    String color;
    private Bishop bishop;

    //TODO TEST FIELDS
    Map<String, ReturnPiece> moves;
    // constructor
    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wq or Bq
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.bishop = new Bishop(this.pieceType, this.pieceFile, this.pieceRank);
        this.rook = new Rook(this.pieceType, this.pieceFile, this.pieceRank);

        validMoves = new ArrayList<String>();
        moveCount = 0;
        color = this.pieceType.toString().substring(0,1).toUpperCase();
        moves = new HashMap<String, ReturnPiece>();
    }

    public void move(PieceFile newFile, int newRank) {
        Map<String, ReturnPiece> moves = populateRegularAndKillMoves();

        // populateRegularAndKillMoves(); // populates moves hashmap with the appropriate moves for standard/kill plays

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

    public Map<String, ReturnPiece> populateRegularAndKillMoves() {
        // leverage the bishop and rook classes to get the moves
        // concatenate the two maps
        moves = new HashMap<String, ReturnPiece>();
        moves.putAll(bishop.populateRegularAndKillMoves());
        moves.putAll(rook.populateRegularAndKillMoves());
        return moves;
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