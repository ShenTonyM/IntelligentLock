package com.example.myf19.intelligentlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button button1 = (Button) findViewById(R.id.button_about);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonHttp = (Button) findViewById(R.id.buttonHTTPTest);
        buttonHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, HTTPActivity.class);
                startActivity(intent);
            }
        });

        Button buttonOtherMembers = (Button) findViewById(R.id.buttonOtherMembers);
        buttonOtherMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UserGroupActivity.class);
                startActivity(intent);
            }
        });
    }
}
