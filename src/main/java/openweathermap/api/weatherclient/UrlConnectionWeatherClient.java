package openweathermap.api.weatherclient;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * @author Ragnar
 */
public class UrlConnectionWeatherClient implements WeatherClient {
    private String appId;

    public UrlConnectionWeatherClient(String appId) {
        this.appId = appId;
    }

    public URLWrapper getUrl(Query query) {
        String queryString = query.getQueryString();
        try {
            return new URLWrapper(queryString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpURLConnection makeRequest(URLWrapper url) throws WeatherReportNotFoundException {
        try {
            HttpURLConnection connection = url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 404) {
                    throw new WeatherReportNotFoundException("No report found for this city!");
                }
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDataFromResponse(HttpURLConnection connection) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getWeatherData(Query query) throws WeatherReportNotFoundException {
        query.setAppId(appId);
        return getDataFromResponse(makeRequest(getUrl(query)));
    }
}
