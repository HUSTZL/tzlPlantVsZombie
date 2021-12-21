import javax.swing.*;
import java.awt.event.ActionEvent;

public class Spikerock extends Plant {
    
    public Timer shootTimer;

    private SoundEffect pea = new SoundEffect("./src/bgms/Pea.wav");

    public Spikerock(GamePanel parent, int x, int y){
        super(parent, x, y);
        setHealth(1000000);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/Spikerock.gif")).getImage());
        shootTimer = new Timer(1200, (ActionEvent e) ->{
            int x1= x*100+103;
            for(int i=0;i<getGp().getLaneZombies().get(y).size();++i){
                Zombie z = getGp().getLaneZombies().get(y).get(i);
                if(z.getPosX()>=x1-100&&z.getPosX()<=x1+70){
                    z.setHealth(z.getHealth()-300);
                    pea.prepare();
                    pea.player.start();
                }
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        shootTimer.stop();
    }
}