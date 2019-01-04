package chessGame;

public class Queen extends Piece
{
	
	public Queen(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "queen";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 10;		
		else		
			super.pieceId = 9;
	}
	
	@Override
	protected boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		System.out.println("inside queen piecemovement");

		boolean isValidMove = false;
		
		if(verticalMovement(initialLocation, finalLocation) ||
			horizontalMovement(initialLocation, finalLocation) ||
			diagonalMovement(initialLocation, finalLocation))
		{
			System.out.println("inside allthemoves");

			if(checkNothingInbetween(initialLocation, finalLocation, cb))
			{
				isValidMove = true;
			}
		}
		return isValidMove;
	}
	
	
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "Q ";
	} 
}
