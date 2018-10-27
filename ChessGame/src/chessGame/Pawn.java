package chessGame;

public class Pawn extends Piece
{	
	public Pawn(Location newSetUpLoc, Colour newColour) 
	{
		super(newSetUpLoc, newColour);
		super.pieceType = "pawn";
	}
	
	//TODO CHECK ALL THE OVERRIDES THAT THYE ARE, IN FACT OVERRIDES
	@Override
	protected boolean movePiece(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		
		if(this.getColour() == Piece.Colour.WHITE)
		{
			return whiteVerticalMovement(initialLocation, finalLocation, cb);
		}
		else
		{
			return blackVerticalMovement(initialLocation, finalLocation, cb);
		}
	}
	
	protected boolean whiteVerticalMovement(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		boolean isVertical = false;
		
		//TODO CHECK IF I SHOULD NOT HARD CODE THE NUMBERS
		if(initialLocation.getRow() == 6 && (finalLocation.getRow() == 5 || finalLocation.getRow() == 4))
		{
			isVertical = true;
		}
		else if((initialLocation.getRow() < 6 && initialLocation.getRow() > 0) &&
				initialLocation.getRow() - finalLocation.getRow() == 1)
		{
			isVertical = true;
		}
		else if(cb.board[(initialLocation.getRow() - 1)][(initialLocation.getColumn() + 1)].getColour().getPrintColourAsString() == "black"
				|| cb.board[(initialLocation.getRow() - 1)][(initialLocation.getColumn() - 1)].getColour().getPrintColourAsString() == "black")
		{
			isVertical = true;
		}
		//TODO SOMETHING ELSE ABOUT PROMOTING PIECE?
		return isVertical;
	}
		
	
	protected boolean blackVerticalMovement(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		boolean isVertical = false;
		
		//TODO CHECK IF I SHOULD NOT HARD CODE THE NUMBERS
		if(initialLocation.getRow() == 1 && (finalLocation.getRow() == 2 || finalLocation.getRow() == 3))
		{
			isVertical = true;
		}
		else if((initialLocation.getRow() > 1 && initialLocation.getRow() < 7) &&
				finalLocation.getRow() - initialLocation.getRow() == 1)
		{
			isVertical = true;
		}
		else if(cb.board[(initialLocation.getRow() + 1)][(initialLocation.getColumn() + 1)].getColour().getPrintColourAsString() == "white"
				|| cb.board[(initialLocation.getRow() + 1)][(initialLocation.getColumn() - 1)].getColour().getPrintColourAsString() == "white")
		{
			isVertical = true;
		}
		//TODO SOMETHING ELSE ABOUT PROMOTING PIECE?
		return isVertical;
	}
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "P ";
	} 
}



