package assignment4;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

public class Controller {
	@FXML
	public ScrollPane viewPane;
	@FXML
	public AnchorPane anchorContainer;
	@FXML
	public Button togglePlayButton;
	@FXML
	public Slider speedSlider;
	
	private CritterWorldView world;
	private WorldAnimTimer animTimer;
	
	public void initialize() {
		
		world = new CritterWorldView(anchorContainer);		
		animTimer = new WorldAnimTimer(world, speedSlider);
		viewPane.setContent(world);

	}
	
	public void updateView() {
		Critter.worldTimeStep();
		Critter.displayWorld(world);
	}
	
	public void toggleAnimateView() {
		if(world.isAnimateOn()) {
			world.setAnimate(false);
			animTimer.stop();
			togglePlayButton.setText("Play");
			Critter.displayWorld(world);
		}
		else {
			world.setAnimate(true);
			animTimer.start();
			togglePlayButton.setText("Stop");
		}
	}
}
