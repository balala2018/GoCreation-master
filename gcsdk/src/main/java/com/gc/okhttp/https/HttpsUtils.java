package com.gc.okhttp.https;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
/**
 * Created by 大白 on 2018/4/26.
 * @function support the sslsocket
 */

public class HttpsUtils {
    /**
     * trust all the https point
     *
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory() {
        //1.生成一个信任管理器类
        X509TrustManager mTrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String outhType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String outhType) throws CertificateException {
            }
        };
        //2.创建加密上下文
        SSLContext sslContext = null;
        try {
            //与服务器保持一致的算法类型。*************************
            sslContext = SSLContext.getInstance("SSL");
            X509TrustManager[] xTrustArray = new X509TrustManager[]
                    {
                            mTrustManager
                    };
            sslContext.init(null, xTrustArray, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }
}

