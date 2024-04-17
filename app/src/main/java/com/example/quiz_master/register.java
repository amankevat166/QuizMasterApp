package com.example.quiz_master;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class register extends AppCompatActivity {
    TextView signup,dont;
    TextInputEditText edit_name,email_edit,pass_edit;
    FirebaseAuth auth;
     //String email,pass;

    //method for validation
    private boolean validInput(String name,String email,String Password){

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

        if (TextUtils.isEmpty(name)) {
            edit_name.setError("Name is required.");
            return false;
        }
        return true;

    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth= FirebaseAuth.getInstance();
        signup=findViewById(R.id.signup_txt);
        dont=findViewById(R.id.dont);
        edit_name=findViewById(R.id.name);
        email_edit=findViewById(R.id.email);
        pass_edit=findViewById(R.id.pass);

        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,email,password;
                name=edit_name.getText().toString();
                email=email_edit.getText().toString();
                password=pass_edit.getText().toString();
                if(validInput(name,email,password)){

                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(),login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                        }
                }


        });

    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}