package chessGame;

import java.util.Scanner;

public class GameManager 
{
	//TODO MAKE THESE ALL PRIVATE????
	//Create all the board pieces
	static Rook w1R = new Rook(Piece.Colour.WHITE);
	static Rook w2R = new Rook(Piece.Colour.WHITE);
	static Rook b1R = new Rook(Piece.Colour.BLACK);
	static Rook b2R = new Rook(Piece.Colour.BLACK);
	static Knight w1N = new Knight(Piece.Colour.WHITE);
	static Knight w2N = new Knight(Piece.Colour.WHITE);
	static Knight b1N = new Knight(Piece.Colour.BLACK);
	static Knight b2N = new Knight(Piece.Colour.BLACK);
	//Bishop bishop1W = new Bishop(Piece.Colour.WHITE);
	//Bishop bishop2W = new Bishop(Piece.Colour.WHITE);
	//Bishop bishop1B = new Bishop(Piece.Colour.BLACK);
	//Bishop bishop2B = new Bishop(Piece.Colour.BLACK);
	static Queen wQ = new Queen(Piece.Colour.WHITE);
	static Queen bQ = new Queen(Piece.Colour.BLACK);
	static King wK = new King(Piece.Colour.WHITE);
	static King bK = new King(Piece.Colour.BLACK);
	static Pawn w1P = new Pawn(Piece.Colour.WHITE);
	static Pawn w2P = new Pawn(Piece.Colour.WHITE);
	static Pawn w3P = new Pawn(Piece.Colour.WHITE);
	static Pawn w4P = new Pawn(Piece.Colour.WHITE);
	static Pawn w5P = new Pawn(Piece.Colour.WHITE);
	static Pawn w6P = new Pawn(Piece.Colour.WHITE);
	static Pawn w7P = new Pawn(Piece.Colour.WHITE);
	static Pawn w8P = new Pawn(Piece.Colour.WHITE);
	static Pawn b1P = new Pawn(Piece.Colour.BLACK);
	static Pawn b2P = new Pawn(Piece.Colour.BLACK);
	static Pawn b3P = new Pawn(Piece.Colour.BLACK);
	static Pawn b4P = new Pawn(Piece.Colour.BLACK);
	static Pawn b5P = new Pawn(Piece.Colour.BLACK);
	static Pawn b6P = new Pawn(Piece.Colour.BLACK);
	static Pawn b7P = new Pawn(Piece.Colour.BLACK);
	static Pawn b8P = new Pawn(Piece.Colour.BLACK);

	
	static String player = "white";
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		//Create a new board passing in all pieces
		ChessBoard cb = new ChessBoard(wQ, bQ, wK, bK, w1R, w2R, b1R, b2R, w1N, w2N, b1N, b2N, w1P, w2P, w3P, w4P, w5P, w6P, w7P, w8P,
										b1P, b2P, b3P, b4P, b5P, b6P, b7P, b8P);
		cb.displayBoard();
		
