package utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ragnar
 */
public class ConsoleTest {

    @Test
    public void greeting_message() {
        Console console = new Console();
        String m = "";
        m += "+-----------------------------------------------------------+\n";
        m += "|  Welcome!                                                 |\n";
        m += "|  Get the latest weather info from the OpenWeatherMap API  |\n";
        m += "|  Made by Ragnar Rebase                                    |\n";
        m += "+-----------------------------------------------------------+\n\n";
        m += "Your options:\n";
        m += "1) Read city names from console\n";
        m += "2) Read city names from " + "a" + "\n\n";
        m += "Choose a number: \n";
        assertEquals(m, console.greeting_message("a"));

    }

}