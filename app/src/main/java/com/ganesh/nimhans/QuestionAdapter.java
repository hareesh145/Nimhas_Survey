package com.ganesh.nimhans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.nimhans.activity.Section5Activity;
import com.ganesh.nimhans.utils.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {

    private Section5Activity section5Activity;
    private ArrayList<Question> questions;

    public QuestionAdapter(Section5Activity section5Activity, ArrayList<Question> questions) {
        this.section5Activity = section5Activity;
        this.questions = questions;
    }


    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_question_with_options, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void updateQuestion67A(boolean enabled) {

    }

    class QuestionHolder extends RecyclerView.ViewHolder {
        TextView question_desc, question_txt, question_a_desc;
        RadioGroup tobaccoProduct1, alcohol1, cannabis1, cocaine1, amphetamine1, inhalants1, sedatives1, hallucinogens1, opiods1;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            question_desc = itemView.findViewById(R.id.question_desc);
            question_txt = itemView.findViewById(R.id.question);
            tobaccoProduct1 = itemView.findViewById(R.id.tobaccoProduct1);
            alcohol1 = itemView.findViewById(R.id.alcohol1);
            cannabis1 = itemView.findViewById(R.id.cannabis1);
            cocaine1 = itemView.findViewById(R.id.cocaine1);
            amphetamine1 = itemView.findViewById(R.id.amphetamine1);
            inhalants1 = itemView.findViewById(R.id.inhalants1);
            sedatives1 = itemView.findViewById(R.id.sedatives1);
            hallucinogens1 = itemView.findViewById(R.id.hallucinogens1);
            opiods1 = itemView.findViewById(R.id.opiods1);

            tobaccoProduct1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "a", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            alcohol1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "b", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            cannabis1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "c", getCheckedIDValue(group.getContext(), radioBtnText));
            });


            cocaine1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "d", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            amphetamine1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "e", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            inhalants1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "f", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            sedatives1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "g", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            hallucinogens1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "h", getCheckedIDValue(group.getContext(), radioBtnText));
            });

            opiods1.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                String radioBtnText = radioButton.getText().toString();
                section5Activity.updateQuestionOption(questions.get(getAdapterPosition()).getQuestionNo() + "i", getCheckedIDValue(group.getContext(), radioBtnText));
            });
        }

        public void bind(Question question) {
            question_desc.setText(question.questionDesc);
            question_txt.setText("" + question.questionNo);
        }
    }

    private int getCheckedIDValue(Context context, String radioBtnText) {

        if (radioBtnText == context.getResources().getString(R.string.never)) {
            return 0;
        } else if (radioBtnText == context.getResources().getString(R.string.once_or_twice)) {
            return 2;
        } else if (radioBtnText == context.getResources().getString(R.string.monthly)) {
            return 3;
        } else if (radioBtnText == context.getResources().getString(R.string.weekly)) {
            return 4;
        } else if (radioBtnText == context.getResources().getString(R.string.daily_or_almost_daily)) {
            return 5;
        }
        return 0;
    }
}
