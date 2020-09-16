package spriteframework.Player;

import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.event.KeyEvent;

public abstract class BasePlayer extends Sprite {
    protected int LEFT_DIRECTION = -1;
    protected int RIGHT_DIRECTION = 1;
    protected int UP_DIRECTION = -1;
    protected int DOWN_DIRECTION = 1;

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;
    private int horizontalDisplacement = 0;
    private int verticalDisplacement = 0;
    private int width;
    //TODO lista de badsprite representar os tiros

    //TODO transformar int playerInitialPositionX, int playerInitialPositionY em ponto e todo lugar q usa X,Y
    public BasePlayer(String PlayerImagePath, int playerInitialPositionX, int playerInitialPositionY) {
        loadImage(PlayerImagePath);
        initialState(playerInitialPositionX,playerInitialPositionY);
    }

    protected void loadImage(String PlayerImagePath) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(PlayerImagePath));
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
    }

    protected void initialState(int playerInitialPositionX, int playerInitialPositionY) {
        setX(playerInitialPositionX);
        setY(playerInitialPositionY);
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
        y += verticalDisplacement;
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
