import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

/**
 * Created by Houhaofei on 2021/12/6.
 */
public class ConeHeadZombie extends Zombie {

    private Image coneHeadZombieImage;
    private Image coneHeadZombieAttackImage;
    private Image coneHeadZombieHurtAttackImage;
    
	private Image normalZombieImage;
	private Image normalZombieAttackImage;
	private Image normalZombieDeadImage;
	private Image normalZombieDeadWalkImage;
	private Image normalZombieDeadAttackImage;
	
    private Image currentImage;
    
    private int xbias;
    private int ybias;
    public ConeHeadZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(3000);
        setSize(200, 200);
        setOpaque(false);
        xbias = 0;
        ybias = 60;
        
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombie.gif")).getImage();
        coneHeadZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombieAttack.gif")).getImage();
        coneHeadZombieHurtAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombieAttack2.gif")).getImage();
 
        normalZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/Zombie.gif")).getImage();
        normalZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieAttack.gif")).getImage();
        normalZombieDeadImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDead.gif")).getImage();
        normalZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadWalk.gif")).getImage();
        normalZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadAttack.gif")).getImage();
        
        normalZombieDeadImage.flush();
        normalZombieDeadWalkImage.flush();
        normalZombieDeadAttackImage.flush();
        
        currentImage = coneHeadZombieImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    public void advance() {
    	super.advance();
    	setLocation(getPosX() + xbias, getMyLane() * 120 + ybias);

        if(!isHalfHurted()) {
        	if(super.isMoving()) {
        		xbias = 0;
        		currentImage = coneHeadZombieImage;
        	}
        	else if(super.isAttacking()) {
        		xbias = -100;
        		currentImage= coneHeadZombieAttackImage;
        	}
        }
        else if(isHalfHurted()) {
        	xbias = -70;
        	ybias = 60;
        	if(super.isMoving()) {
        		currentImage = normalZombieImage;
        	}
        	else if(super.isAttacking()) {
        		currentImage= normalZombieAttackImage;
        	}
        }
        
        if(super.isDead()) {
            if(isMoving()) {
    	        currentImage = normalZombieDeadWalkImage;
    	    }
    	    else if(isAttacking()) {
    	        currentImage= normalZombieAttackImage;
    	    }
            
            ConeHeadZombie temp = this;
            Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
     			public void run() {
     				currentImage = normalZombieDeadImage;
     			} }, 1000);
	        
        	timer.schedule(new TimerTask() {
     			public void run() {
     				setSpeed(0);
     				getGp().remove(temp);
     				//getGp().getLaneZombies().get(getMyLane()).remove(temp);
     			} }, 2000);
        }
    }
}
