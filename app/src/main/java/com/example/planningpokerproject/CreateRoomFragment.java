package com.example.planningpokerproject;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Intent.getIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoomFragment extends Fragment {

    private EditText rID,rPassword,rQuestion;
    private Button rCreate;
    private String ID,Password,Question,username;
    private DatabaseReference myRef;



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

                createQuestion();

            }
        });


        return view;
    }

    @SuppressLint("CommitPrefEdits")
    private void createQuestion(){



        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Admins").child("roland").child(ID).child("Question").setValue(Question);
        myRef.child("Admins").child("roland").child(ID).child("Password").setValue(Password);

    }
    private void initialization(View view){
        rID = view.findViewById(R.id.roomID);
        rPassword = view.findViewById(R.id.roomPassword);
        rQuestion = view.findViewById(R.id.roomQuestion);
        rCreate = view.findViewById(R.id.buttonRoomCreate);

    }
}
