package openweathermap.report;

import exceptions.WeatherReportNotFoundException;
import model.Coordinate;
import openweathermap.api.query.QueryBuilder;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import utility.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ragnar
 */
public class WeatherReport {
    private final static String API_POINT_WEATHER = "weather";
    private final static String API_POINT_FORECAST = "forecast";

    private final String city;
    private Coordinate coords;
    private CurrentWeather currentWeather;
    private Forecast forecast;

    public WeatherReport(UrlConnectionWeatherClient client, String city) throws WeatherReportNotFoundException {
        this.city = city;

        this.coords = new Coordinate(client.getWeatherData(new QueryBuilder().
                searchPath(API_POINT_WEATHER).
                requestPath(city).
                Unit(Unit.metric).
                buildQuery()));

        this.currentWeather = new CurrentWeather(client.getWeatherData(new QueryBuilder().
                searchPath(API_POINT_WEATHER).
                requestPath(city).
                Unit(Unit.metric).
                buildQuery()));

        this.forecast = new Forecast(client.getWeatherData(new QueryBuilder().
                searchPath(API_POINT_FORECAST).
                requestPath(city).
                Unit(Unit.metric).
                buildQuery()));
    }


    public List<String> getReport() {
        List<String> weatherReport = new ArrayList<>();
        weatherReport.add("City: " + city);
        weatherReport.add(coords.getReport());
        weatherReport.add(currentWeather.getReport());
        weatherReport.add(forecast.getReport().trim());
        return weatherReport;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
