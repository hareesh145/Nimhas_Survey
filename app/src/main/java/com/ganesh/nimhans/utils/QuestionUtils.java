package com.ganesh.nimhans.utils;


import android.content.Context;
import android.content.res.Resources;

import com.ganesh.nimhans.R;

import java.util.ArrayList;

public class QuestionUtils {

    public static ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(addQuestion(67, "In the past three months, how often have you used the substance"));
        questions.add(addQuestion(68, "During the past three months, how often have you had a strong desire or urge to use "));
        questions.add(addQuestion(69, "During the past three months, how often has your use of  led to health, social, legal or financial problems?"));
        questions.add(addQuestion(70, "During the past three months, how often have you failed to do what was normally expected of you because of your use of "));
        questions.add(addQuestion(71, "Has a friend or relative or anyone else ever expressed concern about your use of "));
        questions.add(addQuestion(72, "Have you ever tried to cut down on using but failed?"));
        return questions;
    }

    private static Question addQuestion(int questionNo, String questionDesc) {
        Question question = new Question();
        question.setQuestionNo(questionNo);
        question.setQuestionDesc(questionDesc);
        question.setQuestionADesc("Tobacco products (cigarettes, chewing tobacco, cigars, etc.)");
        question.setQuestionBDesc("Alcoholic beverages (beer, wine, spirits, etc.)");
        question.setQuestionCDesc("Cannabis (marijuana, pot, grass, hash, etc.)");
        question.setQuestionDDesc("Cocaine (coke, crack, etc.)");
        question.setQuestionEDesc("Amphetamine-type stimulants (speed, meth, ecstasy, etc.)");
        question.setQuestionFDesc("Inhalants (nitrous, glue, petrol, paint thinner, etc.)");
        question.setQuestionGDesc("Sedatives or sleeping pills (diazepam, alprazolam, flunitrazepam, midazolam, etc.)");
        question.setQuestionHDesc("Hallucinogens (LSD, acid, mushrooms, trips, ketamine, etc.)");
        question.setQuestionIDesc("Opioids (heroin, morphine, methadone, buprenorphine, codeine, etc.)");
        question.setQuestionJDesc("Other ");

        return question;
    }


   /* public static ArrayList<GenericQuestion> getSection6Questions(){
        ArrayList<GenericQuestion> genericQuestions=new ArrayList<>();
        String Is_your_child_74 =  Resources.getSystem().getString(R.string.is_your_child_slow_in_academic_learning);
        String Is_your_child_75 =  Resources.getSystem().getString(R.string.is_your_child_slow_in_non_academic_activities);
        String Is_your_child_76 =  Resources.getSystem().getString(R.string.does_your_child_need_support_for_activities_of_daily_living);
        String Is_your_child_77 =  Resources.getSystem().getString(R.string.is_your_child_having_difficulty_in_socialization_or_communicating_with_others);
        genericQuestions.add(new GenericQuestion(74,Is_your_child_74));
        genericQuestions.add(new GenericQuestion(75,Is_your_child_75));
        genericQuestions.add(new GenericQuestion(76,Is_your_child_76));
        genericQuestions.add(new GenericQuestion(77,Is_your_child_77));

        return genericQuestions;
    }*/
    public static ArrayList<GenericQuestion> getSection6Questions(Context context) {
        ArrayList<GenericQuestion> genericQuestions = new ArrayList<>();
        genericQuestions.add(new GenericQuestion(74, context.getString(R.string.is_your_child_slow_in_academic_learning)));
        genericQuestions.add(new GenericQuestion(75, context.getString(R.string.is_your_child_slow_in_non_academic_activities)));
        genericQuestions.add(new GenericQuestion(76, context.getString(R.string.does_your_child_need_support_for_activities_of_daily_living)));
        genericQuestions.add(new GenericQuestion(77, context.getString(R.string.is_your_child_having_difficulty_in_socialization_or_communicating_with_others)));

        return genericQuestions;
}

}
