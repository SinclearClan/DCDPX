package de.sinclear.gui;

import de.sinclear.App;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class updateAvailableDialog extends JFrame {

    private final Dimension defSize = new Dimension(400, 150);
    private JPanel rootPanel;
    private JButton downloadBtn;
    private JButton cancelBtn;
    private JLabel updateNoteiceLabel;

    public updateAvailableDialog() {
        setTitle("DCDPX Updater");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(defSize);
        setResizable(false);
        setContentPane(rootPanel);
        getRootPane().setDefaultButton(downloadBtn);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        downloadBtn.addActionListener(ae -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://github.com/SinclearClan/DCDPX/releases/latest"));
                App.getPostWindow().dispose();
                dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        cancelBtn.addActionListener(ae -> dispose());
    }
}
