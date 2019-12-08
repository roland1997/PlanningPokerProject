package com.example.planningpokerproject.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.planningpokerproject.Objects.MyAdapter;
import com.example.planningpokerproject.Objects.Question;
import com.example.planningpokerproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CreateRoomFragment extends Fragment {

    private EditText rID, rPassword, rQuestion;
    private Button rCreate;
    private String ID, Password, Question1, username;
    private static final String USERNAME = "userName";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private ArrayList<Question> listing;
    private MyAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_room, container, false);
        initialization(view);
        create();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listing = new ArrayList<Question>();

        myRef = FirebaseDatabase.getInstance().getReference("Admins").child("roland");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot i: dataSnapshot.getChildren()){
                    Question  newQestion = null;
                    Question question = new Question();
                    if(!i.getKey().equals("Password")) {
                        question.setQuestionID(i.getKey());

                    }
                    for(DataSnapshot j: i.getChildren()) {
                        if(!j.getKey().equals("Question")){
                            question.setQuestionPASS(j.getValue().toString());
                            newQestion.setQuestionPASS(j.getValue().toString());
                        }
                        for (DataSnapshot k : j.getChildren()) {

                            question.setQuestion(k.getKey());
                            newQestion =new Question(question.getQuestionID(),question.getQuestion());
                            listing.add(newQestion);
                        }
                    }
                }
                adapter = new MyAdapter(view.getContext(),listing);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void create() {
        rCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = rID.getText().toString();
                Password = rPassword.getText().toString();
                Question1 = rQuestion.getText().toString();

                Question question = new Question(ID, Password, Question1);
                question.setQuestionID(ID);
                question.setQuestionPASS(Password);
                question.setQuestion(Question1);

                checkID(question);


            }
        });
    }

    @SuppressLint("CommitPrefEdits")
    private void createQuestion(Question question) {

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Groups").child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("1").setValue("");
        myRef.child("Groups").child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("2").setValue("");
        myRef.child("Groups").child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("3").setValue("");
        myRef.child("Groups").child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("4").setValue("");
        myRef.child("Groups").child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("5").setValue("");
        myRef.child("Groups").child(question.getQuestionID()).child("RoomCode").setValue(question.getQuestionPASS());


        if (username != null) {

            myRef.child("Admins").child(username).child(question.getQuestionID()).child("RoomCode").setValue(question.getQuestionPASS());
            myRef.child("Admins").child(username).child(question.getQuestionID()).child("Question").child(question.getQuestion()).child("s").setValue("");

        }


    }

    private void checkID(final Question question) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Groups").child(ID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    checkMyRooms(question);

                } else {
                    createQuestion(question);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkMyRooms(final Question question) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Admins").child(username).child(ID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    createQuestion(question);
                } else {
                    Toast.makeText(getContext(), "This ID is already used", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialization(View view) {
        rID = view.findViewById(R.id.roomID);
        rPassword = view.findViewById(R.id.roomPassword);
        rQuestion = view.findViewById(R.id.roomQuestion);
        rCreate = view.findViewById(R.id.buttonRoomCreate);
        recyclerView = view.findViewById(R.id.room_list);

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
