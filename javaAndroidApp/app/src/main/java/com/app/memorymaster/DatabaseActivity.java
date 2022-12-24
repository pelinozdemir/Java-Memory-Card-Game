package com.app.memorymaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class DatabaseActivity extends AppCompatActivity {

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        List<String> image= Arrays.asList();
        CardDetails cards=new CardDetails();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Cards");
        System.out.println(cards.cardname.size());
        for(int i=0;i<cards.cardname.size();i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), cards.myImageList[i]);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteStream);
            byte[] byteArray = byteStream.toByteArray();
            String imageString = Base64.encodeToString(byteArray,Base64.DEFAULT);
           // System.out.println(imageString);
            if (i <= 10) {

                Cards card = new Cards(cards.cardname.get(i).toString(), i, cards.point.get(i).intValue(),2,imageString,"Gryffindor");
                myRef.child(cards.cardname.get(i).toString()).setValue(card);

            }
            if (i > 10 && i <= 21) {

                Cards card = new Cards(cards.cardname.get(i).toString(), i, cards.point.get(i).intValue(),2,imageString, "Slythherin");
                myRef.child(cards.cardname.get(i).toString()).setValue(card);

            }
            if (i > 21 && i <= 32) {

                Cards card = new Cards(cards.cardname.get(i).toString(), i, cards.point.get(i).intValue(),1,imageString, "Ravenclaw");
                myRef.child(cards.cardname.get(i).toString()).setValue(card);

            }
            if (i > 32 && i <= 43) {

                Cards card = new Cards(cards.cardname.get(i).toString(), i, cards.point.get(i).intValue(),1, imageString,"Hufflepuff");
                myRef.child(cards.cardname.get(i).toString()).setValue(card);

            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    }
}