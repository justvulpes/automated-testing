package openweathermap.weather;

import openweathermap.input.FileInput;
import openweathermap.input.StandardInput;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * @author Ragnar
 */
public class WeatherTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();


    @Test
    public void testUserInputCalled() {
        StandardInput mockedStdIn = Mockito.mock(StandardInput.class);
        Weather weather = new Weather("a");
        weather.setStandardInput(mockedStdIn);
        systemOutRule.mute();
        systemInMock.provideLines("1", "q");
        weather.startSystem();
        Mockito.verify(mockedStdIn).stdInput();
    }

    @Test
    public void testFileInputCalled() {
        FileInput mockedFileIn = Mockito.mock(FileInput.class);
        Weather weather = new Weather(System.getenv("a"));
        weather.setFileInput(mockedFileIn);
        systemOutRule.mute();
        systemInMock.provideLines("2");
        weather.startSystem();
        Mockito.verify(mockedFileIn).fileInput();
    }
}
