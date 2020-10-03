package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Position;

import java.util.LinkedList;
import java.util.Random;

import static freezemonster.Commons.getRandomNumberInRage;

public class MonsterSprite extends BadnessBoxSprite {
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;
    private final int DOWN = 4;
    private MonsterShot shot;
    private String imagePathDead;

    public MonsterSprite(int x, int y, String imagePath, String imagePathDead) {
        initMonster(x, y);
        setImageFromResource(imagePath, 40,40);
        this.imagePathDead = imagePathDead;
    }

    private void initMonster(int x, int y) {
        this.setX(x);
        this.setY(y);
        shot = new MonsterShot(x, y);
    }

    public MonsterShot getShot() {
        return shot;
    }

    @Override
    public LinkedList<BadSprite> getBadnesses() {
        LinkedList<BadSprite> aMonsterShot = new LinkedList<BadSprite>();
        aMonsterShot.add(shot);
        return aMonsterShot;
    }

    public boolean monsterHit(Position position){
        boolean hit = isHit(position);
        if(!isDying() && hit){
            monsterFrozen();
        }
        return hit;
    }

    private boolean isHit(Position position){
        return position.getxPosition() >= (getX())
                && position.getxPosition() <= (getX() + getImageWidth())
                && position.getyPosition() >= (getY())
                && position.getyPosition() <= (getY() + getImageHeight());
    }

    private void monsterFrozen(){
        setImageFromPath(imagePathDead);
        setDying(true);
    }

    public void moveMonster(){
        if(!isDying()){
            boolean monsterMoved = false;
            while(monsterNeedMove(monsterMoved)){
                int direction = getRandomNumberInRage(4,1);
                if(direction == LEFT){
                    monsterMoved = moveMonsterToLeftDirection();
                }else if(direction == RIGHT){
                    monsterMoved = moveMonsterToRightDirection();
                }
                else if(direction == UP){
                    monsterMoved = moveMonsterToUpDirection();
                }
                else if(direction == DOWN){
                    monsterMoved = moveMonsterToDownDirection();
                }
            }
        }
    }

    private boolean monsterNeedMove(boolean moved){
        return !moved;
    }

    private boolean moveMonsterToLeftDirection(){
        int y = getY() - 4;
        if(y < 0){
            return false;
        }
        setY(y);
        return true;
    }

    private boolean moveMonsterToRightDirection(){
        int y = getY() + 4;
        if(y > Commons.BOARD_WIDTH){
            return false;
        }
        setY(y);
        return true;
    }

    private boolean moveMonsterToUpDirection(){
        int x = getX() - 4;
        if(x < 0){
            return false;
        }
        setX(x);
        return true;
    }

    private boolean moveMonsterToDownDirection(){
        int x = getX() + 4;
        if(x > Commons.BOARD_HEIGHT){
            return false;
        }
        setX(x);
        return true;
    }
}
