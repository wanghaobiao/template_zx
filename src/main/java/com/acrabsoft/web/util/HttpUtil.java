package com.acrabsoft.web.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;

/**
 * Httpclient工具类
 * @author wanghb
 * @date 2019-04-22
 */
public class HttpUtil {

    private static final int BYTE_LEN = 102400; // 100KB
    private static final String CHARSET = "UTF-8";  // 编码格式




    public static String RequestMethod_GET = "GET";
    public static String RequestMethod_DELETE = "DELETE";
    public static String RequestMethod_POST = "POST";
    public static String RequestMethod_PUT = "PUT";

    /**
     * 发送delete请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Map<String, Object> sendByMethod(String url, Map<String, Object> params,String requestMethod) {
        java.util.Date data = new java.util.Date();
        String timestamp = String.valueOf(data.getTime()/1000);//时间戳（精确到秒）
        String signature = "*********";

        //定义stringbuffer  方便后面读取网页返回字节流信息时的字符串拼接
        StringBuffer buffer = new StringBuffer();

        //创建url_connection
        URLConnection http_url_connection = null;
        try {
            http_url_connection = (new URL(url)).openConnection();
            //将urlconnection类强转为httpurlconnection类
            HttpURLConnection HttpURLConnection = (java.net.HttpURLConnection)http_url_connection;

            HttpURLConnection.setDoInput(true);
            HttpURLConnection.setDoOutput(true);

            HttpURLConnection.setRequestMethod(requestMethod);//设置请求方式。可以是delete put post get
            HttpURLConnection.setRequestProperty("Content-Length", String.valueOf(JSON.toJSONString( params ).getBytes().length));//设置内容的长度
            HttpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置编码格式
            HttpURLConnection.setRequestProperty("accept", "application/json");//设置接收返回参数格式

            HttpURLConnection.setRequestProperty("authkey","************");
            HttpURLConnection.setRequestProperty("signature",signature);
            HttpURLConnection.setRequestProperty("timestamp",timestamp);

            HttpURLConnection.setUseCaches(false);

            // write request.
            BufferedOutputStream output_stream = new BufferedOutputStream(HttpURLConnection.getOutputStream());
            output_stream.write(JSON.toJSONString( params ).getBytes());
            output_stream.flush();
            output_stream.close();
            output_stream = null;
            InputStreamReader input_stream_reader = new InputStreamReader(HttpURLConnection.getInputStream(), "utf-8");
            BufferedReader buffered_reader = new BufferedReader(input_stream_reader);
            buffer = new StringBuffer();
            String line;
            while ((line = buffered_reader.readLine()) != null) {
                buffer.append(line);
            }
            line=null;
            input_stream_reader.close();
            input_stream_reader = null;
            buffered_reader.close();
            buffered_reader = null;
            //  http_url_connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (Map)JSON.parse(buffer.toString());
    }

    /**
     * @description  get请求
     * @param  url  请求地址（get请求时参数自己组装到url上）
     * @param  params  url
     * @return  响应文本
     * @date  20/07/10 16:33
     * @author  wanghb
     * @edit
     */
    public static String get(String url,Map<String, String> params,Integer connectTimeout) throws IOException {
        if(params != null && params.size() > 0){
            url += "?"+getUrlParamsByMap( params );
        }
        //logger.info("get请求路径===>"+url);
        //logger.info("get请求参数===>"+JSON.toJSONString( params ));
        // 请求地址，以及参数设置
        HttpGet get = new HttpGet(url);
        if(connectTimeout != null){
            connectTimeout = connectTimeout * 1000;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).build();
            get.setConfig(requestConfig);
        }
        // 执行请求，获取相应
        String result = getRespString( get);
        //Map<String, Object> resultMaps = (Map) JSON.parse(result);
        //logger.info("get返回结果===>"+resultMaps);
        return result;
    }

    /**
     * @description  get请求
     * @param  url  请求地址（get请求时参数自己组装到url上）
     * @param  params  url
     * @return  响应文本
     * @date  20/07/10 16:33
     * @author  wanghb
     * @edit
     */
    public static String get(String url,Map<String, String> params)throws IOException{
        if(params != null && params.size() > 0){
            url += "?"+getUrlParamsByMap( params );
        }
        System.out.println("get请求路径===>"+url);
        // 请求地址，以及参数设置
        HttpGet get = new HttpGet(url);
        // 执行请求，获取相应
        String result = getRespString( get);
        //Map<String, Object> resultMaps = (Map) JSON.parse(result);
        //System.out.println("get返回结果===>"+resultMaps);
        return result;
    }


    /**
     * @description  post请求
     * @param  urlPath  url地址
     * @param  params  参数
     * @return  返回结果
     * @date  20/07/10 16:35
     * @author  wanghb
     * @edit
     */
    public static Map<String, Object> post(String urlPath,Object params) throws IOException {
        Boolean isPrintln = false;
        if(isPrintln){
            System.out.println("post请求路径===>"+urlPath);
            System.out.println("post请求参数===>"+JSON.toJSONString( params ));
        }
        String Json = JSON.toJSONString( params );
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("accept", "application/json");
            byte[] writebytes = Json.getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            OutputStream outwritestream = conn.getOutputStream();
            outwritestream.write(Json.getBytes());
            outwritestream.flush();
            outwritestream.close();
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader( conn.getInputStream()));
                while (reader.ready()){
                    result += reader.readLine();
                }
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, Object> resultMaps = (Map)JSON.parse(result);
        if(isPrintln) {
            System.out.println( "post返回结果===>" + resultMaps );
        }
        return resultMaps;
    }

    public static String postFile(String url,InputStream is,String name,Map<String,String> params){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            if(params != null){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    meb.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.create(
                            "text/plain", Consts.UTF_8)));
                }
            }
            // 把文件转换成流对象FileBody
            InputStreamBody isb = new InputStreamBody(is, name);
            HttpEntity reqEntity = null;
            if(is != null){
                reqEntity = meb
                        // 相当于<input type="file" name="file"/>
                        //                    .addPart("file", bin)
                        .addPart("file", isb)
                        /* // 相当于<input type="text" name="userName" value=userName>
                         .addPart("userName", userName)
                         .addPart("pass", password)*/
                        .build();
            }else{
                reqEntity = meb.build();
            }
            httpPost.setEntity(reqEntity);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(5000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);


            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            String res = "";
            if (resEntity != null) {
                res = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
            }

            // 销毁
            EntityUtils.consume(resEntity);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }finally {
            try {
                if(response != null){
                    response.close();
                }
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * @description  将map转换成url
     * @param  map  参数
     * @return  返回结果
     * @date  20/07/10 16:33
     * @author  wanghb
     * @edit
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * @description  获取响应信息（String）
     * @param  request
     * @return  返回结果
     * @date  20/07/10 16:34
     * @author  wanghb
     * @edit
     */
    public static String getRespString(HttpUriRequest request) throws IOException {
        // 获取响应流
        InputStream in = getRespInputStream(request);
        if (in == null){
            return "";
        }
        // 流转字符串
        StringBuffer sb = new StringBuffer();
        byte[]b = new byte[BYTE_LEN];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            sb.append(new String(b, 0, len, CHARSET));
        }
        return sb.toString();
    }


    /**
     * @description  获取响应信息（InputStream）
     * @param  request
     * @return  返回结果
     * @date  20/07/10 16:34
     * @author  wanghb
     * @edit
     */
    public static InputStream getRespInputStream(HttpUriRequest request) throws IOException {
        // 获取响应对象
        HttpResponse response = null;
        response = HttpClients.createDefault().execute(request);
        if (response == null) {
            return null;
        }
        // 获取Entity对象
        HttpEntity entity = response.getEntity();
        // 获取响应信息流
        InputStream in = null;
        if (entity != null) {
            in =  entity.getContent();
        }
        return in;
    }

    /**
     * @description  获取本地ip
     * @return  ip
     * @date  20/07/30 18:30
     * @author  wanghb
     * @edit
     */
    public static String getHostIp() throws SocketException {
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip != null
                        && ip instanceof Inet4Address
                        && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                        && ip.getHostAddress().indexOf(":")==-1){
                    return ip.getHostAddress();
                }
            }
        }
        return null;
    }


}
