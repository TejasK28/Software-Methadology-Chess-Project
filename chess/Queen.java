package chess;

public class Queen extends ReturnPiece implements Piece{

    // constructor
    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wq or Bq
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(PieceFile newFile, int newRank) {
        // check if the move is valid
        if (isValidMove(newFile, newRank)) {
            // if valid, move the queen to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the queen
            return this;
        }
    }

    public boolean isValidMove(PieceFile newFile, int newRank) {
        // check if the move is valid for the queen
        // a queen can move any number of squares in any direction: horizontally, vertically, or diagonally
        // so the absolute difference between the current and new rank (row) should be the same as the absolute difference between the current and new file (column)
        // or the current and new rank should be the same, or the current and new file should be the same
        // return true if the move is valid, false otherwise

        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int currentFile = this.pieceFile.ordinal();
        int newFileInt = newFile.ordinal();

        return Math.abs(this.pieceRank - newRank) == Math.abs(currentFile - newFileInt) || this.pieceRank == newRank || currentFile == newFileInt;
    }

    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }
}