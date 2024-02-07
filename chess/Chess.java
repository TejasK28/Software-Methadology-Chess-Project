package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

class ReturnPiece {
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

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }

	/*
	 * Created a ReturnPlay reference
	 */
	static ReturnPlay returnPlay;
	
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
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */

		/*
		 * Currently testing a design pattern via implemeted classes below
		 */

		/*
		 * Disecting the individal positions from and to
		 */
		String [] strArr = move.split(" ");
		String move_from_column  = String.valueOf(strArr[0].charAt(0));
		String move_from_row  = String.valueOf(strArr[0].charAt(1));
		String move_to_column  = String.valueOf(strArr[1].charAt(0));
		String move_to_row  = String.valueOf(strArr[1].charAt(1));
	 
		/*
		 * Testing a simple pawn move: e2 e4
		 */

		 ReturnPiece from_piece = getPieceFromPosition(move_from_column + move_from_row );

		 System.out.println("IDENTIFIED PIECE ON : " + move_from_column + move_from_row);
		 
		 if(from_piece instanceof Pawn)
			((Pawn)from_piece).move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));


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
		
		//Kink Testing
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

}


