package assignment4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{

	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			for(int i = 0; i < 10; i++) {
				Critter.makeCritter("Craig");
				Critter.makeCritter("Critter1");
				Critter.makeCritter("Critter2");
				Critter.makeCritter("Critter3");
				Critter.makeCritter("Critter4");
			}

			
			Parent root = FXMLLoader.load(getClass().getResource("AppScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Critters Project");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
