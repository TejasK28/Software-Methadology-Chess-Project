package chess;

//using Return Piece in Chess.java implement king class
public class King extends ReturnPiece implements Piece{

    // constructor
    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wk or Bk
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    public ReturnPiece move(PieceFile newFile, int newRank) {
        // check if the move is valid
        if (isValidMove(newFile, newRank)) {
            // if valid, move the king to the new position
            this.pieceRank = newRank;
            this.pieceFile = newFile;
            return this;
        } else {
            // if invalid, return the current position of the king
            return this;
        }
    }
    
    public boolean isValidMove(PieceFile newFile, int newRank) {
        // check if the move is valid for the king
        // a king can move one square in any direction: horizontally, vertically, or diagonally
        // so the absolute difference between the current and new rank (row) should be <= 1
        // and the absolute difference between the current and new file (column) should be <= 1

        int rankDifference = Math.abs(this.pieceRank - newRank);
        // file is an enum, so we need to convert it to an int
        // convert the enum to an int by getting its ordinal value
        int fileDifference = Math.abs(this.pieceFile.ordinal() - newFile.ordinal());

        // return true if the move is valid, false otherwise
        return rankDifference <= 1 && fileDifference <= 1;
    }

    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }

}
