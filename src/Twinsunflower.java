import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class Twinsunflower extends Plant {

    private Timer sunProduceTimer;

    public Twinsunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/TwinSunflower.gif")).getImage());
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Sun sta1 = new Sun(getGp(), 80 + x * 100, 110 + y * 120, 130 + y * 120);
            getGp().getActiveSuns().add(sta1);
            getGp().add(sta1, new Integer(1));
            Sun sta2 = new Sun(getGp(), 40 + x * 100, 110 + y * 120, 130 + y * 120);
            getGp().getActiveSuns().add(sta2);
            getGp().add(sta2, new Integer(1));
        });
        sunProduceTimer.start();
    }
    @Override
    public void stop() {
        super.stop();
        sunProduceTimer.stop();
    }
}
