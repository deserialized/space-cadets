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
    private AudioThreadHandler audioThreadHandler;
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

        /* Creates new audio listening thread */
        audioThreadHandler = new AudioThreadHandler(line);
        new Thread(audioThreadHandler).start();
    }

    /* Gets the current audio level */
    public int getAudioLevel() {
        return audioThreadHandler.getAudioLevel();
    }
}