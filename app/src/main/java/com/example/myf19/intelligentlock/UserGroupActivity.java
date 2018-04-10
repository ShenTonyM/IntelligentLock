package com.example.myf19.intelligentlock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Member> memberList = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);

        Intent intent1 = getIntent();
        String username = intent1.getStringExtra("username");

//        initFruits(); // 初始化水果数据
        Button button_show = (Button) findViewById(R.id.button_showMembers);
        button_show.setOnClickListener(this);

        MemberAdapter adapter = new MemberAdapter(UserGroupActivity.this, R.layout.user_item, memberList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // 点击图标的时候会有
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Member member = memberList.get(position);
                Toast.makeText(UserGroupActivity.this, member.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void initFruits() {
//        Member apple = new Member("Apple", R.drawable.apple_pic);
//        memberList.add(apple);
//        Member banana = new Member("Banana", R.drawable.banana_pic);
//        memberList.add(banana);
//        Member orange = new Member("Orange", R.drawable.orange_pic);
//        memberList.add(orange);
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_showMembers) {
            sendRequestMembers();
        }
    }

    private void sendRequestMembers() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Intent intent1 = getIntent();
                String username = intent1.getStringExtra("username");
                /*  这里具体的http内容未定  要和服务器端沟通 */
                try {
                    // formbody的数据
                    FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                    formBody.add("username",username);//传递键值对参数

                    String url = "http://101.132.165.232:8000/maoshen/members";

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
                    final String responseData = response.body().string();
                    Log.d("ResponseCode", String.valueOf(resonseCode));
                    Log.d("Result", responseData);

                    parseJSONWithGSON(responseData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 在这里进行UI操作，将结果显示到界面上
                            Toast.makeText(UserGroupActivity.this, String.valueOf(resonseCode), Toast.LENGTH_SHORT).show();
                        }
                    });

//                    showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<Member> appList = gson.fromJson(jsonData, new TypeToken<List<Member>>() {}.getType());
        for (Member member : appList) {
//            String memberName = member.getName();
//            String memberImgString = member.getImgString();
            memberList.add(member);
        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                Log.d("Members",response);
            }
        });
    }

}
