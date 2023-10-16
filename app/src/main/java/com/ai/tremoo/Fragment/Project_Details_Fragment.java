package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ai.tremoo.Models.Project;
import com.ai.tremoo.R;

import java.util.List;

public class Project_Details_Fragment extends Fragment  {



    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

    private LinearLayout detailsLayout;
    private TextView itemNameTextView;
    private TextView projectActive;
    Button submitData;

    private ProgressDialog progressDialog;


    // Data
    private List<Project> projectList;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project__details_, container, false);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);


        Bundle bundle = getArguments();
        showProgressDialog();
        if (bundle != null) {
            String puid = bundle.getString("puid", "");
            String details = bundle.getString("details", "");
            String name = bundle.getString("name", "");
            String expiryDate = bundle.getString("expiry_date", "");

            TextView puidTextView = rootView.findViewById(R.id.projectId);
            TextView detailsTextView = rootView.findViewById(R.id.projectDetails);
            TextView nameTextView = rootView.findViewById(R.id.categoryName);
            TextView expiryDateTextView = rootView.findViewById(R.id.projectExpiry);

            puidTextView.setText(puid);
            detailsTextView.setText(details);
            nameTextView.setText(name);
            expiryDateTextView.setText(expiryDate);
            hideProgressDialog();
        }



        submitData = rootView.findViewById(R.id.dataSubmission);
        detailsLayout = rootView.findViewById(R.id.detailsLayout);
        itemNameTextView = rootView.findViewById(R.id.itemNameTextView);
//        puId = rootView.findViewById(R.id.projectId);
//        projectCategory = rootView.findViewById(R.id.categoryName);
//        projectDescription = rootView.findViewById(R.id.projectDetails);
//        projectExpiryDate = rootView.findViewById(R.id.projectExpiry);
        projectActive = rootView.findViewById(R.id.projectActive);



        toolbar = rootView.findViewById(R.id.toolbar);
        titleTextView = rootView.findViewById(R.id.title);
        backButton = rootView.findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Handle back button click here
            }
        });
        String dynamicTitle = "Project Details"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);


        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of Submit_Data_Fragment
                Submit_Data_Fragment submitDataFragment = new Submit_Data_Fragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, submitDataFragment); // Use the instance
                transaction.addToBackStack(null); // Optional: Adds to back stack
                transaction.commit();
            }
        });

        return rootView;
    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Project_Desk_Fragment())
                .commit();
    }
}
