package com.example.tremoo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tremoo.R;


public class Membership_Fragment extends Fragment {

    TextView tvMember;
    boolean isFirstTime = true;

    private void changeTextViewColor(TextView button) {
        // Change the background color of the button
        int backgroundColor = R.color.textcolor;
        button.setBackgroundColor(getResources().getColor(backgroundColor));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_membership_, container, false);

        tvMember = view.findViewById(R.id.memberShip);

        if (isFirstTime) {
            // Change the appearance of the TextView on the first time
            changeTextViewColor(tvMember);
            isFirstTime = false;
        }
        return view;
    }
}