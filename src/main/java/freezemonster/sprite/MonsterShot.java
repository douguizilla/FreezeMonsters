package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

public class MonsterShot extends BadSprite {

    public MonsterShot() {
    }

    public MonsterShot(int x, int y){
        initShot(x,y);
    }

    private void initShot(int x, int y) {
        setImageFromPath(Commons.MONSTER_SHOT_IMAGE_PATH);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    public void monsterShotHit(Position position){
        if(this.getX() == position.getxPosition() && this.getY() == position.getyPosition()){
            die();
        }
    }
}
