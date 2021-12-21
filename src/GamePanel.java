import javax.swing.*;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JLayeredPane implements MouseMotionListener {

    private final Image bgImage;
    private final Image pauseImage;

    // Collider grids have plants info
    private final Collider[] colliders;
    private Collider[] brains;

    private final ArrayList<ArrayList<Zombie>> laneZombies;
    private final ArrayList<ArrayList<Pea>> lanePeas;
    private final ArrayList<Sun> activeSuns;

    private final Timer redrawTimer;
    private final Timer advancerTimer;
    private final Timer sunProducer;
    private final ZombieProducer zombieProducer;
    // private Timer zombieProducer;
    private Timer loseTimer;
    private final Timer winTimer;
    private final JLabel sunScoreboard;

    private final SoundEffect bgm = new SoundEffect("./src/bgms/pvzBG1.wav");
    private final SoundEffect zombiesComing = new SoundEffect("./src/bgms/zombiecoming.wav");


    private GamePlay.PlantType activePlantingBrush = GamePlay.PlantType.None;
    private final GamePlay gw;

    private int sunScore;

    private final Random rnd;

    // Two boolean var to determine whether display new Wave image
    public boolean newStage = false;
    public boolean finalStage = false;

    private final Image newStageImg;
    private final Image finalStageImg;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    public GamePanel(GamePlay gamewin, JLabel sunScoreboard) {
        gw = gamewin;
        rnd = new Random();

        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getResource("images/mainB.png")).getImage();
        pauseImage = new ImageIcon(this.getClass().getResource("images/Button2.png")).getImage();
        
        System.out.printf("before img loading");
        newStageImg = new ImageIcon(this.getClass().getResource("images/LargeWave.gif")).getImage();
        finalStageImg = new ImageIcon(this.getClass().getResource("images/FinalWave.gif")).getImage();
        System.out.printf("after img loading");

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

       
        for (int i=0;i<5;i++){
            lanePeas.get(i).add(new LawnCleaner(this,i,0,0));
        }
        
        // Grids to plant plants
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            a.setAction(new PlantActionListener((i % 9), (i / 9)));
            colliders[i] = a;
            add(a, new Integer(0));
        }

        activeSuns = new ArrayList<>();

        // repaint every 5 ms(to play gifs)
        redrawTimer = new Timer(10, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        // advance(game logic computation) every 50 ms
        advancerTimer = new Timer(50, (ActionEvent e) -> advance());
        advancerTimer.start();

        // generate one sun every 5s
        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sta);
            add(sta, 2);
        });
        sunProducer.start();
        zombieProducer = new ZombieProducer(rnd, this);
        zombieProducer.start();

        bgm.prepare();
        zombiesComing.prepare();

        
        loseTimer = new Timer(25, (ActionEvent e) -> {
            boolean haslose=false;
            for(int i=0;i<5;i++){
                for(int j=0;j<laneZombies.get(i).size();++j){
                    if(laneZombies.get(i).get(j).getPosX()<-50){
                        haslose=true;
                        break;
                    }
                }
            }
            if(haslose){
                SoundEffect zombiesWin = new SoundEffect("./src/bgms/zombiegroup.wav");
                zombiesWin.prepare();
                zombiesWin.player.start();
                bgm.player.stop();
                loseTimer.stop();
                JOptionPane.showMessageDialog(GamePanel.this, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                gamewin.dispose();
                System.exit(0);
            }
        });
        loseTimer.start();
        
        winTimer = new Timer(25, (ActionEvent e) -> {
            boolean empty = true;
            for(int i = 0; i < 5; ++i){
                if(!laneZombies.get(i).isEmpty())
                {
                    empty = false;
                    break;
                }
            }
            if(empty && zombieProducer.total_complete)
            {
            	bgm.player.stop();
                JOptionPane.showMessageDialog(null, "LEVEL_CONTENT Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
                LevelData.write("1");
                System.exit(0);
            }
        });
        winTimer.start();


        bgm.player.loop(Clip.LOOP_CONTINUOUSLY);
        zombiesComing.player.start();
    }

    private void advance() {
        for (int i = 0; i < 5; i++) {
            // Zombies move forward and detect plants to attack
            for (int j = 0; j < laneZombies.get(i).size(); j++){
                Zombie z = laneZombies.get(i).get(j);
                z.advance();
            }

            // Peas move forward and detect collision with zombies
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }
            // Plants have self timer tasks to produce peas or attack(don't advance here)

        }
        if(activeSuns!=null){
            for (int i = 0; i < activeSuns.size(); i++) {
                activeSuns.get(i).advance();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);

        g.drawImage(pauseImage, 860, 0, 140, 50, null);

        //Draw brains
        if(brains !=null){
            for (int i = 0; i < 5 ; i++) {
                Collider c = brains[i];
                if (c.assignedPlant != null){
                    Plant p = c.assignedPlant;
                    g.drawImage(p.getImage(), 0, 129 + p.getY() * 120, null);
                }
            }
        }


        // Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedPlant != null) {
                Plant p = c.assignedPlant;
                if (p instanceof Tallnut) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 60 + p.getY() * 120, null);
                } else if (p instanceof Spikeweed || p instanceof Spikerock) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 180 + p.getY() * 120, null);
                } else if (p instanceof Chomper) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 90 + p.getY() * 120, null);
                } else {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 129 + p.getY() * 120, null);
                }
            }
        }


        // Draw Peas

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea pea = lanePeas.get(i).get(j);
                g.drawImage(pea.getImage(), pea.getPosX(), 130 + (i * 120), null);
            }
        }


        if(newStage)
            g.drawImage(newStageImg, 400, 300, null);
        if(finalStage)
            g.drawImage(finalStageImg, 400, 300, null);


        // Zombies are JPanels, don't need to draw manually here
    }

    private class PlantActionListener implements ActionListener {

        int x, y;
        private SoundEffect plant = new SoundEffect("./src/bgms/plant.wav");

        public PlantActionListener(int x, int y) {
            this.x = x;
            this.y = y;
            plant.prepare();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activePlantingBrush == GamePlay.PlantType.ConeHeadZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 75) {
                    ConeHeadZombie z = new ConeHeadZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 75);
                    gw.ConeHeadZombie.countwaittime();
                }
            }
            if (activePlantingBrush == GamePlay.PlantType.PoleVaultingZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 75) {
                    PoleVaultingZombie z = new PoleVaultingZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 75);
                    gw.PoleVaultingZombie.countwaittime();
                }
            }
            if (activePlantingBrush == GamePlay.PlantType.MetalBucketZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 125) {
                    MetalBucketZombie z = new MetalBucketZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, 2);
                    setSunScore(getSunScore() - 125);
                    gw.MetalBucketZombie.countwaittime();
                }
            }
            if (activePlantingBrush == GamePlay.PlantType.FootballZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 175) {
                    FootballZombie z = new FootballZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, 2);
                    setSunScore(getSunScore() - 175);
                    gw.FootballZombie.countwaittime();
                }
            }
            
            if (activePlantingBrush == GamePlay.PlantType.GatlingPea){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 250&&colliders[x + y * 9].assignedPlant instanceof TwicePeashooter) {
                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new GatlingPea(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 250);
                    gw.GatlingPea.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Twinsunflower) {
                plant.prepare();
                plant.player.start();
                if (getSunScore() >= 150 && colliders[x + y * 9].assignedPlant instanceof Sunflower) {
                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new Twinsunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 150);
                    gw.Twinsunflower.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Spikerock && colliders[x + y * 9].assignedPlant instanceof Spikeweed) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new Spikerock(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Spikerock.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Sholve) {
                plant.prepare();
                plant.player.start();
                if (colliders[x + y * 9].assignedPlant != null) {
                    colliders[x + y * 9].removePlant();
                    activePlantingBrush = GamePlay.PlantType.None;
                }
            }

            if (colliders[x + y * 9].assignedPlant != null) {
                activePlantingBrush = GamePlay.PlantType.None;
            }

            if (activePlantingBrush == GamePlay.PlantType.Sunflower) {
                plant.player.start();
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                    gw.Sunflower.countwaittime();
                }
            }
            
            if (activePlantingBrush == GamePlay.PlantType.Peashooter) {
                plant.player.start();
                if (getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Peashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.FreezePeashooter) {
                plant.player.start();
                if (getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                    gw.FreezePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Torchwood) {
                plant.player.start();
                if (getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new Torchwood(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                    gw.Torchwood.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.TwicePeashooter) {
                plant.player.start();
                if (getSunScore() >= 200) {
                    colliders[x + y * 9].setPlant(new TwicePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 200);
                    gw.TwicePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.ThreePeashooter) {
                plant.player.start();
                if (getSunScore() >= 325) {
                    colliders[x + y * 9].setPlant(new ThreePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 325);
                    gw.ThreePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Chomper) {
                plant.player.start();
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new Chomper(GamePanel.this, x, y, 1, 0));
                    setSunScore(getSunScore() - 150);
                    gw.Chomper.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Wallnut) {
                plant.player.start();
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Wallnut(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 50);
                    gw.Wallnut.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.PotatoMine) {
                plant.player.start();
                if (getSunScore() >= 25) {
                    colliders[x + y * 9].setPlant(new PotatoMine(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 25);
                    gw.PotatoMine.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.CherryBomb) {
                plant.player.start();
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new CherryBomb(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 150);
                    gw.CherryBomb.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Tallnut) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].setPlant(new Tallnut(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 125);
                    gw.Tallnut.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Jalapeno) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].setPlant(new Jalapeno(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 125);
                    gw.Jalapeno.countwaittime();
                }
            }

            if (activePlantingBrush == GamePlay.PlantType.Spikeweed) {
                plant.player.start();
                if (getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Spikeweed(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Spikeweed.countwaittime();
                }
            }
            activePlantingBrush = GamePlay.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    static int progress = 0;

    public static void setProgress(int num) {
        progress = progress + num;
    }

    public void setActivePlantingBrush(GamePlay.PlantType activePlantingBrush) {
        this.activePlantingBrush = activePlantingBrush;
    }

    public ArrayList<ArrayList<Zombie>> getLaneZombies() {
        return laneZombies;
    }

    public ArrayList<ArrayList<Pea>> getLanePeas() {
        return lanePeas;
    }

    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }

    public Collider[] getColliders() {
        return colliders;
    }

    public Collider[] getBrain() {
        return brains;
    }
}
