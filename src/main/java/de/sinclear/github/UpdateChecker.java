package de.sinclear.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class UpdateChecker {

    public static final String RELEASES_API = "https://api.github.com/repos/sinclearclan/dcdpx/releases";

    public static boolean updateAvailable() throws IOException, XmlPullParserException {
        MavenXpp3Reader mavenReader = new MavenXpp3Reader();
        Model model = mavenReader.read(new FileReader("pom.xml"));
        double currentVersion = Double.parseDouble(model.getVersion());
        double latestReleaseVersion = getNewestReleaseVersion();
        return latestReleaseVersion > currentVersion;
    }

    public static double getNewestReleaseVersion() throws IOException {
        HashMap<?, ?> releaseInfo = getReleaseInfo(true);
        String version = (String) releaseInfo.get("name");
        return Double.parseDouble(version.substring(1));
    }

    public static HashMap getReleaseInfo(boolean latest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        String url = RELEASES_API;
        if (latest) {
            url += "/latest";
        } else {
            throw new IllegalArgumentException("This case has not been implemented yet. For the moment, stick with" +
                    "'true' to get the latest release. More functionality to come in a future update.");
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        assert responseBody != null;
        return mapper.readValue(responseBody.string(), HashMap.class);
    }

}
