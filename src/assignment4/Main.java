package assignment4;

import java.io.File;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {

			for (int i = 0; i < 10; i++) {
				// Critter.makeCritter("Craig");
				// Critter.makeCritter("Critter1");
				// Critter.makeCritter("Critter2");
				Critter.makeCritter("Critter3");
				// Critter.makeCritter("Critter4");
			}

			Parent root = FXMLLoader.load(getClass().getResource("AppScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setTitle("Critters Project");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (String s : Critter.makeCritterList()) {
			System.out.println(s);
		}
		launch(args);
	}
}
