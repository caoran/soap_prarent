package com.boco.soap.cmnet.util;

import com.boco.soap.cmnet.entity.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ExcelUtils {


    private Workbook workbook;
    private OutputStream os;
    private String pattern;// 日期格式
    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public ExcelUtils(Workbook workboook) {
        this.workbook = workboook;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr 将file.getInputStream()获取的输入流
     * @param fileName file.getOriginalFilename()获取的原文件名
     */
    public static  Workbook getWorkbook(InputStream inStr, String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 获取某sheet某行某列的数据
     * @param sheetIx
     * @param rowNum
     * @param colNum
     * @return
     * @throws Exception
     */
    public String readCell(int sheetIx, int rowNum, int colNum)throws Exception {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowNum);
        if (row == null){
            return  null;
        }
        Cell cell = row.getCell(colNum);
        if (cell == null){
            return null;
        }
        return cell.getStringCellValue();
    }



    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();

            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data,65535);

            wb.write(out);
        } finally {
            wb.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data,int sheetSize) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());

        if(data.getRows().size()==0 || data.getRows()==null){
            //throw new ExcelException("数据源中没有任何数据");
        }

        if(sheetSize>65535 || sheetSize<1){
            sheetSize=65535;
        }


        //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
        //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
        //1.计算一共有多少个工作表
        int totalSize=data.getRows().size();
        int sheetNum=totalSize/sheetSize;
        for(int i=0; i<=sheetNum; i++) {
            int start=sheetSize*sheetNum;
            int end=sheetSize*(sheetNum+1)>totalSize?totalSize:sheetSize*(sheetNum+1);
            writeRowsToExcel(wb, sheet, data.getRows().subList(start,end),data.getSqlColumns(), rowIndex);
            autoSizeColumns(sheet, data.getTitles().size() + 1);
        }

    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));
        titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private  static <T> int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<T> rows,List<String> sqlColumns, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        dataStyle.setWrapText(true);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (T rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;
            Object cellData;
            for (String sqlColumn : sqlColumns) {
                Cell cell = dataRow.createCell(colIndex);
                if (rowData instanceof Map){
                    Map<String,Object> map=(Map) rowData;
                    cellData=map.get(sqlColumn);
                }else {
                    cellData =ReflectionUtils.getFieldValue(rowData, sqlColumn);
                }
                if (cellData != null) {
                    cell.setCellValue( cellData.toString().replaceAll("<br/>","\r\n"));
                } else {
                    cell.setCellValue("");
                }

                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(BorderSide.TOP, color);
        style.setBorderColor(BorderSide.LEFT, color);
        style.setBorderColor(BorderSide.RIGHT, color);
        style.setBorderColor(BorderSide.BOTTOM, color);
    }


    protected String convertVal(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = DateUtils.formatDate(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        double contentNumber = cell.getNumericCellValue();
                        if ((contentNumber - (int) contentNumber) < 4.9E-324D) {
                            cellValue = Integer.toString((int) contentNumber);
                        } else {
                            DecimalFormat dfs = new DecimalFormat("0");
                            cellValue = dfs.format(cell.getNumericCellValue());
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BLANK:
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }

    protected String replaceContent(String content) {
        if (content.equals("")) {
            return "";
        }
        content = content.trim();
        content = content.replace("–", "-");
        content = content.replace("、", ",");
        content = content.replace("，", ",");
        content = content.replace(", ,", ",");
        content = content.replace("\r\n", "");
        content = content.replace("\n", "");
        content = content.replace(';', ',');
        content = content.replace("?", "");
        content = content.replace("  ", "");
        content = content.replace("？", "");
        content = content.replace(" ", "");
        content = content.endsWith(",") ? content.substring(0, content.length() - 1) : content;

        return content;
    }
}
