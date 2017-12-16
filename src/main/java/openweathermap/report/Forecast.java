package openweathermap.report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ragnar
 */
public class Forecast {

    private String data;
    private StringBuilder result = new StringBuilder();
    private Map<String, Integer> dayMinTemp = new HashMap<>();
    private Map<String, Integer> dayMaxTemp = new HashMap<>();
    private Set<String> datesInOrder = new LinkedHashSet<>();

    public Forecast(String data) {
        this.data = data;
    }

    public String parseForecast(String data) {
        result.append("Forecast for the next 3 days:\n");
        try {
            JSONArray jsonArray = new JSONObject(data).getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                int temperature = jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp");
                String dateString = jsonArray.getJSONObject(i).getString("dt_txt").split("\\s")[0];
                datesInOrder.add(dateString);
                dayMinTemp.putIfAbsent(dateString, Integer.MAX_VALUE);
                dayMaxTemp.putIfAbsent(dateString, Integer.MIN_VALUE);
                if (temperature < dayMinTemp.get(dateString)) {
                    dayMinTemp.put(dateString, temperature);
                }
                if (temperature > dayMaxTemp.get(dateString)) {
                    dayMaxTemp.put(dateString, temperature);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // only the first three days worth of data
        getThreeDays();
        return result.toString();
    }

    private void getThreeDays() {
        int counter = 0;
        for (String date : datesInOrder) {
            result.append(String.format("%s: min temp: %d℃, max temp: %d℃\n", date, dayMinTemp.get(date), dayMaxTemp.get(date)));
            if (++counter == 3) break;
        }
    }

    public String getReport() {
        return parseForecast(data);
    }

    public Map<String, Integer> getDayMinTemp() {
        return dayMinTemp;
    }

    public Map<String, Integer> getDayMaxTemp() {
        return dayMaxTemp;
    }

    public Set<String> getDatesInOrder() {
        return datesInOrder;
    }
}
