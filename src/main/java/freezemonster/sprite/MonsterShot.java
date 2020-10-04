package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.util.LinkedList;

public class MonsterShot extends BadSprite {

    private boolean destroyed = false;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;
    private final int DOWN = 4;
    private final int movementSpeed = 1;

    public MonsterShot() {
    }

    public MonsterShot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        setImageFromResource(Commons.MONSTER_SHOT_IMAGE_PATH, 25, 25);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    public boolean monsterShotHit(Position position) {
        if (this.getX() == position.getxPosition() && this.getY() == position.getyPosition()) {
            die();
            return true;
        }
        return false;
    }

    public void shotMovement(int direction) {
        int x;
        int y;

            if (shotCanMove()) {
                if (direction == LEFT) {
                    x = getX() - movementSpeed;
                    setX(x);
                } else if (direction == RIGHT) {
                    x = getY() - movementSpeed;
                    setX(x);
                } else if (direction == UP) {
                    y = getY() - movementSpeed;
                    setY(y);
                } else if (direction == DOWN) {
                    y = getY() + movementSpeed;
                    setY(y);
                }
            } else {
                die();
            }
    }

    public boolean shotCanMove() {
        return (this.getX() < Commons.BOARD_WIDTH - 40 && this.getY() < Commons.BOARD_WIDTH - 40)
                && (this.getX() > 2 && getY() > 2);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }


}
