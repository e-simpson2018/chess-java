package chessGame;

import java.util.ArrayList;
import java.util.List;

import chessGame.Piece.Colour;

public class GameManager 
{	
	//TODO only static for testing - had trouble accessing from PawnValidator
	public static String player;
	public boolean gameNotOver;

	//Constructor
	public GameManager()
	{
		player = "white";
		gameNotOver = true;
	}
	
	//Getter
	public String getPlayer()
	{
		return player;
	}
	
	
	/**
	 * changePlayer is invoked after each successful move and changes the colour (and player)
	 */
	private void changePlayer()
	{
		if(player.equals("white"))
		{
			player = "black";
		}
		else
		{
			player = "white";
		}
	}
	
	/**
	 * capturePiece is called from GameGUI. If a player lands on a square holding the opponent's piece, 
	 * it is added to the colour-appropriate list of captured pieces
	 * 
	 * @param capturedPiece the piece that is being captured
	 */
	public void capturePiece(Piece capturedPiece)
	{
		if(capturedPiece.getColour()==Colour.WHITE)
		{
			ChessBoard.capturedWhite.add(capturedPiece);
		}
		else
		{
			ChessBoard.capturedBlack.add(capturedPiece);
		}
		System.out.println("You have taken a piece");
	}
	
	/**
	 * isPiecePlayersColour checks if a piece is of the player's colour
	 * 
	 * @param piece the piece to be checked for colour
	 * @return true if the piece is the same colour as the player
	 */
	public boolean isPiecePlayersColour(Piece piece)
	{
		return (piece.getColour().getPrintColourAsString().equals(player));
	}
	
	
	/**
	 * movePiece physically moves a piece from one location to another, then calls the method afterMoveChecks()
	 * 
	 * @param initialPosition location of the piece to move
	 * @param pieceDestination destination of the piece
	 * @param pieceToMove the piece to move
	 * @param cb used to get location of a piece
	 */
	public void movePiece(Location initialPosition, Location pieceDestination, Piece pieceToMove, ChessBoard cb)
	{
		if(pieceToMove != null)
		{
			//TODO CHECK THIS METHOD WORKS : SOMETIMES IT SAYS A PIECE HAS MOVED, BUT IT IS STILL VISIBLE ON THE BOARD
			System.out.println("movePiece: " + pieceToMove);
			//Add piece to new location and delete from old location
			ChessBoard.board[pieceDestination.getRow()][pieceDestination.getColumn()] = pieceToMove;
			ChessBoard.board[initialPosition.getRow()][initialPosition.getColumn()] = null;
			//System.out.println("destination should hold the new piece: " + (ChessBoard.board[pieceDestination.getRow()][pieceDestination.getColumn()]).toString());
			//System.out.println("orig square should now be null: " + ChessBoard.board[initialPosition.getRow()][initialPosition.getColumn()]);
			//System.out.println("PIECE WAS MOVED");
			afterMoveChecks(pieceDestination, pieceToMove, cb);
		}
		else
		{
			System.out.println("NO PIECE TO MOVE");
		}
	}
	
	
	/**
	 * afterMoveChecks is called after a piece is moved and calls methods to look for check/mate, check if a pawn has survived to the 
	 * other side (pawnPromotion), and changes player colour
	 * 
	 * @param pieceJustMovedLocation location of the last piece moved
	 * @param pieceJustMoved last piece moved
	 * @param cb used to get location of a piece
	 */
	private void afterMoveChecks(Location pieceJustMovedLocation, Piece pieceJustMoved, ChessBoard cb)
	{
		if(checkForCheck(pieceJustMoved, cb))
		{
			System.out.println("Check");
			if(checkForCheckMate(pieceJustMoved, cb))
			{
				//TODO end game
				System.out.println("CHECKMATE - GAME OVER!");
				gameNotOver = false;
			}
		}

		//TODO checkForPawnPromo(loc, piece, cb); - PUT IT IN PAWN CLASS AFTER VALID MOVEMENT?
		changePlayer();
	}
	
	
	
