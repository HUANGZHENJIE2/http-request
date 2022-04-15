package cn.ac.hzj.httprequest;

public class HttpHeaderBuilder extends HttpMapBuilder{

    protected HttpHeaderBuilder(){
        super();
        this.put("user-agent", "httprequest 1.0 (java1.8)");
    }

    public static HttpHeaderBuilder init(){
        return new HttpHeaderBuilder();
    }

    public HttpHeaderBuilder setContentType(String v){
        this.put("Content-Type", v);
        return this;
    }
}
