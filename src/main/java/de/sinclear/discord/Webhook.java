package de.sinclear.discord;

import okhttp3.*;

import java.io.IOException;

public class Webhook {

    private static final OkHttpClient client = new OkHttpClient();

    private Webhook() {
        throw new IllegalStateException("Utility classes shouldn't be instantiated.");
    }

    public static void postDeal(String webhookUrl, String post) throws IOException {
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
        RequestBody body = RequestBody.create("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\nDCDP\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"content\"\r\n\r\n"+post+"\r\n-----011000010111000001101001--\r\n", mediaType);
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .addHeader("cookie", "__dcfduid=b4fd159053eb11eca78942010a0a0c1d; __sdcfduid=b4fd159053eb11eca78942010a0a0c1d6d4d3db95adff32c2b68f66b2505de99e9c8c6418d91db73bd1b153df67f5b15; __cfruid=26e16ab043860c206d2b389a40ad4c21f494f5fc-1638503237")
                .addHeader("Content-Type", "multipart/form-data; boundary=---011000010111000001101001")
                .build();
        client.newCall(request).execute();
    }

}
