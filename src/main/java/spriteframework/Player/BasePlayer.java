package spriteframework.Player;

import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Position;
import spriteframework.sprite.Sprite;

import java.awt.event.KeyEvent;

public abstract class BasePlayer extends Sprite {

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;
    private int horizontalDisplacement = 0;
    private int verticalDisplacement = 0;

    public BasePlayer(String playerImagePath, Position playerInitialPosition) {
        setImageFromResource(playerImagePath);
        setPosition(playerInitialPosition);
    }

    public BasePlayer(String playerImagePath, int width, int height, Position playerInitialPosition) {
        setImageFromResource(playerImagePath, width, height);
        setPosition(playerInitialPosition);
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

    public void update(){
        moveX(horizontalDisplacement);
        moveY(verticalDisplacement);
    }

    public int moveHorizontalDisplacement(int quantityToMove, int direction ){
        return horizontalDisplacement = quantityToMove*direction;
    }

    public int moveVerticalDisplacement(int quantityToMove, int direction){
        return verticalDisplacement = quantityToMove*direction;
    }
}
