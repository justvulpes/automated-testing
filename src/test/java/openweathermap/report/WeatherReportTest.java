package openweathermap.report;


import exceptions.WeatherReportNotFoundException;
import model.Coordinate;
import openweathermap.api.query.Query;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Ragnar
 */
public class WeatherReportTest {

    private String data;
    private String forecastData;

    @Before
    public void setUp() throws IOException {
        data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/weather.json")));
        forecastData = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/forecast.json")));
    }

    @Test
    public void testWeatherReportConstructorVerifyCalls() throws WeatherReportNotFoundException {
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        Mockito.when(mockedClient.getWeatherData(any(Query.class))).thenReturn(data);
        new WeatherReport(mockedClient, "Tallinn");
        Mockito.verify(mockedClient, times(3)).getWeatherData(any(Query.class));
    }

    @Test
    public void testWeatherReportConstructReport() throws WeatherReportNotFoundException {
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        Mockito.when(mockedClient.getWeatherData(any(Query.class))).thenReturn(data);
        WeatherReport weatherReport = new WeatherReport(mockedClient, "Tallinn");
        weatherReport.setCoords(new Coordinate(data));
        weatherReport.setCurrentWeather(new CurrentWeather(data));
        weatherReport.setForecast(new Forecast(forecastData));
        List<String> arr = new ArrayList<>();
        arr.add("City: Tallinn");
        arr.add("Coordinates: lat: 59.44, lon: 24.75");
        arr.add("Current temperature: 2℃\n");
        arr.add("Forecast for the next 3 days:\n" +
                "2017-12-04: min temp: 0℃, max temp: 0℃\n" +
                "2017-12-05: min temp: 0℃, max temp: 3℃\n" +
                "2017-12-06: min temp: 1℃, max temp: 2℃");
        assertTrue(weatherReport.getReport().equals(arr));
    }

}