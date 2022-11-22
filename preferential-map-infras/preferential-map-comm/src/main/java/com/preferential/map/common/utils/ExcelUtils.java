package com.preferential.map.common.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    @SneakyThrows
    public static List<List<String>> readExcel(String path, int tableIndex) {
        InputStream inputStream = new FileInputStream(path);
        Workbook workbook = null;
        if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            workbook = new XSSFWorkbook(inputStream);
        }

        List<List<String>> data = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(tableIndex);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();
            for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                Cell cell = row.getCell(index);
                cell.setCellType(CellType.STRING);
                String value = cell.getStringCellValue();
                rowData.add(value);
            }
            data.add(rowData);
        }
        return data;
    }

}