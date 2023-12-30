package com.ganesh.nimhans;

import android.os.Environment;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.nimhans.model.child.EligibleResponse;

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

    /**
     * Write Java Object Lists to Excel File
     *
     * @param customers
     * @param filePath
     * @throws IOException
     */
    public static void writeObjects2ExcelFile(List<EligibleResponse> customers, String filePath) throws IOException {
        String[] COLUMNs = {"Id", "qno8", "qno9", "qno10","qno11", "qno12", "qno13", "qno14",};

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
