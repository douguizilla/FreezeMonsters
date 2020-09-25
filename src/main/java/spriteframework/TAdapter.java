package spriteframework;

import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {

    private KeyPressedListener keyPressedListener;
    private KeyReleasedListener keyReleasedListener;

    public void setKeyPressedListener(KeyPressedListener keyPressedListener) {
        this.keyPressedListener = keyPressedListener;
    }

    public void setKeyReleasedListener(KeyReleasedListener keyReleasedListener) {
        this.keyReleasedListener = keyReleasedListener;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isKeyReleasedListenerNotNull()) {
            keyReleasedListener.onKeyReleased(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isKeyPressedListenerNotNull()) {
            keyPressedListener.onKeyPressed(e);
        }
    }

    private boolean isKeyPressedListenerNotNull(){
        return keyPressedListener != null;
    }

    private boolean isKeyReleasedListenerNotNull(){
        return keyReleasedListener != null;
    }
}
