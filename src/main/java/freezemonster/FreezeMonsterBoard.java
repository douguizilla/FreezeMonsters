package freezemonster;

import freezemonster.sprite.MonsterShot;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Player;
import freezemonster.sprite.Shot;
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

import static freezemonster.Commons.NUMBER_OF_MONSTERS_TO_DESTROY;
import static freezemonster.Commons.getRandomNumberInRage;

public class FreezeMonsterBoard extends AbstractBoard {
    private LinkedList<BadSprite> monsters;
    private Shot shot;
    private GameBoardSpecification gameBoardSpecification;
    private int deaths = 0;
    private String playerLastDirection = Commons.UP;
    private Player player;
    protected String message = "Game Over";


    public FreezeMonsterBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification, true);
        this.gameBoardSpecification = gameBoardSpecification;
        configListeners();
    }

    private void configListeners() {
        configKeyPressedListener();
        configKeyReleasedListener();
        configOtherSpritesListener();
    }

    private void configKeyPressedListener() {
        setKeyPressedListener(new KeyPressedListener() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                player.keyPressed(keyEvent);
                processOtherSprites(player, keyEvent);
            }
        });
    }

    private void configKeyReleasedListener() {
        setKeyReleasedListener(new KeyReleasedListener() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                player.keyReleased(keyEvent);
            }
        });
    }

    private void configOtherSpritesListener() {
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

    private void processOtherSprites(BasePlayer player, KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (inGame) {
                if (!shot.isVisible()) {
                    Position playerPosition = player.getPosition();
                    shot = new Shot(playerPosition);
                }
            }
        }
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        monsters = new LinkedList<>();
        for (int i = 0; i < Commons.MONSTERS_PATH_IMAGES.length; i++) {
            monsters.add(createMonster(i));
        }
        return monsters;
    }

    private BadSprite createMonster(int position) {
        int x = getRandomNumberInRage(Commons.BOARD_WIDTH - 200, 0);
        int y = getRandomNumberInRage(Commons.BOARD_HEIGHT - 200, 0);
        MonsterSprite monster = new MonsterSprite(
                new Position(x,y),
                Commons.MONSTERS_PATH_IMAGES[position],
                Commons.DEAD_MONSTERS_PATH_IMAGES[position]
        );
        return monster;
    }


    @Override
    protected void createPlayers() {
        player = new Player();
        players.add(player);
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
        if (isDeathsEqualToNumberOfMonstersToDestroy()) {
            playerWin();
        }

        player.update();
        updateShot();
        updateMonsters();
        updateOtherSprites();
    }

    private boolean isDeathsEqualToNumberOfMonstersToDestroy() {
        return deaths == NUMBER_OF_MONSTERS_TO_DESTROY;
    }

    private void playerWin() {
        inGame = false;
        timer.stop();
        message = "Game won!";
    }

    private void updateShot() {
        if (isShotVisible()) {
            updateShotWhenIsVisible();
        } else {
            playerLastDirection = player.getPlayerLastDirection();
        }
    }

    private boolean isShotVisible() {
        return shot.isVisible();
    }

    private void updateShotWhenIsVisible() {
        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            boolean monsterHit = monsterSprite.monsterHit(shot.getPosition());

            if (monsterHit) {
                deaths++;
                shot.die();
            }
        }
        shot.actShot(playerLastDirection);
    }

    private void updateMonsters() {
        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            monsterSprite.moveMonster();
        }
    }

    protected void updateOtherSprites() {
        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            MonsterShot monsterShot = monsterSprite.getShot();
            if (monsterShotWasHitByShot(monsterSprite)) {
                shot.die();
            }
            monsterSprite.createShot();
            playerWasHit(monsterSprite, monsterShot);
            actMonsterShotIfNotDestroyed(monsterShot);
        }
    }

    private boolean monsterShotWasHitByShot(MonsterSprite monsterSprite) {
        return monsterSprite.shotWasHit(shot.getPosition());
    }

    private void playerWasHit(BadSprite monsterSprite, MonsterShot monsterShot) {
        if (player.isVisible()) {
            if (isPlayerHitByMonster(monsterSprite)) {
                player.setDying(true);
            }
            else if(isPlayerHitByShot(monsterShot)){
                playerWasHitByShot(monsterShot);
            }
        }
    }

    private boolean isPlayerHitByMonster(BadSprite monsterSprite) {
        return isPositionInsidePlayerPosition(monsterSprite.getPosition()) && !monsterSprite.isDying();
    }

    private void playerWasHitByShot(MonsterShot monsterShot) {
            player.setDying(true);
            monsterShot.setDestroyed(true);
    }

    private boolean isPlayerHitByShot(BadSprite monsterShot) {
        return isPositionInsidePlayerPosition(monsterShot.getPosition()) && !monsterShot.isDestroyed();
    }

    private boolean isPositionInsidePlayerPosition(Position position) {
        int x = position.getxPosition();
        int y = position.getyPosition();
        int playerX = player.getX();
        int playerY = player.getY();
        return x >= (playerX) && x <= (playerX + Commons.PLAYER_WIDTH)
                && y >= (playerY)
                && y <= (playerY + Commons.PLAYER_HEIGHT);
    }

    private void actMonsterShotIfNotDestroyed(MonsterShot monsterShot) {
        if (!monsterShot.isDestroyed()) {
            monsterShot.shotMovement();
        }
    }

}
