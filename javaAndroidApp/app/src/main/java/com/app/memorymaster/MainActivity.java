package com.app.memorymaster;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageButton signupbtn;
    private ImageButton loginbtn;




    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("L");
        }

        @SuppressLint("CutPasteId") ImageButton loginbtn=(ImageButton) findViewById(R.id.login);
        loginbtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,MainActivity.class))
        );
        signupbtn=findViewById(R.id.signup);
        signupbtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,SignupActivity.class)));
        loginbtn=findViewById(R.id.login);
        loginbtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginScreenActivity.class)));

    }
}