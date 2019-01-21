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
	
	private MoveValidator mv;
	private Colour colour;
	private int pieceId;
	//TODO make private
	public int temp1;
	public int temp2;
	public int temp3;
	public int temp4;

	//Constructor
	public Piece(Colour colour, MoveValidator mv, int pieceId)
	{
		this.colour = colour;
		this.mv = mv;	
		this.pieceId = pieceId;
	}
	
	//Getters
	public Colour getColour()
	{
		return colour;
	}
	
	public int getPieceId()
	{
		return pieceId;
	}
	
	public MoveValidator getMV()
	{
		return mv;
	}
	
	/**
	 * Different pieces have a Validator with their specific moves
	 * First check that the shape of the move is correct with validateMove()
	 * Then check that there are no pieces in the way (no jumps allowed - apart from Knight)
	 */
	public boolean runMoveValidator(Location initialLocation, Location finalLocation)
	{
		boolean isValidMove = false; //flag
		int id = this.getPieceId();
		if(mv.validateMove(initialLocation, finalLocation))
		{
			//ids of pieces that need to have squares in between checked:
			//bishop, rook, pawn(7Black and 8White, firstmove only) - queen is checked in its own overridden method
			//TODO IMPLEMENT THE FIRSTMOVE PART
			if(id == 1 || id == 2 || id == 7 || id == 8 || id == 11 || id == 12)
			{
				//Check if there are any pieces in the way
				if(checkNothingInbetween(initialLocation, finalLocation))
				{
					isValidMove = true;
				}
			}
			else
			{
				isValidMove = true;
			}
		}
		return isValidMove;
	}
	
	
	/**
	 * checkNothingInbetween() first sets temporary variables so that the opposite directions of the colours are accounted for 
	 * It then checks the direction the piece is moving in, and loops through the in between squares looking for a non-null space
	 */
	public boolean checkNothingInbetween(Location initialLocation, Location finalLocation)
	{
		boolean nothingInbetween = true;
		//Set temporary variables that account for different directions and return type of move
		String moveType = setTempVars(initialLocation, finalLocation);
		
		//Loop through in-between squares
		int j = temp3;
		for(int i = temp1; i < temp2; i++)
		{
			if(moveType.equals("vertical"))
			{
				//checkWhatIsOnSquare returns false if there is a piece on a square that is not first or last square
				if(!(checkWhatIsOnSquare(initialLocation, finalLocation, i, initialLocation.getColumn())))
				{
					return false;
				}
			}
		
			else if(moveType.equals("horizontal"))
			{
				if(!(checkWhatIsOnSquare(initialLocation, finalLocation, initialLocation.getRow(), i)))
				{
					return false;
				}
			}
			else if(moveType.equals("diagonal"))
			{
				if(!(checkWhatIsOnSquare(initialLocation, finalLocation, i, j)))
				{
					return false;
				}
			}
			j = j + temp4;
		}
		System.out.println("nothingInbetween: " + nothingInbetween);	
		return nothingInbetween;
	}
	
	/**
	 * checkWhatIsOnSquare checks if a square (that cannot be the first or last in the move) has a piece on it
	 * returns false if there is a piece that meets these requirements
	 */
	public boolean checkWhatIsOnSquare(Location initialLocation, Location finalLocation, int row, int col)
	{
		if(ChessBoard.board[row][col] != null && (row != initialLocation.getRow()) && (row != finalLocation.getRow()))
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * setTempVars juggles the variables and sets them so they can be used in other methods without
	 *  worrying about direction of the piece
	 *  it returns the shape of the move (vertical, horizontal, diagonal)
	 */
	public String setTempVars(Location initialLocation, Location finalLocation)
	{
		if(ChessUtil.verticalMovement(initialLocation, finalLocation))
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
			return "vertical";
		}
		else if(ChessUtil.horizontalMovement(initialLocation, finalLocation))
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
			return "horizontal";
		}
		else if(ChessUtil.diagonalMovement(initialLocation, finalLocation))
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
			return "diagonal";
		}
		//TODO SET CHECK FOR EMPTY STRING
		return "";
	}
}
