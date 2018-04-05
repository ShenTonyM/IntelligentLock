package com.example.myf19.intelligentlock;

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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserGroupActivity extends AppCompatActivity implements View.OnClickListener {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private List<Member> memberList = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);
//        initFruits(); // 初始化水果数据
        Button button_show = (Button) findViewById(R.id.button_showMembers);
        button_show.setOnClickListener(this);

        MemberAdapter adapter = new MemberAdapter(UserGroupActivity.this, R.layout.user_item, memberList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
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
                /*  这里具体的http内容未定  要和服务器端沟通 */
                try {
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
                    parseJSONWithGSON(responseData);
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
