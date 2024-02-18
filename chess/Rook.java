package chess;
import java.util.*;

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

        this.moves = new HashMap<String, ReturnPiece>();
        this.color = this.pieceType.toString().substring(0,1).toUpperCase();

    }
    
    /*
     * populateRegularMovesAndKillMoves
     * 
     * 1) will popoulate all the regular moves
     * 2) will remove all the occupied moves by the same color
     * 3) this will leave the popoulated moves hashmap for one move
     * 4) hashmap should get cleared regularly in the @move method
     */
    public HashMap<String, ReturnPiece> populateRegularAndKillMoves()
    {
        /*
         * Call 2 methods to popoulate the proper moves based on color and indeitify kill moves
         */
        populateVerticalMove(getPosition());
        populateHorizontalMoves(getPosition());

        System.out.println("THE MOVES OF ROOK : " + this.moves);

        return this.moves; // put in place to keep the compiler happy for now
    }

    /*
     * populateVerticalMoves method 
     * 
     * given a starting position
     * 
     * will
     * 1) keep iterating based on color vertically up and down
     * 
     * WILL MAKE SURE THE KILL MOVES ARE ONLY ADDED IF THE PIECE IS THE OPPOSITE COLOR
     */
    public void populateVerticalMove(String currentPosition)
    {
        if(this.color.equals(white)) // current rook is white
        {
            PieceFile column = this.pieceFile;
            int rank = this.pieceRank;
            /*
             * While loop to go through the vertical path from rank 1
             * if there is an enemy piece, then add the enemy
             * else add the location and null
             */
            while(rank <= 8)
            {
                String checkingPosition = Chess.getStringOfPosition(column, rank);

                if(checkingPosition.equals(getPosition()))
                {
                    ++rank;
                    continue;
                }
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break; // will break if we find an enemy picece
                }
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    break;
                }
                else
                {
                    if(!Chess.pieceExistsAt(checkingPosition))
                            this.moves.put(checkingPosition, null);
                }


                ++rank;
            }

            rank = this.pieceRank;

            while(rank >= 1)
            {
                String checkingPosition = Chess.getStringOfPosition(column, rank);
                
                if(checkingPosition.equals(getPosition()))
                {
                    --rank;
                    continue;
                }
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break; // will break if we find an enemy picece
                }
                else
                {
                    if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                    {
                        break;
                    }
                    else
                        if(!Chess.pieceExistsAt(checkingPosition))
                            this.moves.put(checkingPosition, null);
                }

                --rank;
            }
        }
        else // if the rook is black
        {
            PieceFile column = this.pieceFile;
            int rank = this.pieceRank;

            while(rank <= 8)
            {
                String checkingPosition = Chess.getStringOfPosition(column, rank);
                
                if(checkingPosition.equals(getPosition()))
                {
                    ++rank;
                    continue;
                }

                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break; // will break if we find an enemy picece
                }
                else
                {
                    if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                    {
                        break;
                    }
                    else
                        if(!Chess.pieceExistsAt(checkingPosition))
                            this.moves.put(checkingPosition, null);
                }

                ++rank;
            }

            rank = this.pieceRank;

            while(rank >= 1)
            {
                String checkingPosition = Chess.getStringOfPosition(column, rank);
                
                if(checkingPosition.equals(getPosition()))
                {
                    --rank;
                    continue;
                }
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break; // will break if we find an enemy picece
                }
                else
                {
                    if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                    {
                        break;
                    }
                    else
                        if(!Chess.pieceExistsAt(checkingPosition))
                            this.moves.put(checkingPosition, null);
                }

                --rank;
            }
        }
    }
    /*
     * populaHorizontalMoves method 
     * 
     * given a starting position
     * 
     * will
     * 1) keep iterating based on color horizontally left and right
     * 
     * TODO fix the bug where we dont see our own color
     */
    public void populateHorizontalMoves(String currentPosition)
    {
        if(this.color.equals(white)) // current rook is white
        {
            int column = this.pieceFile.ordinal() + 1;
            int rank = this.pieceRank;
            /*
             * While loop to go through the horizontal path from file 0 to 7
             * if there is an enemy piece, then add the enemy
             * else add the location and null
             */
            while(column < 8)
            {
                String checkingPosition = Chess.getStringOfPosition(PieceFile.values()[column], rank);
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                //Rook is white and we are checking for a white piece
                if(!Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    break;

                }
                else
                    this.moves.put(checkingPosition, null);


                ++column;
            }

            column = this.pieceFile.ordinal() - 1;

            while(column >= 0)
            {
                String checkingPosition = Chess.getStringOfPosition(PieceFile.values()[column], rank);
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    this.moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    break;
                }
                else
                    this.moves.put(checkingPosition, null);

                --column;
            }
        }
        else // if the rook is black
        {
            int column = this.pieceFile.ordinal() + 1;
            int rank = this.pieceRank;
            /*
             * While loop to go through the horizontal path from file 0 to 7
             * if there is an enemy piece, then add the enemy
             * else add the location and null
             */
            while(column < 8)
            {
                String checkingPosition = Chess.getStringOfPosition(PieceFile.values()[column], rank);
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    break;
                }
                else
                    moves.put(checkingPosition, null);


                ++column;
            }

            column = this.pieceFile.ordinal() - 1;

            while(column >= 0)
            {
                String checkingPosition = Chess.getStringOfPosition(PieceFile.values()[column], rank);
                
                //if we find an enemy piece along the current path
                //we will add that to the hashmap
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(white))
                {
                    moves.put(checkingPosition, Chess.getPieceFromPosition(checkingPosition));
                    break;
                }
                if(Chess.pieceExistsAt(checkingPosition) && Chess.getColorOfPieceFromPosition(checkingPosition).equals(black))
                {
                    break;
                }
                else
                    moves.put(checkingPosition, null);

                --column;
            }
        }
    }

}