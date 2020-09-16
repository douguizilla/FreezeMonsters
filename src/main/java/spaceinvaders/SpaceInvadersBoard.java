package spaceinvaders;


import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.Player.BasePlayer;
import spriteframework.sprite.BadSprite;
import spaceinvaders.sprite.Player;

import spaceinvaders.sprite.*;

public class SpaceInvadersBoard extends AbstractBoard {
    private LinkedList<BadSprite> aliens;
    private Shot shot;
    private GameBoardSpecification gameBoardSpecification;
    private int direction = -1;
    private int deaths = 0;

    private String explImg = "images/explosion.png";

    public SpaceInvadersBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification);
        this.gameBoardSpecification = gameBoardSpecification;
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        aliens = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                BomberSprite alien = new BomberSprite(Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
        return aliens;
    }

    @Override
    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    //TODO remove with shot on BasePlayer
    @Override
    protected void drawOtherSprites(Graphics g) {
        drawShot(g);
    }
    //TODO remove with shot on BasePlayer
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
    protected void createPlayers() {
        players = new LinkedList<>();
        players.add(createPlayer());
    }

    protected BasePlayer createPlayer() {
        return new Player();
    }

    //TODO remove the graphics
    @Override
    protected void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(
                0,
                0,
                gameBoardSpecification.getBoardWidth(),
                gameBoardSpecification.getBoardHeight()
        );

        g.setColor(new Color(0, 32, 48));
        g.fillRect(
                50,
                gameBoardSpecification.getBoardWidth() / 2 - 30,
                gameBoardSpecification.getBoardWidth() - 100,
                50
        );
        g.setColor(Color.white);
        g.drawRect(
                50,
                gameBoardSpecification.getBoardWidth() / 2 - 30,
                gameBoardSpecification.getBoardWidth() - 100,
                50
        );

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(
                message,
                (gameBoardSpecification.getBoardWidth() - fontMetrics.stringWidth(message)) / 2,
                gameBoardSpecification.getBoardWidth() / 2);
    }

    @Override
    protected void update() {

        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        for (BasePlayer player : players) {
            player.act();
        }

        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite alien : badSprites) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        for (BadSprite alien : badSprites) {

            int x = alien.getX();

            if (x >= gameBoardSpecification.getBoardWidth() - Commons.BORDER_RIGHT && direction != -1) {

                direction = -1;

                Iterator<BadSprite> i1 = badSprites.iterator();

                while (i1.hasNext()) {
                    BadSprite a2 = i1.next();
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator<BadSprite> i2 = badSprites.iterator();

                while (i2.hasNext()) {

                    BadSprite a = i2.next();
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > gameBoardSpecification.getGround() - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.moveX(direction);
            }
        }
        updateOtherSprites();
    }

    //TODO remove with shot on BasePlayer
    protected void updateOtherSprites() {
        Random generator = new Random();

        for (BadSprite alien : badSprites) {

            int shot = generator.nextInt(15);
            Bomb bomb = ((BomberSprite) alien).getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= gameBoardSpecification.getGround() - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }
}

