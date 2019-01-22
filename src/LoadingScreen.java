/**
 * Name: LoadingScreen.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 9, 2019
 */

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {
    static JProgressBar loadingBar;
    static DefaultBoundedRangeModel model;
    private Image test = Toolkit.getDefaultToolkit().getImage("graphics/icon.png");
    private boolean loaded = false;

    /**
     * Constructor
     *
     * @param time the time to load
     */
    LoadingScreen(int time) {
        setSize(720, 720);
        this.setLocation((int) (Game.screenX / 2) - 360, ((int) (Game.screenY / 2) - 360));
        this.setUndecorated(true);
        model = new DefaultBoundedRangeModel(0, time, 0, time);
        loadingBar = new JProgressBar(model);
        JPanel mainPanel = new MainPanel();
        this.setContentPane(mainPanel);
        add(loadingBar);
        setVisible(true);
        Timer timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.getValue() >= time) {
                    loaded = true;
                    dispose();
                }
                model.setValue(model.getValue() + 1);

            }
        });
        timer.start();

    }

    /**
     * Gets if the bar has loaded
     *
     * @return loaded
     */
    public boolean getLoaded() {
        return loaded;
    }

    /**
     * Sets the value of loaded
     *
     * @param loaded if it has loaded
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    private class MainPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // required
            this.setDoubleBuffered(true);
            g.drawImage(test, 0, 0, null);
            repaint();
        }
    }
}
