package org.maxkey.client.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.client.utils.Preconditions;

public class ParameterList {

    private static final char 	QUERY_STRING_SEPARATOR 	= 	'?';
    private static final String PARAM_SEPARATOR 		= 	"&";
    private static final String PAIR_SEPARATOR 			= 	"=";
    private static final String EMPTY_STRING 			= 	"";

    private final List<Parameter> params;

    public ParameterList() {
        params = new ArrayList<>();
    }

    ParameterList(List<Parameter> params) {
        this.params = new ArrayList<>(params);
    }

    public ParameterList(Map<String, String> map) {
        this();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.add(new Parameter(entry.getKey(), entry.getValue()));
            }
        }
    }

    public void add(String key, String value) {
        params.add(new Parameter(key, value));
    }

    public String appendTo(String url) {
        Preconditions.checkNotNull(url, "Cannot append to null URL");
        final String queryString = asFormUrlEncodedString();
        if (queryString.equals(EMPTY_STRING)) {
            return url;
        } else {
            return url
                    + (url.indexOf(QUERY_STRING_SEPARATOR) == -1 ? QUERY_STRING_SEPARATOR : PARAM_SEPARATOR)
                    + queryString;
        }
    }

    public String asOauthBaseString() {
        return HttpEncoder.encode(asFormUrlEncodedString());
    }

    public String asFormUrlEncodedString() {
        if (params.isEmpty()) {
            return EMPTY_STRING;
        }

        final StringBuilder builder = new StringBuilder();
        for (Parameter p : params) {
            builder.append(PARAM_SEPARATOR).append(p.asUrlEncodedPair());
        }
        return builder.substring(1);
    }

    public void addAll(ParameterList other) {
        params.addAll(other.getParams());
    }

    public void addQuerystring(String queryString) {
        if (queryString != null && !queryString.isEmpty()) {
            for (String param : queryString.split(PARAM_SEPARATOR)) {
            	try{
	                final String[] pair = param.split(PAIR_SEPARATOR);
	                final String key = HttpEncoder.decode(pair[0]);
	                final String value = pair.length > 1 ? HttpEncoder.decode(pair[1]) : EMPTY_STRING;
	                params.add(new Parameter(key, value));
            	}catch(Exception e){
            		
            	}
            }
        }
    }

    public boolean contains(Parameter param) {
        return params.contains(param);
    }

    public int size() {
        return params.size();
    }

    public List<Parameter> getParams() {
        return params;
    }

    public ParameterList sort() {
        final ParameterList sorted = new ParameterList(params);
        Collections.sort(sorted.getParams());
        return sorted;
    }
}
