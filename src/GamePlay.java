import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


public class GamePlay extends JFrame {

    enum PlantType {
        None, Sunflower, Peashooter, FreezePeashooter, Torchwood, TwicePeashooter, ThreePeashooter, Chomper,
        Wallnut, GatlingPea, PotatoMine, CherryBomb, Tallnut, Twinsunflower, Jalapeno, Spikeweed, Spikerock,
        ConeHeadZombie, MetalBucketZombie, PoleVaultingZombie, FootballZombie, Sholve
    }

    public PlantCard Sunflower,Peashooter,FreezePeashooter,Torchwood,TwicePeashooter,ThreePeashooter,
            Chomper,Wallnut,GatlingPea,PotatoMine,CherryBomb,Tallnut,Twinsunflower,Jalapeno,Spikeweed,Spikerock,
            Sholve,ConeHeadZombie,MetalBucketZombie,PoleVaultingZombie,FootballZombie;

    private void initComponents() {
        jPanel1 = new JPanel();

        setPreferredSize(new Dimension(1012, 785));
        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE));
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 168, Short.MAX_VALUE));

        GroupLayout layout = new GroupLayout(getLayeredPane());
        getLayeredPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(800, Short.MAX_VALUE)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(500, Short.MAX_VALUE))
        );
    }

    //main game windows
    public GamePlay(String[] name) {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        initComponents();

        // Add sun counter board in up-left corner(Add after game panel init, to be
        // in the upper layer)
        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        // Init basic game panel(the most background layer)
        GamePanel gp = new GamePanel(this, sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, 0);

        // Add plant cards to game window
        for (int i = 0; i < 9; ++i) {
            if (name[i] == "sunflower") {
                Sunflower = new PlantCard("images/cards/card_sunflower.png");
                Sunflower.setAction(gp,50,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Sunflower);
                });
                getLayeredPane().add(Sunflower, new Integer(1));
            }

            if (name[i] == "peashooter") {
                Peashooter = new PlantCard("images/cards/card_peashooter.png");
                Peashooter.setAction(gp,100,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Peashooter);
                });
                getLayeredPane().add(Peashooter, new Integer(1));
            }

            if (name[i] == "freezepeashooter") {
                FreezePeashooter = new PlantCard("images/cards/card_freezepeashooter.png");
                FreezePeashooter.setAction(gp,175,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.FreezePeashooter);
                });
                FreezePeashooter.countwaittime();
                getLayeredPane().add(FreezePeashooter, new Integer(1));
            }
            if (name[i] == "twicepeashooter") {
                TwicePeashooter = new PlantCard("images/cards/card_twicepeashooter.png");
                TwicePeashooter.setAction(gp,200,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.TwicePeashooter);
                });
                TwicePeashooter.countwaittime();
                getLayeredPane().add(TwicePeashooter, new Integer(1));
            }
            if (name[i] == "threepeashooter") {
                ThreePeashooter = new PlantCard("images/cards/card_threepeashooter.png");
                ThreePeashooter.setAction(gp,325,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.ThreePeashooter);
                });
                ThreePeashooter.countwaittime();
                getLayeredPane().add(ThreePeashooter, new Integer(1));
            }
            if (name[i] == "torchwood") {
                Torchwood = new PlantCard("images/cards/card_torchwood.png");
                Torchwood.setAction(gp,175,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Torchwood);
                });
                Torchwood.countwaittime();
                getLayeredPane().add(Torchwood, new Integer(1));
            }
            if (name[i] == "wallnut") {
                Wallnut = new PlantCard("images/cards/card_wallnut.png");
                Wallnut.setAction(gp,50,110 + 65 * i, 8, 30000, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Wallnut);
                });
                Wallnut.countwaittime();
                getLayeredPane().add(Wallnut, new Integer(1));
            }
            if (name[i] == "chomper") {
                Chomper = new PlantCard("images/cards/card_chomper.png");
                Chomper.setAction(gp,150,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Chomper);
                });
                Chomper.countwaittime();
                getLayeredPane().add(Chomper, new Integer(1));
            }
            if (name[i] == "potatomine") {
                PotatoMine = new PlantCard("images/cards/card_potatomine.png");
                PotatoMine.setAction(gp,25,110 + 65 * i, 8, 30000, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.PotatoMine);
                });
                PotatoMine.countwaittime();
                getLayeredPane().add(PotatoMine, new Integer(1));
            }
            if (name[i] == "gatling") {
                GatlingPea = new PlantCard("images/cards/card_gatling.png");
                GatlingPea.setAction(gp,250,110 + 65 * i, 8, 50000, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.GatlingPea);
                });
                GatlingPea.countwaittime();
                getLayeredPane().add(GatlingPea, new Integer(1));
            }

            if(name[i]=="cherrybomb"){
                CherryBomb = new PlantCard("images/cards/card_cherrybomb.png");
                CherryBomb.setAction(gp,150,110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.CherryBomb);
                });
                CherryBomb.countwaittime();
                getLayeredPane().add(CherryBomb, new Integer(1));
            }

            if(name[i]=="tallwallnut"){
                Tallnut = new PlantCard("images/cards/card_tallwallnut.png");
                Tallnut.setAction(gp,125,110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Tallnut);
                });
                Tallnut.countwaittime();
                getLayeredPane().add(Tallnut, new Integer(1));
            }

            if(name[i]=="twinsunflower"){
                Twinsunflower = new PlantCard("images/cards/card_twinsunflower.png");
                Twinsunflower.setAction(gp,150,110+65*i, 8,50000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Twinsunflower);
                });
                Twinsunflower.countwaittime();
                getLayeredPane().add(Twinsunflower, new Integer(1));
            }

            if(name[i]=="jalapeno"){
                Jalapeno = new PlantCard("images/cards/card_jalapeno.png");
                Jalapeno.setAction(gp,125,110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Jalapeno);
                });
                Jalapeno.countwaittime();
                getLayeredPane().add(Jalapeno, new Integer(1));
            }

            if (name[i] == "spikeweed") {
                Spikeweed = new PlantCard("images/cards/card_spikeweed.png");
                Spikeweed.setAction(gp,100,110 + 65 * i, 8, 7500, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Spikeweed);
                });
                Spikeweed.countwaittime();
                getLayeredPane().add(Spikeweed, new Integer(1));
            }

            if(name[i] == "spikerock") {
                Spikerock = new PlantCard("images/cards/card_spikerock.png");
                Spikerock.setAction(gp,125,110 + 65 * i, 8, 50000, (ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Spikerock);
                });
                Spikerock.countwaittime();
                getLayeredPane().add(Spikerock, new Integer(1));
            }
        }

        // Add shovel to game window
        Sholve = new PlantCard("images/cards/ShovelBack.png");
        Sholve.setSize(100, 50);
        Sholve.setAction(gp,0,760, 0, 0, (ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sholve);
        });
        getLayeredPane().add(Sholve, new Integer(1));

        getLayeredPane().add(sun, new Integer(2));
        setResizable(false);
        setVisible(true);

    }

    private void jPanel1MouseClicked(MouseEvent evt) {
        //open menu
        System.exit(0);
    }

    private void jPanel1MouseEntered(MouseEvent evt) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void jPanel1MouseExited(MouseEvent evt) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private JPanel jPanel1;
}
