package chessGame;

import java.util.Scanner;

import chessGame.Piece.Colour;

public class GameManager 
{	

	private String player = "white";
	private Scanner input = new Scanner(System.in);
		
	/*
	public boolean takeATurn(ChessBoard cb)
	{	
		Piece pieceToMove = null;
		boolean validPieceMovement = false;
		boolean validMove = false;
		Location initialPosition = null;
		Location pieceDestination = null;
		
		//pieceToMove = getPieceToMove();
		
		//TODO DECIDE WHAT TO DO IN THIS CIRCUMSTANCE
		//if(pieceToMove.getColour().getPrintColourAsString() != player)
		//{
			//System.out.println("You cannot move your opponent's piece");
		//}
		
		//pieceDestination = getDestination();
		
		
		//Check that the user's proposed move is valid for the chosen piece
		//validPieceMovement = pieceToMove.isPieceMovement(initialPosition, pieceDestination, cb);
		
		//validMove = isValidMove(validPieceMovement, pieceDestination);
			

		
		pawnPromotion(pieceDestination, pieceToMove, cb);
		
		changePlayer();
		return validMove;
	}*/
	
	/*
	private boolean isValidMove(boolean validPieceMovement, Location loc) 
	{
		//isValidMove() needs to include:
		//isPieceMovement() DONE
		//isOnBoard() DONE
		//if firstClick
		//	isPiecePlayersColour() DONE
		//if secondClick
		//	!isPiecePlayersColour() DONE
		
		boolean validMove = false;
		
		
		
		//TODO PAWNS WILL NEED TO CHECK FOR DIRECTION
		if(validPieceMovement && isOnBoard(loc.getRow(), loc.getColumn()))
		{

			//If a piece is already in the desired location, check if it is the opposition's colour
			if(cb.board[loc.getRow()][loc.getColumn()] != null)
			{
				if(player == "white" && cb.board[loc.getRow()][loc.getColumn()].getColour() == Piece.Colour.WHITE)
				{
					System.out.println("That move is not valid, you cannot land on your own piece");
					//TODO WORK OUT WHAT TO DO IF MOVE IS NOT VALID
				}
				else
				{
					//If it's the opposition's colour, take piece
					cb.capturedBlack.add(cb.board[loc.getRow()][loc.getColumn()]);
					System.out.println("You have taken a piece");
					validMove = true;
				}
			}
			else
			{
				validMove = true;
			}
		
		}
		
		return validMove;
		//REMEMEBER TO CHECK IF THE DESTINATION HAS PLAYER'S PIECE
	}*/
	

	
	public void capturePiece(Piece piece, ChessBoard cb)
	{
		if(piece.getColour()==Colour.WHITE)
		{
			cb.capturedWhite.add(piece);
		}
		else
		{
			cb.capturedBlack.add(piece);
		}
		System.out.println("You have taken a piece");
	}
	

	
	//Get user to name what piece user wants to move and get its location
	public Piece getPiece(int selectedRow, int selectedColumn, ChessBoard cb) 
	{
		System.out.println("selected piece to move is " + cb.board[selectedRow][selectedColumn]);
		return cb.board[selectedRow][selectedColumn];
		
		/*
		System.out.println(player + ", it's your turn");
		System.out.println("What's the piece you would like to move (using algebraic notation)?");
		String usersChoice = input.nextLine().toLowerCase();
		
		int currentRow = convertToRow(Integer.parseInt(usersChoice.substring(1, 2)));
		int currentColumn = convertToColumn(usersChoice.substring(0, 1));
		Location initialLocation = new Location(currentRow, currentColumn);
		
		pieceToMove = cb.board[currentRow][currentColumn];
		System.out.println("pieceToMove " + pieceToMove);
		
		if(!(isPiecePlayersColour(pieceToMove)))
		{
			System.out.print("You cannot move your opponenet's pieces");
			//TODO BREAK FROM METHOD
		}*/
		
	}
	
	//duplicate??
	/*
	public Piece getPieceSelected()
	{
		return null;
	}*/
	
	public boolean isPiecePlayersColour(Piece piece)
	{
		return (piece.getColour().getPrintColourAsString().equals(player));
	}
	
	//Ask user where they want to move to using algebraic notation and convert to a location
	/*
	public Location getDestination()
	{
		System.out.println("Where would you like to move it to (using algebraic notation)?");
		String placeToMoveTo = input.nextLine().toLowerCase();
		int newRow = convertToRow(Integer.parseInt(placeToMoveTo.substring(1, 2)));
		int newColumn = convertToColumn(placeToMoveTo.substring(0, 1));
		Location finalLocation = new Location(newRow, newColumn);	
		
		return finalLocation;
	}*/
	
	
	
	//TODO NEED A METHOD TO CHECK STATUS AFTER MOVING: CHECK OR CHECKMATE
	public boolean checkForCheckMate()
	{
		return false;
	}
	
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
	}
	
	public void movePiece(Location initialPosition, Location pieceDestination, ChessBoard cb, Piece pieceToMove)
	{
		//Add piece to new location and delete from old location
		cb.board[pieceDestination.getRow()][pieceDestination.getColumn()] = pieceToMove;
		cb.board[initialPosition.getRow()][initialPosition.getColumn()] = null;	
		afterMoveChecks(pieceDestination, pieceToMove, cb);
	}
	
	private void afterMoveChecks(Location loc, Piece piece, ChessBoard cb)
	{
		checkForCheckMate();
		checkForPawnPromo(loc, piece, cb);
		changePlayer();
	}

	
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
	
	
	public boolean isOnBoard(int newRow, int newColumn)
	{
		boolean isOnBoard = false;

		if((newRow >= 0 && newRow < 8) &&
				(newColumn >= 0 && newColumn < 8))
		{
			isOnBoard = true;
		}
		return isOnBoard;
	}
	
	
	//TODO CHECK ALGEBRAIC NOTATION
	private int convertToColumn(String str)
	{
		switch(str)
		{
			case "a" : return 0;
			case "b" : return 1;
			case "c" : return 2;
			case "d" : return 3;
			case "e" : return 4;
			case "f" : return 5;
			case "g" : return 6;
			case "h" : return 7;
			default : return -1; //TODO PRINT SOMETHING??
		}
	}
	
	
	private int convertToRow(int i)
	{
		switch(i)
		{
			case 1 : return 7;
			case 2 : return 6;
			case 3 : return 5;
			case 4 : return 4;
			case 5 : return 3;
			case 6 : return 2;
			case 7 : return 1;
			case 8 : return 0;
			default : return -1; //TODO PRINT SOMETHIG??
		}
	}
}
