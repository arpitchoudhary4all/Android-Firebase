package com.example.owner.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    private EditText Resetmail;
    private Button Resetbtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        Resetmail = (EditText) findViewById(R.id.reset);
        Resetbtn = (Button) findViewById(R.id.resetPass);
        firebaseAuth = FirebaseAuth.getInstance();

        Resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail =Resetmail.getText().toString().trim();
                if(mail.equals("")){
                    Toast.makeText(Reset.this,"Please Enter Ur Registered Email",Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Reset.this,"Password Mail Sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Reset.this,MainActivity.class));
                            }
                            else {
                                Toast.makeText(Reset.this,"Error Sending Mail !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}
