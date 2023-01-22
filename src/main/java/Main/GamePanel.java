package Main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    // FPS
    int FPS = 60;

    // System
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI userI= new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyHandler);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    final int dialogueState = 3;


    public int getTitleState() {
        return titleState;
    }


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    public int getDialogueState() {
        return dialogueState;
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {

            // Update: update information such as character positions
            update();

            // Draw: draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        if(gameState == playState){
            //Player
            player.update();
            // NPC
            for(int i = 0; i < npc.length; i++ ){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

        }
        if(gameState == pauseState){
            // nothing
        }

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        // Main menu
        if(gameState == titleState){
            userI.draw(graphics2D);
        }
        else {
            // Tile
            tileManager.draw(graphics2D);

            // Add entities to the list
            entityList.add(player);

            for(int i=0; i< npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i=0; i< obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldX);
                    return result;
                }
            });
            // Draw Entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(graphics2D);
            }
            // Empty Entity List
            for(int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }

            //UI
            userI.draw(graphics2D);

        }
        // release system resources using it
        graphics2D.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE (int i) {
        se.setFile(i);
        se.play();
    }
}
