import java.awt.*;
import javax.swing.*;

public class FreezePea extends Pea {
	private SoundEffect frozenpea = new SoundEffect("./src/bgms/FrozenPea.wav");
	private SoundEffect frozenpeaHitShield = new SoundEffect("./src/bgms/shieldhit2.wav");
	
    public FreezePea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
        setImage(new ImageIcon(this.getClass().getResource("images/bullet/freezepea.png")).getImage());
        frozenpea.prepare();
        frozenpeaHitShield.prepare();
    }
    
    @Override
    public void advance() {
        for (int i = 0; i < gp.getLaneZombies().get(getMyLane()).size(); i++) {
            Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
            if (z.getPosX()+30>=getPosX()&&getPosX()>=z.getPosX()-30) {
            	if(z instanceof MetalBucketZombie) {
            		if(!z.isThreeQuarterHurted()) {
            			frozenpeaHitShield.player.start();
            		}
            		else {
            			frozenpea.player.start();
            		}
            	}
            	else {
            		frozenpea.player.start();
            	}
                z.changeHealth(z.getHealth() - 250);
                z.slow();
                boolean exit = false;
                exit = true;
                gp.getLanePeas().get(getMyLane()).remove(this);
                if (exit) break;
            }
        }
        if(getPosX() > 2000){
            gp.getLanePeas().get(getMyLane()).remove(this);
        }
        setPosX(getPosX() + 15);
    }

}
