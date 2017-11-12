package weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
TODO: Vaheülesanne #1
Mõtle välja kõikvõimalikud testid, mis on vajalikud projekti lahendamiseks.
Implementeeri testid (programmikoodi ei ole) nii et testid ei läbi (on punases)
 */

/*
TODO: Vaheülesanne #2
Registreeri end https://openweathermap.org/api kasutajaks
Kasuta APIsid, et saada praegune temperatuur ja 3 päeva ennustuse
temperatuur (kasuta linnana Tallinn)
Kirjuta realiseeriv programmikood, et implementeeritud testid läbiks. Tee teste
juurde, kuniks oled tulemusega rahul. Refaktoreeri
 */

/*
TODO: Vaheülesanne #3
Muuda linn parametriseeritavaks
Võimalus sisestada linna nimi ka konsoolist.
Kasuta faili nimega input.txt kus on kirjas, mis linna kohta infot küsida
Väljund kirjuta faili output.txt
Täienda teste, refaktoreeri
 */


/*
TODO: Vaheülesanne #4
inpux.txt failis linna nimed,
väljund (hetke ja ennustus) kirjutada faili {linnanimi}.txt
testidega mockida välise sõltuvuse (APId) - Repeatable ehk testid peavad jooksma igas keskkonnas
testidega mockida failist lugemise ja kirjutamise
 */


/**
 * @author Ragnar
 */
public class Weather {
    private final static String BASE = "http://api.openweathermap.org/data/2.5/";
    private final static String API_POINT_WEATHER = "weather";
    private final static String QUERY = "?q=";
    private final static String ID = "?id=";
    private final static String API_POINT_FORECAST = "forecast";
    private final static String APPID = "&APPID=";
    private final static String DEV_KEY = "f17ef112f87d82f2c607d0676b4245e8";
    private final static String UNITS = "&units=metric";


    public static void main(String[] args) throws IOException {

//        readStdInput();

        String inFilename = "res/input.txt";
        String outFilename = "res/output.txt";
        List<String> data = Files.lines(Paths.get(inFilename)).collect(Collectors.toList());
        for (String city: data) {
            System.out.println(city);
        }
        Files.write(Paths.get(outFilename), data);
    }

    private static void readStdInput() {
        Scanner s = new Scanner(System.in);
        Weather weather = new Weather();
        while (s.hasNextLine()) {
            String city = s.nextLine();
            weather.parseCurrentTemperature(weather.getData(
                    BASE + API_POINT_WEATHER + QUERY + city + APPID + DEV_KEY + UNITS), city);
        }
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

    private void parseCurrentTemperature(BufferedReader br, String city) {
        String output;
        try {
            while ((output = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(output);
                int temp = jsonObject.getJSONObject("main").getInt("temp");
                System.out.println(String.format("Current temperature in %s: ", city) + temp + "\u2103");
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
