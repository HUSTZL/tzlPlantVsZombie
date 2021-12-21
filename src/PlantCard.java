import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlantCard extends JPanel implements MouseListener {

    private Image img;
    private Image Im;
    private BufferedImage image;
    private ActionListener al;
    private int x,y;

    public GamePanel gp;
    public int needsun;

    public int changetime=10;
    public int waittime;
    public boolean iswait;
    public Timer Time;
    public Timer WaitTime;
    public Timer redrawTimer;

    private SoundEffect seedLift = new SoundEffect("./src/bgms/seedlift.wav");
    private SoundEffect buzzer = new SoundEffect("./src/bgms/buzzer.wav");
    
    public PlantCard(String imagePath){
        setSize(64, 90);
        
        img = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        Im = img;
        try {
            image = toBufferedImage(img);
        } catch (Exception e) {
        }
        if(image==null){
            System.out.println(imagePath);
        }
        addMouseListener(this);
        seedLift.prepare();
        buzzer.prepare();
        
        redrawTimer = new Timer(5, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();
    }

    public void setAction(GamePanel gp,int needsun,int kx,int ky,int wa,ActionListener al) {
        this.needsun=needsun;
        this.gp=gp;
        this.al = al;
        x=kx;
        y=ky;
        setLocation(x, y);
        waittime = wa;
        iswait = false;
        Time = new Timer(wa,(ActionEvent e) ->{//wa内不能使用
            iswait=false;
            Time.stop();
        });

        WaitTime = new Timer(wa/10,(ActionEvent e) ->{//wa内分10次刷新黑灰分界线
            changetime=changetime+1;
            Im=grayImage(changetime);
            if(changetime==10){
                Im = Toolkit.getDefaultToolkit().createImage(image.getSource());
                WaitTime.stop();
            }
        });
    }

    public void countwaittime(){
        iswait=true;
        Time.start();
        changetime=0;
        Im=grayImage(changetime);
        WaitTime.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(changetime==10&&gp.getSunScore()<needsun){
            Im=grayImage(changetime);
            iswait = true;
        }
        else if(changetime==10&&gp.getSunScore()>=needsun) {
        	Im = img;
        	iswait = false;
        }
        g.drawImage(Im, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
    	if(!iswait) {
    		seedLift.player.start();
    		seedLift.prepare();
    	}
    	else {
    		buzzer.player.start();
    		buzzer.prepare();
    	}
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (al != null && !iswait) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, ""));
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    public Image grayImage(int status){
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(),  image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray=0;
                if(9*status>image.getHeight()-j){
                    gray = getBigger(r, g, b);
                }
                else{
                    gray = getAvg(r, g, b)-2;
                }
                grayImage.setRGB(i, j, colorToRGB(0, gray, gray, gray));
            }
        }
        return toImage(grayImage);
    }

    private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }

    public static int getBigger(int x,int y,int z){
        if(x>=y && x>=z) {
            return x;
        }else if(y>=x && y>=z) {
            return y;
        }else if(z>=x && z>=y) {
            return z;
        }else {
            return 0;
        }
    }

    public static int getAvg(int x,int y,int z){
        int avg=(x+y+z)/3;
        return avg;
    }

    //BufferedImage to Image
    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    private BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}