import javax.swing.*;
import java.awt.event.ActionEvent;

public class Tallnut extends Plant{
    private Timer Ti;

    public Tallnut(GamePanel parent, int x, int y,int k){
        super(parent, x, y);
        if(k==1){
            setHealth(1200);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/TallNut.gif")).getImage());
            Ti = new Timer(0, (ActionEvent e) ->{
                if(getHealth()<=800){
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Tallnut(getGp(),x,y,2));
                }
            });
        }
        if(k==2){
            setHealth(800);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/TallnutCracked1.gif")).getImage());
            Ti = new Timer(0, (ActionEvent e) ->{
                if(getHealth()<=400){
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Tallnut(getGp(),x,y,3));
                }
            });
        }
        if(k==3){
            setHealth(400);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/TallnutCracked2.gif")).getImage());
            Ti = new Timer(0, (ActionEvent e) -> {
            });
        }
        Ti.start();
    }
    @Override
    public void stop() {
        super.stop();
        Ti.stop();
    }
}