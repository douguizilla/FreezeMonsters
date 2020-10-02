package freezemonster;

import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.MainFrame;

import java.awt.*;

public class FreezeMonsterGame extends MainFrame {
    public FreezeMonsterGame () {
        super("Freeze Monster", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    }

    protected  AbstractBoard createBoard() {
        GameBoardSpecification gameBoardSpecification = new GameBoardSpecification(
                Commons.GROUND,
                Commons.BOARD_WIDTH,
                spaceinvaders.Commons.BOARD_HEIGHT,
                new Color(3, 190, 115),
                Commons.DELAY
        );
        return new FreezeMonsterBoard(gameBoardSpecification);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            new FreezeMonsterGame();
        });
    }
}
