import java.awt.*;
import javax.swing.*;

public class Pea {

    private int posX;
    protected GamePanel gp;
    private int myLane;
    private Image Ima=null;
    private Image peahit = new ImageIcon(this.getClass().getResource("images/bullet/PeaShoot.gif")).getImage();

    private SoundEffect pea = new SoundEffect("./src/bgms/Pea.wav");
    private SoundEffect peaHitShiled = new SoundEffect("./src/bgms/shieldhit.wav");
    
    
    public Pea(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
        Ima = new ImageIcon(this.getClass().getResource("images/bullet/pea.png")).getImage();
        
        pea.prepare();
        peaHitShiled.prepare();
        pea.gainControl.setValue(-10.0f);
    }

    public void advance() {

        // Rectangle pRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(myLane).size(); i++) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            // Rectangle zRect = new Rectangle(z.getPosX(), 109 + myLane * 120, 400, 120);
            boolean exit = false;
            if (z.getPosX()+30>=posX&&posX>=z.getPosX()-30) {
                setImage(peahit);
            	if(z instanceof MetalBucketZombie) {
            		if(!z.isThreeQuarterHurted()) {
            			peaHitShiled.player.start();
            		}
            		else {
            			pea.player.start();
            		}
            	}
            	else {
            	    pea.player.start();
            	}
                z.changeHealth(z.getHealth() - 200);
                exit = true;
                gp.getLanePeas().get(myLane).remove(this);
                if (exit) break;
            }
        }
        if(posX > 2000){
            gp.getLanePeas().get(myLane).remove(this);
        }
        posX += 15;
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

    public Image getImage(){
        return Ima;
    }
    
    public void setImage(Image im){
        Ima=im;
    }
    
    public SoundEffect getSound() {
    	return pea;
    }
}
