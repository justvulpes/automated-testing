package weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author Ragnar
 */
public class Weather {
    private final static String BASE = "http://api.openweathermap.org/data/2.5/";
    private final static String API_POINT_WEATHER = "weather?id=";
    private final static String API_POINT_FORECAST = "forecast?id=";
    private final static String CITY = "588409";  // Tallinn
    private final static String AND_APP_ID = "&APPID=";
    private final static String DEV_KEY = "f17ef112f87d82f2c607d0676b4245e8";
    private final static String AND_UNITS = "&units=metric";


    public static void main(String[] args) {
        Weather weather = new Weather();
//        weather.parseCurrentTemperatureInTallinn(weather.getData(BASE + API_POINT_WEATHER + CITY + AND_APP_ID + DEV_KEY + AND_UNITS));
//        weather.parseForecast(weather.getData(BASE + API_POINT_FORECAST + CITY + AND_APP_ID + DEV_KEY + AND_UNITS));

    }

    private BufferedReader getData(String apiPoint) {
        try {
            URL url = new URL(apiPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            return new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseCurrentTemperatureInTallinn(BufferedReader br) {
        String output;
        try {
            while ((output = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(output);
                int temp = jsonObject.getJSONObject("main").getInt("temp");
                System.out.println("Current temperature in Tallinn: " + temp + "\u2103");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseForecast(BufferedReader br) {
        String output;
        try {
            while ((output = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(output);
                System.out.println(jsonObject.getJSONArray("list"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentTemperatureTallinn() {
        return -999;
    }
}
