package exceptions;

/**
 * @author Ragnar
 */
public class WeatherReportNotFoundException extends Exception {

    public WeatherReportNotFoundException(String message) {
        // todo:
        // available reports
        // requested report
        super(message);
    }
}
