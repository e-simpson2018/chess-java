package chessGame;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard 
{
	public static final int CHESSBOARD_WIDTH = 8;
	public static final int CHESSBOARD_LENGTH = 8;
	Piece[][] board;
	List<Piece> capturedWhite;
	List<Piece> capturedBlack;
	
	//Added for GUI, DELETE?
	public ChessBoard()
	{
		
	}
	
	public ChessBoard(Piece wQ, Piece bQ, Piece wK, Piece bK, Piece w1R, Piece w2R, Piece b1R, Piece b2R, Piece w1N, Piece w2N, Piece b1N,
						Piece b2N, Piece w1P, Piece w2P, Piece w3P, Piece w4P, Piece w5P, Piece w6P, Piece w7P, Piece w8P,
						Piece b1P, Piece b2P, Piece b3P, Piece b4P, Piece b5P, Piece b6P, Piece b7P, Piece b8P)
	{
		board = new Piece[CHESSBOARD_WIDTH][CHESSBOARD_LENGTH];
		capturedWhite = new ArrayList<>();
		capturedBlack = new ArrayList<>();
		setUp(wQ, bQ, wK, bK, w1R, w2R, b1R, b2R, w1N, w2N, b1N, b2N, w1P, w2P, w3P, w4P, w5P, w6P, w7P, w8P,
				b1P, b2P, b3P, b4P, b5P, b6P, b7P, b8P);
	}
	
	public void setUp(Piece wQ, Piece bQ, Piece wK, Piece bK, Piece w1R, Piece w2R, Piece b1R, Piece b2R, Piece w1N, Piece w2N, Piece b1N,
						Piece b2N, Piece w1P, Piece w2P, Piece w3P, Piece w4P, Piece w5P, Piece w6P, Piece w7P, Piece w8P,
						Piece b1P, Piece b2P, Piece b3P, Piece b4P, Piece b5P, Piece b6P, Piece b7P, Piece b8P)
	{
		board[0][4] = bQ;
		board[7][3] = wQ;
		board[0][3] = bK;
		board[7][4] = wK;
		board[0][0] = b1R;
		board[0][7] = b2R;
		board[7][0] = w1R;
		board[7][7] = w2R;
		board[0][1] = b1N;
		board[0][6] = b2N;
		board[7][1] = w1N;
		board[7][6] = w2N;
		board[6][0] = w1P;
		board[6][1] = w2P;
		board[6][2] = w3P;
		board[6][3] = w4P;
		board[6][4] = w5P;
		board[6][5] = w6P;
		board[6][6] = w7P;
		board[6][7] = w8P;
		board[1][0] = b1P;
		board[1][1] = b2P;
		board[1][2] = b3P;
		board[1][3] = b4P;
		board[1][4] = b5P;
		board[1][5] = b6P;
		board[1][6] = b7P;
		board[1][7] = b8P;


	}
	
	//TODO ADD COLUMN AND ROW TO PRINT NOTATION
	public void displayBoard()
	{
		for(int i = 0; i < CHESSBOARD_LENGTH; i++)
		{
			for(int j = 0; j < CHESSBOARD_WIDTH; j++)
			{

				System.out.print("    " + board[i][j] + "  \t|");
			}
			System.out.println();
		}
	}
	
	public Location getLocation(Piece piece)
	{
		for(int i = 0; i < CHESSBOARD_WIDTH; i++)
		{
			for(int j = 0; j < CHESSBOARD_LENGTH; j++)
			{
				if(board[i][j] == piece)
				{
					return new Location(i, j);
				}
			}
		}  
		return null;
	}
	
	
}
