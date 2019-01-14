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
	public int temp1;
	public int temp2;
	public int temp3;
	public int temp4;

	//Getters and setters
	
	public Piece(Colour newColour)
	{
		colour = newColour;
	}
	
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
	
	//TODO REDO THESE METHODS
	//TODO CHECK IF THERE IS A STRATEGY TO NOT PASS CHESSBOARD HERE BECAUSE PAWN IS THE ONLY ONE THAT NEEDS IT
	//protected abstract boolean validPieceMovement(Location initialLocation, Location finalLocation);
	
	//ONLY PAWNS USE THE CB
	protected abstract boolean validPieceMovement(Location initialLocation, Location finalLocation, ChessBoard cb);
	
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

		if(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) /
				Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1)
		{

			isDiagonal = true;
		}
		return isDiagonal;
	}
	
	public void setTempVars(Location initialLocation, Location finalLocation)
	{
		if(verticalMovement(initialLocation, finalLocation))
		{

			if(initialLocation.getRow() < finalLocation.getRow())
			{
				temp1 = initialLocation.getRow();
				temp2 = finalLocation.getRow();
			}
			else 
			{
				temp2 = initialLocation.getRow();
				temp1 = finalLocation.getRow();
			}
		}
		
		else if(horizontalMovement(initialLocation, finalLocation))
		{
			if(initialLocation.getColumn() < finalLocation.getColumn())
			{
				temp1 = initialLocation.getColumn();
				temp2 = finalLocation.getColumn();
			}
			else 
			{
				temp2 = initialLocation.getColumn();
				temp1 = finalLocation.getColumn();
			}
		}
		
		else if(diagonalMovement(initialLocation, finalLocation))
		{
			if(initialLocation.getRow() < finalLocation.getRow() && initialLocation.getColumn() < finalLocation.getColumn())
			{
				temp1 = initialLocation.getRow();
				temp2 = finalLocation.getRow();
				temp3 = initialLocation.getColumn();
				temp4 = 1;
			}
			else if (finalLocation.getRow() < initialLocation.getRow() && initialLocation.getColumn() < finalLocation.getColumn())
			{
				temp1 = finalLocation.getRow();
				temp2 = initialLocation.getRow();
				temp3 = finalLocation.getColumn();
				temp4 = -1;
			}
			else if(finalLocation.getRow() < initialLocation.getRow() && finalLocation.getColumn() < initialLocation.getColumn())
			{
				temp1 = finalLocation.getRow();
				temp2 = initialLocation.getRow();
				temp3 = finalLocation.getColumn();
				temp4 = 1;
			}
			else if(initialLocation.getRow() < finalLocation.getRow() && finalLocation.getColumn() < initialLocation.getColumn())
			{
				temp1 = initialLocation.getRow();
				temp2 = finalLocation.getRow();
				temp3 = initialLocation.getColumn();
				temp4 = -1;
			}
		}
		System.out.println("temp vars set");
	}
	
	public boolean checkNothingInbetween(Location initialLocation, Location finalLocation, ChessBoard cb)
	{
		boolean nothingInbetween = true;
		
		setTempVars(initialLocation, finalLocation);
		
		if(verticalMovement(initialLocation, finalLocation))
		{
			for(int i = temp1 + 1; i < temp2; i++)
			{
				if(cb.board[i][initialLocation.getColumn()] != null)
				{
					nothingInbetween = false;
				}
			}
		}
		
		else if(horizontalMovement(initialLocation, finalLocation))
		{
			for(int i = temp1 + 1; i < temp2; i++)
			{
				if(cb.board[initialLocation.getRow()][i] != null)
				{
					nothingInbetween = false;
				}
			}
		}
		
		else if(diagonalMovement(initialLocation, finalLocation))
		{
			int j = temp3;
			
			for(int i = temp1; i < temp2; i++)
			{
				System.out.println("checking inbtween square: " + cb.board[i][j]);
				if((cb.board[i][j] != null) && (i != initialLocation.getRow() && j != initialLocation.getColumn()) && (i != finalLocation.getRow() && j != finalLocation.getColumn()))
				{
					
					return false;
				}
				j = j + temp4;

			}
		}
		System.out.println("nothingInbetween: " + nothingInbetween);	
		return nothingInbetween;
	}
}
