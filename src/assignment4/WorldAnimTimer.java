package assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Slider;

public class WorldAnimTimer extends AnimationTimer{

	CritterWorldView world;
	Slider speedSlider;
	long prevTime = 0;
	double refreshRate = 1.0e9;
	int numFramesPerWorldStep = 0;
	
	public WorldAnimTimer(CritterWorldView world, Slider speed) {
		this.world = world;
		this.speedSlider = speed;
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
			Critter.displayWorld(world);
			prevTime = 0;
			
			if(numFramesPerWorldStep == 3) {
				Critter.worldTimeStep();
				Critter.displayWorld(world);
				numFramesPerWorldStep = 0;
			}
			else {
				numFramesPerWorldStep++;
			}
		}
	}

}
