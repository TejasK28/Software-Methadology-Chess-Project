package chess;
import java.util.*;

public class King extends Piece{
    // constructor
    /*
     * Storing the references to the left and right rook for ease of acess
     */
    Rook rightRook;
    Rook leftRook;
    boolean inCheck;

    Map<String, Rook> castlingList;

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType; // Wk or Bk
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.moveCount = 0;
        this.castlingList = new HashMap<>();
        this.inCheck = false;

        this.color = pieceType.toString().toUpperCase().substring(0, 1);
        moves = new HashMap<String, ReturnPiece>();
        if(color.equals(white))
        {
            rightRook = (Rook)Chess.getPieceFromPosition("h1");
            leftRook = (Rook)Chess.getPieceFromPosition("a1");
        }
        else
        {
            rightRook = (Rook)Chess.getPieceFromPosition("h8");
            leftRook = (Rook)Chess.getPieceFromPosition("a8");
        }
        
    }

    @Override
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        moves.clear();
        castlingList.clear();

        /*
        * Populate the valid moves
        */
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // Skip the current king's position
                if (i == 0 && j == 0) {
                    continue;
                }

                String checkingPosition = Chess.getStringOfPositionWithChange(this.getPosition(), i, j);

                if (checkingPosition != null && !checkingPosition.equals(this.getPosition())) {
                    if (Chess.isEnemyForThisPiece(this.getPosition(), checkingPosition)) {
                        moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    } else if (Chess.isPositionEmpty(checkingPosition)) {
                        moves.put(checkingPosition, null);
                    }
                }
            }
        }

        // Remove moves that would result in a check
        Iterator<String> iterator = moves.keySet().iterator();
        while (iterator.hasNext()) {
            String position = iterator.next();
            if (Chess.getPieceFromPosition(position) != null && Chess.isEnemyButICantKillIt((Piece) Chess.getPieceFromPosition(this.getPosition()), (Piece) Chess.getPieceFromPosition(position))) {
                iterator.remove(); // if there's an enemy and I can't kill it
            } else if (Chess.willMovePutKingInCheck(this.color, PieceFile.valueOf(position.split("")[0]), Integer.parseInt(position.split("")[1]))) {
                iterator.remove(); // if the moveToPiece will put the king in check then remove
            }
        }

        // Call add castle moves to check for the possibility of castling
        if (!inCheck && this.moveCount == 0 && (rightRook.moveCount == 0 || leftRook.moveCount == 0))
            addCastleMovesIfPossible();

        // Returns the moves
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
         if(!inCheck && this.getColor().equals(white) && this.moveCount == 0 && rightRook.moveCount == 0)
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
         if(!inCheck && this.getColor().equals(white) && this.moveCount == 0 && leftRook.moveCount == 0 && !inCheck)
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

         /*
          * Now we focus on black right side and black left side
          */


          if(!inCheck && this.getColor().equals(black) && this.moveCount == 0 && rightRook.moveCount == 0)
         {
            String firstPosToRight = Chess.getStringOfPositionWithChange(this.getPosition(), 1, 0);
            String secondPosToRight = Chess.getStringOfPositionWithChange(this.getPosition(), 2, 0);

            //3
            if(firstPosToRight != null && secondPosToRight != null)
            {
                if(Chess.isPositionSafeFor(firstPosToRight, black) && Chess.isPositionSafeFor(secondPosToRight, black))
                {
                    //we are putting the castle move in the moves hashmap 
                    //and the local hashmap for castling
                    moves.put(secondPosToRight, null);
                    castlingList.put(secondPosToRight, rightRook);
                }
            }
         }

         //this implementation takes care of the left side white castle
         if(!inCheck && this.getColor().equals(black) && this.moveCount == 0 && leftRook.moveCount == 0)
         {
            String firstPosToLeft = Chess.getStringOfPositionWithChange(this.getPosition(), -1, 0);
            String secondPosToLeft = Chess.getStringOfPositionWithChange(this.getPosition(), -2, 0);

            //3
            if(firstPosToLeft != null && secondPosToLeft != null)
            {
                if(Chess.isPositionSafeFor(firstPosToLeft, black) && Chess.isPositionSafeFor(secondPosToLeft, black))
                {
                    //we are putting the castle move in the moves hashmap 
                    //and the local hashmap for castling
                    moves.put(secondPosToLeft, null);
                    castlingList.put(secondPosToLeft, leftRook);
                }
            }
         }

         /*
          * End of castling implemenmtation for black
          */
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
            else // handling the case for black castling
            {
                //castling black rightside
                if(castlingList.containsKey(moveToPosition) && castlingList.get(moveToPosition)==rightRook)
                {
                    Rook r = castlingList.get(moveToPosition);
                    PieceFile rookFile = PieceFile.f;
                    int rookRank = 8;
                    r.setPosition(rookFile, rookRank);
                } // castling black left side
                else if(castlingList.containsKey(moveToPosition)&& castlingList.get(moveToPosition)==leftRook)
                {
                    Rook r = castlingList.get(moveToPosition);
                    PieceFile rookFile = PieceFile.d;
                    int rookRank = 8;
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
