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
import android.view.KeyEvent;
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

public class Single4PlayerActivity extends AppCompatActivity {
    Random rn = new Random();
    MediaPlayer background;
    MediaPlayer victory;
    MediaPlayer shock;
    MediaPlayer tebrik;
    ImageButton sound;

    int card1 = rn.nextInt(11) ;//G
    int card2 = rn.nextInt(11) ;
    int card3 = rn.nextInt(11)+11 ;//S
    int card4 = rn.nextInt(11)+11;
    int card5 = rn.nextInt(11)+22 ;//R
    int card6= rn.nextInt(11)+22 ;
    int card7 = rn.nextInt(11)+33 ;//H
    int card8 = rn.nextInt(11)+33 ;

    int[] cardid={card1,card2, card3,card4,card5,card6,card7,card8};


    int[] usecardid= {0,0,0,0,0,0,0,0};

    Drawable dr2;
    ImageView cardView=null;
    private int count=0;
    int[] pos={0,1,2,3,4,5,6,7};
    ArrayList<Integer> usecardindex= new ArrayList<Integer>();
    int current=-1;
    int currentrandom;
    int randomPosition;
    int currenthomeid;
    String currenthome;
    int currentpoint;
    int win=0;
    Drawable[] cardimages=new Drawable[16];
    String[] cardhome=new String[16];
    int[] cardhomeid=new int[16];
    int[] cardpoint=new int[16];
    ArrayList<Integer> enablePos=new ArrayList<Integer>();

    ArrayList<Integer> cardidlist=new ArrayList<Integer>();

    String image;

    CardDetails card = new CardDetails();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Cards");
    CountDownTimer timer ;
    private static final String TAG=Single4PlayerActivity.class.getName();
    private static ArrayList<AppCompatActivity> activities=new ArrayList<AppCompatActivity>();


    public void onBackPressed() {
        timer.onFinish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single4_player);
        activities.add(this);
        background=MediaPlayer.create(Single4PlayerActivity.this,R.raw.background);
        background.start();
        victory=MediaPlayer.create(Single4PlayerActivity.this,R.raw.victory);
        shock=MediaPlayer.create(Single4PlayerActivity.this,R.raw.shock);
        tebrik=MediaPlayer.create(Single4PlayerActivity.this,R.raw.tebrik);
        cardidlist.add(card1);
        cardidlist.add(card2);
        cardidlist.add(card3);
        cardidlist.add(card4);
        cardidlist.add(card5);
        cardidlist.add(card6);
        cardidlist.add(card7);
        cardidlist.add(card8);
        cardidlist.add(card1);
        cardidlist.add(card2);
        cardidlist.add(card3);
        cardidlist.add(card4);
        cardidlist.add(card5);
        cardidlist.add(card6);
        cardidlist.add(card7);
        cardidlist.add(card8);

        Collections.shuffle(cardidlist);
        System.out.println(cardidlist);

        Drawable hogwarts = AppCompatResources.getDrawable(this, R.drawable.hogwarts_logo_png_transparent);
        Drawable[] pictures={hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,
                hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts};

        TextView time=findViewById(R.id.time_);
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
        ImageData imageData=new ImageData(this,16,pictures);

                //  time.setText(String.valueOf(millisUntilFinished/1000));

                gridView.setAdapter(imageData);
         timer = new CountDownTimer(80000, 1000) {
            public void onTick(long millisUntilFinished) {

                time.setText(String.valueOf(millisUntilFinished/1000));
                if(win==8 ){
                   // System.out.println("WİNNNNNN");
                    tebrik.start();
                    getScore("Win!");
                     victory.stop();
                    background.stop();

                }

            }

            public void onFinish() {
                if(win<8 ){
                   // System.out.println("LOSTT");
                    shock.start();
                    getScore("Lost!");
                    victory.stop();
                    background.stop();



                }else if(win==8){
                    tebrik.start();
                    getScore("You win!");
                    victory.stop();
                    background.stop();


                }



            }
            public  void getScore(String text){
            new AlertDialog.Builder(new ContextThemeWrapper(Single4PlayerActivity.this, R.style.myDialog))
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
                                startActivity(new Intent(Single4PlayerActivity.this,MenuActivity.class));
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

                //System.out.println(cardidlist.get(i)+" "+i);
                enablePos.clear();
                if(current<0){
                    adapterView.setId(current);
                    randomPosition = rn.nextInt(cardid.length);
                    current=i;
                    System.out.println(cardidlist.get(i)+" "+i);
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






                }else{

                    if(current==i){
                        System.out.println(current);
                        ((ImageView)view).setImageResource(R.drawable.hogwarts_logo_png_transparent);

                    }else if(current!=i){
                        System.out.println(current);
                        int t=current;




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

                                        ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
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
                                        ImageData imageData_=new ImageData(Single4PlayerActivity.this,16,pictures);
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

                                        victory.start();
                                        Toast.makeText(getApplicationContext(), "Eşleşti", Toast.LENGTH_SHORT).show();
                                    }
                                  //  int scoretext=2*point*homeid;
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }});

                    }


                    current=-1;



                }

            }

        });//start the countdowntimer





    }




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

                imageView.setLayoutParams(new ViewGroup.LayoutParams(200,300));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }else{


                imageView=(ImageView) view;



            }

            imageView.setImageDrawable(pictures[i]);

            return imageView;
        }
    }


}