package application;

/*

    AudioThreadHandler.java
    Written by Ben Hadlington
    - Continuously records audio.

*/

import javax.sound.sampled.TargetDataLine;
import java.lang.annotation.Target;

/*

    Class

*/

public class AudioThreadHandler extends Thread {
    private int audioLevel = 0;
    private final TargetDataLine line;

    public AudioThreadHandler(TargetDataLine line) {
        /* Starts listening to audio */
        line.start();
        this.line = line;
    }

    /* Gets the current audio level */
    public int getAudioLevel() {
        return audioLevel;
    }

    public void run() {
        /* Buffer for raw audio data */
        byte[] buffer = new byte[6000];

        try {
            /* Read mic data continuously */
            while (true) {
                /* If enough data then calculate RMS */
                if (line.read(buffer, 0, buffer.length) > 0) {
                    audioLevel = calculateRMS(buffer);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /* Formula for this found online: calculates the root-mean-square value for the audio data */
    private int calculateRMS(byte[] data) {
        long sum = 0;
        for(int i = 0; i < data.length; i++) {
            sum += data[i];
        }

        double dAvg = sum / data.length;

        double sumMeanSquare = 0d;
        for(int j = 0; j < data.length; j++) {
            sumMeanSquare = sumMeanSquare + Math.pow(data[j] - dAvg, 2d);
        }

        double averageMeanSquare = sumMeanSquare / data.length;
        return (int)(Math.pow(averageMeanSquare, 0.5d) + 0.5);
    }
}
