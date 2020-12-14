package tubes;


public class Start {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                Game g = new Game();
                Thread thread = new Thread((Runnable) g);
                thread.start();
	}
}
