package com.app.memorymaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SymbolTable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MultiPlayers4Activity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference roomRef = database.getReference("Rooms");
    MediaPlayer background;
    MediaPlayer victory;
    MediaPlayer shock;
    MediaPlayer tebrik;
    ImageButton sound;
    String roomname;
    String role;
    Drawable dr2;
    ImageView cardView=null;
    int current=-1;
    int currenthomeid;
    String currenthome;
    int currentpoint;
    int win=0;
    GridView gridView;
    boolean host=false;
    boolean quest=false;
    ArrayList<Integer> enablePos=new ArrayList<Integer>();
    TextView hostscore;
    TextView questscore;
    public ArrayList<Integer> cardidlist=new ArrayList<Integer>();

    TextView time;
    String image;
    CountDownTimer timer;

    CardDetails card = new CardDetails();
    DatabaseReference myRef = database.getReference("Cards");
    public void onBackPressed() {
        timer.onFinish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_players4_activitiy);
        background=MediaPlayer.create(MultiPlayers4Activity.this,R.raw.background);
        background.start();
        victory=MediaPlayer.create(MultiPlayers4Activity.this,R.raw.victory);
        shock=MediaPlayer.create(MultiPlayers4Activity.this,R.raw.shock);
        tebrik=MediaPlayer.create(MultiPlayers4Activity.this,R.raw.tebrik);
         Drawable hogwarts = AppCompatResources.getDrawable(this, R.drawable.hogwarts_logo_png_transparent);
          Drawable[] pictures={hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,
                hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts,hogwarts};

        gridView=findViewById(R.id.gridView2);

        //  time.setText(String.valueOf(millisUntilFinished/1000));
        time=findViewById(R.id.time_);
        hostscore=findViewById(R.id.hostscore);
        questscore=findViewById(R.id.questscore);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            roomname=extras.getString("roomname");
            role=extras.getString("role");

            roomRef.child(roomname).child("roomdetails").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getKey().toString());


                    for (DataSnapshot data:dataSnapshot.getChildren()){

                        cardidlist.add(data.getValue(Integer.class));







                        System.out.println(data.getValue(Integer.class));

                        if(role.equals("Host")){
                            host=true;
                        }else if(role.equals("Quest")){
                            quest=true;
                        }


                    }
