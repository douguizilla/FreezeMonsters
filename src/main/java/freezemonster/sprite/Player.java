package freezemonster.sprite;

import spaceinvaders.Commons;
import spriteframework.Player.BasePlayer;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Position;

import java.awt.event.KeyEvent;

public class Player extends BasePlayer {
    public Player(String playerImagePath, Position playerInitialPosition) {
        super(playerImagePath, playerInitialPosition);
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(2, LEFT_DIRECTION);
                }
                else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(2, RIGHT_DIRECTION);
                }
                else if(key == KeyEvent.VK_UP){
                    moveVerticalDisplacement(2,UP_DIRECTION);
                }
                else if(key == KeyEvent.VK_DOWN){
                    moveVerticalDisplacement(2,DOWN_DIRECTION);
                }
            }
        });

        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(0, LEFT_DIRECTION);
                }
                else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(0, RIGHT_DIRECTION);
                }
                else if(key == KeyEvent.VK_UP){
                    moveVerticalDisplacement(0,UP_DIRECTION);
                }
                else if(key == KeyEvent.VK_DOWN){
                    moveVerticalDisplacement(0,DOWN_DIRECTION);
                }
            }
        });
    }

    public void update() {
        super.update();
        if (getX() <= 2) {
            setX(2);
        }
        if (getX() >= Commons.BOARD_WIDTH - 2 * this.getImageWidth()) {
            setX(Commons.BOARD_WIDTH - 2 * this.getImageWidth());
        }
    }
}
