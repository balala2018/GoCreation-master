package com.gc.okhttp;


import com.gc.okhttp.https.HttpsUtils;
import com.gc.okhttp.response.CommonJsonCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 大白 on 2018/4/26.
 * @function 请求的发送Post/get，请求相关参数的配置功能，https的支持。
 * CommonRequest,CommonJsonCallback
 */

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;
    //为我们的client去配置参数了
    static {
        //创建我们client对象的构建者
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //十位构建者填充超时时间
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        okHttpBuilder.followRedirects(true);

        //https支持
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        //生成我们的client对象
        mOkHttpClient = okHttpBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 发送具体的http/https请求
     * @param request
     * @param commCallback
     * @return Call
     */


    public static Call sendRequest(Request request, CommonJsonCallback commCallback){
        Call call =new mOkHttpClient.newCall(request);
        call.enqueue(commCallback);
        return call;

    }
}