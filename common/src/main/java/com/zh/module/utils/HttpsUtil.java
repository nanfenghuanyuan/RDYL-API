package com.zh.module.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class HttpsUtil {
    /**
     * 请求超时时间
     */
    private static final int TIME_OUT = 120000;

    /**
     * Https请求
     */
    private static final String HTTPS = "https";

    /**
     * 返回成功状态码
     */
    private static final int OK = 200;

    /**
     * 纯字符串参数post请求
     *
     * @param url 请求URL地址
     * @param paramMap 请求字符串参数集合
     * @return 服务器返回内容
     * @throws Exception
     */
    public static String post(String url, Map<String, String> paramMap) throws Exception {
        Response response = doPostRequest(url, paramMap, null);
        return response.body();
    }

    /**
     * 执行post请求
     *
     * @param url 请求URL地址
     * @param paramMap 请求字符串参数集合
     * @param fileMap 请求文件参数集合
     * @return 服务器相应对象
     * @throws Exception
     */
    public static Response doPostRequest(String url, Map<String, String> paramMap, Map<String, File> fileMap) throws Exception {
        if (null == url || url.isEmpty()) {
            throw new Exception("The request URL is blank.");
        }

        // 如果是Https请求
        if (url.startsWith(HTTPS)) {
            getTrust();
        }
        Connection connection = Jsoup.connect(url);
        //Connection connection = Jsoup.connect(url).url(new URL(null, url, new sun.net.www.protocol.https.Handler()));//可用
        connection.method(Connection.Method.POST);
        connection.timeout(TIME_OUT);
        connection.header("Content-Type", "application/json");
        connection.ignoreHttpErrors(true);
        connection.ignoreContentType(true);

        // 添加字符串类参数
        if (null != paramMap && !paramMap.isEmpty()) {
            connection.data(paramMap);
        }

        // 添加文件参数
        if (null != fileMap && !fileMap.isEmpty()) {
            InputStream in = null;
            File file = null;
            Set<Entry<String, File>> set = fileMap.entrySet();
            try {
                for (Entry<String, File> e : set) {
                    file = e.getValue();
                    in = new FileInputStream(file);
                    connection.data(e.getKey(), file.getName(), in);
                }
            } catch (FileNotFoundException e) {
                throw new Exception(e.getMessage());
            }
        }

        try {
            Response response = connection.execute();
            if (response.statusCode() != OK) {
                throw new Exception(response.statusMessage());
            }
            return response;
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取服务器信任
     */
    private static void getTrust() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * post方式访问
     * @param url 路径
     * @param map 参数
     * @return
     */
    public static String httpsPost(String url, Map<String, String> map) {

        String charset = "UTF-8";
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;

        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-Type", "application/json");
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * get方式访问（如果有参数直接 ?xx&yy&zz 的方式即可）
     * @param url
     * @return
     */
    public static String httpsGet(String url) {

        String charset = "UTF-8";
        HttpClient httpClient = null;
        HttpGet httpGet= null;
        String result = null;

        try {
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

class SSLClient extends DefaultHttpClient {
    //用于进行Https请求的HttpClient
    public SSLClient() throws Exception {
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException { }

            public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException { }

            public X509Certificate[] getAcceptedIssuers() {return null; }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }
}
