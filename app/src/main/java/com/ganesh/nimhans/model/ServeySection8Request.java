package com.ganesh.nimhans.model;

public class ServeySection8Request {


    private String section8Respondent;
    private String section8Gr;
     public int qno108;
     public int qno109;
     public int qno110;
     public int qno111;
     public int qno112;

    public String getSection8Respondent() {
        return section8Respondent;
    }

    public void setSection8Respondent(String section8Respondent) {
        this.section8Respondent = section8Respondent;
    }

    public int getQno108() {
        return qno108;
    }

    public void setQno108(int qno108) {
        this.qno108 = qno108;
    }

    public int getQno109() {
        return qno109;
    }

    public void setQno109(int qno109) {
        this.qno109 = qno109;
    }

    public int getQno110() {
        return qno110;
    }

    public void setQno110(int qno110) {
        this.qno110 = qno110;
    }

    public int getQno111() {
        return qno111;
    }

    public void setQno111(int qno111) {
        this.qno111 = qno111;
    }

    public int getQno112() {
        return qno112;
    }

    public void setQno112(int qno112) {
        this.qno112 = qno112;
    }

    @Override
    public String toString() {
        return "ServeySection8Request{" +
                "section8Respondent='" + section8Respondent + '\'' +
                ", qno108=" + qno108 +
                ", qno109=" + qno109 +
                ", qno110=" + qno110 +
                ", qno111=" + qno111 +
                ", qno112=" + qno112 +
                '}';
    }

    public String getSection8Gr() {
        return section8Gr;
    }

    public void setSection8Gr(String section8Gr) {
        this.section8Gr = section8Gr;
    }
}
