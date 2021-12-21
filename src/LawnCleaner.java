import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LawnCleaner extends Pea{
    private int move;
    private Timer Ti;
    private SoundEffect lawncleaner = new SoundEffect("./src/bgms/lawnmower.wav");
    
    
    public LawnCleaner(GamePanel parent, int lane, int startX,int k){
        super(parent,lane,startX);
        move=k;
        setImage(new ImageIcon(this.getClass().getResource("images/LawnCleaner.png")).getImage());
        lawncleaner.prepare();
        if(k==0){
            Ti = new Timer(0, (ActionEvent e) ->{
                boolean has=false;
                for (int i = 0; i < gp.getLaneZombies().get(lane).size(); i++){
                    if(gp.getLaneZombies().get(lane).get(i).getPosX()<=startX+50){
                        has=true;
                        break;
                    }
                }
                if(has){
                    gp.getLanePeas().get(lane).remove(this);
                    Ti.stop();
                    gp.getLanePeas().get(lane).add(new LawnCleaner(gp,lane,startX+50,1));
                }
            });
            Ti.start();
        }
    }
    @Override
    public void advance() {
        if(move==1){
        	lawncleaner.player.start();
            for (int i = 0; i < gp.getLaneZombies().get(getMyLane()).size(); i++) {
                Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
                if (z.getPosX()<getPosX()+30){
                    z.changeHealth(-100);
                }
            }
            if(getPosX() > 900){
                gp.getLanePeas().get(getMyLane()).remove(this);
            }
            setPosX(getPosX() + 15);
        }
    }

}