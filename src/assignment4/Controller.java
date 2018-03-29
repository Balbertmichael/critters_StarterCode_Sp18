package assignment4;


import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class Controller {
	@FXML
	public ScrollPane viewPane;
	@FXML
	public AnchorPane anchorContainer;
	public GraphicsContext gc;
	
	public void initialize() {
		
		CritterWorldView world = new CritterWorldView(anchorContainer);		
		viewPane.setContent(world);

	}
	
}
