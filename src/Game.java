package tubes;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class permainan{
    abstract public void initialize();
}

public class Game extends permainan{

	public Image hoop, basketballIcon;
	private GamePanel game;

	public Game() {
		hoop = new ImageIcon("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\Hoop.gif").getImage(); // ring basket
		basketballIcon = new ImageIcon("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\basketballIcon.png").getImage(); // bola basket
		game = new GamePanel(basketballIcon, hoop);
                initialize();
	}    

    @Override
    public void initialize() {
        game.start();
        game.run();
    }
}

