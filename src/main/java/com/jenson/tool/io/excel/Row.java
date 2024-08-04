package com.jenson.tool.io.excel;

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
public class Row {
    private Map<Integer, String> cellMap = new HashMap<>();
    private Integer cellIndex = 0;

    public Row addCell(String cell) {
        cellMap.put(cellIndex, cell);
        cellIndex++;
        return this;
    }

    public String getCell(Integer index) {
        return cellMap.get(index);
    }

    public List<String> getCell() {
        return new ArrayList<>(cellMap.values());
    }
}
