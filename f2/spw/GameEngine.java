

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
        /* *06* */
        //EXTEND Heal Item
        private ArrayList<Heal> heal = new ArrayList<Heal>();
        /* END*06* */
        
        /* *07* */
        //EXTEND Boss Item//
        private ArrayList<Boss> boss = new ArrayList<Boss>();
        /* END*07* */
        
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
        
        //EXTEND HP MAXSCORE
        private long hp = 1000;
        
        /* *10* */
        private long maxScore = 0;
        /* END*10* */
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
                                process();
                                /* *06* */
                                processHeal();
                                /* END*06** */
                                /* *07* */
                                processBoss();
                                /* END*07* */
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
        
        /* *06* */
        //EXTEND Generate Heal Item
        private void generateHeal(){
		Heal h = new Heal((int)(Math.random()*390), 30);
		gp.sprites.add(h);
		heal.add(h);
	}
        /* END*06* */
        /* *07* */
        //EXTEND Generate Boss Item
        private void generateBoss(){
		Boss b = new Boss((int)(Math.random()*390), 30);
		gp.sprites.add(b);
		boss.add(b);
	}
        /* END*07* */
	
        
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
                                
                                /* *10* */
                                //EXTEND CODE Max Score calulation
                                calmaxScore();
                                /* END*10* */
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
                        /* *05* */
                                //EXTEND CODE Enemy Attack HP
                                e.proceed2(); //Alive = false
                                enemyAttackHP();
                        /* END*05* */
				//ORGINALCODE die();
				//ORGINALCODE return;
			}
		}
	}
        
        /* *06* */
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
                                h.proceed2(); //Alive = false
                                healHP();
			}
		}
	}
        /* END*06* */
        /* *07* */
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
                                b.proceed2(); //Alive = false
				bossAttackHP();
			}
		}
	}
        /* END*07* */
	
	public void die(){
		timer.stop();
                /* *05* */
                //EXTEND CODE SHOW SCORE Dialog
                JOptionPane.showMessageDialog(null,"Score: " +score +" Points");
                /* END*05* */
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
        @Override
	public long getScore(){
		return score;
	}
        
        //EXTEND CODE get,set HP, HP calculation
        public void setScore(long score){
		this.score = score;
	}
        /* *05* */
        public long getHP(){
                return hp;
        }
        /* END*05* */
        public void setHP(long hp){
                this.hp = hp;
        }
        /* *05* */
        private void enemyAttackHP(){
            hp -= 50;
            if(hp <= 0){
                die();
                return;
            }
        }
        /* END*05* */
        /* *06* */
        @Override
        private void healHP(){
            hp += 200;
            return;
        }
        /* END*06* */
        
        private void bossAttackHP(){
            hp -= 200;
            if(hp <= 0){
                die();
                return;
            }
        }
        /* *10* */
        //EXTEND CODE ge maxScore, Max Score calulation
        private void calmaxScore(){
            maxScore = Math.max(maxScore,score);
        }
        @Override
        public long getmaxScore(){
            return maxScore;
        }
        /* END*10* */
        
	
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
