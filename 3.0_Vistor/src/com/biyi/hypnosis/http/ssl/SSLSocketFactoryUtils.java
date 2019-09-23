package com.biyi.hypnosis.http.ssl;

import android.content.Context;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLSocketFactoryUtils {

    /* * 默认信任所有的证书 * todo 最好加上证书认证，主流App都有自己的证书 * */
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }

    //信任指定的证书
    public static SSLSocketFactory getSSLSocketFactory(Context context, ArrayList certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (int i = 0; i < certificates.size(); i++) {
                InputStream certificate = context.getResources().openRawResource((Integer) certificates.get(i));
                X509Certificate certificate1 = (X509Certificate) certificateFactory.generateCertificate(certificate);
                keyStore.setCertificateEntry(String.valueOf(i), certificate1);
                if (certificate != null) {
                    certificate.close();
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, new TrustManager[]{getX509TrustManager(trustManagerFactory)}
                    /* trustManagerFactory.getTrustManagers()*/, new SecureRandom());

            return sslContext.getSocketFactory();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return createSSLSocketFactory();
    }

    public static X509TrustManager getLocalManager(Context context) {
        if (context == null) {
            return null;
        }
        TrustManagerFactory trustManagerFactory = null;
        try {
            X509Certificate byodRootCert = generateCertFromLocalFile(context);
            // 设置算法
            trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            // 设置类型
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 不加载外部证书
            keyStore.load(null);
            // 加载内置的根证书
            if (byodRootCert != null) {
                keyStore.setCertificateEntry("root_ca", byodRootCert);
            }
            // 加载用户已经信任的自签名证书
//		CertificationUtils.loadKeyStore(context, keyStore);
            trustManagerFactory.init(keyStore);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getX509TrustManager(trustManagerFactory);
    }
    /**
     * 获取内置的根证书
     */
    private static X509Certificate generateCertFromLocalFile(Context context)
            throws CertificateException {
        X509Certificate innerCert = null;
       
        return innerCert;
    }

    private static X509TrustManager getX509TrustManager(
            TrustManagerFactory trustManagerFactory) {
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    public static X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {
            tm =   new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意客户端证书
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意服务端证书

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        } catch (Exception e) {

        }
        return tm;
    }
    public  static  class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return  true;
        }
    }
}
