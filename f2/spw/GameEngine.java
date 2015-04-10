

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

//EXTEND CODE IMPORT
import javax.swing.JOptionPane;



public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        //EXTEND Heal Item
        private ArrayList<Heal> heal = new ArrayList<Heal>();
        //EXTEND Boss Item
        private ArrayList<Boss> boss = new ArrayList<Boss>();
        
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
        
        //EXTEND HP MAXSCORE
        private long hp = 1000;
        private long maxScore = 0;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
                                process();
                                processHeal();
                                processBoss();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
        
        //EXTEND Generate Heal Item
        private void generateHeal(){
		Heal h = new Heal((int)(Math.random()*390), 30);
		gp.sprites.add(h);
		heal.add(h);
	}
        
        //EXTEND Generate Boss Item
        private void generateBoss(){
		Boss b = new Boss((int)(Math.random()*390), 30);
		gp.sprites.add(b);
		boss.add(b);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
                                
                                //EXTEND CODE Max Score calulation
                                calmaxScore();
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
                                //EXTEND CODE Enemy Attack HP
                                enemyAttackHP();
				//ORGINALCODE die();
				//ORGINALCODE return;
			}
		}
	}
        //EXTEND Process Heal
        private void processHeal(){
		if(Math.random() < difficulty/20){
			generateHeal();
		}
		
		Iterator<Heal> h_iter = heal.iterator();
		while(h_iter.hasNext()){
			Heal h = h_iter.next();
			h.proceed();
			
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double hr;
		for(Heal h : heal){
			hr = h.getRectangle();
			if(hr.intersects(vr)){
                                //EXTEND CODE Heal HP
                                healHP();
			}
		}
	}
        
        //EXTEND Process Boss
        private void processBoss(){
		if(Math.random() < difficulty/40){
			generateBoss();
		}
		
		Iterator<Boss> b_iter = boss.iterator();
		while(b_iter.hasNext()){
			Boss b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double br;
		for(Boss b : boss){
			br = b.getRectangle();
			if(br.intersects(vr)){
                                //EXTEND CODE Boss Attack HP
				bossAttackHP();
			}
		}
	}
	
	public void die(){
		timer.stop();
                //EXTEND CODE SHOW SCORE Dialog
                JOptionPane.showMessageDialog(null,"Score: " +score +" Points");
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
                /* *04* */
		//EXTEND CODE KeyEvent UP 
		case KeyEvent.VK_UP:
			v.move2(-1);
			break;
		//EXTEND CODE KeyEvent DOWN 
		case KeyEvent.VK_DOWN:
			v.move2(1);
			break;
                /* END *04* */
		
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}
        
        //EXTEND CODE get,set HP, HP calculation
        public void setScore(long score){
		this.score = score;
	}
        public long getHP(){
                return hp;
        }
        public void setHP(long hp){
                this.hp = hp;
        }
        private void enemyAttackHP(){
            hp -= 50;
            if(hp <= 0){
                die();
                return;
            }
        }
        private void healHP(){
            hp += 200;
            return;
        }
        
        private void bossAttackHP(){
            hp -= 200;
            if(hp <= 0){
                die();
                return;
            }
        }
        //EXTEND CODE ge maxScore, Max Score calulation
        private void calmaxScore(){
            maxScore = Math.max(maxScore,score);
        }
        public long getmaxScore(){
            return maxScore;
        }
        
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
