package chess;

public class Bishop extends ReturnPiece implements Piece{

    // constructor
    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wb or Bb
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(PieceFile newFile, int newRank) {
        // check if the move is valid
        if (isValidMove(newFile, newRank)) {
            // if valid, move the bishop to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the bishop
            return this;
        }
    }
    
    public boolean isValidMove(PieceFile newFile, int newRank) {
        // check if the move is valid for the bishop
        // a bishop can move any number of squares diagonally
        // so the absolute difference between the current and new rank (row) should be the same as the absolute difference between the current and new file (column)
        // return true if the move is valid, false otherwise

        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int currentFile = this.pieceFile.ordinal();
        int newFileInt = newFile.ordinal();

        return Math.abs(this.pieceRank - newRank) == Math.abs(currentFile - newFileInt);
    }

    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }
}