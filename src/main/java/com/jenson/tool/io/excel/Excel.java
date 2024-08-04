package com.jenson.tool.io.excel;

import com.jenson.tool.io.ExcelUtil;
import com.jenson.tool.util.PoiUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Excel {

    private String filePath;
    private Workbook workbook;
    private Map<String, Sheet> sheetMap = new HashMap<>();

    public Excel createWorkBook() {
        workbook = new XSSFWorkbook();
        return this;
    }

    public Excel copyWorkBook(XSSFWorkbook copyXSSFWorkbook) {
        workbook = new XSSFWorkbook();
        for (int i = 0; i < copyXSSFWorkbook.getNumberOfSheets(); i++) {
            PoiUtil.copySheet(copyXSSFWorkbook.getSheetAt(i), workbook.createSheet());
        }
        return this;
    }

    public org.apache.poi.ss.usermodel.Sheet copySheet(XSSFWorkbook xSSFWorkbook, String sheetName) {
        org.apache.poi.ss.usermodel.Sheet copySheet = xSSFWorkbook.getSheet(sheetName);
        org.apache.poi.ss.usermodel.Sheet sheet;
        if (Objects.nonNull(workbook.getSheet(sheetName))) {
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.createSheet(sheetName);
            PoiUtil.copySheet(copySheet, sheet);
        }
        return sheet;
    }

    public Excel templateFillSheet(XSSFWorkbook xSSFWorkbook, String sheetName, Map<String, String> data) {
        ExcelUtil.templateFill(copySheet(xSSFWorkbook, sheetName), data);
        return this;
    }

    public Excel templateFillSheet(XSSFWorkbook xSSFWorkbook, String sheetName, List<Map<String, String>> data) {
        ExcelUtil.templateFill(copySheet(xSSFWorkbook, sheetName), data);
        return this;
    }

    public Excel readTemplate(Integer numberIfSheet) {
        workbook.getSheetAt(numberIfSheet);
        return this;
    }

    public Excel addSheet(Sheet sheet) {
        sheetMap.put(sheet.getName(), sheet);
        return this;
    }

    public Sheet getSheet(String name) {
        return sheetMap.get(name);
    }

    public List<Sheet> getSheet() {
        return new ArrayList<>(sheetMap.values());
    }

    public Excel execute() {
        sheetMap.forEach((name, sheetValue) -> {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(name);
            sheetValue.getRowMap().forEach((rowIndex, rowValue) -> {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIndex);
                rowValue.getCellMap().forEach((cellIndex, value) -> {
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellIndex);
                    cell.setCellValue(value);
                });
            });
        });
        return this;
    }

    public void download() {
        try {
            //创建一个工作表 07版本使用xlsx结尾！
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            //关闭流
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("excel表生成完毕");
    }
}
