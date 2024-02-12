package chess;
import java.util.*;

import chess.Chess.Player;

// using Return Piece in Chess.java implement pawn class
public class Pawn extends ReturnPiece implements Piece
{
    /*
     * @ArrayList validMoves will hold a string of valid moves that the piece can perform based on the moveCount
     * 
     * @moveCount contains the numeber of moves the current piece has performed
     * 
     * @color is a string that holds the first letter of the current color
     *  ex. W or B
     */
    ArrayList<String> validMoves;
    int moveCount;
    String color;
    boolean canBeEnPessanted;
    int lastMoved;

    //TODO TEST FIELDS
    Map<String, ReturnPiece> moves;

    // PAWN CONSTRUCTOR
    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank) {
        
        this.pieceType = pieceType; // Wp or Bp
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        validMoves = new ArrayList<String>();
        moveCount = 0;
        color = this.pieceType.toString().substring(0,1).toUpperCase();
        canBeEnPessanted = false;
        lastMoved = Chess.globalMoveCount;

        //TODO TESTING
        moves = new HashMap<String, ReturnPiece>();
    }

    // MOVE METHODS

    //TODO TESTING NEW MOVE METHODS
    //SEEMS TO BE AN ISSUE WITH ENPESSANT
    // TODO FIX ENPESSANT -- MAKE SURE TO VALIDATE THE PROPER MOVES BASED ON MOVECOUNT
    public void newMove(PieceFile newFile, int newRank)
    {
        populateRegularAndKillMoves(); // populates moves hashmap with the appropriate moves for standard/kill plays

        System.out.println("THE VALID MOVES ARE: " + this.moves);

        if(moves.containsKey(getStringOfPosition(newFile, newRank)))//moves the piece if it is included in the moves hashmap
        {
            if(moves.get(getStringOfPosition(newFile, newRank)) != null) // movement is not null so we remove
            {
                Chess.returnPlay.piecesOnBoard.remove(moves.get(getStringOfPosition(newFile, newRank)));
            }

            //actual movements
            this.pieceFile = newFile;
            this.pieceRank = newRank;
            //increment move count
            moveCount++;
        }
        else
        {
            Chess.returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
        }
    }

    // TODO NEW MOVE HELPER METHODS

    public void populateRegularAndKillMoves()
    {
        //clear original moves
        moves.clear();

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
            //enpessant checks again
            if(previousColumn != null && Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank)) != null && Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
            {
                Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank));
                if(opponentPawn.moveCount == 1 )
                {
                    // TODO GLOBAL ENPESSANT CHECK DELETE THIS IF IT DOESN'T WORK
                    // TODO TEST THIS
                    if(opponentPawn.canBeEnPessanted)
                        moves.put(getStringOfPosition(opponentPawn.pieceFile, opponentPawn.pieceRank + 1), opponentPawn);
                }
            }
            if(nextColumn != null && Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank)) != null && Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("BP") )
            {
                Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank));
                if(opponentPawn.moveCount == 1)
                {
                    //TODO TEST THIS
                    if(opponentPawn.canBeEnPessanted)
                        moves.put(getStringOfPosition(opponentPawn.pieceFile, opponentPawn.pieceRank + 1), opponentPawn);
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
            //enpessant checks
            if(previousColumn != null && Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank)) != null && Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP") )
            {
                Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(previousColumn, this.pieceRank));
                if(opponentPawn.moveCount == 1)
                {
                    //TODO TEST
                    if(opponentPawn.canBeEnPessanted)
                        moves.put(getStringOfPosition(opponentPawn.pieceFile, opponentPawn.pieceRank - 1), opponentPawn);
                }
            }
            if(nextColumn != null && Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank)) != null && Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank)).toString().split(":")[1].toUpperCase().equals("WP") )
            {
                Pawn opponentPawn = (Pawn) Chess.getPieceFromPosition(getStringOfPosition(nextColumn, this.pieceRank));
                if(opponentPawn.moveCount == 1)
                {
                    //TODO TEST
                    if(opponentPawn.canBeEnPessanted)
                        moves.put(getStringOfPosition(opponentPawn.pieceFile, opponentPawn.pieceRank - 1), opponentPawn);
                }
            }

            
        }
            
            
    }


    //TODO Helper method for new move
    public String getStringOfPosition(PieceFile file, int rank)
    {
        return "" + file + rank;
    }

    //----------------------------------------------------------------

    //GETTER METHODS

    /*
     * Overriden method from the Piece interface
     * 
     * Will return a string representing Filerank+rank
     * 
     * ex. "e4"
     */
    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }


    /*
     * Getter method that will return the first letter of the piece color
     * 
     * ex. "W"
     * ex. "B"
     * 
     * Expect uppercase letter
     */
    public String getColor()
    {
        return this.color;
    }

    @Override
    public void move(PieceFile newFile, int newRank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }


}