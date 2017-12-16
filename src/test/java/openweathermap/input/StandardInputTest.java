package openweathermap.input;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.query.Query;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Matchers.any;

/**
 * @author Ragnar
 */
public class StandardInputTest {
    private String data;
    private String forecastData;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() throws IOException {
        data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/weather.json")));
        forecastData = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/forecast.json")));
    }

    @Test
    public void testStdInQuit() {
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        StandardInput standardInput = new StandardInput(mockedClient, new Scanner("q"));
        systemOutRule.mute();
        standardInput.stdInput();
    }

    @Test
    public void testStdInReport() throws WeatherReportNotFoundException {
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        Mockito.when(mockedClient.getWeatherData(any(Query.class))).thenReturn(data).thenReturn(data).thenReturn(forecastData);
        StandardInput standardInput = new StandardInput(mockedClient, new Scanner("Tallinn"));
        systemOutRule.mute();
        standardInput.stdInput();
        assertEquals("Enter a city name: \n" +
                        "City: Tallinn\n" +
                        "Coordinates: lat: 59.44, lon: 24.75\n" +
                        "Current temperature: 2℃\n" +
                        "\n" +
                        "Forecast for the next 3 days:\n" +
                        "2017-12-04: min temp: 0℃, max temp: 0℃\n" +
                        "2017-12-05: min temp: 0℃, max temp: 3℃\n" +
                        "2017-12-06: min temp: 1℃, max temp: 2℃\n",
                systemOutRule.getLogWithNormalizedLineSeparator());
    }

    @Test
    public void testStdInReportNoReportMessage() throws WeatherReportNotFoundException {
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        Mockito.when(mockedClient.getWeatherData(any(Query.class))).thenThrow(new WeatherReportNotFoundException("No Report"));
        StandardInput standardInput = new StandardInput(mockedClient, new Scanner("a"));
        systemOutRule.mute();
        standardInput.stdInput();
        assertEquals("Enter a city name: \n" +
                        "Report not found!\n",
                systemOutRule.getLogWithNormalizedLineSeparator());
    }
}