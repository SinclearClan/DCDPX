package de.sinclear.config;

import com.google.gson.Gson;
import de.sinclear.App;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The ConfigurationHandler object stores important values of DCDPX.
 * It stores a copy of itself (the object) with all these values in the user's home directory.
 * At program startup this copy of the ConfigurationHandler object can be restored, so the user can continue working faster.
 *
 * @author marcrentschler
 * @version 1.0
 */
public class ConfigurationHandler {

    private final String configFilePath = getUserHomeDirectoryPath() + "/.dcdpxconfig.json";
    private String webhookUrl = "";
    private boolean saveWebhookUrl = true;
    private String introductionText = "";

    public ConfigurationHandler() throws IOException, ClassNotFoundException {
        //Check if a config file already exists.
        if (configFileExists()) {
            //If a config file already exists it will be read.
            readConfigFile();
        } else {
            //If no config file exists, a new one will be created.
            createConfigFile();
        }
    }

    /**
     * Reads the config file (if existent) and stores the read values in the currently instantiated object.
     *
     * @throws IOException Config file not existent or can't be accessed.
     */
    public void readConfigFile() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(configFilePath))) {
            HashMap<String, String> config = new Gson().fromJson(reader, HashMap.class);
            if (config.get("webhookUrl").isEmpty()) {
                webhookUrl = "";
            } else {
                webhookUrl = config.get("webhookUrl");
            }
            if (config.get("saveWebhookUrl").isEmpty()) {
                saveWebhookUrl = true;
            } else {
                saveWebhookUrl = Boolean.parseBoolean(config.get("saveWebhookUrl"));
            }
            if (config.get("introductionText").isEmpty()) {
                introductionText = "";
            } else {
                introductionText = config.get("introductionText");
            }
            writeConfigFile();
        } catch (Exception e) {
            e.printStackTrace();
            File configFile = new File(getConfigFilePath());
            try {
                configFile.delete();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            createConfigFile();
        }
    }

    /**
     * Stores current configuration to the config file.
     *
     * @throws IOException Config file not existent or can't be accessed.
     */
    public void writeConfigFile() throws IOException {
        HashMap<String, String> config = new HashMap<>();
        config.put("webhookUrl", webhookUrl);
        config.put("saveWebhookUrl", String.valueOf(saveWebhookUrl));
        config.put("introductionText", introductionText);
        try (FileWriter writer = new FileWriter(configFilePath)) {
            new Gson().toJson(config, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a Config file exists at the default location.
     *
     * @return True if Config file already exists.
     * @throws IOException Config file is inaccessible or is a folder.
     */
    public boolean configFileExists() throws IOException {
        File configFile = new File(configFilePath);
        if (configFile.isDirectory()) {
            throw new IOException("File is a directory");
        } else {
            return configFile.exists();
        }
    }

    /**
     * Creates a new config file at the default location.
     */
    public void createConfigFile() throws IOException {
        File configFile = new File(configFilePath);
        configFile.createNewFile();
        writeConfigFile();
    }

    /**
     * Gets the OS-specific path to the user's home directory.
     *
     * @return The path to the user's home directory.
     */
    public String getUserHomeDirectoryPath() {
        return System.getProperty("user.home");
    }

    /**
     * Gets the currently stored Webhook URL.
     *
     * @return Webhook URL.
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    /**
     * Gets the setting if the Webhook URL should be saved.
     *
     * @return True if the Webhook URL should be saved.
     */
    public boolean getSaveWebhookUrl() {
        return saveWebhookUrl;
    }

    /**
     * Sets if the Webhook URL should be saved.
     *
     * @param save If true, the Webhook URL will be saved to the config file.
     */
    public void setSaveWebhookUrl(boolean save) {
        saveWebhookUrl = save;
    }

    /**
     * Gets the currently stored introduction text.
     *
     * @return Introduction text.
     */
    public String getIntroductionText() {
        return introductionText;
    }

    /**
     * Sets the currently stored introduction text.
     * @param text New introduction text.
     */
    public void setIntroductionText(String text) {
        introductionText = text;
    }

    /**
     * Sets the values into the current Configuration and also writes the changed Configuration into
     * the config file.
     */
    public void setConfigFromGui() throws IOException {
        webhookUrl = App.getPostWindow().getWebhookUrl();
        introductionText = App.getPostWindow().getIntroductionText();
        writeConfigFile();
    }

    /**
     * Gets the path to the config file in the file system.
     *
     * @return Path to the config file.
     */
    public String getConfigFilePath() {
        return configFilePath;
    }

}
