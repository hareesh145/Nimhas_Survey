package com.ganesh.nimhans.utils;

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


    public static ArrayList<GenericQuestion> getSection6Questions(){
        ArrayList<GenericQuestion> genericQuestions=new ArrayList<>();
        genericQuestions.add(new GenericQuestion(74,"Is your child slow in academic learning?"));
        genericQuestions.add(new GenericQuestion(75,"Is your child slow in non- academic activities?"));
        genericQuestions.add(new GenericQuestion(76,"Does your child need support for activities of daily living?"));
        genericQuestions.add(new GenericQuestion(77,"Is your child having difficulty in socialization or communicating with others?"));

        return genericQuestions;
    }
}
