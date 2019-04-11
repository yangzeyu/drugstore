package com.jsmscp.dr.util;


import com.jsmscp.dr.constant.Constant;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.DrugStockOutDto;
import com.jsmscp.dr.dto.MedicalInsuranceReportDto;
import com.jsmscp.dr.dto.StoreOutInReportDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

public  class ExportExcelUtil {

    /***
     * 出库导出Excel数据
     * @param sheetName sheet名称
     * @param fileName 导出文件名
     * @param list 要导出的数据
     * @param request
     * @param response
     * @throws IOException
     */
    public static void exportStockOut(String sheetName, String fileName, List<String> titles,
                                      List<DrugStockOutDto> list, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        SetStyle setStyle = new SetStyle(sheetName, titles).invoke();
        HSSFWorkbook wb = setStyle.getWb();
        if (CollectionUtils.isEmpty(list)) {
            // 输出空文件
            outputFile(fileName, request, response, wb);
            return;
        }
        HSSFSheet sheet = setStyle.getSheet();
        int rowNum = setStyle.getRowNum();
        int cellOrder = 1;
        BigDecimal stockOutPriceSum = new BigDecimal(0);
        BigDecimal insurancePriceSum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            for (DrugStockOutDto drugStockOutDto : list) {
                HSSFRow rows = sheet.createRow(rowNum++);
                int j = 0;
                // 序号
                rows.createCell(0).setCellValue(cellOrder++);
                rows.createCell(1).setCellValue(drugStockOutDto.getOutNo());
                rows.createCell(2).setCellValue(drugStockOutDto.getStoreName());
                if (drugStockOutDto.getPrice() != null) {
                    rows.createCell(3).setCellValue(drugStockOutDto.getPrice().toString());
                } else {
                    rows.createCell(3).setCellValue(new BigDecimal(0).toString());
                    drugStockOutDto.setPrice(new BigDecimal(0));
                }
                stockOutPriceSum = stockOutPriceSum.add(drugStockOutDto.getPrice());

                if (drugStockOutDto.getInsurancePrice() != null) {
                    rows.createCell(4).setCellValue(drugStockOutDto.getInsurancePrice().toString());
                } else {
                    rows.createCell(4).setCellValue(new BigDecimal(0).toString());
                    drugStockOutDto.setInsurancePrice(new BigDecimal(0));
                }
                insurancePriceSum = insurancePriceSum.add(drugStockOutDto.getInsurancePrice());

                   //判断
                if (drugStockOutDto.getPayType() == 1) {
                    rows.createCell(5).setCellValue(Constant.CASH_PAY_TYPE);     //1为现金
                } else if (drugStockOutDto.getPayType() == 2) {
                    rows.createCell(5).setCellValue(Constant.ICARUS_PAY_TYPE);   //2为医保卡
                }
                  // 类别显示中文
                rows.createCell(6).setCellValue(drugStockOutDto.getTradeName() + ":" + drugStockOutDto.getIdcard());
                rows.createCell(7).setCellValue(drugStockOutDto.getOutDate());
                if (drugStockOutDto.getType() == 1) {
                    rows.createCell(8).setCellValue(Constant.SELL_STATUS);   //1为销售
                } else if (drugStockOutDto.getType() == 2) {
                    rows.createCell(8).setCellValue(Constant.REFUND_STATUS);   //2为退货
                } else if (drugStockOutDto.getType() == 3) {
                    rows.createCell(8).setCellValue(Constant.ALLOCATE_STATUS);   //3为调拨出
                } else if (drugStockOutDto.getType() == 4) {
                    rows.createCell(8).setCellValue(Constant.INVENTORY_LOSSES);   //4为盘亏
                }
                if ("1".equals(drugStockOutDto.getIsPairCode())) {
                    rows.createCell(9).setCellValue(Constant.SUCCESS_MATCHING);
                } else {
                    rows.createCell(9).setCellValue(Constant.ERROR_MATCHING);
                }
            }
            //统计
            HSSFRow row = sheet.createRow(list.size() + 1);
            row.createCell(0).setCellValue("合计");
            //row.createCell(1).setCellValue("");
            //row.createCell(2).setCellValue("");
            if (stockOutPriceSum != null) {
                row.createCell(3).setCellValue(stockOutPriceSum.toString());
            } else {
                row.createCell(3).setCellValue(new BigDecimal(0).toString());
            }
            if (insurancePriceSum != null) {
                row.createCell(4).setCellValue(insurancePriceSum.toString());
            } else {
                row.createCell(4).setCellValue(new BigDecimal(0).toString());
            }
            //row.createCell(5).setCellValue("");
            //row.createCell(6).setCellValue("");
            //row.createCell(7).setCellValue("");
            //row.createCell(8).setCellValue("");
            //row.createCell(9).setCellValue("");
        }
        // 输出文件
        outputFile(fileName, request, response, wb);
    }

    /**
     * 导出入库信息
     * @param sheetName
     * @param fileName
     * @param titles
     * @param list
     * @param request
     * @param response
     * @throws IOException
     */
    public static void exportStockDeliver(String sheetName, String fileName, List<String> titles,
                                          List<DrugDeliverDto> list, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        SetStyle setStyle = new SetStyle(sheetName, titles).invoke();
        HSSFWorkbook wb = setStyle.getWb();
        if (CollectionUtils.isEmpty(list)) {
            // 输出空文件
            outputFile(fileName, request, response, wb);
            return;
        }
        HSSFSheet sheet = setStyle.getSheet();
        int rowNum = setStyle.getRowNum();
        int cellOrder = 1;
        BigDecimal priceSum = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            for (DrugDeliverDto drugDeliverDto : list) {
                HSSFRow rows = sheet.createRow(rowNum++);
                int j = 0;
                // 序号
                rows.createCell(0).setCellValue(cellOrder++);
                rows.createCell(1).setCellValue(drugDeliverDto.getDeliverCode());
                if (drugDeliverDto.getPrice() != null) {
                    rows.createCell(2).setCellValue(drugDeliverDto.getPrice().toString());
                } else {
                    rows.createCell(2).setCellValue(0);
                    drugDeliverDto.setPrice(new BigDecimal(0));
                }
                priceSum = priceSum.add(drugDeliverDto.getPrice());    //统计金额
                rows.createCell(3).setCellValue(drugDeliverDto.getDeliverDate());
                rows.createCell(4).setCellValue(drugDeliverDto.getInvoiceStatus());
                if (drugDeliverDto.getType() == 1) {
                    rows.createCell(5).setCellValue(Constant.PURCHASING_THE);  //1为采购入
                } else if (drugDeliverDto.getType() == 2) {
                    rows.createCell(5).setCellValue(Constant.TRANSFERS_INTO);  //2为调拨入
                } else if (drugDeliverDto.getType() == 3) {
                    rows.createCell(5).setCellValue(Constant.INVENTORY_PROFIT);  //3为盘盈
                } else if (drugDeliverDto.getType() == 4) {
                    rows.createCell(5).setCellValue(Constant.SALES_RETURN);    //4为销售退回
                }

                if ("1".equals(drugDeliverDto.getIsPairCode())) {
                    rows.createCell(6).setCellValue(Constant.SUCCESS_MATCHING);  //1为已关联
                } else {
                    rows.createCell(6).setCellValue(Constant.ERROR_MATCHING);    //2位关联失败
                }
                rows.createCell(7).setCellValue(drugDeliverDto.getStoreName());
                rows.createCell(8).setCellValue(drugDeliverDto.getIdcard());
                rows.createCell(9).setCellValue(drugDeliverDto.getInvoiceNumber());
                rows.createCell(10).setCellValue(drugDeliverDto.getStoreName());
            }
            //统计
            HSSFRow row = sheet.createRow(list.size() + 1);
            row.createCell(0).setCellValue("合计");
            //row.createCell(1).setCellValue("");
            if (priceSum != null) {
                row.createCell(2).setCellValue(priceSum.toString());
            } else {
                row.createCell(2).setCellValue(new BigDecimal(0).toString());
            }
//            row.createCell(3).setCellValue("");
//            row.createCell(4).setCellValue("");
//            row.createCell(5).setCellValue("");
//            row.createCell(6).setCellValue("");
//            row.createCell(7).setCellValue("");
//            row.createCell(8).setCellValue("");
//            row.createCell(9).setCellValue("");
        }
        // 输出文件
        outputFile(fileName, request, response, wb);
    }

    /**
     * 系统端医保进销存统计导出
     * @param sheetName
     * @param fileName
     * @param titles
     * @param list
     * @param request
     * @param response
     * @throws IOException
     */
    public static void exportMedicalInsurance(String sheetName, String fileName, List<String> titles,
                                              List<MedicalInsuranceReportDto> list, HttpServletRequest request,
                                              HttpServletResponse response) throws IOException {
        SetStyle setStyle = new SetStyle(sheetName, titles).invoke();
        HSSFWorkbook wb = setStyle.getWb();
        if (CollectionUtils.isEmpty(list)) {
            // 输出空文件
            outputFile(fileName, request, response, wb);
            return;
        }
        HSSFSheet sheet = setStyle.getSheet();
        int rowNum = setStyle.getRowNum();

        int cellOrder = 1;
        if (list != null && list.size() > 0) {
            for (MedicalInsuranceReportDto medicalInsuranceReportDto : list) {
                HSSFRow rows = sheet.createRow(rowNum++);
                int j = 0;
                // 序号
                rows.createCell(0).setCellValue(cellOrder++);
                rows.createCell(1).setCellValue(medicalInsuranceReportDto.getMedicalInsuranceCode());
                rows.createCell(2).setCellValue(medicalInsuranceReportDto.getThreeDirectoryName());
                rows.createCell(3).setCellValue(medicalInsuranceReportDto.getStoreName());
                rows.createCell(4).setCellValue(medicalInsuranceReportDto.getPlatformCode());
                rows.createCell(5).setCellValue(medicalInsuranceReportDto.getGoodName());
                rows.createCell(6).setCellValue(medicalInsuranceReportDto.getDosageForm());
                rows.createCell(7).setCellValue(medicalInsuranceReportDto.getManufactureName());
                rows.createCell(8).setCellValue(medicalInsuranceReportDto.getSpec());
                if (medicalInsuranceReportDto.getStoreOutNumber() != null) {
                    rows.createCell(9).setCellValue(medicalInsuranceReportDto.getStoreOutNumber().toString());
                } else {
                    rows.createCell(9).setCellValue(new BigDecimal(0).toString());
                }
                rows.createCell(10).setCellValue(medicalInsuranceReportDto.getCollectLvl());
            }
        }

        // 输出文件
        outputFile(fileName, request, response, wb);
    }

    /**
     * 药店进销存统计数据导出
     * @param fileName
     * @param titles
     * @param list
     * @param request
     * @param response
     */
    public static void exportStore(String fileName, String sheetName, List<String> titles,
                                   List<StoreOutInReportDto> list, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        SetStyle setStyle = new SetStyle(sheetName, titles).invoke();
        HSSFWorkbook wb = setStyle.getWb();
        if (CollectionUtils.isEmpty(list)) {
            // 输出空文件
            outputFile(fileName, request, response, wb);
            return;
        }
        HSSFSheet sheet = setStyle.getSheet();
        int rowNum = setStyle.getRowNum();

        int cellOrder = 1;
        BigDecimal initStockSum = new BigDecimal(0);
        BigDecimal inQuantitySum = new BigDecimal(0);
        BigDecimal outQuantitySum = new BigDecimal(0);
        BigDecimal stockSum = new BigDecimal(0);
        BigDecimal storeStockSum = new BigDecimal(0);

        if (list != null && list.size() > 0) {
            for (StoreOutInReportDto storeOutInReportDto : list) {
                HSSFRow rows = sheet.createRow(rowNum++);
                int j = 0;
                // 序号
                rows.createCell(0).setCellValue(cellOrder++);
                rows.createCell(1).setCellValue(storeOutInReportDto.getStoreName());
                rows.createCell(2).setCellValue(storeOutInReportDto.getGoodName());
                if (storeOutInReportDto.getInitStock() != null) {
                    rows.createCell(3).setCellValue(storeOutInReportDto.getInitStock().toString());
                } else {
                    rows.createCell(3).setCellValue(new BigDecimal(0).toString());
                    storeOutInReportDto.setInitStock(new BigDecimal(0));
                }
                initStockSum = initStockSum.add(storeOutInReportDto.getInitStock());

                rows.createCell(4).setCellValue(storeOutInReportDto.getDosageForm());
                rows.createCell(5).setCellValue(storeOutInReportDto.getSpec());
                rows.createCell(6).setCellValue(storeOutInReportDto.getManufacture());

                if (storeOutInReportDto.getInQuantity() != null) {
                    rows.createCell(7).setCellValue(storeOutInReportDto.getInQuantity().toString());
                } else {
                    rows.createCell(7).setCellValue(new BigDecimal(0).toString());
                    storeOutInReportDto.setInQuantity(new BigDecimal(0));
                }
                inQuantitySum = inQuantitySum.add(storeOutInReportDto.getInQuantity());

                if (storeOutInReportDto.getOutQuantity() != null) {
                    rows.createCell(8).setCellValue(storeOutInReportDto.getOutQuantity().toString());
                } else {
                    rows.createCell(8).setCellValue(new BigDecimal(0).toString());
                    storeOutInReportDto.setOutQuantity(new BigDecimal(0));
                }
                outQuantitySum = outQuantitySum.add(storeOutInReportDto.getOutQuantity());

                if (storeOutInReportDto.getStock() != null) {
                    rows.createCell(9).setCellValue(storeOutInReportDto.getStock().toString());
                } else {
                    rows.createCell(9).setCellValue(new BigDecimal(0).toString());
                    storeOutInReportDto.setStock(new BigDecimal(0));
                }
                stockSum = stockSum.add(storeOutInReportDto.getStock());

                if (storeOutInReportDto.getStoreStock() != null) {
                    rows.createCell(10).setCellValue(storeOutInReportDto.getStoreStock().toString());
                } else {
                    rows.createCell(10).setCellValue(new BigDecimal(0).toString());
                    storeOutInReportDto.setStoreStock(new BigDecimal(0));
                }
                storeStockSum = storeStockSum.add(storeOutInReportDto.getStoreStock());
            }

            //统计
            HSSFRow row = sheet.createRow(list.size() + 1);
            row.createCell(0).setCellValue("合计");
           //row.createCell(1).setCellValue("");
            // row.createCell(2).setCellValue("");
            if (initStockSum != null) {
                row.createCell(3).setCellValue(initStockSum.toString());
            } else {
                row.createCell(3).setCellValue(new BigDecimal(0).toString());
            }
//            row.createCell(4).setCellValue("");
//            row.createCell(5).setCellValue("");
//            row.createCell(6).setCellValue("");

            if (inQuantitySum != null) {
                row.createCell(7).setCellValue(inQuantitySum.toString());
            } else {
                row.createCell(7).setCellValue(new BigDecimal(0).toString());
            }


            if (outQuantitySum != null) {
                row.createCell(8).setCellValue(outQuantitySum.toString());
            } else {
                row.createCell(8).setCellValue(new BigDecimal(0).toString());
            }

            if (stockSum != null) {
                row.createCell(9).setCellValue(stockSum.toString());
            } else {
                row.createCell(9).setCellValue(new BigDecimal(0).toString());
            }

            if (storeStockSum != null) {
                row.createCell(10).setCellValue(storeStockSum.toString());
            } else {
                row.createCell(10).setCellValue(new BigDecimal(0).toString());
            }
        }
        // 输出文件
        outputFile(fileName, request, response, wb);
    }


    /**
     * 药店端库存管理数据导出
     * @param fileName
     * @param titles
     * @param list
     * @param request
     * @param response
     */
    public static void exportStock(String fileName, String sheetName, List<String> titles,
                                   List<DrugCatalogDto> list, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        SetStyle setStyle = new SetStyle(sheetName, titles).invoke();
        HSSFWorkbook wb = setStyle.getWb();
        if (CollectionUtils.isEmpty(list)) {
            // 输出空文件
            outputFile(fileName, request, response, wb);
            return;
        }
        HSSFSheet sheet = setStyle.getSheet();
        int rowNum = setStyle.getRowNum();

        int cellOrder = 1;
        if (list != null && list.size() > 0) {
            for (DrugCatalogDto drugCatalogDto : list) {
                HSSFRow rows = sheet.createRow(rowNum++);
                int j = 0;
                // 序号
                rows.createCell(0).setCellValue(cellOrder++);
                rows.createCell(1).setCellValue(drugCatalogDto.getDrugName());
                rows.createCell(2).setCellValue(drugCatalogDto.getDrugCode());
                rows.createCell(3).setCellValue(drugCatalogDto.getUnit());
                rows.createCell(4).setCellValue(drugCatalogDto.getDosageForm());
                rows.createCell(5).setCellValue(drugCatalogDto.getSpec());
                rows.createCell(6).setCellValue(drugCatalogDto.getManufacture());
                if (drugCatalogDto.getStock() != null) {
                    rows.createCell(7).setCellValue(drugCatalogDto.getStock().toString());
                } else {
                    rows.createCell(7).setCellValue(new BigDecimal(0).toString());
                }
                if (drugCatalogDto.getStatus() == 1) {
                    rows.createCell(8).setCellValue(Constant.START_USING);
                } else {
                    rows.createCell(8).setCellValue(Constant.STOP_USING);
                }

                rows.createCell(9).setCellValue(drugCatalogDto.getStoreName());
            }

        }
        // 输出文件
        outputFile(fileName, request, response, wb);
    }

    /**
     * 输出文件
     * @param request
     * @param response
     * @param wb
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static void outputFile(String fileName, HttpServletRequest request, HttpServletResponse response,
                                   HSSFWorkbook wb) throws UnsupportedEncodingException, IOException {
        String agent = request.getHeader("User-Agent");
        // 火狐浏览器导出文件不会url解码
        if (StringUtils.isEmpty(agent) == false && agent.toLowerCase().indexOf("firefox") > -1) {
            response.setHeader("Content-disposition", "attachment;filename=\"" +
                    new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xls\"");
        } else {
            response.setHeader("Content-disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName + ".xls", "UTF-8"));
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }

    public static class SetStyle {
        private String sheetName;
        private List<String> titles;
        private HSSFWorkbook wb;
        private HSSFSheet sheet;
        private int rowNum;

        public SetStyle(String sheetName, List<String> titles) {
            this.sheetName = sheetName;
            this.titles = titles;
        }

        public HSSFWorkbook getWb() {
            return wb;
        }

        public HSSFSheet getSheet() {
            return sheet;
        }

        public int getRowNum() {
            return rowNum;
        }

        public SetStyle invoke() {
            wb = new HSSFWorkbook();
            sheet = wb.createSheet(sheetName);

            rowNum = 0;

            //设置excel表格样式
            for (int i = 0; i < titles.size(); i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 25 / 10);
            }


            // 设置单元格样式(标题样式)
            HSSFCellStyle styleTitle = wb.createCellStyle();
            styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            // 对齐方式
            styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 字体
            HSSFFont font = wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            styleTitle.setFont(font);

            // 设置单元格样式(数据值样式)
            HSSFCellStyle styleContent = wb.createCellStyle();
            styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            // 标题行
            HSSFRow rowTitle = sheet.createRow(rowNum++);

            int i = 0;
            HSSFCell cellH0 = rowTitle.createCell(i++);
            cellH0.setCellValue("序号");
            cellH0.setCellStyle(styleTitle);
            for (String title : titles) {
                HSSFCell cellH = rowTitle.createCell(i++);
                cellH.setCellValue(title);
                cellH.setCellStyle(styleTitle);
            }
            return this;
        }
    }
}
