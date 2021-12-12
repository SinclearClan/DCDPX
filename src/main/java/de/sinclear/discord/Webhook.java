package de.sinclear.discord;

import kong.unirest.Unirest;

public class Webhook {

    private Webhook() {
        throw new IllegalStateException("Utility classes shouldn't be instantiated.");
    }

    /**
     * Posts a Discord message with the specified webhook URL.
     *
     * @param webhookUrl The URL of the Discord webhook.
     * @param message The message/deal to post.
     */
    public static void post(String webhookUrl, String message) {
        Unirest.post(webhookUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("username=DCDP&content=" + message)
                .asString();
    }

}