	/**
	 * getOpponentsKingLocation checks the player's colour, loops through the board looking for the opponent's king,
	 * and returns its location
	 * 
	 * @return location of the opponent's king
	 */
	private Location getOpponentsKingLocation()
	{
		Location opponentsKingLocation = null;
		
		for(int i = 0; i < ChessBoard.CHESSBOARD_LENGTH; i++)
		{
			for(int j = 0; j < ChessBoard.CHESSBOARD_WIDTH; j++)
			{
				if(ChessBoard.board[i][j] != null)
				{
					//4 is the id of the white king
					if(ChessBoard.board[i][j].getPieceId() == 4 && player.equals("black"))
					{
						opponentsKingLocation = new Location(i, j);
						return opponentsKingLocation;
					}
					//3 is the id of the black king
					else if(ChessBoard.board[i][j].getPieceId() == 3 && player.equals("white"))
					{
						opponentsKingLocation = new Location(i, j);
						return opponentsKingLocation;
					}
				}
			}
		}	
		return opponentsKingLocation;
	}
	
	
	/**
	 * checkForCheck is invoked after every successful move, and checks if a player is in check
	 * 
	 * @param pieceJustMoved the piece that was moved last
	 * @param cb cb used to get location of a piece
	 * @return true if check has been achieved
	 */
	//TODO could also put a piece in check by a piece moving away and exposing a dangerous path of another piece. Check!
	public boolean checkForCheck(Piece pieceJustMoved, ChessBoard cb)
	{
		System.out.println("checking for check");
		boolean inCheck = false; //flag for check

		Location opponentsKingLocation = getOpponentsKingLocation();

		//If pieceJustMoved can reach the opponent's king in its next move, it is in check 
		if(pieceJustMoved.runMoveValidator(cb.getLocation(pieceJustMoved), opponentsKingLocation))
		{
			inCheck = true;
		}
		return inCheck;
	}
	
	
	/**
	 * checkForCheckMate is invoked if a player is in check, and checks for checkmate
	 * 1) by looking at the squares surrounding the king to see if it can move out of danger itself
	 * 2) by checking if another piece can move in between the king and piece  that's putting it in check
	 * 
	 * @param pieceJustMoved the piece that was moved last
	 * @param cb cb used to get location of a piece
	 * @return true if checkmate has been achieved
	 */
	//TODO also need to check if another piece can take the piece putting king in check
	public boolean checkForCheckMate(Piece pieceJustMoved, ChessBoard cb)
	{
		System.out.println("inside checkMATE");
		boolean inCheckMate = false; //flag for checkmate
		//Holds viable spaces for the king to move and will later check if each one is safe
		List<Location> kingEscapeSquares = new ArrayList<Location>();
		Location opponentsKingLocation = getOpponentsKingLocation();
		
		//check if king can move out of danger itself
		if(squareSurroundingKingOpen(opponentsKingLocation, kingEscapeSquares))
		{
			//Check if the potential king move is free from danger from other pieces
			if(!secondaryCheckKingOutOfDanger(opponentsKingLocation, kingEscapeSquares))
			{
				inCheckMate = true;
			}
		}
		else
		{
			inCheckMate = true;
			if(otherPieceCanSaveKing(pieceJustMoved, opponentsKingLocation, cb))
			{
				inCheckMate = false;
			}
		}
		return inCheckMate;
	}
	
	
	
