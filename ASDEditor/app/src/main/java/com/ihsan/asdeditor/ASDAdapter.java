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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ASDAdapter extends RecyclerView.Adapter<ASDAdapter.ViewHolder> {

    ArrayList<Integer> lines;
    ArrayList<Integer> asdlines;
    ArrayList<JSONObject> datas;
    ArrayList<String> currents;
    String id;
    Activity activity;

    public ASDAdapter(ArrayList<Integer> lines, ArrayList<JSONObject> datas, ArrayList<String> currents, Activity activity, ArrayList<Integer> asdlines, String id) {
        this.lines = lines;
        this.datas = datas;
        this.currents = currents;
        this.activity = activity;
        this.asdlines = asdlines;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_project, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText("ASD line " + lines.get(position).toString() + ", " + currents.get(position));
        try {
            holder.subtitle.setText((String) datas.get(position).getJSONArray("parameters").get(0));
        } catch (JSONException e) {
            holder.subtitle.setText("ERROR While getting ASD data");
        }
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, PickAsdActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(holder.body, 0, 0, holder.body.getMeasuredWidth(), holder.body.getMeasuredHeight());
                i.putExtra("line", asdlines.get(position).toString());
                i.putExtra("id", id);
                activity.startActivity(i, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;
        View body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_item);
            subtitle = itemView.findViewById(R.id.subtitle_item);
            body = itemView.findViewById(R.id.body_asd_item);
        }
    }
}
