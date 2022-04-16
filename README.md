
![Image text](https://github.com/HUANGZHENJIE2/RocketX/raw/main/resources/app.ico)
# http-request
☞ http-request 是通过封装java httpURLConnection的 Maven 包，可快速使用java的GET、POST、PUT、DELETE，请求
## 安装
### 1.maven
```
 <!--使用httprequest源-->
 <repositories>
   <repository>
     <id>github-rich-repo</id>
     <name>httprequest</name>
     <url>https://huangzhenjie2.github.io/http-request/maven-repo/</url>
  </repository>
 </repositories>
 <!--添加依赖-->
 <dependencies>
   <dependency>
       <groupId>cn.ac.hzj.httprequest</groupId>
       <artifactId>http-request</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
 <dependencies>
  
```
## 快速使用
### http get请求
#### 1、通过get请求获取文本
```
import cn.ac.hzj.httprequest.HttpRequest;

....
String context = HttpRequest.get("https://www.baidu.com").getContext();
System.out.println(context);
....
```
#### 2、通过get获取json并转换Java 对象  
2.1 转换为map类型
```
import cn.ac.hzj.httprequest.HttpRequest;
import cn.ac.hzj.httprequest.HttpHeaderBuilder;
.....
 Map<String, String> respMap = HttpRequest.get("http://localhost:9020/api/v1/token",
    HttpHeaderBuilder.init().setAccept("application/json").put("app_key", "00****002")
                            .put("app_secret", "****plosPxyVP6c************").build()
    ).fromJson(Map.class);
        
System.out.println(respMap.toString());
....
```
2.2 通过get获取json并转换为自定义类型
```
import cn.ac.hzj.httprequest.HttpRequest;
import cn.ac.hzj.httprequest.HttpHeaderBuilder;
....
class Token {
    public String message;
    public String token;
    public int code;

    public Token(String message, String token, int code) {
        this.message = message;
        this.token = token;
        this.code = code;
    }

    public Token() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
....

 Toeken token = HttpRequest.get("http://localhost:9020/api/v1/token",
                HttpHeaderBuilder.init().setAccept("application/json").
                        put("app_key", "0000000002")
                        .put("app_secret", "BiyplosPxyVP6cvF4Rp7e8TD2TWZQuUG").build()
                ).fromJson(Toeken.class);
 System.out.println(token.getToken());
....
```

#### 3、get请求数据保存为文件
```
import cn.ac.hzj.httprequest.HttpRequest;

....
HttpRequest.get("https://www.baidu.com").download("./","index.html");
....
```
Tips: 获取文本用getContext()，转为java类型用fromJson，保存结果用dowload(),其他的POST、DELTE、PUT 都适用，转换为JAVA类型时响应结果必须是JSON！一定要是JSON。
使用downlad()，服务响应结国可以是任意类型，只要指定好正确的文明名即可。

#### 4、 post请求
```
import cn.ac.hzj.httprequest.HttpRequest;

....
 String result = HttpRequest.post(
                http://localhost:9020/api/v1/token,
                HttpBodyBuilder.init()
                        .put("app_key",app_key)
                        .put("app_secret", app_secret).build()
        ).getContext();
....
```
#### 5、 上传文件
````
import cn.ac.hzj.httprequest.HttpBodyBuilder;
import cn.ac.hzj.httprequest.HttpRequest;

....
HttpRequest.upload(
                    http://localhost:9020/api/v1/user/,
                    HttpBodyBuilder.init().putFile("file","./1.img") //可以添加多个文件这里的key相当于from表单inputname，values是文件按路径，可以是相对路径或绝对路径
                            .put("business_id", "H"+System.currentTimeMillis())
                            .put("business_name","图片")
                            .put("token", token).build()
                    ).getContext();
....
````
#### 6、 put请求
```
import cn.ac.hzj.httprequest.HttpRequest;

....
 String result = HttpRequest.put(
                http://localhost:9020/api/v1/user/001,
                HttpBodyBuilder.init()
                        .put("email","hua****@outlook.com")
                        .put("phone_number", "130****1824").build()
        ).getContext();
....
```
#### 7、 delete请求
```
import cn.ac.hzj.httprequest.HttpRequest;

....
 String result = HttpRequest.delete(
                http://localhost:9020/api/v1/user,
                HttpBodyBuilder.init()
                        .put("username","001")
        ).getContext();
....
```
#### 8、 自定义其他请求

```
import cn.ac.hzj.httprequest.HttpRequest;

....
  HttpRequest.request(
  "http://localhost:9020/api/v1/user", 
  "HEAD", //请求方法
  HttpHeaderBuilder.init().build(), //初始化header
  null, //请求体
  1500, //连接超时时间
  6000, //读取超时时间
  false, //是否使用输出流
  true,  //是否使用输入流
  false //是否使用缓存
  ).getContext();
....
```
## API 
### HttpRequest
#### static HttpResponse get(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException
#### static HttpResponse get(String url, Map<String, Object> header) throws IOException, NoSuchAlgorithmException, KeyManagementException
#### HttpResponse post(String url, Map<String, Object> body) throws IOException
#### static HttpResponse post(String url, Map<String, Object> header, Map<String, Object> body) throws IOException
#### static HttpResponse upload(String url, Map<String, Object> body) throws IOException
#### static HttpResponse upload(String url,Map<String, Object> header, Map<String, Object> body) throws IOException
#### static HttpResponse put(String url, Map<String, Object> body) throws IOException 
#### static HttpResponse put(String url, Map<String, Object> header, Map<String, Object> body) throws IOException 
#### static HttpResponse delete(String url, Map<String, Object> body) throws IOException
#### static HttpResponse delete(String url, Map<String, Object> header, Map<String, Object> body) throws IOException
#### static HttpResponse request(String url, String method,Map<String, Object> header,Map<String, Object> body,int connectTimeout,int readTimeout,boolean doOutput,boolean doInput, boolean useCaches) throws IOException
### HttpResponse
#### int ResponseCode
#### String ResponseMessage
#### Map<String, List<String>> Headers
#### public byte[] Body
#### String getContext() 
####  <T> T fromJson(Class<T> classOfT) throws JsonSyntaxException
####  void download(String path, String filename) throws IOException
### HttpBodyBuilder
#### static HttpBodyBuilder init()
#### HttpBodyBuilder putFile(String key, String filepath)
#### HttpBodyBuilder put(String key, Object value)
#### Map<String, Object> build()
### HttpHeaderBuilder
#### static HttpHeaderBuilder init()
#### HttpHeaderBuilder setContentType(String value)
#### HttpHeaderBuilder setAccept(String value)
#### HttpHeaderBuilder put(String key, Object value)
#### Map<String, Object> build()
