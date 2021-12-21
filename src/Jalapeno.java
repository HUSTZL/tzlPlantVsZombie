import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Jalapeno extends Plant{
    public int k;
    public Timer BombTimer;
    public Image ima;

    public Jalapeno(GamePanel parent, int x, int y,int k){
        super(parent, x, y);
        setHealth(1000);
        this.k=k;

        if(k==1){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/Jalapeno.gif")).getImage());
            BombTimer = new Timer(720, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new Jalapeno(getGp(),x,y,2));
                for(int i=0;i<getGp().getLaneZombies().get(y).size(); i++) {
                    getGp().getLaneZombies().get(y).get(i).changeHealth(0);
                    getGp().remove(getGp().getLaneZombies().get(y).get(i));
                    DeadZombie dead = new DeadZombie(parent,y,getGp().getLaneZombies().get(y).get(i).getPosX()); 
                }
            });
        }
        else{
            setX(0);
            ima = new ImageIcon(this.getClass().getResource("images/plants/JalapenoAttack.gif")).getImage();
            ima.flush();
            setImage(ima);
            BombTimer = new Timer(1440, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
            });
        }
        BombTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        BombTimer.stop();
    }
}