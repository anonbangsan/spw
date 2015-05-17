
import java.awt.Color;
import java.awt.Graphics2D;

//EXTEND CODE IMPORT
import java.awt.Toolkit;
import java.awt.Image;

public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
            //ORGINALCODE g.setColor(Color.GREEN);
            //ORGINALCODE g.fillRect(x, y, width, height);
            
        //EXTEND CODE DrawImage
            Image img = Toolkit.getDefaultToolkit().getImage("sample.gif");
            g.drawImage(img, x, y, width, height, null);	
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	
        /* *04* */
	//EXTEND CODE MOVE Y-AXIS for UP DOWN
	public void move2(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}
        /* END*04* */
        public void setPosition(){
            x = 180;
            y = 550;
        }

}
