

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.Image;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
        private Image imgBg;
        private Image imgHp;
        
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
                
                //EXTEND CODE DrawImage
                imgBg = Toolkit.getDefaultToolkit().getImage("background.jpg");
                imgHp = Toolkit.getDefaultToolkit().getImage("hp.gif");
                big.drawImage(imgBg, 0, 0, 400, 600,null);
                /* *05* */
                big.drawImage(imgHp, 20, 5, 25, 25, null);
                /* END*05* */
	}

	public void updateGameUI(GameReporter reporter){
		//ORGINALCODE big.clearRect(0, 0, 400, 600);
                
                //EXTEND CODE DrawImage Again
                big.drawImage(imgBg, 0, 0, 400, 600,null);
                
                /* *05* */
                big.drawImage(imgHp, 20, 5, 25, 25, null);
                /* END*05* */
		
		big.setColor(Color.GREEN);		
		big.drawString(String.format("Score:%08d", reporter.getScore()), 250, 20);
                
                //EXTEND CODE UI Report HP MaxScore
                /* *05* */
                big.setColor(Color.RED);
                big.drawString(String.format("%d", reporter.getHP()), 50, 20);
                /* END*05* */
                
                /* *10* */
                big.setColor(Color.YELLOW);
                big.drawString(String.format("Max:%08d", reporter.getmaxScore()), 150, 20);
                /* END*10* */
                
		for(Sprite s : sprites){
			s.draw(big);
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
