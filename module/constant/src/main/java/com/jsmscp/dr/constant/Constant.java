package com.jsmscp.dr.constant;

public class Constant {
    public static final Integer SYS_MENU_TOP_PARENT_ID = 0;


    /**
     * 无效状态
     */
    public static final String STATUS_FALSE = "否";
    /**
     * 有效状态
     */
    public static final String STATUS_TRUE = "是";

    /**
     * 药品目录 医院方
     */
    public static final Byte DRUG_CATALOG_TYPE_HOSPITAL = 1;
    /**
     * 药品目录 药品关联方
     */
    public static final Byte DRUG_CATALOG_TYPE_PARTY_ = 2;

    public static final Byte STATUS_SUCCESS = 1;
    public static final Byte STATUS_FAIL = 0;

    public static final Byte MENU_PARENT_ID = 0;

    public static final Byte MENU_TYPE_MENU = 1;
    public static final Byte MENU_TYPE_OPERATION = 2;

    public static final Byte IS_DELETED_YES = 1;
    public static final Byte IS_DELETED_NO = 0;

    public static final int DEFAULT_PAGE_SIZE = 50;



    public static final String DRUGSTORE_EMAIL_SUBJECT = "药店信息提示";
    public static final String FROM_USER = "fcsybj@163.com";




//    public static final Integer DRUG_STORE_USER_ROLE= 1;

    public static final byte DRUGINVOICE_TYPE_STOCK = 1; //发票类型进货
    public static final byte DRUGINVOICE_TYPE_SALE = 2; //发票类型销售


    public static final byte DRUGIMAGE_TYPE_INVOICE = 1; //上传图片发票类型
    public static final byte DRUGIMAGE_TYPE_DELIVERY = 2; //上传图片随货单

    // 入库类型
    /**
     * 采购入
     */
    public static final Integer STOCK_IN_TYPE_PURCHASE = 1;
    /**
     * 调拨入
     */
    public static final Integer STOCK_IN_TYPE_TRANSFER_IN = 2;
    /**
     * 盘盈
     */
    public static final Integer STOCK_IN_TYPE_PROFIT = 3;
    /**
     * 销售退回
     */
    public static final Integer STOCK_IN_TYPE_SELL_BACK = 4;


    // 出库类型
    /**
     * 销售出
     */
    public static final Integer STOCK_OUT_TYPE_SELL = 1;
    /**
     * 采购退货
     */
    public static final Integer STOCK_OUT_TYPE_PURCHASE_BACK = 2;
    /**
     * 调拨出
     */
    public static final Integer STOCK_OUT_TYPE_TRANSFER_OUT = 3;
    /**
     * 盘亏
     */
    public static final Integer STOCK_OUT_TYPE_LOSS = 4;

    //库存异常状态
    /**
     * 未阅读
     */
    public static final Byte STOCK_WARNING_NOT_READ = 0;
    /**
     * 已阅读
     */
    public static final Byte STOCK_WARNING_READ = 1;
    /**
     * 已处理
     */
    public static final Byte STOCK_WARNING_PROCESSED = 2;

    //库存异常状态
    /**
     * 单个药品总库存异常
     */
    public static final Byte STOCK_WARNING = 1;
    /**
     * 药品批次库存异常
     */
    public static final Byte STOCK_BATCH_NO_WARNING = 2;
    /**
     * 实时库存异常
     */
    public static final Byte STORE_STOCK_WARNING = 3;

    /**
     * 最小库存
     */
    public static final Integer LEAST_STOCK = 0;


    public static final Byte INDETERMINACY = 0;

    public static final String IS_MEDICALINSUERANCE = "是";
    public static final Byte MEDICALINSUERANCE_IS = 1;

    public static final String NO_MEDICALINSUERANCE = "否";
    public static final Byte MEDICALINSUERANCE_NO = 0;

    public static final String  CHINA_COLLECTTYPE = "中成药";
    public static final String  CHINA_COLLECTTYPE_SLICE = "中药饮片";
    public static final String  WESTERN_COLLECTTYPE = "西药";
    public static final String  BED_FEE = "床位费";
    public static final String  REGISTERED_FEE = "挂号费";
    public static final String  NURSING_EXPENSES = "护理费";
    public static final String  EXAMINATION_FEE = "检查费";
    public static final String  SURVEROR_FEE = "检验费";
    public static final String  OTHER_FEE = "其他";
    public static final String  OPERATION_FEE = "手术费";
    public static final String  BLOOD_FEE = "血液";
    public static final String  SPECIAL_MATERIALS = "医用特殊材料";
    public static final String  TREATEMNT_FEE  = "治疗费";

