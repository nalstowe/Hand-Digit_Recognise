package UI;

import javax.swing.*;
import java.awt.*;


public class ProBar {

    private final JFrame mainFrame;
    private JProgressBar progressBar;
    private boolean unDecoreate = false;

    public ProBar(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
    }

    public ProBar(JFrame mainFrame, boolean unDecoreate) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
        this.unDecoreate = unDecoreate;
    }

    public void showProgressBar(String msg) {
        SwingUtilities.invokeLater(() -> {
            if (unDecoreate) {
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setUndecorated(true);
            }
            progressBar = createProgressBar(mainFrame);
            progressBar.setString(msg);
            progressBar.setStringPainted(true);
            progressBar.setIndeterminate(true);
            progressBar.setVisible(true);
            mainFrame.add(progressBar, BorderLayout.NORTH);
            if (unDecoreate) {
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
            mainFrame.repaint();
        });
    }


    private JProgressBar createProgressBar(JFrame mainFrame) {
        JProgressBar jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        jProgressBar.setVisible(false);
        mainFrame.add(jProgressBar, BorderLayout.NORTH);
        return jProgressBar;
    }

    public void setVisible(boolean visible) {
        progressBar.setVisible(visible);
    }
}

