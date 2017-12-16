package openweathermap.input;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import openweathermap.report.WeatherReport;

import java.util.List;
import java.util.Scanner;

/**
 * @author Ragnar
 */
public class StandardInput {
    private UrlConnectionWeatherClient client;
    private Scanner scanner;

    public StandardInput(UrlConnectionWeatherClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public void stdInput() {
        System.out.println("Enter a city name: ");
        while (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            if (in.equals("q")) break;
            List<String> report;
            try {
                report = new WeatherReport(client, in).getReport();
            } catch (WeatherReportNotFoundException e) {
                System.out.println("Report not found!");
                continue;
            }
            System.out.println(String.join("\n", report));
        }
    }
}
