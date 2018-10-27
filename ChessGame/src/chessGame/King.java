package chessGame;

public class King extends Piece
{	
	public King(Location newSetUpLoc, Colour newColour) 
	{
		super(newSetUpLoc, newColour);
		super.pieceType = "king";
	}

	//TODO CHECK ALL THE OVERRIDES THAT THYE ARE, IN FACT OVERRIDES
	@Override
	protected boolean movePiece(Location initialLocation, Location finalLocation, ChessBoard cb) 
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
	
	@Override
	protected boolean verticalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isVertical = false;
		if(initialLocation.getRow() - finalLocation.getRow() == Math.abs(1) &&
			initialLocation.getColumn() - finalLocation.getColumn() == 0)
		{
			isVertical = true;
		}
		
		return isVertical;
	}
		
	protected boolean horizontalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isHorizontal = false;
		if(initialLocation.getColumn() - finalLocation.getColumn() == Math.abs(1) &&
				initialLocation.getRow() - finalLocation.getRow() == 0)
		{
			isHorizontal = true;
		}
		
		return isHorizontal;
	}
	
	protected boolean diagonalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isDiagonal = false;
		if(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1 &&
			Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1)
		{
			isDiagonal = true;
		}
		
		return isDiagonal;
	}
	
	
	
	
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "K ";
	} 
}



