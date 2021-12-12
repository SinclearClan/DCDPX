package de.sinclear.github;

import de.sinclear.App;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class UpdateChecker {

    private UpdateChecker() {
        throw new IllegalStateException("Utility classes shouldn't be instantiated.");
    }

    public static final String RELEASES_API = "https://api.github.com/repos/sinclearclan/dcdpx/releases";

    public static boolean updateAvailable() {
        return getNewestReleaseVersion() > App.getVersion();
    }

    public static double getNewestReleaseVersion() {
        String url = RELEASES_API + "/latest";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        JSONObject responseBody = response.getBody().getObject();
        String version = responseBody.getString("name");
        return Double.parseDouble(version.substring(1));
    }

}
