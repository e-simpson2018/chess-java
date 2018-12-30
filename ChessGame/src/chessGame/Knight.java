package chessGame;

public class Knight extends Piece
{	
	/*public Knight(Location newSetUpLoc, Colour newColour) 
	{
		super(newSetUpLoc, newColour);
		super.pieceType = "knight";
	}*/
	
	public Knight(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "knight";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 6;		
		else		
			super.pieceId = 5;
	}
	

	//protected boolean isPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb)
	//{
		//return true;
	//}
	
	@Override
	protected boolean isPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		boolean isValidMove = false;
		
		if(verticalMovement(initialLocation, finalLocation) ||
			horizontalMovement(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	@Override 
	protected boolean horizontalMovement(Location initialLocation, Location finalLocation)
	{
		
		boolean isHorizontal = false;
		if(Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1 &&
				Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 3)
		{
			isHorizontal = true;
		}
		
		return isHorizontal;
	}
	
	
	
	@Override 
	protected boolean verticalMovement(Location initialLocation, Location finalLocation)
	{
		
		boolean isVertical = false;
		if(Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 3 &&
				Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1)
		{
			isVertical = true;
		}
		
		return isVertical;
	}
	
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "N ";
	} 
}
