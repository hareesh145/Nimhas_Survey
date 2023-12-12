package com.ganesh.nimhans.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.R;
import com.ganesh.nimhans.model.child.EligibleResponse;

import java.util.List;

public class EligibleChildAdapter extends RecyclerView.Adapter<EligibleChildAdapter.EligibleChildHolder> {

    private final Activity activity;
    private final List<EligibleResponse> eligibleResponses;

    public EligibleChildAdapter(Activity activity, List<EligibleResponse> eligibleResponses) {
        this.activity = activity;
        this.eligibleResponses = eligibleResponses;
    }

    @NonNull
    @Override
    public EligibleChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EligibleChildHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EligibleChildHolder holder, int position) {
        holder.bind(eligibleResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return eligibleResponses.size();
    }

    class EligibleChildHolder extends RecyclerView.ViewHolder {
        TextView child_parent_name, child_name, child_id;

        public EligibleChildHolder(@NonNull View itemView) {
            super(itemView);
            child_parent_name = itemView.findViewById(R.id.child_parent_name);
            child_name = itemView.findViewById(R.id.child_name);
            child_id = itemView.findViewById(R.id.child_id);
        }

        public void bind(EligibleResponse eligibleResponse) {
            child_parent_name.setText("Parent Name : " + eligibleResponse.surveySection.demographics.respodentName);
            child_name.setText("Child Name : " + eligibleResponse.qno9);
            child_id.setText("Child ID : " + eligibleResponse.surveySection.demographics.randamId + "" + eligibleResponse.qno8);
        }
    }
}
