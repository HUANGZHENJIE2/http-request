package cn.ac.hzj.httprequest;

import java.util.HashMap;
import java.util.Map;

public class HttpMapBuilder {
    protected Map<String, Object> headers = new HashMap<>();

    protected HttpMapBuilder(){}

    public static HttpMapBuilder init(){
        return new HttpMapBuilder();
    }
    public HttpMapBuilder put(String key, Object value){
        this.headers.put(key,value);
        return this;
    }

    public Map<String, Object> build(){
        return this.headers;
    }
}
