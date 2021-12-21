import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;

public class FootballZombie  extends Zombie {
	
    private Image footballZombieImage;
    private Image footballZombieAttackImage;
    private Image footballZombieHurtImage;
    private Image footballZombieHurtAttackImage;
    private Image footballZombieDeadWalkImage;
    private Image footballZombieDeadAttackImage;
    private Image footballZombieDeadImage;
    private Image currentImage;
    

    public FootballZombie(GamePanel parent, int lane) {
        super(parent, lane);    
        setHealth(10000);
        setSpeed(3);
        
        setSize(200, 200);
        setOpaque(false);
        
        footballZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/FootballZombie.gif")).getImage();
        footballZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/FootballZombieAttack.gif")).getImage();
        footballZombieHurtImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/FootballZombieOrnLost.gif")).getImage();
        footballZombieHurtAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/FootballZombieOrnLostAttack.gif")).getImage();
        footballZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/LostHead.gif")).getImage();
        footballZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/LostHeadAttack.gif")).getImage();
        footballZombieDeadImage = new ImageIcon(this.getClass().getResource("images/zombies/footballzombie/Die.gif")).getImage();
        
        footballZombieDeadImage.flush();
        currentImage = footballZombieImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    public void advance() {
        super.advance();
        setLocation(getPosX(), getMyLane() * 120 + 40);
        if(!isThreeQuarterHurted()) {
        	if(super.isMoving()) {
        		currentImage = footballZombieImage;
        	}
        	else if(super.isAttacking()) {
        		currentImage= footballZombieAttackImage;
        	}
        }
        else if(isThreeQuarterHurted()) {
        	if(super.isMoving()) {
        		currentImage = footballZombieHurtImage;
        	}
        	else if(super.isAttacking()) {
        		currentImage= footballZombieHurtAttackImage;
        	}
        }

        if(super.isDead()) {
	        if(isMoving()) {
 	        	currentImage = footballZombieDeadWalkImage;
 	        }
 	        else if(isAttacking()) {
 	        	currentImage= footballZombieDeadAttackImage;
 	        }
	        Timer timer = new Timer();
	        
	        timer.schedule(new TimerTask() {
     			public void run() {
     				currentImage = footballZombieDeadImage;
     			} }, 1000);
	        
	        FootballZombie temp = this;
	        
        	timer.schedule(new TimerTask() {
     			public void run() {
     				setSpeed(0);
     				getGp().remove(temp);
     				//getGp().getLaneZombies().get(getMyLane()).remove(temp);
     			} }, 2000);
        }
    }
}
