package chessGame;

public class King extends Piece
{	
	/*public King(Location newSetUpLoc, Colour newColour) 
	{
		super(newSetUpLoc, newColour);
		super.pieceType = "king";
	}*/
	
	public King(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "king";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 4;		
		else		
			super.pieceId = 3;
	}
	
	//TODO CHECK ALL THE OVERRIDES THAT THYE ARE, IN FACT OVERRIDES
	@Override
	protected boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		System.out.println("inside king movement");
		boolean isValidMove = false;
		
		if(kingVertical(initialLocation, finalLocation) ||
				kingHorizontal(initialLocation, finalLocation) ||
				kingDiagonal(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	private boolean kingVertical(Location initialLocation, Location finalLocation)
	{
		boolean isKingVertical = false;
		
		if(verticalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1))
		{
			isKingVertical = true;
		}
		
		return isKingVertical;
	}
	
	private boolean kingHorizontal(Location initialLocation, Location finalLocation)
	{
		boolean isKingHorizontal = false;
		
		if(horizontalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1))
		{
			isKingHorizontal = true;
		}
		
		return isKingHorizontal;
	}
	
	private boolean kingDiagonal(Location initialLocation, Location finalLocation)
	{
		boolean isKingDiagonal = false;
		
		if(diagonalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1))
		{
			isKingDiagonal = true;
		}
		
		return isKingDiagonal;
	}
	
	
	
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "K ";
	} 
}



