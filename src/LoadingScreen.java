import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {
    static JProgressBar loadingBar;
    static DefaultBoundedRangeModel model;

    static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static double screenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    double scale = (screenX * screenY) / (1280 * 800);

    double scaleX = screenX / 1280;
    double scaleY = screenY / 800;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new LoadingScreen(100);
    }

    LoadingScreen(int time) {
        setSize(500, 300);
        this.setLocation((int) (((Game.screenX / 2) - 250) * scaleX), ((int) (Game.screenY / 2) - 150));
        model = new DefaultBoundedRangeModel(0, time, 0, time);
        loadingBar = new JProgressBar(model);
        add(loadingBar);
        setVisible(true);
        Timer timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.getValue() >= time) {
                    dispose();
                }
                model.setValue(model.getValue() + 1);

            }
        });
        timer.start();
    }
}
