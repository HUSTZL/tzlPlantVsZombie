
import javax.swing.*;
import java.awt.event.ActionEvent;


public class ThreePeashooter extends Plant{
    public Timer shootTimer;

    public ThreePeashooter(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/threepeashooter.gif")).getImage());
        shootTimer = new Timer(2000, (ActionEvent e) -> {
            boolean has1=false;
            for(int i=0;i<getGp().getLaneZombies().get(y).size();++i){
                if(getGp().getLaneZombies().get(y).get(i).getPosX()>this.getX() * 100){
                    has1=true;
                    break;
                }
            }
            boolean has2=false;
            if(y!=4){
                for(int i=0;i<getGp().getLaneZombies().get(y+1).size();++i){
                    if(getGp().getLaneZombies().get(y+1).get(i).getPosX()>this.getX() * 100){
                        has2=true;
                        break;
                    }
                }
            }
            boolean has3=false;
            if(y!=0){
                for(int i=0;i<getGp().getLaneZombies().get(y-1).size();++i){
                    if(getGp().getLaneZombies().get(y-1).get(i).getPosX()>this.getX() * 100){
                        has3=true;
                        break;
                    }
                }
            }
            //System.out.println("SHOOT");
            if ((y!=0&&y!=4)) {
                if(has1||has2||has3){
                        getGp().getLanePeas().get(y-1).add(new Pea(getGp(), y-1, 103 + this.getX() * 100));
                        getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                        getGp().getLanePeas().get(y+1).add(new Pea(getGp(), y+1, 103 + this.getX() * 100));
                }
            }
            else if(y==0){
                if(has1||has2){
                    getGp().getLanePeas().get(y+1).add(new Pea(getGp(), y+1, 103 + this.getX() * 100));
                    getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                }
            }
            else if(y==4){
                if(has3||has1){
                    getGp().getLanePeas().get(y-1).add(new Pea(getGp(), y-1, 103 + this.getX() * 100));
                    getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                }
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