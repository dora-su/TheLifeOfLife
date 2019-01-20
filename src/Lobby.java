import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Lobby extends JFrame {

	private MyKeyListener keyListener;
	private ImageIcon icon;
	
	JPanel lobbyPanel = new JPanel();
	//add components + modify appearance of the frame
	
	Lobby(Client c){
		super("Lobby");
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(false);
		//this.setUndecorated(true);
		keyListener = new MyKeyListener();
		this.addKeyListener(keyListener); //adds the keylistener

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());
		JButton chat = new JButton("Chat");
		chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.window1.setVisible(!c.window1.isVisible());
			}
		});
		
		JCheckBox ready = new JCheckBox("Ready");
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.output.println(c.userName);
				if (ready.isSelected()) {
					c.output.println("/ready");
				} else {
					c.output.println("/unready");
				}
				c.output.flush();
			}
		});
		
		lobbyPanel.add(ready);
		lobbyPanel.add(chat);
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
