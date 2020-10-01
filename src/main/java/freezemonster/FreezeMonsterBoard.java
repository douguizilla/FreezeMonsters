package freezemonster;

import freezemonster.sprite.MonsterShot;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Player;
import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.awt.*;
import java.util.LinkedList;

public class FreezeMonsterBoard extends AbstractBoard {
    private LinkedList<BadSprite> monsters;
    private MonsterShot shot;
    private GameBoardSpecification gameBoardSpecification;
    private int direction = -1;
    private int deaths = 0;
    protected String message = "Game Over";

    public FreezeMonsterBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification);
        this.gameBoardSpecification = gameBoardSpecification;

    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        LinkedList<BadSprite> monsters = new LinkedList<>();
        int x = 0;//tem que ser posições aleatórias????
        int y = 0; //tem que ser posições aleatórias????
        for(int i = 0; i < Commons.MONSTERS_PATH_IMAGES.length; i++){
            MonsterSprite monster = new MonsterSprite(x, y, Commons.MONSTERS_PATH_IMAGES[i]);
            monsters.add(monster);
        }
        return monsters;
    }

    @Override
    protected void createPlayers() {
        players = new LinkedList<>();
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
                Color.green.brighter()
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

    }
}
