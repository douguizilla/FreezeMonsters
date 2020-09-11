package spriteframework;

import javax.swing.*;

public abstract class MainFrame extends JFrame {

    // hotspot
    protected abstract AbstractBoard createBoard();

    public MainFrame(String title) {

        add(createBoard());

        setTitle(title);
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
