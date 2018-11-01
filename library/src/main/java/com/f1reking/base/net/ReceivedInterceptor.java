package com.f1reking.base.net;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author: F1ReKing
 * @date: 2017/12/7 10:14
 * @desc: 响应拦截器
 */

public class ReceivedInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.fromIterable(originalResponse.headers("Set-Cookie")).map(
                new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Exception {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    }
                }).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object s) throws Exception {
                    RetrofitRequestTool.addHeader("Cookie", (String) s);
                }
            });
        }
        return originalResponse;
    }
}
