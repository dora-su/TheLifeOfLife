/**
 * Name: Launcher.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 8, 2019
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;


public class Launcher {
    /**
     * Main method
     *
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
        LoadingScreen l = new LoadingScreen(2000);
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (l.getLoaded()) {
                break;
            }
        }
        new MainMenu();
    }
}
