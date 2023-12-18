package com.ganesh.nimhans.adapter;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.R;
import com.ganesh.nimhans.activity.Section3cActivity;
import com.ganesh.nimhans.model.child.EligibleResponse;

import java.util.List;

public class EligibleChildAdapter extends RecyclerView.Adapter<EligibleChildAdapter.EligibleChildHolder> {

    private final Activity activity;
    private final List<EligibleResponse> eligibleResponses;
    private final long demoGraphicsID;
    private final int surveyID;

    public EligibleChildAdapter(Activity activity, List<EligibleResponse> eligibleResponses, long demoGraphicsID, int surveyID) {
        this.activity = activity;
        this.eligibleResponses = eligibleResponses;
        this.demoGraphicsID = demoGraphicsID;
        this.surveyID = surveyID;
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
        TextView child_parent_name, child_name, child_id, child_age,district,taluka,village,address;

        public EligibleChildHolder(@NonNull View itemView) {
            super(itemView);
            child_parent_name = itemView.findViewById(R.id.child_parent_name);
            child_name = itemView.findViewById(R.id.child_name);
            child_id = itemView.findViewById(R.id.child_id);
            child_age = itemView.findViewById(R.id.child_age);
            district = itemView.findViewById(R.id.district);
            taluka = itemView.findViewById(R.id.taluka);
            village = itemView.findViewById(R.id.village);
            address = itemView.findViewById(R.id.address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Section3cActivity.class);
                    intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()));
                    intent.putExtra(SURVEY_ID, surveyID);
                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                    activity.startActivity(intent);
                }
            });

        }

        public void bind(EligibleResponse eligibleResponse) {
            child_parent_name.setText("Parent Name : " + eligibleResponse.surveySection.demographics.respodentName);
            child_name.setText("Child Name : " + eligibleResponse.qno9);
            child_age.setText("Age : " + eligibleResponse.qno12);
            child_id.setText("Child ID : " + eligibleResponse.surveySection.demographics.randamId + "" + eligibleResponse.qno8);
            district.setText("State & District : " + eligibleResponse.surveySection.demographics.state + "&" +eligibleResponse.surveySection.demographics.district);
            taluka.setText("Taluka & Village : " + eligibleResponse.surveySection.demographics.taluka + "&" +eligibleResponse.surveySection.demographics.cityOrTownOrVillage);
            /*village.setText("Village Name : " + eligibleResponse.surveySection.demographics.cityOrTownOrVillage);*/
            address.setText("Address: " + eligibleResponse.surveySection.demographics.address);
        }
    }
}
