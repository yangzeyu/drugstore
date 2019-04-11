package com.jsmscp.dr.mapper;

import com.jsmscp.dr.dto.InvoiceCompareDto;
import com.jsmscp.dr.dto.StoreDeliveryInvoiceReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugInvoiceDto;
import com.jsmscp.dr.entity.DrugInvoice;

import java.util.List;

@Mapper
@Repository
public interface DrugInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugInvoice record);

    int insertSelective(DrugInvoice record);

    DrugInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugInvoice record);

    int updateByPrimaryKey(DrugInvoice record);

    DrugInvoice findByNumber(@Param("invoiceNo") String invoiceNo, @Param("storeId") Integer storeId);

    List<DrugInvoiceDto> findInvoiceItem(@Param("deliverId") Integer deliverId);

    List<InvoiceCompareDto> findInvoiceReport(@Param("storeId") Integer storeId,
                                              @Param("invoiceCode") String invoiceCode,
                                              @Param("pageNo") Integer pageNo,
                                              @Param("pageSize") Integer pageSize);

    int findInvoiceReportCount(@Param("storeId") Integer storeId,
                               @Param("invoiceCode") String invoiceCode);

    List<StoreDeliveryInvoiceReportDto> findInvoiceDeliverReport(@Param("storeId") Integer storeId,
                                                                 @Param("invoiceCode") String invoiceCode,
                                                                 @Param("invoiceType") Byte invoiceType,
                                                                 @Param("pageNo") Integer pageNo,
                                                                 @Param("pageSize") Integer pageSize);

    int findInvoiceDeliverCount(@Param("storeId") Integer storeId,
                                @Param("invoiceCode") String invoiceCode,
                                @Param("invoiceType") Byte invoiceType);

    DrugInvoice findInvoice(@Param("storeId") Integer storeId, @Param("invoiceCode") String invoiceCode);

    List<InvoiceCompareDto> findInvoiceMsg(@Param("storeId") Integer storeId, @Param("invoiceCode") String invoiceCode);
}