	/**
	 * squareSurroundingKingOpen is called from checkForCheckMate(). It looks at the squares surrounding the king-in-check to see
	 * if any of them are available to be moved into. If they are, they are added to a list.
	 * 
	 * @param kingsLocation the location of the king-in-check
	 * @param kingEscapeSquares holds the possible moves the king can make to move out of check
	 * @return true if there is a potential square to move the king out of danger
	 */
	//TODO need to test!!!!!!
	private boolean squareSurroundingKingOpen(Location kingsLocation, List<Location> kingEscapeSquares)
	{
		int kr = kingsLocation.getRow();
		int kc = kingsLocation.getColumn();
		//array of flags for squares surrounding king: [kr + 1][kc], [kr][kc + 1], [kr - 1][kc], [kr][kc - 1], 
		//[kr + 1][kc + 1], [kr - 1][kc - 1], [kr + 1][kc - 1], [kr - 1][kc + 1]
		boolean[] squaresAreOnBoard = {true, true, true, true, true, true, true, true};
		
		//all the cases that will change flag to false (edge cases that are off the board)
		if(kr == 7)
		{
			squaresAreOnBoard[0] = false; //square below king - [kr + 1][kc]
			squaresAreOnBoard[4] = false; //square bottom right of king - [kr + 1][kc + 1]
			squaresAreOnBoard[6] = false; //square bottom left of square - [kr + 1][kc - 1]
			
			setFlagsForEscapeSquaresColumns(squaresAreOnBoard, kc);
		}	
		else if(kr == 0)
		{
			squaresAreOnBoard[2] = false; //square above king - [kr - 1][kc]
			squaresAreOnBoard[5] = false; //square top left of king - [kr - 1][kc - 1]
			squaresAreOnBoard[7] = false; //square top right of king - [kr - 1][kc + 1]
			
			setFlagsForEscapeSquaresColumns(squaresAreOnBoard, kc);
		}
		else
		{
			setFlagsForEscapeSquaresColumns(squaresAreOnBoard, kc);
		}

		addEscapeSquareToList((kr + 1), kc, kingEscapeSquares, squaresAreOnBoard[0]); //square below king - [kr + 1][kc]
		addEscapeSquareToList(kr, kc + 1, kingEscapeSquares, squaresAreOnBoard[1]); //square right of king - [kr][kc + 1]
		addEscapeSquareToList(kr - 1, kc, kingEscapeSquares, squaresAreOnBoard[2]); //square above king - [kr - 1][kc]
		addEscapeSquareToList(kr, kc - 1, kingEscapeSquares, squaresAreOnBoard[3]); //square left of king - [kr][kc - 1]
		addEscapeSquareToList(kr + 1, kc + 1, kingEscapeSquares, squaresAreOnBoard[4]); //square bottom right of king - [kr + 1][kc + 1]
		addEscapeSquareToList(kr - 1, kc - 1, kingEscapeSquares, squaresAreOnBoard[5]); //square top left of king - [kr - 1][kc - 1]
		addEscapeSquareToList(kr + 1, kc - 1, kingEscapeSquares, squaresAreOnBoard[6]); //square bottom left of square - [kr + 1][kc - 1]
		addEscapeSquareToList(kr - 1, kc + 1, kingEscapeSquares, squaresAreOnBoard[7]); //square top right of king - [kr - 1][kc + 1]
					
		return !(kingEscapeSquares.isEmpty());
	}
	
	/**
	 * This method is called from squareSurroundingKingOpen() and checks the square being passed is on the board (validSquare)
	 * and then checks if the square is a possible safe move for the king-in-check, and adds to the arrayList
	 */
	public void addEscapeSquareToList(int row, int col, List<Location> kingEscapeSquares, boolean validSquare)
	{
		if(validSquare)
		{
			Piece newSquarePiece = ChessBoard.board[row][col];
			//A square is a possible safe move for the king if the piece is null, or the piece already there is the opposite colour
			if((newSquarePiece == null) || (newSquarePiece != null && newSquarePiece.getColour().getPrintColourAsString().equals(player)))
			{
				Location possibleMovement = new Location(row, col);
				kingEscapeSquares.add(possibleMovement);
			}
		}
	}
	
