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
		
	
	public ChessBoard(Piece queenW, Piece queenB, Piece kingW, Piece kingB, Piece rookW1, Piece rookW2, Piece rookB1, Piece rookB2, Piece bishopW1, Piece bishopW2, Piece bishopB1, Piece bishopB2, Piece knightW1, Piece knightW2, Piece knightB1,
						Piece knightB2, Piece pawnW1, Piece pawnW2, Piece pawnW3, Piece pawnW4, Piece pawnW5, Piece pawnW6, Piece pawnW7, Piece pawnW8,
						Piece pawnB1, Piece pawnB2, Piece pawnB3, Piece pawnB4, Piece pawnB5, Piece pawnB6, Piece pawnB7, Piece pawnB8)
	{
		board = new Piece[CHESSBOARD_WIDTH][CHESSBOARD_LENGTH];
		capturedWhite = new ArrayList<>();
		capturedBlack = new ArrayList<>();
		setUp(queenW, queenB, kingW, kingB, rookW1, rookW2, rookB1, rookB2, bishopW1, bishopW2, bishopB1, bishopB2, knightW1, knightW2, knightB1, knightB2, pawnW1, pawnW2, pawnW3, pawnW4, pawnW5, pawnW6, pawnW7, pawnW8,
				pawnB1, pawnB2, pawnB3, pawnB4, pawnB5, pawnB6, pawnB7, pawnB8);
	}
	
	public void setUp(Piece queenW, Piece queenB, Piece kingW, Piece kingB, Piece rookW1, Piece rookW2, Piece rookB1, Piece rookB2, Piece bishopW1, Piece bishopW2, Piece bishopB1, Piece bishopB2, Piece knightW1, Piece knightW2, Piece knightB1,
						Piece knightB2, Piece pawnW1, Piece pawnW2, Piece pawnW3, Piece pawnW4, Piece pawnW5, Piece pawnW6, Piece pawnW7, Piece pawnW8,
						Piece pawnB1, Piece pawnB2, Piece pawnB3, Piece pawnB4, Piece pawnB5, Piece pawnB6, Piece pawnB7, Piece pawnB8)
	{
		board[0][4] = queenB;
		board[7][3] = queenW;
		board[0][3] = kingB;
		board[7][4] = kingW;
		board[0][0] = rookB1;
		board[0][7] = rookB2;
		board[7][0] = rookW1;
		board[7][7] = rookW2;
		board[0][1] = knightB1;
		board[0][6] = knightB2;		
		board[7][1] = knightW1;
		board[7][6] = knightW2;
		board[7][2] = bishopW1;
		board[7][5] = bishopW2;
		board[0][2] = bishopB1;
		board[0][5] = bishopB2;		
		board[6][0] = pawnW1;
		board[6][1] = pawnW2;
		board[6][2] = pawnW3;
		board[6][3] = pawnW4;
		board[6][4] = pawnW5;
		board[6][5] = pawnW6;
		board[6][6] = pawnW7;
		board[6][7] = pawnW8;
		board[1][0] = pawnB1;
		board[1][1] = pawnB2;
		board[1][2] = pawnB3;
		board[1][3] = pawnB4;
		board[1][4] = pawnB5;
		board[1][5] = pawnB6;
		board[1][6] = pawnB7;
		board[1][7] = pawnB8;
	}
	
	public void displayBoardConsole()
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
	
	
	public int[][] getBoardStatus()
	{
		int[][] matrix = new int[CHESSBOARD_WIDTH][CHESSBOARD_LENGTH]; 
		
		for(int i = 0; i < CHESSBOARD_WIDTH; i++)
		{
			for(int j = 0; j < CHESSBOARD_LENGTH; j++)
			{				
				if(board[i][j] != null)
				{					
					matrix[i][j] = board[i][j].getPieceId();					
				}
				else
				{
					matrix[i][j] = 0;
				}
			}
		}
		
		return matrix;
	}
	
	
}
