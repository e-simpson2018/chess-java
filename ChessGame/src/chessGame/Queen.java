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
	

	protected boolean isPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		return true;
	}
	
	@Override
	protected boolean isPieceMovement(Location initialLocation, Location finalLocation) 
	{
		boolean isValidMove = false;
		
		if(verticalMovement(initialLocation, finalLocation) ||
			horizontalMovement(initialLocation, finalLocation) ||
			diagonalMovement(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "Q ";
	} 
}
