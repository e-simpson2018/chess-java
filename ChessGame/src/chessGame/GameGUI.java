package chessGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

//THIS IS THE CLASS THAT HANDLES THE GAME
public class GameGUI implements ActionListener
{
	public Map<Integer, Image> dictImages = new HashMap<Integer, Image>();
	
	private JPanel gui = new JPanel(new BorderLayout(3,3));	
	private JButton[][] chessBoardSquares = new JButton[8][8]; 
	private JPanel chessBoard;
	private String COLS = "ABCDEFGH";
	private int boardsize = 8;
	
	private Color GREEN = new Color(0, 204, 0);
	private Color LIGHT_YELLOW = new Color (255, 255, 204);
	//private Color YELLOW = new Color (255, 255, 0);
	private Color LIGHT_BLUE = new Color (51, 153, 255);	
	
	//Create all the board pieces
	private Rook rookW1 = new Rook(Piece.Colour.WHITE);
	private Rook rookW2 = new Rook(Piece.Colour.WHITE);
	private Rook rookB1 = new Rook(Piece.Colour.BLACK);
	private Rook rookB2 = new Rook(Piece.Colour.BLACK);
	private Knight knightW1 = new Knight(Piece.Colour.WHITE);
	private Knight knightW2 = new Knight(Piece.Colour.WHITE);
	private Knight knightB1 = new Knight(Piece.Colour.BLACK);
	private Knight knightB2 = new Knight(Piece.Colour.BLACK);
	private Bishop bishopW1 = new Bishop(Piece.Colour.WHITE);
	private Bishop  bishopW2 = new Bishop(Piece.Colour.WHITE);
	private Bishop bishopB1 = new Bishop(Piece.Colour.BLACK);
	private Bishop bishopB2 = new Bishop(Piece.Colour.BLACK);
	private Queen queenW = new Queen(Piece.Colour.WHITE);
	private Queen queenB = new Queen(Piece.Colour.BLACK);
	private King kingW = new King(Piece.Colour.WHITE);
	private King kingB = new King(Piece.Colour.BLACK);
	private Pawn pawnW1 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW2 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW3 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW4 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW5 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW6 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW7 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnW8 = new Pawn(Piece.Colour.WHITE);
	private Pawn pawnB1 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB2 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB3 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB4 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB5 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB6 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB7 = new Pawn(Piece.Colour.BLACK);
	private Pawn pawnB8 = new Pawn(Piece.Colour.BLACK);
	
	public ChessBoard cb;
	private GameManager gm;	
	private boolean isFirstClick = true;
	private Location initLocation;
	private Piece pieceToMove;
	
	
	/**
	 * Constructor
	 */
	public GameGUI()
	{
		gm = new GameManager();
		loadPiecesImages();
		initializeGui();
		//gm.initializeGameManager();
	}
	
	
	
	public void initGame() 
	{
		//Create a new board passing in all pieces
		cb = new ChessBoard(queenW, queenB, kingW, kingB, rookW1, rookW2, rookB1, rookB2, bishopW1, bishopW2, bishopB1, bishopB2, knightW1, knightW2, knightB1, knightB2, pawnW1, pawnW2, pawnW3, pawnW4, pawnW5, pawnW6, pawnW7, pawnW8,
										pawnB1, pawnB2, pawnB3, pawnB4, pawnB5, pawnB6, pawnB7, pawnB8);
		//Debugging
		//cb.displayBoardConsole();
		
		// start gui and display the status of the board
		startGui();
		UpdateBoardImages(cb.getBoardStatus());
	}
			
	
	
	public void startGame()
	{
		do 
		{
			updateGameGui();
		}while(gm.gameNotOver);
	}
	
	
	
	public void updateGameGui()
	{
		UpdateBoardImages(cb.getBoardStatus());
	}
	
	
	
	/**
	 * Load the images of the chess pieces to a dictionary
	 */
	private void loadPiecesImages()
	{
		try 
		{
			dictImages.put(1, ImageIO.read(getClass().getResource("/bishop_black.png")));
			dictImages.put(2, ImageIO.read(getClass().getResource("/bishop_white.png")));
			dictImages.put(3, ImageIO.read(getClass().getResource("/king_black.png")));
			dictImages.put(4, ImageIO.read(getClass().getResource("/king_white.png")));
			dictImages.put(5, ImageIO.read(getClass().getResource("/knight_black.png")));
			dictImages.put(6, ImageIO.read(getClass().getResource("/knight_white.png")));
			dictImages.put(7, ImageIO.read(getClass().getResource("/pawn_black.png")));
			dictImages.put(8, ImageIO.read(getClass().getResource("/pawn_white.png")));
			dictImages.put(9, ImageIO.read(getClass().getResource("/queen_black.png")));
			dictImages.put(10, ImageIO.read(getClass().getResource("/queen_white.png")));
			dictImages.put(11, ImageIO.read(getClass().getResource("/rock_black.png")));
			dictImages.put(12, ImageIO.read(getClass().getResource("/rock_white.png")));
		} 
		catch (Exception ex) 
		{
			System.out.println("There was a problem when attempting to load the images...");
		    System.out.println(ex);
		}
	}	
	
	
	
