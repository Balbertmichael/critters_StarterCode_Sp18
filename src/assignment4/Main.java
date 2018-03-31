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

	private static String myPackage; // package of Critter file. Critter cannot be in default pkg.
	public static ObservableList<String> critter_list;

	private static String pkgLoc;

	// Gets the package name. The usage assumes that Critter and its subclasses are
	// all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
		pkgLoc = Paths.get("").toAbsolutePath().toString().replace("\\", "\\\\") + "\\\\" + "bin\\\\" + myPackage;
		critter_list = makeCritterList();
	}

	public static ObservableList<String> makeCritterList() {
		File f = new File(pkgLoc);
		String[] subFiles = f.list();
		List<String> findClasses = new ArrayList<String>();
		List<String> findCritters = new ArrayList<String>();
		for (String s : subFiles) {
			if (s.endsWith(".class")) {
				findClasses.add(s);
			}
		}
		for (String s : findClasses) {
			String critName = s.substring(0, s.indexOf(".class"));
			try {
				Class<?> check_class = Class.forName(myPackage + '.' + critName);
				int mod = check_class.getModifiers();
				if (Modifier.isAbstract(mod) || Modifier.isPrivate(mod)) {
					continue;
				}
				Class<?> critter_class = Critter.class;
				if (critter_class.isAssignableFrom(check_class)) {
					findCritters.add(critName);
					// System.out.println(critName);
				}
			} catch (ClassNotFoundException e) {
				// Shouldn't happen
				continue;
			}
		}
		return FXCollections.observableArrayList(findCritters);
	}

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
		for (String s : critter_list) {
			System.out.println(s);
		}
		launch(args);
	}
}
