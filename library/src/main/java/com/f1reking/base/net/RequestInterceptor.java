package com.f1reking.base.net;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: F1ReKing
 * @date: 2017/11/14 10:22
 * @desc: 请求拦截器
 */

public class RequestInterceptor implements Interceptor {

    public static final String TOKEN = "key";

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();

        //添加头部cookie等
        //Map<String,String> headerMap =



        return null;
    }
}
