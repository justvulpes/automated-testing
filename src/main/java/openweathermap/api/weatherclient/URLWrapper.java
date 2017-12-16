package openweathermap.api.weatherclient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLWrapper {
    private URL url;

    public URLWrapper(String urlString) throws MalformedURLException {
        this.url = new URL(urlString);
    }

    public HttpURLConnection openConnection() throws IOException {
        return (HttpURLConnection) this.url.openConnection();
    }
}