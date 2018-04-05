package assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import assignment4.Critter.CritterShape;

class CritterWorldView extends Canvas {

	private final double MIN_VOX_SIZE = 10;
	private final double MAX_VOX_SIZE = 20;
	private double vox_size;
	private double crit_size;
	private int rows; // Params.world_height
	private int cols; // Params.world_width

	private AnchorPane parentContainer; // Canvas size basis
	private double minCanvasWidth;
	private double minCanvasHeight;
	private double maxCanvasWidth;
	private double maxCanvasHeight;

	private GraphicsContext gc;

	/**
	 * Animation Code
	 */
	private boolean animate = false;

	/**
	 * Simple checking function to close the controller and toggle the animation
	 * 
	 * @return
	 */
	public boolean isAnimateOn() {
		return animate;
	}

	/**
	 * Toggle function to run animation
	 * 
	 * @param animate
	 *            Basically closes or opens animation
	 */
	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	/**
	 * Constructor that takes in the AnchorPane to draw the Critter world map
	 * 
	 * @param parent
	 *            Anchorpane to draw the world on
	 */
	public CritterWorldView(AnchorPane parent) {

		parentContainer = parent;
		setWorldSizeParams(Params.world_height, Params.world_width);
		gc = getGraphicsContext2D();

		// Scale grid appropriately upon resize
		parentContainer.widthProperty().addListener(e -> Critter.displayWorld(this));
		parentContainer.heightProperty().addListener(e -> Critter.displayWorld(this));

	}

	/**
	 * Function to redraw the Grid in order to update for the new Critters and make
	 * sure that only one step is shown at a time
	 */
	public void redrawGrid() {

		vox_size = calcVoxelSize();
		crit_size = vox_size * 0.60;
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
	 * 
	 * @param numRows
	 *            number of rows
	 * @param numCols
	 *            number of columns
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
	 * Determines size of each voxel depending on the size of the pane Scales voxel
	 * size when needed
	 * 
	 * @return size of one index of a grid
	 */
	private double calcVoxelSize() {

		double width = parentContainer.getWidth();
		double height = parentContainer.getHeight();

		gc.setFill(Color.GAINSBORO);
		// Space is too small to hold entire world
		if (width < minCanvasWidth || height < minCanvasHeight) {
			return MIN_VOX_SIZE;
		}
		// Space is too big for entire world
		else if (width > maxCanvasWidth && height > maxCanvasHeight) {
			return MAX_VOX_SIZE;
		}
		// Otherwise, scale world as needed
		else if (height > width) {
			return width / cols;
		} else {
			return height / rows;
		}
	}

	/**
	 * Ensures Canvas will always be centered in case width and height are larger
	 * than world size
	 * 
	 * @param width
	 *            canvas width
	 * @param height
	 *            canvas height
	 */
	private void translateCanvas(double width, double height) {
		double parentWidth = parentContainer.getWidth();
		double parentHeight = parentContainer.getHeight();

		// System.out.println("Parent: " + parentWidth + " x " + parentHeight);
		// System.out.println("Canvas: " + width + " x " + height);
		// Center horizontally
		if (parentWidth > width) {
			setTranslateX((parentWidth - width) / 2);
		} else {
			setTranslateX(0);
		}

		// Center vertically
		if (parentHeight > height) {
			setTranslateY((parentHeight - height) / 2);
		} else {
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

		for (int i = 0; i <= rows; ++i) {
			double y = i * vox_size;
			gc.strokeLine(0, y, width, y);
		}

		for (int i = 0; i <= cols; ++i) {
			double x = i * vox_size;
			gc.strokeLine(x, 0, x, height);
		}

	}

	/**
	 * Paints a Critter given x and y-coordinate Starting (x, y) indicates if
	 * Critter is wrapped in a rectangle, upper left position of rectangle
	 * 
	 * @param x
	 *            starting x position on canvas
	 * @param y
	 *            starting y position on canvas
	 */
	protected void paintCritter(Critter c, double x, double y) {

		gc.setFill(c.viewFillColor());
		gc.setStroke(c.viewOutlineColor());
		gc.setLineWidth(1);

		switch (c.viewShape()) {
		case CIRCLE:
			paintCircle(x, y);
			break;
		case SQUARE:
			paintSquare(x, y);
			break;
		case DIAMOND:
			paintDiamond(x, y);
			break;
		case STAR:
			paintStar(x, y);
			break;
		case TRIANGLE:
			paintTriangle(x, y);
			break;
		}
	}

	/**
	 * Convert given row index to canvas position values
	 * 
	 * @param x
	 *            row
	 * @return x starting position in canvas
	 */
	public double convertRowToX(int x) {
		return (x * vox_size) + (vox_size - crit_size) / 2;
	}

	/**
	 * Convert given column index to canvas position values
	 * 
	 * @param y
	 * @return
	 */
	public double convertColToY(int y) {
		return (y * vox_size) + (vox_size - crit_size) / 2;
	}

	/* Critter Drawing Functions */
	private void paintSquare(double x, double y) {
		gc.fillRect(x, y, crit_size, crit_size);
		gc.strokeRect(x, y, crit_size, crit_size);
	}

	private void paintCircle(double x, double y) {
		gc.fillOval(x, y, crit_size, crit_size);
		gc.strokeOval(x, y, crit_size, crit_size);
	}

	private void paintDiamond(double x, double y) {
		double midX = x + (crit_size / 2);
		double midY = y + (crit_size / 2);

		double[] xPoints = { midX, x + crit_size, midX, x };
		double[] yPoints = { y, midY, y + crit_size, midY };

		gc.fillPolygon(xPoints, yPoints, 4);
		gc.strokePolygon(xPoints, yPoints, 4);
	}

	private void paintTriangle(double x, double y) {
		double ax = x + (crit_size / 2);
		double ay = y;

		double bx = x;
		double cx = x + crit_size;
		double bcy = ay + crit_size;

		double[] xPoints = { ax, bx, cx };
		double[] yPoints = { ay, bcy, bcy };

		gc.fillPolygon(xPoints, yPoints, 3);
		gc.strokePolygon(xPoints, yPoints, 3);
	}

	private void paintStar(double x, double y) {
		double interval = crit_size / 3;

		double[] xChoices = { x, x + 1 * interval, x + 0.5 * interval, x + 1.5 * interval, x + 2 * interval,
				x + 2.5 * interval, x + 3 * interval };

		double[] yChoices = { y, y + 1 * interval, y + 1.75 * interval, y + 2.10 * interval, y + 3 * interval };

		double[] xPoints = { xChoices[0], xChoices[1], xChoices[3], xChoices[4], xChoices[6], xChoices[4], xChoices[5],
				xChoices[3], xChoices[2], xChoices[1] };
		double[] yPoints = { yChoices[1], yChoices[1], yChoices[0], yChoices[1], yChoices[1], yChoices[2], yChoices[4],
				yChoices[3], yChoices[4], yChoices[2] };
		gc.fillPolygon(xPoints, yPoints, 10);
		gc.strokePolygon(xPoints, yPoints, 10);
	}
}
