import javax.swing.*;
import java.awt.event.ActionEvent;


public class Peashooter extends Plant {

    public Timer shootTimer;

    public Peashooter(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/peashooter.gif")).getImage());
        shootTimer = new Timer(2000, (ActionEvent e) -> {
            // Detect whether zombies exist in lane(if exist, shoot)
            boolean has=false;
            for(int i=0;i<getGp().getLaneZombies().get(y).size();++i){
                if(getGp().getLaneZombies().get(y).get(i).getPosX()>this.getX() * 100){
                    has=true;
                    break;
                }
            }
            if (has) {
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
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
