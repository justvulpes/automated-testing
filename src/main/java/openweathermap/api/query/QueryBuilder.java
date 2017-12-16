package openweathermap.api.query;

import utility.Unit;

/**
 * @author Ragnar
 */
public class QueryBuilder {

    private String _appId;
    private String _searchPath;
    private String _requestPath;
    private Unit _unit;

    public QueryBuilder() {
    }

    public Query buildQuery() {
        return new Query(_appId, _searchPath, _requestPath, _unit);
    }

    public QueryBuilder appId(String _appId) {
        this._appId = _appId;
        return this;
    }

    public QueryBuilder searchPath(String _searchPath) {
        this._searchPath = _searchPath;
        return this;
    }

    public QueryBuilder requestPath(String _requestPath) {
        this._requestPath = _requestPath;
        return this;
    }

    public QueryBuilder Unit(Unit _unit) {
        this._unit = _unit;
        return this;
    }
}
