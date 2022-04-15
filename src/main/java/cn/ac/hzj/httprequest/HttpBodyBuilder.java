package cn.ac.hzj.httprequest;

import java.io.File;

public class HttpBodyBuilder extends HttpMapBuilder{

    protected HttpBodyBuilder(){
        super();
    }

    public static HttpBodyBuilder init(){
        return new HttpBodyBuilder();
    }


    public HttpBodyBuilder putFile(String key, String filepath) {
        this.put(key, new File(filepath));
        return this;
    }
}
