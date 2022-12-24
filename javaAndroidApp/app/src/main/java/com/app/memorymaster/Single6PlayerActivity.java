package com.app.memorymaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Single6PlayerActivity extends AppCompatActivity {

    Random rn = new Random();
    MediaPlayer background;
    MediaPlayer victory;
    MediaPlayer shock;
    MediaPlayer tebrik;
    ImageButton sound;

    int card1 = rn.nextInt(11) ;//G
    int card2 = rn.nextInt(11) ;
    int card3 = rn.nextInt(11) ;//G
    int card4 = rn.nextInt(11) ;
    int card5 = rn.nextInt(11) ;
    int card6 = rn.nextInt(11)+11 ;//S
    int card7 = rn.nextInt(11)+11;
    int card8 = rn.nextInt(11)+11 ;//S
    int card9 = rn.nextInt(11)+11;
    int card10 = rn.nextInt(11)+11 ;//S
    int card11 = rn.nextInt(11)+22 ;//R
    int card12= rn.nextInt(11)+22 ;
    int card13 = rn.nextInt(11)+22 ;//R
    int card14= rn.nextInt(11)+22 ;
    int card15 = rn.nextInt(11)+33 ;//H
    int card16 = rn.nextInt(11)+33 ;
    int card17 = rn.nextInt(11)+33 ;//H
    int card18 = rn.nextInt(11)+33 ;

    int[] cardid={card1,card2, card3,card4,card5,card6,card7,card8,card9,card10,card11,card12,card13,card14,card15,card16,card17,card18};


    int[] usecardid= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    Drawable dr2;
    ImageView cardView=null;
    private int count=0;
    int[] pos={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    ArrayList<Integer> usecardindex= new ArrayList<Integer>();
    int current=-1;
    int currentrandom;
    int randomPosition;
    int currenthomeid;
    String currenthome;
    int currentpoint;
    CountDownTimer timer ;
    int win=0;
    Drawable[] cardimages=new Drawable[36];
    String[] cardhome=new String[36];
    int[] cardhomeid=new int[36];
    int[] cardpoint=new int[36];
    ArrayList<Integer> enablePos=new ArrayList<Integer>();

    ArrayList<Integer> cardidlist=new ArrayList<Integer>();

    String image;

    CardDetails card = new CardDetails();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Cards");

    private static final String TAG=Single4PlayerActivity.class.getName();
    private static ArrayList<AppCompatActivity> activities=new ArrayList<AppCompatActivity>();


    @Override
    public void onBackPressed() {
        timer.onFinish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single4_player);
        activities.add(this);
        background=MediaPlayer.create(Single6PlayerActivity.this,R.raw.background);
        background.start();
        victory=MediaPlayer.create(Single6PlayerActivity.this,R.raw.victory);
        shock=MediaPlayer.create(Single6PlayerActivity.this,R.raw.shock);
        tebrik=MediaPlayer.create(Single6PlayerActivity.this,R.raw.tebrik);
        cardidlist.add(card1);
        cardidlist.add(card2);
        cardidlist.add(card3);
        cardidlist.add(card4);
        cardidlist.add(card5);
        cardidlist.add(card6);
        cardidlist.add(card7);
        cardidlist.add(card8);
        cardidlist.add(card9);
        cardidlist.add(card10);
        cardidlist.add(card11);
        cardidlist.add(card12);
        cardidlist.add(card13);
        cardidlist.add(card14);
        cardidlist.add(card15);
        cardidlist.add(card16);
        cardidlist.add(card17);
        cardidlist.add(card18);
        cardidlist.add(card1);
        cardidlist.add(card2);
        cardidlist.add(card3);
        cardidlist.add(card4);
        cardidlist.add(card5);
        cardidlist.add(card6);
        cardidlist.add(card7);
        cardidlist.add(card8);
        cardidlist.add(card9);
        cardidlist.add(card10);
        cardidlist.add(card11);
        cardidlist.add(card12);
        cardidlist.add(card13);
        cardidlist.add(card14);
        cardidlist.add(card15);
        cardidlist.add(card16);
        cardidlist.add(card17);
        cardidlist.add(card18);
        Collections.shuffle(cardidlist);
        System.out.println(cardidlist);

        Drawable hogwarts = AppCompatResources.getDrawable(this, R.drawable.hogwarts_logo_png_transparent);
        Drawable[] pictures={hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,
                hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,
                hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts};

        TextView time=(TextView) findViewById(R.id.time_);
        TextView score=findViewById(R.id.score);

        GridView gridView=findViewById(R.id.gridView);
        sound=findViewById(R.id.sound);
        sound.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(background.isPlaying()){
                            background.pause();
                            sound.setImageResource(R.drawable.ic_baseline_music_off_24);
                        }else{
                            background.start();
                            sound.setImageResource(R.drawable.ic_baseline_music_note_24);
                        }
                    }
                }
        );
        Single6PlayerActivity.ImageData imageData=new Single6PlayerActivity.ImageData(this,36,pictures);

        //  time.setText(String.valueOf(millisUntilFinished/1000));

        gridView.setAdapter(imageData);
        timer = new CountDownTimer(80000, 1000) {
            public void onTick(long millisUntilFinished) {

                time.setText(String.valueOf(millisUntilFinished/1000));
                if(win==18 ){
                    // System.out.println("WİNNNNNN");
                    tebrik.start();
                    getScore("You win!");

                    victory.stop();
                    background.stop();

                }

            }

            public void onFinish() {
                if(win<18 ){
                    // System.out.println("LOSTT");
                    shock.start();
                    getScore("Lost!");

                    victory.stop();
                    background.stop();
                    cancel();



                }else if(win==18){
                    tebrik.start();
                    getScore("You win!");

                    victory.stop();
                    background.stop();


                }



            }
            public  void getScore(String text){
                new AlertDialog.Builder(new ContextThemeWrapper(Single6PlayerActivity.this, R.style.myDialog))
                        .setTitle(text)
                        .setMessage(score.getText().toString())

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                background.pause();
                                victory.pause();
                                tebrik.pause();
                                shock.pause();
                                startActivity(new Intent(Single6PlayerActivity.this,MenuActivity.class));
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        }.start();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                enablePos.clear();
                if(current<0){
                    adapterView.setId(current);
                    randomPosition = rn.nextInt(cardid.length);
                    current=i;
                    System.out.println(current);
                    //((ImageView) view).setImageDrawable(cardimages[i]);
                    myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            currentpoint = snapshot.child("point").getValue(Integer.class);
                            currenthomeid = snapshot.child("homeid").getValue(Integer.class);
                            currenthome=snapshot.child("home").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                            Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                            dr2 = new BitmapDrawable(bm2);
                            ((ImageView) view).setImageDrawable(dr2);
                            cardView = ((ImageView) view);



                        }
                        @Override
                        public void onCancelled(DatabaseError error) {

                        }});

                  /*  if(usecardid[randomPosition]<2){
                        currentrandom=randomPosition;
                        usecardid[randomPosition]+=1;*/
                        /*Task<DataSnapshot> baseString2=myRef.child(card.cardname.get(cardidlist.get(i))).child("image").get().addOnCompleteListener(task -> {

                            if (!task.isSuccessful()) {

                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                image=String.valueOf(task.getResult().getValue());

                                byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes ,0,imageAsBytes.length);

                                dr2 = new BitmapDrawable(bm2);


                                ((ImageView)view).setImageDrawable(dr2);

                                cardView=((ImageView) view);



                            }
                        });*/




                    //here's the code to put your drawable in your grid view, in my case, it was just a button



                    //}
                    /*else if(usecardid[randomPosition]>=2){
                        usecardindex.add(randomPosition);
                        while(true){
                            randomPosition = rn.nextInt(cardid.length);

                            if(usecardindex.contains(randomPosition)){
                                continue;
                            }else{
                                currentrandom=randomPosition;
                                usecardid[randomPosition]+=1;*/
                              /*  myRef.child(card.cardname.get(cardidlist.get(i))).child("image").get().addOnCompleteListener(task -> {
                                    if (!task.isSuccessful()) {

                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        String image=String.valueOf(task.getResult().getValue());
                                        byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                        Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes ,0,imageAsBytes.length);

                                        Drawable dr = new BitmapDrawable(bm2);

                                        ((ImageView)view).setImageDrawable(dr);

                                        cardView=((ImageView) view);
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                });*/
                    // break;


                    // }
                    // }



                    //  }
                    // ((ImageView)view).setImageDrawable(drawables.get(i));




                }else{

                    /*randomPosition = rn.nextInt(cardid.length);
                    System.out.println(usecardid[randomPosition]+" "+i);*/
                    if(current==i){
                        System.out.println(current);
                        ((ImageView)view).setImageResource(R.drawable.hogwarts_logo_png_transparent);

                    }else if(current!=i){
                        System.out.println(current);
                        int t=current;


                       /* byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                        Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                        Drawable dr = new BitmapDrawable(bm2);
                        ((ImageView) view).setImageDrawable(dr);
                        System.out.println(current);
                        if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                            ((ImageView) view).setImageDrawable(dr);

                            ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                            gridView.setAdapter(imageData_);
                            usecardid[currentrandom] -=1;
                            if(cardhome[i] !=cardhome[t]){
                                int scorestr= Integer.valueOf(score.getText().toString());
                                int scoretext=scorestr-(((cardpoint[i]+cardpoint[t])/2)*cardhomeid[t]*cardhomeid[i]*(Integer.valueOf(time.getText().toString())/10));
                                score.setText(String.valueOf(scoretext));
                            }else if(cardhome[i]==cardhome[t]){
                                int scorestr= Integer.valueOf(score.getText().toString());
                                int scoretext=scorestr-((((cardpoint[i]+cardpoint[t]))/cardhomeid[i])*(Integer.valueOf(time.getText().toString())/10));
                                score.setText(String.valueOf(scoretext));
                            }
                            Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                        } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                            ((ImageView) view).setImageDrawable(dr);
                            pictures[i]=dr;
                            System.out.println(current);
                            pictures[t]=dr2;
                            ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                            gridView.setAdapter(imageData_);
                            enablePos.add(i);
                            enablePos.add(t);
                            usecardid[randomPosition] += 1;
                            usecardindex.add(randomPosition);
                            System.out.println(time.getText().toString());
                            //int scoretext=2*point*homeid;
                            int scorestr= Integer.valueOf(score.getText().toString());
                            int scoretext=((2*cardpoint[i]*cardhomeid[i])*(Integer.valueOf(time.getText().toString())/10))+scorestr;
                            score.setText(String.valueOf(scoretext));
                            win+=1;

                            // int t=0;
                                                   /* int delete=cardidlist.get(i);
                                                    int dublicate=0;
                                                    while(t<cardidlist.size()){
                                                        if(cardidlist.contains(delete)){
                                                            cardidlist.remove(t);
                                                            dublicate+=1;
                                                        }
                                                        if(dublicate==2){
                                                            break;
                                                        }
                                                    }

                            Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                        }*/
                        //  int scoretext=2*point*homeid;
                        /*if(usecardid[randomPosition]<2) {
                            System.out.println("Current");
                            System.out.println(current);

                            System.out.println(t);*/


                        myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange( DataSnapshot snapshot) {
                                Integer point = snapshot.child("point").getValue(Integer.class);
                                Integer homeid = snapshot.child("homeid").getValue(Integer.class);
                                String home=snapshot.child("home").getValue(String.class);
                                String image=snapshot.child("image").getValue(String.class);
                                byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                Drawable dr = new BitmapDrawable(bm2);
                                ((ImageView) view).setImageDrawable(dr);
                                System.out.println(current);
                                if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                    ((ImageView) view).setImageDrawable(dr);

                                    Single6PlayerActivity.ImageData imageData_=new Single6PlayerActivity.ImageData(Single6PlayerActivity.this,36,pictures);
                                    gridView.setAdapter(imageData_);
                                    usecardid[currentrandom] -=1;
                                    if(home !=currenthome){
                                        int scorestr= Integer.valueOf(score.getText().toString());
                                        int scoretext=scorestr-(((point+currentpoint)/2)*currenthomeid*homeid*(Integer.valueOf(time.getText().toString())/10));
                                        score.setText(String.valueOf(scoretext));
                                    }else if(home==currenthome){
                                        int scorestr= Integer.valueOf(score.getText().toString());
                                        int scoretext=scorestr-((((point+currentpoint))/homeid)*(Integer.valueOf(time.getText().toString())/10));
                                        score.setText(String.valueOf(scoretext));
                                    }
                                    Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                                } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                    ((ImageView) view).setImageDrawable(dr);
                                    pictures[i]=dr;
                                    System.out.println(current);
                                    pictures[t]=dr2;
                                    Single6PlayerActivity.ImageData imageData_=new Single6PlayerActivity.ImageData(Single6PlayerActivity.this,36,pictures);
                                    gridView.setAdapter(imageData_);
                                    enablePos.add(i);
                                    enablePos.add(t);
                                    usecardid[randomPosition] += 1;
                                    usecardindex.add(randomPosition);
                                    System.out.println(time.getText().toString());
                                    //int scoretext=2*point*homeid;
                                    int scorestr= Integer.valueOf(score.getText().toString());
                                    int scoretext=((2*point*homeid)*(Integer.valueOf(time.getText().toString())/10))+scorestr;
                                    score.setText(String.valueOf(scoretext));
                                    win+=1;

                                    // int t=0;
                                                   /* int delete=cardidlist.get(i);
                                                    int dublicate=0;
                                                    while(t<cardidlist.size()){
                                                        if(cardidlist.contains(delete)){
                                                            cardidlist.remove(t);
                                                            dublicate+=1;
                                                        }
                                                        if(dublicate==2){
                                                            break;
                                                        }
                                                    }*/
                                    victory.start();
                                    Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                                }
                                //  int scoretext=2*point*homeid;
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }});
                           /* myRef.child(card.cardname.get(cardidlist.get(i))).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {

                                        Log.e("firebase", "Error getting data", task.getException());
                                    } else {
                                        String image = String.valueOf(task.getResult().getValue());
                                        byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                        Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                        Drawable dr = new BitmapDrawable(bm2);
                                        ((ImageView) view).setImageDrawable(dr);
                                        System.out.println(current);
                                        if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                            ((ImageView) view).setImageDrawable(dr);

                                            ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                            gridView.setAdapter(imageData_);
                                            usecardid[currentrandom] -=1;
                                            Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                                        } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                            ((ImageView) view).setImageDrawable(dr);
                                            pictures[i]=dr;
                                            System.out.println(current);
                                            pictures[t]=dr2;
                                            ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                            gridView.setAdapter(imageData_);
                                            enablePos.add(i);
                                            enablePos.add(current);
                                            usecardid[randomPosition] += 1;
                                            usecardindex.add(randomPosition);
                                            int t=0;
                                                   /* int delete=cardidlist.get(i);
                                                    int dublicate=0;
                                                    while(t<cardidlist.size()){
                                                        if(cardidlist.contains(delete)){
                                                            cardidlist.remove(t);
                                                            dublicate+=1;
                                                        }
                                                        if(dublicate==2){
                                                            break;
                                                        }
                                                    }

                                            Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                                        }
                                        // Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                }
                            });*/
                        /*}else if(usecardid[randomPosition]>=2){

                            while(true){
                                randomPosition = rn.nextInt(cardid.length);
                                if(usecardindex.contains(randomPosition)){
                                    for(int z=0;z<usecardindex.size();z++){
                                        System.out.println("İNDEXX");
                                        System.out.println(usecardindex.get(z));
                                    }
                                    continue;
                                }else{*/
                                   /* myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            Integer point = snapshot.child("point").getValue(Integer.class);
                                            Integer homeid = snapshot.child("homeid").getValue(Integer.class);
                                            String image=snapshot.child("image").getValue(String.class);
                                            byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                            Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                            Drawable dr = new BitmapDrawable(bm2);

                                            if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                                ((ImageView) view).setImageDrawable(dr);
                                                ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                                gridView.setAdapter(imageData_);
                                                usecardid[currentrandom] -=1;
                                                System.out.println("usecardid[randomPosition]>=2");
                                                System.out.print(usecardid[0]+" "+usecardid[1]);
                                                Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                                            } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                                ((ImageView) view).setImageDrawable(dr);
                                                enablePos.add(i);
                                                enablePos.add(current);
                                                pictures[i]=dr;
                                                pictures[t]=dr2;
                                                ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                                gridView.setAdapter(imageData_);
                                                usecardid[randomPosition] += 1;
                                                usecardindex.add(randomPosition);
                                                int scoretext=(2*point*homeid)*(Integer.valueOf(time.getText().toString())/10);
                                                score.setText(String.valueOf(scoretext));
                                                  /*  myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot snapshot) {
                                                            Integer point = snapshot.child("point").getValue(Integer.class);
                                                            Integer homeid = snapshot.child("homeid").getValue(Integer.class);
                                                            int scoretext=2*point*homeid;
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError error) {

                                                        }
                                                    });


                                                Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {


                                        }
                                    });*/
                                  /*  myRef.child(card.cardname.get(cardidlist.get(i))).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()) {

                                                Log.e("firebase", "Error getting data", task.getException());
                                            } else {
                                                String image = String.valueOf(task.getResult().getValue());
                                                byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                                                Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                                Drawable dr = new BitmapDrawable(bm2);
                                                //((ImageView) view).setImageDrawable(dr);
                                                if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                                    ((ImageView) view).setImageDrawable(dr);
                                                    ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                                    gridView.setAdapter(imageData_);
                                                    usecardid[currentrandom] -=1;
                                                    System.out.println("usecardid[randomPosition]>=2");
                                                    System.out.print(usecardid[0]+" "+usecardid[1]);
                                                    Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                                                } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                                    ((ImageView) view).setImageDrawable(dr);
                                                    enablePos.add(i);
                                                    enablePos.add(current);
                                                    pictures[i]=dr;
                                                    pictures[t]=dr2;
                                                    ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                                    gridView.setAdapter(imageData_);
                                                    usecardid[randomPosition] += 1;
                                                    usecardindex.add(randomPosition);
                                                  /*  myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot snapshot) {
                                                            Integer point = snapshot.child("point").getValue(Integer.class);
                                                            Integer homeid = snapshot.child("homeid").getValue(Integer.class);
                                                            int scoretext=2*point*homeid;
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError error) {

                                                        }
                                                    });


                                                    Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                                    /*ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
                                                    gridView.setAdapter(imageData_);
                                        }

                                    });
                                    break;


                                }
                            }



                        }*/

                        //((ImageView) view).setImageResource(R.drawable.hogwarts_logo_png_transparent);
                    }


                    current=-1;



                }

            }

        });//start the countdowntimer





    }

   /* private void getImages() {

        for (int i=0;i<16;i++){
            int finalI = i;
            myRef.child(card.cardname.get(cardidlist.get(i))).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Integer point = snapshot.child("point").getValue(Integer.class);
                    Integer homeid = snapshot.child("homeid").getValue(Integer.class);
                    String home=snapshot.child("home").getValue(String.class);
                    String image=snapshot.child("image").getValue(String.class);

                    byte[] imageAsBytes = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bm2 = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    dr2 = new BitmapDrawable(bm2);
                    cardimages[finalI]=dr2;
                    cardhome[finalI]=home;
                    cardhomeid[finalI]=homeid;
                    cardpoint[finalI]=point;




                }
                @Override
                public void onCancelled(DatabaseError error) {

                }});
        }

    }*/


    public class ImageData extends BaseAdapter {
        private Context context;
        private int numberofcards;
        private Drawable[] pictures;
        public ImageData(Context context,int numberofcards,Drawable[] pictures){
            this.context=context;
            this.numberofcards=numberofcards;
            this.pictures=pictures;
        }

        @Override
        public boolean isEnabled(int position) {
            if(enablePos.contains(position)){
                System.out.println(position);
                return false;
            }else{
                return true;
            }

        }

        @Override
        public int getCount() {
            return this.numberofcards;
        }



        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }




        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if(view==null){
                imageView=new ImageView(this.context);

                imageView.setLayoutParams(new ViewGroup.LayoutParams(100,200));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }else{


                imageView=(ImageView) view;



            }

            imageView.setImageDrawable(pictures[i]);

            return imageView;
        }
    }


}