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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseReceiverActivity implements View.OnClickListener {
    private Handler handler;
    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_register;

    String m_result;
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
                            Toast.makeText(LoginActivity.this, "Successful login",Toast.LENGTH_SHORT).show();

                            //跳转到登录成功的界面
                            Intent intent = new Intent(LoginActivity.this, PhotoActivity.class);
                            intent.putExtra("username", username.getText().toString().trim());
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 234:
                        Toast.makeText(LoginActivity.this, "Wrong username or password",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };

        username = (EditText) findViewById(R.id.edit_account);
        password = (EditText) findViewById(R.id.edit_password);
        btn_login = (Button) findViewById(R.id.button_login);
        btn_register = (Button)findViewById(R.id.button_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

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
                            // formbody的数据
                            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                            formBody.add("username",username.getText().toString());
                            // passward是因为田雨非打错了
                            formBody.add("passward",password.getText().toString());

                            String url = "http://101.132.165.232:8000/login";
//                            String url = "https://www.baidu.com";

                            // Http Code
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(10, TimeUnit.SECONDS)
                                    .writeTimeout(10, TimeUnit.SECONDS)
                                    .readTimeout(30, TimeUnit.SECONDS)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(formBody.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            final int resonseCode = response.code();
                            String responseBodyStr = response.body().string();

                            String resultStr = responseBodyStr;

//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // 在这里进行UI操作，将结果显示到界面上
//                                    Toast.makeText(LoginActivity.this, String.valueOf(resonseCode), Toast.LENGTH_SHORT).show();
//                                }
//                            });

                            // loging是田雨非打错了
                            if (resultStr.equals("successful loging in")) //注册成功，发送消息
                            {
                                Message msg = handler.obtainMessage();
                                msg.what = 123;
                                handler.sendMessage(msg);
                            }
                            else if(resultStr.equals("wrong password") || resultStr.equals("wrong username"))
                            {
                                Message msg = handler.obtainMessage();
                                msg.what = 234;
                                handler.sendMessage(msg);
                            }


        //                        //POST信息中加入用户名和密码
        //                        /* 网址未定 */
        //                        String url = "http://101.132.165.232:8000/login";
        //
        //                        map.put("username", username.getText().toString().trim());
        //                        map.put("password", password.getText().toString().trim());
        //                        HttpUtils.post(url, new Callback() {
        //                            @Override
        //                            public void onFailure(Call call, IOException e) {
        //                                Log.e("TAG", "NetConnect error!");
        //                            }
        //
        //                            @Override
        //                            public void onResponse(Call call, Response response) throws IOException {
        //                                String responseBodyStr = response.body().string();
        //                                JSONObject jsonData = null;
        //                                try {
        //
        //                                    jsonData = new JSONObject(responseBodyStr);
        ////                                    String resultStr = jsonData.getString("success");
        //
        //                                    String resultStr = responseBodyStr;
        //
        //                                    if (resultStr.equals("success")) //注册成功，发送消息
        //                                    {
        //                                        Message msg = handler.obtainMessage();
        //                                        msg.what = 123;
        //                                        handler.sendMessage(msg);
        //                                    }
        //                                    else //注册失败
        //                                    {
        //                                        Message msg = handler.obtainMessage();
        //                                        msg.what = 234;
        //                                        handler.sendMessage(msg);
        //                                    }
        //                                } catch (JSONException e) {
        //                                    e.printStackTrace();
        //                                }
        //                            }
        //                        }, map);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.button_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
