package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ai.tremoo.MainActivity;
import com.ai.tremoo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home_Fragment extends Fragment {

    private ImageView menuButton;

    CardView userProfile,projectDesk,myProject,payments;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_, container, false);

        menuButton = rootView.findViewById(R.id.menu);
        menuButton.setVisibility(View.VISIBLE);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawerNavigation();            }

            private void openDrawerNavigation() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).openDrawer();
                }
            }
        });

        userProfile = rootView.findViewById(R.id.cardUserProfile);
        projectDesk = rootView.findViewById(R.id.cardProjectDesk);
        myProject = rootView.findViewById(R.id.cardMyProjects);
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


                // Replace the current fragment with Project_Desk_Fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new Project_Desk_Fragment())
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });


        myProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new Projects_Fragment(), null)
                        .commit();
            }
        });



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

    @Override
    public void onResume() {
        super.onResume();

        // Check if the fragment is attached to the activity and the activity has the support action bar
        if (isAdded() && getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            // Set the title and show the back button in the action bar
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, new Home_Fragment());
            transaction.commit();
            Log.d("FragmentTransaction", "Profile_Fragment replaced with Home_Fragment");

        }
    }


}
