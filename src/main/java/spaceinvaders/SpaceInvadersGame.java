package spaceinvaders;

import java.awt.*;

import spriteframework.AbstractBoard;
import spriteframework.GameBoardSpecification;
import spriteframework.MainFrame;

public class SpaceInvadersGame extends MainFrame {


	public SpaceInvadersGame () {
		super("Space Invaders", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
	}
	
	protected  AbstractBoard createBoard() {
		GameBoardSpecification gameBoardSpecification = new GameBoardSpecification(
				Commons.GROUND,
				Commons.BOARD_WIDTH,
				Commons.BOARD_HEIGHT,
				Color.BLACK,
				Commons.DELAY
		);
		return new SpaceInvadersBoard(gameBoardSpecification);
	}


	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			new SpaceInvadersGame();
		});
	}

}
