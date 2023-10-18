package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    private String puid;
    private String details;
    private String name;
    private String expiryDate;
    private String projectType;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project__details_, container, false);

//        progressDialog = new ProgressDialog(requireContext());
//        progressDialog.setCancelable(false);

        // Inside your Project_Details_Fragment after getting the projectType
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putString("projectType", projectType);
        editor.apply();



//        showProgressDialog();
        Bundle bundle = getArguments();
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
//            hideProgressDialog();
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
                // Create a new fragment instance
                Submit_Data_Fragment submitDataFragment = new Submit_Data_Fragment();
                projectType = bundle.getString("type", "");

                // Pass the data to the Submit_Data_Fragment using arguments
                Bundle dataBundle = new Bundle();
                dataBundle.putString("type", projectType); // Add this line to pass the type information
                submitDataFragment.setArguments(dataBundle);
                Log.d("Project_Details_Fragment", "Received arguments - projectType: " + projectType);

                // Begin the fragment transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, submitDataFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });







        return rootView;
    }

    // Implement your logic to determine the type based on the project details here
    private String getType() {
        // For now, I'm returning a placeholder value "image" as an example
        return "image";
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
