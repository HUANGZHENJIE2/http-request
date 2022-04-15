package cn.ac.hzj.httprequest;

import java.net.HttpURLConnection;

public interface HttpRequestEvent {

    default HttpURLConnection beforeRequest(HttpURLConnection httpURLConnection){
        return httpURLConnection;
    }
}
