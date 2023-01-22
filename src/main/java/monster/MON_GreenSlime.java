package monster;

import Main.GamePanel;
import entity.Entity;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        speed = 1;
        setMaxLife(4);
        setLife(getMaxLife());
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("/Monsters/greenslime_down_1.png");
        up2 = setup("/Monsters/greenslime_down_2.png");
        down1 = setup("/Monsters/greenslime_down_1.png");
        down2 = setup("/Monsters/greenslime_down_2.png");
        left1 = setup("/Monsters/greenslime_down_1.png");
        left2 = setup("/Monsters/greenslime_down_2.png");
        right1 = setup("/Monsters/greenslime_down_1.png");
        right2 = setup("/Monsters/greenslime_down_2.png");
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) +1;
            // change it later to switch

            if(i <= 25) {
                direction = "up";
            }
            if( i > 25 && i <= 50){
                direction = "down";
            }
            if( i > 50 && i <= 75){
                direction = "left";
            }
            if( i > 75){
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }
}
