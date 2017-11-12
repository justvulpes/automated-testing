package weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Ragnar
 */
public class Weather {
    private final static String BASE = "http://api.openweathermap.org/data/2.5/";
    private final static String API_POINT_WEATHER = "weather?id=";
    private final static String API_POINT_FORECAST = "forecast?id=";
    private final static String CITY = "588409";  // id of Tallinn
    private final static String AND_APP_ID = "&APPID=";
    private final static String DEV_KEY = "f17ef112f87d82f2c607d0676b4245e8";
    private final static String AND_UNITS = "&units=metric";

    // todo: write result to file

    public static void main(String[] args) {
//        Weather weather = new Weather();
//        System.out.println(BASE + API_POINT_WEATHER + CITY + AND_APP_ID + DEV_KEY + AND_UNITS);
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
        System.out.println("Current time: " + new Date());
        String output;
        try {
            while ((output = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String dateString = jsonArray.getJSONObject(i).getString("dt_txt");
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
                    Date startDate;  // this is glorious! :)
                    startDate = df.parse(dateString);
                    String newDateString = df.format(startDate);
                    System.out.println(newDateString);
//                    System.out.println(dateString);
                }
            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the MaxMinList of next 3 days.
     */
    public List<Map<String, Integer>> getMaxMinListOfNext3Days() {
        // example
        Map<String, Integer> day1Data = Map.of("Max", 25, "Min", 15);
        Map<String, Integer> day2Data = Map.of("Max", 27, "Min", 12);
        Map<String, Integer> day3Data = Map.of("Max", 23, "Min", 8);
        return List.of(day1Data, day2Data, day3Data);
    }

    public int getCurrentTemperatureTallinn() {
        // example
        return 0;
    }

    public Map<String, Double> getCoords() {
        // example coords of Tallinn
        return Map.of("lat", 59.437, "lon", 24.7535);
    }
}
