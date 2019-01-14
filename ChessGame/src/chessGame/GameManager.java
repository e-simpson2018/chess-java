package chessGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chessGame.Piece.Colour;

public class GameManager 
{	
	//TODO only public for testing
	public String player = "white";
	public boolean gameNotOver = true;

	/**
	 * changePlayer is invoked after each successful move and changes the colour (and player)
	 */
	private void changePlayer()
	{
		if(player == "white")
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
	 * @param cb the ChessBoard holds the lists of captured pieces
	 */
	public void capturePiece(Piece capturedPiece, ChessBoard cb)
	{
		if(capturedPiece.getColour()==Colour.WHITE)
		{
			cb.capturedWhite.add(capturedPiece);
		}
		else
		{
			cb.capturedBlack.add(capturedPiece);
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
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @param pieceToMove the piece to move
	 */
	public void movePiece(Location initialPosition, Location pieceDestination, ChessBoard cb, Piece pieceToMove)
	{
		if(pieceToMove != null)
		{
			System.out.println("movePiece: " + pieceToMove);
			//Add piece to new location and delete from old location
			cb.board[pieceDestination.getRow()][pieceDestination.getColumn()] = pieceToMove;
			cb.board[initialPosition.getRow()][initialPosition.getColumn()] = null;
			System.out.println("PIECE WAS MOVED");
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
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
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
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @return location of the opponent's king
	 */
	//TODO refactor
	private Location getOpponentsKingLocation(ChessBoard cb)
	{
		//System.out.println("inside getOpponentsKingLocation");
		Location opponentsKingLocation = null;
		
		for(int i = 0; i < cb.CHESSBOARD_LENGTH; i++)
		{
			for(int j = 0; j < cb.CHESSBOARD_WIDTH; j++)
			{
				if(cb.board[i][j] != null)
				{
					if(cb.board[i][j].getPieceId() == 4 && player == "black")
					{
						opponentsKingLocation = new Location(i, j);
						return opponentsKingLocation;
					}
					else if(cb.board[i][j].getPieceId() == 3 && player == "white")
					{
						opponentsKingLocation = new Location(i, j);
						return opponentsKingLocation;
					}
				}
			}
		}	
		//System.out.println("opponentsKingLocation: " + opponentsKingLocation);
		return opponentsKingLocation;
	}
	
	
	/**
	 * checkForCheck is invoked after every successful move, and checks if a player is in check
	 * 
	 * @param pieceJustMoved the piece that was moved last
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @return true if check has been achieved
	 */
	//TODO could also put a piece in check by a piece moving away and exposing a dangerous path of another piece. Check!
	public boolean checkForCheck(Piece pieceJustMoved, ChessBoard cb)
	{
		System.out.println("checking for check");
		//TODO check if need to change this method and just check if any of the pieces put in check !! (pretty sure I do!!)
		boolean inCheck = false; //flag for check

		Location opponentsKingLocation = getOpponentsKingLocation(cb);

		//TODO should pass the actual location to not loop through with getLocation
		//If pieceJustMoved can reach the opponent's king in its next move, it is in check 
		if(pieceJustMoved.validPieceMovement(cb.getLocation(pieceJustMoved), opponentsKingLocation, cb))
		{
			inCheck = true;
		}
		return inCheck;
	}
	
	
	/**
	 * checkForCheckMate is invoked if a player is in check, and checks for checkmate
	 * 1) by looking at the squares surrounding the king to see if it can move out of danger itself
	 * 2) by checking if another piece can move in between the king and piece putting it in check
	 * 
	 * @param pieceJustMoved the piece that was moved last
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @return true if checkmate has been achieved
	 */
	//TODO also need to check if another piece can take the piece putting king in check
	public boolean checkForCheckMate(Piece pieceJustMoved, ChessBoard cb)
	{
		System.out.println("inside checkMATE");
		boolean inCheckMate = false; //flag for checkmate
		//TODO Should it be an arraylist instead?
		//Holds viable spaces for the king to move and will later check if each one is safe
		Location[] kingEscapeSquares = new Location[8];
		//TODO check if I can link the 2 methods in stead of having to loop through for the 
		// location again
		Location opponentsKingLocation = getOpponentsKingLocation(cb);
		
		//check if king can move out of danger itself
		if(squareSurroundingKingOpen(opponentsKingLocation, cb, kingEscapeSquares))
		{
			//System.out.println("SQUARESURROUNDINGKING CAME BACK TRUE");
			//Check if the potential king move is free from danger from other pieces
			if(!secondaryCheckKingOutOfDanger(opponentsKingLocation, cb, kingEscapeSquares))
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
				System.out.println("inCheckMate final : " + inCheckMate);
				
			}
		}
		System.out.println("inCheckMate initial : " + inCheckMate);

		//check if other pieces can save king

		return inCheckMate;
	}
	
	
	
	/**
	 * squareSurroundingKingOpen is called from checkForCheckMate(). It looks at the squares surrounding the king-in-check to see
	 * if any of them are available to be moved into. If they are, they are added to a list.
	 * 
	 * @param kingsLocation the location of the king-in-check
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @param kingEscapeSquares holds the possible moves the king can make to move out of check
	 * @return true if there is a potential square to move the king out of danger
	 */
	//TODO redo this method!!!!!
	private boolean squareSurroundingKingOpen(Location kingsLocation, ChessBoard cb, Location[] kingEscapeSquares)
	{
		System.out.println("inside squareSurroundingKingOpen");
		boolean kingCanMove = false; 
		int kr = kingsLocation.getRow();
		int kc = kingsLocation.getColumn();
		
		//System.out.println("kingsLocation.getRow(): " + kingsLocation.getRow());
		//System.out.println("kingsLocation.getColumn(): " + kingsLocation.getColumn());
		
		Piece kingRight = null;
		Piece kingUp = null;
		Piece kingLeft = null;
		Piece kingDown = null;
		Piece kingUpRight = null;
		Piece kingDownLeft = null;
		Piece kingRightDown = null;
		Piece kingLeftUp = null;
		
		//TODO need to take into account if the king is in a corner
		if((kingsLocation.getRow() <= 6 && kingsLocation.getRow() >= 1) && (kingsLocation.getColumn() <= 6 && kingsLocation.getColumn() >= 1))
		{
			int index = 0;
			kingRight = cb.board[kr + 1][kc];
			kingCanMove = addToKingEscapeSquares(kingRight, kingEscapeSquares, (kr + 1), kc, index);
			index++;
			kingUp = cb.board[kr][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUp, kingEscapeSquares, kr, (kc + 1), index);
			index++;
			kingLeft = cb.board[kr - 1][kc];
			kingCanMove = addToKingEscapeSquares(kingLeft, kingEscapeSquares, (kr - 1), kc, index);
			index++;
			kingDown = cb.board[kr][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDown, kingEscapeSquares, kr, (kc - 1), index);
			index++;
			kingUpRight = cb.board[kr + 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUpRight, kingEscapeSquares, (kr + 1), (kc + 1), index);
			index++;
			kingDownLeft = cb.board[kr - 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDownLeft, kingEscapeSquares, (kr - 1), (kc - 1), index);
			index++;
			kingRightDown = cb.board[kr + 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingRightDown, kingEscapeSquares, (kr + 1), (kc - 1), index);
			index++;
			kingLeftUp = cb.board[kr - 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingLeftUp, kingEscapeSquares, (kr - 1), (kc + 1), index);
			
		}
		else if(kingsLocation.getRow() == 7)
		{
			int index = 0;
			kingUp = cb.board[kr][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUp, kingEscapeSquares, kr, (kc + 1), index);
			index++;
			kingLeft = cb.board[kr - 1][kc];
			kingCanMove = addToKingEscapeSquares(kingLeft, kingEscapeSquares, (kr - 1), kc, index);
			index++;
			kingDown = cb.board[kr][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDown, kingEscapeSquares, kr, (kc - 1), index);
			index++;
			kingDownLeft = cb.board[kr - 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDownLeft, kingEscapeSquares, (kr - 1), (kc - 1), index);
			index++;
			kingLeftUp = cb.board[kr - 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingLeftUp, kingEscapeSquares, (kr - 1), (kc + 1), index);
		}
		else if(kingsLocation.getRow() == 0)
		{
			int index = 0;
			kingRight = cb.board[kr + 1][kc];
			kingCanMove = addToKingEscapeSquares(kingRight, kingEscapeSquares, (kr + 1), kc, index);
			index++;
			kingUp = cb.board[kr][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUp, kingEscapeSquares, kr, (kc + 1), index);
			index++;
			kingDown = cb.board[kr][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDown, kingEscapeSquares, kr, (kc - 1), index);
			index++;
			kingUpRight = cb.board[kr + 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUpRight, kingEscapeSquares, (kr + 1), (kc + 1), index);
			index++;
			kingRightDown = cb.board[kr + 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingRightDown, kingEscapeSquares, (kr + 1), (kc - 1), index);

		}
		else if(kingsLocation.getColumn() == 7)
		{
			int index = 0;
			kingRight = cb.board[kr + 1][kc];
			kingCanMove = addToKingEscapeSquares(kingRight, kingEscapeSquares, (kr + 1), kc, index);
			index++;
			kingLeft = cb.board[kr - 1][kc];
			kingCanMove = addToKingEscapeSquares(kingLeft, kingEscapeSquares, (kr - 1), kc, index);
			index++;
			kingDown = cb.board[kr][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDown, kingEscapeSquares, kr, (kc - 1), index);
			index++;
			kingDownLeft = cb.board[kr - 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingDownLeft, kingEscapeSquares, (kr - 1), (kc - 1), index);
			index++;
			kingRightDown = cb.board[kr + 1][kc - 1];
			kingCanMove = addToKingEscapeSquares(kingRightDown, kingEscapeSquares, (kr + 1), (kc - 1), index);

		}
		else if(kingsLocation.getColumn() == 0)
		{
			int index = 0;
			kingRight = cb.board[kr + 1][kc];
			kingCanMove = addToKingEscapeSquares(kingRight, kingEscapeSquares, (kr + 1), kc, index);
			index++;
			kingUp = cb.board[kr][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUp, kingEscapeSquares, kr, (kc + 1), index);
			index++;
			kingLeft = cb.board[kr - 1][kc];
			kingCanMove = addToKingEscapeSquares(kingLeft, kingEscapeSquares, (kr - 1), kc, index);
			index++;
			kingUpRight = cb.board[kr + 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingUpRight, kingEscapeSquares, (kr + 1), (kc + 1), index);
			index++;
			kingLeftUp = cb.board[kr - 1][kc + 1];
			kingCanMove = addToKingEscapeSquares(kingLeftUp, kingEscapeSquares, (kr - 1), (kc + 1), index);
		}
		
		return kingCanMove;
	}
	
	
	/**
	 * addToKingEscapeSquares is called from squareSurroundingKingOpen(). It adds a possible kingEscapeSquare to an array.
	 * 
	 * @param newSquare the location of a possible safe move for the king-in-check
	 * @param kingEscapeSquares holds the possible moves the king can make to move out of check
	 * @param kr the king's row
	 * @param kc the king's column
	 * @return true if location successfully added to the array
	 */
	//TODO check if it should return void in stead of boolean
	private boolean addToKingEscapeSquares(Piece newSquarePiece, Location[] kingEscapeSquares, int kr, int kc, int index)
	{
		//A square is possible a safe move for the king if it is null, or the piece already there is the opposite colour
		if((newSquarePiece == null) || (newSquarePiece != null && newSquarePiece.getColour().getPrintColourAsString() == player))
		{
			Location possibleMovement = new Location(kr, kc);
			kingEscapeSquares[index] = possibleMovement;
			return true;
		}
		return false;
	}
	
	
	/**
	 * secondaryCheckKingOutOfDanger is called from checkForCheckMate(). It takes each of the squares that is a potential safe move 
	 * for the king-in-check, and then looks to see if it will be in the path of another opponent piece 
	 * 
	 * @param opponentsKingLocation the location of the king-in-check
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @param kingEscapeSquares holds all the possible king movements
	 * @return true if the king has a safe option within his possible movements
	 */
	private boolean secondaryCheckKingOutOfDanger(Location opponentsKingLocation, ChessBoard cb, Location[] kingEscapeSquares)
	{
		System.out.println("inside secondaryCheckKingOutOfDanger");
		for(int i = 0; i < kingEscapeSquares.length; i++)
		{
			if(kingEscapeSquares[i] != null)
			{
				//TODO make a method for this so I can use in the method checking inbetween spaces for checkmate
				for(int j = 0; j < cb.CHESSBOARD_LENGTH; j++)
				{
					for(int k = 0; k < cb.CHESSBOARD_WIDTH; k++)
					{
						//If there is a piece on the square and it is the player's colour, check if it can reach the kingEscapeSquare
						if(cb.board[j][k] != null && cb.board[j][k].getColour().getPrintColourAsString() == player)
						{
							Location pieceLocation = new Location(j, k);
							if(!cb.board[j][k].validPieceMovement(pieceLocation, kingEscapeSquares[i], cb))
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
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @return true if the king can be saved by another of the opponent's pieces
	 */
	private boolean otherPieceCanSaveKing(Piece pieceEndangeringKing, Location opponentsKingLocation, ChessBoard cb)
	{
		boolean canSaveKing = false; //flag indicating if the king can be saved
		//TODO Get the location in check original method and pass it to all the subsequent methods??
		Location pieceEndangeringKingLocation = cb.getLocation(pieceEndangeringKing);
		
		//Knights and pawns on a normal move can not be intercepted
		if(pieceEndangeringKing instanceof Knight || pieceEndangeringKing instanceof Pawn)
		{
			//Nothing happens
		}
		else if(pieceEndangeringKing instanceof Rook)
		{
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing, cb))
			{
				canSaveKing = true;
			}
		}
		else if(pieceEndangeringKing instanceof Bishop)
		{
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing, cb))
			{
				canSaveKing = true;
			}
		}
		else if(pieceEndangeringKing instanceof Queen)
		{
			if(checkForInterveningPieces(pieceEndangeringKingLocation, opponentsKingLocation, pieceEndangeringKing, cb))
			{
				canSaveKing = true;
			}
		}
		return canSaveKing;
	}
	
	
	/**
	 * checkForInterveningPieces is called from otherPieceCanSaveKing(). It loops though the squares in between 
	 * pieceEndangeringKing and the king-in-check, and sees if another of the opponents' pieces can intervene to save the king 
	 * 
	 * @param pieceEndangeringKingLocation the location of the piece that is putting the king in check
	 * @param opponentsKingLocation the location of the king-in-check
	 * @param pieceEndangeringKing the piece that is putting the king in check
	 * @param cb ChessBoard holds all the pieces and locations on the board at that moment
	 * @return true if another piece can intervene in the check
	 */
	//TODO get rid of piece? Just added to access the methods inside here
	public boolean checkForInterveningPieces(Location pieceEndangeringKingLocation, Location opponentsKingLocation, Piece pieceEndangeringKing, ChessBoard cb)
	{
		boolean pieceCanIntervene = false; //flag indicating if piece can intervene
		Location inbetweenSquare = null;
		
		//set up temporary variables that change depending if the rows or columns are negative or positive
		pieceEndangeringKing.setTempVars(pieceEndangeringKingLocation, opponentsKingLocation);
		
		if(pieceEndangeringKing.verticalMovement(pieceEndangeringKingLocation, opponentsKingLocation))
		{
			for(int i = pieceEndangeringKing.temp1 ; i < pieceEndangeringKing.temp2; i++)
			{
				inbetweenSquare = new Location(i, pieceEndangeringKingLocation.getColumn());
				//will make a method for this so it is not duplicated
				for(int j = 0; j < cb.CHESSBOARD_LENGTH; j++)
				{
					for(int k = 0; k < cb.CHESSBOARD_WIDTH; k++)
					{
						if(cb.board[j][k] != null && cb.board[j][k].getColour().getPrintColourAsString() != player)
						{
							Location pieceToCheck = new Location(j, k);
							if(cb.board[j][k].validPieceMovement(pieceToCheck, inbetweenSquare, cb))
							{
								pieceCanIntervene = true;
							}
						}
					}
				}
			}
		}
		
		else if(pieceEndangeringKing.horizontalMovement(pieceEndangeringKingLocation, opponentsKingLocation))
		{
			for(int i = pieceEndangeringKing.temp1 + 1; i < pieceEndangeringKing.temp2; i++)
			{
				inbetweenSquare = new Location(pieceEndangeringKingLocation.getRow(), i);
				//will make a method for this so it is not duplicated
				for(int j = 0; j < cb.CHESSBOARD_LENGTH; j++)
				{
					for(int k = 0; k < cb.CHESSBOARD_WIDTH; k++)
					{
						if(cb.board[j][k] != null && !player.equals(cb.board[j][k].getColour().getPrintColourAsString()))
						{
							Location pieceToCheck = new Location(j, k);
							if(cb.board[j][k].validPieceMovement(pieceToCheck, inbetweenSquare, cb))
							{
								pieceCanIntervene = true;
							}
						}
					}
				}
			}
		}
		
		else if(pieceEndangeringKing.diagonalMovement(pieceEndangeringKingLocation, opponentsKingLocation))
		{
			for(int i = pieceEndangeringKing.temp1 + 1; i < pieceEndangeringKing.temp2; i++)
			{
				for(int j = pieceEndangeringKing.temp3 + 1; j < pieceEndangeringKing.temp4; j++) 
				{
					inbetweenSquare = new Location(i, j);
					//will make a method for this so it is not duplicated
					for(int k = 0; k < cb.CHESSBOARD_LENGTH; k++)
					{
						for(int l = 0; l < cb.CHESSBOARD_WIDTH; l++)
						{
							if(cb.board[k][l] != null && !player.equals(cb.board[k][l].getColour().getPrintColourAsString()))
							{
								Location pieceToCheck = new Location(k, l);
								if(cb.board[k][l].validPieceMovement(pieceToCheck, inbetweenSquare, cb))
								{
									pieceCanIntervene = true;
								}
							}
						}
					}
				}
			}
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
