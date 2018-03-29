package assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


class CritterWorldView extends Canvas{
	
	private final double MIN_VOX_SIZE = 20;
	private final double MAX_VOX_SIZE = 40;
	private double vox_size;
	private int rows;									// Params.world_height
	private int cols;									// Params.world_width
	
	private AnchorPane parentContainer;					// Canvas size basis
	private double minCanvasWidth;
	private double minCanvasHeight;
	private double maxCanvasWidth;
	private double maxCanvasHeight;

	private GraphicsContext gc;
	
	
	/**
	 * Constructor: initializes and displays Critter World graph
	 * World will be redrawn and rescaled upon resizing of parent container
	 * @param parent pane that will serve as basis for canvas size
	 */
	public CritterWorldView(AnchorPane parent) {
		
		parentContainer = parent;
		setWorldSizeParams(20, 20);
		gc = getGraphicsContext2D();
		
		// Scale grid appropriately upon resize
		parentContainer.widthProperty().addListener(e -> redraw());
		parentContainer.heightProperty().addListener(e -> redraw());
		

	}
	
	public void redraw() {

		vox_size = calcVoxelSize();
		setWidth(vox_size * cols);
		setHeight(vox_size * rows);
		
		double width = getWidth();
		double height = getHeight();
		
		translateCanvas(width, height);
		gc.fillRect(0, 0, width, height);
		drawGrid();
		
	}
	/**
	 * Allows user to set number of columns and rows for the Critter World
	 * @param numRows number of rows
	 * @param numCols number of columns
	 */
	// TODO: Possibly give user option to resize world or set world size
	public void setWorldSizeParams(int numRows, int numCols) {
		
		rows = numRows;
		cols = numCols;
		
		// Minimum preferred canvas values
		minCanvasWidth = cols * MIN_VOX_SIZE;
		minCanvasHeight = rows * MIN_VOX_SIZE;
		
		// Maximum preferred canvas values
		maxCanvasWidth = cols * MAX_VOX_SIZE;
		maxCanvasHeight = rows * MAX_VOX_SIZE;
		
	}
	
	/**
	 * Determines size of each voxel depending on the size of the pane
	 * Scales voxel size when needed
	 * @return size of one index of a grid
	 */
	private double calcVoxelSize() {
		
		double width = parentContainer.getWidth();
		double height = parentContainer.getHeight();
		
		// Space is too small to hold entire world
		if(width < minCanvasWidth || height < minCanvasHeight) {
			gc.setFill(Color.RED);
			return MIN_VOX_SIZE;
		}
		// Space is too big for entire world
		else if( width > maxCanvasWidth && height > maxCanvasHeight) {
			gc.setFill(Color.YELLOW);
			return MAX_VOX_SIZE;
		}
		// Otherwise, scale world as needed
		else if (height > width){
			gc.setFill(Color.GREEN);
			return width / cols;
		}
		else {
			gc.setFill(Color.BLUE);
			return height / rows;
		}
	}
	
	/**
	 * Ensures Canvas will always be centered in case width and height are larger than world size
	 * @param width canvas width
	 * @param height canvas height
	 */
	private void translateCanvas(double width, double height) {
		double parentWidth = parentContainer.getWidth();
		double parentHeight = parentContainer.getHeight(); 
		
//		System.out.println("Parent: " + parentWidth + " x " + parentHeight);
//		System.out.println("Canvas: " + width + " x " + height);
		// Center horizontally
		if(parentWidth > width) {
			setTranslateX((parentWidth - width) / 2);
		}
		else {
			setTranslateX(0);
		}
		
		// Center vertically
		if(parentHeight > height) {
			setTranslateY((parentHeight - height) / 2);
		}
		else {
			setTranslateY(0);
		}
	}
	
	/**
	 * Draws the grid
	 */
	private void drawGrid() {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		double width = getWidth();
		double height = getHeight();
		
		for(int i = 0; i <= rows; ++i) {
			double y = i * vox_size;
			gc.strokeLine(0, y, width, y);	
		}
		
		for(int i = 0; i <= cols; ++i) {
			double x = i * vox_size;
			gc.strokeLine(x, 0, x, height);	
		}
	
	}
	
//	/**
//	 * Paints all Critters in grid
//	 */
//	private void paintAllCritters() {
//		
//	}
//	
//	/**
//	 * Paints a Critter given x and y-coordinate
//	 */
//	private void paintCritter() {
//		
//	}

}
