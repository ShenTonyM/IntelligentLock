package com.example.myf19.intelligentlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class HTTPActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
//            sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            // Sample from OKHttp Website
            try{
                OkHttpClient client = new OkHttpClient();
                String s = "{'winCondition':'HIGH_SCORE'}";
//                JSONObject json = new JSONObject();
//                json.put("username", "Spring");
                RequestBody body = RequestBody.create(JSON, s);
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8086/get_data.xml")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                showResponse(responseData);
            }catch (Exception e) {
                e.printStackTrace();
            }

            // Sample from First Line Android
//            try {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        // 指定访问的服务器地址是电脑本机
//                        .url("http://10.0.2.2:8086/get_data.xml")
//                        .build();
//                Response response = client.newCall(request).execute();
//                String responseData = response.body().string();
////                    parseJSONWithGSON(responseData);
////                    parseJSONWithJSONObject(responseData);
////                    parseXMLWithSAX(responseData);
////                    parseXMLWithPull(responseData);
//                showResponse(responseData);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                responseText.setText(response);
            }
        });
    }
}
