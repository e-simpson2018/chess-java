package chessGame;

public abstract class Piece 
{
	enum Colour 
	{	
		BLACK("black"), 
		WHITE("white");
		
		
		//TODO CHECK IF I NEED ALL THIS STUFF IN ENUM
		private String printString;
		
		private Colour(String printString) 
		{
			this.printString = printString;
		}
		
		public String getPrintColourAsString() 
		{ 
			return printString; 
		}
	}
	
	private Colour colour;
	protected String pieceType;
	protected int pieceId;
	//added for the gui - in constructor too
	protected Location setUpLocation;	
	

	
	public Piece(Colour newColour)
	{
		colour = newColour;
		//setUpLocation = newSetUpLoc;
	}
	
	//IS THIS THE RIGHT GETTER FOR AN EMUN??
	public Colour getColour()
	{
		return colour;
	}
	
	public void setColour(Colour newColour)
	{
		colour = newColour;
	}
	
	public Location getSetUpLocation()
	{
		return setUpLocation;
	}
	
	public void setSetUpLocation(Location newSetUpLoc)
	{
		setUpLocation = newSetUpLoc;
	}
	
	public String getPieceType()
	{
		return pieceType;
	}
	
	public int getPieceId()
	{
		return pieceId;
	}
	
	//TODO CHECK IF THERE IS A STRATEGY TO NOT PASS CHESSBOARD HERE BECAUSE PAWN IS THE ONLY ONE THAT NEEDS IT
	protected abstract boolean movePiece(Location initialLocation, Location finalLocation, ChessBoard cb);
	
	
	//TODO CHECK STRATEGY ON THESE METHODS
	protected boolean verticalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isVertical = false;
		if((initialLocation.getRow() - finalLocation.getRow() != 0) && 
				(initialLocation.getColumn() - finalLocation.getColumn() == 0))
		{
			isVertical = true;
		}
		
		return isVertical;
	}
		
	protected boolean horizontalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isHorizontal = false;
		if((initialLocation.getColumn() - finalLocation.getColumn() != 0) && 
				(initialLocation.getRow() - finalLocation.getRow() == 0))
		{
			isHorizontal = true;
		}
		
		return isHorizontal;
	}
	
	protected boolean diagonalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isDiagonal = false;
		if((Math.abs(((initialLocation.getColumn() - finalLocation.getColumn()) /
				(initialLocation.getRow() - finalLocation.getRow()))) == 1))
		{
			isDiagonal = true;
		}
		
		return isDiagonal;
	}
}
