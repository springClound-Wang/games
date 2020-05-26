package com.example.demo.service;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 成就客户、专业之上、持续创新、诚信敬业、合作共赢
 * 成为一家世界级的数字商业云服务厂商
 *
 * @Author: xingyan,
 * @Date: 2020/5/25
 * @DESC:
 */
public class ExcelUtils {

        private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

        /**
         * 课程excel
         * @param in
         * @param fileName
         * @return
         * @throws Exception
         */
        public static List getCourseListByExcel(InputStream in, String fileName) throws Exception {

            List list = new ArrayList<>();

            // 创建excel工作簿
            Workbook work = getWorkbook(in, fileName);
            if (null == work) {
                throw new Exception("创建Excel工作薄为空！");
            }

            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            for (int i = 0; i < work.getNumberOfSheets(); i++) {

                sheet = work.getSheetAt(i);
                if(sheet == null) {
                    continue;
                }

                // 滤过第一行标题
                for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }


                    List<Object> li = new ArrayList<>();
                    for (int x=0;x<row.getLastCellNum();x++) {
                        cell = row.getCell(x);
                        li.add(cell);
                    }
                    list.add(li);
                }
            }
            work.close();
            return list;
        }

        /**
         * 判断文件格式
         * @param in
         * @param fileName
         * @return
         */
        private static Workbook getWorkbook(InputStream in, String fileName) throws Exception {

            Workbook book = null;
            String filetype = fileName.substring(fileName.lastIndexOf("."));

            if(".xls".equals(filetype)) {
                book = new HSSFWorkbook(in);
            } else if (".xlsx".equals(filetype)) {
                book = new XSSFWorkbook(in);
            } else {
                throw new Exception("请上传excel文件！");
            }

            return book;
        }
    }