System.out.println(cardidlist);

                    createGame(pictures);



                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });



        }




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

         timer = new CountDownTimer(80000, 1000) {
            public void onTick(long millisUntilFinished) {

                time.setText(String.valueOf(millisUntilFinished/1000));
                if(win==8 ){

                    getScore("Game Over");
                    tebrik.start();
                    background.stop();

                }

            }

            public void onFinish() {

                    getScore("Game Over");
                    shock.start();
                    background.stop();





            }
            public  void getScore(String text){

                if(Integer.valueOf(questscore.getText().toString())>Integer.valueOf(hostscore.getText().toString())){
                    if(role.equals("Quest")){
                        new AlertDialog.Builder(new ContextThemeWrapper(MultiPlayers4Activity.this, R.style.myDialog))
                                .setTitle(text)
                                .setMessage("You win")

                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(MultiPlayers4Activity.this,MenuActivity.class));
                                    }
                                })



                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else if(role.equals("Host")){
                        new AlertDialog.Builder(new ContextThemeWrapper(MultiPlayers4Activity.this, R.style.myDialog))
                                .setTitle(text)
                                .setMessage("You lost")

                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(MultiPlayers4Activity.this,MenuActivity.class));
                                    }
                                })



                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }else if(Integer.valueOf(questscore.getText().toString())<Integer.valueOf(hostscore.getText().toString())){
                    if(role.equals("Quest")){
                        new AlertDialog.Builder(new ContextThemeWrapper(MultiPlayers4Activity.this, R.style.myDialog))
                                .setTitle(text)
                                .setMessage("You lost")


                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(MultiPlayers4Activity.this,MenuActivity.class));
                                    }
                                })



                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else if(role.equals("Host")){
                        new AlertDialog.Builder(new ContextThemeWrapper(MultiPlayers4Activity.this, R.style.myDialog))
                                .setTitle(text)
                                .setMessage("You win")

                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(MultiPlayers4Activity.this,MenuActivity.class));
                                    }
                                })


                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }else{
                    new AlertDialog.Builder(new ContextThemeWrapper(MultiPlayers4Activity.this, R.style.myDialog))
                            .setTitle(text)
                            .setMessage("Scores are equals")


                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    startActivity(new Intent(MultiPlayers4Activity.this,MenuActivity.class));
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

             roomRef.child(roomname).setValue(null);
            }

        }.start();









    }

    private void createGame(Drawable[] pictures) {
        MultiPlayers4Activity.ImageData imageData_=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,true,"host");
        gridView.setAdapter(imageData_);

        roomRef.child(roomname).addValueEventListener(new ValueEventListener() {
          DataSnapshot previous = null;
         @Override
         public void onDataChange(DataSnapshot snapshot) {
              String   change="change";


              if(snapshot.child("host").exists() && snapshot!=null){
                  change= snapshot.child("host").getValue(String.class);

                  if(host){
                      if(change.equals("Host")){

                          MultiPlayers4Activity.ImageData imageData=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,true,change);
                          getHostScore();
                          getQuestScore();
                          gridView.setAdapter(imageData);
                          gridView.setEnabled(true);


                      }else if(change.equals("Quest")){

                          MultiPlayers4Activity.ImageData imageData=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,false,change);
                          getHostScore();
                          getQuestScore();
                          gridView.setAdapter(imageData);
                          gridView.setEnabled(false);

                      }

                  }else if(quest){
                      if(change.equals("Quest")){

                          MultiPlayers4Activity.ImageData imageData=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,true,change);
                          getHostScore();
                          getQuestScore();
                          gridView.setAdapter(imageData);
                          gridView.setEnabled(true);

                      }else if(change.equals("Host")){

                          MultiPlayers4Activity.ImageData imageData=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,false,change);
                          getHostScore();
                          getQuestScore();
                          gridView.setAdapter(imageData);
                          gridView.setEnabled(false);

                      }
                  }


                   }else{

              }


                                                                        }

                                                          @Override
                                                          public void onCancelled(DatabaseError error) {

                                                          }
                                                      }

        );
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getQuestScore();
                getHostScore();
                enablePos.clear();
                if(current<0){
                    current=i;
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

                        ((ImageView)view).setImageResource(R.drawable.hogwarts_logo_png_transparent);

                    }else if(current!=i){

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

                                if (!((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                    ((ImageView) view).setImageDrawable(dr);

                                    MultiPlayers4Activity.ImageData imageData_=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,true,"host");
                                    gridView.setAdapter(imageData_);

                                    if(home !=currenthome){
                                        if (quest){
                                            int scorestr= Integer.valueOf(questscore.getText().toString());
                                            int scoretext=scorestr-(((point+currentpoint)/2)*currenthomeid*homeid*(Integer.valueOf(time.getText().toString())/10));
                                            questscore.setText(String.valueOf(scoretext));
                                            roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);

                                            roomRef.child(roomname).child("host").setValue("Host");

                                        }else if(host){
                                            int scorestr= Integer.valueOf(hostscore.getText().toString());
                                            int scoretext=scorestr-(((point+currentpoint)/2)*currenthomeid*homeid*(Integer.valueOf(time.getText().toString())/10));
                                            hostscore.setText(String.valueOf(scoretext));
                                            System.out.println("kazanamadı");
                                            roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);
                                            roomRef.child(roomname).child("host").setValue("Quest");
                                        }

                                    }else if(home==currenthome){
                                        if (quest){
                                            int scorestr= Integer.valueOf(questscore.getText().toString());
                                            int scoretext=scorestr-((((point+currentpoint))/homeid)*(Integer.valueOf(time.getText().toString())/10));
                                            questscore.setText(String.valueOf(scoretext));
                                            roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);
                                            roomRef.child(roomname).child("host").setValue("Host");

                                        }else if(host){
                                            int scorestr= Integer.valueOf(hostscore.getText().toString());
                                            int scoretext=scorestr-((((point+currentpoint))/homeid)*(Integer.valueOf(time.getText().toString())/10));
                                            hostscore.setText(String.valueOf(scoretext));
                                            roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);
                                            roomRef.child(roomname).child("host").setValue("Quest");
                                        }

                                    }
                                    Toast.makeText(getApplicationContext(), "Eşleşmedi", Toast.LENGTH_SHORT).show();



                                } else if (((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().sameAs(((BitmapDrawable) (cardView).getDrawable()).getBitmap())) {
                                    ((ImageView) view).setImageDrawable(dr);
                                    pictures[i]=dr;

                                    pictures[t]=dr2;
                                    MultiPlayers4Activity.ImageData imageData_=new MultiPlayers4Activity.ImageData(MultiPlayers4Activity.this,16,pictures,true, "host");
                                    gridView.setAdapter(imageData_);
                                    enablePos.add(i);
                                    enablePos.add(t);
                                    if (quest){
                                        int scorestr= Integer.valueOf(questscore.getText().toString());
                                        int scoretext=((2*point*homeid)*(Integer.valueOf(time.getText().toString())/10))+scorestr;
                                        questscore.setText(String.valueOf(scoretext));
                                        roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);


                                    }else if(host){
                                        int scorestr= Integer.valueOf(hostscore.getText().toString());
                                        int scoretext=((2*point*homeid)*(Integer.valueOf(time.getText().toString())/10))+scorestr;
                                        hostscore.setText(String.valueOf(scoretext));
                                        roomRef.child(roomname).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("point").setValue(scoretext);

                                    }

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

        });

    }

    private void getQuestScore() {
        roomRef.child(roomname).child("players").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String state="state";

                        for(DataSnapshot data:snapshot.getChildren()){
System.out.println(data.child("point").getValue(Integer.class));
                                if(data.child("state").getValue(String.class).equals("Quest")){
                                    int questscr= data.child("point").getValue(Integer.class);
                                    System.out.println(questscr);
                                    questscore.setText(String.valueOf(questscr));

                                }




                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
    }
    private void getHostScore() {

        roomRef.child(roomname).child("players").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String state="state";

                        for(DataSnapshot data:snapshot.getChildren()){

                            System.out.println(data.child("point").getValue(Integer.class));
                                if(data.child("state").getValue(String.class).equals("Host")){
                                    int questscr= data.child("point").getValue(Integer.class);
                                    System.out.println(questscr);
                                    hostscore.setText(String.valueOf(questscr));

                                }




                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
    }

    public class ImageData extends BaseAdapter {
        private Context context;
        private int numberofcards;
        private Drawable[] pictures;
        boolean allitem;
        String hostuser;
        public ImageData(Context context,int numberofcards,Drawable[] pictures,boolean allitem,String hostuser){
            this.context=context;
            this.numberofcards=numberofcards;
            this.pictures=pictures;
            this.allitem=allitem;
            this.hostuser=hostuser;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return allitem;
        }

        @Override
        public boolean isEnabled(int position) {
            if(enablePos.contains(position)){

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
            if(quest){
                if(hostuser=="Host"){

                }

            }else if(host){

            }

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
