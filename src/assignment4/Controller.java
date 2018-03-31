package assignment4;

import java.io.File;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	@FXML
	public ChoiceBox<String> critterChoiceBox;
	@FXML
	public TextField critterNumber;
	@FXML
	public Button addCritter;

	private CritterWorldView world;
	private WorldStepTimer worldStepTimer;

	public void initialize() {
		world = new CritterWorldView(anchorContainer);
		populateChoiceBox();
		worldStepTimer = new WorldStepTimer(world, speedSlider);
		viewPane.setContent(world);
	}

	public void updateView() {
		Critter.worldTimeStep();
		Critter.displayWorld(world);
	}

	public void toggleAnimateView() {
		if (world.isAnimateOn()) {
			world.setAnimate(false);
			worldStepTimer.stop();
			togglePlayButton.setText("Play");
		} else {
			world.setAnimate(true);
			worldStepTimer.start();
			togglePlayButton.setText("Stop");
		}
	}

	private void populateChoiceBox() {
		critterChoiceBox.getItems().addAll(Main.critter_list);
		if (!Main.critter_list.isEmpty()) {
			critterChoiceBox.setValue(Main.critter_list.get(0));
		}
	}

	public void controllerMakeCritter() {
		String inputNum = critterNumber.getText();
		String inputCritter = critterChoiceBox.getValue();
		System.out.println(inputNum + " " + inputCritter);
		try {
			int num = Integer.parseInt(inputNum);
			for(int i = 0; i < num; ++i) {
				Critter.makeCritter(inputCritter);
			}
		}catch(NumberFormatException | InvalidCritterException e){
			//For now leave as empty to ignore any strange changes
		}
	}
}
