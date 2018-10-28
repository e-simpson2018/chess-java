package chessGame;

import java.awt.Image;
import javax.swing.SwingUtilities;




public class Main 
{

	private static GameManager gm;
	
	
	
	public static void main(String[] args) 
	{
		gm = new GameManager();		
		gm.initGame();
		gm.StartGame();
		
	}
}