package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;

import static freezemonster.Commons.*;


public class Shot extends BadSprite {

    public Shot() {
    }

    public Shot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {

        String shotImg = "freezemonsterimages/ray.png";
        setImageFromPath(shotImg, 50, 50);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }


    public void moveShotToRightDirection(){
        int y = getY() + 4;
        if(y > Commons.BOARD_WIDTH){
            die();
        }else{
            setY(y);
        }
    }

    public void moveShotToLeftDirection(){
        int y = getY() - 4;
        if(y < 0){
            die();
        }else{
            setY(y);
        }
    }

    public void moveShotToDownDirection(){
        int x = getX() + 4;
        if(x > Commons.BOARD_HEIGHT){
            die();
        }else{
            setX(x);
        }
    }

    public void moveShotToUpDirection(){
        int x = getX() - 4;
        if(x < 0){
            die();
        }else{
            setX(x);
        }
    }

    public void actShot(String playerLastDirection) {
        if(isVisible()) {
            if (playerLastDirection.equals(LEFT)) {
                this.moveShotToLeftDirection();
            } else if (playerLastDirection.equals(RIGHT)) {
                this.moveShotToRightDirection();
            } else if (playerLastDirection.equals(DOWN)) {
                this.moveShotToDownDirection();
            } else if (playerLastDirection.equals(UP)) {
                this.moveShotToUpDirection();
            }
        }
    }
}
