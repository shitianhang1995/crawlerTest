package com.sth.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Auther: root
 * @Date: 2019/7/24 01:49
 * @Description:
 */
@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();

        //设置最大连接数
        this.cm.setMaxTotal(100);

        //设置每个主机最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求下载页面数据
     *
     * @param url
     * @return
     */
    public String doGetHtml(String url) {
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        try {
            //设置请求头
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("cookie", "unpl=V2_ZzNtbUUFQBxwWxRUeE1UAGJTEF0SVBYWJgoWU3MQXlVjBkdaclRCFX0UR1JnGl4UZwUZXEZcRhZFCEdkeBBVAWMDE1VGZxpFK0oYEDpBA04%2bR0ICLFYTHHMME1N7S1hSMwYUCEsEERB8D0BcextfDWUAR15AZ3MWdThGVUsfXwJvARdfRVdzJXI4dmRzEF0GYAUiXHJWc1chVE9XfRtZBCoFEVpKVUYXcgh2VUsa; __jdc=122270672; __jdv=122270672|haosou-pinzhuan|t_288551095_haosoupinzhuan|cpc|0a875d61c5fe47d8bc48679132932d23_0_6c284bc12e944a31a2d2b3a6983a54d6|1563872742041; areaId=14; __jdu=726934361; PCSYCityID=1116; shshshfpa=8eddd613-0064-a5c7-f340-bd5e5371c553-1563872743; shshshfpb=orUF8rLCaSuk35ezsjac1RQ%3D%3D; xtest=1438.cf6b6759; rkv=V0200; 3AB9D23F7A4B3C9B=DGQOB4NKADFRYXBDEEWWLA4WL25R3N6VMBHJE24MYEI3JDBIDXLPPFYFMMDTBKFIBF5IGAGD77BOHZN4KLXMFGXXOA; qrsc=3; __jda=122270672.726934361.1563872741.1563908513.1563916275.5; ipLoc-djd=14-1116-1119-50033; wlfstk_smdl=6dwq6iar9hsynsv2brdgz74cd2waoqfv; __jdb=122270672.4.726934361|5.1563916275; shshshfp=ff828fd07efe48ba9e9ab26a251d28f5; shshshsID=6964ea9e8f87144f07f7f2e24b975f53_1_1563918124063");
            httpGet.addHeader(":upgrade-insecure-requests", "1");
            httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);
            //解析响应。返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体是否为空
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    /**
     * 下载图片
     *
     * @param url
     * @return
     */
    public String doGetImage(String url) {
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        try {
            //设置请求头
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("cookie", "unpl=V2_ZzNtbUUFQBxwWxRUeE1UAGJTEF0SVBYWJgoWU3MQXlVjBkdaclRCFX0UR1JnGl4UZwUZXEZcRhZFCEdkeBBVAWMDE1VGZxpFK0oYEDpBA04%2bR0ICLFYTHHMME1N7S1hSMwYUCEsEERB8D0BcextfDWUAR15AZ3MWdThGVUsfXwJvARdfRVdzJXI4dmRzEF0GYAUiXHJWc1chVE9XfRtZBCoFEVpKVUYXcgh2VUsa; __jdc=122270672; __jdv=122270672|haosou-pinzhuan|t_288551095_haosoupinzhuan|cpc|0a875d61c5fe47d8bc48679132932d23_0_6c284bc12e944a31a2d2b3a6983a54d6|1563872742041; areaId=14; __jdu=726934361; PCSYCityID=1116; shshshfpa=8eddd613-0064-a5c7-f340-bd5e5371c553-1563872743; shshshfpb=orUF8rLCaSuk35ezsjac1RQ%3D%3D; xtest=1438.cf6b6759; rkv=V0200; 3AB9D23F7A4B3C9B=DGQOB4NKADFRYXBDEEWWLA4WL25R3N6VMBHJE24MYEI3JDBIDXLPPFYFMMDTBKFIBF5IGAGD77BOHZN4KLXMFGXXOA; qrsc=3; __jda=122270672.726934361.1563872741.1563908513.1563916275.5; ipLoc-djd=14-1116-1119-50033; wlfstk_smdl=6dwq6iar9hsynsv2brdgz74cd2waoqfv; __jdb=122270672.4.726934361|5.1563916275; shshshfp=ff828fd07efe48ba9e9ab26a251d28f5; shshshsID=6964ea9e8f87144f07f7f2e24b975f53_1_1563918124063");
            httpGet.addHeader(":upgrade-insecure-requests", "1");
            httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);
            //解析响应。返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体是否为空
                if (response.getEntity() != null) {
                    //下载图片
                    //获取图片后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，重命名图片
                    String picName = UUID.randomUUID().toString() + extName;
                    //下载图片
                    //声明OutputStream
                    OutputStream outputStream = new FileOutputStream(new File("F:\\javatest\\img\\" + picName));
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 设置请求信息
     *
     * @return
     */
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10 * 1000)    //创建连接最长时间
                .setConnectionRequestTimeout(5 * 1000)   //获取连接的最长时间
                .setSocketTimeout(50 * 1000)  //数据传输的最长时间
                .build();
        return config;
    }


}
