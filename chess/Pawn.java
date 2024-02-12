package chess;
import java.util.*;

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


        //TODO TESTING
        moves = new HashMap<String, ReturnPiece>();
    }

    // MOVE METHODS

    //TODO TESTING NEW MOVE METHODS

    public void newMove(PieceFile newFile, int newRank)
    {
        populateRegularAndKillMoves(); // populates moves hashmap with the appropriate moves for standard/kill plays

        System.out.println("THE VALID MOVES ARE: " + this.moves);

        if(moves.containsKey(getStringOfPosition(newFile, newRank)))//moves the piece if it is included in the moves hashmap
        {
            if(moves.get(getStringOfPosition(newFile, newRank)) != null) // movement is not null so we remove
            {
                Chess.returnPlay.piecesOnBoard.remove(Chess.getPieceFromPosition(getStringOfPosition(newFile, newRank)));
            }

            //actual movements
            this.pieceFile = newFile;
            this.pieceRank = newRank;
            //increment move count
            moveCount++;
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
        }
            
            
            
            
    }


    //TODO Helper method for new move
    public String getStringOfPosition(PieceFile file, int rank)
    {
        return "" + file + rank;
    }

    //----------------------------------------------------------------

    /*
     * Instance method that will
     *  1) populate valid moved in arraylist
     *  2) update the canMove boolean
     *  3) actually move
     */
    public void move(PieceFile newFile, int newRank) 
    {
        // check if the move is valid
        popoulateValidMoves();

        /*
         * If current piece can move,
            * then change the position of this piece,
            * and increment the moveCount integer to keep track of the count
         * 
         * else
            * change the static returnPlay object message enum to ILLEGAL MOVE
         * 
         * TODO implement the identification of kill pieces
         */
            if(validMoves.contains("" + newFile + newRank))
            {
                if(Chess.pieceExistsAt("" + newFile + newRank))
                {
                    Chess.returnPlay.piecesOnBoard.remove(Chess.getPieceFromPosition(("" + newFile + newRank)));
                    System.out.println("A PIECE WAS JUST KILLED!");
                }
                this.pieceFile = newFile;
                this.pieceRank = newRank;

                //add a method to check if we enpessanted
                removeIfEnPessantPlayed();

                moveCount++;      
                //A legal move return a null message
                Chess.returnPlay.message = null;
            }
            else
                Chess.returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;

            // TODO delete this print message
            System.out.println("VALID MOVES: " + validMoves + "\nMOVE COUNT: " + moveCount);
    }

    //HELPER METHODS FOR MOVE METHOD
    /*
     * Instance method that will add the valid moves based on the first move
     * Will also take care of the edge case of exceeding the board max size
     * 
     * TODO implement the enpessant
     */
    public void popoulateValidMoves()
    {
        //Clears arraylist from old valid moves
        validMoves.clear();

        if(Chess.whosPlaying == Chess.Player.white) // white is playing
        {
            if(moveCount == 0) // first white move
            {
                for(int i = 1; i <= 2; i++)
                    if((this.pieceRank + i) <= 8) // edge case -- making sure we are within the board
                        validMoves.add("" + this.pieceFile + (this.pieceRank + i));
            }
            else
                validMoves.add("" + this.pieceFile + (this.pieceRank + 1));
        }
        else
        {

            if(moveCount == 0) // first black move
            {
                for(int i = 1; i <= 2; i++)
                    if((this.pieceRank - i) > 0) // edge case -- making sure we are within the board
                        validMoves.add(this.pieceFile.toString() + (this.pieceRank - i));
            }      
            else
                validMoves.add(this.pieceFile.toString() + (this.pieceRank - 1));
        }
        

        //remove the moves that are not valid due to other pieces on the board
        //TODO issue finding possible kills because of this
        // for(int i = 0; i < Chess.returnPlay.piecesOnBoard.size(); i++)
        // {
        //     //This will get the positon of a piece on the board
        //     //Will cross reference the initial available moves & remove occupied spaces
        //     String otherPosition = Chess.returnPlay.piecesOnBoard.get(i).toString().split(":")[0];
        //     if(validMoves.contains(otherPosition))
        //         validMoves.remove(otherPosition);
        // }

        for(ReturnPiece piece : Chess.returnPlay.piecesOnBoard) // attempted refractor
            if(validMoves.contains(piece.toString().toUpperCase().contains(this.getPosition().toUpperCase())))
                validMoves.remove(piece.pieceFile.toString() + piece.pieceRank);

        //TODO test this method
        identifyPossibleKills();

        findEnpessant();

    }

    /*
     * Helper method to add any pieces that are within kill range
     * to get added to the validMoves arrayList
     */

     // TODO I never differentiated between black/white pieces while indeitfying kill pieces
     // TODO rewrite the if statement for > or < -- potential bug there
    public void identifyPossibleKills()
    {
        String pieceFileString = "" + this.pieceFile;
        int nextIndex = ReturnPiece.PieceFile.valueOf(pieceFileString).ordinal() + 1;
        int previousIndex = ReturnPiece.PieceFile.valueOf(pieceFileString).ordinal() - 1;
        
        /*
         * Will change based on black/white
         */
        int targetRank;

        if(Chess.whosPlaying == Chess.Player.white)
        {
            targetRank = this.pieceRank + 1;
            // nextIndex is a logical value
            // targetRank is a physical value

            if(nextIndex < 8 && nextIndex >= 0 && targetRank <= 8 && targetRank > 0)
            {
                if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank) && Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank).toString().toUpperCase().contains("B"))
                        validMoves.add("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank);
            }
            if(previousIndex < 8 && previousIndex >= 0 && targetRank <= 8 && targetRank > 0)
            {
                if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank) && Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank).toString().toUpperCase().contains("B"))
                        validMoves.add("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank);
            }

            // if(nextIndex < 8 && previousIndex >= 0 && targetRank <= 8 && targetRank > 0)
            // {
    
            //     if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank))
            //     {
            //         if(Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank).toString().toUpperCase().contains("B"))
            //             validMoves.add("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank);
            //     }

            //     if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank))
            //     {
            //         if(Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank).toString().toUpperCase().contains("B"))
            //             validMoves.add("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank);
            //     }
                    
            // }   
        }
        else
        {
            targetRank = this.pieceRank - 1;

            if(nextIndex < 8 && nextIndex >= 0 && targetRank <= 8 && targetRank > 0)
            {
                if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank) && Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank).toString().toUpperCase().contains("W"))
                        validMoves.add("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank);
            }
            if(previousIndex < 8 && previousIndex >= 0 && targetRank <= 8 && targetRank > 0)
            {
                if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank) && Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank).toString().toUpperCase().contains("W"))
                        validMoves.add("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank);
            }

            // if(previousIndex >= 0 && nextIndex < 8 && targetRank <= 8 && targetRank > 0)
            // {
            //     if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank))
            //     {
            //         if(Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank).toString().toUpperCase().contains("W"))
            //             validMoves.add("" + ReturnPiece.PieceFile.values()[nextIndex] + targetRank);
            //     }

            //     if(Chess.pieceExistsAt("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank))
            //     {
            //         if(Chess.getPieceFromPosition("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank).toString().toUpperCase().contains("W"))
            //             validMoves.add("" + ReturnPiece.PieceFile.values()[previousIndex] + targetRank);
            //     }
            // }
        }


        
        
    }

    /*
     * findEnpessant will 
     * 1) iterate through the entire returnPlay static object
     * 2) check for a pawn whose move count is equal to 1
     * 3) if the moved pawn is to the left/right of the current pawn
     * 4) append the pawn to the validMoves list
     * 5) check for which color is playing
     * 
     * instanceof checks for the dynamic type or one of its subclasses
     */
    public void findEnpessant()
    {

        // 1
        for(ReturnPiece piece : Chess.returnPlay.piecesOnBoard)
        {
            // 2
            if(piece instanceof Pawn)
            {
                Pawn toBeKilledPawn  = (Pawn) piece;
                // 3
                if(toBeKilledPawn.moveCount == 1 && Math.abs((toBeKilledPawn.pieceFile.ordinal() - this.pieceFile.ordinal())) == 1 && toBeKilledPawn.pieceRank == this.pieceRank)
                {
                    System.out.println("ENPESSANT POSSIBLE");
                    // 4 & 5
                    if(Chess.whosPlaying == Chess.Player.white && toBeKilledPawn.pieceType == ReturnPiece.PieceType.BP)
                        validMoves.add("" + toBeKilledPawn.pieceFile + (toBeKilledPawn.pieceRank + 1));
                    else if(Chess.whosPlaying == Chess.Player.black && toBeKilledPawn.pieceType == ReturnPiece.PieceType.WP)
                        validMoves.add("" + toBeKilledPawn.pieceFile + (toBeKilledPawn.pieceRank - 1));
                }
            }
        }

    }

    /*
     * Method will
     * 1) if the opponent pawn is right "behind" the current pawn
     * 2) remove the oppoennt pawn from the board
     */

     // TODO complete this method
    public void removeIfEnPessantPlayed()
    {
        if(Chess.whosPlaying == Chess.Player.white)
        {

        }
        else
        {

        }
            
    }


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


}