package application;

/*

    SpirographApplication.java
    Written by Ben Hadlington
    - Creates the application.

*/

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*

    Class

*/

public class SpirographApplication extends Application {
    private Group root;

    public static void main(String[] args) {
        /* Launches the application */
        launch();
    }

    @Override
    public void start(Stage stage) {
        /* Sets up the interface scene */
        setupScene(stage);
    }

    public void setupScene(Stage stage) {
        /* Sets up the stage style */
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        /* Generates scene */
        this.root = new Group();
        Scene scene = new Scene(this.root, 600, 700, Color.BLACK);

        /* Applies style.css */
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);

        /* Creates interface */
        Interface userInterface = new Interface();

        /* Creates interface objects */
        Canvas canvas = userInterface.setupCanvas();
        Pane infoPane = userInterface.setupInfoPane();
        Button cycleButton = userInterface.setupCycleButton();

        /* Adds interface objects to root */
        addToRoot(canvas);
        addToRoot(infoPane);
        addToRoot(cycleButton);

        /* Initiates graphical spirograph animation */
        userInterface.initiateThread();

        /* Makes scene visible */
        stage.setScene(scene);
        stage.show();
    }

    /* Overloaded methods to add an interface item to root */
    public void addToRoot(Canvas canvas) {
        root.getChildren().add(canvas);
    }

    public void addToRoot(Pane pane) {
        root.getChildren().add(pane);
    }

    public void addToRoot(Button button) {
        root.getChildren().add(button);
    }
}