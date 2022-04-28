package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.service.ExcelWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExcelWriterImpl implements ExcelWriter {

    private final Workbook workbook;

    public ExcelWriterImpl() {
        this.workbook = new XSSFWorkbook();
    }


    @Override
    public HttpServletResponse write(List<?> data, HttpServletResponse response) {
        Sheet sheetContent = createSheetContent(data);
        writeSheetData(sheetContent, data);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user";
        response.setHeader(headerKey,headerValue);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void writeExcelToFile(String outputPath) {
        try (OutputStream out = new FileOutputStream(outputPath)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSheetData(Sheet sheet, List<?> data) {
        List<String> headers = createHeaderFromClassFields(data.get(0).getClass());
        int i = 0;
        writeRow(headers, sheet.createRow(i++));
        for (Object obj : data) {
            List<Object> rowContent = createContent(headers, obj);
            writeRow(rowContent, sheet.createRow(i++));
        }
    }

    private List<Object> createContent(List<String> headers, Object obj) {
        List<Object> data = new LinkedList<>();
        Class<?> clazz = obj.getClass();
        for (String columnName : headers) {
            try {
                Field declaredField = clazz.getDeclaredField(columnName);
                declaredField.setAccessible(true);
                data.add(declaredField.get(obj));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private List<String> createHeaderFromClassFields(Class<?> clazz) {
        List<String> headers = new LinkedList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            headers.add(field.getName());
        }
        return headers;
    }

    private Sheet createSheetContent(List<?> data) {
        if (data.size() == 0){
            throw new RuntimeException("List is Empty");
        }
        return workbook.createSheet(data.get(0).getClass().getSimpleName().toLowerCase());

    }

    private void writeRow(List<?> data, Row row) {
        for (int i = 0; i < data.size(); i++) {
            Object cellValue = data.get(i);
            if (cellValue != null) {
                Cell cell = row.createCell(i);
//                switch (cell.getCellType()){
//                    case STRING: cell.setCellValue(cellValue.toString()); break;
//                    case BOOLEAN: cell.setCellValue((Boolean) cellValue); break;
//                    case NUMERIC: cell.setCellValue((Double) cellValue); break;
//                }
                if (cellValue instanceof Number) {
                    cell.setCellValue(((Double) cellValue));
                } else if (cellValue instanceof Boolean) {
                    cell.setCellValue((Boolean) cellValue);
                } else if (cellValue instanceof RichTextString) {
                    cell.setCellValue((RichTextString) cellValue);
                } else if (cellValue instanceof Date) {
                    cell.setCellValue((Date) cellValue);
                } else if (cellValue instanceof LocalDate) {
                    cell.setCellValue((LocalDate) cellValue);
                } else if (cellValue instanceof LocalDateTime) {
                    cell.setCellValue((LocalDateTime) cellValue);
                } else if (cellValue instanceof Calendar) {
                    cell.setCellValue((Calendar) cellValue);
                } else {
                    cell.setCellValue(cellValue.toString());
                }
            }
        }
    }
}