	/**
	 * setFlagsForEscapeSquaresColumns is called from squareSurroundingKingOpen() to invalidate squares that are off the board
	 * from columns
	 */
	public void setFlagsForEscapeSquaresColumns(boolean[] flagsIfValidSquare, int col)
	{
		if(col == 7)
		{
			flagsIfValidSquare[1] = false; //square right of king - [kr][col + 1]
			flagsIfValidSquare[4] = false; //square bottom right of king - [kr + 1][col + 1]
			flagsIfValidSquare[7] = false; //square top right of king - [kr - 1][col + 1]
		}
		else if(col == 0)
		{
			flagsIfValidSquare[3] = false; //square left of king - [kr][col - 1]
			flagsIfValidSquare[5] = false; //square top left of king - [kr - 1][col - 1]
			flagsIfValidSquare[6] = false; //square bottom left of square - [kr + 1][col - 1]
		}
	}
	
	
		
	/**
	 * secondaryCheckKingOutOfDanger is called from checkForCheckMate(). It takes each of the squares that is a potential safe move 
	 * for the king-in-check, and then looks to see if it will be in the path of another opponent piece 
	 * 
	 * @param opponentsKingLocation the location of the king-in-check
	 * @param kingEscapeSquares holds all the possible king movements
	 * @return true if the king has a safe option within his possible movements
	 */
	private boolean secondaryCheckKingOutOfDanger(Location opponentsKingLocation, List<Location> kingEscapeSquares)
	{
		for(int i = 0; i < kingEscapeSquares.size(); i++)
		{
			//TODO NEED THIS? I already check for null before I save them to the arrayList
			if(kingEscapeSquares.get(i) != null)
			{
				for(int j = 0; j < ChessBoard.CHESSBOARD_LENGTH; j++)
				{
					for(int k = 0; k < ChessBoard.CHESSBOARD_WIDTH; k++)
					{
						//If there is a piece on the square and it is the player's colour, check if it can reach the kingEscapeSquare
						if(ChessBoard.board[j][k] != null && player.equals(ChessBoard.board[j][k].getColour().getPrintColourAsString()))
						{
							Location pieceLocation = new Location(j, k);
							if(!ChessBoard.board[j][k].runMoveValidator(pieceLocation, kingEscapeSquares.get(i)))
							{
								System.out.println("a kingEscapeSquare is free");
								return true;
							}
						}
					}
				}
			}
		}
		System.out.println("no free kingEscapeSquare");
		return false;
	}
	
	
	
