package test;

import org.junit.jupiter.api.Test;
import weather.Weather;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ragnar
 */
public class WeatherTest {

    /**Maximum and minimum possible temperatures.*/
    public static final int MAX_TEMP = 50;
    public static final int MIN_TEMP = -50;

    @Test
    void getCurrentTemperatureTooLow() {
        Weather weather = new Weather();
        assertFalse(weather.getCurrentTemperatureTallinn() < MIN_TEMP);
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
