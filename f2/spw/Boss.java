/* *07* */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

//EXTEND CODE IMPORT
import java.awt.Toolkit;
import java.awt.Image;

public class Boss extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	
	public Boss(int x, int y) {
		super(x, y, 120, 120);
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		//ORGINALCODE g.setColor(Color.RED);
		//ORGINALCODE g.fillRect(x, y, width, height);
        
        //EXTEND CODE DrawImage
                Image img = Toolkit.getDefaultToolkit().getImage("boss.gif");
                g.drawImage(img, x, y, width, height, null);	
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
        
        public void proceed2(){
            alive = false;
        }
	
	public boolean isAlive(){
		return alive;
	}
}
/* END*07* */