import javax.swing.*;
import java.awt.event.ActionEvent;

public class GatlingPea extends Plant{
    public Timer shootTimer;

    public GatlingPea(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/GatlingPea.gif")).getImage());
        shootTimer = new Timer(2000, (ActionEvent e) -> {
            boolean has=false;
            for(int i=0;i<getGp().getLaneZombies().get(y).size();++i){
                if(getGp().getLaneZombies().get(y).get(i).getPosX()>this.getX() * 100){
                    has=true;
                    break;
                }
            }
            if (has) {
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 140 + this.getX() * 100));
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 110 + this.getX() * 100));
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 80 + this.getX() * 100));
                getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 50 + this.getX() * 100));
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