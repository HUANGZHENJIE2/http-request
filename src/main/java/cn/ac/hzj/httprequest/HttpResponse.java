package cn.ac.hzj.httprequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import java.io.*;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    public int ResponseCode;
    public String ResponseMessage;
    public Map<String, List<String>> Headers;
    public byte[] Body;

    public String getContext() {
        return new String(Body);
    }


    public <T> T fromJson(Class<T> classOfT) throws JsonSyntaxException {
        return new Gson().fromJson(getContext(),classOfT);
    }


    public void download(String path, String filename) throws IOException {
        File _path = new File(path);
        if (!_path.exists())
            _path.mkdirs();
        File file = new File(path + File.separator+ filename);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(Body,0, Body.length);
    }
}
