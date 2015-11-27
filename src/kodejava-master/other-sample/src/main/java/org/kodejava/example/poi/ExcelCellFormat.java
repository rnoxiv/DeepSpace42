package org.kodejava.example.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelCellFormat {
    public static void main(String[] args) {
        //
        // Create an instance of workbook and sheet
        //
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        //
        // Create an instance of HSSFCellStyle which will be use to format the
        // cell. Here we define the cell top and bottom border and we also
        // define the background color.
        //
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop((short) 6); // double lines border
        style.setBorderBottom((short) 1); // single line border
        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

        //
        // We also define the font that we are going to use for displaying the
        // data of the cell. We set the font to ARIAL with 20pt in size and
        // make it BOLD and give blue as the color.
        //
        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLUE.index);
        style.setFont(font);

        //
        // We create a simple cell, set its value and apply the cell style.
        //
        HSSFRow row = sheet.createRow(1);
        HSSFCell cell = row.createCell(1);
        cell.setCellValue(new HSSFRichTextString("Hi there... It's me again!"));
        cell.setCellStyle(style);
        sheet.autoSizeColumn((short) 1);

        //
        // Finally we write out the workbook into an excel file.
        //
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("ExcelDemo.xls"));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
