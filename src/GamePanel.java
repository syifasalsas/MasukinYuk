package tubes;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.sql.*;
import sun.audio.*;

interface Engine{
    public void run();
    public void playSound(int x);
}

public class GamePanel extends JPanel implements KeyListener, Runnable, Engine{
	// fields
        private Statement stmt;
        private Connection conn;
        public Clip clip;
        private ResultSet rs;
	private int x, i, y;
	private Image ball, hoop;
	private int velX, velY;
	private int ballX, ballY;
	private int lives, makes, misses;
	private boolean MOVING, ENDOFGAME, RESTART; // flags
        private JFrame frame;
        private JLabel label;
        private JPanel panel;
        public JTextField text;
        private JButton button;

        //constuctor
	public GamePanel(Image ball, Image hoop) {
		this.ball = ball;
		this.hoop = hoop;
		ballX = 0;
		ballY = 400;
		velX = 5;
		velY = 10;
		MOVING = true;
		makes = 0;
		misses = 0;
		lives = 5;
		setBackground(Color.WHITE);
		ENDOFGAME = false;
		RESTART = false;
                frame = new JFrame("Username");
                panel = new JPanel();
                label = new JLabel("Username");
                text = new JTextField(20);
                button = new JButton("Submit");
	}
        
//      untuk input nama pada game
        public void start(){
            frame.setSize(500,500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            panel.setBackground(Color.white);
            
            panel.setLayout(null);
            label.setBounds(213,155,80,25);
            panel.add(label);
            
            text.setBounds(145,190,200,30);
            panel.add(text);
           
            button.setBounds(203,230,80,25);
            ButtonListener start = new ButtonListener(this,1);
            button.addActionListener(start);
            panel.add(button);
            frame.setVisible(true);
        }
        
//      desain untuk tampilan apabila game sudah selesai
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
                try{
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tubes_basket","root","");
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT MAX(makes) as makes FROM Pemain");
//		perulangan buat nampilin nyawanya
                for (int i = 0; i < lives; i++) {
			g.drawImage(ball, 465 - (i * 25), 45, 25, 25, null);
		}
                
		// draws the strings at the end of the game
		if (ENDOFGAME) {
                    
                    g.setFont(new Font("serif", Font.BOLD, 45));
                    while(rs.next()){g.drawString("High Score: " + rs.getInt("makes") , 108, 205);}
                    g.setFont(new Font("Times New Roman", Font.BOLD, 40));
                    g.drawString("Your Score: " + makes, 128, 250);
                    g.setFont(new Font("serif", Font.ITALIC, 21));
                    g.drawString("spacebar to restart", 174, 275);
                    
                        
                }
		g.setFont(new Font("serif", Font.PLAIN, 25)); // skor dan nama pemain
		g.drawString(text.getText() + " : "+makes, 10, 27);
		g.drawImage(hoop, 190, 0, 125, 125, null); // kerjang basket
		g.drawImage(ball, ballX, ballY, 50, 50, null); // bola basket
                conn.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
	}


	public boolean checkBall() {
		if (ballX >= 200 && ballX <= 255 && ballY < 80)
			return true;
		return false;
	}

//      lagu dalam permainan
	public void playSound(int x) {
		// buzzer sound
		if (x == 1) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\endOfGame.wav").getAbsoluteFile()));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
                else if(x == 2){
                    InputStream music;    
                    try {
                        clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\Song.wav").getAbsoluteFile()));
                        clip.start();
                                
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
                }
		// swish sound
		else {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Shifa\\Documents\\NetBeansProjects\\Tubes\\src\\tubes\\swish.wav").getAbsoluteFile()));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

        
//      menjalakan bola dan permainannya
	public void run() {
            try{
		// loop for basketball moving side to side
                playSound(2);
                FloatControl gainControl = (FloatControl) 
                clip.getControl(FloatControl.Type.MASTER_GAIN);           
                gainControl.setValue(-10.0f);
		for (i = 0; i <= (450 / velX) * 2; i = i + 1) {
			// basketball has been shot
                        
			if (!MOVING) {
				for (int x = 0; x <= 415 / velY; x++) {
					// basketball is missed
					if (x == 415 / velY) {
						ballY = 415;
						lives--;
						misses++;
						MOVING = !MOVING;
						break;
					}
					ballY = ballY - velY;
					repaint();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// check for a made basket
					if (checkBall()) {
						MOVING = !MOVING;
						playSound(-1);
						makes++;
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						velX = velX + 1;
						velY = velY + 1;
						i = 0;
						ballX = 0;
						ballY = 400;
						break;
					}
				}
			}
			if (i == (450 / velX * 2)) // prevents ball for moving alll the way past the rim
				i = 0;
			if (i < 450 / velX) {
				ballX = ballX + velX;
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			else {
				ballX = ballX - velX;
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (lives == 0 && !ENDOFGAME) { // "holds" the loop temporarily so waiting for the player to restart the
											// game
				playSound(1);
				ballX = -10000; // to make it seem like the ball dissapears
                                Db(text.getText(),makes);
				ENDOFGAME = true; // prevents the buzzer for sounding infinetly until the spacebar is pressed
			} else {
                            
                        }
		}
                
                
            }catch(Exception er){
                er.printStackTrace();
            }
            
	}
        
//      menyimpana data pemain dan score
        public void Db(String name, int score){
            try{
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tubes_basket","root","");
                stmt = conn.createStatement();
                stmt.execute("INSERT INTO pemain(user, makes) VALUES('"+text.getText()+"','"+score+"')");
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

//      tombol yang dipake untuk bermain
	@Override
	public void keyPressed(KeyEvent e) {
		// for a restart
		if (ENDOFGAME) {
			ballX = 0;
			ballY = 400;
			lives = 5;
			i = 0;
			velX = 5;
			velY = 10;
			makes = 0;
			misses = 0;
			ENDOFGAME = false;
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			return;

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			MOVING = !MOVING;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}