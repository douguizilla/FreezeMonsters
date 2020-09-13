package spriteframework;


import spriteframework.Player.BasePlayer;
import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public abstract class AbstractBoard extends JPanel {

    protected Dimension dimension;

    protected LinkedList<BasePlayer> players;

    protected LinkedList<BadSprite> badSprites;

    protected boolean inGame = true;
    protected String message = "Game Over";

    protected Timer timer;



    protected abstract void createBadSprites();

    protected abstract void createOtherSprites();

    protected abstract void drawOtherSprites(Graphics g);

    protected abstract void update();

    protected abstract void processOtherSprites(BasePlayer player, KeyEvent e);

    private int GROUND;
    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;
    private int DELAY;
    private Color COLOR;

    public AbstractBoard(int GROUND, int BOARD_WIDTH, int BOARD_HEIGHT, Color COLOR, int DELAY) {
        this.GROUND = GROUND;
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        this.DELAY = DELAY;
        this.COLOR = COLOR;
        initBoard();
        createPlayers();
        badSprites = new LinkedList<BadSprite>();
        createBadSprites();
        createOtherSprites();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(getBOARD_WIDTH(), getBOARD_HEIGHT());
        setBackground(Color.black);

        timer = new Timer(getDELAY(), new GameCycle());
        timer.start();

        createPlayers();
        badSprites = new LinkedList<BadSprite>();
        createBadSprites();
        createOtherSprites();
    }


    protected void createPlayers() {
        players = new LinkedList<BasePlayer>();
        players.add(createPlayer());
    }

    protected abstract BasePlayer createPlayer();

    public BasePlayer getPlayer(int i) {
        if (i >= 0 && i < players.size())
            return players.get(i);
        return null;
    }

    private void drawBadSprites(Graphics g) {

        for (BadSprite bad : badSprites) {

            if (bad.isVisible()) {

                g.drawImage(bad.getImage(), bad.getX(), bad.getY(), this);
            }

            if (bad.isDying()) {

                bad.die();
            }
            if (bad.getBadnesses() != null) {
                for (BadSprite badness : bad.getBadnesses()) {
                    if (!badness.isDestroyed()) {
                        g.drawImage(badness.getImage(), badness.getX(), badness.getY(), this);
                    }
                }
            }
        }
    }

    private void drawPlayers(Graphics g) {
        for (BasePlayer player : players) {
            if (player.isVisible()) {
                g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            }

            if (player.isDying()) {

                player.die();
                inGame = false;
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g1) { // Template Method
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.black);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, getGROUND(), getBOARD_WIDTH(), getGROUND());

            drawBadSprites(g);
            drawPlayers(g);
            drawOtherSprites(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
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

                processOtherSprites(player, e); // hotspot
            }
        }
    }

    public int getGROUND() {
        return GROUND;
    }

    public void setGROUND(int GROUND) {
        this.GROUND = GROUND;
    }

    public int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public void setBOARD_WIDTH(int BOARD_WIDTH) {
        this.BOARD_WIDTH = BOARD_WIDTH;
    }

    public int getBOARD_HEIGHT() {
        return BOARD_HEIGHT;
    }

    public void setBOARD_HEIGHT(int BOARD_HEIGHT) {
        this.BOARD_HEIGHT = BOARD_HEIGHT;
    }

    public int getDELAY() {
        return DELAY;
    }

    public void setDELAY(int DELAY) {
        this.DELAY = DELAY;
    }

    public Color getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(Color COLOR) {
        this.COLOR = COLOR;
    }
}
