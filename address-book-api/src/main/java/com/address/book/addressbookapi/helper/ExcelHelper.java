package com.address.book.addressbookapi.helper;

import com.address.book.addressbookapi.entity.ContactEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class ExcelHelper {
    private ExcelHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static String[] headers = {"ContactId", "FirstName", "LastName", "Email", "isActive", "createdBy", "createdDate", "updatedBy", "updatedDate"};
    static String sheetOne = "Address_Book";

    public static ByteArrayInputStream contactsToExcel(List<ContactEntity> contacts) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetOne);

            CellStyle styleOne = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            styleOne.setFont(font);


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


                Cell cell1 = row.createCell(0);
                cell1.setCellValue(contact.getContactId());
                cell1.setCellStyle(style);

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(contact.getFirstName());
                cell2.setCellStyle(style);

                Cell cell3 = row.createCell(2);
                cell3.setCellValue(contact.getLastName());
                cell3.setCellStyle(style);

                Cell cell4 = row.createCell(3);
                cell4.setCellValue(contact.getEmailAddress());
                cell4.setCellStyle(style);

                Cell cell5 = row.createCell(4);
                cell5.setCellValue(contact.getIsActive());
                cell5.setCellStyle(style);

                Cell cell6 = row.createCell(5);
                cell6.setCellValue(contact.getCreatedBy());
                cell6.setCellStyle(style);

                // This is date cell
                Cell cell = row.createCell(6);
//                cell.setCellValue(contact.getCreatedDate().toString());
                cell.setCellStyle(style);

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(contact.getUpdatedBy());
                cell7.setCellStyle(style);

                // This is date cell
                Cell cellOne = row.createCell(8);
//                cellOne.setCellValue(contact.getUpdatedDate().toString());
                cellOne.setCellStyle(style);

                // Setting values of mobile Table to the excel
//                List<MobileEntity> mobileEntities = contact.getMobileEntities();

//                for (MobileEntity mobileEntity : mobileEntities) {
//                    Cell cell8 = row.createCell(9);
//                    cell8.setCellValue(mobileEntity.getMobileId());
//                    cell8.setCellStyle(style);
//
//                    Cell cell9 = row.createCell(10);
//                    cell9.setCellValue(mobileEntity.getMobileNumber());
//                    cell9.setCellStyle(style);
//                }


            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new IllegalArgumentException("fail to import data to Excel file: " + e.getMessage());
        }
    }


    // check that file is of excel type
    public static boolean checkExcelFormat(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }

    // convert excel to list of contacts

    public static ArrayList<ContactEntity> convertExcelToListOfProduct(InputStream file) {
        final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ArrayList<ContactEntity> contactEntities = new ArrayList<>();



        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {



            XSSFSheet sheet = workbook.getSheet("Address_Book");

            Integer rows = 0;

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rows == 0) {
                    rows++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();

                Integer cid = 0;

                ContactEntity contact = new ContactEntity();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 0:
                            contact.setContactId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            contact.setFirstName(cell.getStringCellValue());
                            break;

                        case 2:
                            contact.setLastName(cell.getStringCellValue());
                            break;
                        case 3:
                            contact.setEmailAddress(cell.getStringCellValue());
                            break;
                        case 4:
                            contact.setIsActive(cell.getStringCellValue());
                            break;
                        case 5:
                            contact.setCreatedBy(cell.getStringCellValue());
                            break;
                        case 6:
//                            contact.setCreatedDate(cell.getStringCellValue());
                            break;
                        case 7:
                            contact.setUpdatedBy(cell.getStringCellValue());
                            break;
                        case 8:
//                            contact.setUpdatedDate(cell.getStringCellValue());
                            break;
                        default:
                            break;

                    }
                    cid++;
                }
                contactEntities.add(contact);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        stopWatch.stop();

        logger.info("excel Read -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());
        return contactEntities;
    }



}
