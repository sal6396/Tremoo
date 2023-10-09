package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.tremoo.MainActivity;
import com.ai.tremoo.R;


public class Payments_Fragment extends Fragment {

    TextView tvManage;
    boolean isFirstTime = true;

    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

//    private void changeTextViewColor(TextView button) {
//        // Change the background color of the button
//        int backgroundColor = R.color.textcolor;
//        button.setBackgroundColor(getResources().getColor(backgroundColor));
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payments_, container, false);


        toolbar = view.findViewById(R.id.toolbar);
        titleTextView = view.findViewById(R.id.title);
        backButton = view.findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Handle back button click here
            }
        });
        String dynamicTitle = "Payments"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);



//        if (isFirstTime) {
//            // Change the appearance of the TextView on the first time
//            changeTextViewColor(tvManage);
//            isFirstTime = false;
//        }

        return view;
    }
    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Home_Fragment())
                .commit();
    }
}
