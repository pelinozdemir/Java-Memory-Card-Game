package com.app.memorymaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NonNls;

public class LoginScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_screen);
        super.onCreate(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        ImageButton signupbtn=(ImageButton) findViewById(R.id.loginpagebuton);

        signupbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        login();
                    }
                }
        );

    }

    private void login(){
        EditText email=findViewById(R.id.emaillogin) ;

        EditText password =findViewById(R.id.passwordlogin);




        String emailstr=email.getText().toString().trim();
        String passwordstr=password.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNls Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("Tamam");
                    showLoginActivity();
                }else{
                    Toast.makeText(LoginScreenActivity.this,"failled",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private  void showLoginActivity(){

        startActivity(new Intent(LoginScreenActivity.this,MenuActivity.class));
        finish();
    }
}