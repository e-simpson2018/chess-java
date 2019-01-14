package chessGame;

public class Pawn extends Piece
{	
	public Pawn(Colour newColour) 
	{
		super(newColour);
		super.pieceType = "pawn";
		if (newColour ==  Piece.Colour.WHITE)		
			super.pieceId = 8;		
		else		
			super.pieceId = 7;				
	}
	
	//TODO CHECK ALL THE OVERRIDES THAT THYE ARE, IN FACT OVERRIDES
	@Override
	protected boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb) 
	{
		boolean isValid = false;
		
		System.out.println("inside pawn movement");
		
		if(firstMove(initialLocation, finalLocation, cb) ||
			normalMove(initialLocation, finalLocation, cb)	||
			captureMove(initialLocation, finalLocation, cb))
		{
			isValid = true;
		}
		//TODO SOMETHING ELSE ABOUT PROMOTING PIECE?
		return isValid;
	}
	
	
	/**
	 * firstMove() allows for the following conditions:
	 * If it's a black piece on the starting row, it can move either one or two rows forward
	 * Same for white, but in the opposite direction
	 * No horizontal movement allowed
	 * The destination square cannot have a piece already there (regardless of colour)
	 * TODO NEED TO DECIDE WHETHER TO PASS PIECE INSTEAD OF USING CB
	 */
	private boolean firstMove(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		boolean flag1 = false;
		if(noHorizontalMovement(initialLocation, finalLocation) && isEmptySquare(initialLocation, finalLocation, cb))
		{
			if(this.getColour() == Piece.Colour.WHITE)
			{
				//check it's on first row and moves only 1 or 2 places forward
				if(initialLocation.getRow() == 6 && (finalLocation.getRow() == 5 || finalLocation.getRow() == 4))
				{
					flag1 = true;
				}
			}
			else 
			{
				if(initialLocation.getRow() == 1 && (finalLocation.getRow() == 2 || finalLocation.getRow() == 3))
				{
					flag1 = true;
				}
			}
		}
		System.out.println(" pawn first move comes back: " + flag1);
		return flag1;
	}
	
	private boolean noHorizontalMovement(Location initialLocation, Location finalLocation)
	{
		return (initialLocation.getColumn() - finalLocation.getColumn() == 0);
	}
	
	private boolean isEmptySquare(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		return (cb.board[finalLocation.getRow()][finalLocation.getColumn()] == null);
	}
	
	/**
	 * normalMove() allows for the following conditions:
	 * If it's a black piece after the starting row and before the end row, it can move one row forward
	 * Same for white, but in the opposite direction
	 * No horizontal movement allowed
	 * The destination square cannot have a piece already there (regardless of colour)
	 * TODO NEED TO DECIDE WHETHER TO PASS PIECE INSTEAD OF USING CB AND CHECK FOR PAWN PROMOTION
	 */
	private boolean normalMove(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		boolean flag2 = false;
		
		if(noHorizontalMovement(initialLocation, finalLocation) && isEmptySquare(initialLocation, finalLocation, cb))
		{
			if(this.getColour() == Piece.Colour.WHITE)
			{
				//check it's on first row and moves only 1 or 2 places forward
				if(initialLocation.getRow() < 6 && (initialLocation.getRow() - finalLocation.getRow() == 1))
				{
					flag2 = true;
				}
			}
			else 
			{
				if(initialLocation.getRow() > 1 && (initialLocation.getRow() - finalLocation.getRow() == -1))
				{
					flag2 = true;
				}
			}
		}
		System.out.println("pawn normal move comes back: " + flag2);
		return flag2;

	}
	
	/**
	 * captureMove() allows for the following conditions:
	 * If it's a black piece, it can move forward diagonally by one row
	 * Same for white, but in the opposite direction
	 * The destination square must have a piece already there (regardless of colour, will be dealt with in GameGUI)
	 * TODO NEED TO DECIDE WHETHER TO PASS PIECE INSTEAD OF USING CB AND CHECK FOR PAWN PROMOTION
	 */
	private boolean captureMove(Location initialLocation, Location finalLocation, ChessBoard cb)
	{		
		boolean flag3 = false;
		if(diagonalMovement(initialLocation, finalLocation) && !isEmptySquare(initialLocation, finalLocation, cb))
		{
			if(this.getColour() == Piece.Colour.WHITE && (initialLocation.getRow() - finalLocation.getRow() == 1))
			{
				//check it's on first row and moves only 1 or 2 places forward
				if(initialLocation.getRow() < 6 && (initialLocation.getRow() - finalLocation.getRow() == 1))
				{
					flag3 = true;
				}
			}
			else 
			{
				if(this.getColour() == Piece.Colour.BLACK && (initialLocation.getRow() - finalLocation.getRow() == -1))
				{
					flag3 = true;
				}
			}
		}
		System.out.println("pawn capture movement comes back: " + flag3);
		return flag3;
	
	}
		
	
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "P ";
	} 
}



