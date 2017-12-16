package openweathermap.report;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ragnar
 */
public class CurrentWeather {
    private String data;
    private int temperature;

    public CurrentWeather(String data) {
        this.data = data;
        this.temperature = parseCurrentTemperature();
    }

    public int parseCurrentTemperature() {
        int temperature;
        try {
            JSONObject jsonObject = new JSONObject(data);
            temperature = jsonObject.getJSONObject("main").getInt("temp");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return temperature;
    }

    public String getReport() {
        return String.format("Current temperature: %d℃\n", temperature);
    }

    public int getTemperature() {
        return temperature;
    }
}
