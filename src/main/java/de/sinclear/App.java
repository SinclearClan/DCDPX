package de.sinclear;

import de.sinclear.gui.PostWindow;
import javax.swing.UIManager;

public class App {

    public static void main(final String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new PostWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
