package server;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class Request {
    private final String path;
    private final Map<String, String> queryParams;

    public Request(String path){
        this.path = path;
        this.queryParams = parseQueryParams(path);
    }
    private Map<String, String> parseQueryParams(String requestPath) {
        int queryStartIndex = requestPath.indexOf('?');
        if (queryStartIndex == -1) {
            return new HashMap<>();
        }

        String queryString = requestPath.substring(queryStartIndex + 1);
        List<org.apache.http.NameValuePair> params = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
        Map<String, String> paramMap = new HashMap<>();

        for (org.apache.http.NameValuePair param : params) {
            paramMap.put(param.getName(), param.getValue());
        }

        return paramMap;
    }

    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
