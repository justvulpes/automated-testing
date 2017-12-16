package model;


import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Ragnar
 */
public class CoordinateTest {

    @Test
    public void testParseCoordFullJson() throws IOException {
        String data = data = new String(Files.readAllBytes(Paths.get("src/main/res/test_data/weather.json")));
        Coordinate coordinate = new Coordinate(data);
        assertEquals(59.44, coordinate.getLatitude());
        assertEquals(24.75, coordinate.getLongitude());
    }

    @Test
    public void testParseCoordNegativeZero() {
        String data = "{\"coord\":{\"lon\":-20.22,\"lat\":0.00}}";
        Coordinate coordinate = new Coordinate(data);
        assertEquals(0.00, coordinate.getLatitude());
        assertEquals(-20.22, coordinate.getLongitude());
    }

    @Test
    public void testCoordsLimits() {
        String data = "{\"coord\":{\"lon\":180.0,\"lat\":-180.00}}";
        Coordinate coordinate = new Coordinate(data);
        assertEquals(-180, coordinate.getLatitude(), 0.001);
        assertEquals(180, coordinate.getLongitude(), 0.001);
    }

    @Test
    public void testGetReport() {
        String data = "{\"coord\":{\"lon\":22.22,\"lat\":33.33}}";
        Coordinate coordinate = new Coordinate(data);
        assertEquals(String.format("Coordinates: lat: %.2f, lon: %.2f", 33.33, 22.22), coordinate.getReport());

    }

    @Test (expected = RuntimeException.class)
    public void testParseCoordBadJson() throws IOException {
        String data = "{{";
        Coordinate coordinate = new Coordinate(data);
        assertEquals(59.44, coordinate.getLatitude());
        assertEquals(24.75, coordinate.getLongitude());
    }


}