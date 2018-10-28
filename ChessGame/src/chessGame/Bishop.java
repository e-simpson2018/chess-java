package chessGame;

public class Bishop extends Piece
{	
	/*public Bishop(Location newSetUpLoc, Colour newColour) 
	{
		super(newSetUpLoc, newColour);
		super.pieceType = "rook";
	}*/
	
	public Bishop(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "bishop";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 2;		
		else		
			super.pieceId = 1;
	}
	

	@Override
	protected boolean movePiece(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		boolean isValidMove = false;
		
		if(diagonalMovement(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "B ";
	} 
}


