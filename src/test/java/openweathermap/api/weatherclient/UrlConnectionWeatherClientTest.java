package openweathermap.api.weatherclient;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author Ragnar
 */
public class UrlConnectionWeatherClientTest {

    private URLWrapper mockedUrl;
    private HttpURLConnection connection;
    private UrlConnectionWeatherClient client;

    @Before
    public void setUp() throws IOException {
        mockedUrl = Mockito.mock(URLWrapper.class);
        connection = Mockito.mock(HttpURLConnection.class);
        client = new UrlConnectionWeatherClient("a");
        Mockito.when(mockedUrl.openConnection()).thenReturn(connection);
    }

    @Test
    public void testGetWeatherDataNormalWeatherQuery() throws Exception {
        Mockito.when(connection.getResponseCode()).thenReturn(200);  // OK
        client.makeRequest(mockedUrl);
        Mockito.verify(mockedUrl).openConnection();
    }

    @Test(expected = WeatherReportNotFoundException.class)
    public void testNoReportFoundException() throws IOException, WeatherReportNotFoundException {
        Mockito.when(connection.getResponseCode()).thenReturn(404);  // Not found
        client.makeRequest(mockedUrl);
    }

    @Test(expected = RuntimeException.class)
    public void testConnectionBadRequestRuntimeException() throws IOException, WeatherReportNotFoundException {
        Mockito.when(connection.getResponseCode()).thenReturn(400);  // Bad Request
        client.makeRequest(mockedUrl);
    }

    @Test
    public void testSetProperRequestMethod() throws IOException, WeatherReportNotFoundException {
        Mockito.when(connection.getResponseCode()).thenReturn(200);
        client.makeRequest(mockedUrl);
        Mockito.verify(connection).setRequestMethod("GET");
    }

    @Test
    public void testSetRequestProperty() throws IOException, WeatherReportNotFoundException {
        Mockito.when(connection.getResponseCode()).thenReturn(200);
        client.makeRequest(mockedUrl);
        Mockito.verify(connection).setRequestProperty("Accept", "application/json");
    }

    @Test(expected = RuntimeException.class)
    public void testConnectionCausesIOException() throws IOException, WeatherReportNotFoundException {
        Mockito.when(connection.getResponseCode()).thenThrow(new IOException());
        client.makeRequest(mockedUrl);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUrl() throws Exception {
        Query mockedQuery = Mockito.mock(Query.class);
        client.getUrl(mockedQuery);
        Mockito.verify(mockedQuery.getQueryString());
    }

    @Test(expected = RuntimeException.class)
    public void testWeatherDataAppIdSet() throws WeatherReportNotFoundException {
        Query mockedQuery = Mockito.mock(Query.class);
        client.getWeatherData(mockedQuery);
        Mockito.verify(mockedQuery).setAppId("a");
    }

    @Test
    public void testGetDataFromResponse() throws Exception {
        InputStream inStream = new ByteArrayInputStream("test data".getBytes());
        InputStreamReader mockedIn = Mockito.mock(InputStreamReader.class);
        BufferedReader mockedBr = Mockito.mock(BufferedReader.class);
        Mockito.when(connection.getInputStream()).thenReturn(inStream);
        Mockito.when(mockedBr.readLine()).thenReturn("abc").thenReturn("");
        whenNew(InputStreamReader.class).withAnyArguments().thenReturn(mockedIn);
        whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockedBr);
        assertEquals("test data", client.getDataFromResponse(connection));
    }

    @Test (expected = RuntimeException.class)
    public void testGetDataFromResponseBad() throws Exception {
        InputStreamReader mockedIn = Mockito.mock(InputStreamReader.class);
        BufferedReader mockedBr = Mockito.mock(BufferedReader.class);
        Mockito.when(connection.getInputStream()).thenThrow(new IOException());
        whenNew(InputStreamReader.class).withAnyArguments().thenReturn(mockedIn);
        whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockedBr);
        System.out.println(client.getDataFromResponse(connection));
    }
}