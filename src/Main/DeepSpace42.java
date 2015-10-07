package Main;

import javax.swing.JFrame;

public class DeepSpace42 {
    
    private static final String title = "DEEP SPACE 42"; 
    
    public static void main(String[] args) {
        
		JFrame frame = new JFrame();
		frame.add(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle(title);
		frame.setResizable(false);
                frame.setUndecorated(true);
		frame.setVisible(true);
                frame.pack();
                
                
    }
    
}
