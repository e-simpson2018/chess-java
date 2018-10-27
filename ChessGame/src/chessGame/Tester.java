package chessGame;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Tester extends Application {

	//TODO CHECK IMAGE RESOURCES!!!!!!!!!!
	//change the strings to reflect real images
    public String B_Rook = "File:Images\\blackRook.png";
    public String B_Knight = "File:Images\\blackKnight.png";
    public String B_Bishop = "File:Images\\blackBishop.png";
    public String B_Queen = "File:Images\\blackQueen.png";
    public String B_king = "File:Images\\blackKing.png";
    public String B_Pawn = "File:Images\\blackPawn.png";
    //black pieces
    public String W_Rook = "File:Images\\whiteRook.png";
    public String W_Knight = "File:Images\\whiteKnight.png";
    public String W_Bishop = "File:Images\\whiteBishop.png";
    public String W_Queen = "File:Images\\whiteQueen.png";
    public String W_king = "File:Images\\whiteKing.png";
    public String W_Pawn = "File:Images\\whitePawn.png";
    //white pieces

    //the board with its original set up
    private List<Piece> model = new ArrayList<Piece>();
    //root for fx
    static GridPane root;
    //size of board
    static int size;
    //declares the array of imageViews
    static ImageView[][] tiles;
    
    //static Array[][] current;

    //Create the board without pieces
    public void blankBoard(GridPane root, int size) 
    {
        for (int row = 0; row < size; row++) 
        {
            for (int col = 0; col < size; col++) 
            {
            	//Put a new button in each tile
                tiles[row][col] = new ImageView();
                //Set the colour of the new tile
                String color;
                if ((row + col) % 2 == 0) 
                {
                    color = "white";
                } 
                else 
                {
                    color = "black";
                }
                //Use the set colour to colour the tile
                tiles[row][col].setStyle("-fx-background-color: " + color + ";");
                //Add the board to the gridpane
                root.add(tiles[row][col], col, row);
                //Set the size of the tiles
                tiles[row][col].setFitHeight(50);
                tiles[row][col].setFitWidth(50);
            }
        }
        //set button size
        for (int i = 0; i < size; i++) 
        {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }

    public void start(Stage primaryStage) 
    {
    	//Declaring gridpane
        root = new GridPane();
        //Declaring size
        size = 8;
        //declares the array of buttons
        tiles = new ImageView[size][size];
        
        //set up the empty board
        //blankBoard(root, size);
      //loads the blank board
        
        String imageName = "File:Images\\blackRook.png";
        
        ImageView image = new ImageView(new Image(imageName));
        root.getChildren().add(image);
        
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        
        
        
        //setUp(tiles); 
    }
        
        //Old code DELETE?
        /*
        if ("onePlayer".equals(Main_menu.gameType)) {
            set_up(tiles);
            //set up the game board
            //Add in a call to launch AI into game

        }
        if ("twoPlayer".equals(Main_menu.gameType)) {
            set_up(tiles);
            //set up game board

        }
        setUp(tiles);
        
//        model = new ArrayList<>();
    }*/

    public void setUp(ImageView[][] tiles) 
    {
    	
    	Location brl = new Location(0, 0);
        Piece blackRookLeft = new Rook(brl, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Rook, blackRookLeft);

    	Location brr = new Location(0, 7);
        Piece blackRookRight = new Rook(brr, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Rook, blackRookRight);
        //set up black rooks

    	Location bkl = new Location(0, 1);
        Piece blackKnightLeft = new Knight(bkl, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Knight, blackKnightLeft);

    	Location bkr = new Location(0, 6);
        Piece blackKnightRight = new Knight(bkr, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Knight, blackKnightRight);
        //set up black knight

    	Location bbl = new Location(0, 2);
        Piece blackBishopLeft = new Bishop(bbl, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Bishop, blackBishopLeft);

    	Location bbr = new Location(0, 5);
        Piece blackBishopRight = new Bishop(bbr, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Bishop, blackBishopRight);
        //set up black Bishop

    	Location bq = new Location(0, 4);
        Piece blackQueen = new Queen(bq, Piece.Colour.BLACK);
        setUpPiece(tiles, B_Queen, blackQueen);
        //set up black Queen

    	Location bk = new Location(0, 3);
        Piece blackKing = new King(bk, Piece.Colour.BLACK);
        setUpPiece(tiles, B_king, blackKing);
        //set up black King

        /*for (int i = 0; i < 8; i++) {
            Pawn blackPawn = new Pawn(Piece.Colour.BLACK);
            setUpPiece(tiles, B_Pawn, blackPawn);
        }*/
        //set up the black pieces on the board with listerners

        //-------------------------------------------------------------------------//
    	Location wrl = new Location(7, 0);
        Piece whiteRookLeft = new Rook(wrl, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Rook, whiteRookLeft);

    	Location wrr = new Location(7, 7);
        Piece whiteRookRight = new Rook(wrr, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Rook, whiteRookRight);

    	Location wkl = new Location(7, 1);
        Piece whiteKnightLeft = new Knight(wkl, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Knight, whiteKnightLeft);

    	Location wkr = new Location(7, 6);
        Piece whiteKnightRight = new Knight(wkr, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Knight, whiteKnightRight);
        //set up black knight

    	Location wbl = new Location(7, 2);
        Piece whiteBishopLeft = new Bishop(wbl, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Bishop, whiteBishopLeft);

    	Location wbr = new Location(7, 5);
        Piece whiteBishopRight = new Bishop(wbr, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Bishop, whiteBishopRight);
        //set up black Bishop

    	Location wk = new Location(7, 3);
        Piece whiteKing = new King(wk, Piece.Colour.WHITE);
        setUpPiece(tiles, W_king, whiteKing);
        //set up black King

    	Location wq = new Location(7, 4);
        Piece whiteQueen = new Queen(wq, Piece.Colour.WHITE);
        setUpPiece(tiles, W_Queen, whiteQueen);
        //set up black Queen

        /*
        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Piece.Colour.WHITE);
            setUpPiece(tiles, W_Pawn, whitePawn);
        }*/
        //set up the white pieces on the board with listerners

        //Image imageOk = new Image(getClass().getResourceAsStream("ok.png"));
        //Button backButton = new Button("Back", new ImageView());

    }
    


    private void setUpPiece(
            ImageView[][] tiles, String imageName, Piece piece) {
        int x = piece.getSetUpLocation().getRow();
        int y = piece.getSetUpLocation().getColumn();
        ImageView tile = tiles[x][y];
        ImageView image = new ImageView(new Image(imageName));
        tile = image;
        root.getChildren().add(image);
        //tile.setOnAction(foo
        //        -> showValidMoves(tiles, piece, imageName)
        //);
        //add the piece to the arraylist
        model.add(piece);
    }





    
    public static void main(String[] args) {
        launch(args);
    }

}
