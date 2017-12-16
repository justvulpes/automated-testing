package openweathermap.api.query;

import utility.Unit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Ragnar
 */
public class Query {

    private final static String BASE = "http://api.openweathermap.org/data/2.5/";
    private final static String QUESTION_MARK = "?";
    private final static String AND = "&";

    private String appId;
    private String searchPath;
    private String requestPath;
    private Unit unit;
    private String encoding = "UTF-8";

    public Query(String appId, String searchPath, String requestPath, Unit unit) {
        this.appId = appId;
        this.searchPath = searchPath;
        this.requestPath = requestPath;
        this.unit = unit;
    }

    public String getQueryString() {
        try {
            return BASE + searchPath + QUESTION_MARK +
                    "q=" + URLEncoder.encode(requestPath, encoding) +
                    AND +
                    "appid=" + appId +
                    AND +
                    "units=" + unit;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 not supported");
        }
    }

    public String getAppId() {
        return appId;
    }

    public String getSearchPath() {
        return searchPath;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
