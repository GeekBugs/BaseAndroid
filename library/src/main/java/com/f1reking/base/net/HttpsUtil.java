package com.f1reking.base.net;

import com.f1reking.base.base.BaseLibApplication;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author: F1ReKing
 * @date: 2017/12/7 11:04
 * @desc:
 */

public class HttpsUtil {

    private HttpsUtil(){}

    public static HttpsUtil getInstance() {
        return HttpsUtilHolder.INSTANCE;
    }

    private static class HttpsUtilHolder {
        static final HttpsUtil INSTANCE = new HttpsUtil();
    }

    /**
     * 设置证书
     */
    public SSLSocketFactory setCertificates() {
        try {
            InputStream inputStream =
                BaseLibApplication.getContext().getAssets().open("ssl_certificate.pem");
            // 读取证书
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            // 创建一个证书库，并将证书导入证书库
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry(BaseLibApplication.getContext().getPackageName(),
                certificateFactory.generateCertificate(inputStream));
            inputStream.close();

            //创建一个信任管理器，并将证书库导入
            TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            //创建一个使用我们新人管理器的SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
