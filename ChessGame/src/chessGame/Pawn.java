package chessGame;

import chessGame.Piece.Colour;

public class Pawn extends Piece
{	
	public Pawn(Colour newColour) 
	{
		super(newColour, new PawnValidator(), init(newColour));			
	}
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 8;		
		else		
			return 7;
	}

	//runMoveValidator(Location initialLocation, Location finalLocation) is taken care of in PawnValidator
		
	@Override
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "P ";
	} 
}



