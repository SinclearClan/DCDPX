package de.sinclear.config;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The Configuration object stores important values of DCDPX.
 * It stores a copy of itself (the object) with all these values in the user's home directory.
 * At program startup this copy of the Configuration object can be restored, so the user can continue working faster.
 *
 * @author marcrentschler
 * @version 1.0
 */
public class Configuration implements Serializable {

    private final String configFilePath = getUserHomeDirectoryPath() + "/.dcdpxconfig.json";
    private String webhookUrl = "";

    public Configuration() throws IOException, ClassNotFoundException {
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
        BufferedReader reader = Files.newBufferedReader(Paths.get(configFilePath));
        try {
            HashMap<String, String> config = new Gson().fromJson(reader, HashMap.class);
            if (config.get("webhookUrl").isEmpty()) {
                this.setWebhookUrl("https://");
            } else {
                this.setWebhookUrl(config.get("webhookUrl"));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
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
        config.put("webhookUrl", this.webhookUrl);
        FileWriter writer = new FileWriter(configFilePath);
        try {
            new Gson().toJson(config, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.close();
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
     *
     * @throws IOException Config file is inaccessible, could not be created.
     */
    public void createConfigFile() throws IOException {
        File configFile = new File(configFilePath);
        try {
            configFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Sets the Webhook URL into the current Configuration and also writes the changed Configuration into
     * the config file.
     *
     * @param url Webhook URL to be stored.
     * @throws IOException Could not store the config file.
     */
    public void setWebhookUrl(String url) throws IOException {
        webhookUrl = url;
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
