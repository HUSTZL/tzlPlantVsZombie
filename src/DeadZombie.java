import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class DeadZombie extends Zombie{
    public Image deadZombieImage;
    public DeadZombie(GamePanel parent, int lane, int posX) {
        super(parent, lane);    
        setSize(200, 200);
        setOpaque(false);
        setLocation(posX - 20, lane * 120 + 69);
        deadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/BoomDie.gif")).getImage();
        deadZombieImage.flush();
        
        getGp().add(this,new Integer(1));
        
        DeadZombie temp = this;
        Timer timer = new Timer();
    	timer.schedule(new TimerTask() {
 			public void run() {
                getGp().remove(temp);
 			} }, 2000);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(deadZombieImage, 0, 0, null);
    }
}