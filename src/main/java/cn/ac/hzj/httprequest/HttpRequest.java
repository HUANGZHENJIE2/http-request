package cn.ac.hzj.httprequest;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;



public class HttpRequest {


    /**
     * 发送 http GET请求<br/>
     * @param url  例如：http//www.hzj.ac.cn && https://www.hzj.ac.cn ....<br/>
     * @return HttpResponse 返回 HttpResponse 示例，可获取响应状态码、响应消息、响应体等<br/>
     *
     * <p><br/>
     *  import cn.ac.hzj.httprequest.HttpRequest<br/>
     *  <br/>
     *  .....<br/>
     *
     *   // 请求示例<br/>
     *   String responseText =  HttpRequest.get("https://www.www.hzj.ac.cn.com").getContext();<br/>
     *   System.out.println(responseText)<br/>
     *  .....<br/>
     *
     *  <br/>
     *
     * <p/><br/>
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static HttpResponse get(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        return get(url, HttpHeaderBuilder.init().setContentType("").build());
    }

    /**
     * 发送 http GET请求
     * @param url http url 例如：http//www.hzj.ac.cn && https://www.hzj.ac.cn ....<br/>
     * @param header http 请求时的请求头 <b>Map<String, String><b/> 类型<br/>
     * @return HttpResponse 返回 HttpResponse 示例，可获取响应状态码、响应消息、响应体等<br/>
     * <br/>
     * <p>
     *  import cn.ac.hzj.httprequest.HttpRequest<br/>
     *
     *  .....
     *
     *   // 请求示例
     *   Map<String, String> header = new HashMap<>();<br/>
     *   header.put("Accept", "application/json")<br/>
     *   String responseText =  HttpRequest.get("https://www.www.hzj.ac.cn.com", header).getContext();<br/>
     *   System.out.println(responseText)<br/>
     *  .....
     *
     *
     *
     * <p/>
     * <br/>
     *
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static HttpResponse get(String url, Map<String, Object> header) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return request(
                url,
                "GET",
                header,
                null,
                1500,
                60000,
                false,
                true,
                false
        );
    }

    public static HttpResponse upload(String url, Map<String, Object> body) throws IOException {
        return post(url,  HttpHeaderBuilder.init().setContentType("multipart/form-data; boundary=Hzj19960916").build(), body);
    }

    public static HttpResponse upload(String url,Map<String, Object> header, Map<String, Object> body) throws IOException{
        header.put("Content-Type","multipart/form-data; boundary=Hzj19960916");
        return post(url,  header, body);
    }



    public static HttpResponse put(String url, Map<String, Object> body) throws IOException {
        return put(url, null, body);
    }

    public static HttpResponse put(String url, Map<String, Object> header, Map<String, Object> body) throws IOException {
        if (null == header){
            header = HttpHeaderBuilder.init().setContentType("application/json").build();
        }

        return request(
                url,
                "PUT",
                header,
                body, 15000,
                60000,
                true,
                true,
                false

        );
    }

    public static HttpResponse delete(String url, Map<String, Object> body) throws IOException {
        return delete(url, null, body);
    }

    public static HttpResponse delete(String url, Map<String, Object> header, Map<String, Object> body) throws IOException {
        if (null == header){
            header = HttpHeaderBuilder.init().setContentType("application/json").build();
        }

        return request(
                url,
                "DELETE",
                header,
                body, 15000,
                60000,
                true,
                true,
                false

        );
    }

    public static HttpResponse post(String url, Map<String, Object> body) throws IOException {
        return post(url, null, body);
    }

    public static HttpResponse post(String url, Map<String, Object> header, Map<String, Object> body) throws IOException {
        if (null == header){
            header = HttpHeaderBuilder.init().setContentType("application/x-www-form-urlencoded").build();
        }

        return request(
                url,
                "POST",
                header,
                body, 15000,
                60000,
                true,
                true,
                false

        );
    }

    public static HttpResponse request(
            String url,
            String method,
            Map<String, Object> header,
            Map<String, Object> body,
            int connectTimeout,
            int readTimeout,
            boolean doOutput,
            boolean doInput,
            boolean useCaches
    ) throws IOException{
        URL _url = new URL(url);

        if (null == header){
            header = HttpHeaderBuilder.init().build();
        }
        HttpURLConnection httpURLConnection = buildHttpURLConnection(
                _url, method, header, body,  connectTimeout,readTimeout,doOutput, doInput, useCaches, null
        );
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.ResponseCode = httpURLConnection.getResponseCode();
        httpResponse.ResponseMessage = httpURLConnection.getResponseMessage();
        httpResponse.Headers = httpURLConnection.getHeaderFields();

        httpResponse.Body = inputStreamToByteArray(httpURLConnection.getInputStream());
        return httpResponse;
    }


    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while (-1 != (len = inputStream.read(buffer))) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }


    private static HttpURLConnection buildHttpURLConnection(
            URL url,
            String method,
            Map<String, Object> headers,
            Map<String, Object> body,
            int connectTimeout,
            int readTimeout,
            boolean doOutput,
            boolean doInput,
            boolean useCaches,
            HttpRequestEvent httpRequestEvent

    ) throws IOException {

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);
        httpURLConnection.setDoOutput(doOutput);
        httpURLConnection.setDoInput(doInput);
        httpURLConnection.setUseCaches(useCaches);
        if (null != httpRequestEvent)
            httpURLConnection = httpRequestEvent.beforeRequest(httpURLConnection);

        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key).toString());
        httpURLConnection.connect();
        if (doOutput) {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if (null != body) {
                switch (headers.get("Content-Type").toString()) {
                    case "application/json":
                        Gson gson = new Gson();
                        outputStream.write(gson.toJson(body).getBytes());
                        outputStream.flush();
                        break;
                    case "application/octet-stream; boundary=Hzj19960916":
                        for (String key : body.keySet()){
                            if (body.get(key) instanceof String) {
                                outputStream.write("--Hzj19960916\r\n".getBytes());
                                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n").getBytes());
                                outputStream.write("\r\n".getBytes());
                                outputStream.write(body.get(key).toString().getBytes());
                                outputStream.write("\r\n".getBytes());
//                                outputStream.flush();
                            } else if (body.get(key) instanceof File){
                                File uploadFile = (File) body.get(key);
                                InputStream inputStream = new FileInputStream(uploadFile);
                                outputStream.write("--Hzj19960916\r\n".getBytes());
                                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\""+ uploadFile.getName() + "\"\r\n").getBytes());
                                outputStream.write(inputStreamToByteArray(inputStream));
                                outputStream.write("\r\n".getBytes());

                            }else {
                                System.out.println("body中“"+key+"”的字段被忽略，只接受String 和File类型");
                            }
                        }
                    case "multipart/form-data; boundary=Hzj19960916":
//                        outputStream = new FileOutputStream("./huang.txt");
                        outputStream.write("Content-type: multipart/form-data, boundary=Hzj19960916\r\n".getBytes());
                        for (String key : body.keySet()){
                            if (body.get(key) instanceof String) {
                                outputStream.write("--Hzj19960916\r\n".getBytes());
                                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n").getBytes());
                                outputStream.write("\r\n".getBytes());
                                outputStream.write((body.get(key).toString()).getBytes());
                                outputStream.write("\r\n".getBytes());
//                                outputStream.flush();
                            } else if (body.get(key) instanceof File){
                                File uploadFile = (File) body.get(key);
                                InputStream inputStream = new FileInputStream(uploadFile);
                                outputStream.write("--Hzj19960916\r\n".getBytes());
                                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\""+ uploadFile.getName() + "\"\r\n").getBytes());
                                outputStream.write("\r\n".getBytes());
                                outputStream.write(inputStreamToByteArray(inputStream));
                                outputStream.write("\r\n".getBytes());

                            }else {
                                System.out.println("body中“"+key+"”的字段被忽略，只接受String 和File类型");
                            }
                        }
                        outputStream.write("--Hzj19960916--\r\n".getBytes());
                        break;
                    default:
                        String prams = "";
                        for (String key : body.keySet())
                            prams += key + "=" + body.get(key) + "&";
                        outputStream.write(decode(prams.substring(0, prams.length() -1)).getBytes());
                        outputStream.flush();
                        break;
                }
            }
        }
        return httpURLConnection;
    }

    public static String decode(String url) {
        try {
            String prevURL = "";
            String decodeURL = url;
            while (!prevURL.equals(decodeURL)) {
                prevURL = decodeURL;
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }

}
