import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SeedCardpre extends JPanel implements MouseListener {
    private ActionListener al;
    private int x,y;
    public int reid=0,id;
    private Image img;
    private String name;

    public SeedCardpre(){
        setSize(64, 90);
        setOpaque(false);
        addMouseListener(this);
        setVisible(true);
    }

    public void setAction(int id,int kx,int ky,String path,String name,ActionListener al){
        this.id=id;
        this.al=al;
        x=kx;
        y=ky;
        this.name=name;
        if(name!=null) img = new ImageIcon(this.getClass().getResource(path)).getImage();
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public Image getImage(){
        return img;
    }
    public void setImage(Image im){
        img = im;
    }
    public String getname(){
        return name;
    }
    public void setname(String na){
        name=na;
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        if (al != null) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, ""));
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}