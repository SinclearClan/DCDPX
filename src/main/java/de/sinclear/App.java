package de.sinclear;

import de.sinclear.config.Configuration;
import de.sinclear.gui.PostWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.IOException;
import java.text.ParseException;

public class App {

    private static Configuration configuration;
    private static PostWindow postWindow;

    public static void main(final String[] args) {
        try {
            configuration = new Configuration();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            postWindow = new PostWindow();
        } catch (UnsupportedLookAndFeelException | ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        return configuration;
    }

    public static PostWindow getPostWindow() {
        return postWindow;
    }
}
