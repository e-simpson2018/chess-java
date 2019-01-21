package chessGame;

public class KingValidator implements MoveValidator
{
	/**
	 * validateMove checks the move is either vertical, horizontal or diagonal, and that it is only one step
	 */
	@Override
	public boolean validateMove(Location initialLocation, Location finalLocation) 
	{
		boolean isValidMove = false;
		
		if(kingVertical(initialLocation, finalLocation) ||
				kingHorizontal(initialLocation, finalLocation) ||
				kingDiagonal(initialLocation, finalLocation))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	private boolean kingVertical(Location initialLocation, Location finalLocation)
	{
		if(ChessUtil.verticalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1))
		{
			return true;
		}
		return false;
	}
	
	private boolean kingHorizontal(Location initialLocation, Location finalLocation)
	{
		if(ChessUtil.horizontalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1))
		{
			return true;
		}
		return false;
	}
	
	private boolean kingDiagonal(Location initialLocation, Location finalLocation)
	{
		if(ChessUtil.diagonalMovement(initialLocation, finalLocation) &&
			(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) == 1))
		{
			return true;
		}
		return false;
	}
}
