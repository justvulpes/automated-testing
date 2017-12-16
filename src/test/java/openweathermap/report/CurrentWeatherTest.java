package openweathermap.report;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * @author Ragnar
 */
public class CurrentWeatherTest {

    private CurrentWeather currentWeather;

    @Before
    public void setUp() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/weather.json")));
        currentWeather = new CurrentWeather(data);
    }

    @Test
    public void testCurrentTemperatureMaxValue() {
        assertTrue(currentWeather.getTemperature() < 70);
    }

    @Test
    public void testCurrentTemperatureMinValue() {
        assertTrue(currentWeather.getTemperature() > -70);
    }

    @Test
    public void testReportString() {
        assertTrue(currentWeather.getReport().startsWith("Current temperature:"));
    }

    @Test (expected = RuntimeException.class)
    public void testBadJSON() {
        CurrentWeather currentWeather = new CurrentWeather("}}");
        currentWeather.parseCurrentTemperature();
    }

}