package openweathermap.report;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Ragnar
 */
public class ForecastTest {

    private String data;
    private Forecast forecast;

    @Before
    public void setUp() throws IOException {
        data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/forecast.json")));
        forecast = new Forecast(data);
    }

    @Test
    public void testParseForecastInitial() {
        assertEquals(0, forecast.getDayMinTemp().size());
        assertEquals(0, forecast.getDayMaxTemp().size());
        assertEquals(0, forecast.getDatesInOrder().size());
    }

    @Test
    public void testParseForecastHasTemps() {
        forecast.parseForecast(data);
        assertTrue(forecast.getDayMinTemp().size() >= 1);
        assertTrue(forecast.getDayMaxTemp().size() >= 1);
        assertTrue(forecast.getDatesInOrder().size() >= 1);
    }

    @Test
    public void testForecastTemperatureMaxLimit() {
        forecast.parseForecast(data);
        for (Integer temp : forecast.getDayMinTemp().values()) {
            assertTrue(temp < 70);
        }
    }

    @Test
    public void testForecastTemperatureMinLimit() {
        forecast.parseForecast(data);
        for (Integer temp : forecast.getDayMinTemp().values()) {
            assertTrue(temp > -70);
        }
    }

    @Test
    public void testForecastTemperatureMaxHigherOrEqualToMin() {
        forecast.parseForecast(data);
        for (String date : forecast.getDatesInOrder()) {
            assertTrue(forecast.getDayMinTemp().get(date) <= forecast.getDayMaxTemp().get(date));
        }
    }

    @Test
    public void testGetReportFirstLine() {
        assertEquals("Forecast for the next 3 days:", forecast.getReport().split("\n")[0]);
    }

    @Test
    public void testGetReportTotalLines() {
        assertEquals(4, forecast.getReport().split("\n").length);
    }

    @Test (expected = RuntimeException.class)
    public void testBadJSON() {
        Forecast f = new Forecast("}}");
        f.parseForecast("}}");
    }
}