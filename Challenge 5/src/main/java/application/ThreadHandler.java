package application;

/*

    ThreadHandler.java
    Written by Ben Hadlington
    - Runs a new thread which constantly eases between different hypocycloids.

*/

import javafx.scene.canvas.GraphicsContext;

/*

    Class

*/

public class ThreadHandler extends Thread {
    private GraphicsContext g;
    private int R, r, O;
    private int cycle = 0;

    /* Constructor */
    public ThreadHandler(GraphicsContext g, int R, int r, int O) {
        this.g = g;
        this.R = R;
        this.r = r;
        this.O = O;
    }

    /* Creates a new hypocycloid object with current values */
    private void redraw() {
        Hypocycloid newHypocycloid = new Hypocycloid(g, R, r, O);
    }

    /*

        Performs different boundary and bounce-back functions based on the current cycle status:

        Key: incr/decr - variableName
            0/1 - R
            2/3 - r
            4/5 - O

    */
    private void handleCycle() {
        switch (cycle) {
            case 0 -> {
                if (R >= 75) {
                    cycle = 1;
                } else {
                    R++;
                }
            }
            case 1 -> {
                if (R <= 50) {
                    cycle = 0;
                } else {
                    R--;
                }
            }
            case 2 -> {
                if (r >= 200) {
                    cycle = 3;
                } else {
                    r++;
                }
            }
            case 3 -> {
                if (r <= 100) {
                    cycle = 2;
                } else {
                    r--;
                }
            }
            case 4 -> {
                if (O >= 10) {
                    cycle = 5;
                } else {
                    O++;
                }
            }
            case 5 -> {
                if (O <= 1) {
                    cycle = 4;
                } else {
                    O--;
                }
            }
        }
    }

    /* Cycle between different variables */
    public void switchVariable() {
        if (cycle == 0 || cycle == 1) {
            cycle = 2;
        } else if (cycle == 2 || cycle == 3) {
            cycle = 4;
        } else if (cycle == 4 || cycle == 5) {
            cycle = 0;
        }
    }

    /*

        OPERATION:
            - Draw new hypocycloid.
            - Small delay.
            - Refresh hypocycloid variables.
            - Clear canvas.

    */
    public void run() {
        while (true) {
            redraw();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            handleCycle();
            g.clearRect(0, 0, 600, 600);
        }
    }
}
