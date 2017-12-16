package openweathermap.weather;

import openweathermap.api.weatherclient.UrlConnectionWeatherClient;
import openweathermap.input.FileInput;
import openweathermap.input.StandardInput;
import utility.Console;

import java.util.Scanner;

/*
TODO: Vaheülesanne #1
Mõtle välja kõikvõimalikud testid, mis on vajalikud projekti lahendamiseks.
Implementeeri testid (programmikoodi ei ole) nii et testid ei läbi (on punases)
 */

/*
TODO: Vaheülesanne #2
Registreeri end https://openweathermap.org/api kasutajaks
Kasuta APIsid, et saada praegune temperatuur ja 3 päeva ennustuse
temperatuur (kasuta linnana Tallinn)
Kirjuta realiseeriv programmikood, et implementeeritud testid läbiks. Tee teste
juurde, kuniks oled tulemusega rahul. Refaktoreeri
Kasuta OpenWeatherMap APIsid, et saada praegune temperatuur ja 3 päeva ennustuse temperatuurid (min/max)
 */

/*
TODO: Vaheülesanne #3
Muuda linn parametriseeritavaks
Võimalus sisestada linna nimi ka konsoolist.
Kasuta faili nimega input.txt kus on kirjas, mis linna kohta infot küsida
Väljund kirjuta faili output.txt
Täienda teste, refaktoreeri
 */

/*
TODO: Vaheülesanne #4
inpux.txt failis linna nimed,
väljund (hetke ja ennustus) kirjutada faili {linnanimi}.txt
○ Linna nimi
○ Linna koordinaadid
○ Iga ennustatud päeva (kolm esimest) kohta:
    ■ Max temp
    ■ Min temp
○ Hetke ilma temperatuur
testidega mockida välise sõltuvuse (APId) - Repeatable ehk testid peavad jooksma igas keskkonnas
testidega mockida failist lugemise ja kirjutamise
 */

/*
TODO: raise WeatherReportNotFound when Query Fails
 */


/**
 * @author Ragnar
 */
public class Weather {

    private Console console = new Console();
    private UrlConnectionWeatherClient client;
    private FileInput fileInput;
    private StandardInput standardInput;
    private Scanner scanner;


    public Weather(String appId) {
        this.client = new UrlConnectionWeatherClient(appId);
        this.scanner = new Scanner(System.in);
        this.fileInput = new FileInput(client);
        this.standardInput = new StandardInput(client, scanner);
    }

    public void startSystem() {
        System.out.println(console.greeting_message("input.txt"));
        user_input();
    }

    private void user_input() {
        while (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            switch (in) {
                case "1":
                    standardInput.stdInput();
                    return;
                case "2":
                    fileInput.fileInput();
                    return;
                default:
                    System.out.println("Bad input, try again!");
                    break;
            }
        }
    }

    public void setFileInput(FileInput fileInput) {
        this.fileInput = fileInput;
    }

    public void setStandardInput(StandardInput standardInput) {
        this.standardInput = standardInput;
    }
}
