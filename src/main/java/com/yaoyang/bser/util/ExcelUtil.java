package com.yaoyang.bser.util;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * @param sheetName 表名
     * @param header    表头数据
     * @param body      主干数据 body.get(i) 对应 i+1行的所有数据
     */
    public static void generateExcel(String sheetName, List<String> header, List<List<String>> body, OutputStream out) {
        //新建excel报表
        HSSFWorkbook excel = new HSSFWorkbook();
        //添加一个sheet
        HSSFSheet hssfSheet = excel.createSheet(sheetName);
        //往excel表格创建一行，excel的行号是从0开始的
        // 设置表头
        HSSFRow firstRow = hssfSheet.createRow(0);
        for (int columnNum = 0; columnNum < header.size(); columnNum++) {
            //创建单元格
            HSSFCell hssfCell = firstRow.createCell(columnNum);
            //设置单元格的值
            hssfCell.setCellValue(header.size() < columnNum ? "-" : header.get(columnNum));
        }
        // 设置主体数据
        for (int rowNum = 0; rowNum < body.size(); rowNum++) {
            //往excel表格创建一行，excel的行号是从0开始的
            HSSFRow hssfRow = hssfSheet.createRow(rowNum + 1);
            List<String> data = body.get(rowNum);
            for (int columnNum = 0; columnNum < data.size(); columnNum++) {
                //创建单元格
                HSSFCell hssfCell = hssfRow.createCell(columnNum);
                //设置单元格的值
                hssfCell.setCellValue(data.size() < columnNum ? "-" : data.get(columnNum));
            }
        }
        try {
            excel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        List<String> header = new ArrayList<>(); // 第一行数据
        header.add("编号");
        header.add("姓名");
        header.add("性别");
        header.add("手机号");
        List<List<String>> body = new ArrayList<>();
        List<String> data1 = new ArrayList<>(); // 第二行数据
        data1.add("1001");
        data1.add("张三");
        data1.add("男");
        data1.add("12345678900");
        List<String> data2 = new ArrayList<>(); // 第三行数据
        data2.add("1002");
        data2.add("李四");
        data2.add("男");
        data2.add("12300000000");
        body.add(data1);
        body.add(data2);
        try (
                OutputStream out = new FileOutputStream("E:/test.xls") // 输出目的地
        ) {
            ExcelUtil.generateExcel("sheetName", header, body, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
