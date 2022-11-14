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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*

    Class

*/

public class SpirographApplication extends Application {
    private Group root;

    /*
        Settings
        NOTE: vertical program, windowY must be higher than windowX
    */
    HashMap<String, Integer> config = new HashMap<>(Map.of(
            "windowX", 900,
            "windowY", 1000
    ));

    public static void main(String[] args) {
        /* Launches the application */
        launch();
    }

    @Override
    public void start(Stage stage) {
        /* Adjust size config */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        config.replace("windowX", screenSize.height - 200);
        config.replace("windowY", screenSize.height - 100);

        /* Sets up the interface scene */
        setupScene(stage);
    }

    public void setupScene(Stage stage) {
        /* Sets up the stage style */
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        /* Generates scene */
        this.root = new Group();
        Scene scene = new Scene(this.root, config.get("windowX"), config.get("windowY"), Color.BLACK);

        /* Applies style.css */
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);

        /* Creates interface */
        Interface userInterface = new Interface(config.get("windowX"), config.get("windowY"));

        /* Creates interface objects */
        Canvas canvas = userInterface.setupCanvas();
        Pane infoPane = userInterface.setupInfoPane();
        Button cycleButton = userInterface.setupCycleButton();

        /* Adds interface objects to root */
        addToRoot(canvas);
        addToRoot(infoPane);
        addToRoot(cycleButton);

        /* Creates variable labels */
        Label OLabel = userInterface.setupVarLabel("O", 1);
        Label rLabel = userInterface.setupVarLabel("r", 2);
        Label RLabel = userInterface.setupVarLabel("R", 3);

        /* Creates mode label */
        Label modeLabel = userInterface.setupModeLabel();

        /* Adds labels to root */
        addToRoot(OLabel);
        addToRoot(rLabel);
        addToRoot(RLabel);
        addToRoot(modeLabel);

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

    public void addToRoot(Label label) {
        root.getChildren().add(label);
    }
}