package com.example.myf19.intelligentlock.utils;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by myf19 on 2018/4/11.
 */

public class MyHttpUtil {
    byte[] str;
    public byte[] httpGet(String url){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        Request request=builder.get().url(url).build();
        Call call=okHttpClient.newCall(request);
        //同步GET请求
        try {
            Response response=call.execute();
            str= response.body().bytes();
        }catch (Exception e){
            e.printStackTrace();
        }

        return str;
    }
}
