package spaceinvaders;

import java.awt.*;

import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

public class SpaceInvadersGame extends MainFrame {


	public SpaceInvadersGame () {
		super("Space Invaders");
	}
	
	protected  AbstractBoard createBoard() {
		return new SpaceInvadersBoard(290, 358, 350, Color.BLACK, 17);
	}


	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			new SpaceInvadersGame();
		});
	}

}
