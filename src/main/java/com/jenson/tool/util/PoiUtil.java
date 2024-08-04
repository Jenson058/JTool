package com.jenson.tool.util;

import com.jenson.tool.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import java.util.Iterator;
import java.util.function.Supplier;

public class PoiUtil {
    private static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    public static void copySheet(Sheet srcSheet, Sheet desSheet) {
        copySheet(srcSheet, desSheet, true, true, null);
    }

    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyValueFlag) {
        copySheet(srcSheet, desSheet, copyValueFlag, true, null);
    }

    public static void copySheet(Sheet srcSheet, Sheet desSheet, StyleMapping mapping) {
        copySheet(srcSheet, desSheet, true, true, mapping);
    }

    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyStyleFlag, StyleMapping mapping) {
        copySheet(srcSheet, desSheet, true, copyStyleFlag, mapping);
    }

    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        if (srcSheet.getWorkbook() == desSheet.getWorkbook()) {
            logger.warn(new Supplier<String>() {
                @Override
                public String get() {
                    return "统一workbook内复制sheet建议使用 workbook的cloneSheet方法";
                }
            });
        }

        //合并区域处理
        copyMergedRegion(srcSheet, desSheet);

        //行复制
        Iterator<Row> rowIterator = srcSheet.rowIterator();

        int areadlyColunm = 0;
        while (rowIterator.hasNext()) {
            Row srcRow = rowIterator.next();
            Row desRow = desSheet.createRow(srcRow.getRowNum());
            copyRow(srcRow, desRow, copyValueFlag, copyStyleFlag, mapping);

            //调整列宽(增量调整)
            if (srcRow.getPhysicalNumberOfCells() > areadlyColunm) {
                for (int i = areadlyColunm; i < srcRow.getPhysicalNumberOfCells(); i++) {
                    desSheet.setColumnWidth(i, srcSheet.getColumnWidth(i));
                }
                areadlyColunm = srcRow.getPhysicalNumberOfCells();
            }
        }
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow) {
        copyRow(srcRow, desRow, true, true, null);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, boolean copyValueFlag) {
        copyRow(srcRow, desRow, copyValueFlag, true, null);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, StyleMapping mapping) {
        copyRow(srcRow, desRow, true, true, mapping);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, boolean copyStyleFlag, StyleMapping mapping) {
        copyRow(srcRow, desRow, true, copyStyleFlag, mapping);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow,boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        Iterator<Cell> it = srcRow.cellIterator();
        while (it.hasNext()) {
            Cell srcCell = it.next();
            Cell desCell = desRow.createCell(srcCell.getColumnIndex());
            copyCell(srcCell, desCell, copyValueFlag, copyStyleFlag, mapping);
        }
    }

    /**
     * 复制区域（合并单元格）
     */
    public static void copyMergedRegion(Sheet srcSheet, Sheet desSheet) {
        int sheetMergerCount = srcSheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            desSheet.addMergedRegion(srcSheet.getMergedRegion(i));
            CellRangeAddress cellRangeAddress = srcSheet.getMergedRegion(i);
        }
    }

    /**
     * 复制单元格，复制数据，如果同文件，复制样式，不同文件则不复制样式
     */
    public static void copyCell(Cell srcCell, Cell desCell) {
        copyCell(srcCell, desCell, true, true,null);
    }

    /**
     * 复制单元格， 如果同文件，复制样式，不同文件则不复制样式
     * @param copyValueFlag 控制是否复制数据
     */
    public static void copyCell(Cell srcCell, Cell desCell, boolean copyValueFlag) {
        copyCell(srcCell, desCell, copyValueFlag, true, null);
    }


    public static void copyCell(Cell srcCell, Cell desCell,  StyleMapping mapping) {
        copyCell(srcCell, desCell, true, true, mapping);
    }


    public static void copyCell(Cell srcCell, Cell desCell, boolean copyStyleFlag, StyleMapping mapping) {
        copyCell(srcCell, desCell, true, copyStyleFlag, mapping);
    }


    public static void copyCell(Cell srcCell, Cell desCell, boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        Workbook srcBook = srcCell.getSheet().getWorkbook();
        Workbook desBook = desCell.getSheet().getWorkbook();

        //复制样式
        //如果是同一个excel文件内，连带样式一起复制
        if (srcBook == desBook && copyStyleFlag) {
            //同文件，复制引用
            desCell.setCellStyle(srcCell.getCellStyle());
        } else if (copyStyleFlag) {
            //不同文件，通过映射关系复制
            if (null != mapping) {
                short desIndex = mapping.desIndex(srcCell.getCellStyle().getIndex());
                desCell.setCellStyle(desBook.getCellStyleAt(desIndex));
            }
        }

        //复制评论
        if (srcCell.getCellComment() != null) {
            desCell.setCellComment(srcCell.getCellComment());
        }

        //复制内容
        desCell.setCellType(srcCell.getCellType());

        if (copyValueFlag) {
            switch (srcCell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    desCell.setCellValue(srcCell.getStringCellValue());
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    desCell.setCellValue(srcCell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    desCell.setCellFormula(srcCell.getCellFormula());
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    desCell.setCellValue(srcCell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    desCell.setCellValue(srcCell.getErrorCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    //nothing to do
                    break;
                default:
                    break;
            }
        }

    }



    public static  StyleMapping copyCellStyle(Workbook srcBook, Workbook desBook){
        if (null == srcBook || null == desBook) {
            throw new ExcelException("源excel 或 目标excel 不存在");
        }
        if (srcBook.equals(desBook)) {
            throw new ExcelException("不要使用此方法在同一个文件中copy style，同一个excel中复制sheet不需要copy Style");
        }
        if ((srcBook instanceof HSSFWorkbook && desBook instanceof XSSFWorkbook) ||
                (srcBook instanceof XSSFWorkbook && desBook instanceof HSSFWorkbook)) {
            throw new ExcelException("不支持在不同的版本的excel中复制样式）");
        }

//        logger.debug("src中style number:{}, des中style number:{}", srcBook.getNumCellStyles(), desBook.getNumCellStyles());
        short[] src2des = new short[srcBook.getNumCellStyles()];
        short[] des2src = new short[desBook.getNumCellStyles() + srcBook.getNumCellStyles()];

        for(short i=0;i<srcBook.getNumCellStyles();i++){
            //建立双向映射
            CellStyle srcStyle = srcBook.getCellStyleAt(i);
            CellStyle desStyle = desBook.createCellStyle();
            src2des[srcStyle.getIndex()] = desStyle.getIndex();
            des2src[desStyle.getIndex()] = srcStyle.getIndex();

            //复制样式
            desStyle.cloneStyleFrom(srcStyle);
        }


        return new StyleMapping(des2src, src2des);
    }

    /**
     * 存放两个excel文件中的styleTable的映射关系，以便于在复制表格时，在目标文件中获取到对应的样式
     */
    public static class StyleMapping {
        /**
         *
         */
        private short[] des2srcIndexMapping;
        /**
         *
         */
        private short[] src2desIndexMapping;

        /**
         * 不允许其他类创建此类型对象
         */
        private StyleMapping() {
        }

        public StyleMapping(short[] des2srcIndexMapping, short[] src2desIndexMapping) {
            this.des2srcIndexMapping = des2srcIndexMapping;
            this.src2desIndexMapping = src2desIndexMapping;
        }

        public short srcIndex(short desIndex) {
            if (desIndex < 0 || desIndex >= this.des2srcIndexMapping.length) {
                throw new ExcelException("索引越界：源文件styleNum=" + this.des2srcIndexMapping.length + " 访问位置=" + desIndex);
            }
            return this.des2srcIndexMapping[desIndex];
        }


        public short desIndex(short srcIndex) {
            if (srcIndex < 0 || srcIndex >= this.src2desIndexMapping.length) {
                throw new ExcelException("索引越界：源文件styleNum=" + this.src2desIndexMapping.length + " 访问位置=" + srcIndex);
            }

            return this.src2desIndexMapping[srcIndex];
        }
    }
}
