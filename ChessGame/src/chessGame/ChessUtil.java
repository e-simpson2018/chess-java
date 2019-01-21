package chessGame;

public final class ChessUtil 
{
	//Constructor
	private ChessUtil() 
	{
		
	}
	
	/**
	 * This static method checks for vertical movement
	 * @param initialLocation
	 * @param finalLocation
	 * @return true if move is vertical
	 */
	public static boolean verticalMovement(Location initialLocation, Location finalLocation)
	{
		//vertical movement and no horizontal movement
		if((initialLocation.getRow() - finalLocation.getRow() != 0) && 
				(initialLocation.getColumn() - finalLocation.getColumn() == 0))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This static method checks for horizontal movement
	 * @param initialLocation
	 * @param finalLocation
	 * @return true if horizontal movement
	 */
	public static boolean horizontalMovement(Location initialLocation, Location finalLocation)
	{
		//horizontal movement and no vertical movement
		if((initialLocation.getColumn() - finalLocation.getColumn() != 0) && 
				(initialLocation.getRow() - finalLocation.getRow() == 0))
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * This static method checks for diagonal movements
	 * @param initialLocation
	 * @param finalLocation
	 * @return true if move is diagonal
	 */
	public static boolean diagonalMovement(Location initialLocation, Location finalLocation)
	{
		boolean isDiagonal = false;

		//TODO best way to deal with if divisible by 0. create an exception?
		if((initialLocation.getColumn() - finalLocation.getColumn() == 0) || (initialLocation.getRow() - finalLocation.getRow() == 0))
		{
			return false;
		}
		
		//checks that the absolute value of the horizontal steps / vertical steps equal 1
		if(Math.abs(initialLocation.getColumn() - finalLocation.getColumn()) /
				Math.abs(initialLocation.getRow() - finalLocation.getRow()) == 1)
		{
			isDiagonal = true;
		}
		return isDiagonal;
	}
	
	
	
	
}
