package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    boolean checkDrawTime = false;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_W){
                 gp.userI.commandNum--;
            }
            if(code == KeyEvent.VK_S){
                gp.userI.commandNum++;
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.userI.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);

                }
                else if(gp.userI.commandNum == 1){
                    // work in progress
                }
                else if(gp.userI.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        // Play State
        if(gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W){
                upPressed = true;

            }
            if(code == KeyEvent.VK_S){
                downPressed = true;

            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;

            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }

            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;

            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;

            }
        }
        // Pause State
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;

            }

        }
        // Dialogue State
        else if(gp.gameState == gp.getDialogueState()){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;

        }
        if(code == KeyEvent.VK_S){
            downPressed = false;

        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;

        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;

        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;

        }

        // Debug

        if(code == KeyEvent.VK_T){
            if(checkDrawTime == true){
                checkDrawTime = false;
            } else if(checkDrawTime == false){
                checkDrawTime = true;
            }

        }

    }
}
