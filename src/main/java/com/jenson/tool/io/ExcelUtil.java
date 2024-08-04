package com.jenson.tool.io;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;


public class ExcelUtil {
    private static final String REGEX = "\\{[a-zA-Z0-9_-]+\\}";
    private static final String REGEXLIST = "\\{.[a-zA-Z0-9_-]+\\}";
    Pattern r = Pattern.compile(REGEX);


    public static void templateFill(Sheet template, Map<String, String> objectMap) {
        Map<String, Cell> cellMap = matching(template, REGEX);
        cellMap.forEach((key, value) -> {
            value.setCellValue(objectMap.get(key.replace("{", "").replace("}", "")));
        });
    }

    public static void templateFill(Sheet template, List<Map<String, String>> mapList) {
        Map<String, Cell> cellMap = matching(template, REGEXLIST);
        int rowIndex = -1;
        for (Map<String, String> data : mapList) {
            for (String key : data.keySet()) {
                Cell cell = cellMap.get("{."+key+"}");
                if (Objects.isNull(cell)){
                    break;
                }
                if (rowIndex == -1) {
                    rowIndex = cell.getRowIndex();
                }
                Row row = template.getRow(rowIndex);
                if (Objects.isNull(row)) {
                    row = template.createRow(rowIndex);
                }
                Cell nextCell = row.getCell(cell.getColumnIndex());
                if (Objects.isNull(nextCell)) {
                    nextCell = row.createCell(cell.getColumnIndex());
                }
                nextCell.setCellValue(data.get(key));
            }
            rowIndex++;
        }
    }

    private static Map<String, Cell> matching(Sheet sheet, String regex) {
        Map<String, Cell> cellMap = new HashMap<>();
        sheet.forEach(row -> {
            row.forEach(cell -> {
                String cellValue = getCellDataAsString(cell);
                if (Objects.nonNull(cellValue) && cellValue.matches(regex)) {
                    cellMap.put(cellValue, cell);
                }
            });
        });
        return cellMap;
    }

    private static String getCellDataAsString(Cell cell) {
        if (Objects.isNull(cell)) {
            return null;
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_FORMULA:
                return String.valueOf(cell.getCellFormula());
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_ERROR:
                return null;
            case HSSFCell.CELL_TYPE_BLANK:
                //nothing to do
                return null;
            default:
                return null;
        }
    }
}
