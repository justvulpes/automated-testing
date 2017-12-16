package openweathermap.api.weatherclient;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.query.Query;

/**
 * @author Ragnar
 */
public interface WeatherClient {
    String getWeatherData(Query query) throws WeatherReportNotFoundException;
}
