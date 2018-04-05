package com.example.myf19.intelligentlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserGroupActivity extends AppCompatActivity {

    private List<Member> memberList = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);
        initFruits(); // 初始化水果数据
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

    private void initFruits() {
        Member apple = new Member("Apple", R.drawable.apple_pic);
        memberList.add(apple);
        Member banana = new Member("Banana", R.drawable.banana_pic);
        memberList.add(banana);
        Member orange = new Member("Orange", R.drawable.orange_pic);
        memberList.add(orange);
    }
}
