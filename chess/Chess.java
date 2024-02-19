package chess;

import java.util.ArrayList;
import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import java.util.*;

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
	 * Declared an ENUM reference to keep track of the players
	 * 
	 * Default is white
	 */
	static Player whosPlaying = Player.white;

	/*
	 * Declared 4 Strings that will get assignned to the move
	 */
	static String [] strArr = null;
	static String move_from_column = null;
	static String move_from_row = null;
	static String move_to_column = null;
	static String move_to_row = null;

	//global move count specifically for enpessant currently
	public static int globalMoveCount = 0;
	public static ReturnPiece lastMovedPiece;
	
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
		
		//handles a wrong move with a message
		if(!pieceExistsAt(move_from_column + move_from_row))
		{
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return returnPlay;
		}
		else
			returnPlay.message = null;
		


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

		if(whosPlaying == Player.white) // white's turn
		{
			// if we are playing the wrong side
			if(getColorOfPieceFromPosition(move_from_column + move_from_row).equals("B")) 
			{
				System.out.println("ILLEGAL MOVE YOURE PLAYING FOR THE WRONG SIDE");
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE; // illegal move
				return returnPlay; // returning the returnPlay
			} 

			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);


			// check for check
			if(isKingInCheck("B"))
			{
				returnPlay.message = ReturnPlay.Message.CHECK;
			}

			// check for checkmate
			if(isKingInCheckmate("B"))
			{
				returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
			}
			
			// set the last moved piece
			lastMovedPiece = getPieceFromPosition(move_to_column + move_to_row);
			
			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE) // if the move was successful
			{
				switchPlayer(); //switch player & null the message
				++globalMoveCount; // increment the globalMoveCount
			}

			System.out.println("WHITE JUST MOVED");
			System.out.println("GLOBAL MOVE COUNT: " + globalMoveCount);
			System.out.println("BLACK TO MOVE");
		}
		else // Black's turn
		{	
			
			if(getColorOfPieceFromPosition(move_from_column + move_from_row).equals("W"))
			{
				System.out.println("ILLEGAL MOVE YOURE PLAYING FOR THE WRONG SIDE");
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return returnPlay;
			}

			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);
			
			// check for check
			if(isKingInCheck("W"))
			{
				returnPlay.message = ReturnPlay.Message.CHECK;
			}

			// check for checkmate
			if(isKingInCheckmate("W"))
			{
				returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			}
			
			// set the last moved piece
			lastMovedPiece = getPieceFromPosition(move_to_column + move_to_row);
			

			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE) // if the move was successful
			{
				switchPlayer(); //switch player & null the message
				++globalMoveCount; // increment the globalMoveCount
			}
			//TODO delete print 
			System.out.println("BLACK JUST MOVED");
			System.out.println("GLOBAL MOVE COUNT: " + globalMoveCount);
			System.out.println("WHITE TO MOVE");

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

	// SETUP METHODS

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

	public static void disectFromPosition(String move)
	{
		move_from_column  = String.valueOf(strArr[0].charAt(0));
		move_from_row  = String.valueOf(strArr[0].charAt(1));
		move_to_column  = String.valueOf(strArr[1].charAt(0));
		move_to_row  = String.valueOf(strArr[1].charAt(1));
	}


	// MOVEMENT METHODS
	/*
	 * Static method will get the from position & to position.
	 * It will identify the piece on the board and move accordingly.
	 */
	public static void movePieceFromTo(String move_from_column, String move_from_row, String move_to_column, String move_to_row)
	{
		ReturnPiece from_piece = getPieceFromPosition(move_from_column + move_from_row );
		 
		 if(from_piece instanceof Pawn)
		 {
			// TODO new move testing for pawn
			// TODO promotion
			Pawn current_pawn = ((Pawn)from_piece);
			current_pawn.move(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));

			//Promotion implementation
			//default promotion to queen
			// TODO need to accept a choice for promotion
			if(current_pawn.pieceRank == 8 && current_pawn.color.equals("W"))
			{
				//adds a white queen in the place of the old pawn and removes old pawn
				returnPlay.piecesOnBoard.add(new Queen(PieceType.WQ,current_pawn.pieceFile, current_pawn.pieceRank));
				returnPlay.piecesOnBoard.remove(current_pawn);
			}
			else if(((Pawn)from_piece).pieceRank == 1 && ((Pawn)from_piece).color.equals("B"))
			{
				returnPlay.piecesOnBoard.add(new Queen(PieceType.BQ,current_pawn.pieceFile, current_pawn.pieceRank));
				returnPlay.piecesOnBoard.remove(current_pawn);
			}
		 }
		 
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

	// LOGISTIC METHODS
	/*
	 * Method that will switch the side of who is playing at the moment
	 */
	public static void switchPlayer()
	{
		if(whosPlaying == Player.black)
			whosPlaying = Player.white;
		else
			whosPlaying = Player.black;
		
		returnPlay.message = null; // reset the message when we switch
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

	// TODO test this method
	public static String getColorOfPieceFromPosition(String position)
	{
		ReturnPiece returnPiece = getPieceFromPosition(position);
		return "" + returnPiece.toString().split(":")[1].substring(0,1).toUpperCase();
	}

	// SPECIAL END CASE METHODS
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

	public static String getStringOfPosition(PieceFile file, int rank)
	{
		return "" + file + rank;
	}

	// check if the king is in check
	public static boolean isKingInCheck(String color)
	{
		// get the king
		King king = null;
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece instanceof King && piece.toString().split(":")[1].substring(0,1).equals(color))
			{
				king = (King) piece;
				break;
			}
		}

		// check if the king is in check
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece.toString().split(":")[1].substring(0,1).equals(color)){
				// print passing
				//System.out.println("PASSING");
				continue;
			}

			// cast the piece to the appropriate piece
			Piece casted_piece = (Piece) piece;
			// print the type of piece and the piece itself
			//System.out.println("THE PIECE IS: " + casted_piece.toString().split(":")[1].substring(0,1));

			// check if the piece can move to the king's position
			if (casted_piece.isValidMove(king.pieceFile, king.pieceRank))
			{
				System.out.println("THE KING IS IN CHECK");
				return true;
			}
		}

		return false;
	}

	// TODO: Refactor Class design and come back to this method
	// check if the king is in checkmate
	public static boolean isKingInCheckmate(String color)
	{
		// get the king
		ReturnPiece king = null;
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece instanceof King && piece.toString().split(":")[1].substring(0,1).equals(color))
			{
				king = piece;
				break;
			}
		}

		// check if the king is in checkmate
		// looping through same color pieces and simulate all possible moves
		// if the king is still in check after all possible moves, then it is checkmate
		// if the king is not in check after all possible moves, then it is not checkmate
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece.toString().split(":")[1].substring(0,1).equals(color))
			{
				// cast the piece to the appropriate piece
				Piece casted_piece = (Piece) piece;
				// print the type of piece and the piece itself
				//System.out.println("THE PIECE IS: " + casted_piece.toString().split(":")[1].substring(0,1));
				// simulate all possible moves 
				for (HashMap.Entry<String, ReturnPiece> move : casted_piece.populateRegularAndKillMoves().entrySet()) {
					// print the move
					System.out.println("THE MOVE IS: " + move.getKey());

					// store casted_piece's position
					PieceFile originalFile = casted_piece.pieceFile;
					int originalRank = casted_piece.pieceRank;

					System.out.println("THE MOVE FILE IS: " + move.getKey().split(":")[0].substring(0,1));
					// convert above to PieceFile
					PieceFile newFile = PieceFile.valueOf(move.getKey().split(":")[0].substring(0,1));
					// convert above to int
					int newRank = Integer.parseInt(move.getKey().split(":")[0].substring(1,2));
					// print the move rank
					System.out.println("THE MOVE RANK IS: " + move.getKey().split(":")[0].substring(1,2));

					// simulate the move
					casted_piece.pieceFile = newFile;
					casted_piece.pieceRank = newRank;
					// check if the king is still in check
					if(!isKingInCheck(color))
					{
						// if the king is not in check, then it is not checkmate
						// move the piece back
						casted_piece.pieceFile = originalFile;
						casted_piece.pieceRank = originalRank;

						// print not checkmate
						System.out.println("THE KING IS NOT IN CHECKMATE");
						return false;
					}
					// move the piece back
					casted_piece.pieceFile = originalFile;
					casted_piece.pieceRank = originalRank;
				}

		}
		// print checkmate
		System.out.println("THE KING IS IN CHECKMATE");
		return true;
			
	}
		return false;
	}
}