	/**
	 * otherPieceCanSaveKing is called from checkForCheckMate(). It takes the piece that is putting the king in danger,
	 * loops though the squares in between and sees if another of the opponents' pieces can intervene to save the king 
	 * 
	 * @param pieceEndangeringKing the piece that is putting the king in check
	 * @param opponentsKingLocation location of the king in danger
	 * @param cb used to get location of a piece
	 * @return true if the king can be saved by another of the opponent's pieces
	 */
	private boolean otherPieceCanSaveKing(Piece pieceEndangeringKing, Location opponentsKingLocation, ChessBoard cb)
	{
		boolean canSaveKing = false; //flag
		Location pieceEndangeringKingLocation = cb.getLocation(pieceEndangeringKing);
		
		//Knights and pawns on a normal move can not be intercepted
		if(pieceEndangeringKing instanceof Knight || pieceEndangeringKing instanceof Pawn)
		{
			//Nothing happens
		}
		else if(pieceEndangeringKing instanceof Rook)
		{
			//TODO CAST TO THEIR SPECIFIC INSTANCES!!!!!!
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing))
			{
				canSaveKing = true;
			}
		}
		else if(pieceEndangeringKing instanceof Bishop)
		{
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing))
			{
				canSaveKing = true;
			}
		}
		else if(pieceEndangeringKing instanceof Queen)
		{
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing))
			{
				canSaveKing = true;
			}
		}
		return canSaveKing;
	}
	
	/**
	 * checkForInterveningPieces is called from otherPieceCanSaveKing() and does the actual looping through all the pieces
	 */
	public boolean checkForInterveningPieces(Location pieceEndangeringKingLocation, Location opponentsKingLocation, Piece pieceEndangeringKing)
	{
		boolean pieceCanIntervene = false; //flag indicating if piece can intervene
		Location inbetweenSquare = null;
		
		//set up temporary variables that change depending if the rows or columns are negative or positive. Returns the shape of move
		String moveShape = pieceEndangeringKing.setTempVars(pieceEndangeringKingLocation, opponentsKingLocation);
		
		int j = pieceEndangeringKing.temp3;
		for(int i = pieceEndangeringKing.temp1 ; i < pieceEndangeringKing.temp2; i++)
		{
			for(int k = 0; k < ChessBoard.CHESSBOARD_LENGTH; k++)
			{
				for(int l = 0; l < ChessBoard.CHESSBOARD_WIDTH; l++)
				{
					//Check if the board square has a piece, and if so, if it's the opponent's colour
					if(ChessBoard.board[k][l] != null && !player.equals(ChessBoard.board[k][l].getColour().getPrintColourAsString()))
					{
						Location pieceToCheck = new Location(k, l);
						if(moveShape.equals("vertical"))
						{
							inbetweenSquare = new Location(i, pieceEndangeringKingLocation.getColumn());
						}
						else if(moveShape.equals("horizontal"))
						{
							inbetweenSquare = new Location(pieceEndangeringKingLocation.getRow(), i);
						}
						else
						{
							inbetweenSquare = new Location(i, j);
						}
						
						//the piece that has been found is now checked to see if it can move to cut off the check
						if(ChessBoard.board[j][k].runMoveValidator(pieceToCheck, inbetweenSquare))
						{
							pieceCanIntervene = true;
						}
					}
				}
			}
			j = j + pieceEndangeringKing.temp4;
		}
		return pieceCanIntervene;
	}
	
	
	
	/*
	//TODO go over logic behind this
	//Checking if pawn got to other side of the board, can promote it
	public void checkForPawnPromo(Location loc, Piece pieceToMove, ChessBoard cb)
	{
		if(player == "white" && pieceToMove.getColour().getPrintColourAsString() == "white" &&
				pieceToMove.getPieceType() == "pawn" && loc.getRow() == 0)
		{
			cb.board[loc.getRow()][loc.getColumn()] = pawnPromotion2(cb);
		}
		else if(player == "black" && pieceToMove.getColour().getPrintColourAsString() == "black" &&
				pieceToMove.getPieceType() == "pawn" && loc.getRow() == 7)
		{
			cb.board[loc.getRow()][loc.getColumn()] = pawnPromotion2(cb);
		}
	}
	
	//TODO go over this
	public Piece pawnPromotion2(ChessBoard cb)
	{
		Piece promotedPiece = null;
		System.out.println("Your pawn has reached the other side!");
		System.out.println("Which piece would you like to promote it to?");
		
		String promotion = input.nextLine().toLowerCase();
		
		//TODO MUST BE A BETTER WAY THAN REUSING THE FOR LOOP TWICE	
		if(player == "white")
		{
			for(Piece capturedPiece : cb.capturedWhite)
			{
				if(capturedPiece.getPieceType() == promotion)
				{
					promotedPiece = capturedPiece;
					//TODO DELETE THE PIECE FROM HERE
				}
				else
				{
					System.out.println("That piece has not been taken. Pick another piece");
				}
			}
		}
		else
		{
			for(Piece capturedPiece : cb.capturedBlack)
			{
				if(capturedPiece.getPieceType() == promotion)
				{
					promotedPiece = capturedPiece;
					//TODO DELETE THE PIECE FROM HERE
				}
				else
				{
					System.out.println("That piece has not been taken. Pick another piece");
				}
			}
		}
		return promotedPiece;		
	}*/
	


	

	

}
