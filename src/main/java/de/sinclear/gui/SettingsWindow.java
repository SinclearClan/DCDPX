package de.sinclear.gui;

import de.sinclear.App;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SettingsWindow extends JFrame {

    private final Dimension defSize = new Dimension(450, 325);
    private JPanel rootPanel;
    private JLabel saveSettingsLabel;
    private JLabel saveWebhookUrlLabel;
    private ButtonGroup saveWebhookUrlButtonGroup = new ButtonGroup();
    private JRadioButton saveWebhookUrlYesRadio;
    private JRadioButton saveWebhookUrlNoRadio;
    private JLabel customEmojiLabel;

    public SettingsWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("DCDPX Einstellungen");
        setContentPane(rootPanel);
        pack();
        setSize(defSize);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    saveSettings();
                    setVisible(false);
                    setAlwaysOnTop(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        saveWebhookUrlButtonGroup.add(saveWebhookUrlYesRadio);
        saveWebhookUrlButtonGroup.add(saveWebhookUrlNoRadio);
    }

    public void display(Boolean visible) throws IOException {
        App.getConfig().readConfigFile();
        if (App.getConfig().getSaveWebhookUrl()) {
            saveWebhookUrlYesRadio.setSelected(true);
        } else {
            saveWebhookUrlNoRadio.setSelected(true);
        }
        setAlwaysOnTop(true);
        setVisible(visible);
    }

    public void saveSettings() throws IOException {
        App.getConfig().setSaveWebhookUrl(saveWebhookUrlYesRadio.isSelected());
        App.getConfig().writeConfigFile();
    }

}
