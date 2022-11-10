package application;

/*

    AudioHandler.java
    Written by Ben Hadlington (+ lots of help from online)
    - Collects audio from microphone and reduces it to a scalar value.

*/

import javax.sound.sampled.*;

/*

    Class

*/

public class AudioHandler {
    private TargetDataLine line;
    private AudioFormat format;
    private DataLine.Info info;

    public AudioHandler() {
        /* Sets up a line for the default microphone */
        format = new AudioFormat(42000.0f, 16, 1, true, true);
        info = new DataLine.Info(TargetDataLine.class, format);

        /* No default microphone */
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Cannot record audio. No default input device detected.");
        }

        /* Open the audio line */
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public int captureAudio() {
        line.start();
        return record();
    }

    private int record() {
        /* Buffer for raw audio data */
        byte[] buffer = new byte[600];

        try {
            /* Read mic data continuously */
            while (true) {
                /* If enough data then calculate RMS */
                if (line.read(buffer, 0, buffer.length) > 0) {
                    int audioLevel = calculateRMS(buffer);
                    line.stop();
                    return audioLevel;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return -1;
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