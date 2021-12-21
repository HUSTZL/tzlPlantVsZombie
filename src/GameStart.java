import javax.swing.*;

public class GameStart extends JFrame  {
    //menu windows
    public GameStart() {
        Menu menu = new Menu();
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu, 0);
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }
}
