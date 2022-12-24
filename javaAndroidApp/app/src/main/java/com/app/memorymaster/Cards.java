package com.app.memorymaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cards {

    public String cardname;
    public int id;
    public int point;
    public int homeid;
    public String home;
    public String image;


    public Cards(String cardname,int id, int point,int homeid,String image,String home) {
        this.cardname= cardname;
        this.id=id;
        this.point= point;
        this.homeid=homeid;
        this.image=image;
        this.home=home;
    }

}