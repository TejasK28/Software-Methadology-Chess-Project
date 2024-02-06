package chess;

public class Knight extends ReturnPiece {
    // current position of the knight
    private PieceType pieceType;
    private PieceFile pieceFile;
    private int pieceRank;

    // constructor
    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wn or Bn
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(int newRank, PieceFile newFile) {
        // check if the move is valid
        if (isValidMove(newRank, newFile)) {
            // if valid, move the knight to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the knight
            return this;
        }
    }

    public boolean isValidMove(int newRank, PieceFile newFile) {
        // check if the move is valid for the knight
        // a knight can move in an L-shape: two squares in one direction and one square in a perpendicular direction
        // so the absolute difference between the current and new rank (row) should be 2 and the absolute difference between the current and new file (column) should be 1
        // or the absolute difference between the current and new rank (row) should be 1 and the absolute difference between the current and new file (column) should be 2
        // return true if the move is valid, false otherwise

        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int currentFile = this.pieceFile.ordinal();
        int newFileInt = newFile.ordinal();

        return (Math.abs(this.pieceRank - newRank) == 2 && Math.abs(currentFile - newFileInt) == 1) || (Math.abs(this.pieceRank - newRank) == 1 && Math.abs(currentFile - newFileInt) == 2);
    }
}