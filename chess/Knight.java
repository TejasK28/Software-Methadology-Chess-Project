package chess;
import java.util.*;

public class Knight extends Piece {

    /*
     * Knight constructor
     * 
     * Initializes the pieceType, PieceFile, and PieceRank
     */
    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank) 
    {
        this.pieceType = pieceType; // Wn or Bn
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.color = pieceType.toString().toUpperCase().substring(0, 1);
    }

    /*
     * populateRegularAndKillMoves
     * 
     * Populates the moves hashmap with the appropriate moves for standard/kill plays
     * 
     * @return moves hashmap
     */
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves() 
    {
        // TODO: populate the moves hashmap with the appropriate moves for standard/kill plays





        return null;
    }

    /*
     * These methods will only move in 2 spaces in absolute positions while bounds checking
     */
    public void check()
    {
        /*
         * We will check top left & right first
         * 
         * We will bounds check & add to moves accordingly
         */
         checkTopLeftAndRight();

         /*
          * Now we will check bottom left & right second
          */


        
         

    }

    public void checkTopLeftAndRight()
    {
        /*
         * currentPosition holds the String value of our position
         */
        String currentPosition = getPosition();
        /*
         * Checking rank + 2
         */
        int checkingRank = this.pieceRank + 2;
        int checkingLeftFileIndex = this.pieceFile.ordinal() - 1;
        int checkingRightFileIndex = this.pieceFile.ordinal() + 1;
        //if top left is valid
        if(checkingLeftFileIndex >= 0 && checkingRank <= 8)
        {
           //get the position of the checking position
           String checkingPosition = getStringOfPosition(PieceFile.values()[checkingLeftFileIndex], checkingRank);
           
           //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
           if(Chess.pieceExistsAt(checkingPosition) && isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }  
           else if(Chess.pieceExistsAt(checkingPosition) && !(isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               this.moves.put(checkingPosition, null);
           }

        }

        //if top right is valid
        if(checkingRightFileIndex < 8 && checkingRank <= 8)
        {
           //get the position of the checking position
           String checkingPosition = getStringOfPosition(PieceFile.values()[checkingRightFileIndex], checkingRank);
           
           //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
           if(Chess.pieceExistsAt(checkingPosition) && isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }   
           else if(Chess.pieceExistsAt(checkingPosition) && !(isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               this.moves.put(checkingPosition, null);
           }

        }
    }
    
}