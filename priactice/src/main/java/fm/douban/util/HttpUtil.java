package fm.douban.util;

import okhttp3.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private OkHttpClient okHttpClient;

    public HttpUtil() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    //构建http header，也许爬虫有用
    public Map<String, String> buildHeaderData(String referer, String host) {
        Map<String ,String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
        header.put("Referer",referer);
        header.put("Host",host);
        return header;
    };
    //根据输入的url，读取页面内容并返回
   public String getContent(String url, Map<String, String> headers) {
        if (!StringUtils.hasText(url)){
            return null;
        }
        Request request = new Request.Builder().url(url)
                .addHeader("User-Agent",headers.get("User-Agent"))
                .addHeader("Referre",headers.get("Referer"))
                .addHeader("Host",headers.get("Host"))
                .build();
        Call call = okHttpClient.newCall(request);
        String content = null;
        try {
            content = call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("request "+url+" error! message:"+e.getMessage());
        }
        return content;
    }
}
