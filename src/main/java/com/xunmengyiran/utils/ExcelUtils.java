package com.xunmengyiran.utils;

import com.xunmengyiran.constants.Constants;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static org.apache.poi.hssf.record.ExtendedFormatRecord.CENTER;

public class ExcelUtils {

    /**
     * 读取excel的标题
     *
     * @param file
     * @return 数组
     */
    public static String[] readExcelTitle(File file) {
        InputStream is = null;
        XSSFWorkbook xb = null;
        String[] title = new String[0];
        try {
            is = new FileInputStream(file);
            xb = new XSSFWorkbook(is);
            // Sheet工作表
            XSSFSheet xs = xb.getSheetAt(0);
            // 获取首行标题
            XSSFRow xr = xs.getRow(0);
            // 标题总列数
            int colNum = xr.getPhysicalNumberOfCells();
            title = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                title[i] = getCellFormatValue(xr.getCell((short) i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return title;
    }

    public static List<Map> readExcelContent(File file, String[] title) {
        InputStream is = null;
        XSSFWorkbook xb = null;
        List<Map> list = new ArrayList<Map>();
        try {
            is = new FileInputStream(file);
            xb = new XSSFWorkbook(is);
            // Sheet工作表
            XSSFSheet xs = xb.getSheetAt(0);
            // 总行数
            int rowNum = xs.getLastRowNum();

            XSSFRow xr = xs.getRow(0);
            System.out.println("总行数" + rowNum);
            // 获取标题
            if (title != null && title.length > 0) {
                String str = null;
                // 总列数
                int colNum = xr.getPhysicalNumberOfCells();
                System.out.println("总列数" + colNum);
                Map<String, String> content = null;
                // 正文内容应该从第二行开始,第一行为表头的标题
                for (int i = 1; i <= rowNum; i++) {
                    content = new HashMap<String, String>();
                    xr = xs.getRow(i);
                    for (int j = 0; j < colNum; j++) {
                        str = getCellFormatValue(xr.getCell((short) j)).trim();
                        content.put(title[j], str);
                    }
                    list.add(content);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 根据XSSFCell类型设置数据
     *
     * @param xssfCell
     * @return
     */
    private static String getCellFormatValue(XSSFCell xssfCell) {
        String cellvalue = "";
        if (xssfCell != null) {
            // 判断当前Cell的Type
            switch (xssfCell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    // 如果是Date类型则，转化为Data格式
                    if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
                        // 方法1：data格式是带时分秒的：2011-10-12 0:00:00
                        // 方法2：格式是不带带时分秒的：2011-10-12
                        Date date = xssfCell.getDateCellValue();
                        cellvalue = Constants.DATE_FORMAT.sdf4.format(date);
                    } else {
                        // 如果是纯数字,取得当前Cell的数值
                        DecimalFormat df = new DecimalFormat("0");
                        cellvalue = df.format(xssfCell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRING
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = xssfCell.getRichStringCellValue().getString();
                    break;
                default:
                    // 默认的Cell值
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue.trim();
    }

    public static Boolean export(String filePath, String fileName, String[] titleArr, List<T> dataList) {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook(500);
            workbook.setCompressTempFiles(true);
            Sheet sheet = workbook.createSheet("sheet1");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            Row hssfRow = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle hssfCellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setBold(true);
            //居中样式
            hssfCellStyle.setAlignment(CENTER);
            hssfCellStyle.setWrapText(true);
            hssfCellStyle.setFont(font);

            Cell hssfCell;
            for (int i = 0; i < titleArr.length; i++) {
                hssfCell = hssfRow.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titleArr[i]);//列名1
                hssfCell.setCellStyle(hssfCellStyle);//列居中显示
            }
            // 第五步，写入实体数据
            int line = 0;
            for (int i = 0; i < dataList.size(); i++) {
                hssfRow = sheet.createRow(line + 1);
                line++;
                hssfRow.createCell(i).setCellValue(dataList.get(i) == null ? "" : dataList.get(i).toString());
            }
            // 第七步，将文件输出到客户端浏览器
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    File dic = new File(filePath);
                    if (dic.exists()) {
                        if (dic.isDirectory())
                            ;
                        else
                            dic.mkdirs();
                    } else {
                        dic.mkdirs();
                    }
                    String xlsxName = filePath + File.separator + fileName + ".xlsx";
                    fileOutputStream = new FileOutputStream(xlsxName);
                    workbook.write(fileOutputStream);
                    return true;
                } catch (Exception e) {
                }finally {
                    if (fileOutputStream != null){
                        fileOutputStream.close();
                    }
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return false;
    }
}
