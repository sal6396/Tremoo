package com.ai.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ai.tremoo.MainActivity;
import com.ai.tremoo.R;

public class Projects_Fragment extends Fragment {

//    private List<ProjectModel> projectList;
//    private ProjectAdapter adapter;

    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects_, container, false);



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
        String dynamicTitle = "My Projects"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);


//        projectList = new ArrayList<>();
//        adapter = new ProjectAdapter(requireContext(), projectList);
//
//        ListView listView = view.findViewById(R.id.listViewMyProjects);
//        listView.setAdapter(adapter);
//
//        // Make an API request to fetch project data
//        fetchProjectData();

        return view;
    }

//    private void fetchProjectData() {
//        // Create a JsonObjectRequest to get the project data from the server
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                Constants.URL_GET_PROJECTS,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Clear existing data
//                        projectList.clear();
//
//                        try {
//                            // Check if the "data" field exists in the JSON response
//                            if (response.has("data")) {
//                                JSONArray dataArray = response.getJSONArray("data");
//
//                                // Parse the JSON array and populate the projectList
//                                for (int i = 0; i < dataArray.length(); i++) {
//                                    JSONObject projectJson = dataArray.getJSONObject(i);
//                                    String projectId = projectJson.getString("puid");
//                                    String projectName = projectJson.getString("title");
//                                    String projectStatus = projectJson.getString("status");
//
//                                    // Get the "category" object
//                                    JSONObject categoryObject = projectJson.getJSONObject("category");
//                                    String categoryName = categoryObject.getString("name");
//
//                                    String projectExpiry = projectJson.getString("expiry_date");
//
//                                    ProjectModel project = new ProjectModel(projectId, projectName, projectStatus, categoryName, projectExpiry);
//                                    projectList.add(project);
//                                }
//                            } else {
//                                // Handle the case where "data" field is missing
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        // Notify the adapter that data has changed
//                        adapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors, display an error message or take appropriate action
//                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
//                    }
//                }
//        );
//
//        // Add the request to the Volley request queue
//        RequestHandler.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
//    }

    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Home_Fragment())
                .commit();
    }
}
