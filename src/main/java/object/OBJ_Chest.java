package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        down1 = setup("/Object/chest");
        name = "Chest";


    }
}
