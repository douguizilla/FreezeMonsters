package spriteframework;


import spriteframework.Player.BasePlayer;
import spriteframework.sprite.BadSprite;
import spaceinvaders.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public abstract class AbstractBoard extends JPanel {

    protected Dimension dimension;

    //define sprites
//    private List<Alien> aliens;
    protected LinkedList<BasePlayer> players;

    protected LinkedList<BadSprite> badSprites;

//    private Shot shot;
//    
    // define global control vars   
//    private int direction = -1;
//    private int deaths = 0;

    private int numberPlayers;  // to do - future use
    protected boolean inGame = true;
    //    private String explImg = "src/images/explosion.png";
    protected String message = "Game Over";

    protected Timer timer;

    // Frozen Spots
    //  void initBoard()
    // 
    // HotSpots
    protected abstract void createBadSprites();

    protected abstract void createOtherSprites();

    protected abstract void drawOtherSprites(Graphics g);

    protected abstract void update();

    protected abstract void processOtherSprites(BasePlayer player, KeyEvent e);

    public AbstractBoard() {

        initBoard();
        createPlayers();
        numberPlayers = 1;
        badSprites = new LinkedList<BadSprite>();
        createBadSprites();
        createOtherSprites();
        //        shot = new Shot();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();

        createPlayers();
        numberPlayers = 1;
        badSprites = new LinkedList<BadSprite>();
        createBadSprites();
        createOtherSprites();
        //        shot = new Shot();
    }


    protected void createPlayers() {
        players = new LinkedList<BasePlayer>();
        players.add(createPlayer());
    }

    protected Player createPlayer() {
        return new Player();
    }

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

            g.drawLine(0, Commons.GROUND,
                    Commons.BOARD_WIDTH, Commons.GROUND);

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

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
    }


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
}
