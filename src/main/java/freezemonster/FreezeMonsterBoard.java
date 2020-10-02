package freezemonster;

import freezemonster.sprite.MonsterShot;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Player;
import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import static freezemonster.Commons.getRandomNumberInRage;

public class FreezeMonsterBoard extends AbstractBoard {
    private LinkedList<BadSprite> monsters;
    private MonsterShot shot;
    private GameBoardSpecification gameBoardSpecification;
    private int direction = -1;
    private int deaths = 0;
    protected String message = "Game Over";

    public FreezeMonsterBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification, true);
        this.gameBoardSpecification = gameBoardSpecification;

        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                players.get(0).keyPressed(keyEvent);
            }
        });

        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                players.get(0).keyReleased(keyEvent);
            }
        });
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        LinkedList<BadSprite> monsters = new LinkedList<>();
        for(int i = 0; i < Commons.MONSTERS_PATH_IMAGES.length; i++){
            int x = getRandomNumberInRage(Commons.BOARD_WIDTH, 0);
            int y = getRandomNumberInRage(Commons.BOARD_HEIGHT, 0);
            MonsterSprite monster = new MonsterSprite(x, y, Commons.MONSTERS_PATH_IMAGES[i],  Commons.MONSTERS_PATH_IMAGES[i]);
            monsters.add(monster);
        }
        return monsters;
    }


    @Override
    protected void createPlayers() {
        players.add(new Player());
    }

    @Override
    protected void drawBoard() {
        graphicsDrawner.fillRectangle(
                new Rectangle(
                        0,
                        0,
                        dimension.width,
                        dimension.height
                ),
                gameBoardSpecification.getColor()
        );
        if (inGame) {
            Position initialLinePosition = new Position(
                    0,
                    gameBoardSpecification.getGround()
            );

            Position finalLinePosition = new Position(
                    gameBoardSpecification.getBoardWidth(),
                    gameBoardSpecification.getGround()
            );
        }
    }

    @Override
    protected void gameOver() {
        graphicsDrawner.fillRectangle(new Rectangle(
                        0,
                        0,
                        gameBoardSpecification.getBoardWidth(),
                        gameBoardSpecification.getBoardHeight()
                ), Color.black
        );

        graphicsDrawner.fillRectangle(new Rectangle(
                        50,
                        gameBoardSpecification.getBoardWidth() / 2 - 30,
                        gameBoardSpecification.getBoardWidth() - 100,
                        50
                ), new Color(0, 32, 48)
        );

        graphicsDrawner.drawRectangle(new Rectangle(
                        50,
                        gameBoardSpecification.getBoardWidth() / 2 - 30,
                        gameBoardSpecification.getBoardWidth() - 100,
                        50
                ), Color.white
        );

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        Position position = new Position(
                (gameBoardSpecification.getBoardWidth() - fontMetrics.stringWidth(message)) / 2,
                gameBoardSpecification.getBoardWidth() / 2
        );
        graphicsDrawner.drawString(small, Color.white, position, message);
    }

    @Override
    protected void update() {
        if (deaths == Commons.NUMBER_OF_MONSTERS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        Player player = (Player) players.get(0);

        if(player.isShotVisible()){
            Position shotPosition = player.getShotPosition();
            for(BadSprite monster: badSprites){
                MonsterSprite monsterSprite = (MonsterSprite) monster;
                boolean monsterHit = monsterSprite.monsterHit(shotPosition);

                if(monsterHit){
                    deaths++;
                    player.disappearShot();
                }
            }
            player.actShot();
        }

        for(BadSprite monster: badSprites){
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            monsterSprite.moveMonster();
        }



    }
}
