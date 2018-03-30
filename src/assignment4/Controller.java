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
	private WorldStepTimer worldStepTimer;
	
	public void initialize() {
		
		world = new CritterWorldView(anchorContainer);		
		worldStepTimer = new WorldStepTimer(world, speedSlider);
		viewPane.setContent(world);

	}
	
	public void updateView() {
		Critter.worldTimeStep();
		Critter.displayWorld(world);
	}
	
	public void toggleAnimateView() {
		if(world.isAnimateOn()) {
			world.setAnimate(false);
			worldStepTimer.stop();
			togglePlayButton.setText("Play");
		}
		else {
			world.setAnimate(true);
			worldStepTimer.start();
			togglePlayButton.setText("Stop");
		}
	}
}
