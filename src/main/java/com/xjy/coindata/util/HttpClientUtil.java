package com.xjy.coindata.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int TIME_OUT = 1000 * 20;

    private CloseableHttpClient httpClient;

    public HttpClientUtil() {
        httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    }

    public HttpClientUtil(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    //GET请求
    public Map<Object, Object> get(String url) {

        Map<Object, Object> map = null;
        HttpGet getMethod = new HttpGet(url);

        // 设置请求与数据处理的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();
        getMethod.setConfig(requestConfig);

        CloseableHttpResponse response = null;

        try {

            response = httpClient.execute(getMethod);

            HttpEntity httpentity = response.getEntity();
            if (httpentity != null) {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(EntityUtils.toString(httpentity, "UTF-8"), Map.class);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                logger.warn("httpClient.close失败");
            }
        }

        return map;
    }

    //post请求
    public String post(String url, Map<String, String> params) {

        HttpPost httppost = new HttpPost(url);

        // 设置请求与数据处理的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();
        httppost.setConfig(requestConfig);

        //放入请求参数
        List<NameValuePair> list = new ArrayList<>();
        Iterator<?> iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }

        CloseableHttpResponse response = null;
        String result = null;

        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            httppost.setEntity(entity);

            response = httpClient.execute(httppost);
            HttpEntity httpentity = response.getEntity();

            if (httpentity != null) {
                result = EntityUtils.toString(httpentity, "UTF-8");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                logger.warn("httpClient.close失败");
            }
        }

        return result;
    }

}
