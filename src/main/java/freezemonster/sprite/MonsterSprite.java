package freezemonster.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import java.util.LinkedList;

public class MonsterSprite extends BadnessBoxSprite {
    private MonsterShot shot;

    public MonsterSprite(int x, int y, String imagePath) {
        initMonster(x, y);
        setImageFromPath(imagePath);
    }

    private void initMonster(int x, int y) {
        this.setX(x);
        this.setY(y);
        shot = new MonsterShot(x, y);
    }

    public MonsterShot getShot() {
        return shot;
    }

    @Override
    public LinkedList<BadSprite> getBadnesses() {
        LinkedList<BadSprite> aMonsterShot = new LinkedList<BadSprite>();
        aMonsterShot.add(shot);
        return aMonsterShot;
    }
}
