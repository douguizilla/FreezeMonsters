package spriteframework;


import spriteframework.Player.BasePlayer;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public abstract class AbstractBoard extends JPanel {

    private GameBoardSpecification gameBoardSpecification;

    protected Dimension dimension;

    protected LinkedList<BasePlayer> players;

    protected LinkedList<BadSprite> badSprites;

    protected boolean inGame = true;
    protected String message = "Game Over";

    protected Timer timer;
    private Graphics2D graphics;

    protected abstract LinkedList<BadSprite> createBadSprites();

    protected abstract void createOtherSprites();

    protected abstract void drawOtherSprites(Graphics graphics);

    protected abstract void update();

    protected abstract void processOtherSprites(BasePlayer player, KeyEvent e);


    public AbstractBoard(GameBoardSpecification gameBoardSpecification) {
        this.gameBoardSpecification = gameBoardSpecification;
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(
                gameBoardSpecification.getBoardWidth(),
                gameBoardSpecification.getBoardHeight()
        );
        setBackground(gameBoardSpecification.getColor());
        timer = new Timer(gameBoardSpecification.getDelay(), new GameCycle());
        timer.start();
        createPlayers();
        badSprites = createBadSprites();
        createOtherSprites();
    }

    protected abstract void createPlayers();

    private void drawBadSprites() {
        for (BadSprite bad : badSprites) {
            drawBadSprite(bad);
            drawBadnessFromBadnessBoxSprite(bad);
        }
    }

    private void drawBadSprite(BadSprite bad){
        if (bad.isVisible()) {
            drawSprite(bad);
        }
        if (bad.isDying()) {
            bad.die();
        }
    }

    private void drawBadnessFromBadnessBoxSprite(BadSprite bad){
        if(isBadnessBoxSprite(bad)){
            badnessBoxSpriteTreatment((BadnessBoxSprite) bad);
        }
    }

    private boolean isBadnessBoxSprite(BadSprite bad){
        return bad instanceof BadnessBoxSprite;
    }

    private void badnessBoxSpriteTreatment(BadnessBoxSprite badnessBoxSprite){
        if (badnessBoxSpriteBadnessIsNotNull(badnessBoxSprite)) {
            for (BadSprite badness : badnessBoxSprite.getBadnesses()) {
                drawBadnessIfNotDestroyed(badness, graphics);
            }
        }
    }

    private boolean badnessBoxSpriteBadnessIsNotNull(BadnessBoxSprite badnessBoxSprite){
        return badnessBoxSprite.getBadnesses() != null;
    }

    private void drawBadnessIfNotDestroyed(BadSprite badness, Graphics graphics){
        if (badnessIsNotDestroyed(badness)) {
            drawSprite(badness);
        }
    }

    private boolean badnessIsNotDestroyed(BadSprite badness){
        return !badness.isDestroyed();
    }

    private void drawPlayers() {
        for (BasePlayer player : players) {
            if (player.isVisible()) {
                drawSprite(player);
            }
            if (player.isDying()) {
                player.die();
                inGame = false;
            }
        }
    }

    private void drawSprite(Sprite sprite){
        graphics.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g1) { // Template Method
        graphics = (Graphics2D) g1;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
        graphics.setColor(Color.green);
        if (inGame) {
            graphics.drawLine(
                    0,
                    gameBoardSpecification.getGround(),
                    gameBoardSpecification.getBoardWidth(),
                    gameBoardSpecification.getGround()
            );
            drawBadSprites();
            drawPlayers();
            drawOtherSprites(graphics);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(graphics);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    protected abstract void gameOver(Graphics g);


    private void doGameCycle() {
        update();
        repaint();
    }


    private class GameCycle implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            for (BasePlayer player : players)
                player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            for (BasePlayer player : players) {
                player.keyPressed(e);
                processOtherSprites(player, e);
            }
        }
    }
}
