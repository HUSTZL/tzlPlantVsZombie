import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;


public class Zombie extends JPanel {

    private int health = 2500;
    private int speed = 1;
    private int fullHealth;
    
    private GamePanel gp;

    private int posX;
    private int myLane;
    private boolean isMoving;
    private boolean isAttacking;
    private boolean quarterHurt;
    private boolean halfHurt;
    private boolean threeQuarterHurt;
    private boolean isDead;
    private boolean ifScore;
    private Collider collided;
    
    
    public SoundEffect zombiesEating = new SoundEffect("./src/bgms/zombieEat.wav");
    public SoundEffect zombiesWin = new SoundEffect("./src/bgms/zombiegroup.wav");
    public SoundEffect gulp = new SoundEffect("./src/bgms/gulp.wav");

    public Zombie(GamePanel parent, int lane) {
        this.gp = parent;
        myLane = lane;
        fullHealth = health;
        posX = 1000;
        isMoving = true;
        isAttacking = false;
        quarterHurt = false;
        halfHurt = false;
        threeQuarterHurt = false;
        isDead = false;
        ifScore = false;
        collided = null;
        
        setLocation(1000, getMyLane() * 120);
        zombiesEating.prepare();
        zombiesWin.prepare();
        gulp.prepare();
        
        zombiesEating.gainControl.setValue(-10.0f);
    }

    public void advance() {
        if (isMoving) {
            boolean isCollides = false;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.getColliders()[i].assignedPlant != null && !(gp.getColliders()[i].assignedPlant instanceof Spikeweed)
                &&!(gp.getColliders()[i].assignedPlant instanceof Spikerock)&& gp.getColliders()[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.getColliders()[i];
                }
            }
            if (!isCollides) {
            	isAttacking = false;
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                    	posX -= speed;
                    }
                    slowInt--;
                } else {
                    posX -= speed;
                }
            } else {
            	isMoving = false;
            	isAttacking = true;
            }
        }
        
        if(isAttacking) {
            zombiesEating.player.loop(Clip.LOOP_CONTINUOUSLY);
            
            if(collided.assignedPlant!=null){
                collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 1);
                if (collided.assignedPlant.getHealth() <= 0) {
                    zombiesEating.player.stop();
                    gulp.player.start();
                    isAttacking = false;
                    isMoving = true;
                }
            }
            if(collided.assignedPlant==null){
                zombiesEating.player.stop();
                isAttacking = false;
                isMoving = true;
            }
        }
        if(health < fullHealth*3/4) {
        	quarterHurt = true;
        }
        if(health < fullHealth/2) {
        	halfHurt = true;
        }
        if(health < fullHealth/4) {
        	threeQuarterHurt = true;
        }
        
        if(health < 50) {
        	isDead = true;
            zombiesEating.player.stop();
            
            if(!ifScore) {
            	// System.out.println("ZOMBIE DIE");
            	GamePanel.setProgress(10);
            	ifScore = true;
            }
            Zombie temp = this;
            // after zombie dead, remove this zombie after 1000ms to finish dead gif
	        Timer timer = new Timer();
        	timer.schedule(new TimerTask() {
     			public void run() {
     	            gp.getLaneZombies().get(getMyLane()).remove(temp);
     			} }, 1000);

        }
    }

    int slowInt = 0;

    public void slow() {
        slowInt = 1000;
    }

   
    
    public static Zombie getZombie(String type, GamePanel parent, int lane) {
        Zombie z = null; // = new Zombie(parent, lane);
        switch (type) {
            case "NormalZombie":
                z = new NormalZombie(parent, lane);
                break;
            case "ConeHeadZombie":
                z = new ConeHeadZombie(parent, lane);
                break;
            case "MetalBucketZombie":
            	z = new MetalBucketZombie(parent, lane);
                break;/*
            case "NewspaperZombie":
            	z = new NewspaperZombie(parent, lane);
                break;*/
            case "PoleVaultingZombie":
            	z = new PoleVaultingZombie(parent, lane);
                break;
            case "FootballZombie":
            	z = new FootballZombie(parent, lane);
                break;
            default:
                System.out.printf("getZombie error");
        }
        return z;
    }

    public int getHealth() {
        return health;
    }
    
    public int getFullHealth() {
        return fullHealth;
    }

    public void setHealth(int health) {
        this.health = health;
        this.fullHealth = health;
    }
    
    public void changeHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getMyLane() {
        return myLane;
    }

    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }

    public boolean isMoving() {
        return isMoving;
    }
    

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public void setAttacking(boolean Attacking) {
    	isAttacking = Attacking;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isQuarterHurted() {
        return quarterHurt;
    }
    
    public void setQuarterHurt(boolean hurt) {
    	quarterHurt = hurt;
    }
    
    public boolean isHalfHurted() {
        return halfHurt;
    }
    
    public void setHalfHurt(boolean hurt) {
    	halfHurt = hurt;
    }
    
    public boolean isThreeQuarterHurted() {
        return threeQuarterHurt;
    }
    
    public void setThreeQuarterHurt(boolean hurt) {
    	threeQuarterHurt = hurt;
    }
    
    public boolean ifScore() {
    	return ifScore;
    }
    
    public void setScore(boolean ifscore) {
    	ifScore = ifscore;
    }
    
    public int getSlowInt() {
        return slowInt;
    }

    public void setSlowInt(int slowInt) {
        this.slowInt = slowInt;
    }
}
