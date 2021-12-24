package de.sinclear;

import de.sinclear.config.ConfigurationHandler;
import de.sinclear.gui.PostWindow;
import de.sinclear.gui.SettingsWindow;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.IOException;
import java.text.ParseException;

public class App {

    private static final double VERSION = 1.3;
    private static ConfigurationHandler configurationHandler;
    private static PostWindow postWindow;
    private static SettingsWindow settingsWindow;

    public static void main(final String[] args) {
        try {
            configurationHandler = new ConfigurationHandler();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            postWindow = new PostWindow();
            settingsWindow = new SettingsWindow();
        } catch (UnsupportedLookAndFeelException | ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static double getVersion() {
        return VERSION;
    }

    public static ConfigurationHandler getConfig() {
        return configurationHandler;
    }

    public static PostWindow getPostWindow() {
        return postWindow;
    }

    public static SettingsWindow getSettingsWindow() {
        return settingsWindow;
    }
}
