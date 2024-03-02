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


        // System.out.println("IM KING: " + this + " : my POSITION IS : " + this.getColor() );
        
    }

    @Override
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() {
        if(this.pieceRank == 9)
            return new HashMap<String, ReturnPiece>();
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

        // print possible moves
        System.out.println("Possible moves for " + this + " : " + moves);
        
        // print pieces on board
        System.out.println("Pieces on board: " + Chess.returnPlay.piecesOnBoard);

        Set<Map.Entry<String, ReturnPiece>> movesEntries = new HashSet<>(moves.entrySet());

        for (Map.Entry<String, ReturnPiece> entry : movesEntries) 
        {
            String position = entry.getKey();
            ReturnPiece returnPiece = entry.getValue();

            if (returnPiece != null) {
                // looping through the opponent pieces to see if they can move to the king's possible moves
                for (ReturnPiece p : Chess.returnPlay.piecesOnBoard) {
                    Piece piece = (Piece) p;
                    // print piece
                    // System.out.println("Piece: " + piece);
                    if (piece.getColor().equals(this.color)) {
                        continue;
                    } else {
                        // get the piece of the position
                        Piece pieceAtPosition = (Piece) Chess.getPieceFromPosition(position);

                        // change color to the opposite 
                        String oppositeColor = this.color.equals(white) ? black : white;
                        // change color
                        piece.color = this.color;

                        if (piece.isValidMove(PieceFile.valueOf(position.split("")[0]), Integer.parseInt(position.split("")[1]))) {
                            moves.remove(position);
                            // change color back
                            piece.color = oppositeColor;
                            break;
                        }
                        piece.color = oppositeColor;
                        
                    }
                }
            } else {
                //TODO JUST TESTING SOMETHING

                // set original position
                PieceFile originalFile = this.pieceFile;
                int originalRank = this.pieceRank;
                // set new position
                
                //TODO TESTING A BUG
                Piece savePiece = null;
                if(Chess.getPieceFromPosition(position) != null)
                {
                    savePiece = (Piece) Chess.getPieceFromPosition(position);
                    Chess.returnPlay.piecesOnBoard.remove(savePiece);
                }

                this.setPosition(PieceFile.valueOf(position.split("")[0]), Integer.parseInt(position.split("")[1]));


                // Check if the king is still in check after the move
                if (Chess.kingIsInCheck(this.color)) {
                    moves.remove(position);
                }
                this.setPosition(originalFile, originalRank);
                //TODO TESTING A BUG
                if(savePiece != null)
                {
                    Chess.returnPlay.piecesOnBoard.add(savePiece);
                }

            }
        }

        // Call add castle moves to check for the possibility of castling
        if (!inCheck && this.moveCount == 0 && (rightRook.moveCount == 0 || leftRook.moveCount == 0))
        {
            addCastleMovesIfPossible();
        }

        // print possible moves
        System.out.println("Final Possible moves for " + this + " : " + moves);

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
        System.out.println("IM KING: " + this + " : my POSITION IS : " + this.getColor());
        System.out.println("what the heck");
        HashMap<String, ReturnPiece> moves = populateRegularAndKillMoves();
        String moveToPosition = getStringOfPosition(newFile, newRank);
        // print possible moves
        System.out.println("Possible moves for " + this + " : " + moves);
        
        if(moves.containsKey(moveToPosition))//moves the piece if it is included in the moves hashmap
        {
            System.out.println("removing piece at position: " + moves.get(moveToPosition));
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
