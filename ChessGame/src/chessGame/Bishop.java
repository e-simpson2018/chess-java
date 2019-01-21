package chessGame;

import chessGame.Piece.Colour;

public class Bishop extends Piece
{	
	//Constructor
	public Bishop(Colour newColour) 
	{
		super(newColour, new DynamicDiagonalValidator(), init(newColour));
	}
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 2;		
		else		
			return 1;
	}
	
	//runMoveValidator(Location initialLocation, Location finalLocation) is taken care of in DynamicDiagonalValidator
	
	@Override
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "B ";
	} 
}


