package spaceinvaders.sprite;

import spriteframework.Commons;
import spriteframework.Player.BasePlayer;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends BasePlayer {

    private int width;

    public Player() {
        super();
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {

                    dx = -2;
                }

                if (key == KeyEvent.VK_RIGHT) {

                    dx = 2;
                }
            }
        });
        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {

                    dx = 0;
                }

                if (key == KeyEvent.VK_RIGHT) {

                    dx = 0;
                }
            }
        });
    }

    @Override
    protected void loadImage() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/player.png"));
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
    }

    @Override
    protected void initialState() {
        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }

    public void act() {

        x += dx;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

}
