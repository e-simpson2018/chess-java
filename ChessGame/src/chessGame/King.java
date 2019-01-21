package chessGame;

import chessGame.Piece.Colour;

public class King extends Piece
{	
	//Constructor
	public King(Colour newColour) 
	{
		super(newColour, new KingValidator(), init(newColour));
	}
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 4;		
		else		
			return 3;
	}
	
	//runMoveValidator(Location initialLocation, Location finalLocation) is taken care of in KingValidator
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "K ";
	} 
}



