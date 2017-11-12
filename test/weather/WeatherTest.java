package weather;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ragnar
 */
public class WeatherTest {

    /**Maximum and minimum possible temperatures.*/
    public static final int MAX_TEMP = 50;
    public static final int MIN_TEMP = -50;

    private final static String BASE = "http://api.openweathermap.org/data/2.5/";
    private final static String API_POINT_WEATHER = "weather";
    private final static String QUERY = "?q=";
    private final static String ID = "?id=";
    private final static String API_POINT_FORECAST = "forecast";
    private final static String APPID = "&APPID=";
    private final static String DEV_KEY = "f17ef112f87d82f2c607d0676b4245e8";
    private final static String UNITS = "&units=metric";

    @Test
    void getCurrentTemperatureTooLow() throws IOException, JSONException {
        Weather weather = new Weather();
        Weather mockedWeather = mock(Weather.class);

        when(mockedWeather.getData(BASE + API_POINT_WEATHER + QUERY + "Tallinn" + APPID + DEV_KEY + UNITS)).thenReturn(new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get("res/test_data1.json")))));
//        BufferedReader br = mockedWeather.getData(BASE + API_POINT_WEATHER + QUERY + "Tallinn" + APPID + DEV_KEY + UNITS);
//        JSONObject jsonObject = new JSONObject();
//        System.out.println(jsonObject);
//        while (true) {
//            String data = br.readLine();
//            if (data == null || Objects.equals(data, "")) {
//                break;
//            }
//            System.out.println(data);
//        }
//        System.out.println(mockedWeather.getData(BASE + API_POINT_WEATHER + QUERY + "Tallinn" + APPID + DEV_KEY + UNITS).readLine());
        // TODO: Method should return int, not String
//        System.out.println(weather.parseCurrentTemperature(mockedWeather.getData(BASE + API_POINT_WEATHER + QUERY + "Tallinn" + APPID + DEV_KEY + UNITS), "Tallinn"));
        String result = weather.parseCurrentTemperature(mockedWeather.getData(BASE + API_POINT_WEATHER + QUERY + "Tallinn" + APPID + DEV_KEY + UNITS), "Tallinn");
        String[] pcs = result.split(":");
        int temp = Integer.parseInt(String.valueOf(pcs[1].trim().charAt(0)));
        assertFalse(temp < MIN_TEMP);
    }

    @Test
    void getCurrentTemperatureTooHigh() {
        Weather weather = new Weather();
        assertFalse(weather.getCurrentTemperatureTallinn() > MAX_TEMP);
    }

    /**
     * Check if the maximum temperature is higher or equal to minimum temperature.
     */
    @Test
    void getMaxMinOfNext3DaysMaxHigherThanMin() {
        Weather weather = new Weather();
        for (Map map: weather.getMaxMinListOfNext3Days()) {
            assertTrue((Integer)map.get("Max") >= (Integer) map.get("Min"));
        }
    }

    @Test
    void getMaxMinOfNext3DaysProperAmountOfData() {
        Weather weather = new Weather();
        assertEquals(weather.getMaxMinListOfNext3Days().size(), 3);
        for (int i = 0; i < 3; i++) {
            assertEquals(weather.getMaxMinListOfNext3Days().get(i).keySet().size(), 2);
        }
    }

    @Test
    void getMaxMinOfNext3DaysTooHigh() {
        Weather weather = new Weather();
        for (Map map: weather.getMaxMinListOfNext3Days()) {
            assertFalse((Integer)map.get("Max") >= MAX_TEMP);
            assertFalse((Integer)map.get("Min") >= MAX_TEMP);
        }
    }

    @Test
    void getMaxMinOfNext3DaysTooLow() {
        Weather weather = new Weather();
        for (Map map: weather.getMaxMinListOfNext3Days()) {
            assertFalse((Integer)map.get("Max") <= MIN_TEMP);
            assertFalse((Integer)map.get("Min") <= MIN_TEMP);
        }
    }

    @Test
    void getMaxMinOfNext3DaysTooBigChange() {
        Weather weather = new Weather();
        int temp = weather.getMaxMinListOfNext3Days().get(0).get("Max");
        for (Map map: weather.getMaxMinListOfNext3Days()) {

            assertTrue(Math.abs((Integer)map.get("Max") - temp) < 30);
            temp = (Integer)map.get("Max");
        }
        for (Map map: weather.getMaxMinListOfNext3Days()) {
            assertTrue(Math.abs((Integer)map.get("Min") - temp) < 30);
            temp = (Integer)map.get("Min");
        }
    }

    @Test
    void getCoordsPossible() {
        Weather weather = new Weather();
        assertTrue(weather.getCoords().get("lat") > -90.0);
        assertTrue(weather.getCoords().get("lat") < 90.0);
        assertTrue(weather.getCoords().get("lon") < 180.0);
        assertTrue(weather.getCoords().get("lon") > -180.0);
    }

}
