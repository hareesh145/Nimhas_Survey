package com.ganesh.nimhans;

import android.os.Environment;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.model.child.Root;
import com.ganesh.nimhans.model.child.SurveySection;
import com.google.gson.JsonObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Ravishankar.kumar
 */
public class ConvertJsonToExcel {

    /**
     * Convert JSON String to Java List Objects
     *
     * @param
     * @return List<Customer>
     */
    public static List<EligibleResponse> convertJsonString2Objects(String jsonString) {
        List<EligibleResponse> customers = null;

        try {
            customers = new ObjectMapper().readValue(jsonString, new TypeReference<List<EligibleResponse>>() {
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
    public static void writeSurveyReports(List<Root>  customers, String filePath) throws IOException {
        String[] COLUMNs = {"Date of entry","Data Collector Name","Data Collector ID","Start date&time","Latitude","Longitude","Household ID", "State", "District", "Taluka", "City/Town/Village",
                "Locale", "Household no", "Name of the HoH", "Address", "Mobile no","Child ID","Child Name","Gender","Age", "Marital status", "Occupation", "Education","Consented for study","Q.19", "Q.20", "Q.21", "Q.22", "Q.23", "Q.24", "Q.25", "Q.26", "Q.27", "Q.28", "Q.29", "Q.30", "Q.31", "Q.32", "Q.33", "Q.34", "Q.35", "Q.36", "Q.37", "Q.38", "Q.39", "Q.40", "Q.41", "Q.42", "Q.43", "Q.44", "Q.45", "Q.46", "Q.47", "Q.48", "Q.49", "Q.50", "Q.51", "Q.52", "Q.53", "Q.54", "Q.55", "Q.56", "Q.57", "Q.58", "Q.59", "Q.60", "Q.61", "Q.62", "Q.63", "Q.64", "Q.65","Social Phobia (R)","Social Phobia (T)","Panic Disorder (R)","Panic Disorder (T)","Major Depression (R)","Major Depression (T)","Separation Anxiety (R)","Separation Anxiety (T)","Generalized Anxiety (R)","Generalized Anxiety (T)","Obsessive - Compulsive (R)","Obsessive - Compulsive (T)","RCADS self screener", "Q.66A","Q.67A","Q.68A","Q.69A","Q.71A","Q.72A","Q.66B","Q.67B","Q.68B","Q.69B","Q.70B","Q.71B","Q.72B","Q.66C","Q.67C","Q.68C","Q.69C","Q.70C","Q.71C","Q.72C","Q.66D","Q.67D","Q.68D","Q.69D","Q.70D","Q.71D","Q.72D","Q.66E","Q.67E","Q.68E","Q.69E","Q.70E","Q.71E","Q.72E","Q.66F","Q.67F","Q.68F","Q.69F","Q.70F","Q.71F","Q.72F","Q.66G","Q.67G","Q.68G","Q.69G","Q.70G","Q.71G","Q.72G","Q.66H","Q.67H","Q.68H","Q.69H","Q.70H","Q.71H","Q.72H","Q.66I","Q.67I","Q.68I","Q.69I","Q.70I","Q.71I","Q.72I","Q.66J","Specify ","Q.67J","Q.68J","Q.69J","Q.70J","Q.71J","Q.72J","Q.73","Substance (Alcohol)_score","Substance (Alcohol)_screener","Substance (Other)_score","Substance (Other)_screener","Substance (injectable drug)_screener","Child result","6A.Respondent","Specify","Q.73A","Q.73B","Q.73C","Specify","Q.73D","Q.73E","Q.73F","Specify","Q.73G","Q.73H","6B.Respondent","Specify","Q.74","Q.75","Q.76","Q.77","ID_score",
                "ID_screener","7A.Respondent","Specify","Q.78","Q.79","Q.80","Q.81","Q.82","Q.83","Q.84","Q.85","Q.86","Q.87","Q.88","Q.89","Q.90","Q.91","Q.92","Q.93","Q.94","Q.95","Q.96","Q.97","Mchat_asd_score","Mchat_asd_screener","7B.Respondent","Specify","Q.98","Q.99","Q.100","Q.101","Q.102","Q.103","Q.104","Q.105","Q.106","Q.107","IASQ_asd_score","IASQ_asd_screener","8.Respondent","Specify","Q.108","Q.109","Q.110","Q.111","Q.112","SLD_screener","9.Respondent","Specify","Q.113","Q.114","Q.115","Q.116","Q.117","Q.118","Q.119","Q.120","Q.121","Q.122","Q.123","Q.124","Q.125","Q.126","Q.127","Q.128","Q.129","Q.130","Q.131","Q.132","Q.133","Q.134","Q.135","Q.136","Q.137","Q.138","Inattention_subset_score","Inattention_subset_screener","Hyperactivity_subset_score","Hyperactivity_subset_screener","ODD_subset_score","ODD_subset_screener","10.Respondent","Specify","Q.139","Q.140","Q.141","Q.142","Q.143",
                "Q.144","Q.145","Q.146","Q.147","Q.148","Q.149","Q.150","Q.151","Q.152","Q.153","Q.154","Q.155","Q.156","Q.157","Q.158","Q.159","CD_screener","11.Respondent","Specify","Q.160","Q.161","Q.162","Q.163","Q.164","Q.165","Q.166","Q.167","Q.168","Q.169","Q.170","Q.171","Q.172","Q.173","Q.174","Q.175","Q.176","Q.177","Q.178","Q.179","Q.180","Q.181","Q.182","Q.183","Q.184","Q.185","Q.186","Q.187","Q.188","Q.189","Q.190","Q.191","Q.192","Q.193","Q.194","Q.195","Q.196","Q.197","Q.198","Q.199","Q.200","Q.201","Q.202","Q.203","Q.204","Q.205","Q.206","SP (R-score) (P)","SP (T-score) (P)","PD (R-score) (P)","PD (T-score) (P)","MDD (R-score) (P)","MDD (T-score) (P)","SAD (R-score) (P)","SAD (T-score) (P)","GAD (R-score) (P)","GAD (T-score) (P)","OCD (R-score) (P)","OCD (T-score) (P)","RCADS_parent_screener","12.Respondent","Specify","Q.207","Specify","Q.208","Specify","Q.209","Q.210","Q.211","Specify","Q.212","Specify","Q.213","Q.214","Q.215","Q.216A","Q.216B","Q.216C","Q.217","Q.218","Specify","Q.219A","Q.219B","Q.219C","13.Respondent","Specify","Q.220","Q.221","Q.221A","Q.222","Q.223","Q.224","Q.225","Q.226","Q.227","Specify","Q.228","Parent result","End date&time","Latitude","Longitude"};

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("SurveyReport");

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        // CellStyle for Age
        CellStyle ageCellStyle = workbook.createCellStyle();
        ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

        int rowIdx = 1;
        for (Root customer : customers) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(customer.houseHold.surveySection.demographics.dataEntryDate);
            row.createCell(1).setCellValue(customer.houseHold.surveySection.demographics.userName);
            row.createCell(2).setCellValue(customer.houseHold.surveySection.demographics.userCode);
            row.createCell(3).setCellValue(customer.startDateTime);
            row.createCell(4).setCellValue(customer.latitude);
            row.createCell(5).setCellValue(customer.longitude);
            row.createCell(6).setCellValue(customer.houseHold.surveySection.demographics.randamId);
            row.createCell(7).setCellValue(customer.houseHold.surveySection.demographics.state);
            row.createCell(8).setCellValue(customer.houseHold.surveySection.demographics.district);
            row.createCell(9).setCellValue(customer.houseHold.surveySection.demographics.taluka);
            row.createCell(10).setCellValue(customer.houseHold.surveySection.demographics.cityOrTownOrVillage);
            row.createCell(11).setCellValue(customer.houseHold.surveySection.demographics.locale);
            row.createCell(12).setCellValue(customer.houseHold.surveySection.demographics.houseHoldNo);
            row.createCell(13).setCellValue(customer.houseHold.surveySection.demographics.respodentName);
            row.createCell(14).setCellValue(customer.houseHold.surveySection.demographics.address);
            row.createCell(15).setCellValue(customer.houseHold.surveySection.demographics.mobileno);
            row.createCell(16).setCellValue(customer.houseHold.surveySection.demographics.randamId+ "" + customer.houseHold.qno8);
            row.createCell(17).setCellValue(customer.houseHold.qno9);
            row.createCell(18).setCellValue(customer.houseHold.qno11);
            row.createCell(19).setCellValue(customer.houseHold.qno12);
            row.createCell(20).setCellValue(customer.houseHold.qno13);
            row.createCell(21).setCellValue(customer.houseHold.qno14);
            row.createCell(22).setCellValue(customer.houseHold.qno15);
            row.createCell(23).setCellValue(customer.houseHold.surveySection.demographics.consentedForStudy);
            row.createCell(24).setCellValue(customer.qno19);
            row.createCell(25).setCellValue(customer.qno20);
            row.createCell(26).setCellValue(customer.qno21);
            row.createCell(27).setCellValue(customer.qno22);
            row.createCell(28).setCellValue(customer.qno23);
            row.createCell(29).setCellValue(customer.qno24);
            row.createCell(30).setCellValue(customer.qno25);
            row.createCell(31).setCellValue(customer.qno26);
            row.createCell(32).setCellValue(customer.qno27);
            row.createCell(33).setCellValue(customer.qno28);
            row.createCell(34).setCellValue(customer.qno29);
            row.createCell(35).setCellValue(customer.qno30);
            row.createCell(36).setCellValue(customer.qno31);
            row.createCell(37).setCellValue(customer.qno32);
            row.createCell(38).setCellValue(customer.qno33);
            row.createCell(39).setCellValue(customer.qno34);
            row.createCell(40).setCellValue(customer.qno35);
            row.createCell(41).setCellValue(customer.qno36);
            row.createCell(42).setCellValue(customer.qno37);
            row.createCell(43).setCellValue(customer.qno38);
            row.createCell(44).setCellValue(customer.qno39);
            row.createCell(45).setCellValue(customer.qno40);
            row.createCell(46).setCellValue(customer.qno41);
            row.createCell(47).setCellValue(customer.qno42);
            row.createCell(48).setCellValue(customer.qno43);
            row.createCell(49).setCellValue(customer.qno44);
            row.createCell(50).setCellValue(customer.qno45);
            row.createCell(51).setCellValue(customer.qno46);
            row.createCell(52).setCellValue(customer.qno47);
            row.createCell(53).setCellValue(customer.qno48);
            row.createCell(54).setCellValue(customer.qno49);
            row.createCell(55).setCellValue(customer.qno50);
            row.createCell(56).setCellValue(customer.qno51);
            row.createCell(57).setCellValue(customer.qno52);
            row.createCell(58).setCellValue(customer.qno53);
            row.createCell(59).setCellValue(customer.qno54);
            row.createCell(60).setCellValue(customer.qno55);
            row.createCell(61).setCellValue(customer.qno56);
            row.createCell(62).setCellValue(customer.qno57);
            row.createCell(63).setCellValue(customer.qno58);
            row.createCell(64).setCellValue(customer.qno59);
            row.createCell(65).setCellValue(customer.qno60);
            row.createCell(66).setCellValue(customer.qno61);
            row.createCell(67).setCellValue(customer.qno62);
            row.createCell(68).setCellValue(customer.qno63);
            row.createCell(69).setCellValue(customer.qno64);
            row.createCell(70).setCellValue(customer.qno65);
            row.createCell(71).setCellValue(customer.socialPhobia);
            row.createCell(72).setCellValue("");
            row.createCell(73).setCellValue(customer.panicDisorder);
            row.createCell(74).setCellValue("");
            row.createCell(75).setCellValue(customer.majorDepression);
            row.createCell(76).setCellValue("");
            row.createCell(77).setCellValue(customer.separationAnxiety);
            row.createCell(78).setCellValue("");
            row.createCell(79).setCellValue(customer.generalizedAnxiety);
            row.createCell(80).setCellValue("");
            row.createCell(81).setCellValue(customer.obsessiveCompulsive);
            row.createCell(82).setCellValue("");
            row.createCell(83).setCellValue("");
            row.createCell(84).setCellValue(customer.qno66a);
            row.createCell(85).setCellValue(customer.qno67a);
            row.createCell(86).setCellValue(customer.qno68a);
            row.createCell(87).setCellValue(customer.qno69a);
            row.createCell(88).setCellValue(customer.qno71a);
            row.createCell(89).setCellValue(customer.qno72a);
            row.createCell(90).setCellValue(customer.qno66a);
            row.createCell(91).setCellValue(customer.qno67b);
            row.createCell(92).setCellValue(customer.qno68b);
            row.createCell(93).setCellValue(customer.qno69b);
            row.createCell(94).setCellValue(customer.qno70b);
            row.createCell(95).setCellValue(customer.qno71b);
            row.createCell(96).setCellValue(customer.qno72b);
            row.createCell(97).setCellValue(customer.qno66c);
            row.createCell(98).setCellValue(customer.qno67c);
            row.createCell(99).setCellValue(customer.qno68c);
            row.createCell(100).setCellValue(customer.qno69c);
            row.createCell(101).setCellValue(customer.qno70c);
            row.createCell(102).setCellValue(customer.qno71c);
            row.createCell(103).setCellValue(customer.qno72c);
            row.createCell(104).setCellValue(customer.qno66d);
            row.createCell(105).setCellValue(customer.qno67d);
            row.createCell(106).setCellValue(customer.qno68d);
            row.createCell(107).setCellValue(customer.qno69d);
            row.createCell(108).setCellValue(customer.qno70d);
            row.createCell(109).setCellValue(customer.qno71d);
            row.createCell(110).setCellValue(customer.qno72d);
            row.createCell(111).setCellValue(customer.qno66e);
            row.createCell(112).setCellValue(customer.qno67e);
            row.createCell(113).setCellValue(customer.qno68e);
            row.createCell(114).setCellValue(customer.qno69e);
            row.createCell(115).setCellValue(customer.qno70e);
            row.createCell(116).setCellValue(customer.qno71e);
            row.createCell(117).setCellValue(customer.qno72e);
            row.createCell(118).setCellValue(customer.qno66f);
            row.createCell(119).setCellValue(customer.qno67f);
            row.createCell(120).setCellValue(customer.qno68f);
            row.createCell(121).setCellValue(customer.qno69f);
            row.createCell(122).setCellValue(customer.qno70f);
            row.createCell(123).setCellValue(customer.qno71f);
            row.createCell(124).setCellValue(customer.qno72f);
            row.createCell(125).setCellValue(customer.qno66g);
            row.createCell(126).setCellValue(customer.qno67g);
            row.createCell(127).setCellValue(customer.qno68g);
            row.createCell(128).setCellValue(customer.qno69g);
            row.createCell(129).setCellValue(customer.qno70g);
            row.createCell(130).setCellValue(customer.qno71g);
            row.createCell(131).setCellValue(customer.qno72g);
            row.createCell(132).setCellValue(customer.qno66h);
            row.createCell(133).setCellValue(customer.qno67h);
            row.createCell(134).setCellValue(customer.qno68h);
            row.createCell(135).setCellValue(customer.qno69h);
            row.createCell(136).setCellValue(customer.qno70h);
            row.createCell(137).setCellValue(customer.qno71h);
            row.createCell(138).setCellValue(customer.qno72h);
            row.createCell(139).setCellValue(customer.qno66i);
            row.createCell(140).setCellValue(customer.qno67i);
            row.createCell(141).setCellValue(customer.qno68i);
            row.createCell(142).setCellValue(customer.qno69i);
            row.createCell(143).setCellValue(customer.qno70i);
            row.createCell(144).setCellValue(customer.qno71i);
            row.createCell(145).setCellValue(customer.qno72i);
            row.createCell(146).setCellValue(customer.qno66j);
            row.createCell(147).setCellValue("");
            row.createCell(148).setCellValue(customer.qno67j);
            row.createCell(149).setCellValue(customer.qno68j);
            row.createCell(150).setCellValue(customer.qno69j);
            row.createCell(151).setCellValue(customer.qno70j);
            row.createCell(152).setCellValue(customer.qno71j);
            row.createCell(153).setCellValue(customer.qno72j);
            row.createCell(154).setCellValue(customer.qno73);
            row.createCell(155).setCellValue(customer.alcoholScore);
            row.createCell(156).setCellValue(customer.otherScore);
            row.createCell(157).setCellValue(customer.injectableScore);
            row.createCell(158).setCellValue(customer.assistResult);
            row.createCell(159).setCellValue(customer.section6Arespondent);
            row.createCell(160).setCellValue(customer.childStatus);
            row.createCell(161).setCellValue(customer.section6Arespondent);
            row.createCell(162).setCellValue("");
            row.createCell(163).setCellValue(customer.qno73a);
            row.createCell(164).setCellValue(customer.qno73b);
            row.createCell(165).setCellValue(customer.qno73c);
            row.createCell(166).setCellValue("");
            row.createCell(167).setCellValue(customer.qno73d);
            row.createCell(168).setCellValue(customer.qno73e);
            row.createCell(169).setCellValue(customer.qno73f);
            row.createCell(170).setCellValue("");
            row.createCell(171).setCellValue(customer.qno73g);
            row.createCell(172).setCellValue(customer.qno73h);
            row.createCell(173).setCellValue("");
            row.createCell(174).setCellValue("");
            row.createCell(175).setCellValue(customer.qno74);
            row.createCell(176).setCellValue(customer.qno75);
            row.createCell(177).setCellValue(customer.qno76);
            row.createCell(178).setCellValue(customer.qno77);
            row.createCell(179).setCellValue(customer.idResult);
            row.createCell(180).setCellValue("");
            row.createCell(181).setCellValue(customer.mchatRespondent);
            row.createCell(182).setCellValue("");
            row.createCell(183).setCellValue(customer.qno78);
            row.createCell(184).setCellValue(customer.qno79);
            row.createCell(185).setCellValue(customer.qno80);
            row.createCell(186).setCellValue(customer.qno81);
            row.createCell(187).setCellValue(customer.qno82);
            row.createCell(188).setCellValue(customer.qno83);
            row.createCell(189).setCellValue(customer.qno84);
            row.createCell(190).setCellValue(customer.qno85);
            row.createCell(191).setCellValue(customer.qno86);
            row.createCell(192).setCellValue(customer.qno87);
            row.createCell(193).setCellValue(customer.qno88);
            row.createCell(194).setCellValue(customer.qno89);
            row.createCell(195).setCellValue(customer.qno90);
            row.createCell(196).setCellValue(customer.qno91);
            row.createCell(197).setCellValue(customer.qno92);
            row.createCell(198).setCellValue(customer.qno93);
            row.createCell(199).setCellValue(customer.qno94);
            row.createCell(200).setCellValue(customer.qno95);
            row.createCell(201).setCellValue(customer.qno96);
            row.createCell(202).setCellValue(customer.qno97);
            row.createCell(203).setCellValue(customer.mchatResult);
            row.createCell(204).setCellValue("");
            row.createCell(205).setCellValue(customer.iasqRespondent);
            row.createCell(206).setCellValue("");
            row.createCell(207).setCellValue(customer.qno98);
            row.createCell(208).setCellValue(customer.qno99);
            row.createCell(209).setCellValue(customer.qno100);
            row.createCell(210).setCellValue(customer.qno101);
            row.createCell(211).setCellValue(customer.qno102);
            row.createCell(212).setCellValue(customer.qno103);
            row.createCell(213).setCellValue(customer.qno104);
            row.createCell(214).setCellValue(customer.qno105);
            row.createCell(215).setCellValue(customer.qno106);
            row.createCell(216).setCellValue(customer.qno107);
            row.createCell(217).setCellValue(customer.iasqResult);
            row.createCell(218).setCellValue("");
            row.createCell(219).setCellValue(customer.section8Respondent);
            row.createCell(220).setCellValue("");
            row.createCell(221).setCellValue(customer.qno108);
            row.createCell(222).setCellValue(customer.qno109);
            row.createCell(223).setCellValue(customer.qno110);
            row.createCell(224).setCellValue(customer.qno111);
            row.createCell(225).setCellValue(customer.qno112);
            row.createCell(226).setCellValue(customer.sldResult);
            row.createCell(227).setCellValue(customer.section9Respondent);
            row.createCell(228).setCellValue("");
            row.createCell(229).setCellValue(customer.qno113);
            row.createCell(230).setCellValue(customer.qno114);
            row.createCell(231).setCellValue(customer.qno115);
            row.createCell(232).setCellValue(customer.qno116);
            row.createCell(233).setCellValue(customer.qno117);
            row.createCell(234).setCellValue(customer.qno118);
            row.createCell(235).setCellValue(customer.qno119);
            row.createCell(236).setCellValue(customer.qno120);
            row.createCell(237).setCellValue(customer.qno121);
            row.createCell(238).setCellValue(customer.qno122);
            row.createCell(239).setCellValue(customer.qno123);
            row.createCell(240).setCellValue(customer.qno124);
            row.createCell(241).setCellValue(customer.qno125);
            row.createCell(242).setCellValue(customer.qno126);
            row.createCell(243).setCellValue(customer.qno127);
            row.createCell(244).setCellValue(customer.qno128);
            row.createCell(245).setCellValue(customer.qno129);
            row.createCell(246).setCellValue(customer.qno130);
            row.createCell(247).setCellValue(customer.qno131);
            row.createCell(248).setCellValue(customer.qno132);
            row.createCell(249).setCellValue(customer.qno133);
            row.createCell(250).setCellValue(customer.qno134);
            row.createCell(251).setCellValue(customer.qno135);
            row.createCell(252).setCellValue(customer.qno136);
            row.createCell(253).setCellValue(customer.qno137);
            row.createCell(254).setCellValue(customer.qno138);
            row.createCell(255).setCellValue(customer.inattentionSubsetScore);
            row.createCell(256).setCellValue("");
            row.createCell(257).setCellValue(customer.hyperAcctivitySubsetScore);
            row.createCell(258).setCellValue("");
            row.createCell(259).setCellValue(customer.oddSubsetScore);
            row.createCell(260).setCellValue("");
            row.createCell(261).setCellValue(customer.section10Respondent);
            row.createCell(262).setCellValue("");
            row.createCell(263).setCellValue(customer.qno139);
            row.createCell(264).setCellValue(customer.qno140);
            row.createCell(265).setCellValue(customer.qno141);
            row.createCell(266).setCellValue(customer.qno142);
            row.createCell(267).setCellValue(customer.qno143);
            row.createCell(268).setCellValue(customer.qno144);
            row.createCell(269).setCellValue(customer.qno145);
            row.createCell(270).setCellValue(customer.qno146);
            row.createCell(271).setCellValue(customer.qno147);
            row.createCell(272).setCellValue(customer.qno148);
            row.createCell(273).setCellValue(customer.qno149);
            row.createCell(274).setCellValue(customer.qno150);
            row.createCell(275).setCellValue(customer.qno151);
            row.createCell(276).setCellValue(customer.qno152);
            row.createCell(277).setCellValue(customer.qno153);
            row.createCell(278).setCellValue(customer.qno154);
            row.createCell(279).setCellValue(customer.qno155);
            row.createCell(280).setCellValue(customer.qno156);
            row.createCell(281).setCellValue(customer.qno157);
            row.createCell(282).setCellValue(customer.qno158);
            row.createCell(283).setCellValue(customer.qno159);
            row.createCell(284).setCellValue(customer.cdResult);
            row.createCell(285).setCellValue(customer.section11Respondent);
            row.createCell(286).setCellValue("");
            row.createCell(287).setCellValue(customer.qno160);
            row.createCell(288).setCellValue(customer.qno161);
            row.createCell(289).setCellValue(customer.qno162);
            row.createCell(290).setCellValue(customer.qno163);
            row.createCell(291).setCellValue(customer.qno164);
            row.createCell(292).setCellValue(customer.qno165);
            row.createCell(293).setCellValue(customer.qno166);
            row.createCell(294).setCellValue(customer.qno167);
            row.createCell(295).setCellValue(customer.qno168);
            row.createCell(296).setCellValue(customer.qno169);
            row.createCell(297).setCellValue(customer.qno170);
            row.createCell(298).setCellValue(customer.qno171);
            row.createCell(299).setCellValue(customer.qno172);
            row.createCell(300).setCellValue(customer.qno173);
            row.createCell(301).setCellValue(customer.qno174);
            row.createCell(302).setCellValue(customer.qno175);
            row.createCell(303).setCellValue(customer.qno176);
            row.createCell(304).setCellValue(customer.qno177);
            row.createCell(305).setCellValue(customer.qno178);
            row.createCell(306).setCellValue(customer.qno179);
            row.createCell(307).setCellValue(customer.qno180);
            row.createCell(308).setCellValue(customer.qno181);
            row.createCell(309).setCellValue(customer.qno182);
            row.createCell(310).setCellValue(customer.qno183);
            row.createCell(311).setCellValue(customer.qno184);
            row.createCell(312).setCellValue(customer.qno185);
            row.createCell(313).setCellValue(customer.qno186);
            row.createCell(314).setCellValue(customer.qno187);
            row.createCell(315).setCellValue(customer.qno188);
            row.createCell(316).setCellValue(customer.qno189);
            row.createCell(317).setCellValue(customer.qno190);
            row.createCell(318).setCellValue(customer.qno191);
            row.createCell(319).setCellValue(customer.qno192);
            row.createCell(320).setCellValue(customer.qno193);
            row.createCell(321).setCellValue(customer.qno194);
            row.createCell(322).setCellValue(customer.qno195);
            row.createCell(323).setCellValue(customer.qno196);
            row.createCell(324).setCellValue(customer.qno197);
            row.createCell(325).setCellValue(customer.qno198);
            row.createCell(326).setCellValue(customer.qno199);
            row.createCell(327).setCellValue(customer.qno200);
            row.createCell(328).setCellValue(customer.qno201);
            row.createCell(329).setCellValue(customer.qno202);
            row.createCell(330).setCellValue(customer.qno203);
            row.createCell(331).setCellValue(customer.qno204);
            row.createCell(332).setCellValue(customer.qno205);
            row.createCell(333).setCellValue(customer.qno206);
            row.createCell(334).setCellValue(customer.parentSocialPhobia);
            row.createCell(335).setCellValue("");
            row.createCell(336).setCellValue(customer.parentPanicDisorder);
            row.createCell(337).setCellValue("");
            row.createCell(338).setCellValue(customer.parentMajorDepression);
            row.createCell(339).setCellValue("");
            row.createCell(340).setCellValue(customer.parentSeparationAnxiety);
            row.createCell(341).setCellValue("");
            row.createCell(342).setCellValue(customer.parentGeneralizedAnxiety);
            row.createCell(343).setCellValue("");
            row.createCell(344).setCellValue(customer.parentObsessiveCompulsive);
            row.createCell(345).setCellValue("");
            row.createCell(346).setCellValue("");
            row.createCell(347).setCellValue(customer.section12Respondent);
            row.createCell(348).setCellValue("");
            row.createCell(349).setCellValue(customer.qno207);
            row.createCell(350).setCellValue("");
            row.createCell(351).setCellValue(customer.qno208);
            row.createCell(352).setCellValue("");
            row.createCell(353).setCellValue(customer.qno209);
            row.createCell(354).setCellValue(customer.qno210);
            row.createCell(355).setCellValue(customer.qno211);
            row.createCell(356).setCellValue("");
            row.createCell(357).setCellValue(customer.qno212);
            row.createCell(358).setCellValue("");
            row.createCell(359).setCellValue(customer.qno213);
            row.createCell(360).setCellValue(customer.qno214);
            row.createCell(361).setCellValue(customer.qno215);
            row.createCell(362).setCellValue("");
            row.createCell(363).setCellValue("");
            row.createCell(364).setCellValue("");
            row.createCell(365).setCellValue(customer.qno217);
            row.createCell(366).setCellValue(customer.qno218);
            row.createCell(367).setCellValue("");
            row.createCell(368).setCellValue(customer.qno219);
            row.createCell(369).setCellValue("");
            row.createCell(370).setCellValue("");
            row.createCell(371).setCellValue("");
            row.createCell(372).setCellValue("");
            row.createCell(373).setCellValue(customer.qno220);
            row.createCell(374).setCellValue(customer.qno221);
            row.createCell(375).setCellValue(customer.qno221);
            row.createCell(376).setCellValue(customer.qno222);
            row.createCell(377).setCellValue(customer.qno223);
            row.createCell(378).setCellValue(customer.qno224);
            row.createCell(379).setCellValue(customer.qno225);
            row.createCell(380).setCellValue(customer.qno226);
            row.createCell(381).setCellValue(customer.qno227);
            row.createCell(382).setCellValue("");
            row.createCell(383).setCellValue(customer.qno228);
            row.createCell(384).setCellValue(customer.parentStatus);
            row.createCell(385).setCellValue(customer.endDateTime);
            row.createCell(386).setCellValue(customer.latitude);
            row.createCell(387).setCellValue(customer.longitude);
        }
        File fileOutput = null;

        fileOutput = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filePath);
        FileOutputStream fileOut = new FileOutputStream(fileOutput);
        workbook.write(fileOut);
        fileOut.close();
    }
    public static void writeHouseHoldTableReport(List<EligibleResponse> customers, String filePath) throws IOException {
        String[] COLUMNs = {"Date of entry","Data Collector Name","Data Collector ID","Date of Interview","Household ID", "Child ID","State", "District", "Taluka", "City/Town/Village",
                "Locale", "Household no", "Name of the HoH", "Address", "Mobile no","Line no" ,"Name of the HH member","Relationship", "Gender", "Age", "Marital status", "Occupation", "Education"};

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("HouseHoldTable");

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        // CellStyle for Age
        CellStyle ageCellStyle = workbook.createCellStyle();
        ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

        int rowIdx = 1;
        for (EligibleResponse customer : customers) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(customer.surveySection.demographics.dataEntryDate);
            row.createCell(1).setCellValue(customer.surveySection.demographics.userName);
            row.createCell(2).setCellValue(customer.surveySection.demographics.userCode);
            row.createCell(3).setCellValue(customer.surveySection.demographics.interviewDate);
            row.createCell(4).setCellValue(customer.surveySection.demographics.randamId);
            row.createCell(5).setCellValue(customer.surveySection.demographics.randamId+ "" +customer.qno8);
            row.createCell(6).setCellValue(customer.surveySection.demographics.state);
            row.createCell(7).setCellValue(customer.surveySection.demographics.district);
            row.createCell(8).setCellValue(customer.surveySection.demographics.taluka);
            row.createCell(9).setCellValue(customer.surveySection.demographics.cityOrTownOrVillage);
            row.createCell(10).setCellValue(customer.surveySection.demographics.locale);
            row.createCell(11).setCellValue(customer.surveySection.demographics.houseHoldNo);
            row.createCell(12).setCellValue(customer.surveySection.demographics.respodentName);
            row.createCell(13).setCellValue(customer.surveySection.demographics.address);
            row.createCell(14).setCellValue(customer.surveySection.demographics.mobileno);
            row.createCell(15).setCellValue(customer.qno8);
            row.createCell(16).setCellValue(customer.qno9);
            row.createCell(17).setCellValue(customer.qno10);
            row.createCell(18).setCellValue(customer.qno11);
            row.createCell(19).setCellValue(customer.qno12);
            row.createCell(20).setCellValue(customer.qno13);
            row.createCell(21).setCellValue(customer.qno14);
            row.createCell(22).setCellValue(customer.qno15);
//            row.createCell(11).setCellValue(customer.demographics.demographicsId);
//            row.createCell(12).setCellValue(customer.demographics.state);
//            row.createCell(13).setCellValue(customer.demographics.district);
//            row.createCell(14).setCellValue(customer.demographics.taluka);
//            row.createCell(15).setCellValue(customer.demographics.cityOrTownOrVillage);
//            row.createCell(16).setCellValue(customer.demographics.houseHoldNo);
//
//            row.createCell(17).setCellValue(customer.demographics.locale);
//            row.createCell(18).setCellValue(customer.demographics.respodentName);
//            row.createCell(18).setCellValue(customer.demographics.address);
//            row.createCell(19).setCellValue(customer.demographics.mobileno);

        }
        File fileOutput = null;

        fileOutput = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filePath);
        FileOutputStream fileOut = new FileOutputStream(fileOutput);
        workbook.write(fileOut);
        fileOut.close();
    }


