package chessGame;

public class DynamicDiagonalValidator implements MoveValidator 
{
	/**
	 * validateMove checks if the move is a diagonal movement
	 */
	@Override
	public boolean validateMove(Location initialLocation, Location finalLocation) 
	{
		boolean areValidSteps = false;
		
		if(ChessUtil.diagonalMovement(initialLocation, finalLocation))
		{
			areValidSteps = true;
		}
		return areValidSteps;
	}

}
