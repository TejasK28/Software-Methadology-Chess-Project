package chess;
import java.util.*;

public class King extends Piece{
    // constructor
    /*
     * Storing the references to the left and right rook for ease of acess
     */
    Rook rightRook;
    Rook leftRook;

    Map<String, Rook> castlingList;

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wk or Bk
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.moveCount = 0;
        this.castlingList = new HashMap<>();

        this.color = pieceType.toString().toUpperCase().substring(0, 1);
        moves = new HashMap<String, ReturnPiece>();

        rightRook = (Rook)Chess.getPieceFromPosition("h1");
        leftRook = (Rook)Chess.getPieceFromPosition("a1");
    }

    @Override
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        
        moves.clear();
        castlingList.clear();

        // populate the moves hashmap with the appropriate moves for standard/kill plays
        // consider 8 directions: up, down, left, right, up-right, up-left, down-right, down-left
        for (int fileChange = -1; fileChange <= 1; fileChange++) {
            for (int rankChange = -1; rankChange <= 1; rankChange++) {
                // Skip the case where fileChange and rankChange are both 0, as this corresponds to the King's current position
                if (fileChange == 0 && rankChange == 0) {
                    continue;
                }

                PieceFile newFile = PieceFile.values()[this.pieceFile.ordinal() + fileChange];
                int newRank = this.pieceRank + rankChange;

                // Check if the new file and rank are within the board's boundaries
                if (newFile.ordinal() > 0 && newFile.ordinal() < 8 && newRank > 0 && newRank < 8) {
                    String newPosition = getStringOfPosition(newFile, newRank);
                    // If the new position is not occupied, add it to the moves with null as value
                    if (!Chess.pieceExistsAt(newPosition)) {
                        moves.put(newPosition, null);
                    }         
                    // If the new position is occupied by an opponent's piece, add it to the moves
                    else if (!Chess.getColorOfPieceFromPosition(getStringOfPosition(newFile, newRank)).equals(this.color)) {
                        moves.put(newPosition, Chess.getPieceFromPosition(newPosition));
                    }
                }
            }
        }
        
        //removing any moves that would result in a check
        for(String position : moves.keySet())
        {
            if(Chess.getPieceFromPosition(position) != null)
                if(Chess.thisPieceKillMeButICantKillThatPiece((Piece)Chess.getPieceFromPosition(this.getPosition()), (Piece)Chess.getPieceFromPosition(position)))
                {
                    moves.remove(position);
                }
        }
        

        //call addcastle moves to check for possibility of castling
            addCastleMovesIfPossible();
        //returns the moves
        return moves;
    }

    /*
     * TODO implement canCastle
     * but we need to implement check first
     */
    public void addCastleMovesIfPossible()
    {
        /*
         * How do we know we can castle for white for the rook in on the right?
         */
        /*
         * 1) check we are in white
         * 2) check if the current move is 0 and the rook to the right is 0
         * 3) the 2 positions to the right should be empty and SAFE
         * 4) check if we are not currently in check
         */

         //1
         //2
         //4
         if(!Chess.kingIsInCheck(this.getColor()) && this.getColor().equals(white) && this.moveCount == 0 && rightRook.moveCount == 0)
         {
            String firstPosToRight = Chess.getStringOfPositionWithChange(this.getPosition(), 1, 0);
            String secondPosToRight = Chess.getStringOfPositionWithChange(this.getPosition(), 2, 0);

            //3
            if(firstPosToRight != null && secondPosToRight != null)
            {
                if(Chess.isPositionSafeFor(firstPosToRight, white) && Chess.isPositionSafeFor(secondPosToRight, white))
                {
                    //we are putting the castle move in the moves hashmap 
                    //and the local hashmap for castling
                    moves.put(secondPosToRight, null);
                    castlingList.put(secondPosToRight, rightRook);
                }
            }
         }

         //this implementation takes care of the left side white castle
         if(!Chess.kingIsInCheck(this.getColor()) && this.getColor().equals(white) && this.moveCount == 0 && leftRook.moveCount == 0)
         {
            String firstPosToLeft = Chess.getStringOfPositionWithChange(this.getPosition(), -1, 0);
            String secondPosToLeft = Chess.getStringOfPositionWithChange(this.getPosition(), -2, 0);

            //3
            if(firstPosToLeft != null && secondPosToLeft != null)
            {
                if(Chess.isPositionSafeFor(firstPosToLeft, white) && Chess.isPositionSafeFor(secondPosToLeft, white))
                {
                    //we are putting the castle move in the moves hashmap 
                    //and the local hashmap for castling
                    moves.put(secondPosToLeft, null);
                    castlingList.put(secondPosToLeft, leftRook);
                }
            }
         }
    }

    /*
     * I'm overriding the move method just for the king because of the castling case to also move the rook automatically
     */

    @Override
    public void move(PieceFile newFile, int newRank) 
    {
        HashMap<String, ReturnPiece> moves = populateRegularAndKillMoves();
        String moveToPosition = getStringOfPosition(newFile, newRank);

        if(moves.containsKey(moveToPosition))//moves the piece if it is included in the moves hashmap
        {
            if(moves.get(moveToPosition) != null) // movement is not null so we remove
            {
                Chess.returnPlay.piecesOnBoard.remove(moves.get(moveToPosition));
            }

            //actual movements
            this.pieceFile = newFile;
            this.pieceRank = newRank;
            //increment move count
            incrementMoveCount();
            
            
            //TODO HANDLE CASTLING
            if(this.color.equals(white))
            {
                //castling white rightside
                if(castlingList.containsKey(moveToPosition) && castlingList.get(moveToPosition)==rightRook)
                {
                    Rook r = castlingList.get(moveToPosition);
                    PieceFile rookFile = PieceFile.f;
                    int rookRank = 1;
                    r.setPosition(rookFile, rookRank);
                } // castling white left side
                else if(castlingList.containsKey(moveToPosition)&& castlingList.get(moveToPosition)==leftRook)
                {
                    Rook r = castlingList.get(moveToPosition);
                    PieceFile rookFile = PieceFile.d;
                    int rookRank = 1;
                    r.setPosition(rookFile, rookRank);
                }
            }
        
            
        }
        else
        {
            Chess.returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
        }
    }

    @Override
    public void incrementMoveCount()
    {
        ++moveCount;
    }


}
