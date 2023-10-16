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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ai.tremoo.Adapter.ProjectAdapter;
import com.ai.tremoo.Models.ProjectsResponse;
import com.ai.tremoo.R;
import com.ai.tremoo.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Project_Desk_Fragment extends Fragment {

    private List<ProjectsResponse> projectList;
    private ProjectAdapter adapter;
    private ProgressDialog progressDialog;
    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;
    private String BEARER_TOKEN;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project__desk_, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        titleTextView = view.findViewById(R.id.title);
        backButton = view.findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String dynamicTitle = "Projects";
        titleTextView.setText(dynamicTitle);

        projectList = new ArrayList<>();
        adapter = new ProjectAdapter(requireContext(), projectList);

        ListView listView = view.findViewById(R.id.listViewLT);
        listView.setAdapter(adapter);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);

        fetchProjectData();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ProjectsResponse selectedProject = projectList.get(position);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("projectId", selectedProject.getProjectId());
//
//                Project_Details_Fragment projectDetailsFragment = new Project_Details_Fragment();
//                projectDetailsFragment.setArguments(bundle);
//
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, projectDetailsFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        return view;
    }

    private void fetchProjectData() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String bearerToken = preferences.getString("BEARER_TOKEN", "");

        Call<List<ProjectsResponse>> call = RequestHandler.getInstance().getApi().getProjects("Bearer " + bearerToken);
        call.enqueue(new Callback<List<ProjectsResponse>>() {
            @Override
            public void onResponse(Call<List<ProjectsResponse>> call, Response<List<ProjectsResponse>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    projectList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle unsuccessful response
                    Log.e("Retrofit", "Response Error: " + response.message());
                    // Show appropriate error message to the user
                }
            }

            @Override
            public void onFailure(Call<List<ProjectsResponse>> call, Throwable t) {
                progressDialog.dismiss();
                // Handle failure
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                // Show appropriate error message to the user
            }
        });
    }



    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Home_Fragment())
                .commit();
    }
}
