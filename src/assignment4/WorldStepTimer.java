package assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Slider;

public class WorldStepTimer extends AnimationTimer{

	CritterWorldView world;
	Slider speedSlider;
	long prevTime = 0;
	double refreshRate = 1.0e9;
	int numSteps = 1;
	
	public WorldStepTimer(CritterWorldView world, Slider speed) {
		this.world = world;
		this.speedSlider = speed;
	}
	
	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}
	
	@Override
	public void handle(long now) {
		if (prevTime == 0) {
			prevTime = now;
			return;
		}
		
		long timeInterval = now - prevTime;
		
		if(timeInterval > refreshRate) {
			refreshRate = 1.0e9 / speedSlider.getValue();
			
			for(int i = 0; i < numSteps; ++i) {
				Critter.worldTimeStep();	
			}

			Critter.displayWorld(world);
			prevTime = 0;
		}
	}

}
