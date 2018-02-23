package com.example.owner.myapplication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Registration extends AppCompatActivity {

    private EditText Username,Email,Userpwd;
    private Button Register,Existing;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth =FirebaseAuth.getInstance();

        Username = (EditText) findViewById(R.id.Username);
        Email = (EditText) findViewById(R.id.Email);
        Userpwd = (EditText) findViewById(R.id.Userpwd);
        Register = (Button) findViewById(R.id.btnRegister);
        Existing = (Button) findViewById(R.id.User);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email = Email.getText().toString().trim();
                    String user_pwd = Userpwd.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                emailVerification();
                            }
                            else {
                                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        Existing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private Boolean validate(){
        Boolean result = false;
        String Name = Username.getText().toString();
        String Mail = Email.getText().toString();
        String Password = Userpwd.getText().toString();

        if(Name.isEmpty() || Mail.isEmpty() || Password.isEmpty()){
            Toast.makeText(this,"Please Enter Complete Details",Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
    private void emailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registration.this,"Registration Successful,Verifiaction Mail Sent",Toast.LENGTH_SHORT).show();;
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(Registration.this,"Verifiaction Mail Not Sent !",Toast.LENGTH_SHORT).show();;
                    }
                }
            });
        }
    }
}
