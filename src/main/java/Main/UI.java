package Main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage heart_full, heart_half, heart_blank;
    Font arial_40, arial_80B, purisaB;
    public boolean messageOn = false;
    public String message = " ";
    String currentDialogue = " ";
    int messageCounter = 0;
    boolean gameFinished = false;
    int commandNum = 0;

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        InputStream is = getClass().getResourceAsStream("/Fonts/PurisaB.ttf");
        try {
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create Hud
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        //Menu State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //Play State
        if (gp.gameState == gp.playState) {

            drawPlayerLife();
        }
        // Pause State
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();

        }
        //Dialogue State
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }
    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // Draw Blank heart
        while(i < gp.player.getMaxLife()/2) {
            g2.drawImage(heart_blank, x,y, null);
            i++;
            x += gp.tileSize;
        }
        // Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        // Draw Current Life
        while(i < gp.player.getLife()) {
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.getLife()){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;

        }

    }
    public void drawTitleScreen() {
        g2.setColor(new Color(70, 120, 89));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
        String text = "2D Adventure";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        //Main Color
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Game Menu Image
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2, gp.tileSize *2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x,y);

        if(commandNum <= 0) {
            if(commandNum < 0) {
                commandNum = 2;
            }
            g2.drawString(">", x-gp.tileSize,y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x,y);
        if(commandNum == 1) {
            g2.drawString(">", x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x,y);
        if(commandNum >= 2) {
            if(commandNum > 2) {
                commandNum = 0;
            }
            g2.drawString(">", x-gp.tileSize,y);
        }



    }

    public void drawDialogueScreen() {
        // Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 150);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;

    }
}
