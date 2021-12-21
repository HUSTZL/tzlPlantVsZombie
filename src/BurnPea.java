import java.awt.*;
import javax.swing.*;

public class BurnPea extends Pea{
	private SoundEffect burnpea = new SoundEffect("./src/bgms/firepea.wav");
    public BurnPea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
        setImage(new ImageIcon(this.getClass().getResource("images/bullet/BurnPea.gif")).getImage());
        burnpea.prepare();

    }

    @Override
    public void advance() {
        for (int i = 0; i < gp.getLaneZombies().get(getMyLane()).size(); i++) {
            Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
            boolean exit = false;
            if (z.getPosX()+30>=getPosX()&&getPosX()>=z.getPosX()-30){
            	burnpea.player.start();
                z.changeHealth(z.getHealth() - 400);
                exit = true;
                gp.getLanePeas().get(getMyLane()).remove(this);
                if (exit) break;
            }
            if(getPosX() > 2000){
                gp.getLanePeas().get(getMyLane()).remove(this);
            }
        }
        setPosX(getPosX() + 15);
    }
}