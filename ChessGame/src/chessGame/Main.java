package chessGame;

public class Main 
{

	private static GameGUI gg;
	
	public static void main(String[] args) 
	{
		gg = new GameGUI();		
		gg.initGame();
		gg.startGame();
		
	}
}