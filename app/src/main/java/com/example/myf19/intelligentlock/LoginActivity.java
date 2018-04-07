package com.example.myf19.intelligentlock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myf19.intelligentlock.model.LoginResult;
import com.example.myf19.intelligentlock.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseReceiverActivity implements View.OnClickListener {
    private Handler handler;
    private EditText username;
    private EditText password;
    private Button btn_login;
    LoginResult m_result;
    Map<String, String> map = new HashMap<String, String>();

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //处理登录成功消息
        handler  = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 123:
                        try
                        {
                            //获取用户登录的结果
                            LoginResult result = (LoginResult)msg.obj;
                            String userName = result.getNicename();

                            Toast.makeText(LoginActivity.this, "您成功登录",Toast.LENGTH_SHORT).show();

                            //跳转到登录成功的界面
                            Intent intent = new Intent(LoginActivity.this, SelfIntroActivity.class);
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            }
        };

        username = (EditText) findViewById(R.id.edit_account);
        password = (EditText) findViewById(R.id.edit_password);
        btn_login = (Button) findViewById(R.id.button_login);

        btn_login.setOnClickListener(this);

    }

    private LoginResult parseJSONWithGson(String jsonData)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, LoginResult.class);
    }
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.button_login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                             /* 老方法 */
//                            /* 使用FormBody传递键值对 */
//                            RequestBody formBody = new FormBody.Builder()
//                                    .add("account", account)
//                                    .add("password",password)
//                                    .build();
//
//                            /* 网址未定 */
//                            String host = "http://rlsxsb.market.alicloudapi.com";
//                            String path = "/face/attribute";
//                            String appcode = "a3d794449e5b43b09daf823623e9f0e7";
//                            String url = host + path;
//
//                            // Http Code
//                            OkHttpClient client = new OkHttpClient.Builder()
//                                    .connectTimeout(10, TimeUnit.SECONDS)
//                                    .writeTimeout(10, TimeUnit.SECONDS)
//                                    .readTimeout(30, TimeUnit.SECONDS)
//                                    .build();
//                            Request request = new Request.Builder()
//                                    .addHeader("Authorization", "APPCODE"+ appcode)
//                                    .url(url)
//                                    .post(formBody)
//                                    .build();
//                            Response response = client.newCall(request).execute();
//                            int resonseCode = response.code();
//                            String responseData = response.body().string();
//                            Log.d("ResponseCode", String.valueOf(resonseCode));
//                            Log.d("Result", responseData);

                            //POST信息中加入用户名和密码
                            /* 网址未定 */
                            String host = "http://rlsxsb.market.alicloudapi.com";
                            String path = "/face/attribute";
                            String appcode = "a3d794449e5b43b09daf823623e9f0e7";
                            String url = host + path;

                            map.put("uid", username.getText().toString().trim());
                            map.put("pwd", password.getText().toString().trim());
                            //HttpUtils.httpPostMethod(url, json, handler);
                            HttpUtils.post(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("DaiDai", "OnFaile:",e);
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseBody = response.body().string();
                                    m_result = parseJSONWithGson(responseBody);
                                    //发送登录成功的消息
                                    Message msg = handler.obtainMessage();
                                    msg.what = 123;
                                    msg.obj = m_result; //把登录结果也发送过去
                                    handler.sendMessage(msg);
                                }
                            }, map);
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
//            case R.id.btn_register:
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                break;
        }
    }
}
