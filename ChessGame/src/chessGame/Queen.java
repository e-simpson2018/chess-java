package chessGame;

public class Queen extends Piece
{
	//2nd MoveValidator
	private MoveValidator mv2;
	
	//Constructor
	public Queen(Colour newColour) 
	{
		super(newColour, new DynamicDiagonalValidator(), init(newColour));
		this.mv2 = new DynamicStraightValidator();
	}
	
	//Set pieceId to pass to super constructor
	public static int init(Colour newColour)
	{
		if (newColour ==  Piece.Colour.WHITE)		
			return 10;		
		else		
			return 9;
	}
	
	/**
	 * runMoveValidator uses 2 MoveValidators (only piece to do so) to check for both diagonal and horizontal/vertical movements
	 * It also checks nothing is in between 
	 */
	@Override
	public boolean runMoveValidator(Location initialLocation, Location finalLocation)
	{
		boolean isValidMove = false;
		//TODO check syntax this.getMV()??????
		if(this.getMV().validateMove(initialLocation, finalLocation) || mv2.validateMove(initialLocation, finalLocation))
		{
			if(checkNothingInbetween(initialLocation, finalLocation))
			{
				isValidMove = true;
			}
		}
		return isValidMove;
	}
	
	@Override
	public String toString() 
	{ 
		//TODO UPDATE
	    return super.getColour() + "Q ";
	} 
}
