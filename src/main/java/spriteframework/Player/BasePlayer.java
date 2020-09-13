package spriteframework.Player;

import spriteframework.Commons;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.event.KeyEvent;

public abstract class BasePlayer extends Sprite {

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;
    private int horizontalDisplacement = 0;
    private int verticalDisplacement = 0;
    private int width;

    public BasePlayer(String PlayerImagePath) {
        loadImage(PlayerImagePath);
        initialState();
    }



    protected void loadImage(String PlayerImagePath) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(PlayerImagePath));
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
    }

    protected void initialState() {
        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }



    public void setKeyPressedListener(KeyPressedListener keyPressedListener) {
        this.keyPressedListener = keyPressedListener;
    }

    public void setKeyReleasedListener(KeyReleasedListener keyReleasedListener) {
        this.keyReleasedListener = keyReleasedListener;
    }

    public void keyPressed(KeyEvent eventKey) {
        if (keyPressedListener != null)
            keyPressedListener.onKeyPressed(eventKey);
    }

    public void keyReleased(KeyEvent eventKey) {
        if (keyReleasedListener != null)
            keyReleasedListener.onKeyReleased(eventKey);
    }

    public void act(){
        x += horizontalDisplacement;
    }

    public void moveHorizontalDisplacement(int number){
        horizontalDisplacement = number;
    }

    public void moveVerticalDisplacement(int number){
        verticalDisplacement = number;
    }

    public int getWidth() {
        return width;
    }
}
