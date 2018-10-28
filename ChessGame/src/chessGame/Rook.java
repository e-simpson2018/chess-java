package chessGame;

public class Rook extends Piece
{	
	public Rook(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "rook";
		
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 12;		
		else		
			super.pieceId = 11;
	}	
	
	

	@Override
	protected boolean movePiece(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		boolean isValidMove = false;
		
		if(verticalMovement(initialLocation, finalLocation) ||
			horizontalMovement(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "R ";
	} 
}


