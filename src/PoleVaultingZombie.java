import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;

public class PoleVaultingZombie  extends Zombie {
	private int collidedCount;
	private boolean isJumping;
	private boolean isTallnut;
	private Collider collided;
	
	private Image poleVaultingZombieImage;
	private Image poleVaultingZombieJumpImage;
	private Image poleVaultingZombieJumpImage2;
	private Image poleVaultingZombieWalkImage;
	private Image poleVaultingZombieAttackImage;
	private Image poleVaultingZombieLostHeadImage;
	private Image poleVaultingZombieDeadWalkImage;
	private Image poleVaultingZombieDeadAttackImage;
	private Image poleVaultingZombieDeadImage;
	
	private Image currentImage;
	
    private int xbias;
    private int ybias;
    
	private SoundEffect poleVault = new SoundEffect("./src/bgms/polevault.wav");

	
    public PoleVaultingZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setSpeed(3);
        setSize(600, 600);
        setOpaque(false);
        collidedCount = 0;
        isJumping = false;
        isTallnut = false;
        collided = null;
        
        poleVaultingZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombie.gif")).getImage();
        poleVaultingZombieJumpImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieJump.gif")).getImage();
        poleVaultingZombieJumpImage2 = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieJump2.gif")).getImage();
        poleVaultingZombieWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieWalk.gif")).getImage();
        poleVaultingZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieAttack.gif")).getImage();
        poleVaultingZombieLostHeadImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieLostHead.gif")).getImage();
        poleVaultingZombieDeadWalkImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieLostHeadWalk.gif")).getImage();
        poleVaultingZombieDeadAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieLostHeadAttack.gif")).getImage();
        poleVaultingZombieDeadImage = new ImageIcon(this.getClass().getResource("images/zombies/polevaultingzombie/PoleVaultingZombieDie.gif")).getImage();
        poleVaultingZombieJumpImage.flush();
        poleVaultingZombieJumpImage2.flush();
        poleVaultingZombieDeadWalkImage.flush();
        poleVaultingZombieDeadAttackImage.flush();
        poleVaultingZombieDeadImage.flush();
        
        currentImage = poleVaultingZombieImage;
        
        poleVault.prepare();

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, null);
    }
    
    @Override
    public void advance() {
    	setLocation(getPosX()-200, getMyLane() * 120 - 20);
    	
        if (isMoving()) {
        	collided = null;
            boolean tempCollided = false;
            if(!isJumping) {
            	for (int i = getMyLane() * 9; i < (getMyLane() + 1) * 9; i++) {
					if (getGp().getColliders()[i].assignedPlant != null && !(getGp().getColliders()[i].assignedPlant instanceof Spikeweed) 
					&&!(getGp().getColliders()[i].assignedPlant instanceof Spikerock)&& getGp().getColliders()[i].isInsideCollider(getPosX())) {
                    	collidedCount++;
                        tempCollided = true;
                        collided = getGp().getColliders()[i];
                        if(collided.assignedPlant instanceof Tallnut) {
                        	isTallnut = true;
                        }
                        else {
                        	isTallnut = false;
                        }
					}
				}
				if(getGp().getBrain()!=null&&getPosX()<=20){
					if(getGp().getBrain()[getMyLane()].assignedPlant != null){
						collidedCount++;
						tempCollided = true;
						collided = getGp().getBrain()[getMyLane()];
					}
				}
            }
            if (!tempCollided) {
            	setAttacking(false);
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                    	setPosX(getPosX() - getSpeed()) ;
                    }
                    slowInt--;
                } else {
                	setPosX(getPosX() - getSpeed());
                }
            } 
            else{
            	if(collidedCount > 1) {
            		setAttacking(true);
            		setMoving(false);
            	}
            	else{
            		setSpeed(0);
            		isJumping = true;
            		poleVault.player.start();
            		currentImage = poleVaultingZombieJumpImage;
                	Timer timer = new Timer();
                	timer.schedule(new TimerTask() {
             			public void run() {
             				if(!isTallnut) {
             					setPosX(getPosX() - 150);
             				}
             				
             				currentImage = poleVaultingZombieJumpImage2;
             				//setPosX(getPosX());
             			} }, 1000);
                	timer.schedule(new TimerTask() {
             			public void run() {
             				
             				currentImage = poleVaultingZombieWalkImage;
             				
             				setSpeed(1);
             				isJumping = false;
             			} }, 2000);
            	}
            }
        	}
            
            if(isAttacking()) {
            	currentImage= poleVaultingZombieAttackImage;
            	zombiesEating.player.loop(Clip.LOOP_CONTINUOUSLY);
            	
                if(collided.assignedPlant!=null){
                    collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 1);
                    if (collided.assignedPlant.getHealth() <= 0) {
                        zombiesEating.player.stop();
                        gulp.player.start();
                		setAttacking(false);
                		currentImage = poleVaultingZombieWalkImage;
                		setMoving(true);
                    }
                }
                if(collided.assignedPlant==null){
                    zombiesEating.player.stop();
                    setAttacking(false);
                    currentImage = poleVaultingZombieWalkImage;
                    setMoving(true);
                }
            }
            
            if(getHealth() < getFullHealth()/2) {
            	setHalfHurt(true);
            }

            if(getHealth() < 50) {
            	setDead(true);
                zombiesEating.player.stop();
                
                if(!ifScore()) {
                	// System.out.println("ZOMBIE DIE");
                	GamePanel.setProgress(10);
                	setScore(true);
                }
                PoleVaultingZombie temp = this;
    	        Timer timer = new Timer();
            	timer.schedule(new TimerTask() {
         			public void run() {
         				getGp().getLaneZombies().get(getMyLane()).remove(temp);
         			} }, 1000);
            }
            

            if(super.isDead()) {
            	if(collidedCount == 0) {
            		currentImage = poleVaultingZombieLostHeadImage;
            	}
            	else {
            		if(isMoving()) {
            			currentImage = poleVaultingZombieDeadWalkImage;
     	            }
            		else if(isAttacking()) {
     	        	    currentImage= poleVaultingZombieDeadAttackImage;
     	            }
            	}
    	        
    	        
    	        Timer timer = new Timer();
    	        timer.schedule(new TimerTask() {
         			public void run() {
         				setSpeed(0);
         				currentImage = poleVaultingZombieDeadImage;
         			} }, 1000);
    	        
    	        PoleVaultingZombie temp = this;
            	timer.schedule(new TimerTask() {
         			public void run() {
         				getGp().remove(temp);
         				//getGp().getLaneZombies().get(getMyLane()).remove(temp);
         			} }, 2000);
            }
        
    }
}
