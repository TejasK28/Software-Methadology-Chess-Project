package chess;
import java.util.*;

// using Return Piece in Chess.java implement pawn class
public class Pawn extends Piece
{
    /*
     * @ArrayList validMoves will hold a string of valid moves that the piece can perform based on the moveCount
     * 
     * @moveCount contains the numeber of moves the current piece has performed
     * 
     * @color is a string that holds the first letter of the current color
     *  ex. W or B
     */
    boolean canBeEnPessanted;
    int lastMoved;

    // PAWN CONSTRUCTOR
    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        
        this.pieceType = pieceType; // Wp or Bp
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        this.color = pieceType.toString().toUpperCase().substring(0, 1);
        canBeEnPessanted = false;
        lastMoved = Chess.globalMoveCount;

        //TODO TESTING
        moves = new HashMap<String, ReturnPiece>();
    }

    // TODO NEW MOVE HELPER METHODS

    public HashMap<String, ReturnPiece> populateRegularAndKillMoves()
    {
        //white
        if(Chess.whosPlaying == Chess.Player.white)
        {
            //Handling the first 2/1 unobstructed spaces
            if(this.moveCount == 0)// add 2 moves on the first move
            {
                for(int i = 1; i <= 2; i++)
                {
                    int newRank = this.pieceRank + i; // newrank +1 & +2

                    if(newRank <= 8 && newRank > 0 && !Chess.pieceExistsAt(getStringOfPosition(this.pieceFile, newRank))) // if newRank is within the height of board && no piece exists on the toBeAdded space
                    {
                        moves.put(getStringOfPosition(this.pieceFile, newRank), null); // moves hashmap will have the straight path moves if spaces exist
                    }
                }
            }
            else // if we are on the second move and so on we can only move once
            {
                int newRank = this.pieceRank + 1; // newrank +1 & +2

                if(newRank <= 8 && newRank > 0 && !Chess.pieceExistsAt(getStringOfPosition(this.pieceFile, newRank))) // if newRank is within the height of board && no piece exists on the toBeAdded space
                {
                    moves.put(getStringOfPosition(this.pieceFile, newRank), null); // moves hashmap will have the straight path moves if spaces exist
                }
            }
        }
        else
        {
            //Handling the first 2/1 unobstructed spaces
            if(this.moveCount == 0)// add 2 moves on the first move
            {
                for(int i = 1; i <= 2; i++)
                {
                    int newRank = this.pieceRank - i; // newrank +1 & +2

                    if(newRank <= 8 && newRank > 0 && !Chess.pieceExistsAt(getStringOfPosition(this.pieceFile, newRank))) // if newRank is within the height of board && no piece exists on the toBeAdded space
                    {
                        moves.put(getStringOfPosition(this.pieceFile, newRank), null); // moves hashmap will have the straight path moves if spaces exist
                    }
                }
            }
            else // if we are on the second move and so on we can only move once
            {
                int newRank = this.pieceRank - 1; // newrank +1 & +2

                if(newRank <= 8 && newRank > 0 && !Chess.pieceExistsAt(getStringOfPosition(this.pieceFile, newRank))) // if newRank is within the height of board && no piece exists on the toBeAdded space
                {
                    moves.put(getStringOfPosition(this.pieceFile, newRank), null); // moves hashmap will have the straight path moves if spaces exist
                }
            }
        }


        // print moves
        //System.out.println("MOVES: " + moves);
        

        //check for the next column
        PieceFile nextColumn;
        int nextColumnIndex = this.pieceFile.ordinal() + 1;

        if(nextColumnIndex < 8) // if the next column is within the width of the board
        {
            nextColumn = PieceFile.values()[this.pieceFile.ordinal() + 1];
        }
        else // otherwise nextColumn is null
        {
            nextColumn = null;
        }

        //check for the previous columns
        PieceFile previousColumn;
        int previousColumnIndex = this.pieceFile.ordinal() - 1;
        
        if(previousColumnIndex >= 0) // if the previous column is within the width of the board
        {
            previousColumn = PieceFile.values()[this.pieceFile.ordinal() - 1];
        }
        else // will be null otherwise
        {
            previousColumn = null;
        }     

        //now we will check for kill pieces for white
        if(Chess.whosPlaying == Chess.Player.white)
        {
            //check for the next column
            nextColumnIndex = this.pieceFile.ordinal() + 1;

            if(nextColumnIndex < 8) // if the next column is within the width of the board
            {
                nextColumn = PieceFile.values()[this.pieceFile.ordinal() + 1];
            }
            else // otherwise nextColumn is null
            {
                nextColumn = null;
            }

            //check for the previous columns
            previousColumnIndex = this.pieceFile.ordinal() - 1;
            
            if(previousColumnIndex >= 0) // if the previous column is within the width of the board
            {
                previousColumn = PieceFile.values()[this.pieceFile.ordinal() - 1];
            }
            else // will be null otherwise
            {
                previousColumn = null;
            }     
            
            int plusOneRank;

            if(this.pieceRank + 1 <= 8)
                plusOneRank = this.pieceRank + 1;
            else
                plusOneRank = -1;
            
            if(previousColumn != null && plusOneRank != -1 && Chess.pieceExistsAt(getStringOfPosition(previousColumn, plusOneRank)) && Chess.getColorOfPieceFromPosition(getStringOfPosition(previousColumn, plusOneRank)).equals("B"))
            {
                moves.put(getStringOfPosition(previousColumn, plusOneRank), Chess.getPieceFromPosition(getStringOfPosition(previousColumn, plusOneRank)));
            }
            if(nextColumn != null && plusOneRank != -1 && Chess.pieceExistsAt(getStringOfPosition(nextColumn, plusOneRank)) && Chess.getColorOfPieceFromPosition(getStringOfPosition(nextColumn, plusOneRank)).equals("B"))
            {
                moves.put(getStringOfPosition(nextColumn, plusOneRank), Chess.getPieceFromPosition(getStringOfPosition(nextColumn, plusOneRank)));
            }

            // enpessant checks
            // this piece is on the 5th rank
            // if the piece to the left or right of this piece is a pawn and has moveCount == 1
            // and if the last moved piece was a pawn and moved 2 spaces
            // then this piece can be enpessanted
            if(this.pieceRank == 5)
            {
                // handle enpessant & handle left-most and right-most pawns
                PieceFile leftFile = null;
                PieceFile rightFile = null;

                if (this.pieceFile.ordinal() > 0) {
                    leftFile = PieceFile.values()[this.pieceFile.ordinal() - 1];
                }

                if (this.pieceFile.ordinal() < PieceFile.values().length - 1) {
                    rightFile = PieceFile.values()[this.pieceFile.ordinal() + 1];
                }
                // print last moved piece
                System.out.println("LAST MOVED PIECE: " + Chess.lastMovedPiece);
                if(leftFile != null && Chess.pieceExistsAt(getStringOfPosition(leftFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(leftFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
                {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(leftFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(leftFile, this.pieceRank + 1), opponentPawn);
                    }
                }
                if(leftFile != null && Chess.pieceExistsAt(getStringOfPosition(rightFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(rightFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
                {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(rightFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(rightFile, this.pieceRank + 1), opponentPawn);
                    }
                }
            }
        else // if we are black so we are looking for white pices and looking down
        {
            int minusOneRank;

            if(this.pieceRank - 1 > 0) // minusOneRank is within the height of the board
            minusOneRank = this.pieceRank - 1;
            else
            minusOneRank = -1;
            
            if(previousColumn != null && minusOneRank != -1 && Chess.pieceExistsAt(getStringOfPosition(previousColumn, minusOneRank)) && Chess.getColorOfPieceFromPosition(getStringOfPosition(previousColumn, minusOneRank)).equals("W"))
            {
                moves.put(getStringOfPosition(previousColumn, minusOneRank), Chess.getPieceFromPosition(getStringOfPosition(previousColumn, minusOneRank)));
            }
            if(nextColumn != null && minusOneRank != -1 && Chess.pieceExistsAt(getStringOfPosition(nextColumn, minusOneRank)) && Chess.getColorOfPieceFromPosition(getStringOfPosition(nextColumn, minusOneRank)).equals("W"))
            {
                moves.put(getStringOfPosition(nextColumn, minusOneRank), Chess.getPieceFromPosition(getStringOfPosition(nextColumn, minusOneRank)));
            }

            // enpessant checks for black
            // this piece is on the 4th rank
            // if the piece to the left or right of this piece is a pawn and has moveCount == 1
            // and if the last moved piece was a pawn and moved 2 spaces
            // then this piece can be enpessanted
            if(this.pieceRank == 4)
            {
                // handle enpessant & handle left-most and right-most pawns
                PieceFile leftFile = null;
                PieceFile rightFile = null;

                if (this.pieceFile.ordinal() > 0) {
                    leftFile = PieceFile.values()[this.pieceFile.ordinal() - 1];
                }

                if (this.pieceFile.ordinal() < PieceFile.values().length - 1) {
                    rightFile = PieceFile.values()[this.pieceFile.ordinal() + 1];
                }
                // print last moved piece
                System.out.println("LAST MOVED PIECE: " + Chess.lastMovedPiece);

                if (leftFile != null && Chess.pieceExistsAt(getStringOfPosition(leftFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(leftFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP")) {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(leftFile, this.pieceRank));
                    // print if Chess.lastMovedPiece == opponentPawn
                    System.out.println("LAST MOVED PIECE == OPPONENT PAWN: " + (Chess.lastMovedPiece == opponentPawn));
                    if (opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn) {
                        canBeEnPessanted = true;
                        System.out.println("CAN BE ENPESANTED: " + canBeEnPessanted);
                        moves.put(getStringOfPosition(leftFile, this.pieceRank - 1), opponentPawn);
                    }
                }
                if (rightFile != null && Chess.pieceExistsAt(getStringOfPosition(rightFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(rightFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP")) {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(rightFile, this.pieceRank));
                    // print if Chess.lastMovedPiece == opponentPawn
                    System.out.println("LAST MOVED PIECE == OPPONENT PAWN: " + (Chess.lastMovedPiece == opponentPawn));
                    if (opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn) {
                        canBeEnPessanted = true;
                        System.out.println("CAN BE ENPESANTED: " + canBeEnPessanted);
                        moves.put(getStringOfPosition(rightFile, this.pieceRank - 1), opponentPawn);
                    }
                }
            }
            
        }
        }

        return moves;
    }

}