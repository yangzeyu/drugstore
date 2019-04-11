package com.jsmscp.dr.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.MedicalInsurance;

import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExcelUtil {

    public static void exportDrugBaseModel(HttpServletResponse response) {
        String sheetName = null;
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("stockInfo");
        for (int i = 0; i < 10; i++) {
            sheet.setColumnWidth(i, 25 * 256);
        }

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("药品名称");
        row.createCell(1).setCellValue("平台编码");
        row.createCell(2).setCellValue("商品名称");
        row.createCell(3).setCellValue("剂型");
        row.createCell(4).setCellValue("中药/西药");
        row.createCell(5).setCellValue("规格");
        row.createCell(6).setCellValue("包装单位");
        row.createCell(7).setCellValue("转换比");
        row.createCell(8).setCellValue("单价");
        row.createCell(9).setCellValue("零售价");
        row.createCell(10).setCellValue("批准文号");
        row.createCell(11).setCellValue("生产厂家");
        row.createCell(12).setCellValue("医保编码");
        row.createCell(13).setCellValue("是否医保");
        row.createCell(14).setCellValue("单次用量");
        row.createCell(15).setCellValue("单次用量单位");
        row.createCell(16).setCellValue("频次");
        row.createCell(17).setCellValue("频次编码");
        row.createCell(18).setCellValue("单次最大用量");
        row.createCell(19).setCellValue("给药途径");

        try {
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename= " + new String("药品信息模板".getBytes("UTF-8"),
                            "ISO8859-1") + ".xlsx");
            response.setContentType("application/msexcel");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static HashMap<String, Object> readDrugBaseExcel(InputStream inputStream,
                                                            HashMap<String, Integer> map,
                                                            HashMap<String, Long> medicalInsuranceMap)
            throws Exception {
        HashMap<String, Object> backMap = new HashMap<>();
        List<DrugBase> drugList = new ArrayList<>();
        List<String> msgList = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = getWorkbook(inputStream);

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                DrugBase drug = new DrugBase();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(12) != null) {  //首先查询药品的生产厂家是否存在
                    boolean contains = map.containsKey(getCellValue(row.getCell(12)).toString());
                    if (contains) {
                        drug.setManufactureId(map.get(getCellValue(row.getCell(12)).toString()));
                    } else {
                        msgList.add(getCellValue(row.getCell(12)).toString() + "  未找到生产厂家,请核对!");
                        continue;
                    }
                }
                if (row.getCell(13) != null) {
                    String medicalInsurance = row.getCell(13).toString();
                    boolean contains = medicalInsuranceMap.containsKey(
                            getCellValue(row.getCell(13)).toString());
                    if (contains) {
                        drug.setInsuranceId(medicalInsuranceMap.get(medicalInsurance));
                    } else {
                        msgList.add(getCellValue(row.getCell(13)).toString() + "  未找到医保目录,请核对!");
                        continue;
                    }
                }
                if (row.getCell(0) != null) {
                    String name = getCellValue(row.getCell(0)).toString();
                    drug.setName(name);
                    //药品名称拼音大写
                    if (name.contains("(")) {
                        drug.setNamePinyin(PinyinUtil.changeToGetShortPinYin(
                                name.substring(0, name.indexOf("("))).toUpperCase());
                    } else {
                        drug.setNamePinyin(PinyinUtil.changeToGetShortPinYin(name).toUpperCase());
                    }
                }
                if (row.getCell(1) != null) {
                    drug.setPlatformCode(getCellValue(row.getCell(1)).toString());
                }
                if (row.getCell(2) != null) {
                    String goodName = getCellValue(row.getCell(2)).toString();
                    drug.setGoodName(goodName);
                    //商品名臣拼音
                    if (goodName.contains("(")) {
                        drug.setNamePinyin(PinyinUtil.changeToGetShortPinYin(
                                goodName.substring(0, goodName.indexOf("("))).toUpperCase());
                    } else {
                        drug.setNamePinyin(PinyinUtil.changeToGetShortPinYin(goodName).toUpperCase());
                    }
                }
                if (row.getCell(3) != null) {
                    Byte type = null;
                    if (Constant.CHINA_COLLECTTYPE.equals(getCellValue(row.getCell(3)).toString())) {
                        type = Constant.COLLECTTYPE_CHINA;
                    } else if (Constant.WESTERN_COLLECTTYPE.equals(getCellValue(row.getCell(3)).toString())) {
                        type = Constant.COLLECTTYPE_WESTERN;
                    } else if (Constant.OTHER_FEE.equals(getCellValue(row.getCell(3)).toString())){
                        type = Constant.OTHER_DRUG_TYPE;
                    }
                    drug.setType(type);
                }
                if (row.getCell(4) != null) {
                    drug.setDosageForm(getCellValue(row.getCell(4)).toString());
                }
                if (row.getCell(5) != null) {
                    drug.setSpec(getCellValue(row.getCell(5)).toString());
                }
                if (row.getCell(6) != null) {
                    drug.setUnit(getCellValue(row.getCell(6)).toString());
                }
                if (row.getCell(7) != null) {
                    drug.setStandSpecRate(Integer.valueOf(getCellValue(row.getCell(7)).toString()));
                }
                if (row.getCell(8) != null) {
                    drug.setUnitPrice(new BigDecimal(getCellValue(row.getCell(8)).toString()));
                }
                if (row.getCell(9) != null) {
                    drug.setRetailPrice(new BigDecimal(getCellValue(row.getCell(9)).toString()));
                }
                if (row.getCell(10) != null) {
                    drug.setPermissionNumber(getCellValue(row.getCell(10)).toString());
                }
                if (row.getCell(11) != null) {
                    Byte status = null;
                    if (Constant.STATUS_FALSE.equals(getCellValue(row.getCell(14)).toString())) {
                        status = Constant.STATUS_FAIL;
                    } else if (Constant.STATUS_TRUE.equals(getCellValue(row.getCell(14)).toString())) {
                        status = Constant.STATUS_SUCCESS;
                    }
                    drug.setStatus(status);
                }
                if (row.getCell(14) != null) {
                    Byte isMedicalInsurance = null;
                    if (Constant.IS_MEDICALINSUERANCE.equals(getCellValue(row.getCell(14)).toString())) {
                        isMedicalInsurance = Constant.MEDICALINSUERANCE_IS;
                    } else if (Constant.NO_MEDICALINSUERANCE.equals(getCellValue(row.getCell(14)).toString())) {
                        isMedicalInsurance = Constant.MEDICALINSUERANCE_NO;
                    }
                    drug.setIsMedicalInsurance(isMedicalInsurance);
                }
                if (row.getCell(15) != null) {
                    drug.setOnceNumber(getCellValue(row.getCell(15)).toString());
                }
                if (row.getCell(16) != null) {
                    drug.setOnceUnit(getCellValue(row.getCell(16)).toString());
                }
                if (row.getCell(17) != null) {
                    drug.setFreqCode(getCellValue(row.getCell(17)).toString());
                }
                if (row.getCell(18) != null) {
                    drug.setFreqName(getCellValue(row.getCell(18)).toString());
                }
                if (row.getCell(19) != null) {
                    drug.setMaxOnceNumber(Integer.valueOf(getCellValue(row.getCell(19)).toString()));
                }
                if (row.getCell(20) != null) {
                    drug.setDirection(getCellValue(row.getCell(20)).toString());
                }
                drug.setUpdateAt(new Date());
                drug.setCreateAt(new Date());


                drugList.add(drug);
            }
        }
        backMap.put(Constant.BACK_STATUS_SUCCESS, drugList);
        backMap.put(Constant.BACK_STATUS_ERROR, msgList);
        return backMap;
    }

    private static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    private static Workbook getWorkbook(InputStream inStr) throws Exception {
        Workbook wb = null;
        wb = WorkbookFactory.create(inStr);
        return wb;
    }


    public static List<MedicalInsurance> readMedicalInsurance(InputStream inputStream)
            throws Exception {
        List<MedicalInsurance> medicalInsuranceList = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = getWorkbook(inputStream);

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                MedicalInsurance medicalInsurance = new MedicalInsurance();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(1) != null) {
                    medicalInsurance.setMedicalInsuranceCode(
                            String.valueOf(getCellValue(row.getCell(1)).toString()));
                }
                if (row.getCell(2) != null) {
                    medicalInsurance.setThreeDirectoryName(getCellValue(row.getCell(2)).toString());
                }
                if (row.getCell(3) != null) {
                    medicalInsurance.setThreeDirectoryType(getCellValue(row.getCell(3)).toString());
                }
                if (row.getCell(4) != null) {
                    switch (getCellValue(row.getCell(4)).toString()) {
                        case Constant.CHINA_COLLECTTYPE:
                            medicalInsurance.setCollectType(Constant.CHINA_COLLECTTYPE_CODE);
                            break;
                        case Constant.CHINA_COLLECTTYPE_SLICE:
                            medicalInsurance.setCollectType(Constant.CHINA_COLLECTTYPE_SLICE_CODE);
                            break;
                        case Constant.WESTERN_COLLECTTYPE:
                            medicalInsurance.setCollectType(Constant.WESTERN_COLLECTTYPE_CODE);
                            break;
                        case Constant.BED_FEE:
                            medicalInsurance.setCollectType(Constant.BED_FEE_CODE);
                            break;
                        case Constant.REGISTERED_FEE:
                            medicalInsurance.setCollectType(Constant.REGISTERED_FEE_CODE);
                            break;
                        case Constant.NURSING_EXPENSES:
                            medicalInsurance.setCollectType(Constant.NURSING_EXPENSES_CODE);
                            break;
                        case Constant.EXAMINATION_FEE:
                            medicalInsurance.setCollectType(Constant.EXAMINATION_FEE_CODE);
                            break;
                        case Constant.SURVEROR_FEE:
                            medicalInsurance.setCollectType(Constant.SURVEROR_FEE_CODE);
                            break;
                        case Constant.OTHER_FEE:
                            medicalInsurance.setCollectType(Constant.OTHER_FEE_CODE);
                            break;
                        case Constant.OPERATION_FEE:
                            medicalInsurance.setCollectType(Constant.OPERATION_FEE_CODE);
                            break;
                        case Constant.BLOOD_FEE:
                            medicalInsurance.setCollectType(Constant.BLOOD_FEE_CODE);
                            break;
                        case Constant.SPECIAL_MATERIALS:
                            medicalInsurance.setCollectType(Constant.SPECIAL_MATERIALS_CODE);
                            break;
                        case Constant.TREATEMNT_FEE:
                            medicalInsurance.setCollectType(Constant.TREATEMNT_FEE_CODE);
                    }
                }
                if (row.getCell(5) != null) {
                    medicalInsurance.setCollectLvl(getCellValue(row.getCell(5)).toString());
                }
                if (row.getCell(6) != null) {
                    Byte status = null;
                    if (Constant.STATUS_FALSE.equals(getCellValue(row.getCell(6)).toString())) {
                        status = Constant.STATUS_FAIL;
                    } else if (Constant.STATUS_TRUE.equals(getCellValue(row.getCell(6)).toString())) {
                        status = Constant.STATUS_SUCCESS;
                    }
                    medicalInsurance.setStatus(status);
                }
                if (row.getCell(7) != null) {
                    medicalInsurance.setDosageForm(getCellValue(row.getCell(7)).toString());
                }
                if (row.getCell(8) != null) {
                    medicalInsurance.setSpec(getCellValue(row.getCell(8)).toString());
                }
                medicalInsurance.setUpdateAt(new Date());
                medicalInsurance.setCreateAt(new Date());


                medicalInsuranceList.add(medicalInsurance);
            }
        }

        return medicalInsuranceList;
    }

}
