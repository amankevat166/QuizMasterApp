package com.example.quiz_master;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class last_page extends AppCompatActivity {
    TextView play_again,point;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        play_again=findViewById(R.id.play_txt);
        point=findViewById(R.id.point);
        Intent intent=getIntent();
        int total_points= Integer.parseInt(String.valueOf(intent.getIntExtra("point",0)));
        point.setText(""+total_points);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}