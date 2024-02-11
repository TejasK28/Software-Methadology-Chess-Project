package chess;

import java.util.ArrayList;
import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

/*
 * ReturnPiece clas
 * 
 * DO NOT EDIT
 */
class ReturnPiece
{
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

/*
 * ReturnPlay class
 * 
 * DO NOT EDIT
 */
class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

/*
 * Chess Class
 * 
 * DONT EDIT THE EXISTING FIELDS!
 */
public class Chess {
	
	enum Player { white, black }

	/*
	 * Created a ReturnPlay reference
	 */
	public static ReturnPlay returnPlay;

	/*
	 * Created an ENUM reference to keep track of the players
	 * 
	 * Default is white
	 */
	static Player whosPlaying = Player.white;

	/*
	 * Created 4 Strings that will get assignned to the move
	 */
	static String [] strArr = null;
	static String move_from_column = null;
	static String move_from_row = null;
	static String move_to_column = null;
	static String move_to_row = null;
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */

		// TODO handle when the user enters an incorrect move where we dont start from a piece
		 
		// TODO can be promotion as well

		/*
		 * Disecting the individal positions from and to
		 */
		strArr = move.split(" ");

		if(strArr.length >= 2)
			disectFromPosition(move);
		


		/*
		 * If statement that identifies a resign statement from the user
		 * 
		 * Will return a ReturnPlay object accordingly with the appropriate message
		 */
		if(resignPrompted() != null)
			return returnPlay;
			
	 
		/*
		 * This code will allow any piece on the board to move anywhere without any rules
		 * 
		 * TODO implement other rules in the move methods themselves
		 * 
		 * Remember that we will update the message of the ReturnPlay via the clases of the pieces themselves
		 */

