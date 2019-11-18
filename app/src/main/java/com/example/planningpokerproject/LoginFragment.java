package com.example.planningpokerproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment {

    private EditText lUserName;
    private EditText lPassword;
    private Button lLoginButton, lRegisterButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String username,password;


    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstace){
        View view = inflater.inflate(R.layout.fragment_login,container, false);

        initialization(view);



        lRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new RegisterFragment());
                fr.commit();
            }
        });

        lLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCheck(username);

            }
        });




        return view;
    }

    private void userCheck(final String username){
        // Read from the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(username);

        myRef.child("Password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String kod = dataSnapshot.getValue().toString().replace("{","");
              String kod2 = kod.replace("=","");
              String kod3 = kod2.replace("}","");
              String kod5 = kod3.replace("Password","");




                Log.d("barack",kod5);




                if(kod5.equals(password)){
                    Log.d("alma",password);
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container,new RegisterFragment());
                    fr.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void initialization (View view){
        lLoginButton = view.findViewById(R.id.lLoginButton);
        lRegisterButton = view.findViewById(R.id.lRegisterButton);

        lUserName = view.findViewById(R.id.lLoginName);
        lPassword = view.findViewById(R.id.lLoginPassword);

        //lUserName.getText().toString();
        //lPassword.getText().toString();

        username = lUserName.getText().toString();
        password = lPassword.getText().toString();

    }
}
