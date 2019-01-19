import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PopUpTest {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("test");
        frame.setSize(500,500);
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.setVisible(true);

        JFrame frame2 = new JFrame("test");
        frame2.setSize(400,317);
//        frame2.getContentPane().setBackground(new Color(255,255,255,20));
        frame2.setUndecorated(true);
//        frame2.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        frame2.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));

        JPanel panel = new JPanel();       ImageIcon image =new ImageIcon("graphics/optionPane.png");
        JLabel picLabel = new JLabel(image);



//        panel.setOpaque(false);
//
//        JLabel label = new JLabel("gergergeg");
   panel.add(picLabel);
//
//        panel.revalidate();
//        panel.repaint();
//panel.setBackground(new Color(80,255,255,0));
panel.setOpaque(false);
        panel.setVisible(true);
        frame2.setUndecorated(true);
        frame2.add(panel);

        frame2.setVisible(false);



        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                frame.setEnabled(false);
                frame2.setEnabled(true);
                frame2.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.setVisible(true);


    }
}
