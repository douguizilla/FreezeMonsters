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
        super("/images/player.png");
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(-2);
                }

                else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(2);
                }
            }
        });

        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(0);
                }

                else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(0);
                }
            }
        });
    }


    @Override
    public void act() {
        super.act();

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

}
