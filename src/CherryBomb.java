import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CherryBomb extends Plant{
    public Timer BombTimer;
    //public Timer DeadZombieTimer;
    public Image ima;
    // private Image BoomDie;
    private SoundEffect cherrybomb = new SoundEffect("./src/bgms/cherrybomb.wav");
    
    /***
     * 
     * @param parent: game panel containing this plant
     * @param x: index of x collider
     * @param y: index of y collider
     * @param k: decide cherry is booming or boomed(facilitate animation)
     */
    public CherryBomb(GamePanel parent, int x, int y,int k){
        super(parent, x, y);
        cherrybomb.prepare();
        // BoomDie = new ImageIcon(this.getClass().getResource("images/zombies/BoomDie.gif")).getImage();
        
        if(k==1){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/CherryBomb.gif")).getImage());
            BombTimer = new Timer(630, (ActionEvent e) ->{
                int x1 = 60 + (x % 9) * 100;   // display x coordinate
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new CherryBomb(getGp(),x,y,2));


                for(int i=0;i<getGp().getLaneZombies().get(y).size(); i++) {
                    if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1-255&&
                        getGp().getLaneZombies().get(y).get(i).getPosX()<x1+255){
                            getGp().getLaneZombies().get(y).get(i).changeHealth(0);
                            getGp().remove(getGp().getLaneZombies().get(y).get(i));
                            DeadZombie dead = new DeadZombie(parent,y,getGp().getLaneZombies().get(y).get(i).getPosX());    
                    }
                }
                if(y!=0){
                    for(int i=0;i<getGp().getLaneZombies().get(y-1).size(); i++) {
                        if(getGp().getLaneZombies().get(y-1).get(i).getPosX()>=x1-255&&
                            getGp().getLaneZombies().get(y-1).get(i).getPosX()<x1+255){
                                getGp().getLaneZombies().get(y-1).get(i).changeHealth(0);
                                getGp().remove(getGp().getLaneZombies().get(y - 1).get(i));
                                DeadZombie dead = new DeadZombie(parent,y - 1,getGp().getLaneZombies().get(y-1).get(i).getPosX()); 
                        }
                    }
                }
                if(y!=4){
                    for(int i=0;i<getGp().getLaneZombies().get(y+1).size(); i++) {
                        if(getGp().getLaneZombies().get(y+1).get(i).getPosX()>=x1-255&&
                            getGp().getLaneZombies().get(y+1).get(i).getPosX()<x1+255){
                                getGp().getLaneZombies().get(y+1).get(i).changeHealth(0);
                                getGp().remove(getGp().getLaneZombies().get(y + 1).get(i));
                                DeadZombie dead = new DeadZombie(parent,y + 1,getGp().getLaneZombies().get(y + 1).get(i).getPosX()); 
                        }
                    }
                }

                
                // Blow up zombies in this lane and nearby two lanes(border detection)
                // Notice: zombies killed by bomb have different dying gif
                for(int yi = Math.max(y - 1, 0); yi <= Math.min(y + 1, 4); yi++)
                {
                    for(int i=0;i<getGp().getLaneZombies().get(yi).size(); i++) {
                        if(getGp().getLaneZombies().get(yi).get(i).getPosX()>=x1-255&&
                            getGp().getLaneZombies().get(yi).get(i).getPosX()<x1+255){
                                getGp().getLaneZombies().get(yi).get(i).changeHealth(0);
                                getGp().remove(getGp().getLaneZombies().get(yi).get(i));
                                DeadZombie dead = new DeadZombie(parent,yi,getGp().getLaneZombies().get(yi).get(i).getPosX());    

                        }
                    }
                }
            });
        }
        else{
            ima = new ImageIcon(this.getClass().getResource("images/plants/Boom.gif")).getImage();
            ima.flush();
            setImage(ima);
            cherrybomb.player.start();
            BombTimer = new Timer(1300, (ActionEvent e) ->{
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