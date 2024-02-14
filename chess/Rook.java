package chess;
import java.util.*;

public class Rook extends ReturnPiece implements Piece{

    //HashMap validMoves holds all the valid moves in the form : Key: String -> Value: ReturnPiece
    Map<String, ReturnPiece> moves;
    //String color that holds the first letter of the color in all caps
    String color;
    String white;
    String black;

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
        this.white = "W";
        this.black = "B";
    }

    /*
     * Move Method
     *
     * 
     * 1) calls popoulateMoves -- must clear beforehand
     * 2) will move 
     */
    public void move(PieceFile newFile, int newRank) 
    {
        //clearing the moves hashmap
        moves.clear();

        //calls popoulateMoves to ensure the hashmap contains all the valid moves based on color
        populateRegularMovesAndKillMoves();
    } 
    
    /*
     * populateRegularMovesAndKillMoves
     * 
     * 1) will popoulate all the regular moves
     * 2) will remove all the occupied moves by the same color
     * 3) this will leave the popoulated moves hashmap for one move
     * 4) hashmap should get cleared regularly in the @move method
     */
    public HashMap<String, ReturnPiece> populateRegularMovesAndKillMoves()
    {
        /*
         * Starting off by checking the white rook first
         */
        if(this.color.equals(white))
        {
            
        }
        else //If this rook is black
        {

        }


        return null; // put in place to keep the compiler happy for now
    }

    /*
     * Returns the current position as a String
     */
    @Override
    public String getPosition() 
    {
        return "" + this.pieceFile + this.pieceRank;
    }
}