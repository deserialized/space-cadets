package application;

/*

    Interface.java
    Written by Ben Hadlington
    - Sets up and handles the user interface.

*/

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/*

    Class

*/

public class Interface {
    private ThreadHandler newThread;
    private GraphicsContext g;
    private int windowX, windowY;
    private HashMap<String, Label> labelMap = new HashMap<>();

    /* Starting values */
    private final HashMap<String, Integer> config = new HashMap<>(Map.of(
            "R", 150,
            "r", 300,
            "O", 10
    ));

    /* Constructor */
    public Interface(int windowX, int windowY) {
        this.windowX = windowX;
        this.windowY = windowY;
    }

    public Canvas setupCanvas() {
        /* Creates new drawing canvas */
        Canvas canvas = new Canvas(windowX, windowX);
        this.g = canvas.getGraphicsContext2D();
        g.setLineWidth(1);
        g.setStroke(Color.GREEN);

        return canvas;
    }

    public Pane setupInfoPane() {
        /* Creates new pane */
        Pane newPane = new Pane();
        newPane.setId("infoBackground");
        newPane.setPrefSize(windowX, 100);
        newPane.relocate(0, windowY - 100);

        return newPane;
    }

    public Button setupCycleButton() {
        /* Creates new button */
        Button step = new Button();
        step.setId("step");
        step.setPrefSize(windowX, windowX);

        /* Creates event listener */
        step.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                /* Cycles the animation variable */
                newThread.switchVariable();
            }
        });

        return step;
    }

    public Label setupVarLabel(String varName, int labelNumber) {
        /* Creates new Label */
        Label newLabel = new Label();
        newLabel.setId(varName + "Label");
        newLabel.setText(varName + ": " + config.get(varName));
        newLabel.setPrefSize(windowX / 2, 30);
        newLabel.relocate(10, windowY - 5 - (30 * labelNumber));

        /* Adds to label HashMap */
        labelMap.put(varName, newLabel);

        return newLabel;
    }

    public Label setupModeLabel() {
        /* Creates new Label */
        Label newLabel = new Label();
        newLabel.setId("modeLabel");
        newLabel.setText("Mode: R");
        newLabel.setPrefSize(windowX / 2, 40);
        newLabel.relocate(30, 20);

        /* Adds to label HashMap */
        labelMap.put("Mode", newLabel);

        return newLabel;
    }

    public void initiateThread() {
        /* Starts animation thread */
        newThread = new ThreadHandler(g, (windowX / 2), labelMap, config.get("R"), config.get("r"), config.get("O"));
        new Thread(newThread).start();
    }
}