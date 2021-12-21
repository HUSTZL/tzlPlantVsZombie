import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class MetalBucketZombie  extends Zombie {
    private Image metalBucketZombieImage;
    private Image metalBucketZombieAttackImage;
    
	private Image normalZombieImage;
	private Image normalZombieAttackImage;
	private Image normalZombieDeadImage;
	private Image normalZombieDeadWalkImage;
	private Image normalZombieDeadAttackImage;
	
	private Image currentImage;
	
    private int xbias;
    private int ybias;
    public MetalBucketZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(4000);
        
        setSize(200, 200);
        setOpaque(false);
        xbias = 0;
        ybias = 60;
        
        metalBucketZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/metalbucketzombie/BucketheadZombie.gif")).getImage();
        metalBucketZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/metalbucketzombie/BucketheadZombieAttack.gif")).getImage();
 
        normalZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/Zombie.gif")).getImage();
        normalZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieAttack.gif")).getImage();
        normalZombieDeadImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDead.gif")).getImage();
        normalZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadWalk.gif")).getImage();
        normalZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadAttack.gif")).getImage();
        
        normalZombieDeadImage.flush();
        normalZombieDeadWalkImage.flush();
        normalZombieDeadAttackImage.flush();
        
        currentImage = metalBucketZombieImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    public void advance() {
    	super.advance();
    	setLocation(getPosX() + xbias, getMyLane() * 120 + ybias);

        if(!isThreeQuarterHurted()) {
        	if(super.isMoving()) {
        		xbias = -10;
        		currentImage = metalBucketZombieImage;
        	}
        	else if(super.isAttacking()) {
        		xbias = -70;
        		currentImage= metalBucketZombieAttackImage;
        	}
        }
        else if(isThreeQuarterHurted()) {
        	
        	ybias = 60;
        	if(super.isMoving()) {
        		xbias = -70;
        		currentImage = normalZombieImage;
        	}
        	else if(super.isAttacking()) {
        		xbias = -90;
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
            
            MetalBucketZombie temp = this;
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
