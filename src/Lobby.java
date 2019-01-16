import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Lobby extends JFrame {

	private MyKeyListener keyListener;
	private ImageIcon icon;

	public static void main(String [] args) {
	new Game();
//	new Spinner();
//		Spinner.getRoll();
		//new MainMenu();
		//new Lobby();	`
		//new Rules();
	}
	
	JPanel lobbyPanel = new JPanel();
	//add components + modify appearance of the frame
	
	Lobby(){
		super("Lobby");
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(false);
		//this.setUndecorated(true);
		


		keyListener = new MyKeyListener();
		this.addKeyListener(keyListener); //adds the keylistener

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());

		this.setVisible(true);
		this.add(lobbyPanel);
	}

	/**
			* private class for the keylistener to check for key presses
     */
	private class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		/**
		 * check for key presses
		 *
		 */
		public void keyPressed(KeyEvent e) {
			//exit if esc is pressed
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

}
