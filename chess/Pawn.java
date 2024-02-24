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

    // PAWN CONSTRUCTOR
    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        
        this.pieceType = pieceType; // Wp or Bp
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        this.color = pieceType.toString().toUpperCase().substring(0, 1);
        this.moveCount = 0;

        //TODO TESTING
        moves = new HashMap<String, ReturnPiece>();
    }

    @Override
     public HashMap<String, ReturnPiece> populateRegularAndKillMoves()
    {
        // this clears the hashmap
        moves.clear();
        
        // basic pawn moves for white and black
        //white
        if(this.color.equals(white))
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
                    else
                        break;
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
        else // black turn
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
                    else
                        break;
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
        } // end of blacks turn

        // System.out.println("PAWN BASIC MOVES: " + moves);

        // kill moves 
        PieceFile prevFile = null;
        PieceFile nextFile = null;

        if (this.pieceFile.ordinal() > 0) {
            prevFile = PieceFile.values()[this.pieceFile.ordinal() - 1];
        }

        if (this.pieceFile.ordinal() < PieceFile.values().length - 1) {
            nextFile = PieceFile.values()[this.pieceFile.ordinal() + 1];
        }

        //now we will check for kill pieces for white
        if(Chess.whosPlaying == Chess.Player.white)
        {
            if (this.pieceRank < 8)  {
                int plusOneRank = this.pieceRank + 1;
                if (prevFile != null) {
                    // print 
                    if (Chess.pieceExistsAt(getStringOfPosition(prevFile, plusOneRank))) {
                        if (Chess.isEnemyForThisPiece(getStringOfPosition(this.pieceFile, this.pieceRank), getStringOfPosition(prevFile, plusOneRank))) {
                            moves.put(getStringOfPosition(prevFile, plusOneRank), Chess.getPieceFromPosition(getStringOfPosition(prevFile, plusOneRank)));
                            // System.out.println("COLOR of piece in capture pos.");
                            // System.out.println(Chess.getColorOfPieceFromPosition(getStringOfPosition(prevFile, plusOneRank)));
                        }
                    } 
                }
                
                if (nextFile != null) {
                    if (Chess.pieceExistsAt(getStringOfPosition(nextFile, plusOneRank))) {
                        if (Chess.isEnemyForThisPiece(getStringOfPosition(this.pieceFile, this.pieceRank), getStringOfPosition(nextFile, plusOneRank))) {
                            moves.put(getStringOfPosition(nextFile, plusOneRank), Chess.getPieceFromPosition(getStringOfPosition(nextFile, plusOneRank)));
                            // System.out.println("COLOR of piece in capture pos.");
                            // System.out.println(Chess.getColorOfPieceFromPosition(getStringOfPosition(nextFile, plusOneRank)));
                        }
                    } 
                }
            }
            
            // enpessant checks
            // this piece is on the 5th rank
            // if the piece to the left or right of this piece is a pawn and has moveCount == 1
            // and if the last moved piece was a pawn and moved 2 spaces
            // then this piece can be enpessanted
            if(this.pieceRank == 5)
            {
                // handle enpessant
                // print last moved piece
                System.out.println("LAST MOVED PIECE: " + Chess.lastMovedPiece);
                if(prevFile != null && Chess.pieceExistsAt(getStringOfPosition(prevFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(prevFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
                {

                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(prevFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(prevFile, this.pieceRank + 1), opponentPawn);
                    }
                }
                if(nextFile != null && Chess.pieceExistsAt(getStringOfPosition(nextFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(nextFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
                {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(nextFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(nextFile, this.pieceRank + 1), opponentPawn);
                    }
                }
            }
        }
        else // if we are black so we are looking for white pices and looking down
        {
            if (this.pieceRank > 1)  {
                int minusOneRank = this.pieceRank - 1;
                if (prevFile != null) {
                    // print 
                    if (Chess.pieceExistsAt(getStringOfPosition(prevFile, minusOneRank))) {
                        if (Chess.isEnemyForThisPiece(getStringOfPosition(this.pieceFile, this.pieceRank), getStringOfPosition(prevFile, minusOneRank))) {
                            moves.put(getStringOfPosition(prevFile, minusOneRank), Chess.getPieceFromPosition(getStringOfPosition(prevFile, minusOneRank)));
                            // System.out.println("COLOR of piece in capture pos.");
                            // System.out.println(Chess.getColorOfPieceFromPosition(getStringOfPosition(prevFile, minusOneRank)));
                        }
                    } 
                }
                
                if (nextFile != null) {
                    if (Chess.pieceExistsAt(getStringOfPosition(nextFile, minusOneRank))) {
                        if (Chess.isEnemyForThisPiece(getStringOfPosition(this.pieceFile, this.pieceRank), getStringOfPosition(nextFile, minusOneRank))) {
                            moves.put(getStringOfPosition(nextFile, minusOneRank), Chess.getPieceFromPosition(getStringOfPosition(nextFile, minusOneRank)));
                            // System.out.println("COLOR of piece in capture pos.");
                            // System.out.println(Chess.getColorOfPieceFromPosition(getStringOfPosition(nextFile, minusOneRank)));
                        }
                    } 
                }
            }

            // enpessant checks for black
            // this piece is on the 4th rank
            // if the piece to the left or right of this piece is a pawn and has moveCount == 1
            // and if the last moved piece was a pawn and moved 2 spaces
            // then this piece can be enpessanted
            if(this.pieceRank == 4)
            {
                // handle enpessant
                // print last moved piece
                // System.out.println("LAST MOVED PIECE: " + Chess.lastMovedPiece);
                if(prevFile != null && Chess.pieceExistsAt(getStringOfPosition(prevFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(prevFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP") )
                {

                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(prevFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(prevFile, this.pieceRank - 1), opponentPawn);
                    }
                }
                if(nextFile != null && Chess.pieceExistsAt(getStringOfPosition(nextFile, this.pieceRank)) && Chess.getPieceFromPosition(getStringOfPosition(nextFile, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP") )
                {
                    Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(nextFile, this.pieceRank));
                    if(opponentPawn.moveCount == 1 && Chess.lastMovedPiece == opponentPawn)
                    {
                        canBeEnPessanted = true;
                        moves.put(getStringOfPosition(nextFile, this.pieceRank - 1), opponentPawn);
                    }
                }
            }
            
        }

        // System.out.println("PAWN MOVES for " + this + " : " + moves) ;

        return moves;
    }


    public void addPossibleKills()
    {
        //get the current positiion
        String currenPosition = this.getPosition();

        //if current piece is white we are going to check upriight and upleft
        if(this.color.equals(white))
        {
            //checking the up right position for pawn
            String upRightPositon = Chess.getStringOfPositionWithChange(currenPosition, 1, 1);

            if(upRightPositon != null) // if it is valid position
            {   
                //check if we can add it
                if(Chess.isEnemyForThisPiece(currenPosition, upRightPositon))
                {
                    moves.put(upRightPositon, Chess.getPieceFromPosition(upRightPositon));
                }
            }


            //checking the up right position for pawn
            String upLeftPosition = Chess.getStringOfPositionWithChange(currenPosition, -1, 1);

            if(upLeftPosition != null) // if it is valid position
            {   
                //check if we can add it
                if(Chess.isEnemyForThisPiece(currenPosition, upLeftPosition))
                {
                    moves.put(upLeftPosition, Chess.getPieceFromPosition(upLeftPosition));
                }
            }
        }
        else // black piece
        {

            //checking the downright position for pawn
            String downRightPosition = Chess.getStringOfPositionWithChange(currenPosition, 1, -1);

            if(downRightPosition != null) // if it is valid position
            {   
                //check if we can add it
                if(Chess.isEnemyForThisPiece(currenPosition, downRightPosition))
                {
                    moves.put(downRightPosition, Chess.getPieceFromPosition(downRightPosition));
                }
            }


            //checking the downleft position for pawn
            String downLeftPosition = Chess.getStringOfPositionWithChange(currenPosition, -1, -1);

            if(downLeftPosition != null) // if it is valid position
            {   
                //check if we can add it
                if(Chess.isEnemyForThisPiece(currenPosition, downLeftPosition))
                {
                    moves.put(downLeftPosition, Chess.getPieceFromPosition(downLeftPosition));
                }
            }
        }
        
    }
 
}