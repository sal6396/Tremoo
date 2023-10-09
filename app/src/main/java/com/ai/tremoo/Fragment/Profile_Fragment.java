package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.tremoo.LoginActivity;
import com.ai.tremoo.MainActivity;
import com.ai.tremoo.R;
import com.ai.tremoo.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class Profile_Fragment extends Fragment {
    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

//    private TextInputEditText edUserName, edUserEmail, edCountry, edCity, edGender, edEducation;
//    private ImageView ivImage;
//    private ImageView addButton;
//    private TextView tvProfile;
//    private boolean isFirstTime = true;
//
//    private void changeTextViewColor(TextView textView) {
//        // Change the background color of the textView
//        int backgroundColor = R.color.textcolor;
//        textView.setBackgroundColor(getResources().getColor(backgroundColor));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

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
        String dynamicTitle = "Profile"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);


//        addButton = view.findViewById(R.id.imageView3);
//        tvProfile = view.findViewById(R.id.profile);
//
//        if (isFirstTime) {
//            // Change the appearance of the TextView on the first time
//            changeTextViewColor(tvProfile);
//            isFirstTime = false;
//        }

//        if (!SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
//            // If user not logged in, redirect to login activity
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            getActivity().finish();
//            return view;
//        }
//
//        edUserName = view.findViewById(R.id.textViewUsername);
//        edUserEmail = view.findViewById(R.id.editTextEmail);
//        edCountry = view.findViewById(R.id.editTextCountry);
//        edCity = view.findViewById(R.id.editTextCity);
//        edGender = view.findViewById(R.id.editTextGander);
//        edEducation = view.findViewById(R.id.editTextEducatio);
//        ivImage = view.findViewById(R.id.userImage);
//
//
//        String username = SharedPrefManager.getInstance(getActivity()).getUsername();
//        String userEmail = SharedPrefManager.getInstance(getActivity()).getUserEmail();
//        String education = SharedPrefManager.getInstance(getActivity()).getEducation();
//        String city = SharedPrefManager.getInstance(getActivity()).getCity();
//        String country = SharedPrefManager.getInstance(getActivity()).getCountry();
//        String gender = SharedPrefManager.getInstance(getActivity()).getGender();
//        String image = SharedPrefManager.getInstance(getActivity()).getImage();
//
//        if (username != null) {
//            edUserName.setText(username);
//        }
//        if (userEmail != null) {
//            edUserEmail.setText(userEmail);
//        }
//        if (education != null) {
//            edEducation.setText(education);
//        }
//        if (city != null) {
//           edCity.setText(city);
//        }
//        if (country != null) {
//            edCountry.setText(country);
//        }
//        if (gender != null) {
//            edGender.setText(gender);
//        }
//        if (image != null) {
//            Picasso.get().load(image).into(ivImage); // Using Picasso to load image from URL into ImageView
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
