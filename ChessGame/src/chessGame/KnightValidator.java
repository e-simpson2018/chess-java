package chessGame;

public class KnightValidator implements MoveValidator 
{
	//TODO check if it's ok to do all this other stuff in the class using this strategy
	private int rowDifference;
	private int columnDifference;
	
	
	/**
	 * validateMove checks for horizontal L shapes and vertical L shapes - 2 steps in one direction and 1 perpendicular, vice versa
	 */
	@Override
	public boolean validateMove(Location initialLocation, Location finalLocation) 
	{
		rowDifference = initialLocation.getRow() - finalLocation.getRow();
		columnDifference = initialLocation.getColumn() - finalLocation.getColumn();
		boolean isValidMove = false; //flag
		
		if(horizontalL(rowDifference, columnDifference) || verticalL(rowDifference, columnDifference))
		{
			isValidMove = true;
		}
		return isValidMove;
	}
	
	private boolean horizontalL(int rowDifference, int columnDifference)
	{
		if(Math.abs(rowDifference) == 1)
		{
			if(Math.abs(columnDifference) == 2)
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean verticalL(int rowDifference, int columnDifference)
	{
		if(Math.abs(columnDifference) == 1)
		{
			if(Math.abs(rowDifference) == 2)
			{
				return true;
			}
		}
		return false;
	}
}
