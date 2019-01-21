package chessGame;

public class PawnValidator implements MoveValidator 
{
	private GameManager gm;
	
	public PawnValidator()
	{
		gm = new GameManager();
	}
	
	/**
	 * This method needs to check for the 3 possible moves a pawn can make:
	 * 1) the first move and be one or two steps
	 * 2) any move afterwards can only move one step
	 * 3) to capture a piece, it must move one step diagonally
	 */
	@Override
	public boolean validateMove(Location initialLocation, Location finalLocation) 
	{
		boolean isValid = false;
		int rowDifference = initialLocation.getRow() - finalLocation.getRow();
		
		if(firstOrNormalMove(initialLocation, finalLocation, rowDifference) || captureMove(initialLocation, finalLocation, rowDifference))
		{
			isValid = true;
		}
		//TODO SOMETHING ELSE ABOUT PROMOTING PIECE?
		return isValid;
	}
	
	
	/**
	 * firstOrNormalMove checks the if it is a valid pawn first move or normal move
	 * @param initialLocation origin of piece
	 * @param finalLocation destination of piece
	 * @param rowDifference difference between initial and final row
	 * @return true if it adheres to pawn's first or normal rules
	 */
	private boolean firstOrNormalMove(Location initialLocation, Location finalLocation, int rowDifference)
	{
		boolean moveOK = false;
		boolean playerWhite = (("white").equals(GameManager.player));
		
		//If pawns are moving vertically, they cannot land on another piece
		if(noHorizontalMovement(initialLocation, finalLocation) && isEmptySquare(initialLocation, finalLocation))
		{
			if(playerWhite)
			{
				//for white's firstmove, it must be on row 6, and end on 4 or 5. normalMove has to be less than row 6 and only move 1 step
				if(firstMove(initialLocation, finalLocation, 6, 4, 5) || normalMove(playerWhite, initialLocation, rowDifference, 6, 1))
				{
					moveOK = true;
				}
			}
			else 
			{
				//for black's firstmove, it must be on row 1, and end on 2 or 3. normalMove has to be more than row 1 and only move 1 step
				if(firstMove(initialLocation, finalLocation, 1, 2, 3) || normalMove(playerWhite, initialLocation, rowDifference, 1, -1))
				{
					moveOK = true;
				}
			}
		}
		return moveOK;
	}
	
		
	/**
	 * firstMove() allows for the following conditions:
	 * If it's a black piece on the starting row, it can move either one or two rows forward
	 * Same for white, but in the opposite direction
	 * No horizontal movement allowed
	 * The destination square cannot have a piece already there (regardless of colour)
	 * @param initialLocation
	 * @param finalLocation
	 * @param init the starting row
	 * @param oneStep ending row if one step
	 * @param twoStep ending row if two steps
	 * @return true if piece adheres to all the piece's rules
	 */
	private boolean firstMove(Location initialLocation, Location finalLocation, int init, int oneStep, int twoStep)
	{
		if(initialLocation.getRow() == init && (finalLocation.getRow() == oneStep || finalLocation.getRow() == twoStep))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * normalMove allows for the following conditions:
	 * The piece can only move one step forward
	 * No horizontal movement allowed
	 * @param playerWhite player's colour
	 * @param initialLocation origin of the move
	 * @param rowDifference Difference between initial row and destination row
	 * @param init origin row
	 * @param oneStep takes into account directo of the colour
	 * @return true if move adheres to the piece's rules
	 */
	//TODO TAKE OUT ONESTEP??
	private boolean normalMove(boolean playerWhite, Location initialLocation, int rowDifference, int init, int oneStep)
	{
		if(rowDifference == oneStep)
		{
			//Checks colour and the direction
			if((playerWhite && (initialLocation.getRow() < init)) || ((!playerWhite) && (initialLocation.getRow() > init)))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * captureMove() allows for the following conditions:
	 * If it's a black piece, it can move forward diagonally by one row
	 * Same for white, but in the opposite direction
	 * The destination square must have a piece already there (regardless of colour, will be dealt with in GameGUI)
	 * TODO check if I should take care of colour of capture piece here
	 */
	private boolean captureMove(Location initialLocation, Location finalLocation, int rowDifference)
	{		
		boolean flag3 = false; //flag
		//check movement is diagonal, it is landing on a piece, and is only moving one square
		if(ChessUtil.diagonalMovement(initialLocation, finalLocation) && !isEmptySquare(initialLocation, finalLocation) &&
				(Math.abs(rowDifference) == 1))
		{
			//Check the direction of movement based on colour
			if((gm.getPlayer().equals("white") && rowDifference > 0) || (!gm.getPlayer().equals("white") && rowDifference < 0))
			{
				flag3 = true;
			}
		}
		return flag3;
	}
	
	private boolean noHorizontalMovement(Location initialLocation, Location finalLocation)
	{
		return (initialLocation.getColumn() - finalLocation.getColumn() == 0);
	}
	
	private boolean isEmptySquare(Location initialLocation, Location finalLocation)
	{
		return (ChessBoard.board[finalLocation.getRow()][finalLocation.getColumn()] == null);
	}

}
