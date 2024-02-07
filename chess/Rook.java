package chess;

public class Rook extends ReturnPiece implements Piece{
    // current position of the rook


    // constructor
    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wr or Br
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(PieceFile newFile, int newRank) {
        // check if the move is valid
        if (isValidMove(newFile, newRank)) {
            // if valid, move the rook to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the rook
            /*
             * TODO delete this code
             * I just put it here for it to work
             */

            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        }
    }

    public boolean isValidMove(PieceFile newFile, int newRank) {
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

    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }
}