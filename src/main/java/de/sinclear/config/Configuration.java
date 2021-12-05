package de.sinclear.config;

import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

/**
 * The Configuration object stores important values of DCDPX.
 * It stores a copy of itself (the object) with all these values in the user's home directory.
 * At program startup this copy of the Configuration object can be restored, so the user can continue working faster.
 *
 * @author marcrentschler
 * @version 1.0
 */
public class Configuration implements Serializable {

    private final String configFilePath = getUserHomeDirectoryPath() + "/.dcdpx.config";
    private String webhookUrl = "";

    public Configuration() throws IOException, ClassNotFoundException {
        //Check if a config file already exists.
        if (configFileExists()) {
            //If a config file already exists it will be read.
            readConfigFile();
        } else {
            //If no config file exists, a new one will be created.
            assert createConfigFile();
        }
    }

    /**
     * Reads the config file (if existent) and stores the read values in the currently instantiated object.
     * It could happen that the version of the current Configuration object is incompatible with the stored version
     * of the Object. In this case the stored version will be deleted and replaced by an empty one. This action is
     * necessary to ensure further functionality. It might happen everytime something about this class has changed
     * significantly after a new DCDPX version release.
     *
     * @throws IOException ConfigFile not existent or can't be accessed.
     */
    public void readConfigFile() throws IOException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(configFilePath));
        try {
            Configuration configFile = (Configuration) reader.readObject();
            if (configFile.getWebhookUrl().isEmpty()) {
                this.setWebhookUrl("https://");
            } else {
                this.setWebhookUrl(configFile.getWebhookUrl());
            }
        } catch (Exception e) {
            File configFile = new File(getConfigFilePath());
            assert configFile.delete();
            createConfigFile();
        }
    }

    /**
     * Stores an exact copy of the currently instantiated Configuration object into the file system.
     *
     * @throws IOException ConfigFile not existent or can't be accessed.
     */
    public void writeConfigFile() throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(configFilePath));
        writer.writeObject(this);
        writer.close();
    }

    /**
     * Checks if a ConfigFile exists at the default location.
     *
     * @return True if ConfigFile already exists.
     * @throws IOException ConfigFile is inaccessible or is a folder.
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
     * Creates a new ConfigFile at the default location.
     *
     * @return True if file could be created. Should be False if there's already a file with the same name.
     * @throws IOException ConfigFile is inaccessible, could not be created.
     */
    public boolean createConfigFile() throws IOException {
        File configFile = new File(configFilePath);
        return configFile.createNewFile();
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
     * the ConfigFile.
     *
     * @param url Webhook URL to be stored.
     * @throws IOException Could not store the ConfigFile.
     */
    public void setWebhookUrl(String url) throws IOException {
        webhookUrl = url;
        writeConfigFile();
    }

    /**
     * Gets the path to the ConfigFile in the file system.
     *
     * @return Path to the ConfigFile.
     */
    public String getConfigFilePath() {
        return configFilePath;
    }

}
