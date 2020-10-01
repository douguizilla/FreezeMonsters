package freezemonster;

import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.sprite.BadSprite;

import java.util.LinkedList;

public class FreezeMonsterBoard extends AbstractBoard {

    public FreezeMonsterBoard(GameBoardSpecification gameBoardSpecification) {
        super(gameBoardSpecification);
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        return null;
    }

    @Override
    protected void createPlayers() {

    }

    @Override
    protected void drawBoard() {

    }

    @Override
    protected void gameOver() {

    }

    @Override
    protected void update() {

    }
}
