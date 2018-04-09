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
                            Toast.makeText(LoginActivity.this, "您成功登录",Toast.LENGTH_SHORT).show();

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
                            //POST信息中加入用户名和密码
                            /* 网址未定 */
                            String url = "http://101.132.165.232:8000/maoshen/login";

                            map.put("username", username.getText().toString().trim());
                            map.put("password", password.getText().toString().trim());
                            HttpUtils.post(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("TAG", "NetConnect error!");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseBody = response.body().string();

                                    //不用例子里的LoginResult
//                                    m_result = parseJSONWithGson(responseBody);
                                    m_result = responseBody;

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
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