		if(whosPlaying == Player.white)
		 {
			//TODO delete print 
			System.out.println("\nWHITE'S TURN");

			if(getPieceFromPosition("" + move_from_column + move_from_row).toString().split(":")[1].toUpperCase().contains("B"))
			{
				System.out.println("ILLEGAL MOVE YOURE PLAYING FOR THE WRONG SIDE");
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return returnPlay;
			}

			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);
			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE)
				switchPlayer();
		 }
		else
		{	
			//TODO delete print 
			System.out.println("\nBLACK'S TURN");

			if(getPieceFromPosition("" + move_from_column + move_from_row).toString().split(":")[1].toUpperCase().contains("W"))
			{
				System.out.println("ILLEGAL MOVE YOURE PLAYING FOR THE WRONG SIDE");
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return returnPlay;
			}
			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);
			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE)
				switchPlayer();
		 }


		 /*
		  * This is the if statement to test a draw 
		  * This is here because a draw is performed after the move is executed unlike resign
		  */
		if(drawPrompted() != null)
			return returnPlay;
		

		return returnPlay;

	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		
		/*
		 * This method will reset the game/start fresh in the start
		 * 
		 * This method manipulates the ReturnPlay returnPlay reference field @
		 * the the top of this class
		 */
		setupBoard();


	}


	/*
	 * Static method that is called in the chess.start() method
	 * 
	 * Will clear the board and put all pieces back to their 
	 * original positions
	 */

	 public static void setupBoard()
	 {
		/*
		 * ReturnPlay object is initialized
		 * 
		 * piecesOnBoard is initialized with an ArrayList
		 * Message will show null
		 */
		returnPlay = new ReturnPlay();
		returnPlay.piecesOnBoard = new ArrayList<ReturnPiece>();
		returnPlay.message = null;

		//Pawn testing
		//This should add all the white/black pawns on the board
		for(int i = 0; i < 8; i++)
		{
			returnPlay.piecesOnBoard.add(new Pawn(PieceType.WP, PieceFile.values()[i], 2));
			returnPlay.piecesOnBoard.add(new Pawn(PieceType.BP, PieceFile.values()[i], 7));
		}
		//Rook Testing
		//Should add all white/black rooks on board
		int [] rook_position = {0, 7};
		for(int i = 0; i < 4; i++)
			if(i < 2)
				returnPlay.piecesOnBoard.add(new Rook(PieceType.WR, PieceFile.values()[rook_position[i % 2]], 1));
			else
				returnPlay.piecesOnBoard.add(new Rook(PieceType.BR, PieceFile.values()[rook_position[i % 2]], 8));
 
		//Knight testing
		//Should add all white/black knights on board
		int [] knight_position = {1, 6};
		for(int i = 0; i < 4; i++)
			if(i < 2)
				returnPlay.piecesOnBoard.add(new Knight(PieceType.WN, PieceFile.values()[knight_position[i % 2]], 1));
			else
				returnPlay.piecesOnBoard.add(new Knight(PieceType.BN, PieceFile.values()[knight_position[i % 2]], 8));

		//Bishop testing
		//Should add all white/black bishops on board
		int [] bishop_position = {2, 5};
		for(int i = 0; i < 4; i++)
			if(i < 2)
				returnPlay.piecesOnBoard.add(new Bishop(PieceType.WB, PieceFile.values()[bishop_position[i % 2]], 1));
			else
				returnPlay.piecesOnBoard.add(new Bishop(PieceType.BB, PieceFile.values()[bishop_position[i % 2]], 8));
		
		//Queen Testing
		//Should add black/white queens on board
		for(int i = 0; i < 2; i++)
			if(i < 1)
				returnPlay.piecesOnBoard.add(new Queen(PieceType.WQ, PieceFile.values()[3], 1));
			else
				returnPlay.piecesOnBoard.add(new Queen(PieceType.BQ, PieceFile.values()[3], 8));
		
		//King Testing
		//Should add black/white kings on board
		for(int i = 0; i < 2; i++)
			if(i < 1)
				returnPlay.piecesOnBoard.add(new King(PieceType.WK, PieceFile.values()[4], 1));
			else
				returnPlay.piecesOnBoard.add(new King(PieceType.BK, PieceFile.values()[4], 8));
		
		/*
		 * We are returning a board with the pieces in their original position
		 */		
	}

	/*
	 * Static method to be used to check whic piece is in some position of the board
	 */
	public static ReturnPiece getPieceFromPosition(String position)
	{
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			Piece interface_piece = (Piece) piece;

			if(interface_piece.getPosition().equals(position))
				return piece;
		}
		/*
		 * Included to make the compiler happy
		 * We already assume we got the correct position
		 */
		return null;
	}

	/*
	 * Static method will get the from position & to position.
	 * It will identify the piece on the board and move accordingly.
	 */
	public static void movePieceFromTo(String move_from_column, String move_from_row, String move_to_column, String move_to_row)
	{
		ReturnPiece from_piece = getPieceFromPosition(move_from_column + move_from_row );
		 
		 if(from_piece instanceof Pawn)
			((Pawn)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));
		else if(from_piece instanceof Rook)
			((Rook)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));
		else if(from_piece instanceof Knight)
			((Knight)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));
		else if(from_piece instanceof Bishop)
			((Bishop)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));
		else if(from_piece instanceof Queen)
			((Queen)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));
		else if(from_piece instanceof King)
			((King)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));

		
	}

	/*
	 * Method that will switch the side of who is playing at the moment
	 */
	public static void switchPlayer()
	{
		if(whosPlaying == Player.black)
			whosPlaying = Player.white;
		else
			whosPlaying = Player.black;
	}

	public static boolean pieceExistsAt(String targetPosition)
	{
		for(int i = 0; i < returnPlay.piecesOnBoard.size(); i++)
		{
			ReturnPiece piece = returnPlay.piecesOnBoard.get(i);
			String positionOfPiece = piece.toString().split(":")[0];
			
			if(positionOfPiece.equals(targetPosition))
				return true;
		}

		return false;
	}


	public static void disectFromPosition(String move)
	{
		move_from_column  = String.valueOf(strArr[0].charAt(0));
		move_from_row  = String.valueOf(strArr[0].charAt(1));
		move_to_column  = String.valueOf(strArr[1].charAt(0));
		move_to_row  = String.valueOf(strArr[1].charAt(1));
	}

	public static ReturnPlay resignPrompted()
	{
		if(strArr[0].equals("resign"))
		{
			// TODO delete print statement
			System.out.println(whosPlaying.toString().toUpperCase() + " IS RSIGNING");
			if(whosPlaying == Player.white)
				returnPlay.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			else
				returnPlay.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			return returnPlay;
		}

		return null;
	}

	public static ReturnPlay drawPrompted()
	{
		if(strArr.length >= 3)
		{
			if(strArr[2].equals("draw?"))
			{
				System.out.println(whosPlaying.toString().toUpperCase() + " WANTS A DRAW");
				returnPlay.message = ReturnPlay.Message.DRAW;
				return returnPlay;
			}
		}

		return null;
	}
}