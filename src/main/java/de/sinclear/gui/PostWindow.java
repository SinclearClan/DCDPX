package de.sinclear.gui;

import de.sinclear.App;
import de.sinclear.discord.Webhook;
import de.sinclear.github.UpdateChecker;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;

public class PostWindow extends JFrame {

    private final Dimension minSize = new Dimension(600, 400);
    private final Dimension defSize = new Dimension(800, 500);
    private JPanel rootPanel;
    private JToolBar toolBar;
    private JLabel toolbarInfoLabel;
    private JPanel dealPanel;
    private JTextField webhookUrlField;
    private JLabel webhookUrlLabel;
    private JLabel pingLabel;
    private ButtonGroup pingRadioButtonGroup = new ButtonGroup();
    private JRadioButton pingNobodyRadio;
    private JRadioButton pingEveryoneRadio;
    private JRadioButton pingHereRadio;
    private JLabel introductionTextLabel;
    private JTextArea introductionTextField;
    private JScrollPane introductionTextPane;
    private JLabel gameTitleLabel;
    private JTextField gameTitleField;
    private JLabel gamePlatformLabel;
    private JTextField gamePlatformField;
    private JLabel gameDiscountLabel;
    private JTextField gameDiscountField;
    private JLabel gameDiscountUnit;
    private JLabel gamePriceLabel;
    private JTextField gamePriceField;
    private JLabel gamePriceUnit;
    private JLabel gameDaysLeftLabel;
    private JTextField gameDaysLeftField;
    private JLabel gameDaysLeftUnit;
    private JLabel gameRecommendedByLabel;
    private JTextField gameRecommendedByField;
    private JLabel gameLink1Label;
    private JTextField gameLink1Field;
    private JLabel gameLink2Label;
    private JTextField gameLink2Field;
    private JButton exitBtn;
    private JButton sendBtn;
    private JButton settingsBtn;
    private JButton webhookInfoBtn;

    public PostWindow() throws ParseException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SINCLEAR – DCDP");
        setContentPane(rootPanel);
        setMinimumSize(minSize);
        setPreferredSize(defSize);
        pack();
        setSize(defSize);
        setLocationRelativeTo(null);
        setVisible(true);

        pingRadioButtonGroup.add(pingNobodyRadio);
        pingRadioButtonGroup.add(pingEveryoneRadio);
        pingRadioButtonGroup.add(pingHereRadio);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    App.getConfig().setConfigFromGui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (UpdateChecker.updateAvailable()) {
            new updateAvailableDialog();
        }

        if (!App.getConfig().getWebhookUrl().isEmpty()) {
            webhookUrlField.setText(App.getConfig().getWebhookUrl());
        }

        settingsBtn.addActionListener(ae -> {
            try {
                App.getSettingsWindow().display(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exitBtn.addActionListener(ae -> {
            App.getSettingsWindow().dispose();
            dispose();
        });

        sendBtn.addActionListener(ae -> {
            String post = "";
            if (pingEveryoneRadio.isSelected()) {
                post += "@everyone ";
            } else if (pingHereRadio.isSelected()) {
                post += "@here ";
            }
            if (!introductionTextField.getText().isEmpty()) {
                post += introductionTextField.getText() + "\n";
            }
            if (!gameTitleField.getText().isEmpty()) {
                post += ":label: **" + gameTitleField.getText() + "**\n";
            }
            if (!gamePlatformField.getText().isEmpty()) {
                post += ":globe_with_meridians: " + gamePlatformField.getText() + "\n";
            }
            if (!gameDiscountField.getText().isEmpty()) {
                post += ":anger: -" + gameDiscountField.getText() + "%\n";
            }
            if (!gamePriceField.getText().isEmpty()) {
                post += ":euro: " + gamePriceField.getText() + "€\n";
            }
            if (!gameDaysLeftField.getText().isEmpty()) {
                post += ":alarm_clock: Noch " + gameDaysLeftField.getText() + " Tage\n";
            }
            if (!gameRecommendedByField.getText().isEmpty()) {
                post += ":busts_in_silhouette: Empfohlen von " + gameRecommendedByField.getText() + "\n";
            }
            if (!gameLink1Field.getText().isEmpty()) {
                post += ":link: " + gameLink1Field.getText() + "\n";
            }
            if (!gameLink2Field.getText().isEmpty()) {
                post += ":link: " + gameLink2Field.getText() + "\n";
            }
            if (!webhookUrlField.getText().isEmpty()) {
                try {
                    Webhook.post(webhookUrlField.getText(), post);
                    App.getConfig().setConfigFromGui();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getWebhookUrl() {
        return webhookUrlField.getText();
    }

    public String getIntroductionText() {
        return introductionTextField.getText();
    }
}
