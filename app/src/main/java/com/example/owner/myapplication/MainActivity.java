package com.example.owner.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView Error;
    private EditText Id,Pass;
    private Button Login,Newuser,Forgot;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Id = (EditText) findViewById(R.id.id);
        Pass = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.btnLogin);
        Newuser = (Button) findViewById(R.id.btnNewuser);
        Error = (TextView) findViewById(R.id.error);
        Forgot = (Button) findViewById(R.id.btnForgot);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,Home.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iduser = Id.getText().toString();
                String passuser = Pass.getText().toString();
                if(iduser.isEmpty() || passuser.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter Userid & Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    validate(Id.getText().toString(), Pass.getText().toString());
                }
            }
        });
        Newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Reset.class));
            }
        });

    }

    private void validate (String userId,String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userId,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkVerified();
                }
                else{
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkVerified(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this,Home.class));
        }
        else{
            Toast.makeText(this,"Verify Ur Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
