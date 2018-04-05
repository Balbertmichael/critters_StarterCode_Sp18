package assignment4;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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

	@FXML
	public TextField stepsNumber;
	@FXML
	public Button stepsForward;

	@FXML
	public TextField seedNumber;
	@FXML
	public Button setSeedNumber;

	@FXML
	public ChoiceBox<String> runStatsChoiceBox;
	@FXML
	public Button changeRunStats;
	@FXML
	public TextFlow runStatsText;

	@FXML
	public Button resetButton;
	
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
			enableButtons();			
			worldStepTimer.stop();
			togglePlayButton.setText("Play");
		} else {
			world.setAnimate(true);
			try{
				int numSteps = Integer.parseInt(stepsNumber.getText());
				worldStepTimer.setNumSteps(numSteps);
			} finally {
				// Do nothing
			}
			disableButtons();
			worldStepTimer.start();
			togglePlayButton.setText("Stop");
		}
	}

	private void populateChoiceBox() {
		ObservableList<String> s = Critter.makeCritterList();
		critterChoiceBox.getItems().addAll(s);
		runStatsChoiceBox.getItems().addAll(s);
		if (!s.isEmpty()) {
			critterChoiceBox.setValue(s.get(0));
			runStatsChoiceBox.setValue(s.get(0));
		}
	}

	public void controllerMakeCritter() {
		String inputNum = critterNumber.getText();
		String inputCritter = critterChoiceBox.getValue();
	    //System.out.println(inputNum + " " + inputCritter);
		if (!world.isAnimateOn()) {
			try {
				int num = Integer.parseInt(inputNum);
				for (int i = 0; i < num; ++i) {
					Critter.makeCritter(inputCritter);
				}
			} catch (NumberFormatException | InvalidCritterException e) {
				// For now leave as empty to ignore any strange changes
			}
			Critter.displayWorld(world);
		}
	}

	public void stepForward() {
		int stepCount = 0;
		if (!world.isAnimateOn()) {
			try {
				stepCount = Integer.parseInt(stepsNumber.getText());
				for (int i = 0; i < stepCount; ++i) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(world);
			} catch (NumberFormatException e) {

			}
		}
	}

	public void setSeed() {
		Long seed;
		if (!world.isAnimateOn()) {
			try {
				seed = Long.parseLong(seedNumber.getText());
				Critter.setSeed(seed);
			} catch (NumberFormatException e) {

			}
		}
	}

	public void setRunStats() {
		String inputCritter = runStatsChoiceBox.getValue();
		String critterRunStats;
		String myPackage = Critter.class.getPackage().toString().split(" ")[1];
		if(!world.isAnimateOn()) {
			try {
				List<Critter> critters = Critter.getInstances(inputCritter);
				Class<?> critter_class = Class.forName(myPackage + '.' + inputCritter);
				critterRunStats = (String) critter_class.getMethod("runStats", List.class).invoke(null, critters);
				Text runStatsOutput = new Text(critterRunStats);
				runStatsText.getChildren().clear();
				runStatsText.getChildren().add(runStatsOutput);
				
			}
			catch (InvalidCritterException | IllegalAccessException | ClassNotFoundException | ClassCastException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			}
		}
	}
	
	public void resetWorld() {
		Critter.clearWorld();
		Critter.displayWorld(world);
	}
	
	private void disableButtons() {
		addCritter.setDisable(true);
		stepsForward.setDisable(true);
		setSeedNumber.setDisable(true);
		changeRunStats.setDisable(true);
		stepsNumber.setDisable(true);
		runStatsChoiceBox.setDisable(true);
		resetButton.setDisable(true);
	}
	
	private void enableButtons() {
		addCritter.setDisable(false);
		stepsForward.setDisable(false);
		setSeedNumber.setDisable(false);
		changeRunStats.setDisable(false);		
		stepsNumber.setDisable(false);
		runStatsChoiceBox.setDisable(false);
		resetButton.setDisable(false);
	}
	
}