		do 
		{
			takeATurn(cb);
			cb.displayBoard();
		}while(!checkForCheckMate());
	}
		
	public static boolean takeATurn(ChessBoard cb)
	{	
		Piece pieceToMove;
		boolean validPieceMovement = false;
		boolean validMove = false;
		
		
		//Get user to name what piece user wants to move and get its location
		System.out.println(player + ", it's your turn");
		System.out.println("What's the piece you would like to move (using algebraic notation)?");
		String usersChoice = input.nextLine().toLowerCase();
		//System.out.println("piece: " + usersChoice);
		int currentRow = convertToRow(Integer.parseInt(usersChoice.substring(1, 2)));
		int currentColumn = convertToColumn(usersChoice.substring(0, 1));
		Location initialLocation = new Location(currentRow, currentColumn);
		//System.out.println("pieceToMove: " + pieceToMove);
		//Location initialLocation = cb.getLocation(pieceToMove);
		//System.out.println("initial row : " + initialLocation.getRow());
		//System.out.println("initial col : " + initialLocation.getColumn());

		pieceToMove = cb.board[currentRow][currentColumn];
		System.out.println("pieceToMove " + pieceToMove);
		
		//TODO DECIDE WHAT TO DO IN THIS CIRCUMSTANCE
		if(pieceToMove.getColour().getPrintColourAsString() != player)
		{
			System.out.println("You cannot move your opponent's piece");
		}
		
		//Ask user where they want to move to using algebraic notation and convert to a location
		System.out.println("Where would you like to move it to (using algebraic notation)?");
		String placeToMoveTo = input.nextLine().toLowerCase();
		//System.out.println("place: " + placeToMoveTo);
		int newRow = convertToRow(Integer.parseInt(placeToMoveTo.substring(1, 2)));
		int newColumn = convertToColumn(placeToMoveTo.substring(0, 1));
		Location finalLocation = new Location(newRow, newColumn);
		//System.out.println("row: " + newRow);
		//System.out.println("column: " + newColumn);
		
		
		
		//Check that the user's proposed move is valid for the chosen piece
		validPieceMovement = pieceToMove.movePiece(initialLocation, finalLocation, cb);
		//System.out.println(validPieceMovement);
				
		//TODO PAWNS WILL NEED TO CHECK FOR DIRECTION
		if(validPieceMovement && checkLocationIsOnBoard(newRow, newColumn))
		{
			//If a piece is already in the desired location, check if it is the opposition's colour
			if(cb.board[newRow][newColumn] != null)
			{
				if(player == "white" && cb.board[newRow][newColumn].getColour() == Piece.Colour.WHITE)
				{
					System.out.println("That move is not valid, you cannot land on your own piece");
					//TODO WORK OUT WHAT TO DO IF MOVE IS NOT VALID
				}
				else
				{
					//If it's the opposition's colour, take piece
					cb.capturedBlack.add(cb.board[newRow][newColumn]);
					System.out.println("You have taken a piece");
					validMove = true;
				}
			}
			else
			{
				validMove = true;
			}
			
			//Add piece to new location and delete from old location
			cb.board[newRow][newColumn] = pieceToMove;
			cb.board[initialLocation.getRow()][initialLocation.getColumn()] = null;	
			
			if(player == "white" && pieceToMove.getColour().getPrintColourAsString() == "white" &&
					pieceToMove.getPieceType() == "pawn" && finalLocation.getRow() == 0)
			{
				cb.board[finalLocation.getRow()][finalLocation.getColumn()] = pawnPromotion(cb);
			}
			else if(player == "black" && pieceToMove.getColour().getPrintColourAsString() == "black" &&
					pieceToMove.getPieceType() == "pawn" && finalLocation.getRow() == 7)
			{
				cb.board[finalLocation.getRow()][finalLocation.getColumn()] = pawnPromotion(cb);
			}
		}
		

		
		changePlayer();
		return validMove;
	}
	
	//TODO NEED A METHOD TO CHECK STATUS AFTER MOVING: CHECK OR CHECKMATE
	
	private static boolean checkForCheckMate()
	{
		return false;
	}
	
	private static Piece pawnPromotion(ChessBoard cb)
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

	/*
	private static Piece getPiece(String usersChoice)
	{ 
		if(player == "white")
		{
			switch(usersChoice)
			{
				case "queen" :
					return wQ;
				case "king" :
					return wK;
				case "" :
					return w1R;
			default : 
					System.out.println("I do not recognise that piece");
					//TODO DECIDE WHAT TO DO IF SAY WRONG PIECE NAME
					return null;
			}
		}
		else
		{
			switch(usersChoice)
			{
				case "queen" :
					return bQ;
				case "king" :
					return bK;
			default : 
					System.out.println("I do not recognise that piece");
					//TODO DECIDE WHAT TO DO IF SAY WRONG PIECE NAME
					return null;
			}
		}
				
	}*/
	
	private static void changePlayer()
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
	
	private static boolean checkLocationIsOnBoard(int newRow, int newColumn)
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
	private static int convertToColumn(String str)
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
	
	private static int convertToRow(int i)
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
