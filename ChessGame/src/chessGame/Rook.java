package chessGame;

import chessGame.Piece.Colour;

public class Rook extends Piece
{	
	public Rook(Colour newColour) 
	{
		super(newColour, new DynamicStraightValidator(), init(newColour));
	}	
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 12;		
		else		
			return 11;
	}

	//runMoveValidator(Location initialLocation, Location finalLocation) taken care of in DynamicStraightValidator
	
	@Override
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "R ";
	} 
}


