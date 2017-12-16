package openweathermap.api.query;

import org.junit.Test;
import utility.Unit;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


/**
 * @author Ragnar
 */
public class QueryTest {

    @Test
    public void testQueryStringSimple() {
        Query query = new Query("key", "weather", "Tallinn", Unit.metric);
        assertEquals("http://api.openweathermap.org/data/2.5/weather?q=Tallinn&appid=key&units=metric",
                query.getQueryString());
    }

    @Test
    public void testQueryStringExtra() {
        Query query = new Query("k", "forecast", "Random333", Unit.imperial);
        assertEquals("http://api.openweathermap.org/data/2.5/forecast?q=Random333&appid=k&units=imperial",
                query.getQueryString());
    }

    @Test
    public void testQueryStringCheckForUrlEncoderWithSpecialNames() {
        Query query = new Query("k", "forecast", "Parnu$Linn", Unit.metric);
        assertEquals("http://api.openweathermap.org/data/2.5/forecast?q=Parnu%24Linn&appid=k&units=metric",
                query.getQueryString());
    }

    @Test (expected = AssertionError.class)
    public void testQueryStringBadUrlEncoder() throws UnsupportedEncodingException {
        Query query = new Query("k", "forecast", "abc", Unit.metric);
        query.setEncoding("x");
        query.getQueryString();
    }

}