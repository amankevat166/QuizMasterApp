package com.example.quiz_master;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton stat_btn,rule_btn,logout_btn;
    AppCompatButton play;
    MediaPlayer btn_sound;
    
    FirebaseAuth auth;
    private DatabaseReference database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth= FirebaseAuth.getInstance();
        stat_btn=findViewById(R.id.stat_btn);
        rule_btn=findViewById(R.id.rule_btn);
        logout_btn=findViewById(R.id.logout_btn);
        play=findViewById(R.id.play);

        //button sound method
        btn_sound=MediaPlayer.create(getApplicationContext(),R.raw.btn_sound);
        //set questions in object
        database= FirebaseDatabase.getInstance().getReference("Question");
        Question q1=new Question();
        q1.setQuestion("Bhagavad Gita is found in which chapter of the Mahabharata?");
        q1.setOption1("Virata Parva");
        q1.setOption2("Udyoga Prava");
        q1.setOption3("Bhishma Parva");
        q1.setOption4("Drona Parva");
        q1.setCorrect_answer("Bhishma Parva");
        Question q2=new Question();
        q2.setQuestion("What is beyond five sense organs?");
        q2.setOption1("Manifest");

        q2.setOption2("Unmanifest");
        q2.setOption3("Anger");
        q2.setOption4("Desire");
        q2.setCorrect_answer("Unmanifest");
        Question q3=new Question();
        q3.setQuestion("Who is Partha?");
        q3.setOption1("Yudhishtira");
        q3.setOption2("Bhima");
        q3.setOption3("Sri Krishna");
        q3.setOption4("Arjuna");
        q3.setCorrect_answer("Arjuna");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sound.start();
               // AddData(q1);
               // AddData(q2);
               // AddData(q3);

               Intent intent=new Intent(MainActivity.this, quiz_home.class);
                startActivity(intent);


            }
        });
        //dialogue box
        Dialog d=new Dialog(this);
        d.setContentView(R.layout.stats_dialogue);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.55);
        d.getWindow().setLayout(width, height);
        stat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView total_point=d.findViewById(R.id.total_point);
                TextView right_ans=d.findViewById(R.id.ans_right);
                TextView wrong_ans=d.findViewById(R.id.ans_wrong);
                SharedPreferences sharedPreferences = getSharedPreferences("QuizAppState", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int totalPoints = sharedPreferences.getInt("total_points", 0);
                //int points=+totalPoints;
                total_point.setText(String.valueOf(totalPoints));
                int rightans = sharedPreferences.getInt("right_ans", 0);
                right_ans.setText(String.valueOf(rightans));
                int wrongans = sharedPreferences.getInt("wrong_ans", 0);
                wrong_ans.setText(String.valueOf(wrongans));
                d.show();
                btn_sound.start();
            }
        });
        //making array adapter

        //dialogue box
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.rules_dialogue);
        dialog.getWindow().setLayout(width, height);
        rule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                btn_sound.start();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sound.start();
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to logout")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser currentUser = auth.getCurrentUser();
                                auth.signOut();
                                startActivity(new Intent(getApplicationContext(), login.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });


    }
    public void AddData(Question question){
        database.push().setValue(question);
    }
}