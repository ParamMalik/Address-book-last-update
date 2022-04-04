package com.address.book.addressbookapi.helper;

import com.address.book.addressbookapi.entity.ContactEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    static String[] headers = {"ContactId", "FirstName", "LastName", "Email", "isActive", "createdBy", "createdDate", "updatedBy", "updatedDate"};
    static String sheetOne = "Address_Book";

    public static ByteArrayInputStream tutorialsToExcel(List<ContactEntity> contacts) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetOne);

            CellStyle styleOne = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            styleOne.setFont(font);

            styleOne.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
            styleOne.setFillPattern(FillPatternType.BRICKS);


            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {

                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                headerRow.setRowStyle(styleOne);
            }


            int rowIdx = 1;
            for (ContactEntity contact : contacts) {
                Row row = sheet.createRow(rowIdx++);

                CellStyle style = workbook.createCellStyle();

                if (contact.getContactId() % 2 == 0) {

                    // Setting Background color
                    style.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
                    style.setFillPattern(FillPatternType.BIG_SPOTS);
                } else {
                    style.setFillBackgroundColor(IndexedColors.ROSE.getIndex());
                    style.setFillPattern(FillPatternType.DIAMONDS);
                }

                CreationHelper creationHelper = workbook.getCreationHelper();
                CellStyle cellStyle = workbook.createCellStyle();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy hh:mm:ss"));


                row.createCell(0).setCellValue(contact.getContactId());
                row.createCell(1).setCellValue(contact.getFirstName());
                row.createCell(2).setCellValue(contact.getLastName());
                row.createCell(3).setCellValue(contact.getEmailAddress());
                row.createCell(4).setCellValue(contact.getIsActive());
                row.createCell(5).setCellValue(contact.getCreatedBy());

                // This is date cell
                Cell cell = row.createCell(6);
                cell.setCellValue(contact.getCreatedDate());
                cell.setCellStyle(cellStyle);

                row.createCell(7).setCellValue(contact.getUpdatedBy());

                // This is date cell
                Cell cellOne = row.createCell(8);
                cell.setCellValue(contact.getUpdatedDate());
                cell.setCellStyle(cellStyle);

                row.setRowStyle(style);

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}
