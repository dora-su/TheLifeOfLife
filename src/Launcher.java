import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Name: Launcher.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 8, 2019
 */
public class Launcher {
    /**
     * Main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            File audioFile = new File("graphics/music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Sound could not be loaded.");
        }

        new MainMenu();
    }
}
