/**
 * Name: Launcher.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 8, 2019
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Launcher {
    /**
     * Main method
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    	// Start music
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("graphics/music.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Sound could not be loaded.");
        }
        
        // Start loading Screen
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
        // start main menu
        new MainMenu();
    }
}
