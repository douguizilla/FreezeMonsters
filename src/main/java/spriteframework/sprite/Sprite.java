package spriteframework.sprite;

import java.awt.*;

public class Sprite {
    protected int LEFT_DIRECTION = -1;
    protected int RIGHT_DIRECTION = 1;
    protected int UP_DIRECTION = -1;
    protected int DOWN_DIRECTION = 1;

    private boolean visible;
    protected Image image;
    private boolean dying;

    protected int imageWidth;
    protected int imageHeight;
    private Position position = new Position();

    public Sprite() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        position.setxPosition(x);
    }

    public void setY(int y) {
        position.setyPosition(y);

    }

    public int getY() {
        return position.getyPosition();
    }

    public int getX() {
        return position.getxPosition();
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Rectangle getRect() {
        return new Rectangle(getX(), getY(),
                image.getWidth(null), image.getHeight(null));
    }

    public void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }

    public void moveX(int direction) {
        int newPosition = getX()+direction;
        setX(newPosition);
    }

    public void moveY(int direction) {
        int newPosition = getY()+direction;
        setY(newPosition);
    }
}
