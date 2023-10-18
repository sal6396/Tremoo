package com.ai.tremoo.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ai.tremoo.Adapter.ProjectAdapter;
import com.ai.tremoo.Models.ProjectsResponse;
import com.ai.tremoo.R;
import com.ai.tremoo.RequestHandler;
import com.ai.tremoo.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Project_Desk_Fragment extends Fragment {

    private List<ProjectsResponse.Project> projectList;
    private ProjectAdapter adapter;
    private ProgressDialog progressDialog;

    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

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
                onBackPressed(); // Handle back button click here
            }
        });
        String dynamicTitle = "Projects"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);


        projectList = new ArrayList<>();
        adapter = new ProjectAdapter(requireContext(), projectList);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);

        ListView listView = view.findViewById(R.id.listViewLT);
        listView.setAdapter(adapter);

        fetchProjectData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectsResponse.Project selectedProject = projectList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("puid", selectedProject.getPuid());
                bundle.putString("details", selectedProject.getDetails());
                bundle.putString("name", selectedProject.getCategory().getName());
                bundle.putString("expiry_date", selectedProject.getExpiryDate());
                bundle.putString("type", selectedProject.getType());


                Project_Details_Fragment projectDetailsFragment = new Project_Details_Fragment();
                projectDetailsFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, projectDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return view;
    }

    private void fetchProjectData() {
        showProgressDialog();
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());

        // Check if token is saved in SharedPreferences
        if (sharedPrefManager.getToken() != null) {
            String bearerToken = "Bearer " + sharedPrefManager.getToken();

            Call<ProjectsResponse> call = RequestHandler.getInstance().getApi().getProjects(bearerToken);
            call.enqueue(new Callback<ProjectsResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProjectsResponse> call, @NonNull Response<ProjectsResponse> response) {
                    hideProgressDialog();

                    if (response.isSuccessful() && response.body() != null) {
                        ProjectsResponse projectsResponse = response.body();
                        projectList.addAll(projectsResponse.getProjects());
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle unsuccessful response
                        handleErrorResponse(response);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProjectsResponse> call, @NonNull Throwable t) {
                    hideProgressDialog();

                    // Handle failure
                    handleFailure(t);
                }
            });
        } else {
            // Token is not saved in SharedPreferences, handle this case accordingly (e.g., redirect to login screen)
        }
    }

    private void handleErrorResponse(Response<?> response) {
        // Handle different HTTP error codes here and show appropriate error messages to the user
        // For example:
        if (response.code() == 401) {
            // Unauthorized error, handle token expiration or invalid token
        } else {
            // Other HTTP errors, show a generic error message
            showToast("Error: " + response.message());
        }
    }

    private void handleFailure(Throwable t) {
        // Handle network failures, show a generic error message
        showToast("Request failed: " + t.getMessage());
    }

    private void showToast(String message) {
        // Show a Toast message on the UI thread
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Home_Fragment())
                .commit();
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
}
