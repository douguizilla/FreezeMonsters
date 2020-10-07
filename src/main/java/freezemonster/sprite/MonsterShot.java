package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.util.LinkedList;

import static freezemonster.Commons.getRandomNumberInRage;

public class MonsterShot extends BadSprite {

    private boolean destroyed = false;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;
    private final int DOWN = 4;
    private final int movementSpeed = 1;
    private int direction = 1;

    public MonsterShot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        setImageFromResource(Commons.MONSTER_SHOT_IMAGE_PATH, Commons.SHOT_SIZE, Commons.SHOT_SIZE);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    public boolean shotWasHit(Position position) {
        boolean hit = isHit(position);
        if(!isDestroyed() && hit){
            setDestroyed(true);
        }else{
            hit = false;
        }
        return hit;
    }

    private boolean isHit(Position position){
        return position.getxPosition() >= (getX())
                && position.getxPosition() <= (getX() + getImageWidth())
                && position.getyPosition() >= (getY())
                && position.getyPosition() <= (getY() + getImageHeight());
    }

    public void shotMovement() {
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
            }
            else {
                setDestroyed(true);
                this.direction = getRandomNumberInRage(4,1);
            }
    }

    public boolean shotCanMove() {
        if((this.getX() <= (Commons.BOARD_WIDTH - 75) && this.getY() <= (Commons.BOARD_HEIGHT - 75))
                && (this.getX() > 2 && getY() > 2)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }


}
