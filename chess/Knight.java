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

        moves = new HashMap<String, ReturnPiece>();
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
        /*
         * Ensures out map is cleared everytime before a move
         */
        moves.clear();
        /*
         * We will check top left & right first
         * 
         * We will bounds check & add to moves accordingly
         */
        checkTopLeftAndRight();

        /*
         * Now we will check bottom left & right second
         */
         checkBottomLeftAndRight();

         /*
          * Now we will check right up and down
          */
         checkRightUpAndDown();


         /*
          * Now we will check left up and down
          */
           checkLeftUpAndDown();


           System.out.println(this.pieceType + " AT " + this.getPosition() + " KNIGHT MOVES: " + moves);
          
        return moves;
    }


    /*
     * This method will check the top left and right move for knight depending on the color of said knight
     */
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
           if(Chess.pieceExistsAt(checkingPosition) && Chess.isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }  
           else if(Chess.pieceExistsAt(checkingPosition) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               moves.put(checkingPosition, null);
           }

        }

        //if top right is valid
        if(checkingRightFileIndex < 8 && checkingRank <= 8)
        {
           //get the position of the checking position
           String checkingPosition = getStringOfPosition(PieceFile.values()[checkingRightFileIndex], checkingRank);
           
           //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
           if(Chess.pieceExistsAt(checkingPosition) && Chess.isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }   
           else if(Chess.pieceExistsAt(checkingPosition) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               moves.put(checkingPosition, null);
           }

        }
    }
    
    /*
     * This method will check the bottom left and right pieces for the knight depending on the color of said knight
     */
    public void checkBottomLeftAndRight()
    {
        /*
         * currentPosition holds the String value of our position
         */
        String currentPosition = getPosition();
        /*
         * Checking rank - 2
         */
        int checkingRank = this.pieceRank - 2;
        int checkingLeftFileIndex = this.pieceFile.ordinal() - 1;
        int checkingRightFileIndex = this.pieceFile.ordinal() + 1;
        //if top left is valid
        if(checkingLeftFileIndex >= 0 && checkingRank >= 1)
        {
           //get the position of the checking position
           String checkingPosition = getStringOfPosition(PieceFile.values()[checkingLeftFileIndex], checkingRank);
           
           //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
           if(Chess.pieceExistsAt(checkingPosition) && Chess.isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }  
           else if(Chess.pieceExistsAt(checkingPosition) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               moves.put(checkingPosition, null);
           }

        }

        //if top right is valid
        if(checkingRightFileIndex < 8 && checkingRank >= 1)
        {
           //get the position of the checking position
           String checkingPosition = getStringOfPosition(PieceFile.values()[checkingRightFileIndex], checkingRank);
           
           //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
           if(Chess.pieceExistsAt(checkingPosition) && Chess.isEnemyForThisPiece(currentPosition, checkingPosition))
           {
               moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
           }   
           else if(Chess.pieceExistsAt(checkingPosition) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPosition))) 
           { // else there is a piece but it's the same color as ours
               //we don't do anything as that space is invalid to move to or take
           }
           else // in all other case (free space)
           {
               moves.put(checkingPosition, null);
           }

        }
    }
    
    /*
     * This method will check the right up and down positions of the knight
     */

     public void checkRightUpAndDown()
     {
         /*
          * currentPosition holds the String value of our position
          */
         String currentPosition = getPosition();
         /*
          * Checking rank +- 1
          */
         int checkingRankUp = this.pieceRank + 1;
         int checkingRankDown = this.pieceRank - 1;

         /*
          * Checks file + 2 to the right
          */
         int checkingRightFileIndex = this.pieceFile.ordinal() + 2;
        
         //if right up  is valid
         if(checkingRightFileIndex < 8 && checkingRankUp <= 8)
         {
            //get the position of the checking position for UP AND DOWN
            String checkingPositionUp = getStringOfPosition(PieceFile.values()[checkingRightFileIndex], checkingRankUp);
        
            //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
            if(Chess.pieceExistsAt(checkingPositionUp) && Chess.isEnemyForThisPiece(currentPosition, checkingPositionUp))
            {
                moves.put(checkingPositionUp, Chess.getPieceFromPosition(checkingPositionUp));
            }  
            else if(Chess.pieceExistsAt(checkingPositionUp) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPositionUp))) 
            { // else there is a piece but it's the same color as ours
                //we don't do anything as that space is invalid to move to or take
            }
            else // in all other case (free space)
            {
                moves.put(checkingPositionUp, null);
            }
 
         }

         //if statement ot check left down
         if(checkingRightFileIndex < 8 && checkingRankDown >= 1)
         {
            String checkingPositionDown = getStringOfPosition(PieceFile.values()[checkingRightFileIndex], checkingRankDown);

            //this if statement check for the down
            if(Chess.pieceExistsAt(checkingPositionDown) && Chess.isEnemyForThisPiece(currentPosition, checkingPositionDown))
            {
                moves.put(checkingPositionDown, Chess.getPieceFromPosition(checkingPositionDown));
            }  
            else if(Chess.pieceExistsAt(checkingPositionDown) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPositionDown))) 
            { // else there is a piece but it's the same color as ours
                //we don't do anything as that space is invalid to move to or take
            }
            else // in all other case (free space)
            {
                moves.put(checkingPositionDown, null);
}
         }
    
     }
     
     public void checkLeftUpAndDown()
     {
         /*
          * currentPosition holds the String value of our position
          */
         String currentPosition = getPosition();
         /*
          * Checking rank +- 1
          */
         int checkingRankUp = this.pieceRank + 1;
         int checkingRankDown = this.pieceRank - 1;

         /*
          * Checks file + 2 to the right
          */
         int checkingLeftFileIndex = this.pieceFile.ordinal() - 2;
        
         //if right up and down is valid
         if(checkingLeftFileIndex >= 0 && checkingRankUp <= 8)
         {
            //get the position of the checking position for UP AND DOWN
            String checkingPositionUp = getStringOfPosition(PieceFile.values()[checkingLeftFileIndex], checkingRankUp);

            //if that piece is occupied by the enemy, we will add that piece to the moves hashmap
            if(Chess.pieceExistsAt(checkingPositionUp) && Chess.isEnemyForThisPiece(currentPosition, checkingPositionUp))
            {
                moves.put(checkingPositionUp, Chess.getPieceFromPosition(checkingPositionUp));
            }  
            else if(Chess.pieceExistsAt(checkingPositionUp) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPositionUp))) 
            { // else there is a piece but it's the same color as ours
                //we don't do anything as that space is invalid to move to or take
            }
            else // in all other case (free space)
            {
                moves.put(checkingPositionUp, null);
            }
         }

         //if statement ot check left down
         if(checkingLeftFileIndex >= 0 && checkingRankDown >= 1)
         {
            String checkingPositionDown = getStringOfPosition(PieceFile.values()[checkingLeftFileIndex], checkingRankDown);

            //this if statement check for the down
            if(Chess.pieceExistsAt(checkingPositionDown) && Chess.isEnemyForThisPiece(currentPosition, checkingPositionDown))
            {
                moves.put(checkingPositionDown, Chess.getPieceFromPosition(checkingPositionDown));
            }  
            else if(Chess.pieceExistsAt(checkingPositionDown) && !(Chess.isEnemyForThisPiece(currentPosition, checkingPositionDown))) 
            { // else there is a piece but it's the same color as ours
                //we don't do anything as that space is invalid to move to or take
            }
            else // in all other case (free space)
            {
                moves.put(checkingPositionDown, null);
}
         }
    
    }


}