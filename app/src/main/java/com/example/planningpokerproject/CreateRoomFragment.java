package com.example.planningpokerproject;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.getIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoomFragment extends Fragment {

    private EditText rID,rPassword,rQuestion;
    private Button rCreate;
    private String ID,Password,Question,username;
    private static final String USERNAME= "userName";
    private DatabaseReference myRef;
    private FirebaseDatabase database;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_room,container, false);

        initialization(view);

        rCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = rID.getText().toString();
                Password = rPassword.getText().toString();
                Question = rQuestion.getText().toString();

                Log.d("alma1",ID);
                Log.d("alma2",Password);
                Log.d("alma3",Question);

                checkID();

            }
        });


        return view;
    }

    @SuppressLint("CommitPrefEdits")
    private void createQuestion(){

        myRef = FirebaseDatabase.getInstance().getReference();

            myRef.child("Groups").child(ID).child("Question").child(Question).child("1").setValue("");
            myRef.child("Groups").child(ID).child("Question").child(Question).child("2").setValue("");
            myRef.child("Groups").child(ID).child("Question").child(Question).child("3").setValue("");
            myRef.child("Groups").child(ID).child("Question").child(Question).child("4").setValue("");
            myRef.child("Groups").child(ID).child("Question").child(Question).child("5").setValue("");

        if (username != null) {
            myRef.child("Admins").child(username).child(ID).child(Question).setValue("");
        }
    }

    private void checkID(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Groups").child(ID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()!=null){
                    checkMyRooms();
                    //Toast.makeText(getContext(),"This ID is already used", Toast.LENGTH_SHORT).show();
                }
                else{
                    createQuestion();
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

    }

    private void checkMyRooms(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Admins").child(username).child(ID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    createQuestion();
                }
                else{
                    Toast.makeText(getContext(),"This ID is already used", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initialization(View view){
        rID = view.findViewById(R.id.roomID);
        rPassword = view.findViewById(R.id.roomPassword);
        rQuestion = view.findViewById(R.id.roomQuestion);
        rCreate = view.findViewById(R.id.buttonRoomCreate);

        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
        }

    }

    public static CreateRoomFragment newInstance(String text) {
        CreateRoomFragment fragment = new CreateRoomFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, text);
        fragment.setArguments(args);
        return fragment;
    }
}
