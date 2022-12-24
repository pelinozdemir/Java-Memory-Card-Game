package com.app.memorymaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CreateRoomActivity extends AppCompatActivity {
    Button sixgrid;
    Button fourgrid;
    Button twogrid;
    ArrayList<Integer> cardidlist=new ArrayList<Integer>();
    Players players;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference roomRef = database.getReference("Rooms");
    DatabaseReference userRef=database.getReference("Users");
    private FirebaseAuth user = FirebaseAuth.getInstance();
    String currentplayername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        userRef.child(user.getCurrentUser().getUid().toString()).child("username").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){

                        }else{
                            currentplayername=String.valueOf(task.getResult().getValue());

                            System.out.println(currentplayername);
                        }
                    }
                }
        );

        twogrid=findViewById(R.id.twogrid);
        fourgrid=findViewById(R.id.fourgrid);
        sixgrid=findViewById(R.id.sixgrid);
        GridView gridView=findViewById(R.id.grid);
        gridView.setNumColumns(2);
        gridView.setAdapter(new CreateRoomActivity.ImageData(CreateRoomActivity.this,4));

        twogrid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridView.setNumColumns(2);
                gridView.setClickable(false);
                gridView.setAdapter(new CreateRoomActivity.ImageData(CreateRoomActivity.this,4));


                new AlertDialog.Builder(new ContextThemeWrapper(CreateRoomActivity.this, R.style.myDialog))
                        .setTitle("2*2")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Players players=new Players(currentplayername,"Host",0);
                                roomRef.child(currentplayername).child("players").child(user.getCurrentUser().getUid().toString()).setValue(players);
                                Random rn = new Random();
                                int card1 = rn.nextInt(11) ;//G
                                int card2 = rn.nextInt(11) ;
                                int card3 = rn.nextInt(11)+11 ;//S
                                int card4 = rn.nextInt(11)+11;
                                int card5 = rn.nextInt(11)+22 ;//R
                                int card6= rn.nextInt(11)+22 ;
                                int card7 = rn.nextInt(11)+33 ;//H
                                int card8 = rn.nextInt(11)+33 ;
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
                                Rooms4 rooms4=new Rooms4(cardidlist.get(0),
                                        cardidlist.get(1),
                                        cardidlist.get(2),
                                        cardidlist.get(3),
                                        cardidlist.get(4),cardidlist.get(5),cardidlist.get(6),cardidlist.get(7),
                                        cardidlist.get(8),cardidlist.get(9),cardidlist.get(10),cardidlist.get(11),
                                        cardidlist.get(12),cardidlist.get(13),cardidlist.get(14),cardidlist.get(15)

                                );




                                roomRef.child(currentplayername).child("roomdetails").setValue(rooms4);


                                roomRef.child(currentplayername).child("host").setValue("Host");
                                System.out.println(currentplayername);


                                System.out.println(currentplayername);
                                createRoomMethod(4,currentplayername);

                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                //  startActivity(new Intent(getActivity(), Single2PlayerActivity.class));

            }
        });
        fourgrid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridView.setNumColumns(4);
                gridView.setClickable(false);
                gridView.setAdapter(new CreateRoomActivity.ImageData(CreateRoomActivity.this,16));
                userRef.child(user.getCurrentUser().getUid().toString()).child("username").get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(Task<DataSnapshot> task) {
                                if(!task.isSuccessful()){

                                }else{
                                    currentplayername=String.valueOf(task.getResult().getValue());

                                    System.out.println(currentplayername);
                                }
                            }
                        }
                );
                new AlertDialog.Builder(new ContextThemeWrapper(CreateRoomActivity.this, R.style.myDialog))
                        .setTitle("4*4")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Players players=new Players(currentplayername,"Host",0);
                                System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                roomRef.child(currentplayername).child("players").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(players);
                                Random rn = new Random();
                                int card1 = rn.nextInt(11) ;//G
                                int card2 = rn.nextInt(11) ;
                                int card3 = rn.nextInt(11)+11 ;//S
                                int card4 = rn.nextInt(11)+11;
                                int card5 = rn.nextInt(11)+22 ;//R
                                int card6= rn.nextInt(11)+22 ;
                                int card7 = rn.nextInt(11)+33 ;//H
                                int card8 = rn.nextInt(11)+33 ;
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
                                Rooms4 rooms4=new Rooms4(cardidlist.get(0),
                                        cardidlist.get(1),
                                        cardidlist.get(2),
                                        cardidlist.get(3),
                                        cardidlist.get(4),cardidlist.get(5),cardidlist.get(6),cardidlist.get(7),
                                        cardidlist.get(8),cardidlist.get(9),cardidlist.get(10),cardidlist.get(11),
                                        cardidlist.get(12),cardidlist.get(13),cardidlist.get(14),cardidlist.get(15)

                                );




                                roomRef.child(currentplayername).child("roomdetails").setValue(rooms4);


                                roomRef.child(currentplayername).child("host").setValue("Host");
                                System.out.println(currentplayername);


                                System.out.println(currentplayername);
                                createRoomMethod(4,currentplayername);

                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                // startActivity(new Intent(getActivity(),Single4PlayerActivity.class));
            }
        });

        sixgrid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridView.setNumColumns(6);
                gridView.setClickable(false);
                gridView.setAdapter(new CreateRoomActivity.ImageData(CreateRoomActivity.this,36));

                userRef.child(user.getCurrentUser().getUid().toString()).child("username").get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(Task<DataSnapshot> task) {
                                if(!task.isSuccessful()){

                                }else{
                                    currentplayername=String.valueOf(task.getResult().getValue());

                                    System.out.println(currentplayername);
                                }
                            }
                        }
                );
                //startActivity(new Intent(getActivity(), Single6PlayerActivity.class));
                new AlertDialog.Builder(new ContextThemeWrapper(CreateRoomActivity.this, R.style.myDialog))
                        .setTitle("4*4")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                      /*  Players players=new Players(currentplayername,"Host",0);
                                        roomRef.child(currentplayername).child("players").child(user.getCurrentUser().getUid().toString()).setValue(players);
                                        roomRef.child(currentplayername).child("roomdetails").setValue("6");*/

                                Players players=new Players(currentplayername,"Host",0);
                                roomRef.child(currentplayername).child("players").child(user.getCurrentUser().getUid().toString()).setValue(players);
                                Random rn = new Random();
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
                                Rooms6 rooms6=new Rooms6(cardidlist.get(0),
                                        cardidlist.get(1),
                                        cardidlist.get(2),
                                        cardidlist.get(3),
                                        cardidlist.get(4),cardidlist.get(5),cardidlist.get(6),cardidlist.get(7),
                                        cardidlist.get(8),cardidlist.get(9),cardidlist.get(10),cardidlist.get(11),
                                        cardidlist.get(12),cardidlist.get(13),cardidlist.get(14),cardidlist.get(15),
                                        cardidlist.get(16),
                                        cardidlist.get(17),
                                        cardidlist.get(18),
                                        cardidlist.get(19),
                                        cardidlist.get(20),cardidlist.get(21),cardidlist.get(22),cardidlist.get(23),
                                        cardidlist.get(24),cardidlist.get(25),cardidlist.get(26),cardidlist.get(27),
                                        cardidlist.get(28),cardidlist.get(29),cardidlist.get(30),cardidlist.get(31),
                                        cardidlist.get(32),
                                        cardidlist.get(33),
                                        cardidlist.get(34),
                                        cardidlist.get(35)



                                );




                                roomRef.child(currentplayername).child("roomdetails").setValue(rooms6);


                                roomRef.child(currentplayername).child("host").setValue("Host");
                                System.out.println(currentplayername);


                                System.out.println(currentplayername);
                                createRoomMethod(6,currentplayername);


                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });




    }
    private void createRoomMethod(int number,String currentplayername) {
     /*  Intent intent=new Intent(CreateRoomActivity.this,MultiPlayers4Activity.class);
        intent.putExtra("roomname",currentplayername);
        intent.putExtra("role","Host");

        startActivity(intent);*/
        if(number==4){
            Intent intent=new Intent(CreateRoomActivity.this,MultiPlayers4Activity.class);
            intent.putExtra("roomname",currentplayername);
            intent.putExtra("role","Host");

            startActivity(intent);
        }else if(number==6){
            Intent intent=new Intent(CreateRoomActivity.this,MultiPlayer6Activity.class);
            intent.putExtra("roomname",currentplayername);
            intent.putExtra("role","Host");

            startActivity(intent);
        }



           roomRef.addListenerForSingleValueEvent(
                   new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot snapshot) {
                           if(snapshot.hasChild(currentplayername)){

                           }else{
                               if(number==4){

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

        public ImageData(Context context,int numberofcards){
            this.context=context;
            this.numberofcards=numberofcards;

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

                imageView.setLayoutParams(new ViewGroup.LayoutParams(130,250));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }else{


                imageView=(ImageView) view;



            }

            imageView.setImageResource(R.drawable.hogwarts_logo_png_transparent);

            return imageView;
        }
    }

}
