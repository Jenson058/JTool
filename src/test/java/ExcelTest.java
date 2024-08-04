
import com.jenson.tool.io.excel.Excel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTest {
    public static void main(String[] args) {
        String filePath = "E:\\template.xlsx";
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(fis);
            Map<String, String> data1 = new HashMap<>();
            Map<String, String> name = new HashMap<>();
            name.put("name","冯新杰");
            data1.put("body1", "1.1");
            data1.put("body2", "1.2");
            data1.put("body3", "1.3");
            List<Map<String,String>> mapList1 = new ArrayList<>();
            mapList1.add(data1);
            mapList1.add(data1);
            mapList1.add(data1);
            mapList1.add(data1);
            Map<String, String> data2 = new HashMap<>();
            data2.put("body4", "2.4");
            data2.put("body5", "2.5");
            List<Map<String,String>> mapList2 = new ArrayList<>();
            mapList2.add(data2);
            mapList2.add(data2);
            mapList2.add(data2);
            mapList2.add(data2);
            new Excel()
                    .setFilePath("E:\\test.xlsx")
                    .createWorkBook()
                    .templateFillSheet((XSSFWorkbook) workbook,"Sheet1",name)
                    .templateFillSheet((XSSFWorkbook) workbook, "Sheet1", mapList1)
                    .templateFillSheet((XSSFWorkbook) workbook, "Sheet1", mapList2)
                    .download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
