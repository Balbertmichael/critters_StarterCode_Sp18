package assignment4;


import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class Controller {
	@FXML
	public ScrollPane viewPane;
	@FXML
	public AnchorPane anchorContainer;
	
	
	private CritterWorldView world;
	
	public void initialize() {
		
		world = new CritterWorldView(anchorContainer);		
		viewPane.setContent(world);

	}
	
	public void updateView() {
		Critter.worldTimeStep();
		Critter.displayWorld(world);
	}
}
