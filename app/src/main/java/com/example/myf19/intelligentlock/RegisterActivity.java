package com.example.myf19.intelligentlock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myf19.intelligentlock.model.LoginResult;
import com.example.myf19.intelligentlock.utils.HttpUtils;

import org.json.JSONArray;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_empNo;
    private EditText et_pass;
    private EditText et_passConfirm;

    private Button btn_Submit;

    private int status;
    Map<String, String> map = new HashMap<String, String>();
    private Handler handler;
    private String url = "http://101.132.165.232:8000/register";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_empNo = (EditText)findViewById(R.id.et_empNo);
        et_pass = (EditText)findViewById(R.id.et_pass);
        et_passConfirm = (EditText)findViewById(R.id.et_passConfirm);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                if (msg.what==123)
                {
                    Toast.makeText(RegisterActivity.this, "Successful Registration",Toast.LENGTH_SHORT).show();
                    //跳转到登录成功的界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if (msg.what == 234)
                {
                    Toast.makeText(RegisterActivity.this, "Registration Failed",Toast.LENGTH_SHORT).show();
                }

            }
        };
        btn_Submit = (Button)findViewById(R.id.btn_submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        // formbody的数据
                        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                        formBody.add("username",et_empNo.getText().toString());
                        // passward是因为田雨非打错了
                        formBody.add("passward",et_pass.getText().toString());

                        String url = "http://101.132.165.232:8000/register";
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
                        final String responseBodyStr = response.body().string();

                        String resultStr = responseBodyStr;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 在这里进行UI操作，将结果显示到界面上
                                Toast.makeText(RegisterActivity.this, String.valueOf(resonseCode), Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegisterActivity.this, responseBodyStr, Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (resultStr.equals("yes")) //注册成功，发送消息
                        {
                            Message msg = handler.obtainMessage();
                            msg.what = 123;
                            handler.sendMessage(msg);
                        }
                        else
                        {
                            Message msg = handler.obtainMessage();
                            msg.what = 234;
                            handler.sendMessage(msg);
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


//                    map.put("username", et_empNo.getText().toString());
//                    // passward是因为田雨非
//                    map.put("passward", et_pass.getText().toString());
//
//                    HttpUtils.post(url, new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Log.e("TAG", "NetConnect error!");
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            final String responseBodyStr = response.body().string();
//                            try
//                            {
//                                //获取返回的json数据，为{"success":"success"}形式.
//                                JSONObject jsonData = new JSONObject(responseBodyStr);
//                                String resultStr = jsonData.getString("success");
//
//                                resultStr = responseBodyStr;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // 在这里进行UI操作，将结果显示到界面上
//                                        Toast.makeText(RegisterActivity.this, responseBodyStr, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                                if (resultStr.equals("yes")) //注册成功，发送消息
//                                {
//                                    Message msg = handler.obtainMessage();
//                                    msg.what = 123;
//                                    handler.sendMessage(msg);
//                                }
//                                else //注册失败
//                                {
//                                    Message msg = handler.obtainMessage();
//                                    msg.what = 234;
//                                    handler.sendMessage(msg);
//                                }
//                            }
//                            catch(JSONException e)
//                            {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, map);
                }
            }).start();
            }
        });
    }
}
