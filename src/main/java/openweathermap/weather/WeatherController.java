package openweathermap.weather;

/**
 * @author Ragnar
 */
public class WeatherController {

    private final static String DEV_KEY = System.getenv("DEV_KEY");

    public static void main(String[] args) {
        new Weather(DEV_KEY).startSystem();
    }
}
