import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public abstract class Plant {

    private int health = 50;
    private Image Ima=null;
    private int x;
    private int y;
    public Timer Time;
    
    private GamePanel gp;


    public Plant(GamePanel parent, int i){
        gp = parent;
        this.y = i;
        Time = new Timer(0,(ActionEvent e) ->{
            if(health<=0){
                gp.getBrain()[i].removePlant();
            }
        });
        Time.start();
    }

    public Plant(GamePanel parent, int x, int y) {
        this.x = x;
        this.y = y;
        gp = parent;
        Time = new Timer(25,(ActionEvent e) ->{
            if(health<=0){
                gp.getColliders()[x + y * 9].removePlant();
            }
        });
        Time.start();
    }

    public void stop() {
        Time.stop();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Image getImage(){
        return Ima;
    }
    
    public void setImage(Image im){
        Ima=im;
    }
}
