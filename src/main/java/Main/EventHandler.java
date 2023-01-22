package Main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col<gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {
        // check if the player is more then 1 tile away from the last event
        int xDistanc = Math.abs(gp.player.worldX - previousEventX);
        int yDistanc = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistanc,yDistanc);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(hit(27,16, "right")){
                //event happens
                damagePit(27,16,gp.dialogueState);
            }
            if(hit(23,7,"up")) {
                healingPool(23,7,gp.dialogueState);
            }
        }


    }
    public void healingPool(int col, int row, int gameState){
        if(gp.keyHandler.enterPressed){
            gp.gameState = gameState;
            gp.userI.currentDialogue = "You dring the water. \nYour life have been recovered";
            gp.player.setLife(gp.player.getMaxLife());
        }
    }
    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.userI.currentDialogue = "You fall into a pit!";
        gp.player.setLife(gp.player.getLife() - 1);
        eventRect[col][row].eventDone = true;
        canTouchEvent = false;

    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if(gp.player.direction == reqDirection || reqDirection == "any"){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x =  eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y =  eventRect[col][row].eventRectDefaultY;


        return hit;
    }
}
