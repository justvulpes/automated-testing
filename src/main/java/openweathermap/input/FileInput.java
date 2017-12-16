package openweathermap.input;

import exceptions.WeatherReportNotFoundException;
import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import openweathermap.report.WeatherReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Ragnar
 */
public class FileInput {
    private final static String IN_FILE = "src/main/res/input.txt";
    private final static String OUT_PATH = "src/main/res/weatherout/";

    private BufferedReader br;

    private UrlConnectionWeatherClient client;

    public FileInput(UrlConnectionWeatherClient client) {
        this.client = client;
        try {
            br = new BufferedReader(new FileReader(IN_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fileInput() {
        try {
            System.out.println("Reading from input.txt");
            while (true) {
                String city = br.readLine();
                if (city == null || city.isEmpty()) {
                    break;
                }
                List<String> report;
                try {
                    report = new WeatherReport(client, city).getReport();
                } catch (WeatherReportNotFoundException e) {
                    System.out.println("Report not found!");
                    return;
                }
                System.out.println(String.format("Writing to %s.txt", city));
                Files.write(Paths.get(String.format("%s%s.txt", OUT_PATH, city)), report);
            }
            System.out.println("Done processing.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }
}
