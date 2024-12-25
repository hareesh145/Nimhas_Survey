package com.ganesh.nimhans.adapter;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS10_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS11_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_3_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS6_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7A_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7B_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS8_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_3_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.R;
import com.ganesh.nimhans.activity.Section12Activity;
import com.ganesh.nimhans.activity.Section3cActivity;
import com.ganesh.nimhans.activity.Section6Activity;
import com.ganesh.nimhans.activity.Section7aActivity;
import com.ganesh.nimhans.activity.Section7bActivity;
import com.ganesh.nimhans.model.child.PendingListModel;
import com.ganesh.nimhans.utils.PreferenceConnector;
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
        TextView child_parent_name, child_name, child_id, child_age, district, taluka, village, address, child_status, parent_status, mobile_number, next_visit_date, next_visit_time, pnext_visit_date, pnext_visit_time;

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
            mobile_number = itemView.findViewById(R.id.mobile_number);
            next_visit_time = itemView.findViewById(R.id.next_visit_time);
            next_visit_date = itemView.findViewById(R.id.next_visit_date);
            pnext_visit_time = itemView.findViewById(R.id.pnext_visit_time);
            pnext_visit_date = itemView.findViewById(R.id.pnext_visit_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceConnector.writeString(activity, RCADS6_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS4_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS7A_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS7B_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS8_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS9_1_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS9_2_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS9_3_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS10_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS11_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS5_1_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS5_2_RESULT, "");
                    PreferenceConnector.writeString(activity, RCADS5_3_RESULT, "");

                    if (eligibleResponses.get(getBindingAdapterPosition()).childStatus == null){
                        if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 2 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 3) {
                            //If the age is greater than 1 & less than 2
                            Toast.makeText(activity.getApplicationContext(), "Section7aActivity",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, Section7aActivity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        } else if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 4 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 5) {
                            //If the age is greater than 1 & less than 2
                            Intent intent = new Intent(activity, Section7bActivity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                            Toast.makeText(activity.getApplicationContext(), "Section7bActivity",Toast.LENGTH_LONG).show();
                        } else if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 0 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 1) {
                            //If the age is greater than 1 & less than 2
                            Intent intent = new Intent(activity, Section12Activity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                            Toast.makeText(activity.getApplicationContext(), "Section12Activity",Toast.LENGTH_LONG).show();

                        }else {
                            Intent intent = new Intent(activity, Section6Activity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        }

                    }else if ("Interview Partially Completed".equalsIgnoreCase(eligibleResponses.get(getBindingAdapterPosition()).childStatus)
                            || "Interview Pending".equalsIgnoreCase(eligibleResponses.get(getBindingAdapterPosition()).childStatus)) {
                        Intent intent = new Intent(activity, Section3cActivity.class);
                        intent.putExtra("isFromPendingList", true);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                        intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                        intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                        intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                        intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                        activity.startActivity(intent);
                    } else if ("Interview Partially Completed".equalsIgnoreCase(eligibleResponses.get(getBindingAdapterPosition()).parentStatus)
                            || "Interview Pending".equalsIgnoreCase(eligibleResponses.get(getBindingAdapterPosition()).parentStatus)) {
                        if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 2 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 3) {
                            //If the age is greater than 1 & less than 2
                            Intent intent = new Intent(activity, Section7aActivity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        } else if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 4 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 5) {
                            //If the age is greater than 1 & less than 2
                            Intent intent = new Intent(activity, Section7bActivity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        } else if (eligibleResponses.get(getAdapterPosition()).houseHold.qno12 >= 0 && eligibleResponses.get(getAdapterPosition()).houseHold.qno12 <= 1) {
                            //If the age is greater than 1 & less than 2
                            Intent intent = new Intent(activity, Section12Activity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        }else {
                            Intent intent = new Intent(activity, Section6Activity.class);
                            intent.putExtra("isFromPendingList", true);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponses.get(getAdapterPosition()).houseHold);
                            intent.putExtra(SURVEY_ID, eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.surveyId);
                            intent.putExtra(DEMO_GRAPHIC_ID, Long.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.surveySection.demographics.demographicsId));
                            intent.putExtra(NO_OF_CHILDERNS, eligibleResponses.size());
                            intent.putExtra(AGE_ID, String.valueOf(eligibleResponses.get(getAdapterPosition()).houseHold.qno12));
                            activity.startActivity(intent);
                        }
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
            if (eligibleResponse.childStatus != null) {
                if (eligibleResponse.childStatus.equals("Interview Partially Completed")) {
                    next_visit_date.setVisibility(View.VISIBLE);
                    next_visit_time.setVisibility(View.VISIBLE);
                    child_status.setText("Child Status : " + eligibleResponse.childStatus);
                    next_visit_date.setText("C.Next visit date : " + eligibleResponse.childPCDate);
                    next_visit_time.setText("C.Next visit time : " + eligibleResponse.childPCTime);
                } else {
                    child_status.setText("Child Status : " + eligibleResponse.childStatus);
                    next_visit_date.setVisibility(View.GONE);
                    next_visit_time.setVisibility(View.GONE);
                }
            } else {
                child_status.setText("Child Status : N/A");
            }
            if (eligibleResponse.parentStatus != null) {
                if (eligibleResponse.parentStatus.equals("Interview Partially Completed")) {
                    pnext_visit_date.setVisibility(View.VISIBLE);
                    pnext_visit_time.setVisibility(View.VISIBLE);
                    parent_status.setText("Parent Status : " + eligibleResponse.parentStatus);
                    pnext_visit_date.setText("P.Next visit date : " + eligibleResponse.parentPCDate);
                    pnext_visit_time.setText("P.Next visit time : " + eligibleResponse.parentPCTime);
                } else {
                    parent_status.setText("Parent Status : " + eligibleResponse.parentStatus);
                    pnext_visit_date.setVisibility(View.GONE);
                    pnext_visit_time.setVisibility(View.GONE);
                }
            } else {
                parent_status.setText("Parent Status : N/A");
            }

            mobile_number.setText("Mobile Number : " + eligibleResponse.houseHold.surveySection.demographics.mobileno);
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
