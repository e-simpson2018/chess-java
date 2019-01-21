package chessGame;

public class DynamicStraightValidator implements MoveValidator 
{
	/**
	 * This method checks for vertical and horizontal movement
	 */
	@Override
	public boolean validateMove(Location initialLocation, Location finalLocation) 
	{
		boolean areValidSteps = false;
		
		if(ChessUtil.horizontalMovement(initialLocation, finalLocation) || ChessUtil.verticalMovement(initialLocation, finalLocation))
		{
			areValidSteps = true;
		}
		return areValidSteps;
	}

}
