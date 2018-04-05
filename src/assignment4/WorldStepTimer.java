package assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Slider;

public class WorldStepTimer extends AnimationTimer {

	CritterWorldView world;
	Slider speedSlider;
	long prevTime = 0;
	double refreshRate = 1.0e9;
	int numSteps = 1;

	/**
	 * Constructor the the Animation timer for WorldStep
	 * 
	 * @param world
	 *            The CritterWorldView that is being animated
	 * @param speed
	 *            The speed in which the animation should run at
	 */
	public WorldStepTimer(CritterWorldView world, Slider speed) {
		this.world = world;
		this.speedSlider = speed;
	}

	/**
	 * Sets the number of steps that we call Critter.worldTimeStep for the animation
	 * 
	 * @param numSteps
	 *            Number of steps in worldTimeStep
	 */
	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	/**
	 * Called every frame to handle the world time steps according the number of
	 * steps given Takes in the current time to see the difference between the old
	 * call and new call to start with the next frame in animating
	 */
	@Override
	public void handle(long now) {
		if (prevTime == 0) {
			prevTime = now;
			return;
		}

		long timeInterval = now - prevTime;

		if (timeInterval > refreshRate) {
			refreshRate = 1.0e9 / speedSlider.getValue();

			for (int i = 0; i < numSteps; ++i) {
				Critter.worldTimeStep();
			}

			Critter.displayWorld(world);
			prevTime = 0;
		}
	}

}
