package com.example.quiz_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class quiz_home extends AppCompatActivity {
    MediaPlayer btn_sound;
    TextView timer;
    ImageButton back_btn;
    ViewPager viewPager;
    ArrayList<Question> question=new ArrayList<>();
    DatabaseReference database;
    Adapter_Viewpager adapter;
   // ArrayList<String> option1=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);
        btn_sound=MediaPlayer.create(getApplicationContext(),R.raw.btn_sound);
        back_btn=findViewById(R.id.back_btn);
        viewPager=findViewById(R.id.viewpager);
        timer=findViewById(R.id.timer);
        database= FirebaseDatabase.getInstance().getReference("Question");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot myshot:snapshot.getChildren()){
                    Question q=myshot.getValue(Question.class);
                    //Log.d("check","question"+q.getQuestion());
                    question.add(q);
                }
                adapter=new Adapter_Viewpager(getApplicationContext(),question);
                viewPager.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Adapter_Viewpager adapter_viewpager=new Adapter_Viewpager(quiz_home.this,question);
        viewPager.setAdapter(adapter_viewpager);




    }
}