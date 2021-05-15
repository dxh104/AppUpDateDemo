package com.example.app_baselibrary;


import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHelper {

    private static ApiHelper apiHelper;
    private ApiService apiService;

    public static ApiHelper getInstance() {
        return apiHelper == null ? apiHelper = new ApiHelper() : apiHelper;
    }

    private ApiHelper() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException { // 拦截器
                        Request request = chain.request()
                                .newBuilder()
//                                .addHeader("Content-Type", "application/json")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                                .addHeader("Accept", "application/json")
                                .build();
                        Response response = chain.proceed(request);
                        ResponseBody responseBody = response.body();
                        long contentLength = responseBody.contentLength();
                        if (!bodyEncoded(response.headers())) {//检查body编码方式
                            BufferedSource source = responseBody.source();
                            source.request(Long.MAX_VALUE); // Buffer the entire body.
                            Buffer buffer = source.buffer();

                            Charset charset = UTF8;
                            MediaType contentType = responseBody.contentType();
                            if (contentType != null) {
                                try {
                                    charset = contentType.charset(UTF8);
                                } catch (UnsupportedCharsetException e) {
                                    return response;
                                }
                            }

                            if (!isPlaintext(buffer)) {
                                return response;
                            }

                            if (contentLength != 0) {
                                String result = buffer.clone().readString(charset);
                                Log.e("http---------->", " response:" + result);
                            }
                        }
                        return response;
                    }
                })
                .build();

        // 初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // json解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())) // 支持RxJava
                .client(httpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getRetrofitService() {
        return apiService;
    }

    //检查body编码方式
    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");


    // 是否为纯文本
    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


}
