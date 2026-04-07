import javax.swing.*;

/**
 * Main is the entry point for the game.
 * It creates the window (JFrame) and drops the GamePanel inside it.
 *
 * Teaching note: SwingUtilities.invokeLater ensures the window is built
 * on the correct thread. This is standard Swing best practice.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Java 2D Platformer");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);

            window.pack();                    // size window to fit the panel
            window.setLocationRelativeTo(null); // centre on screen
            window.setVisible(true);

            gamePanel.requestFocusInWindow(); // make sure keyboard input works
        });
    }
}
