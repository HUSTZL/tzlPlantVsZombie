import javax.swing.*;
import java.awt.event.ActionEvent;

public class Torchwood extends Plant {

    public Timer shootTimer;


    public Torchwood(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/torchwood.gif")).getImage());
        shootTimer = new Timer(0, (ActionEvent e) -> {
            for(int i=0;i<getGp().getLanePeas().get(y).size();i++){
                if (getGp().getLanePeas().get(y).get(i) instanceof FreezePea){
                    Pea p=getGp().getLanePeas().get(y).get(i);
                    int x1 = 60 + (x % 9) * 100;
                    if(p.getPosX()<x1&&x1-p.getPosX()<=15){
                        getGp().getLanePeas().get(y).remove(i);
                        getGp().getLanePeas().get(y).add(new Pea(getGp(), y, x1));
                    }
                    continue;
                }
                else if (getGp().getLanePeas().get(y).get(i) instanceof BurnPea ||
                getGp().getLanePeas().get(y).get(i) instanceof LawnCleaner){
                    continue;
                }
                else{
                    Pea p=getGp().getLanePeas().get(y).get(i);
                    int x1 = 60 + (x % 9) * 100;
                    if(p.getPosX()<x1&&x1-p.getPosX()<=15){
                        getGp().getLanePeas().get(y).remove(i);
                        getGp().getLanePeas().get(y).add(new BurnPea(getGp(), y, x1));
                    }
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
