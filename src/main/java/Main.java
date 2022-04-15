import cn.ac.hzj.httprequest.HttpBodyBuilder;
import cn.ac.hzj.httprequest.HttpHeaderBuilder;
import cn.ac.hzj.httprequest.HttpMapBuilder;
import cn.ac.hzj.httprequest.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        Map<String,String> data = Collections.unmodifiableMap(HttpRequest.post(
                "http://localhost:9020/api/v1/token",
                HttpHeaderBuilder.init().setContentType("application/json").build(),
                HttpMapBuilder.init().put("app_key", "0000000002").put("app_secret", "BiyplosPxyVP6cvF4Rp7e8TD2TWZQuUG").build()
        ).fromJson(Map.class));

        String token = data.get("token");

        HttpRequest.upload("http://localhost:9020/api/office/ofd",
                HttpBodyBuilder.init().putFile("file", "./trojan.xlsx").put("token", token).put("business_id", "huangzhenjie.io")
                        .put("business_name", "huangzhenjie.io").build()
                ).download("./", "trojan.ofd");

        System.out.println(data.toString());

    }
}
