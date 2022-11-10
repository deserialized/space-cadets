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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/*

    Class

*/

public class Interface {
    private ThreadHandler newThread;
    private GraphicsContext g;

    /* Constructor */
    public Interface() {}

    public Canvas setupCanvas() {
        /* Creates new drawing canvas */
        Canvas canvas = new Canvas(600, 600);
        this.g = canvas.getGraphicsContext2D();
        g.setLineWidth(1);
        g.setStroke(Color.GREEN);

        return canvas;
    }

    public Pane setupInfoPane() {
        /* Creates new pane */
        Pane newPane = new Pane();
        newPane.setId("infoBackground");
        newPane.setPrefSize(600, 100);
        newPane.relocate(0, 600);

        return newPane;
    }

    public Button setupCycleButton() {
        /* Creates new button */
        Button step = new Button();
        step.setId("step");
        step.setPrefSize(600, 600);

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

    public void initiateThread() {
        /* Starts animation thread */
        newThread = new ThreadHandler(g, 50, 200, 1);
        new Thread(newThread).start();
    }

    /* Fetches the graphic context */
    public GraphicsContext getContext() {
        return this.g;
    }
}