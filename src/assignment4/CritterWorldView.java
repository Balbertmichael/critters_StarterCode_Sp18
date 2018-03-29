package assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import assignment4.Critter.CritterShape;

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
	
	
	public CritterWorldView(AnchorPane parent) {
		
		parentContainer = parent;
		setWorldSizeParams(Params.world_width, Params.world_height);
		gc = getGraphicsContext2D();
		
		// Scale grid appropriately upon resize
		parentContainer.widthProperty().addListener(e -> Critter.displayWorld(this));
		parentContainer.heightProperty().addListener(e -> Critter.displayWorld(this));
		

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
		
		gc.setFill(Color.GAINSBORO);
		// Space is too small to hold entire world
		if(width < minCanvasWidth || height < minCanvasHeight) {
			return MIN_VOX_SIZE;
		}
		// Space is too big for entire world
		else if( width > maxCanvasWidth && height > maxCanvasHeight) {
			return MAX_VOX_SIZE;
		}
		// Otherwise, scale world as needed
		else if (height > width){
			return width / cols;
		}
		else {
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
		gc.setStroke(Color.DIMGREY);
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
//	 * TODO: Ask if possible to turn x_coord and y_coord protected (I'd rather not mix view code into model)
//	 */
//	private void paintAllCritters() {
//		
//	}
//	
	
	/**
	 * Paints a Critter given x and y-coordinate
	 */
	protected void paintCritter(Critter c, int x, int y) {

		gc.setFill(c.viewFillColor());
		gc.setStroke(c.viewOutlineColor());
		gc.setLineWidth(2);
		
		// Critter will take up 75% of middle of voxel space
		double critSize = vox_size * 0.60;
		double startXPos = (x * vox_size) + (vox_size - critSize) / 2;
		double startYPos = (y * vox_size) + (vox_size - critSize) / 2;
		
		switch(c.viewShape()) {
			case CIRCLE:
				gc.fillOval(startXPos, startYPos, critSize, critSize);
				gc.strokeOval(startXPos, startYPos, critSize, critSize);
				break;
			case SQUARE:
				gc.fillRect(startXPos, startYPos, critSize, critSize);
				gc.strokeRect(startXPos, startYPos, critSize, critSize);
				break;
			case DIAMOND:
				double midX = (x * vox_size) + (vox_size / 2);
				double midY = (y * vox_size) + (vox_size / 2);
				
				double [] xDiaPoints = {midX, startXPos + critSize, midX, startXPos};
				double [] yDiaPoints = {startYPos, midY, startYPos + critSize, midY};
				
				gc.fillPolygon(xDiaPoints, yDiaPoints, 4);
				gc.strokePolygon(xDiaPoints, yDiaPoints, 4);
				break; 
				
			// TODO: Find a way to not make star look stupid
			case STAR:
				double star_ax = (x * vox_size) + (vox_size / 2); 
				double star_ay = startYPos + critSize;
				
				double star_bx = startXPos;
				double star_cx = startXPos + critSize;
				double star_bcy = startYPos;
				
				double [] xStarPoints = {star_ax, star_bx, star_cx};
				double [] yStarPoints = {star_ay, star_bcy, star_bcy};
				
				gc.fillPolygon(xStarPoints, yStarPoints, 3);
				
			case TRIANGLE:
				double ax = (x * vox_size) + (vox_size / 2); 
				double ay = startYPos;
				
				double bx = startXPos;
				double cx = startXPos + critSize;
				double bcy = ay + critSize;
				
				double [] xTriPoints = {ax, bx, cx};
				double [] yTriPoints = {ay, bcy, bcy};
				
				gc.fillPolygon(xTriPoints, yTriPoints, 3);
				gc.strokePolygon(xTriPoints, yTriPoints, 3);
				break;
		}
		
	}

}
