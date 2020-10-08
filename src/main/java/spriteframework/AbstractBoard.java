package spriteframework;


import spriteframework.Player.BasePlayer;
import spriteframework.listeners.KeyPressedListener;
import spriteframework.listeners.KeyReleasedListener;
import spriteframework.listeners.OtherSpriteListener;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public abstract class AbstractBoard extends JPanel {


    protected Dimension dimension;
    protected LinkedList<BasePlayer> players = new LinkedList<>();
    protected LinkedList<BadSprite> badSprites;
    protected boolean inGame = true;
    protected Timer timer;
    protected GraphicsDrawner graphicsDrawner = new GraphicsDrawner();

    private Graphics2D graphics;
    private OtherSpriteListener otherSpriteListener;
    private GameBoardSpecification gameBoardSpecification;
    private TAdapter tAdapter = new TAdapter();
    private boolean showDeadSprite = false;

    public AbstractBoard(GameBoardSpecification gameBoardSpecification) {
        initBoard(gameBoardSpecification);
    }

    public AbstractBoard(GameBoardSpecification gameBoardSpecification, boolean showDeadSprite) {
        this.showDeadSprite = showDeadSprite;
        initBoard(gameBoardSpecification);
    }

    protected void setKeyPressedListener(KeyPressedListener keyPressedListener) {
        tAdapter.setKeyPressedListener(keyPressedListener);
    }

    protected void setKeyReleasedListener(KeyReleasedListener keyReleasedListener) {
        tAdapter.setKeyReleasedListener(keyReleasedListener);
    }

    public void setOtherSpriteListener(OtherSpriteListener otherSpriteListener) {
        this.otherSpriteListener = otherSpriteListener;
        this.otherSpriteListener.createOtherSprites();
    }

    private void initBoard(GameBoardSpecification gameBoardSpecification) {
        this.gameBoardSpecification = gameBoardSpecification;
        configBoard();
        createSprites();
    }

    private void configBoard() {
        addKeyListener(tAdapter);
        setFocusable(true);
        dimension = new Dimension(
                gameBoardSpecification.getBoardWidth(),
                gameBoardSpecification.getBoardHeight()
        );
        setBackground(gameBoardSpecification.getColor());
        timer = new Timer(gameBoardSpecification.getDelay(), new GameCycle());
        timer.start();
    }

    private void createSprites() {
        createPlayers();
        badSprites = createBadSprites();
    }

    protected abstract LinkedList<BadSprite> createBadSprites();

    protected abstract void createPlayers();

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        doDrawing(graphics);
    }

    private void doDrawing(Graphics graphics) {
        configGraphics(graphics);
        drawBoard();
        drawGame();
        Toolkit.getDefaultToolkit().sync();
    }

    private void configGraphics(Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
        graphicsDrawner.setGraphics(graphics);
    }

    protected abstract void drawBoard();

    private void drawGame() {
        if (inGame) {
            drawGameIsRunning();
        } else {
            drawGameIsOver();
        }
    }

    private void drawGameIsRunning() {
        drawBadSprites();
        drawPlayers();
        if (isOtherSpritesListenerNotNull()) {
            otherSpriteListener.drawOtherSprites();
        }
    }

    private void drawBadSprites() {
        for (BadSprite bad : badSprites) {
            drawBadSprite(bad);
            drawBadnessFromBadnessBoxSprite(bad);
        }
    }

    private void drawBadSprite(BadSprite bad) {
        drawSpriteIfIsVisible(bad);
        if (!showDeadSprite) {
            setBadSpriteDeadIfIsDying(bad);
        }
    }

    private void setBadSpriteDeadIfIsDying(BadSprite bad) {
        if (bad.isDying()) {
            bad.die();
        }
    }

    private void drawBadnessFromBadnessBoxSprite(BadSprite bad) {
        if (isBadnessBoxSprite(bad)) {
            badnessBoxSpriteTreatment((BadnessBoxSprite) bad);
        }
    }

    private boolean isBadnessBoxSprite(BadSprite bad) {
        return bad instanceof BadnessBoxSprite;
    }

    private void badnessBoxSpriteTreatment(BadnessBoxSprite badnessBoxSprite) {
        if (badnessBoxSpriteBadnessIsNotNull(badnessBoxSprite)) {
            for (BadSprite badness : badnessBoxSprite.getBadnesses()) {
                drawBadnessIfNotDestroyed(badness);
            }
        }
    }

    private boolean badnessBoxSpriteBadnessIsNotNull(BadnessBoxSprite badnessBoxSprite) {
        return badnessBoxSprite.getBadnesses() != null;
    }

    private void drawBadnessIfNotDestroyed(BadSprite badness) {
        if (badnessIsNotDestroyed(badness)) {
            drawSprite(badness);
        }
    }

    private boolean badnessIsNotDestroyed(BadSprite badness) {
        return !badness.isDestroyed();
    }

    private void drawPlayers() {
        for (BasePlayer player : players) {
            drawSpriteIfIsVisible(player);
            playerIsDyingTreatment(player);
        }
    }

    private void drawSpriteIfIsVisible(Sprite sprite) {
        if (sprite.isVisible()) {
            drawSprite(sprite);
        }
    }

    private void playerIsDyingTreatment(BasePlayer player) {
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    private boolean isOtherSpritesListenerNotNull() {
        return otherSpriteListener != null;
    }

    protected void drawSprite(Sprite sprite) {
        graphics.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    }

    private void drawGameIsOver() {
        if (timer.isRunning()) {
            timer.stop();
        }
        gameFinished();
    }

    protected abstract void gameFinished();

    private class GameCycle implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    protected abstract void update();

}
