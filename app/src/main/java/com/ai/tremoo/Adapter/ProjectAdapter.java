package com.ai.tremoo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ai.tremoo.Models.ProjectsResponse;
import com.ai.tremoo.R;

import java.util.List;

public class ProjectAdapter extends BaseAdapter {
    private List<ProjectsResponse> projects;
    private LayoutInflater inflater;

    public ProjectAdapter(Context context, List<ProjectsResponse> projects) {
        this.projects = projects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.multi_line, parent, false);
        }

        TextView projectNameTextView = convertView.findViewById(R.id.line_a);
        TextView projectIdTextView = convertView.findViewById(R.id.line_b);
        TextView projectProjectExpiryTextView = convertView.findViewById(R.id.line_c);
        TextView projectCategoryTextView = convertView.findViewById(R.id.line_d);
        TextView projectStatusTextView = convertView.findViewById(R.id.line_f);

        ProjectsResponse project = projects.get(position);

//        projectIdTextView.setText(project.getProjectId());
//        projectNameTextView.setText(project.getProjectName());
//        projectStatusTextView.setText(project.getProjectStatus());
//        projectCategoryTextView.setText(project.getProjectCategory());
//        projectProjectExpiryTextView.setText(project.getProjectExpiry());


        return convertView;
    }
}
