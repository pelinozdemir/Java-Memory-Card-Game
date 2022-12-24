package com.app.memorymaster;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.annotation.NonNullApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.jetbrains.annotations.NonNls;


public class SignupActivity extends AppCompatActivity {

    private  FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth=FirebaseAuth.getInstance();
        ImageButton signupbtn=(ImageButton) findViewById(R.id.signuppagebuton);
        signupbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        register();
                    }
                }
        );

    }
    private void register(){
        EditText email=findViewById(R.id.emailsignup) ;
        EditText username=(EditText) findViewById(R.id.username);
        EditText password =findViewById(R.id.passwordsignup);



        String usernamestr=username.getText().toString().trim();
        String emailstr=email.getText().toString().trim();
        String passwordstr=password.getText().toString().trim();
        String id=(FirebaseAuth.getInstance().getCurrentUser()).getUid().toString();
        System.out.println(emailstr+" "+passwordstr+" "+usernamestr);
        User user=new User(usernamestr,emailstr,id,passwordstr);
        mAuth.createUserWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNls Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
                    DatabaseReference myRef = database.getReference("Users");
                    myRef.child((FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            showSignupActivity();

                        }
                    });
                }else{
                    Toast.makeText(SignupActivity.this,"failled",Toast.LENGTH_SHORT);
                }
            }
        });

    }
    private  void showSignupActivity(){

        startActivity(new Intent(this,LoginScreenActivity.class));
        finish();
    }
}