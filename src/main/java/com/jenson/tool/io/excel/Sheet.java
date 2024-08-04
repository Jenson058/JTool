package com.jenson.tool.io.excel;

import com.jenson.tool.base.KeyValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Sheet {
    private String name;
    private Map<Integer, Row> rowMap = new HashMap<>();
    private Integer rowIndex = 0;
    private List<KeyValue<String, String>> headerList = new ArrayList<>();


    public Sheet(String name) {
        this.name = name;
    }

    public Sheet addRow(Row row) {
        rowMap.put(rowIndex, row);
        rowIndex++;
        return this;
    }

    public Row getRow(Integer index) {
        return rowMap.get(index);
    }

    public List<Row> getRow() {
        return new ArrayList<>(rowMap.values());
    }

    public Sheet addHeader(String key, String value) {
        headerList.add(new KeyValue<>(key, value));
        return this;
    }

    public Sheet asList(List<Map<String, Object>> mapList, Boolean isHeader) {
        if (isHeader) {
            Row headerRow = new Row();
            headerList.forEach(header -> {
                headerRow.addCell(header.getValue());
            });
            addRow(headerRow);
            mapList.forEach(row -> {
                Row body = new Row();
                headerList.forEach(header -> {
                    body.addCell(row.get(header.getKey()).toString());
                });
                addRow(body);
            });
        } else {
            mapList.forEach(row -> {
                Row body = new Row();
                row.forEach((key, value) -> {
                    body.addCell(value.toString());
                });
                addRow(body);
            });
        }
        return this;
    }
}
