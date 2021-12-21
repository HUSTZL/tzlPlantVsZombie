import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Wallnut extends Plant{
    private Timer Ti;

    public Wallnut(GamePanel parent, int x, int y,int k){
        super(parent, x, y);
        if(k==1){
            setHealth(900);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/WallNut.gif")).getImage());
            Ti = new Timer(0, (ActionEvent e) ->{
                if(getHealth()<=600){
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Wallnut(getGp(),x,y,2));
                }
            });
        }
        if(k==2){
            setHealth(600);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/Wallnut_cracked1.gif")).getImage());
            Ti = new Timer(0, (ActionEvent e) ->{
                if(getHealth()<=300){
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Wallnut(getGp(),x,y,3));
                }
            });
        }
        if(k==3){
            setHealth(300);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/Wallnut_cracked2.gif")).getImage());
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