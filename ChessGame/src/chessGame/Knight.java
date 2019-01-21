package chessGame;

import chessGame.Piece.Colour;

public class Knight extends Piece
{	
	//Constructor
	public Knight(Colour newColour) 
	{
		super(newColour, new KnightValidator(), init(newColour));
	}
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 6;		
		else		
			return 5;
	}

	//runMoveValidator(Location initialLocation, Location finalLocation) is taken care of in KnightValidator
	
	@Override
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "N ";
	} 
}
