import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
                
                /* *08* */
                Font fn= new Font("Courier New", Font.BOLD,20);
                JMenuBar menubar = new JMenuBar();
                JMenu menuMain = new JMenu("Main");
                JMenu menuAbout = new JMenu("About");
                JMenuItem menuRestart =new JMenuItem("Restart");
                JMenuItem menuExit = new JMenuItem("Exit");
                JMenuItem menuStop = new JMenuItem("Stop");
                JMenuItem menuCredit = new JMenuItem("Credit");      
                menuMain.setFont(fn);
                menuAbout.setFont(fn);
                menuRestart.setFont(fn);
                menuExit.setFont(fn);
                menuStop.setFont(fn);
                menuCredit.setFont(fn);
                menuMain.add(menuRestart);
                menuMain.add(menuStop);
                menuMain.add(menuExit);
                menuAbout.add(menuCredit);
                menubar.add(menuMain);
                menubar.add(menuAbout);
                frame.setJMenuBar(menubar);
                
                /* END*08* */
               
		SpaceShip v = new SpaceShip(180, 550, 35, 35);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);
                         
                /*- *09* -*/
                MenuListener menuListener = new MenuListener(menuExit,menuCredit,menuRestart,menuStop,engine);
                menuExit.addActionListener(menuListener);
                menuCredit.addActionListener(menuListener);
                menuRestart.addActionListener(menuListener);
                menuStop.addActionListener(menuListener);
                /*- END*09* -*/
                
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
	}
       
    
        
}
