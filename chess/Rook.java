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
        /*
         * @ current_file_index : int
         *  - stores the changing index of the columns(row-way traverse)
         */
        int current_file_index = this.pieceFile.ordinal() + 1;
        

        /*
         * @ current_rank : int
         *  - stores the current rank
         */
        int current_rank = this.pieceRank;

        
        /*
         * This while loop checks from the current file & rank to the right until the end
         */
        while(current_file_index < 8)
        {
            /*
             * @ current_piecefile : PieceFile
             *  - stores the pieceFile equivalent of the current file index
             */
            PieceFile current_piecefile = PieceFile.values()[current_file_index];

            /*
            * @ checking_index : String
            * - stores the string version of the checking position
            */
            String checkingIndex = getStringOfPosition(current_piecefile, current_rank);

            String currentindex = getPosition();

            //we are going to default to the same rank, but change the file
            
            //we now are safe from messing with our own rook

            //if the position to the right is an enemy, we can add the piece to a hashmap and break; 
            if(isEnemyForThisPiece(currentPosition, checkingIndex))
            {
                //adding the index we can move to
                moves.put(checkingIndex, Chess.getPieceFromPosition(checkingIndex));
                break;

            }
            else if(!isEnemyForThisPiece(currentPosition, checkingIndex)) // if the position we are checking is our own color
            {
                break;
            }
            else // free space
            {
                moves.put(currentindex, null);
            }

            ++current_file_index;
        }
        
        current_file_index = this.pieceFile.ordinal() - 1;

        /*
         * this while loop goes in the left direction
         */
        while(current_file_index >= 0)
        {
            /*
             * @ current_piecefile : PieceFile
             *  - stores the pieceFile equivalent of the current file index
             */
            PieceFile current_piecefile = PieceFile.values()[current_file_index];

            /*
            * @ checking_index : String
            * - stores the string version of the checking position
            */
            String checkingIndex = getStringOfPosition(current_piecefile, current_rank);

            String currentindex = getPosition();

            //we are going to default to the same rank, but change the file
            
            //we now are safe from messing with our own rook

            //if the position to the right is an enemy, we can add the piece to a hashmap and break; 
            if(isEnemyForThisPiece(currentPosition, checkingIndex))
            {
                //adding the index we can move to
                moves.put(checkingIndex, Chess.getPieceFromPosition(checkingIndex));
                break;

            }
            else if(!isEnemyForThisPiece(currentPosition, checkingIndex)) // if the position we are checking is our own color
            {
                break;
            }
            else // free space
            {
                moves.put(currentindex, null);
            }

            --current_file_index;
        }
        
    }

    public void vertical(String currentPosition)
    {
        /*
         * @ current_file_index : int
         *  - stores the changing index of the columns(row-way traverse)
         */
        int current_file_index = this.pieceFile.ordinal();
        

        /*
         * @ current_rank : int
         *  - stores the current rank
         */
        int current_rank = this.pieceRank + 1;

        
        /*
         * This while loop checks from the current file & rank up until the end
         */
        while(current_file_index <= 8)
        {
            /*
             * @ current_piecefile : PieceFile
             *  - stores the pieceFile equivalent of the current file index
             */
            PieceFile current_piecefile = PieceFile.values()[current_file_index];

            /*
            * @ checking_index : String
            * - stores the string version of the checking position
            */
            String checkingIndex = getStringOfPosition(current_piecefile, current_rank);

            String currentindex = getPosition();

            //we are going to default to the same rank, but change the file
            
            //we now are safe from messing with our own rook

            //if the position to the right is an enemy, we can add the piece to a hashmap and break; 
            if(isEnemyForThisPiece(currentPosition, checkingIndex))
            {
                //adding the index we can move to
                moves.put(checkingIndex, Chess.getPieceFromPosition(checkingIndex));
                break;

            }
            else if(!isEnemyForThisPiece(currentPosition, checkingIndex)) // if the position we are checking is our own color
            {
                break;
            }
            else // free space
            {
                moves.put(currentindex, null);
            }

            ++current_rank;
        }
        
        current_rank = this.pieceRank - 1;
        /*
         * this while loop goes in the down direction
         */
        while(current_file_index > 0)
        {
            /*
             * @ current_piecefile : PieceFile
             *  - stores the pieceFile equivalent of the current file index
             */
            PieceFile current_piecefile = PieceFile.values()[current_file_index];

            /*
            * @ checking_index : String
            * - stores the string version of the checking position
            */
            String checkingIndex = getStringOfPosition(current_piecefile, current_rank);

            String currentindex = getPosition();

            //we are going to default to the same rank, but change the file
            
            //we now are safe from messing with our own rook

            //if the position to the right is an enemy, we can add the piece to a hashmap and break; 
            if(isEnemyForThisPiece(currentPosition, checkingIndex))
            {
                //adding the index we can move to
                moves.put(checkingIndex, Chess.getPieceFromPosition(checkingIndex));
                break;

            }
            else if(!isEnemyForThisPiece(currentPosition, checkingIndex)) // if the position we are checking is our own color
            {
                break;
            }
            else // free space
            {
                moves.put(currentindex, null);
            }

            --current_rank;;
        }
        
    }

}