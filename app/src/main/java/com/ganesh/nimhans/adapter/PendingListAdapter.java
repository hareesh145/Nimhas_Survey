package com.ganesh.nimhans.adapter;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.R;
import com.ganesh.nimhans.activity.Section3cActivity;
import com.ganesh.nimhans.activity.Section6Activity;
import com.ganesh.nimhans.model.child.PendingListModel;
import com.ganesh.nimhans.utils.StateModel;

import java.util.List;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.EligibleChildHolder> {

    private final Activity activity;
    private final List<PendingListModel> eligibleResponses;
    private List<StateModel> stateModels;

    public PendingListAdapter(Activity activity, List<PendingListModel> eligibleResponses, List<StateModel> stateModels) {
        this.activity = activity;
        this.eligibleResponses = eligibleResponses;
        this.stateModels = stateModels;
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
        TextView child_parent_name, child_name, child_id, child_age, district, taluka, village, address,child_status,parent_status;

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
            child_status = itemView.findViewById(R.id.child_status);
            parent_status = itemView.findViewById(R.id.parent_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("child_status.getText().toString() : ","child_status.getText().toString() :"+child_status.getText().toString());
                    if (child_status.getText().toString().equals("Interview Partially Completed") || child_status.getText().toString().equals("Interview Pending")){
                    Intent intent = new Intent(activity, Section3cActivity.class);
                    intent.putExtra("isFromPendingList",true);
                    intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                    intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                    intent.putExtra(DEMO_GRAPHIC_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId);
                    intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                    intent.putExtra(AGE_ID, eligibleResponses.get(getAdapterPosition()).houseHold.qno12);
                    activity.startActivity(intent);
                    }else {
                        Intent intent = new Intent(activity, Section6Activity.class);
                        intent.putExtra("isFromPendingList", true);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                        intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                        intent.putExtra(DEMO_GRAPHIC_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId);
                        intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                        intent.putExtra(AGE_ID, eligibleResponses.get(getAdapterPosition()).houseHold.qno12);
                        activity.startActivity(intent);
                    }
                }
            });

        }

        public void bind(PendingListModel eligibleResponse) {
            child_parent_name.setText("Name of HoH : " + eligibleResponse.houseHold.surveySection.demographics.respodentName);
            child_name.setText("Child Name : " + eligibleResponse.houseHold.qno9);
            child_age.setText("Age : " + eligibleResponse.houseHold.qno12);
            child_id.setText("Child ID : " + eligibleResponse.houseHold.surveySection.demographics.randamId + "" + eligibleResponse.houseHold.qno8);
            district.setText("District : " + getSelectedDistrictName(eligibleResponse.houseHold.surveySection.demographics.district));
            taluka.setText("Taluka  : " + getSelectedTalukaName(eligibleResponse.houseHold.surveySection.demographics.taluka));
            village.setText("Village : " + getSelectedVillageName(eligibleResponse.houseHold.surveySection.demographics.cityOrTownOrVillage));
            address.setText("Address: " + eligibleResponse.houseHold.surveySection.demographics.address);
            child_status.setText("Child Status : " + eligibleResponse.childStatus );
            parent_status.setText("Parent Status : "+eligibleResponse.parentStatus);
        }


        private String getSelectedVillageName(String selectedVillage) {
            for (StateModel stateModel :
                    stateModels) {
                if (stateModel.villageCode.equals(selectedVillage)) {
                    return stateModel.villageName;
                }
            }
            return "";
        }

        private String getSelectedTalukaName(String selectedVillage) {
            for (StateModel stateModel :
                    stateModels) {
                if (stateModel.subDistrictCode.equals(selectedVillage)) {
                    return stateModel.subDistrictName;
                }
            }
            return "";
        }

        private String getSelectedDistrictName(String selectedVillage) {
            for (StateModel stateModel :
                    stateModels) {
                if (stateModel.districtCode.equals(selectedVillage)) {
                    return stateModel.districtName;
                }
            }
            return "";
        }

        private String getSelectedStateName(String selectedState) {
            for (StateModel stateModel :
                    stateModels) {
                if (stateModel.stateCode.equals(selectedState)) {
                    return stateModel.stateName;
                }
            }
            return "";
        }
    }
}
