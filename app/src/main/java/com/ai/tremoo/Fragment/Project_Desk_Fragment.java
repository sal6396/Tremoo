package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.ai.tremoo.MainActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ai.tremoo.Adapter.ProjectAdapter;
import com.ai.tremoo.Constants;
import com.ai.tremoo.Models.ProjectModel;
import com.ai.tremoo.R;
import com.ai.tremoo.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Project_Desk_Fragment extends Fragment {

    private List<ProjectModel> projectList;
    private ProjectAdapter adapter;
    private ProgressDialog progressDialog;
    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project__desk_, container, false);

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
        String dynamicTitle = "Projects"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);


        // Set the ActionBar title

        projectList = new ArrayList<>();
        adapter = new ProjectAdapter(requireContext(), projectList);

        ListView listView = view.findViewById(R.id.listViewLT);
        listView.setAdapter(adapter);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);

        // Make an API request to fetch project data
        fetchProjectData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected project
                ProjectModel selectedProject = projectList.get(position);

                // Create a bundle to pass the project ID to the Project_Details_Fragment
                Bundle bundle = new Bundle();
                bundle.putString("projectId", selectedProject.getProjectId());

                // Create an instance of the Project_Details_Fragment
                Project_Details_Fragment projectDetailsFragment = new Project_Details_Fragment();
                projectDetailsFragment.setArguments(bundle);

                // Replace the current fragment with Project_Details_Fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, projectDetailsFragment);
                transaction.addToBackStack(null); // Optional: Adds to back stack
                transaction.commit();
            }
        });

        return view;
    }

    private void fetchProjectData() {
        // Create a JsonObjectRequest to get the project data from the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Constants.URL_GET_PROJECTS,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        // Clear existing data
                        projectList.clear();

                        try {
                            // Check if the "data" field exists in the JSON response
                            if (response.has("data")) {
                                JSONArray dataArray = response.getJSONArray("data");

                                // Parse the JSON array and populate the projectList
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject projectJson = dataArray.getJSONObject(i);
                                    String id = projectJson.getString("id");
                                    String projectId = projectJson.getString("puid");
                                    String projectDetails = projectJson.getString("details");
                                    String projectName = projectJson.getString("title");
                                    String projectStatus = projectJson.getString("status");

                                    // Get the "category" object
                                    JSONObject categoryObject = projectJson.getJSONObject("category");
                                    String categoryName = categoryObject.getString("name");

                                    String projectExpiry = projectJson.getString("expiry_date");

                                    ProjectModel project = new ProjectModel(id,projectId, projectName, projectDetails, projectStatus, categoryName, projectExpiry);
                                    projectList.add(project);
                                }
                            } else {
                                // Handle the case where "data" field is missing
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Notify the adapter that data has changed
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // Handle errors, display an error message, or take appropriate action
                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
                    }
                }
        );
        // Add the request to the Volley request queue
//        RequestHandler.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Home_Fragment())
                .commit();
    }
}
