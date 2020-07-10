package org.maxkey.pretty;

import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.pretty.impl.SqlPretty;
import org.maxkey.pretty.impl.XmlPretty;

public class PrettyFactory {
    
    static final Pretty jsonPretty  = new JsonPretty();
    
    static final Pretty sqlPretty   = new SqlPretty();
    
    static final Pretty xmlPretty   = new XmlPretty();
    
    public static Pretty getJsonPretty() {
        return jsonPretty;
    }
    
    public static Pretty getXmlPretty() {
        return xmlPretty;
    }
    
    public static Pretty getSqlPretty() {
        return sqlPretty;
    }
    
}
