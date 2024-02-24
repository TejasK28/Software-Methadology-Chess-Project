package chess;

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
	public static String [] strArr = null;
	static String move_from_column = null;
	static String move_from_row = null;
	static String move_to_column = null;
	static String move_to_row = null;

	/*
	 * predefined constants
	 * for player color
	 */

	 public static final String white = "W";
	 public static final String black = "B";

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
	 * 
	 * DON't DELETE BUT YOU CAN EDIT
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */

		// TODO handle when the user enters an incorrect move where we dont start from a piece
		 
		// TODO can be promotion as well

		/*
		 * Disecting the individal positions from and to
		 */
		strArr = move.split(" ");

		/*
		 * If we have more than 2 moves
		 * we will first get the first 2 moves
		 */
		if(strArr.length >= 2)
			disectFromPosition(move);
		else if(strArr.length  == 1) // possible resign
			if(resignPrompted() != null)
				return returnPlay;
		
		//handles a wrong move with a message
		//ensures the input space and output spaces are valid or else we will return a illegal move error
		//TODO add a method to make sure the position strings are valid
		if(!pieceExistsAt(move_from_column + move_from_row))
		{
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return returnPlay;
		}
		else
			returnPlay.message = null;

		/*
		 * This code will allow any piece on the board to move anywhere without any rules
		 * 
		 * TODO implement other rules in the move methods themselves
		 * 
		 * Remember that we will update the message of the ReturnPlay via the clases of the pieces themselves
		 */

		 //whosPlaying = Player.white;//TODO DELETE THIS

		if(whosPlaying == Player.white) // white's turn
		{
			// if we are playing the wrong side
			if(getColorOfPieceFromPosition(move_from_column + move_from_row).equals(black)) 
			{
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE; // illegal move
				return returnPlay; // returning the returnPlay
			} 

			// check if white is in check. If so, this move must undo the check
			if(kingIsInCheck(white))
			{
				// if the move does not undo the check, then it is an illegal move
				Piece from_piece = (Piece) getPieceFromPosition(move_from_column + move_from_row);
				PieceFile originalFile = from_piece.pieceFile;
				int originalRank = from_piece.pieceRank;

				// simulate the move
				from_piece.setPosition(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));

				if(kingIsInCheck(black))
				{	
					// move the piece back
					from_piece.setPosition(originalFile, originalRank);
					returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
					return returnPlay;
				}
			}

			// move the piece
			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);

			// check for check
			if(kingIsInCheck(black))
			{
				returnPlay.message = ReturnPlay.Message.CHECK;
				// check for checkmate
				if(isKingInCheckmate(black))
				{
					returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
				}
				return returnPlay;
			}

			// set the last moved piece
			lastMovedPiece = getPieceFromPosition(move_to_column + move_to_row);
			
			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE) // if the move was successful
			{
				switchPlayer(); //switch player & null the message
				++globalMoveCount; // increment the globalMoveCount
			}
		}
		else // Black's turn
		{	
			
			if(getColorOfPieceFromPosition(move_from_column + move_from_row).equals(white))
			{
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return returnPlay;
			}

			// check if the black in check, If so, this move must undo the check
			if(kingIsInCheck(black))
			{
				// if the move does not undo the check, then it is an illegal move
				Piece from_piece = (Piece) getPieceFromPosition(move_from_column + move_from_row);
				PieceFile originalFile = from_piece.pieceFile;
				int originalRank = from_piece.pieceRank;

				// simulate the move
				from_piece.setPosition(PieceFile.valueOf(move_to_column), Integer.parseInt(move_to_row));

				if(kingIsInCheck(black))
				{	
					// move the piece back
					from_piece.setPosition(originalFile, originalRank);
					returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
					return returnPlay;
				}
			}

			movePieceFromTo(move_from_column, move_from_row, move_to_column, move_to_row);
			
			// check for check
			if(kingIsInCheck(white))
			{
				returnPlay.message = ReturnPlay.Message.CHECK;
				// check for checkmate
				if(isKingInCheckmate(white))
				{
					returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
				}
				return returnPlay;
			}

			// set the last moved piece
			lastMovedPiece = getPieceFromPosition(move_to_column + move_to_row);
			

			if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE) // if the move was successful
			{
				switchPlayer(); //switch player & null the message
				++globalMoveCount; // increment the globalMoveCount
			}

		 }
		
		 /*
		  * This is the if statement to test a draw 
		  * This is here because a draw is performed after the move is executed unlike resign
		  */
		
		  if(drawPrompted() != null)
			return returnPlay;
		
		return returnPlay;
	}
	
	
	/*
	 * This method should reset the game, and start from scratch.
	 * 
	 * DON'T DELETE BUT YOU CAN EDIT
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
		whosPlaying = Player.white; // default player is white

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
			/*
			 * we will check if the pawn in question is at the end of the board
			 * and the strArr array is equal to 3, indicating a promotion query
			 */
			//{WP, WR, WN, WB, WQ, WK, 
			//BP, BR, BN, BB, BK, BQ};
			if(current_pawn.pieceRank == 8 && strArr.length == 3)
			{
				switch(strArr[2].toUpperCase())
				{
					//rook
					case "R":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.WR,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//Knight
					case "N":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.WN,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//bishop
					case "B":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.WB,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//queen
					case "Q":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.WQ,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;
				}
			}
			else if(current_pawn.pieceRank == 1 && strArr.length == 3)// take care of black's promotion at the end
			{
				switch(strArr[2].toUpperCase())
				{
					//rook
					case "R":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.BR,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//Knight
					case "N":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.BN,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//bishop
					case "B":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.BB,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;

					//queen
					case "Q":
						returnPlay.piecesOnBoard.add(new Rook(PieceType.BQ,current_pawn.pieceFile, current_pawn.pieceRank));
						returnPlay.piecesOnBoard.remove(current_pawn);
						break;
				}
			}
			else // then we will default to queen for the respective color
			{
				if(current_pawn.pieceRank == 8 && current_pawn.color.equals(white))
				{
					//adds a white queen in the place of the old pawn and removes old pawn
					returnPlay.piecesOnBoard.add(new Queen(PieceType.WQ,current_pawn.pieceFile, current_pawn.pieceRank));
					returnPlay.piecesOnBoard.remove(current_pawn);
				}
				else if(((Pawn)from_piece).pieceRank == 1 && ((Pawn)from_piece).color.equals(black))
				{
					returnPlay.piecesOnBoard.add(new Queen(PieceType.BQ,current_pawn.pieceFile, current_pawn.pieceRank));
					returnPlay.piecesOnBoard.remove(current_pawn);
				}
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
		//TODO UNCOMMENT THIS
		// TODO NEEDED CODE

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
	 * will return null if nothing exists
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
			System.out.println(whosPlaying.toString().toUpperCase() + " IS RESIGNING");
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

	/*
	 * This method will take in 2 pieces and will return true if 
	 * this piece can see that piece in its move map
	 */
	public static boolean thisPieceCanKillThatPiece(Piece thisPiece, Piece thatPiece)
	{
		Map<String, ReturnPiece> returnMovesOfThisPiece = thisPiece.moves;
		String thatPiecePosition = thatPiece.getPosition();
		return returnMovesOfThisPiece.containsKey(thatPiecePosition);
	}

	// check if the king is in check
	public static boolean kingIsInCheck(String targetColor)
	{
		/*
		 * First get the king of the color we are checking
		 */

		 King king = null;
	
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			//if the piece is an instance of king
			//and the piece is the correct color
			//save that instance of a king
			//break the loop
			if(piece instanceof King && ((King)piece).color.equals(targetColor))
			{
				king = (King) piece;
				break;
			}
		}

		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece instanceof King)
				continue;
			Piece p = (Piece)piece;

			if(p.getMoves().containsKey(king.getPosition())) // TODO currently here before checking out a bug in the pawn class
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
		King king = null;
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece instanceof King && ((Piece)piece).getColor().equals(color))
			{
				king = (King)piece;
				break;
			}
		}
		// checking this king has nowhere to move and the checking piece can't be killed
		if(king.getMoves().size() == 0 && !pieceCanBeKilled(getPieceThatIsCheckingKing(color)))
		{
			System.out.println("THE KING IS IN CHECKMATE");
				return true;
		}




		return false;
	}

	/*
	 * Return the instance of king
	 */
	public static King getKing(String color)
	{
		for(ReturnPiece piece : returnPlay.piecesOnBoard)
		{
			if(piece instanceof King)
			{
				King king = (King) piece;;

				if(king.getColor().equals(color))
					return king;
			}
		}

		return null;
	}

	/*
	 * Method to find the piece that is checking the king
	 */
	public static Piece getPieceThatIsCheckingKing(String color)
	{
		for(ReturnPiece p : returnPlay.piecesOnBoard)
		{
			if(p instanceof King)
				continue;

			Piece piece = (Piece) p;
			String positionOfKing = getKing(color).getPosition();

			if(!piece.getColor().equals(color) && piece.getMoves().containsKey(positionOfKing)) // if we are the enemy and the piece sees the king
			{
				return piece;
			}
		}


		return null;
	}
	/*
	* Will return true if a certain piece can be killed by the other team
	*/
	public static boolean pieceCanBeKilled(Piece targetPiece)
	{
		String enemyColor = targetPiece.getColor();
		String targetPosition = targetPiece.getPosition();

		for(ReturnPiece p : returnPlay.piecesOnBoard)
			{
				if(p instanceof King)
					continue;
				
				Piece piece = (Piece) p;

				if(!piece.getColor().equals(enemyColor)) // if the piece is the ally
				{
					if(piece.getMoves().containsKey(targetPosition)) // if the ally can kill the enemy
						return true;
				}
			}
		return false;
	}

	/*
	 * Helper method to check if I move a position from the original to the next,
	 * will it still be targeted
	 */
	public static boolean shiftPositionAndCheckIfPieceIsStillInDanger(Piece thisPiece, PieceFile checkPieceFile, int checkPieceRank)
	{
		PieceFile originalFile = thisPiece.pieceFile;
		int originalRank = thisPiece.pieceRank;
		ReturnPiece savePiece = Chess.getPieceFromPosition("" + checkPieceFile + checkPieceRank);
		returnPlay.piecesOnBoard.remove(savePiece);

		//changing the position to that piece to similate the new position
		thisPiece.setPosition(checkPieceFile, checkPieceRank);

		//if the piece is still in danger
		for(ReturnPiece p : returnPlay.piecesOnBoard)
		{
			if(p == thisPiece) continue;

			Piece piece = (Piece) p;

			if(piece.getMoves().containsKey(thisPiece.getPosition()))
			{
				thisPiece.setPosition(originalFile, originalRank);
				returnPlay.piecesOnBoard.add(savePiece);
				return true;
			}
		}
		returnPlay.piecesOnBoard.add(savePiece);
		thisPiece.setPosition(originalFile, originalRank);
		return false;
	}

	/*
	 * Method will invoke a helper method to check if
	 * the current piece can't kill the second pice passed in
	 * 
	 * this is mainly for the use of the KING
	 */
	public static boolean isEnemyButICantKillIt(Piece thisPiece, Piece thatPiece)
	{
		if (shiftPositionAndCheckIfPieceIsStillInDanger(thisPiece, thatPiece.pieceFile, thatPiece.pieceRank))
			return true;
	
		return false;
	}
	/*
	 * Will return false if the position is not on the board
	 */
	public static boolean positionIsWithinBoundsOfBoard(String position)
	{
		String [] positionArray = position.split("");

		if(positionArray.length >= 3) // negative number
			return false;


		try
		{
			PieceFile file = PieceFile.valueOf(positionArray[0]);
		}
		catch(Exception e)
		{
			return false;
		}

		int rank = Integer.parseInt(positionArray[1]);


		if(rank <= 0 || rank > 8 )
			return false;


		return true;
	}


	/*
	 * will return null if the position is invalid
	 * otherwise will return a new string with the change
	 */
	public static String getStringOfPositionWithChange(String position, int x, int y)
	{
		
		
			if(PieceFile.valueOf(position.substring(0,1)).ordinal() + x < 0 || PieceFile.valueOf(position.substring(0,1)).ordinal() + x >= 8)
			{
				return null;
			}

			if(Integer.parseInt(position.split("")[1]) + y < 1 || Integer.parseInt(position.split("")[1]) + y > 8)
			{
				return null;
			}

			return "" + PieceFile.values()[PieceFile.valueOf(position.split("")[0].toLowerCase()).ordinal() + x] + (Integer.parseInt(position.split("")[1]) + y);
	}


	/*
     * Takes in the current piece position and the checking piece position
     * 
     * will return true if the 2 colors differ
     * 
     * will return false if the 2 colors are the the same
     */
    public static boolean isEnemyForThisPiece(String thisPosition, String thatPosition)
    {
        Piece thisPiece = ((Piece) Chess.getPieceFromPosition(thisPosition));
        Piece thatPiece = ((Piece) Chess.getPieceFromPosition(thatPosition));

        if(thisPiece != null && thatPiece != null)
        {
            if(!thisPiece.getColor().equals(thatPiece.getColor()))
                return true;

        }
        
        return false;
    }

	/*
	 * Method that will return true of the piece on second position is the same color as the piece on first position
	 */
	public static boolean isAllyForThisPiece(String thisPosition, String thatPosition)
    {
        Piece thisPiece = ((Piece) Chess.getPieceFromPosition(thisPosition));
        Piece thatPiece = ((Piece) Chess.getPieceFromPosition(thatPosition));

        if(thisPiece != null && thatPiece != null)
        {
            if(thisPiece.getColor().equals(thatPiece.getColor()))
                return true;

        }
        
        return false;
    }

	/*
	 * Checks if the position is empty
	 * expects a valid position
	 */
	public static boolean isPositionEmpty(String targetPosition)
    {
        Piece thisPiece = ((Piece) Chess.getPieceFromPosition(targetPosition));

        if(thisPiece == null)
        {
                return true;
        }
        
        return false;
    }

	/*
	 * Method to check if a certain position is safe for a color
	 */
	public static boolean isPositionSafeFor(String targetPosition, String thisColor)
	{
		//what makes a position safe for a certain color?

		/*
		 * 1) the targetPosition should be empty
		 * 2) the target position should not be seen by an enemy
		 */

		 //1

		 if(Chess.isPositionEmpty(targetPosition))
		 {
			//2

			for(ReturnPiece p : returnPlay.piecesOnBoard)
			{
				Piece piece = (Piece) p;

				if(!piece.getColor().equals(thisColor)) // if the piece is the enemy
				{
					if(!piece.getMoves().containsKey(targetPosition))
						return true;
				}
			}
		 }

		 return false; // meaning the potion is not safe
	}
	

	
}


