package openweathermap.api.query;

import org.junit.Test;
import utility.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * @author Ragnar
 */
public class QueryBuilderTest {

    @Test
    public void testBuildStandardQuery() {
        Query query = new QueryBuilder()
                .searchPath("a")
                .requestPath("b")
                .appId("c")
                .Unit(Unit.metric)
                .buildQuery();

        assertEquals("a", query.getSearchPath());
        assertEquals("b", query.getRequestPath());
        assertEquals("c", query.getAppId());
        assertEquals(Unit.metric, query.getUnit());
    }

    @Test
    public void testBuildStandardQuery2() {
        Query query = new QueryBuilder()
                .searchPath("vroom")
                .requestPath("yuum")
                .appId("someid")
                .Unit(Unit.imperial)
                .buildQuery();

        assertEquals("vroom", query.getSearchPath());
        assertEquals("yuum", query.getRequestPath());
        assertEquals("someid", query.getAppId());
        assertEquals(Unit.imperial, query.getUnit());
    }

    @Test
    public void testBuildMissingAppIdQuery() {
        Query query = new QueryBuilder()
                .searchPath("a")
                .requestPath("b")
                .Unit(Unit.metric)
                .buildQuery();

        assertNull(query.getAppId());
    }
}