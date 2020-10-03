package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.Player.BasePlayer;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Position;

import java.awt.event.KeyEvent;
import java.util.zip.DeflaterInputStream;

public class Player extends BasePlayer {

    private final String LEFT = "left";
    private final String RIGHT = "right";
    private final String UP = "up";
    private final String DOWN = "down";

    private Shot shot = new Shot();
    private String lastDirection = UP;

    public Player() {
        super(Commons.PLAYER_IMAGE_PATH, 30, 50,
                new Position(Commons.INIT_PLAYER_X, Commons.INIT_PLAYER_Y));
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(2, LEFT_DIRECTION);
                    lastDirection = LEFT;
                } else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(2, RIGHT_DIRECTION);
                    lastDirection = RIGHT;
                } else if (key == KeyEvent.VK_UP) {
                    moveVerticalDisplacement(2, UP_DIRECTION);
                    lastDirection = UP;
                } else if (key == KeyEvent.VK_DOWN) {
                    moveVerticalDisplacement(2, DOWN_DIRECTION);
                    lastDirection = DOWN;
                }
            }
        });

        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(0, LEFT_DIRECTION);
                } else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(0, RIGHT_DIRECTION);
                } else if (key == KeyEvent.VK_UP) {
                    moveVerticalDisplacement(0, UP_DIRECTION);
                } else if (key == KeyEvent.VK_DOWN) {
                    moveVerticalDisplacement(0, DOWN_DIRECTION);
                }
            }
        });
    }

    public void update() {
        super.update();
        if (getX() <= 2) {
            setX(2);
        }
        if (getX() >= (Commons.BOARD_WIDTH - 2 * this.getImageWidth())) {
            setX(Commons.BOARD_WIDTH - 2 * this.getImageWidth());
        }
    }

    public boolean isShotVisible() {
        return shot.isVisible();
    }

    public Position getShotPosition() {
        return shot.getPosition();
    }

    public void disappearShot() {
        shot.die();
    }

    public void actShot() {
        if (lastDirection.equals(LEFT)) {
            shot.moveShotToLeftDirection();
        } else if (lastDirection.equals(RIGHT)) {
            shot.moveShotToRightDirection();
        } else if (lastDirection.equals(DOWN)) {
            shot.moveShotToDownDirection();
        } else if (lastDirection.equals(UP)) {
            shot.moveShotToUpDirection();
        }
    }

}
