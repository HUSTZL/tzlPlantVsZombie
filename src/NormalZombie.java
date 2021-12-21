import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NormalZombie extends Zombie {
	private Image[] normalZombieImages = {new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/Zombie.gif")).getImage(),
                                         new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/Zombie2.gif")).getImage(),
                                         new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/Zombie3.gif")).getImage()};
	private Image normalZombieImage;
	private Image normalZombieAttackImage;
	private Image normalZombieDeadImage;
	private Image normalZombieDeadWalkImage;
	private Image normalZombieDeadAttackImage;
	private Image currentImage;
	
    public NormalZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setSize(200, 200);
        setOpaque(false);
        Random rnd = new Random();
        int id = rnd.nextInt(3);
        
        normalZombieImage = normalZombieImages[id];
        normalZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieAttack.gif")).getImage();
        normalZombieDeadImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDead.gif")).getImage();
        normalZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadWalk.gif")).getImage();
        normalZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/normalzombie/ZombieDeadAttack.gif")).getImage();
        
        normalZombieDeadImage.flush();
        normalZombieDeadWalkImage.flush();
        normalZombieDeadAttackImage.flush();
    
        currentImage = normalZombieImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    
    public void advance() {
    	super.advance();
    	setLocation(getPosX() - 80, getMyLane() * 120 + 60);
    	
    	if(super.isMoving()) {
    		currentImage = normalZombieImage;
    	}
    	else if(super.isAttacking()) {
    		currentImage= normalZombieAttackImage;
    	}
    	
        if(super.isDead()) {
            if(isMoving()) {
    	        currentImage = normalZombieDeadWalkImage;
    	    }
    	    else if(isAttacking()) {
    	        currentImage= normalZombieDeadAttackImage;
    	    }
            
            NormalZombie temp = this;
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

