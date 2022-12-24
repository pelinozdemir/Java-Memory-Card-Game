package com.app.memorymaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {
   ListView roomslist;
   FloatingActionButton createroom;
   List<String> rooms;
   String currentplayername;
   String roomname;
   ArrayAdapter<String> arrayAdapter;
   Players players;
    private FirebaseAuth user = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://memorymaster-d4422-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference roomRef = database.getReference("Rooms");
    DatabaseReference userRef=database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        roomslist=findViewById(R.id.roomslist);
        createroom=findViewById(R.id.createroom);
        rooms=new ArrayList<String>();
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
        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    rooms.add(dataSnapshot.getKey().toString());



                arrayAdapter = new ArrayAdapter<String>(RoomsActivity.this, android.R.layout.simple_list_item_1, rooms);
                roomslist.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        roomslist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        roomname=rooms.get(i);
                        players=new Players(currentplayername,"Quest",0);
                        roomRef.child(roomname).child("players").child(user.getCurrentUser().getUid().toString()).setValue(players);
                        roomslist.setEnabled(false);

                        Intent intent=new Intent(RoomsActivity.this,MultiPlayers4Activity.class);
                        intent.putExtra("roomname",roomname);
                        intent.putExtra("role","Quest");

                        startActivity(intent);
                    }
                }
        );
        createroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createroom.setEnabled(false);
                startActivity(new Intent(RoomsActivity.this,CreateRoomActivity.class));
            }
        });

    }
}