package test;

import org.junit.jupiter.api.Test;
import weather.Weather;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Ragnar
 */
public class WeatherTest {

    @Test
    void getCurrentTemperatureTooLow() {
        Weather weather = new Weather();
        assertFalse(weather.getCurrentTemperatureTallinn() < -50);
    }

    @Test
    void getCurrentTemperatureTooHigh() {
        Weather weather = new Weather();
        assertFalse(weather.getCurrentTemperatureTallinn() > 50);
    }

    @Test
    void getMaxMinOfNext3DaysMaxHigherThanMin() {
    }

    @Test
    void getMaxMinOfNext3DaysProperAmountOfData() {
    }

    @Test
    void getMaxMinOfNext3DaysTooHigh() {
    }

    @Test
    void getMaxMinOfNext3DaysTooLow() {
    }

    @Test
    void getMaxMinOfNext3DaysTooBigChange() {
    }

    @Test
    void getCoordsPossible() {
    }

    @Test
    void getCoordsFormat() {
    }

}
