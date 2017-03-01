package pucman;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main  {

    
    public void open_game() {
        JFrame jf = new JFrame();
        jf.add(new JUEGO());
        jf.setTitle("PACMANZULEMTHO");
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.setSize(390, 420);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true); 
        jf.setResizable(false);
        
    }

    public static void main(String[] args) {
        
             }
        }