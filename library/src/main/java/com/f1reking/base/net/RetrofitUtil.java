package com.f1reking.base.net;

import com.f1reking.base.BuildConfig;
import com.f1reking.base.net.fastjson.FastJsonConverterFactory;
import com.f1reking.base.util.LLog;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author: F1ReKing
 * @date: 2017/12/7 10:27
 * @desc:
 */

public class RetrofitUtil {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 15;

    public RetrofitUtil() {
    }

    public static <T> T getService(Class<T> tClass, String host) {
        OkHttpClient.Builder builder =
            new OkHttpClient.Builder().addInterceptor(new ReceivedInterceptor())
                .addInterceptor(new RequestInterceptor());

        //设置日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String content) {
                        LLog.json(content);
                    }
                });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

        //https配置
        SSLSocketFactory sslSocketFactory = HttpsUtil.getInstance().setCertificates();
        if (sslSocketFactory != null) {
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return hostname.equals(session.getPeerHost());
                }
            })
                .sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                });
        }

        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit =
            new Retrofit.Builder().baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(
                    FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(tClass);
    }

    /**
     * Retrofit上传file
     *
     * @param key 参数
     * @param file 上传的文件
     */
    public static MultipartBody.Part getRequestPart(String key, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
            MultipartBody.Part.createFormData(key, file.getName(), requestBody);
        return body;
    }

}
