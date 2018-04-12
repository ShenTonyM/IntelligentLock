package com.example.myf19.intelligentlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myf19.intelligentlock.utils.MyHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Member> memberList = new ArrayList<Member>();
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);

//        Intent intent1 = getIntent();
//        String username = intent1.getStringExtra("username");

//        initFruits(); // 初始化水果数据

        MemberAdapter adapter = new MemberAdapter(UserGroupActivity.this, R.layout.user_item, memberList);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // 点击图标的时候会有反应
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Member member = memberList.get(position);
                Toast.makeText(UserGroupActivity.this, member.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        Button button_show = (Button) findViewById(R.id.button_showMembers);
        button_show.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sendRequestMembers();
                MemberAdapter adapter = new MemberAdapter(UserGroupActivity.this, R.layout.user_item, memberList);
                listView.setAdapter(adapter);
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

//            Intent intent1 = getIntent();
//            String username = intent1.getStringExtra("username");
            /*  这里具体的http内容未定  要和服务器端沟通 */
            try {
                // formbody的数据
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                formBody.add("username","123");//传递键值对参数

                String url = "http://101.132.165.232:8000/list";

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
                String responseData = response.body().string();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();

        // User的图片是url, Member的图片是byte[]
        List<User> appList = gson.fromJson(jsonData, new TypeToken<List<User>>() {}.getType());
        for (User user : appList) {
            final String userName = user.getName();
            boolean flag = true;
            for (User olduser: userList){
                if(olduser.getName().equals(userName)){
                    flag = false;
                    break;
                }
            }
            if (flag){
                userList.add(user);

                // urlImage是一个图片的url
                final String urlImage = user.getImage();

                // imgByte是二维图片
                final byte[] imgByte;

                MyHttpUtil httpUtil = new MyHttpUtil();
                imgByte = httpUtil.httpGet(urlImage);

                Member member = new Member(userName,imgByte);
                memberList.add(member);
            }
        }
    }
}
