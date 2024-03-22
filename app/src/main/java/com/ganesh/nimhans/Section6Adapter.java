package com.ganesh.nimhans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.activity.Section6Activity;
import com.ganesh.nimhans.utils.GenericQuestion;

import java.util.ArrayList;

public class Section6Adapter extends RecyclerView.Adapter<Section6Adapter.QuestionHolder> {

    private ArrayList<GenericQuestion> genericQuestions;
    private Section6Activity activity;

    public int selectedPosition = -1;

    public Section6Adapter(ArrayList<GenericQuestion> genericQuestions, Section6Activity activity) {
        this.genericQuestions = genericQuestions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Section6Adapter.QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_6, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Section6Adapter.QuestionHolder holder, int position) {
        holder.bind(genericQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        return genericQuestions.size();
    }

    public boolean isRadioButtonChecked() {

        return false;
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        TextView question_desc, question_txt;
        RadioGroup child_radio_grp;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            question_desc = itemView.findViewById(R.id.question_desc);
            question_txt = itemView.findViewById(R.id.question_no);
            child_radio_grp = itemView.findViewById(R.id.child_radio_grp);
            child_radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.child_strong_disagree:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 1);
                            break;
                        case R.id.child_disagree_btn:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 2);
                            break;
                        case R.id.child_neutral:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 3);
                            break;
                        case R.id.child_agree:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 4);
                            break;
                        case R.id.child_strong_agree:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 5);
                            break;
                        default:
                            activity.setQuestionOption(genericQuestions.get(getAdapterPosition()).questionNo, 0);
                            break;
                    }
                }
            });
        }

        public void bind(GenericQuestion genericQuestion) {
            question_txt.setText("" + genericQuestion.questionNo);
            question_desc.setText(genericQuestion.getQuestionDesc());
        }
    }
}