	/**
	 * Initialize the GUI
	 */
	private void initializeGui()
	{	
		
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));		
		
		chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        
        
        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < chessBoardSquares.length; i++) 
        {
            for (int j = 0; j < chessBoardSquares[i].length; j++) 
            {
                JButton btn = new JButton();
                btn.setMargin(buttonMargin);
                // chess pieces are 64x64 pixels in size, so we'll 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                btn.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) 
                {
                    //b.setBackground(Color.WHITE);
                	btn.setBackground(LIGHT_YELLOW);
                } 
                else 
                {
                    //b.setBackground(Color.BLACK);
                	btn.setBackground(GREEN);
                }                
                chessBoardSquares[i][j] = btn;
                chessBoardSquares[i][j].addActionListener(this);
            }
        }
        
        // fill the chess board panel        
        chessBoard.add(new JLabel(""));		// empty label for the cell(0,0) of the GridLayout
        for (int i = 0; i < boardsize; i++) 
        {
        	// Add the letters to the columns
            chessBoard.add(new JLabel(COLS.substring(i, i + 1), SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int i = 0; i < boardsize; i++) 
        {
            for (int j = 0; j < boardsize; j++) 
            {
                switch (j) 
                {
                    case 0:
                    	// add numbers in the column of the GridLayout                        
                    	chessBoard.add(new JLabel("" + (8 - i), SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[i][j]);
                }
            }
        }        
	}	
	
	
	
	public void startGui()
	{
		JFrame frame = new JFrame("Chess Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 900);	
		frame.add(gui);
		frame.setVisible(true);
	}
	
	
	
	public Image getPieceImage(int pieceId)
	{
		try
		{
			return dictImages.get(pieceId);
		}
		catch (Exception ex)
		{
			System.out.println("The name of the piece was not found in the dictionary...");
			System.out.println(ex);
			return null;
		}
	}	
	
	
	public void drawPiece(int row, int col, Image img)	
	{
		// set image on a specific button from the matrix of buttons that represent the chess board
		chessBoardSquares[row][col].setIcon(new ImageIcon(img));		
	}
	
	
	// TODO This should be private, at the moment is being used for tests 
	//The matrix passed reflects the piece ids in their current positions
	public void UpdateBoardImages(int[][] matrix)	// [rows][columns]
	{
		for (int i = 0; i < boardsize; i++)		// rows
		{
			for (int j = 0; j < boardsize; j++)	// columns
			{
				// assuming the values of the pieces in the matrix is > 0
				if (matrix[i][j] > 0)
					drawPiece(i, j, getPieceImage(matrix[i][j]));
				else
					chessBoardSquares[i][j].setIcon(null);
			}
		}
	}	
	
	
	
	/**
	 *  Handle a button click.  
	 *  @param e   the event that triggered the method
	 */
	public void actionPerformed(ActionEvent e) 
	{
		JButton b = (JButton)e.getSource();
		int col = -1;
		int row = -1;

		// first find which button (board square) was clicked.
		for (int i = 0; i < boardsize; i++) 
		{
			for (int j = 0; j < boardsize; j++) 
			{
				if (chessBoardSquares[i][j] == b) 
				{
					//TODO change to matrix cols and rows??
					col = j;
					row = i;					  
				}	
			}
		}
		if (col != -1 && row != -1)			  
			CheckSquareClicked(row, col);		 
	}
	  
	  
	  
	/** This method should alternates between selecting a piece
	 *  and selecting any square.  After both are selected, the piece's 
	 *  legalMove should called, and if the move is legal, the matrix representing the board should be updated,
	 *  the board should be redrawn and the piece should appear moved.
	*/
	private void CheckSquareClicked(int row, int col)
	{
		if(isFirstClick)
		{
			System.out.println("inside first click. player: " + gm.player);
			//System.out.println("row of pieceToMove: " + row);
			//System.out.println("col of pieceToMove: " + col);
			if(cb.board[row][col] == null)
			{
				System.out.println("Please choose a square with a piece");
				return;
			}
			pieceToMove = cb.board[row][col];
			System.out.println("fristclick pieceToMove: " + pieceToMove);
			initLocation = new Location(row, col);
			System.out.println("fristclick initLocation: " + initLocation);

			if(!gm.isPiecePlayersColour(pieceToMove))
			{
				System.out.println("You cannot move your opponent's piece");
				//TODO make an exception for this event??
				return;
			}
			
			System.out.println("fristclick got past the if statement");

			chessBoardSquares[row][col].setBackground(LIGHT_BLUE);
			isFirstClick = false;
		}
		else
		{
			System.out.println("inside second click");

			Location finalLocation = new Location(row, col);
			
			if(pieceToMove.validPieceMovement(initLocation, finalLocation, cb))
			{
				System.out.println("MOVEMENT CONFIRMED. now to see if pieceOnDestination is null");
				Piece pieceOnDestination = cb.board[row][col];

				//check square to move to is empty
				if(pieceOnDestination == null)
				{
					gm.movePiece(initLocation, finalLocation, cb, pieceToMove);
				}
				else 
				{
					if(gm.isPiecePlayersColour(pieceOnDestination))
					{
						System.out.println("That move is not valid, you cannot land on your own piece");
						//TODO make an exception?
					}
					else
					{
						gm.capturePiece(pieceOnDestination, cb);
						gm.movePiece(initLocation, finalLocation, cb, pieceToMove);
						//TODO decide what to do if comes back as check
					}
				}
			}
			else
			{
				System.out.println("That move is not allowed, not valid movement");
				//TODO do something else?
			}
			
			
			//return square to original colour
			if ((initLocation.getColumn() % 2 == 1 && initLocation.getRow() % 2 == 1) || (initLocation.getColumn() % 2 == 0 && initLocation.getRow() % 2 == 0)) 
            {
				chessBoardSquares[initLocation.getRow()][initLocation.getColumn()].setBackground(LIGHT_YELLOW);
            } 
            else 
            {
            	chessBoardSquares[initLocation.getRow()][initLocation.getColumn()].setBackground(GREEN);
            }     
			
			System.out.println("got to end of second click");

			
			isFirstClick = true;
		}
	} 
}
