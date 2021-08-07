package com.whyyu.indoormanagementserver.util;

import com.whyyu.indoormanagementserver.entity.WiFi;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author WhyYu
 * @Description 读取AP、WiFi、BlueTooth三类数据，使用时需要注意的是excel表格的列顺序要和实体类的属性顺序一致<br>
 * 不然在自动注入的时候，属性就注入错位置了
 * @Date 2021/7/26 15:14
 */
public class ExcelReader {

    public static List<String> supportTypeList =
            Arrays.asList("int", "Integer", "float", "Float", "long", "Long", "double", "Double", "String");

    public static ArrayList readExcel(File file, Class clazz) throws Exception {
        // 将class的set函数以<name, setName()>记录到map中，之后调用把值注入具体实例
        // 同时存储属性，以便找到具体对应的set函数
        Map<String, Method> setMethodMap = new HashMap<>(10);
        ArrayList<String> fieldList = new ArrayList<>(10);
        for (Field field : clazz.getDeclaredFields()) {
            String name = field.getName();
            fieldList.add(name);
            if (supportTypeList.contains(field.getType().getSimpleName())) {
                try {
                    Method setMethod = clazz.getDeclaredMethod(
                            "set" + String.valueOf(name.charAt(0)).toUpperCase() + field.getName().substring(1),
                            field.getType());
                    setMethodMap.put(name, setMethod);
                } catch (NoSuchMethodException ignored) {
                }
            }
        }

        ArrayList excelData = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        // 数据库主键不在表格中，所以把第一个值删去
        int lastCellNum = setMethodMap.size() - 1;
        fieldList.remove(0);

        // 大坑，excel文件里面的数值，在变成double后可能会造成精度问题，即在cell.getNumericCellValue()时返回的double可能会有精度丢失
        // 这里直接强制格式化把
        DecimalFormat df = new DecimalFormat("#.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //将每一行的数据注入到class实例中并保存在list中，第0行标题列不管
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            // 通过反射创建实例
            Object instance = clazz.newInstance();
            try {
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);
                    // 有些时候cell会是null,避免空指针调用getCellType
                    if (cell == null) {
                        continue;
                    }
                    Object value;
                    Method set = setMethodMap.get(fieldList.get(j));
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            set.invoke(instance, value);
                            break;
                        case NUMERIC:
                            value = cell.getNumericCellValue();
                            set.invoke(instance, Double.parseDouble(df.format(value)));
                            break;
                        default:
                    }
                }
            } catch (Exception e) {
                throw new Exception("该路径下 " + file.getPath() + " 文件内容有誤");
            }
            excelData.add(instance);
        }
        workbook.close();
        return excelData;
    }


    public static void main(String[] args) {
        File file = new File("C:\\Users\\Dell\\Desktop\\诗琳通CGCS2000控制点及设备基础数据\\四楼\\四楼WiFi.xlsx");
        try {
            ArrayList<WiFi> excelData = ExcelReader.readExcel(file, WiFi.class);
        } catch (Exception e) {
            System.out.println("!!!");
        }
    }
}

