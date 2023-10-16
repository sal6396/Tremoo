package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ai.tremoo.Models.Project;
import com.ai.tremoo.R;

import java.util.List;

public class Project_Details_Fragment extends Fragment  {



    // UI elements

    private LinearLayout detailsLayout;
    private TextView itemNameTextView;
    private TextView puId, projectCategory, projectDescription,  projectExpiryDate, projectActive;
    Button submitData;
    ImageButton backDetails;


    // Data
    private List<Project> projectList;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project__details_, container, false);


        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String projectId = bundle.getString("projectId");
//            fetchProjectDetails(projectId); // Pass the projectId to fetch the specific project
//        }


        submitData = rootView.findViewById(R.id.dataSubmission);
        detailsLayout = rootView.findViewById(R.id.detailsLayout);
        itemNameTextView = rootView.findViewById(R.id.itemNameTextView);
        puId = rootView.findViewById(R.id.projectId);
        projectCategory = rootView.findViewById(R.id.categoryName);
        projectDescription = rootView.findViewById(R.id.projectDetails);
        projectExpiryDate = rootView.findViewById(R.id.projectExpiry);
        projectActive = rootView.findViewById(R.id.projectActive);

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

//        backDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create an instance of Submit_Data_Fragment
//                Project_Desk_Fragment projectDeskFragment = new Project_Desk_Fragment();
//
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, projectDeskFragment); // Use the instance
//                transaction.addToBackStack(null); // Optional: Adds to back stack
//                transaction.commit();
//            }
//        });

        return rootView;
    }

//    private void fetchProjectDetails(String projectID) {
//        // Create a JsonObjectRequest to get the project data from the server
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("VolleyResponse", "Response JSON: " + response.toString());
//
//                        try {
//                            // Parse the JSON array of projects
//                            JSONArray projectsArray = response.getJSONArray("data");
//
//
//                            // Iterate through the projects to find the one with matching project ID
//                            for (int i = 0; i < projectsArray.length(); i++) {
//                                JSONObject projectObject = projectsArray.getJSONObject(i);
//                                String projectId = projectObject.getString("puid");
//
//                                if (projectId.equals(projectID)) {
//                                    // Found the project with matching project ID
//                                    String projectDetails = projectObject.getString("details");
//                                    String projectStatus = projectObject.getString("status");
//                                    JSONObject categoryObject = projectObject.getJSONObject("category");
//                                    String categoryName = categoryObject.getString("name");
//                                    String projectExpiry = projectObject.getString("expiry_date");
//
//                                    // Set the data to your TextViews directly
//                                    puId.setText(projectId);
//                                    projectActive.setText(projectStatus);
//                                    projectCategory.setText(categoryName);
//                                    projectExpiryDate.setText(projectExpiry);
//                                    projectDescription.setText(projectDetails);
//
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors, display an error message or take appropriate action
//                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
//                        // Display an error message to the user if needed
//                        requireActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(requireContext(), "Error fetching project details.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//        );
//
//        // Add the request to the Volley request queue
////        RequestHandler.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
//    }
}