    public static void writeHouseHoldFormReport(List<SurveySection> customers, String filePath) throws IOException {
        String[] COLUMNs = {"Date of entry","Data Collector Name","Data Collector ID","Date of Interview","Household ID", "State", "District", "Taluka", "City/Town/Village",
                "Locale", "Household no", "Respodent name", "Address", "Mobile no","Consented for study","Religion", "Caste/Tribe", "Marital status", "Have any children", "Son(s)", "Daughter(s)", "Total monthly income", "Total no of people in the HH", "Mental illness", "Substance use","Result"};

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("HouseHoldData");

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        // CellStyle for Age
        CellStyle ageCellStyle = workbook.createCellStyle();
        ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

        int rowIdx = 1;
        for (SurveySection customer : customers) {
            Row row = sheet.createRow(rowIdx++);
            //row.createCell(0).setCellValue(customer.surveyId);
            row.createCell(0).setCellValue(customer.demographics.dataEntryDate);
            row.createCell(1).setCellValue(customer.demographics.userName);
            row.createCell(2).setCellValue(customer.demographics.userCode);
            row.createCell(3).setCellValue(customer.demographics.interviewDate);
            row.createCell(4).setCellValue(customer.demographics.randamId);
            row.createCell(5).setCellValue(customer.demographics.state);
            row.createCell(6).setCellValue(customer.demographics.district);
            row.createCell(7).setCellValue(customer.demographics.taluka);
            row.createCell(8).setCellValue(customer.demographics.cityOrTownOrVillage);
            row.createCell(9).setCellValue(customer.demographics.locale);
            row.createCell(10).setCellValue(customer.demographics.houseHoldNo);
            row.createCell(11).setCellValue(customer.demographics.respodentName);
            row.createCell(12).setCellValue(customer.demographics.address);
            row.createCell(13).setCellValue(customer.demographics.mobileno);
            row.createCell(14).setCellValue(customer.demographics.consentedForStudy);
            row.createCell(15).setCellValue(customer.qno1);
            row.createCell(16).setCellValue(customer.qno2);
            row.createCell(17).setCellValue(customer.qno3);
            row.createCell(18).setCellValue(customer.qno4);
            row.createCell(19).setCellValue(customer.qno5A);
            row.createCell(20).setCellValue(customer.qno5B);
            row.createCell(21).setCellValue(customer.qno6);
            row.createCell(22).setCellValue(customer.qno7);
            row.createCell(23).setCellValue(customer.qno16);
            row.createCell(24).setCellValue(customer.qno17);
            row.createCell(25).setCellValue("");

            //row.createCell(25).setCellValue(customer.demographics.randamId);

        }
        File fileOutput = null;

        fileOutput = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filePath);
        FileOutputStream fileOut = new FileOutputStream(fileOutput);
        workbook.write(fileOut);
        fileOut.close();
    }

    /**
     * Write Java Object Lists to Excel File
     *
     * @param customers
     * @param filePath
     * @throws IOException
     */
    public static void writeObjects2ExcelFile(List<EligibleResponse> customers, String filePath) throws IOException {
        String[] COLUMNs = {"Id", "qno8", "qno9", "qno10", "qno11", "qno12", "qno13", "qno14",};

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("HouseHoldData");

        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        // CellStyle for Age
        CellStyle ageCellStyle = workbook.createCellStyle();
        ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

        int rowIdx = 1;
        for (EligibleResponse customer : customers) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(customer.houseHoldId);
            row.createCell(1).setCellValue(customer.qno8);
            row.createCell(2).setCellValue(customer.qno9);
        }
        File fileOutput = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filePath);
        FileOutputStream fileOut = new FileOutputStream(fileOutput);
        workbook.write(fileOut);
        fileOut.close();
    }
}
