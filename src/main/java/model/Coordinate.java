package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ragnar
 */
public class Coordinate {
    private double latitude;
    private double longitude;

    public Coordinate(String data) {
        this.latitude = parseCoord(data, "lat");
        this.longitude = parseCoord(data, "lon");
    }

    public double parseCoord(String data, String coordinate) {
        double coord;
        try {
            JSONObject jsonObject = new JSONObject(data);
            coord = jsonObject.getJSONObject("coord").getDouble(coordinate);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return coord;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getReport() {
        return String.format("Coordinates: lat: %.2f, lon: %.2f", latitude, longitude);
    }
}
