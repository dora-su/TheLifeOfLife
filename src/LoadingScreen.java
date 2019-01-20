/**
 * Name: LoadingScreen.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 2, 2019
 */
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {
	static JProgressBar loadingBar;
	static DefaultBoundedRangeModel model;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoadingScreen(5000);
	}
	
	LoadingScreen(int time) {
		setSize(500,300);
		this.setLocation((int)(Game.screenX/2) - 250, ((int)(Game.screenY/2) - 150));
		model = new DefaultBoundedRangeModel(0,time,0,time);
		loadingBar = new JProgressBar(model);
		add(loadingBar);
		setVisible(true);
		Timer timer = new Timer(1,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getValue() >= time) {
					dispose();
				}
				model.setValue(model.getValue()+1);
				
			}
		});
		timer.start();
	}
}
