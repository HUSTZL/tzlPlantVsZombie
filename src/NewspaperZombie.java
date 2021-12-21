import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/*
public class NewspaperZombie  extends Zombie {
	private boolean hasPaper;
	private boolean isCrazy;
	
    private Image newspaperZombieImage;
    private Image newspaperZombieAttackImage;
    private Image newspaperZombieLoseImage;
    private Image newspaperZombieLoseWalkImage;
    private Image newspaperZombieLoseAttackImage;
    private Image newspaperZombieDeadWalkImage;
    private Image newspaperZombieDeadAttackImage;
    private Image currentImage;
    
    
    private SoundEffect[] roar = {new SoundEffect("./src/bgms/newspaper_rarrgh.wav"),new SoundEffect("./src/bgms/newspaper_rarrgh2.wav")};
    private SoundEffect paperBroken = new SoundEffect("./src/bgms/newspaper_rip.wav");

    public NewspaperZombie(GamePanel parent, int lane) {
        super(parent, lane);
        hasPaper = true;
        isCrazy = false;
        setHealth(2000);

        setSize(200, 200);
        setOpaque(false);
        newspaperZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombie.gif")).getImage();
        newspaperZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieAttack.gif")).getImage();
        newspaperZombieLoseImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieLosePaper.gif")).getImage();
        newspaperZombieLoseWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieLose.gif")).getImage();
        newspaperZombieLoseAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieLoseAttack.gif")).getImage();
        newspaperZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieDead.gif")).getImage();
        newspaperZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/newspaperzombie/NewspaperZombieLackDeadAttack.gif")).getImage();
        newspaperZombieDeadWalkImage.flush();
        newspaperZombieDeadAttackImage.flush();
        
        currentImage = newspaperZombieImage;
        
        roar[0].prepare();
        roar[1].prepare();
        paperBroken.prepare();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    public void advance() {
        super.advance();
        setLocation(getPosX() -100, getMyLane() * 120 + 40);
        if(hasPaper) {
        	if(super.isMoving()) {
        		currentImage = newspaperZombieImage;
        	}
        	else if(super.isAttacking()) {
        		currentImage= newspaperZombieAttackImage;
        	}
        }

        if(getHealth() < 1200 &  getHealth() > 0 & !isCrazy) {
        	paperBroken.player.start();
        	roar[0].player.start();
        	isCrazy = true;
        	currentImage = newspaperZombieLoseImage;
        	hasPaper = false;
        	setSpeed(0);
        	Timer timer = new Timer();
        	timer.schedule(new TimerTask() {
     			public void run() {
     				roar[1].player.start();
     	        	setSpeed(3);
     	        	if(isMoving()) {
     	        		currentImage = newspaperZombieLoseWalkImage;
     	        	}
     	        	else if(isAttacking()) {
     	        		currentImage= newspaperZombieLoseAttackImage;
     	        	}
     			} }, 1000);
        }
        if(super.isDead()) {
	        if(isMoving()) {
 	        	currentImage = newspaperZombieDeadWalkImage;
 	        }
 	        else if(isAttacking()) {
 	        	currentImage= newspaperZombieDeadAttackImage;
 	        }
	        setSpeed(0);
	        
	        NewspaperZombie temp = this;
	        Timer timer = new Timer();
        	timer.schedule(new TimerTask() {
     			public void run() {
     				getGp().remove(temp);
     				//getGp().getLaneZombies().get(getMyLane()).remove(temp);
     			} }, 1000);
        }
    }
    
    public boolean hasPaper() {
    	return hasPaper;
    }
}
*/