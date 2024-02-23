package chess;
import java.util.HashMap;

public class Rook extends Piece{
    /*
     * Rook Constructor
     * 
     * intitalizes inherited fields:
     * @pieceType
     * @pieceFile
     * @pieceRank
     * 
     * Will initialize other variables as necessary
     */
    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank)
    {
        this.pieceType = pieceType; // Wr or Br
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;

        moves = new HashMap<String, ReturnPiece>();
        this.color = pieceType.toString().toUpperCase().substring(0, 1);
    }
    

    /*
     * populateRegularMovesAndKillMoves
     * 
     * 1) will popoulate all the regular moves
     * 2) will remove all the occupied moves by the same color
     * 3) this will leave the popoulated moves hashmap for one move
     * 4) hashmap should get cleared regularly in the @move method
     */
    @Override
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves()
    {
        /*
         * Call 2 methods to popoulate the proper moves based on color and indeitify kill moves
         */

        moves.clear();


        horizontal(this.getPosition());
        vertical(this.getPosition());



        return moves;
    }

    public void horizontal(String currentPosition)
    {
       //curr position right
       for(int i = 1; i <= 10; i++)
       {
           String checkingPosition = Chess.getStringOfPositionWithChange(currentPosition, i, 0);

           if(checkingPosition != null)
           {   
               boolean weFoundEnemy = Chess.isEnemyForThisPiece(currentPosition, checkingPosition);

               if(weFoundEnemy) // an enemy is on this piece so we will add it and stop checking
               {
                   moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                   break;
               }
               else if(!Chess.pieceExistsAt(checkingPosition))// enemy is not on this piece
                   moves.put(checkingPosition, null);
           }
           else // null space
               break;
       }

       //curr left
       for(int i = 1; i <= 10; i++)
       {
           String checkingPosition = Chess.getStringOfPositionWithChange(currentPosition, -i, 0);

           if(checkingPosition != null)
           {   
               boolean weFoundEnemy = Chess.isEnemyForThisPiece(currentPosition, checkingPosition);

               if(weFoundEnemy) // an enemy is on this piece so we will add it and stop checking
               {
                   moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                   break;
               }
               else if(!Chess.pieceExistsAt(checkingPosition))// enemy is not on this piece
                   moves.put(checkingPosition, null);
           }
           else // null space
               break;
       }
   }

    public void vertical(String currentPosition)
    {
        //curr position up
        for(int i = 1; i <= 10; i++)
        {
            String checkingPosition = Chess.getStringOfPositionWithChange(currentPosition, 0, i);

            if(checkingPosition != null)
            {   
                boolean weFoundEnemy = Chess.isEnemyForThisPiece(currentPosition, checkingPosition);

                if(weFoundEnemy) // an enemy is on this piece so we will add it and stop checking
                {
                    moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                else if(!Chess.pieceExistsAt(checkingPosition))// enemy is not on this piece
                    moves.put(checkingPosition, null);
            }
            else // null space
                break;
        }

        //curr position down
        for(int i = 1; i <= 10; i++)
        {
            String checkingPosition = Chess.getStringOfPositionWithChange(currentPosition, 0, -i);

            if(checkingPosition != null)
            {   
                boolean weFoundEnemy = Chess.isEnemyForThisPiece(currentPosition, checkingPosition);

                if(weFoundEnemy) // an enemy is on this piece so we will add it and stop checking
                {
                    moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                else if(!Chess.pieceExistsAt(checkingPosition))// enemy is not on this piece
                    moves.put(checkingPosition, null);
            }
            else // null space
                break;
        }
    }

}