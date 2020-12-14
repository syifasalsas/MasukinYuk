package tubes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class ButtonListener extends JFrame implements ActionListener {
    private GamePanel game;
    private int y;
    public Image basketballIcon;
//    private Image basketballIcon;
//    Image basketballIcon = new ImageIcon("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\basketballIcon.png").getImage(); // bola basket
    public ButtonListener(GamePanel game, int y){
        this.y = y;
        this.game = game;
        basketballIcon = new ImageIcon("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\basketballIcon.png").getImage(); // bola basket
//        hoop = new ImageIcon("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\Hoop.gif").getImage(); 
        
    }
    
    
    public void actionPerformed(ActionEvent e) {
            switch(y){
                case 1: 
                setTitle("Masukin Yuk");
                setSize(500, 500);
                setLocationRelativeTo(null);
                setBackground(Color.white);
                setResizable(false);
                addKeyListener(game);
                add(game);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                setIconImage(basketballIcon);
                break;
                
            }
            
        
    }

    
}
