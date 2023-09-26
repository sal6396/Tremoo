package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.tremoo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home_Fragment extends Fragment {

    CardView userProfile,projectDesk,myProject,payments;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_, container, false);

        userProfile = rootView.findViewById(R.id.cardUserProfile);
        projectDesk = rootView.findViewById(R.id.cardProjectDesk);
        myProject = rootView.findViewById(R.id.cardProject);
        payments = rootView.findViewById(R.id.cardPayment);

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new Profile_Fragment(), null)
                        .commit();
            }
        });

        projectDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the BottomNavigationView (assuming it's a BottomNavigationView)
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation); // Replace with your actual ID
                bottomNavigationView.setVisibility(View.GONE);

                // Replace the current fragment with Project_Desk_Fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new Project_Desk_Fragment())
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });


//        myProject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frameLayout, new Projects_Fragment(), null)
//                        .commit();
//            }
//        });



        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new Payments_Fragment(), null)
                        .commit();
            }
        });


        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }
}