    public static final Byte  CHINA_COLLECTTYPE_CODE = 1;
    public static final Byte  CHINA_COLLECTTYPE_SLICE_CODE = 2;
    public static final Byte  WESTERN_COLLECTTYPE_CODE = 3;
    public static final Byte  BED_FEE_CODE = 4;
    public static final Byte  REGISTERED_FEE_CODE = 5;
    public static final Byte  NURSING_EXPENSES_CODE = 6;
    public static final Byte  EXAMINATION_FEE_CODE = 7;
    public static final Byte  SURVEROR_FEE_CODE = 8;
    public static final Byte  OTHER_FEE_CODE = 9;
    public static final Byte  OPERATION_FEE_CODE = 10;
    public static final Byte  BLOOD_FEE_CODE = 11;
    public static final Byte  SPECIAL_MATERIALS_CODE = 12;
    public static final Byte  TREATEMNT_FEE_CODE  = 13;

    public static final String BACK_STATUS_ERROR = "ERROR";
    public static final String BACK_STATUS_SUCCESS = "SUCCESS";

    public static final Byte IS_EMERGENT_TRUE = 1;
    public static final Byte IS_EMERGENT_FALSE = 0;

    public static final int DRUG_PAGE_SIZE = 4;


    public static final Integer DROP_DOWN_LIST_SIZE = 10;
    public static final Integer ZERO_SIZE = 0;


    public static final String IS_PAIR_CODE_TRUE = "1";
    public static final String IS_PAIR_CODE_FALSE = "0";


    public static final String LOGIN_URL = "http://218.204.93.165:8092/login";
    public static final String API_URL = "http://218.204.93.165:8093/api";
    public static final String INITIAL_PASSWORD = "123456";

    public static final Byte COLLECTTYPE_CHINA = 1;  //中药
    public static final Byte COLLECTTYPE_WESTERN = 2;  //西药

    public static final Byte PRICE_CORRECT_TRUE = 1;
    public static final Byte PRICE_CORRECT_FALSE = 0;
    public static final Byte OTHER_DRUG_TYPE = 3;

    public static final Byte INSURANCE_FLAG_TRUE = 1; //医保药品
    public static final Byte INSURANCE_FLAG_FALSE = 0; //非医保药品

    public static final Integer PAY_CASH_TYPE = 1;
    public static final Integer PAY_INSURANCE_TYPE = 2;

    public static final String SUCCESS_INVOICE_STATUS = "已关联";

    public static final String ERROR_INVOICE_STATUS = "关联异常";

    public static final String SUCCESS_MATCHING = "已对码";
    public static final String ERROR_MATCHING = "未对码";


    public static final String CASH_PAY_TYPE = "现金";

    public static final String ICARUS_PAY_TYPE = "医保卡";

    //出库
    public static final String SELL_STATUS = "销售";

    public static final String REFUND_STATUS = "退货";

    public static final String ALLOCATE_STATUS = "调拨出";

    public static final String INVENTORY_LOSSES = "盘亏";

    //入库
    public static final String PURCHASING_THE = "采购入";

    public static final String TRANSFERS_INTO = "调拨入";

    public static final String INVENTORY_PROFIT = "盘盈";

    public static final String SALES_RETURN = "销售退回";

    public static final String START_USING = "启用";

    public static final String STOP_USING = "禁用";

    public static final String QUERY_INVOICE_URL = "https://fapiao.market.alicloudapi.com/invoice/query?";

    public static final String QUERY_INVOICE_HEADER = "APPCODE 96133ece7f8b4acc80a4e967d761c8d9";

    public static final Byte QUERY_INVOICE_STATUS_FALSE = 0;
    public static final Byte QUERY_INVOICE_STATUS_TRUE = 1;


    public static final String QUERY_SUCCESS = "true";
    public static final String QUERY_FALSE = "false";

    public static final Byte UPLOADIMAGE_TRUE = 1;
    public static final Byte UPLOADIMAGE_FALSE = 0;



}
