package com.example.planningpokerproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




public class RegisterFragment extends Fragment {



    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstace){
        View view = inflater.inflate(R.layout.fragment_register1,container, false);

        return view;
    }
}
