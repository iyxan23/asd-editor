package com.ihsan.asdeditor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Hashtable;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    ArrayList<Hashtable<String, String>> data;
    Activity activity;

    public ProjectsAdapter(ArrayList<Hashtable<String, String>> datas, Activity activity) {
        this.data = datas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_project, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Hashtable<String, String> currentdata = data.get(position);
        holder.project.setText(currentdata.get("name"));
        holder.other.setText(currentdata.get("coname") + " • " + currentdata.get("package") + " • " + currentdata.get("version"));
        holder.id.setText(currentdata.get("id"));
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PickAsdActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(holder.body, 0, 0, holder.body.getMeasuredWidth(), holder.body.getMeasuredHeight());
                i.putExtra("id", currentdata.get("id"));
                i.putExtra("name", currentdata.get("name"));
                activity.startActivity(i, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView project;
        TextView other;
        TextView id;
        View body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project = itemView.findViewById(R.id.project);
            other = itemView.findViewById(R.id.other);
            body = itemView.findViewById(R.id.body);
            id = itemView.findViewById(R.id.id);
        }
    }
}
