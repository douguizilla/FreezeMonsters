package spriteframework;

import java.awt.*;

public class GameBoardSpecification {
    private int ground;
    private int boardWidth;
    private int boardHeight;
    private Color color;
    private int delay;

    public GameBoardSpecification(int ground, int boardWidth, int boardHeight, Color color, int delay) {
        this.ground = ground;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.color = color;
        this.delay = delay;
    }

    public int getGround() {
        return ground;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public Color getColor() {
        return color;
    }

    public int getDelay() {
        return delay;
    }
}
