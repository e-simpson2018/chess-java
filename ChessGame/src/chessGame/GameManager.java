package chessGame;

import java.util.Scanner;

import chessGame.Piece.Colour;

public class GameManager 
{	
	private String player = "white";
	
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
		
	}
	
	public boolean isPiecePlayersColour(Piece piece)
	{
		return (piece.getColour().getPrintColourAsString().equals(player));
	}
	
	//TODO NEED A METHOD TO CHECK STATUS AFTER MOVING: CHECK OR CHECKMATE
	
	public boolean checkForCheckMate()
	{/*
		boolean inCheck = false;
		if(player == "black" && playersPiece.getPieceId() == )
		{
			
		}
		
		
		getLocation()
		findOpponentsKing();
		getKingsLocation();
		if(validPieceMovement())
		{
			inCheck = true;
		}
		
		
		if(inCheck)
		{
			opponentsKing.
		}*/
		
		
		
		
		return false;
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
	
	public void movePiece(Location initialPosition, Location pieceDestination, ChessBoard cb, Piece pieceToMove)
	{
		//Add piece to new location and delete from old location
		cb.board[pieceDestination.getRow()][pieceDestination.getColumn()] = pieceToMove;
		cb.board[initialPosition.getRow()][initialPosition.getColumn()] = null;	
		afterMoveChecks(pieceDestination, pieceToMove, cb);
	}
	
	private void afterMoveChecks(Location loc, Piece piece, ChessBoard cb)
	{
		//checkForCheckMate();
		//checkForPawnPromo(loc, piece, cb);
		//do i need changePlayer? It was working fine without it
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
	
	/*
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
	}*/
}
