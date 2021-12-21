import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Sunflower extends Plant {

    private Timer sunProduceTimer;

    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/sunflower.gif")).getImage());
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Sun sta = new Sun(getGp(), 60 + x * 100, 110 + y * 120, 130 + y * 120);
            getGp().getActiveSuns().add(sta);
            getGp().add(sta, new Integer(1));
        });
        sunProduceTimer.start();
    }
    public Sunflower(GamePanel parent, int x, int y,int i) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/sunflower.gif")).getImage());
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
        });
        sunProduceTimer.start();
    }
    @Override
    public void stop() {
        super.stop();
        sunProduceTimer.stop();
    }
}
