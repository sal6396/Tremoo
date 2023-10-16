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
    private List<ProjectsResponse.Project> projects;
    private LayoutInflater inflater;

    public ProjectAdapter(Context context, List<ProjectsResponse.Project> projects) {
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

        ProjectsResponse.Project project = projects.get(position);

        projectIdTextView.setText(project.getPuid());
        projectNameTextView.setText(project.getTitle());
        projectStatusTextView.setText(getStatusText(project.getStatus()));
        projectCategoryTextView.setText(project.getCategory().getName());
        projectProjectExpiryTextView.setText(project.getExpiryDate());

        return convertView;
    }

    private String getStatusText(int status) {
        if (status == 1) {
            return "Active";
        } else {
            return "Inactive"; // You can customize this for other status values
        }
    }

}
