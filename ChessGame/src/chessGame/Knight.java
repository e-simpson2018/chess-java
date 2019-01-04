package chessGame;

public class Knight extends Piece
{	
	
	public Knight(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "knight";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 6;		
		else		
			super.pieceId = 5;
	}
	

	//protected boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb)
	//{
		//return true;
	//}
	
	@Override
	protected boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		boolean isValidMove = false;
		System.out.println("inside knight movement");
		if(horizontalL(initialLocation, finalLocation) ||
			verticalL(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	private boolean horizontalL(Location initialLocation, Location finalLocation)
	{
		boolean flag1 = false;
		System.out.println("inside horizontalL");

		if(Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1)
		{
			if(initialLocation.getColumn() - finalLocation.getColumn() == 2)
			{
				flag1 = true;
			}
			else if(initialLocation.getColumn() - finalLocation.getColumn() == -2)
			{
				flag1 = true;
			}
		}
		
		return flag1;
	}
	
	private boolean verticalL(Location initialLocation, Location finalLocation)
	{
		boolean flag2 = false;
		System.out.println("inside verticalL");

		
		if(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1)
		{
			if(initialLocation.getRow() - finalLocation.getRow() == 2)
			{
				flag2 = true;
			}
			else if(initialLocation.getRow() - finalLocation.getRow() == -2)
			{
				flag2 = true;
			}
		}
		return flag2;
	}
	
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "N ";
	} 
}
