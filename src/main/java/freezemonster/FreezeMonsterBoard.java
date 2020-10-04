package freezemonster;

import freezemonster.sprite.MonsterShot;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Player;
import freezemonster.sprite.Shot;
import spaceinvaders.sprite.Bomb;
import spaceinvaders.sprite.BomberSprite;
import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.Player.BasePlayer;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.listeners.OtherSpriteListener;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import static freezemonster.Commons.getRandomNumberInRage;

public class FreezeMonsterBoard extends AbstractBoard {
    private LinkedList<BadSprite> monsters;
    private Shot shot;
    private GameBoardSpecification gameBoardSpecification;
    private int direction = -1;
    private int deaths = 0;
    private String playerLastDirection = Commons.UP;
    private int monsterShotDirection = 1;
    protected String message = "Game Over";

    public FreezeMonsterBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification, true);
        this.gameBoardSpecification = gameBoardSpecification;

        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                players.get(0).keyPressed(keyEvent);
                processOtherSprites(players.get(0), keyEvent);
            }
        });

        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                players.get(0).keyReleased(keyEvent);
            }
        });

        setOtherSpriteListener(new OtherSpriteListener() {
            @Override
            public void createOtherSprites() {
                shot = new Shot();
            }

            @Override
            public void drawOtherSprites() {
                drawShot();
            }
        });
    }

    private void drawShot() {
        if (shot.isVisible()) {
            drawSprite(shot);
        }
    }

    protected void processOtherSprites(BasePlayer player, KeyEvent e) {
        int x = player.getX();
        int y = player.getY();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (inGame) {
                if (!shot.isVisible()) {
                    shot = new Shot(x, y);
                }
            }
        }
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        LinkedList<BadSprite> monsters = new LinkedList<>();
        for (int i = 0; i < Commons.MONSTERS_PATH_IMAGES.length; i++) {
            int x = getRandomNumberInRage(Commons.BOARD_WIDTH - 80, 0);
            int y = getRandomNumberInRage(Commons.BOARD_HEIGHT - 80, 0);
            MonsterSprite monster = new MonsterSprite(x, y, Commons.MONSTERS_PATH_IMAGES[i], Commons.DEAD_MONSTERS_PATH_IMAGES[i]);
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
    protected void gameFinished() {
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
        player.update();

        if (!shot.isVisible()) {
            playerLastDirection = player.getPlayerLastDirection();
        }

        if (shot.isVisible()) {

            for (BadSprite monster : badSprites) {
                MonsterSprite monsterSprite = (MonsterSprite) monster;
                boolean monsterHit = monsterSprite.monsterHit(shot.getPosition());
                boolean monsterShotHit = monsterSprite.getShot().monsterShotHit(shot.getPosition());

                if (monsterHit) {
                    deaths++;
                    shot.die();
                }

                if(monsterShotHit){
                    shot.die();
                }

            }
            shot.actShot(playerLastDirection);
        }

        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            monsterSprite.moveMonster();
        }
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
        for (BadSprite monster : badSprites) {
            MonsterShot monsterShot = ((MonsterSprite) monster).getShot();

            if (monster.isVisible() && monsterShot.isDestroyed()) {

                monsterShot.setDestroyed(false);
                monsterShot.setX(monster.getX());
                monsterShot.setY(monster.getY());
            }

            int monsterShotX = monsterShot.getX();
            int monsterShotY = monsterShot.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !monster.isDestroyed()) {

                if (monsterShotX >= (playerX)
                        && monsterShotX <= (playerX + Commons.PLAYER_WIDTH)
                        && monsterShotY >= (playerY)
                        && monsterShotY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    players.get(0).setDying(true);
                    monsterShot.setDestroyed(true);
                }
            }

            if(!monsterShot.isVisible()){
                monsterShotDirection = getRandomNumberInRage(4,1);
            }

            if (!monsterShot.isDestroyed()) {
                monsterShot.shotMovement(monsterShotDirection);
                if(!monsterShot.shotCanMove()){
                    monsterShot.setDestroyed(true);
                }
            }
        }
    }
}
