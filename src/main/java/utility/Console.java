package utility;

/**
 * @author Ragnar
 */
public class Console {

    public String greeting_message(String filename) {
        String msg = "";
        msg += "+-----------------------------------------------------------+\n";
        msg += "|  Welcome!                                                 |\n";
        msg += "|  Get the latest weather info from the OpenWeatherMap API  |\n";
        msg += "|  Made by Ragnar Rebase                                    |\n";
        msg += "+-----------------------------------------------------------+\n\n";
        msg += "Your options:\n";
        msg += "1) Read city names from console\n";
        msg += "2) Read city names from " + filename + "\n\n";
        msg += "Choose a number: \n";
        return msg;
    }
}
