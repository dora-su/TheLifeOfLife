
/**
 * Name: HouseSelection.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 6, 2019
 */

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HouseSelection extends JFrame {
    private Image soldImg;

    /**
     * Constructor
     *
     * @param player the player
     */
    HouseSelection(Player player) {
    	//setting screen size and settings
        this.setSize((int) (Game.scaleX * 1000), (int) (Game.scaleY * 800));
        this.setResizable(false);
        this.setUndecorated(true);
        // 250, 400
        this.setLocation((int) (Game.screenX / 2) - ((int) (Game.scaleX * 1000) / 2),
                ((int) (Game.screenY / 2) - ((int) (Game.scaleY * 800) / 2)));
        this.setFocusable(true);
        JPanel panel = new JPanel();

        this.add(panel);

        panel.setLayout(new GridLayout(2, 4));

        ArrayList<JButton> buttons = new ArrayList<JButton>();

        for (Property p : Game.properties) {

        	 // setImage to bought
            JButton button = new JButton(p.getImage());
            soldImg = Toolkit.getDefaultToolkit()
                    .getImage("graphics/homes/" + p.getName().toLowerCase() + "SOLD.png");
            soldImg = soldImg.getScaledInstance((int) (250 * Game.scaleX), (int) (400 * Game.scaleY),
                    java.awt.Image.SCALE_SMOOTH);
            if (!Game.soldProperties[Game.properties.indexOf(p)]) {
                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        player.addProperty(p);
                       
                        // confirm house purchase
                        String[] options = new String[]{"Confirm", "Back"};
                        int confirm = JOptionPane.showOptionDialog(null,
                                "Are you sure you want to purchase " + p.getName() + " for $" + p.getValue(),
                                "Confirm Your Purchase", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                options, options[0]);

                        if (confirm != 0) {
                            return;
                        }
                        // add property to player
                        player.addProperty(p);
                        // get image
                        ImageIcon pic = p.getImage();
                        Image scaledImg = pic.getImage();
                        scaledImg = scaledImg.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY),
                                java.awt.Image.SCALE_SMOOTH);

                        Game.myHouse.setIcon(new ImageIcon(scaledImg));
                        // remove money from player
                        player.removeMoney(p.getValue());
                        button.removeActionListener(this);
                        // remove property from players
                        player.c.output.println(player.c.userName);
                        player.c.output.println("/removep " + Game.properties.indexOf(p));
                        player.c.output.flush();

                        button.setIcon(new ImageIcon(soldImg));
                        // close window
                        dispose();
                    }

                });
            } else {
            	//set button icon 
                button.setIcon(new ImageIcon(soldImg));
            }
            // add buttons
            buttons.add(button);
            panel.add(button);

        }

        this.setVisible(true);
    }

}