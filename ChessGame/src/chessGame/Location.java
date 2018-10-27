package chessGame;

public class Location 
{
	private int row, column;
	
	Location(int newRow, int newColumn)
	{
		row = newRow;
		column = newColumn;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
}
