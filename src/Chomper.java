import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Chomper extends Plant{
    public Timer shootTimer;
    private SoundEffect chomp = new SoundEffect("./src/bgms/chomp2.wav");

    public Chomper(GamePanel parent, int x, int y,int k,int Zombiex){
        super(parent, x, y);
        setHealth(100);

        if(k==1){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/Chomper.gif")).getImage());
            shootTimer = new Timer(0, (ActionEvent e) ->{
                if (getGp().getLaneZombies().get(y).size() > 0){
                    int x1 = (x % 9) * 100;
                    boolean has=false;
                    for (int i = 0; i < getGp().getLaneZombies().get(y).size(); i++){
                        if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1&&
                        getGp().getLaneZombies().get(y).get(i).getPosX()<x1+280){
                            has=true;
                        }
                    }
                    
                    if(has){
                        getGp().getColliders()[x + y * 9].removePlant();
                        getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,2,0));
                    }
                }
            });
        }
        if(k==2){
        	chomp.prepare();
        	chomp.player.start();
            setImage(new ImageIcon(this.getClass().getResource("images/plants/ChomperAttack.gif")).getImage());
            shootTimer = new Timer(810, (ActionEvent e)->{
                int x1 = (x % 9) * 100;
                int idnow=0;
                int zx=1000;
                boolean has=false;
                for (int i = 0; i < getGp().getLaneZombies().get(y).size(); i++){
                    if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1&&
                    getGp().getLaneZombies().get(y).get(i).getPosX()<x1+280){
                        has=true;
                        int nowx=getGp().getLaneZombies().get(y).get(i).getPosX()-x1;
                        if(nowx<zx){
                            zx=nowx;
                            idnow=i;
                        }
                    }
                }
                if(has&&getGp().getLaneZombies().get(y)!=null&&getGp().getLaneZombies().get(y).size()>idnow){
                    Zombie z = getGp().getLaneZombies().get(y).get(idnow);
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,3,0));
                    z.changeHealth(0);
                    z.getGp().remove(z);
                }
                else{
                    getGp().getColliders()[x + y * 9].removePlant();
                    getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,1,0));
                }
            });
        }
        if(k==3){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/ChomperDigest.gif")).getImage());
            shootTimer = new Timer(30000, (ActionEvent e)->{
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,1,0));
            });
        }
        shootTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        shootTimer.stop();
    }
}