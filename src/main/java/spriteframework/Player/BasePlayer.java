package spriteframework.Player;

import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.Sprite;

import java.awt.event.KeyEvent;

public abstract class BasePlayer extends Sprite {

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;

    public BasePlayer() {
        loadImage();
        initialState();
    }

    protected abstract void loadImage();

    protected abstract void initialState();

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


}
