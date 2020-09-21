package spriteframework.Player;

import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Position;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.event.KeyEvent;

public abstract class BasePlayer extends Sprite {

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;
    private int horizontalDisplacement = 0;
    private int verticalDisplacement = 0;
    private int width;

    public BasePlayer(String PlayerImagePath, Position playerInitialPosition) {
        loadImage(PlayerImagePath);
        setPosition(playerInitialPosition);
    }

    protected void loadImage(String PlayerImagePath) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(PlayerImagePath));
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
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
        moveX(horizontalDisplacement);
        moveY(verticalDisplacement);
    }

    public void moveHorizontalDisplacement(int quantityToMove, int direction ){
        horizontalDisplacement = quantityToMove*direction;
    }

    public void moveVerticalDisplacement(int quantityToMove, int direction){
        verticalDisplacement = quantityToMove*direction;
    }

    public int getWidth() {
        return width;
    }
}
