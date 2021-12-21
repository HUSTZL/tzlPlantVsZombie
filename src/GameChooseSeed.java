import javax.swing.*;

public class GameChooseSeed extends JFrame {
    public SeedChoose aSeedChoose;
    //choose plants windows
    public GameChooseSeed() {
        setSize(1400, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        aSeedChoose = new SeedChoose();
        getLayeredPane().add(aSeedChoose, 0);
    }
}
