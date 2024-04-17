package com.example.quiz_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView login,dont;
    FirebaseAuth auth;
    TextInputEditText email_edit,pass_edit;

    private boolean validInput(String email,String Password){

        if (TextUtils.isEmpty(email)) {
            email_edit.setError("Email is required.");
            return false;
        } else if (!isValidEmail(email)) {
            email_edit.setError("Invalid email format.");
            return false;
        }
        if (TextUtils.isEmpty(Password)) {
            pass_edit.setError("Password is required.");
            return false;
        }

        return true;

    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_edit=findViewById(R.id.email);
        pass_edit=findViewById(R.id.pass);
        login=findViewById(R.id.login_txt);
        dont=findViewById(R.id.dont);
        auth= FirebaseAuth.getInstance();
        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=email_edit.getText().toString();
                password=pass_edit.getText().toString();
                if(validInput(email,password)){

                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                   else{
                                     Toast.makeText(login.this, "Your email or pass is Invalid", Toast.LENGTH_SHORT).show();
                                  }

                                }
                            });
                }
            }

        });

    }
}