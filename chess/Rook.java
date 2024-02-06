package chess;

public class Rook extends ReturnPiece {
    // current position of the rook
    private PieceType pieceType;
    private PieceFile pieceFile;
    private int pieceRank;

    // constructor
    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wr or Br
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(int newRank, PieceFile newFile) {
        // check if the move is valid
        if (isValidMove(newRank, newFile)) {
            // if valid, move the rook to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the rook
            return this;
        }
    }

    public boolean isValidMove(int newRank, PieceFile newFile) {
        // check if the move is valid for the rook
        // a rook can move any number of squares horizontally or vertically
        // so the current and new rank should be the same, or the current and new file should be the same
        // return true if the move is valid, false otherwise

        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int currentFile = this.pieceFile.ordinal();
        int newFileInt = newFile.ordinal();

        return this.pieceRank == newRank || currentFile == newFileInt;
    }
}