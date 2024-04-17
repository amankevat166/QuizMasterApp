package com.example.quiz_master;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_Viewpager extends PagerAdapter {
    Context context;
    ArrayList<Question> question;
    CountDownTimer countDownTimer;
    int initialtime=31000;
    int interval=1000;
    LayoutInflater layoutInflater;
    int counter=0;
    int last=2;
    int total = 0;
    int temp=0,tempw=0;
    MediaPlayer correct,incorrect;
    DatabaseReference database;
    FirebaseAuth auth;
    private String userid,nodeid;

    public Adapter_Viewpager(Context context, ArrayList<Question> question){
        this.context = context;
        this.question=question;
    }


    @Override
    public int getCount() {
        return question.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Question q=question.get(counter);
       layoutInflater= LayoutInflater.from(context);
       database=FirebaseDatabase.getInstance().getReference("States");
       userid=FirebaseAuth.getInstance().getUid();



        View view=layoutInflater.inflate(R.layout.question_page,container,false);
        correct=MediaPlayer.create(context.getApplicationContext(), R.raw.correct_sound);
        incorrect=MediaPlayer.create(context.getApplicationContext(), R.raw.wrong_sound);
        TextView quest=view.findViewById(R.id.quest);
        AppCompatButton option1=view.findViewById(R.id.option1);
        AppCompatButton option2=view.findViewById(R.id.option2);
        AppCompatButton option3=view.findViewById(R.id.option3);
        AppCompatButton option4=view.findViewById(R.id.option4);
        ImageButton back_btn=view.findViewById(R.id.back_btn);
        TextView point=view.findViewById(R.id.point);
        ImageButton lf1=view.findViewById(R.id.lf1);
        ImageButton lf2=view.findViewById(R.id.lf2);
        ImageButton lf3=view.findViewById(R.id.lf3);
        TextView timer=view.findViewById(R.id.timer);

        quest.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
        container.addView(view);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn_sound.start();
                Intent intent=new Intent(context.getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        countDownTimer= new CountDownTimer(initialtime, interval) {
            @Override
            public void onTick(long l) {
               timer.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                countDownTimer.start();
            }
        };
        countDownTimer.start();

        //option1 set on clicklistener
            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question q1=question.get(counter);
                    checkans(q1.getOption1(),q1.getCorrect_answer(),option1,point,userid);
                    btn_disable(option1,option2,option3,option4);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(counter==2){
                                intent();
                            }
                            else {
                                counter++;

                                Question q2 = question.get(counter);
                                setcontent(q2, quest, option1, option2, option3, option4);
                                btn_enable(option1, option2, option3, option4);
                                int color = ContextCompat.getColor(context, R.color.white);
                                option1.setBackgroundColor(color);
                                option2.setBackgroundColor(color);
                                option3.setBackgroundColor(color);
                                option4.setBackgroundColor(color);

                                //timer
                               counttimer(timer,quest,option1,option2,option3,option4);
                            }
                        }
                    },2000);

                }
            });

        //option 2 set on click
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q1=question.get(counter);
                checkans(q1.getOption2(),q1.getCorrect_answer(),option2,point,userid);
                btn_disable(option1,option2,option3,option4);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (counter == 2) {
                            intent();
                        } else {
                            counter++;
                            Question q2 = question.get(counter);

                            setcontent(q2, quest, option1, option2, option3, option4);
                            btn_enable(option1, option2, option3, option4);
                            int color = ContextCompat.getColor(context, R.color.white);
                            option1.setBackgroundColor(color);
                            option2.setBackgroundColor(color);
                            option3.setBackgroundColor(color);
                            option4.setBackgroundColor(color);

                            //timer

                           counttimer(timer,quest,option1,option2,option3,option4);


                        }
                    }
                },2000);

            }
        });
        //option 3 set on click
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q1=question.get(counter);
                checkans(q1.getOption3(),q1.getCorrect_answer(),option3,point,userid);
                btn_disable(option1,option2,option3,option4);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (counter == 2) {
                            intent();
                        } else {
                            counter++;
                            Question q2 = question.get(counter);
                            setcontent(q2, quest, option1, option2, option3, option4);
                            btn_enable(option1, option2, option3, option4);
                            int color = ContextCompat.getColor(context, R.color.white);
                            option1.setBackgroundColor(color);
                            option2.setBackgroundColor(color);
                            option3.setBackgroundColor(color);
                            option4.setBackgroundColor(color);
                            //timer
                           counttimer(timer,quest,option1,option2,option3,option4);
                        }
                    }
                },2000);

            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q1=question.get(counter);
                checkans(q1.getOption4(),q1.getCorrect_answer(),option4,point,userid);
                btn_disable(option1,option2,option3,option4);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (counter == 2) {
                            intent();
                        } else {
                            counter++;
                            Question q2 = question.get(counter);
                            setcontent(q2, quest, option1, option2, option3, option4);
                            btn_enable(option1, option2, option3, option4);
                            int color = ContextCompat.getColor(context, R.color.white);
                            option1.setBackgroundColor(color);
                            option2.setBackgroundColor(color);
                            option3.setBackgroundColor(color);
                            option4.setBackgroundColor(color);
                            //timer
                            counttimer(timer,quest,option1,option2,option3,option4);
                        }
                    }
                },2000);

            }
        });
        //skip question button
        lf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Skip question", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (counter == 2) {
                            intent();
                        } else {
                            counter++;
                            Question q2 = question.get(counter);
                            setcontent(q2, quest, option1, option2, option3, option4);
                            //timer
                            counttimer(timer,quest,option1,option2,option3,option4);
                        }
                        lf1.setEnabled(false);
                    }
                },2000);

            }
        });
        //right answer button
        lf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeline2(lf2,point,quest,option1, option2, option3, option4);

            }
        });
        lf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lifeline3(lf3,option1,option2,option3,option4);
            }
        });
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
    public void setcontent(Question q1,TextView quest,AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){
        quest.setText(q1.getQuestion());
        option1.setText(q1.getOption1());
        option2.setText(q1.getOption2());
        option3.setText(q1.getOption3());
        option4.setText(q1.getOption4());
    }
    public void counttimer(TextView timer,TextView quest,AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){

            if (countDownTimer != null) {
                countDownTimer.cancel(); // Cancel the current timer if it's running
            }
            countDownTimer= new CountDownTimer(initialtime,interval) {
                @Override
            public void onTick(long l) {
                timer.setText(""+l/1000);
            }
            @Override
            public void onFinish() {
                    countDownTimer.cancel();
                    countDownTimer.start();

                if (counter == 2) {
                    intent();
                }
                else {
                    counter++;
                    Question q2 = question.get(counter);
                    setcontent(q2, quest, option1, option2, option3, option4);
                }

            }
        };
            countDownTimer.start();

    }

    public void checkans(String ans, String c_ans,AppCompatButton option,TextView txt_point,String userid) {

        if (ans.equals(c_ans)) {
            correct.start();
            int color = ContextCompat.getColor(context, R.color.green);
            option.setBackgroundColor(color);
            int point = Integer.parseInt(txt_point.getText().toString());
            total = point + 20;
            txt_point.setText(String.valueOf(total));
            States_Model states_model=new States_Model();
            states_model.setTotal_points(Integer.parseInt(txt_point.getText().toString()));
            states_model.setUserid(userid);

            SharedPreferences sharedPreferences = context.getSharedPreferences("QuizAppState", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("total_questions", totalQuestions);

            editor.putInt("total_points", total);
            editor.apply();
//            int totalPoints = sharedPreferences.getInt("total_points", 0);
//            editor.putInt("total_points", total);
            temp++;
            int right_ans=temp;

            editor.putInt("right_ans", right_ans);
            editor.apply();

            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();

        } else {
            incorrect.start();
            int color = ContextCompat.getColor(context, R.color.red);
            option.setBackgroundColor(color);
            SharedPreferences sharedPreferences = context.getSharedPreferences("QuizAppState", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            tempw++;
            int wrong_ans=tempw;
            editor.putInt("wrong_ans", wrong_ans);
            editor.apply();
            Toast.makeText(context, "wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public void btn_enable(AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
    }
    public void btn_disable(AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }
    public void intent(){
            Intent intent=new Intent(context.getApplicationContext(),last_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
             intent.putExtra("point",total);
            context.getApplicationContext().startActivity(intent);


    }
    public void lifeline3(ImageButton lf3,AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){
        lf3.setEnabled(false);
        if(counter==0){
            option1.setEnabled(false);
            option4.setEnabled(false);
            int color = ContextCompat.getColor(context, R.color.red);
            option4.setBackgroundColor(color);
            option1.setBackgroundColor(color);
        } else if (counter==1) {
            option3.setEnabled(false);
            option1.setEnabled(false);
            int color = ContextCompat.getColor(context, R.color.red);
            option3.setBackgroundColor(color);
            option1.setBackgroundColor(color);
        }
        else if (counter==2) {
            option3.setEnabled(false);
            option2.setEnabled(false);
            int color = ContextCompat.getColor(context, R.color.red);
            option3.setBackgroundColor(color);
            option2.setBackgroundColor(color);
        }
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               option1.setEnabled(true);
               option2.setEnabled(true);
               option3.setEnabled(true);
               option4.setEnabled(true);
           }
       },3000);
    }
    public void lifeline2(ImageButton lf2,TextView txt_point,TextView quest,AppCompatButton option1,AppCompatButton option2,AppCompatButton option3,AppCompatButton option4){
        lf2.setEnabled(false);
        int col = ContextCompat.getColor(context, R.color.black);
        lf2.setBackgroundColor(col);
        if(counter==0){
            int color = ContextCompat.getColor(context, R.color.green);
            option3.setBackgroundColor(color);
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            correct.start();
        } else if (counter==1) {
            int color = ContextCompat.getColor(context, R.color.green);
            option2.setBackgroundColor(color);
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            correct.start();

        }
        else if (counter==2) {
            int color = ContextCompat.getColor(context, R.color.green);
            option4.setBackgroundColor(color);
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            correct.start();
            intent();
        }
        int point = Integer.parseInt(txt_point.getText().toString());
        total = point + 20;
        txt_point.setText(String.valueOf(total));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);
                counter++;
                Question q2 = question.get(counter);
                setcontent(q2, quest, option1, option2, option3, option4);
                int color = ContextCompat.getColor(context, R.color.white);
                option1.setBackgroundColor(color);
                option2.setBackgroundColor(color);
                option3.setBackgroundColor(color);
                option4.setBackgroundColor(color);

            }
        },2000);

    }


}
