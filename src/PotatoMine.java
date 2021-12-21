import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PotatoMine extends Plant{
    public Timer WaitTimer;
    private SoundEffect potatomine = new SoundEffect("./src/bgms/potato_mine.wav");
    public PotatoMine(GamePanel parent, int x, int y,int k){
        super(parent,x,y);
        potatomine.prepare();
        if(k==1){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/PotatoMineNotReady.gif")).getImage());
            WaitTimer = new Timer(10000, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new PotatoMine(getGp(),x,y,2));
            });
        }
        if(k==2){
            setHealth(1000);
            setImage(new ImageIcon(this.getClass().getResource("images/plants/PotatoMine.gif")).getImage());
            WaitTimer = new Timer(0, (ActionEvent e) ->{
                boolean has=false;
                int x1 = 60 + (x % 9) * 100;
                for (int i = 0; i < getGp().getLaneZombies().get(y).size(); i++){
                    if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1-100&&
                    getGp().getLaneZombies().get(y).get(i).getPosX()<x1+100){
                        has=true;
                        break;
                    }
                }
                if(has){
                	potatomine.player.start();
                    for (int i = 0; i < getGp().getLaneZombies().get(y).size(); i++){
                        if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1-155&&
                        getGp().getLaneZombies().get(y).get(i).getPosX()<x1+155){
                            getGp().getLaneZombies().get(y).get(i).changeHealth(0);
                            getGp().remove(getGp().getLaneZombies().get(y).get(i));
                            DeadZombie dead = new DeadZombie(parent,y,getGp().getLaneZombies().get(y).get(i).getPosX()); 
                        }
                    }
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new PotatoMine(getGp(),x,y,3));
                }
            });
        }
        if(k==3){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/ExplosionSpudow.gif")).getImage());
            WaitTimer = new Timer(100, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new PotatoMine(getGp(),x,y,4));
            });
        }
        if(k==4){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/PotatoMine_mashed.gif")).getImage());
            WaitTimer = new Timer(1000, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
                setHealth(0);
            });
        }
        WaitTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        WaitTimer.stop();
    }
}