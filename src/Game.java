
/**
 * Name: Game.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 6, 2019
 */

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {

    //Declaring class Variables

    private JPanel gameAreaPanel;
    static JFrame gameFrame;
    private Image map, popWindow, exitPic;
    private Image[] mangoes;
    private static Image careerPlaceHolder, housePlaceHolder;
    private Image spinPic, hoverSpinPic;
    private Image chatPic, hoverChatPic;
    static Image startUpPic;
    private ImageIcon icon;
    int turn;

    private static ArrayList<ActionTile> path;
    static ArrayList<Career> collegeCareers;
    static ArrayList<Career> normalCareers;
    static ArrayList<Property> properties;
    static Career startUp;
    static boolean[] soldProperties;

    //variables for spinner
    private static boolean finished = false;
    private static int rotate;
    private static Random rand = new Random();
    private static Client c;
    private JButton chat, exit;
    private static JButton spin;
    static JButton myCareer;
    static JButton myHouse;
    private Player player;
    private JFrame careerFrame, houseFrame;
    private Game g;
    private Polygon p;

    static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static double screenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private double scale = (screenX * screenY) / (1920 * 1200.0);

    static double scaleX = screenX / 1920.0;
    static double scaleY = screenY / 1200.0;

    private DecimalFormat myFormatter;

    private Font font1, font2;

    /**
     * Constructor
     *
     * @param c  the client
     * @param pl the player username
     */
    Game(Client c, String pl) {
        super("My Game");
        g = this;
        Game.c = c;
        turn = 0;
        player = c.map.get(pl);
        // Set the frame to full screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize((int) screenX, (int) screenY);
        this.setUndecorated(true);
        //setting icon
        icon = new ImageIcon("graphics/icon.png");
        this.setIconImage(icon.getImage());

        gameFrame = this;

        myFormatter = new DecimalFormat("###,###.###");
        
        // hard coded values for tile coordinates
        path = new ArrayList<ActionTile>();
        path.add(new MoneyTile((int) (scaleX * 90), (int) (scaleY * 444), null, 0));
        path.add(new ChoiceTile((int) (scaleX * 174), (int) (scaleY * 446), "Would You Like to Go to College", 18));
        path.add(new MoneyTile((int) (scaleX * 240), (int) (scaleY * 452), "Buy work attire", -100));
        path.add(new MoneyTile((int) (scaleX * 303), (int) (scaleY * 431), "Receive entry bonus", 3000));
        path.add(new MoneyTile((int) (scaleX * 321), (int) (scaleY * 371), "Birthday gift from coworkers", 200));
        path.add(new PayDayTile((int) (scaleX * 284), (int) (scaleY * 314)));

        path.add(new MoneyTile((int) (scaleX * 248), (int) (scaleY * 269), "Catch the flu. Pay hospital fees", -300));
        path.add(new MoneyTile((int) (scaleX * 192), (int) (scaleY * 218), "Buy birthday gift for your friend", -100));
        path.add(new MoneyTile((int) (scaleX * 144), (int) (scaleY * 176), "Get rhinoplasty.", -3000));
        path.add(new PayDayTile((int) (scaleX * 129), (int) (scaleY * 104)));

        path.add(new MoneyTile((int) (scaleX * 188), (int) (scaleY * 72), "Star in a TV Commercial", 1400));
        path.add(new MoneyTile((int) (scaleX * 255), (int) (scaleY * 85), "Sell old clothing", 200));
        path.add(new MoneyTile((int) (scaleX * 318), (int) (scaleY * 110), "Take pole dancing lessons", -200));
        path.add(new PayDayTile((int) (scaleX * 389), (int) (scaleY * 134)));

        path.add(new MoneyTile((int) (scaleX * 440), (int) (scaleY * 178),
                "Caught by the police for drinking and driving", -500));
        path.add(new MoneyTile((int) (scaleX * 464), (int) (scaleY * 234), "Inherited $3000 from your grandma", 3000));
        path.add(new MoneyTile((int) (scaleX * 480), (int) (scaleY * 298), "Shopping spree!", -500));
        path.add(new PayDayTile((int) (scaleX * 496), (int) (scaleY * 364))); //advance to tile 37 from this path

        path.add(new MoneyTile((int) (scaleX * 164), (int) (scaleY * 516), "Buy a new laptop", -2000));
        path.add(new MoneyTile((int) (scaleX * 166), (int) (scaleY * 576),
                "Received a 98% in Calc101. Parents stop paying for your residence!", -10000));
        path.add(new MoneyTile((int) (scaleX * 171), (int) (scaleY * 642),
                "Maintained a 3.5 GPA. Receive a merit scholarship!", 4500));
        path.add(new MoneyTile((int) (scaleX * 179), (int) (scaleY * 708), "Participated in a Psych101 experiment",
                200));
        path.add(new MoneyTile((int) (scaleX * 185), (int) (scaleY * 775), "Spent too much at Muji!", -300));
        path.add(new MoneyTile((int) (scaleX * 210), (int) (scaleY * 835), "Win a raffle!", 300));
        path.add(new Tile((int) (scaleX * 251), (int) (scaleY * 886), "Hung out with friends who covered your bill!"));
        path.add(new Tile((int) (scaleX * 313), (int) (scaleY * 920),
                "You celebrated your birthday! Just another few years to go!"));
        path.add(new Tile((int) (scaleX * 379), (int) (scaleY * 941), "You joined the badminton team!"));
        path.add(new Tile((int) (scaleX * 449), (int) (scaleY * 955), "Congratulations on Athlete of the Year."));
        path.add(new MoneyTile((int) (scaleX * 514), (int) (scaleY * 940),
                "You failed CS301. You must repay course fees. ", 4000));
        path.add(new Tile((int) (scaleX * 556), (int) (scaleY * 884),
                "Oh no. You forgot to hand in your assignment! Your GPA dropped to 3.2."));
        path.add(new Tile((int) (scaleX * 560), (int) (scaleY * 821), "Congratulations on finishing Junior Year."));
        path.add(new MoneyTile((int) (scaleX * 565), (int) (scaleY * 752), "Receive an internship at Google!", 25000));
        path.add(new MoneyTile((int) (scaleX * 560), (int) (scaleY * 688), "Win a hackathon!", 3000));
        path.add(new MoneyTile((int) (scaleX * 550), (int) (scaleY * 621),
                "Congratulations on winning another merit scholarship.", 4000));
        path.add(new MoneyTile((int) (scaleX * 548), (int) (scaleY * 563),
                "Congratulations on graduating university! Take a grad trip to Paris", 0));
        path.add(new Tile((int) (scaleX * 535), (int) (scaleY * 492)));
        path.add(new Tile((int) (scaleX * 513), (int) (scaleY * 433),
                "Oh no you showed up late to work. Better be earlier next time!"));
        //start of new path(Tile 37)
        path.add(new MoneyTile((int) (scaleX * 575), (int) (scaleY * 419), "Buy christmas gifts for all your friends",
                400));
        path.add(new PayDayTile((int) (scaleX * 641), (int) (scaleY * 394)));
        path.add(new MoneyTile((int) (scaleX * 669), (int) (scaleY * 336), "Save an endangered species", 20000));
        path.add(new MoneyTile((int) (scaleX * 680), (int) (scaleY * 270), "Pay for wedding venue fees!", -10000));
        path.add(new PayDayTile((int) (scaleX * 678), (int) (scaleY * 209)));
        path.add(new Tile((int) (scaleX * 681), (int) (scaleY * 140))); //tile to get married on (42)
        //tile after marriage
        path.add(new MoneyTile((int) (scaleX * 705), (int) (scaleY * 80),
                "Fall off a cruise ship. Thankfully you’re still alive, gotta pay those medical bills.", -10000));
        path.add(new Tile((int) (scaleX * 770), (int) (scaleY * 59), "Congratulations on your new child!"));
        path.add(new PayDayTile((int) (scaleX * 833), (int) (scaleY * 75)));
        path.add(new MoneyTile((int) (scaleX * 888), (int) (scaleY * 117), "Buy sailboat", -30000));
        path.add(new MoneyTile((int) (scaleX * 899), (int) (scaleY * 177), "Go skydiving", -1000));
        path.add(new MoneyTile((int) (scaleX * 903), (int) (scaleY * 244), "Adopt a cat", -400));
        path.add(new PayDayTile((int) (scaleX * 892), (int) (scaleY * 309)));
        path.add(new Tile((int) (scaleX * 849), (int) (scaleY * 360))); //tile to buy house on (50)
        path.add(new MoneyTile((int) (scaleX * 850), (int) (scaleY * 426), "Pay property taxes!", -2000));
        path.add(new MoneyTile((int) (scaleX * 899), (int) (scaleY * 470), "Pay your broker commission.", -30000));
        path.add(new Tile((int) (scaleX * 939), (int) (scaleY * 526),
                "You wasted 600 sheets of paper making snowflakes. Be more environmentally friendly!"));
        path.add(new PayDayTile((int) (scaleX * 927), (int) (scaleY * 591)));
        path.add(new MoneyTile((int) (scaleX * 886), (int) (scaleY * 642),
                "You fell into the lake and nearly drowned. You decided to take swimming lessons to prevent this again.",
                -500));
        //new path (56)
        path.add(new ChoiceTile((int) (scaleX * 855), (int) (scaleY * 691), "Would you like to go to night school?",
                67));

        path.add(new Tile((int) (scaleX * 818), (int) (scaleY * 754), "Gone fishin!"));
        path.add(new PayDayTile((int) (scaleX * 812), (int) (scaleY * 820)));
        path.add(new MoneyTile((int) (scaleX * 855), (int) (scaleY * 868),
                "You invested in beanie babies 10 years ago. Now you have made $200000", 200000));
        path.add(new Tile((int) (scaleX * 917), (int) (scaleY * 900),
                "You went to a party and had too much redbull. Please donate pass out next time."));

        path.add(new Tile((int) (scaleX * 985), (int) (scaleY * 924), "Congratulations on your new child!"));
        path.add(new PayDayTile((int) (scaleX * 1049), (int) (scaleY * 941)));
        //good
        path.add(new MoneyTile((int) (scaleX * 1114), (int) (scaleY * 955), "Send your child to Olympiads.", -1000));
        path.add(new MoneyTile((int) (scaleX * 1187), (int) (scaleY * 957), "Forget to pay taxes.", -200000));
        path.add(new MoneyTile((int) (scaleX * 1254), (int) (scaleY * 959), "Caught for using your phone when driving",
                -20000));
        path.add(new PayDayTile((int) (scaleX * 1320), (int) (scaleY * 956)));

        //second path(67)
        path.add(new MoneyTile((int) (scaleX * 907), (int) (scaleY * 733), "Pay for textbooks. ", -2500));
        path.add(new MoneyTile((int) (scaleX * 966), (int) (scaleY * 750),
                "Congrats you used your AP credits from high school! Refund part of your tuition", 7000));
        path.add(new MoneyTile((int) (scaleX * 1038), (int) (scaleY * 744), "Buy too many pencils", 100));
        path.add(new Tile((int) (scaleX * 1098), (int) (scaleY * 735),
                "You received a certificate of merit for your 100% in gym!"));
        path.add(new Tile((int) (scaleX * 1171), (int) (scaleY * 740), "You rejected a job offer by Apple."));
        path.add(new MoneyTile((int) (scaleX * 1230), (int) (scaleY * 761), "Pay for insurance", -2000));
        path.add(new MoneyTile((int) (scaleX * 1291), (int) (scaleY * 793),
                "You fell off a cliff while hiking with your friends. Thankfully you didn't die, but there's medical bills. ",
                -300000));
        path.add(new Tile((int) (scaleX * 1338), (int) (scaleY * 826), "Join a sorority/fraternity!"));

        //75
        path.add(new Tile((int) (scaleX * 1381), (int) (scaleY * 882)));

        path.add(new Tile((int) (scaleX * 1391), (int) (scaleY * 949), "Congratulations on your new child!"));
        //correct coords up to here

        //path 77
        path.add(new MoneyTile((int) (scaleX * 1464), (int) (scaleY * 931),
                "Chop down the tree on your front lawn. Fined by the city", -30000));
        path.add(new Tile((int) (scaleX * 1525), (int) (scaleY * 915),
                "Take a nice hike in Algonquin Park with your family!"));
        path.add(new PayDayTile((int) (scaleX * 1577), (int) (scaleY * 874)));

        path.add(new Tile((int) (scaleX * 1600), (int) (scaleY * 807),
                "You Failed The CCC"));
        path.add(new MoneyTile((int) (scaleX * 1590), (int) (scaleY * 751), "Your lost you waller", -300));
        path.add(new MoneyTile((int) (scaleX * 1523), (int) (scaleY * 704),
                "Acccidently forgot to show up to work.", -3000));
        path.add(new PayDayTile((int) (scaleX * 1460), (int) (scaleY * 700)));
        path.add(new Tile((int) (scaleX * 1393), (int) (scaleY * 686),
                "Oh no! You and your spouse are having a lot of arguments"));
        path.add(new MoneyTile((int) (scaleX * 1325), (int) (scaleY * 678), "Buy 1500 Junior Chickens", -300));
        path.add(new MoneyTile((int) (scaleX * 1257), (int) (scaleY * 666),
                "Accidentally whipped the Wii remote at TV. Buy a new TV.", -3000));
        path.add(new PayDayTile((int) (scaleX * 1193), (int) (scaleY * 650)));
        path.add(new MoneyTile((int) (scaleX * 1136), (int) (scaleY * 613), "Win the lottery", 100000));
        path.add(new MoneyTile((int) (scaleX * 1151), (int) (scaleY * 549), "Invent a new programming language",
                190000));
        path.add(new MoneyTile((int) (scaleX * 1193), (int) (scaleY * 503), "Donate to a Mango Charity", -300000));
        path.add(new PayDayTile((int) (scaleX * 1239), (int) (scaleY * 449)));

        path.add(new MoneyTile((int) (scaleX * 1244), (int) (scaleY * 393),
                "Receive apology money from Richmond Hill High School.", 30000));
        //stop tile
        path.add(new ChoiceTile((int) (scaleX * 1214), (int) (scaleY * 326), "Would you like to have your own start up",
                99));

        path.add(new Tile((int) (scaleX * 1183), (int) (scaleY * 276),
                "You got drunk partying with friends. Thankfully your liver didn't fail (yet)."));
        path.add(new PayDayTile((int) (scaleX * 1155), (int) (scaleY * 212)));
        path.add(new MoneyTile((int) (scaleX * 1142), (int) (scaleY * 152), "Win a table flipping tournament", 6000));

        path.add(new MoneyTile((int) (scaleX * 1183), (int) (scaleY * 103), "", 103)); // INSERT MESSAGE HERE
        path.add(new Tile((int) (scaleX * 1245), (int) (scaleY * 89), "Organize a hackathon!"));
        path.add(new PayDayTile((int) (scaleX * 1305), (int) (scaleY * 79)));
        //96
        path.add(new Tile((int) (scaleX * 1386), (int) (scaleY * 84), "Congratulations on your new child!"));
        path.add(new MoneyTile((int) (scaleX * 1446), (int) (scaleY * 98),
                "Participate in the annual sadposting conference.", 0));
        path.add(new MoneyTile((int) (scaleX * 1516), (int) (scaleY * 134),
                "Burn your Grade 11 English homework in a literature burning riot.", 0));

        //path 2
        path.add(new PayDayTile((int) (scaleX * 1271), (int) (scaleY * 299)));
        path.add(new MoneyTile((int) (scaleX * 1325), (int) (scaleY * 266),
                "Win the international bubble tea drinking competition with a total of 400 drinks drank.", 200000));
        path.add(new MoneyTile((int) (scaleX * 1391), (int) (scaleY * 246),
                "Received a $50000 grant from the government", 50000));//don tthink this is rigith
        path.add(new MoneyTile((int) (scaleX * 1459), (int) (scaleY * 261),
                "You accidentally set part of your house on fire. Pay for repairs.", -30000));
        path.add(new MoneyTile((int) (scaleX * 1524), (int) (scaleY * 267),
                "Received a small no-need-for-return loan of $20000 from Donald Trump.", 20000));
        path.add(new MoneyTile((int) (scaleX * 1585), (int) (scaleY * 233),
                "Congrats on opening up your startup and having 10 employees.", 0));
        //new stop sign
        path.add(new Tile((int) (scaleX * 1601), (int) (scaleY * 167)));
        path.add(new PayDayTile((int) (scaleX * 1572), (int) (scaleY * 111)));
        path.add(new MoneyTile((int) (scaleX * 1645), (int) (scaleY * 89), "Win the Nobel Prize for creating TLAP",
                200000));
        path.add(new MoneyTile((int) (scaleX * 1702), (int) (scaleY * 77),
                "You got drunk and bought bubble tea for everyone you saw.", -150000));

        path.add(new MoneyTile((int) (scaleX * 1780), (int) (scaleY * 85), "You wrote an award-winning haiku!", 2000));
        path.add(new PayDayTile((int) (scaleX * 1821), (int) (scaleY * 131)));
        //stop
        path.add(new Tile((int) (scaleX * 1818), (int) (scaleY * 209)));

        path.add(new MoneyTile((int) (scaleX * 1810), (int) (scaleY * 270), "Pay for your brother's funeral.", -8000));
        path.add(new MoneyTile((int) (scaleX * 1802), (int) (scaleY * 337), "Congrats on your game show win!", 30000));
        path.add(new MoneyTile((int) (scaleX * 1788), (int) (scaleY * 399), "Remodel your home.", -20000));
        path.add(new MoneyTile((int) (scaleX * 1775), (int) (scaleY * 459), "Buy a yacht.", -1000000));
        path.add(new MoneyTile((int) (scaleX * 1767), (int) (scaleY * 530), "Hold a garage sale.", 4000));
        path.add(new MoneyTile((int) (scaleX * 1766), (int) (scaleY * 593),
                "You received a lot of money from your long-term investment.", 500000));
        path.add(new MoneyTile((int) (scaleX * 1769), (int) (scaleY * 657),
                "You have stomach cancer. Pay your medical bills.", 30000));
        path.add(new MoneyTile((int) (scaleX * 1778), (int) (scaleY * 723),
                "Throw yourself a birthday party. You have received a lot of gifts from friends!", 2000));

        //final stop
        path.add(new Tile((int) (scaleX * 1790), (int) (scaleY * 786)));

        //creating images for non college careers
        ImageIcon chef = new ImageIcon("graphics/careers/chef.png");
        ImageIcon flightAttendant = new ImageIcon("graphics/careers/flightattendant.png");
        ImageIcon uberDriver = new ImageIcon("graphics/careers/uberDriver.png");
        ImageIcon stripper = new ImageIcon("graphics/careers/stripper.png");
        ImageIcon roadWorker = new ImageIcon("graphics/careers/roadWorker.png");

        //getting images for college careers
        ImageIcon police = new ImageIcon("graphics/careers/police.png");
        ImageIcon math = new ImageIcon("graphics/careers/math.png");
        ImageIcon nurse = new ImageIcon("graphics/careers/nurse.png");
        ImageIcon softwareEngineer = new ImageIcon("graphics/careers/softwareEngineer.png");
        ImageIcon doctor = new ImageIcon("graphics/careers/doctor.png");

        //adding non college careers and setting their salary,name and picture
        normalCareers = new ArrayList<Career>();
        normalCareers.add(new Career("Police", 125000, police));
        normalCareers.add(new Career("Math", 115000, math));
        normalCareers.add(new Career("Nurse", 105000, nurse));
        normalCareers.add(new Career("Doctor", 180000, doctor));
        normalCareers.add(new Career("Software Engineer", 300000, softwareEngineer));

        //adding college careers and setting their salary name and picture
        collegeCareers = new ArrayList<Career>();
        collegeCareers.add(new Career("Chef", 48000, chef));
        collegeCareers.add(new Career("Flight Attendant", 50000, flightAttendant));
        collegeCareers.add(new Career("Uber Driver", 72000, uberDriver));
        collegeCareers.add(new Career("Stripper", 70000, stripper));
        collegeCareers.add(new Career("Road Worker", 40000, roadWorker));

        //adding the start up special career
        startUpPic = Toolkit.getDefaultToolkit().getImage("graphics/careers/startup.png");
        startUpPic = startUpPic.getScaledInstance((int) (250 * scaleX), (int) (400 * scaleY), Image.SCALE_DEFAULT);

        startUp = new Career("Start Up", 0, new ImageIcon(startUpPic));

        //getting pictures for the properties in game
        ImageIcon mansion = new ImageIcon("graphics/homes/mansion.png");
        ImageIcon castle = new ImageIcon("graphics/homes/castle.png");
        ImageIcon condo = new ImageIcon("graphics/homes/condo.png");
        ImageIcon detached = new ImageIcon("graphics/homes/detached.png");
        ImageIcon hut = new ImageIcon("graphics/homes/hut.png");
        ImageIcon igloo = new ImageIcon("graphics/homes/igloo.png");
        ImageIcon bungalow = new ImageIcon("graphics/homes/bungalow.png");
        ImageIcon farm = new ImageIcon("graphics/homes/farm.png");

        //adding properties to its arraylist with its name, value and image
        properties = new ArrayList<Property>();
        properties.add(new Property("Mansion", 2200000, mansion));
        properties.add(new Property("Castle", 4200000, castle));
        properties.add(new Property("Condo", 550000, condo));
        properties.add(new Property("Detached", 1400000, detached));
        properties.add(new Property("Farm", 850000, farm));
        properties.add(new Property("Hut", 150000, hut));
        properties.add(new Property("Igloo", 200000, igloo));
        properties.add(new Property("Bungalow", 1000000, bungalow));

        soldProperties = new boolean[properties.size()];

        // resizing the properties to size based off screen
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            ImageIcon pic = p.getImage();

            Image image = pic.getImage();
            image = image.getScaledInstance((int) (250 * scaleX), (int) (400 * scaleY), java.awt.Image.SCALE_SMOOTH);
            p.setImage(new ImageIcon(image));
        }
        //getting picture of the map and scaling it
        map = Toolkit.getDefaultToolkit().getImage("graphics/board.png");
        map = map.getScaledInstance((int) screenX, (int) screenY, Image.SCALE_DEFAULT);

        //getting picture of player and scaling it
        mangoes = new Image[6];
        for (int i = 0; i < 6; i++) {
            mangoes[i] = Toolkit.getDefaultToolkit().getImage("graphics/mango" + (i + 1) + ".png");
            mangoes[i] = mangoes[i].getScaledInstance((int) (45 * scaleX), (int) (45 * scaleY), Image.SCALE_DEFAULT);
        }
        //getting picture of spinner and scaling it
        spinPic = Toolkit.getDefaultToolkit().getImage("graphics/spin_button.png");
        spinPic = spinPic.getScaledInstance((int) (149 * scaleX), (int) (149 * scaleY), Image.SCALE_DEFAULT);

        hoverSpinPic = Toolkit.getDefaultToolkit().getImage("graphics/spin_hover.png");
        hoverSpinPic = hoverSpinPic.getScaledInstance((int) (149 * scaleX), (int) (149 * scaleY), Image.SCALE_DEFAULT);

        //getting picture of chat and scaling it
        chatPic = Toolkit.getDefaultToolkit().getImage("graphics/chat.png");
        chatPic = chatPic.getScaledInstance((int) (128 * scaleX), (int) (128 * scaleY), Image.SCALE_DEFAULT);

        hoverChatPic = Toolkit.getDefaultToolkit().getImage("graphics/chat_Hover.png");
        hoverChatPic = hoverChatPic.getScaledInstance((int) (128 * scaleX), (int) (128 * scaleY), Image.SCALE_DEFAULT);

        //getting picture of exit bar and scaling it
        exitPic = Toolkit.getDefaultToolkit().getImage("graphics/exit.png");
        exitPic = exitPic.getScaledInstance((int) (154 * scaleX), (int) (130 * scaleY), Image.SCALE_DEFAULT);

        //getting popup window picture
        popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

        //getting picture for initial career holder and scaling it
        careerPlaceHolder = Toolkit.getDefaultToolkit().getImage("graphics/careers/career.png");
        careerPlaceHolder = careerPlaceHolder.getScaledInstance((int) (114 * scaleX), (int) (184 * scaleY),
                Image.SCALE_DEFAULT);

        //getting picture for initial career holder and scaling it
        housePlaceHolder = Toolkit.getDefaultToolkit().getImage("graphics/homes/home.png");
        housePlaceHolder = housePlaceHolder.getScaledInstance((int) (114 * scaleX), (int) (184 * scaleY),
                Image.SCALE_DEFAULT);

        //creating the game area panel
        gameAreaPanel = new GameAreaPanel();
        gameAreaPanel.setLayout(new BoxLayout(gameAreaPanel, BoxLayout.Y_AXIS));

        //creating the interactive bottom panel on the screen
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setOpaque(false);
        gameAreaPanel.add(Box.createVerticalStrut((int) (1012 * scaleY)));
        gameAreaPanel.add(bottomPanel);

        this.setContentPane(gameAreaPanel);
        this.requestFocusInWindow();
        //		this.setUndecorated(true);

        //creating polygon for spinner
        p = new Polygon();
        p.addPoint((int) (scaleX * 1514), (int) (scaleY * 300));
        p.addPoint((int) (scaleX * 1534), (int) (scaleY * 300));
        p.addPoint((int) (scaleX * 1524), (int) (scaleY * 340));
        Timer timer = new Timer(1, new SpinnerListener());
        timer.start();

        //creating spin button to spin the spinner
        spin = new JButton(new ImageIcon(spinPic));
        spin.setBackground(null);
        spin.setContentAreaFilled(false);
        spin.setBorderPainted(false);
        spin.setFocusPainted(false);
        spin.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                spin.setIcon(new ImageIcon(hoverSpinPic));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                spin.setIcon(new ImageIcon(spinPic));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });
        spin.addActionListener(new RollListener());
        spin.setVerticalAlignment(JButton.CENTER);

        //creating exit button for user
        exit = new JButton(new ImageIcon(exitPic));
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.setBorderPainted(false);
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                System.exit(-1);

            }

        });

        //adding exit and spin to the bottom panel
        bottomPanel.add(exit);
        bottomPanel.add(spin);
        bottomPanel.add(Box.createHorizontalStrut((int) (scaleX * 205)));

        myHouse = new JButton(new ImageIcon(housePlaceHolder));
        myHouse.setContentAreaFilled(false);
        myHouse.setBorderPainted(false);
        myHouse.setFocusPainted(false);
        myHouse.addMouseListener(new MouseListener() {
            //JFrame houseFrame;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //	frame.setOpacity(0.5f);

                //creating a new frame in the center of the scree
                houseFrame = new JFrame();
                houseFrame.setSize(250, 400);
                houseFrame.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
                houseFrame.setUndecorated(true);
                houseFrame.setResizable(false);

                if (player.getProperty() != null) {
                    //if the player owns a property then enlarge the photo
                    JLabel image = new JLabel(
                            new ImageIcon("graphics/homes/" + player.getProperty().getName().toLowerCase() + ".png"));
                    houseFrame.add(image);

                    houseFrame.setVisible(true);
                }
                //houseFrame.setAlwaysOnTop(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //if not hovering over don't show the frame
                houseFrame.dispose();
                //frame.dispose();

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

        });

        //adding the my houses to the bottom panel
        bottomPanel.add(myHouse);
        bottomPanel.add(Box.createHorizontalStrut((int) (scaleX * 0)));

        //Jbutton in the bottom bar where player's carrer is shown
        myCareer = new JButton(new ImageIcon(careerPlaceHolder));
        myCareer.setContentAreaFilled(false);
        myCareer.setBorderPainted(false);
        myCareer.setFocusPainted(false);
        myCareer.addMouseListener(new MouseListener() {

            //JFrame frame;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //if the player has been given a career and if the user hovers over it then enlarge the photo
                careerFrame = new JFrame();
                careerFrame.setSize(250, 400);
                careerFrame.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
                careerFrame.setUndecorated(true);
                careerFrame.setResizable(false);

                if (player.getCareer() != null) {

                    String name = player.getCareer().getCareerName().trim();
                    // check if name is empty
                    if (name.indexOf(" ") > 0) {
                        int index = name.indexOf(" ");
                        name = name.substring(0, index) + name.substring(index + 1);
                    }
                    // display images
                    JLabel image = new JLabel(new ImageIcon(
                            "graphics/careers/" + name.toLowerCase() + ".png"));
                    careerFrame.add(image);
                    careerFrame.setVisible(true);
                }
                careerFrame.setAlwaysOnTop(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                careerFrame.dispose();
                //frame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

        });

        //adding my carrer to the bottom panel
        bottomPanel.add(myCareer);
        bottomPanel.add(Box.createRigidArea(new Dimension((int) (scaleX * 900), 0)));

        chat = new JButton(new ImageIcon(chatPic));
        chat.setContentAreaFilled(false);
        chat.setBorderPainted(false);
        chat.setFocusPainted(false);
        chat.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chat.setIcon(new ImageIcon(hoverChatPic));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chat.setIcon(new ImageIcon(chatPic));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

        });
        bottomPanel.add(chat);
        chat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                c.window1.setVisible(!c.window1.isVisible());
            }

        });
        chat.setVerticalAlignment(JButton.CENTER);

        this.setVisible(true);

        //importing the custom fonts 
        try {
            font1 = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/fonts/josefin.ttf")).deriveFont((float) (scale * 80));
        } catch (FontFormatException e1) {
            e1.printStackTrace();
            System.out.println("Font format incorrect.");
        } catch (IOException e1) {
        	System.out.println("Font file not found.");
            e1.printStackTrace();
        }

        try {
            font2 = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/fonts/langdon.ttf")).deriveFont((float) (scale * 40));
        } catch (FontFormatException e1) {
			e1.printStackTrace();
			System.out.println("Font format incorrect.");
		} catch (IOException e1) {
			System.out.println("Font file not found.");
			e1.printStackTrace();
		}

    } // End of Constructor

    /**
     * --------- INNER CLASSES -------------
     **/
    private class GameAreaPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);

			//drawing background map
			g.drawImage(map, 0, 0, null);
			for (Player p : c.players) {
				if (!p.isMoving && p.newTile > p.getTile()) {
					p.setTile(p.newTile);
					p.moved = true;
				}
				g.drawImage(mangoes[c.players.indexOf(p)], path.get(p.getTile()).getX() - 17,
						path.get(p.getTile()).getY() - 17, null);
			}
			// draw bottom game exit image
			g.setFont(font1);
			String money = myFormatter.format(player.getMoney());
			g.drawString(money, (int) (995 * scaleX), (int) (1135 * scaleY));

			//drawing player icon
			Image playerIcon = mangoes[c.players.indexOf(player)];
			//playerIcon = playerIcon.getScaledInstance(2*(int) (45 * scaleX), 2*(int) (45 * scaleY), Image.SCALE_DEFAULT);
			g.drawImage(playerIcon, (int) (1590 * scaleX), (int) (1045 * scaleY), null);

			//showing family
			if (player.family != 0) {
				g.setColor(Color.pink);
				g.fillOval((int) (1490 * scaleX), (int) (1100 * scaleY), 40, 40);
				for (int i = 1; i < player.family; i++) {
					g.setColor(new Color(0, 0, 182, 155));
					g.fillOval((int) ((1490 * scaleX) + (i * 60)), (int) (1100 * scaleY), 40, 40);
				}
			}

			// draw player name on screen
			g.setColor(Color.BLACK);
			g.setFont(font2);
			g.drawString(player.getName(), (int) (1470 * scaleX), (int) (1082 * scaleY));

			// spinner
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("graphics/tempSpinner.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			AffineTransform at = AffineTransform.getTranslateInstance(scaleX * 1362, scaleY * 323);
			at.scale(scaleX, scaleY);
			at.rotate(Math.toRadians(rotate), image.getWidth() / 2, image.getHeight() / 2);
			((Graphics2D) g).drawImage(image, at, null);

			// adds pointer
			g.setColor(Color.WHITE);
			g.fillPolygon(p);
			try {
				// Thread.sleep(100);
			} catch (Exception e) {
			}
			repaint();
		}

    }

    static double vel, accel, distance;
    static boolean move;

    /**
     * Spins the wheel
     *
     * @param dist the distance to be spun
     * @param b    if it is the players move
     */
    static void spin(double dist, boolean b) {
        distance = dist;
        vel = dist / 80.0;
        accel = -dist / 10000.0;
        move = b;
    }

    /**
     * SpinnerListener
     */
    private class SpinnerListener implements ActionListener {
    	
        boolean running = false;
        
        @Override
        public void actionPerformed(ActionEvent arg0) {
            gameAreaPanel.setEnabled(false);
            // if currently spinning
            if (running) {
                // checks if dist is over
                if (vel > 0) {
                    rotate -= vel;
                    distance = 0;
                    // end dist
                } else {
                	// velocity is 0 and spinner has stopped
                    for (int j = 0; j < 5; j++) {
                    	// get angle of spinner
                        int angle = rotate % 360;
                        // checks for negative angle
                        if (angle < 0) {
                            angle += 360;
                        }
                        angle = 360 - angle;
                        // checks if spinner has landed on the current angle
                        if (angle >= j * 72 && angle < (j + 1) * 72) {
                        	// if player has not been moved, move the player
                            if (!c.players.get(turn % c.players.size()).moved) {
                                c.players.get(turn % c.players.size()).move(g, j + 1, path,
                                        c.players.get(turn % c.players.size()).equals(player));
                            } else {
                            	// reset the current players moved boolean
                                c.players.get(turn % c.players.size()).moved = false;
                            }
                            turn++;
                            //player1.move(1,path);
                        }
                    }
                    finished = true;
                    running = false;
                }
                // delay vel each time
                // so the spinner slows down
                vel += accel;
                // if not running, start running
            } else if (distance > 0) {
                finished = false;
                running = true;
                gameAreaPanel.setEnabled(true);
            }
        }

    }

    /**
     * RollListener
     */
    class RollListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
        	// check if it is the player's turn
            if (c.players.get(turn % c.players.size()).equals(player)) {
            	// tell server that the player is spinning
                c.output.println(player.getName());
                c.output.println("/spin " + (rand.nextInt(360) + 5000));
                c.output.flush();
            }
        }
    }

}