package openweathermap.input;

import openweathermap.api.query.Query;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Matchers.any;

/**
 * @author Ragnar
 */
public class FileInputTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private String data;
    private String forecastData;

    @Before
    public void setUp() throws Exception {
        systemOutRule.mute();
        data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/weather.json")));
        forecastData = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/forecast.json")));
        UrlConnectionWeatherClient mockedClient = Mockito.mock(UrlConnectionWeatherClient.class);
        Mockito.when(mockedClient.getWeatherData(any(Query.class))).thenReturn(data).thenReturn(data).thenReturn(forecastData);
        FileInput fileInput = new FileInput(mockedClient);
        BufferedReader mockedBr = Mockito.mock(BufferedReader.class);
        fileInput.setBr(mockedBr);
        Mockito.when(mockedBr.readLine()).thenReturn("test").thenReturn(null);
        fileInput.fileInput();
    }

    @Test
    public void fileInputReadWriteTestFileContent() throws Exception {
        List<String> actualLines = Files.readAllLines(Paths.get("src/main/res/weatherout/test.txt"));
        List<String> expectedLines = Files.readAllLines(Paths.get("src/main/res/test_data/wreport.txt"));
        assertEquals(actualLines, expectedLines);
    }

    @Test
    public void fileInputReadMessages() throws Exception {
        assertEquals("Reading from input.txt\n" +
                "Writing to test.txt\n" +
                "Done processing.\n",
                systemOutRule.getLogWithNormalizedLineSeparator());
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(Paths.get("src/main/res/weatherout/test.txt"));
    }
}