package com.ganesh.nimhans.utils;

public class GenericQuestion {


    public GenericQuestion(int questionNo, String questionDesc) {
        this.questionNo = questionNo;
        this.questionDesc = questionDesc;
    }

    public int questionNo;
    public String questionDesc;

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }
}
