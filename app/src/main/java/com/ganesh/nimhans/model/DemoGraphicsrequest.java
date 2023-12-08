package com.ganesh.nimhans.model;

public class DemoGraphicsrequest {

    final String state;
    final String district;
    final String taluka;
    final String cityOrTownOrVillage;
    final  String houseHoldNo;
    final String locale;
    final String respodentName;
    final String address;
    final String mobileno;
    final String interviewDate;
    final String consentedForStudy;
    final String visit;
    final  String date;
    final  String resultCode;
    final String specify;
    final String nextAgainDate;
    final String supervisorName;
    final String supervisorCode;
    final String questionnaireDate;
    final String userName;
    final String userCode;
    final String dataEntryDate;
    String nextAgainTime = null;

    public DemoGraphicsrequest(String state, String district, String taluka, String cityOrTownOrVillage, String houseHoldNo, String locale, String respodentName, String address, String mobileno, String interviewDate, String consentedForStudy, String visit, String date, String resultCode, String specify, String nextAgainDate,String nextAgainTime, String supervisorName, String supervisorCode, String questionnaireDate, String userName, String userCode,String dataEntryDate) {
        this.state = state;
        this.district = district;
        this.taluka = taluka;
        this.cityOrTownOrVillage = cityOrTownOrVillage;
        this.houseHoldNo = houseHoldNo;
        this.locale = locale;
        this.respodentName = respodentName;
        this.address = address;
        this.mobileno = mobileno;
        this.interviewDate = interviewDate;
        this.consentedForStudy = consentedForStudy;
        this.visit = visit;
        this.date = date;
        this.resultCode = resultCode;
        this.specify = specify;
        this.nextAgainDate = nextAgainDate;
        this.supervisorName = supervisorName;
        this.supervisorCode = supervisorCode;
        this.questionnaireDate = questionnaireDate;
        this.userName = userName;
        this.userCode = userCode;
        this.dataEntryDate = dataEntryDate;
        this.nextAgainTime = nextAgainTime;
    }
}
