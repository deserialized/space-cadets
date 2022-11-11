package application;

/*

    ThreadHandler.java
    Written by Ben Hadlington
    - Runs a new thread which constantly eases between different hypocycloids.

*/

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

/*

    Class

*/

public class ThreadHandler extends Thread {
    private  final AudioHandler audioHandler;
    private final HashMap<String, Label> labelMap;
    private GraphicsContext g;
    private HashMap<String, Integer> variableMap = new HashMap<>();
    private int cycle = 0;
    private final int midpoint;
    private double amplifier = 2.5;
    private HashMap<String, Integer> subCycles = new HashMap<>(Map.of(
            "R", 0,
            "r", 0,
            "O", 0
    ));

    /* Settings */
    private final HashMap<String, Integer> config = new HashMap<>(Map.of(
            "maxR", 150,
            "minR", 75,
            "maxr", 300,
            "minr", 200,
            "maxO", 25,
            "minO", 10
    ));

    /* Constructor */
    public ThreadHandler(GraphicsContext g, int midpoint, HashMap<String, Label> labelMap, int R, int r, int O) {
        this.g = g;
        this.midpoint = midpoint;
        this.labelMap = labelMap;
        this.variableMap.put("R", R);
        this.variableMap.put("r", r);
        this.variableMap.put("O", O);
        this.audioHandler = new AudioHandler();

        /* Scale up amplifier based on window size */
        this.amplifier = (midpoint / 300) * amplifier;
    }

    /* Creates a new hypocycloid object with current values */
    private void redraw() {
        new Hypocycloid(g, midpoint, this.variableMap.get("R"), this.variableMap.get("r"), this.variableMap.get("O"));
    }

    /* Performs boundary checks and modifies hypocycloid parameters */
    private void handleCycleVariable(String varName) {
        int subCycle = subCycles.get(varName);
        int value = variableMap.get(varName);

        if (subCycle == 0) {
            /* Increment function */
            if (value >= config.get("max" + varName)) {
                subCycles.replace(varName, 1);
            } else {
                variableMap.replace(varName, value + 1);
            }
        } else {
            /* Decrement function */
            if (value <= config.get("min" + varName)) {
                subCycles.replace(varName, 0);
            } else {
                variableMap.replace(varName, value - 1);
            }
        }
    }

    /* Determines which value(s) should be manipulated */
    private void handleCycle() {
        switch (cycle) {
            case 0 -> {handleCycleVariable("R");}
            case 1 -> {handleCycleVariable("r");}
            case 2 -> {handleCycleVariable("O");}
            case 3 -> {
                handleCycleVariable("R");
                handleCycleVariable("r");
            }
            case 4 -> {
                handleCycleVariable("R");
                handleCycleVariable("r");
                handleCycleVariable("O");
            }
            default -> {
                /* Collects audio data, 0 is quietest */
                float audioLevel = audioHandler.captureAudio();

                /* Calculate new values */
                int RValue = (int) Math.max(0, Math.floor(config.get("maxR") - (audioLevel * amplifier)));

                /* Set values */
                variableMap.replace("R", RValue);
                handleCycleVariable("O");
            }
        }
    }

    /* Updates variable labels */
    private void updateLabels() {
        labelMap.forEach((varName, label) -> {
            Platform.runLater(() -> {
                String suffix;
                if (varName.equals("Mode")) {
                    if (cycle == 0) {
                        suffix = "R";
                    } else if (cycle == 1) {
                        suffix = "r";
                    } else if (cycle == 2) {
                        suffix = "O";
                    } else if (cycle == 3) {
                        suffix = "R, r";
                    } else if (cycle == 4) {
                        suffix = "R, r, O";
                    } else {
                        suffix = "Audio";
                    }
                } else {
                    suffix = variableMap.get(varName).toString();
                }
                label.setText(varName + ": " + suffix);
            });
        });
    }

    /* Cycle between different variables */
    public void switchVariable() {
        cycle++;
        if (cycle > 5) {
            cycle = 0;
        }
    }

    /*

        OPERATION:
            - Draw new hypocycloid.
            - Small delay.
            - Refresh hypocycloid variables.
            - Update variable labels.
            - Clear canvas.

    */
    public void run() {
        while (true) {
            redraw();

            int sleepTime = 50;
            if (cycle == 5) {
                sleepTime = 25;
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
            handleCycle();
            updateLabels();

            Platform.runLater(() -> {
                g.clearRect(0, 0, midpoint * 2, midpoint * 2);
            });
        }
    }
}