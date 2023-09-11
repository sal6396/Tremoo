package com.example.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tremoo.R;


public class Payments_Fragment extends Fragment {

    TextView tvManage;
    boolean isFirstTime = true;

    private void changeTextViewColor(TextView button) {
        // Change the background color of the button
        int backgroundColor = R.color.textcolor;
        button.setBackgroundColor(getResources().getColor(backgroundColor));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payments_, container, false);

        tvManage = view.findViewById(R.id.managePay);

        if (isFirstTime) {
            // Change the appearance of the TextView on the first time
            changeTextViewColor(tvManage);
            isFirstTime = false;
        }

        return view;
    }
}
