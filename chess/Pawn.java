package chess;

// using Return Piece in Chess.java implement pawn class
public class Pawn extends ReturnPiece {
    // current position of the pawn
    private PieceType pieceType;
    private PieceFile pieceFile;
    private int pieceRank;

    // constructor
    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wp or Bp
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(int newRank, PieceFile newFile) {
        // check if the move is valid
        if (isValidMove(newRank, newFile)) {
            // if valid, move the pawn to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the pawn
            return this;
        }
    }

    public boolean isValidMove(int newRank, PieceFile newFile) {
        // check if the move is valid for the pawn
        // a pawn can move one square forward, or two squares forward if it is in its starting position
        // a pawn can capture an opponent's piece by moving one square forward diagonally
        // a pawn can move only forward, so the new rank should be greater than the current rank
        // a pawn can move only horizontally if it is capturing an opponent's piece, so the absolute difference between the current and new file should be 1
        // return true if the move is valid, false otherwise

        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int currentFile = this.pieceFile.ordinal();
        int newFileInt = newFile.ordinal();

        return (newRank - this.pieceRank == 1 || (newRank - this.pieceRank == 2 && this.pieceRank == 2)) && Math.abs(currentFile - newFileInt) == 1;
    }

}