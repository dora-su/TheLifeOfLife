import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lobby extends JFrame {
	
	public static void main(String [] args) {
		new GamePanel();
//	new Spinner();
//		Spinner.getRoll();
		//new MainMenu();
		//new Lobby();
		//new Rules();
	}
	
	JPanel lobbyPanel = new JPanel();
	
	Lobby(){
		
		super("Lobby");
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(false);
		//this.setUndecorated(true);
		
		this.setVisible(true);
		this.add(lobbyPanel);
		
	}
}
