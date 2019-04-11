package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.CompareDrugCodeDto;
import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.dto.DrugStockInDto;
import com.jsmscp.dr.dto.DrugStockMSGDto;
import com.jsmscp.dr.dto.DrugStockOutDetailDto;
import com.jsmscp.dr.dto.DrugDeliveryDto;
import com.jsmscp.dr.entity.DrugCatalog;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface DrugCatalogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugCatalog record);

    int insertSelective(DrugCatalog record);

    DrugCatalog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugCatalog record);

    int updateByPrimaryKey(DrugCatalog record);

    List<DrugCatalogDto> findDrugCatalog(@Param("keyword") String keyword, @Param("storeId") Integer storeId,
                                         @Param("status") Byte status, @Param("pageNo") Integer pageNo,
                                         @Param("pageSize") Byte pageSize);

    int findCount(@Param("keyword") String keyword, @Param("storeId") Integer storeId, @Param("status") Byte status);

    DrugCatalogDto findOne(@Param("drugCatalogId") Long drugCatalogId);

    DrugCatalog findByDrugCode(@Param("drugCode") String drugCode, @Param("storeId") Integer storeId);

    List<DrugCatalogDto> findStock(@Param("storeId") String storeId, @Param("keyword") String keyword,
                                   @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<DrugCatalogDto> findDrugStock(@Param("storeId") Integer storeId, @Param("drugName") String drugName,
                                       @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer findStockCount(@Param("storeId") Integer storeId, @Param("drugName") String drugName);

    List<CompareDrugCodeDto> findAllNoDrugId(@Param("successStatus") Byte successStatus,
                                             @Param("emergent") Byte emergent);

    List<DrugDeliveryDto> findStockInItem(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId,
                                          @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<DrugStockOutDetailDto> findStockOutItem(@Param("storeId") Integer storeId,  @Param("catalogId") Long catalogId,
                                                @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<DrugDeliveryDto> findDrugStockWarning(@Param("storeId") Integer storeId, @Param("drugId") Long drugId);

    List<DrugStockInDto> findDeliverItem(@Param("drugId") Long drugId, @Param("batchNo") String batchNo,
                                         @Param("storeId") Integer storeId);

    List<DrugStockOutDetailDto> findInvoiceItem(@Param("drugId") Long drugId, @Param("batchNo") String batchNo,
                                                @Param("storeId") Integer storeId);
    //查询药品信息
    List<DrugCatalog> findByDrugId(@Param("storeId") Integer storeId, @Param("goodName") String goodName);

    int findNoDrugIdCount(@Param("storeId") Integer storeId);

    void compareDrug(@Param("drugCode") String drugCode, @Param("drugId") Long drugId,
                     @Param("storeId") Integer storeId, @Param("conversionRatio") Integer conversionRatio,
                     @Param("insuranceId") Integer insuranceId);

    List<DrugStockMSGDto> findStockOutMSG(@Param("storeId") Integer storeId, @Param("drugCode") String drugCode,
                                    @Param("batchNo") String batchNo);

    List<DrugStockMSGDto> findStockInMSG(@Param("storeId") Integer storeId, @Param("drugCode") String drugCode,
                                   @Param("batchNo") String batchNo);

    Integer findDrugStockCount(@Param("storeId") String storeId, @Param("keyword") String keyword);
    //查询药品信息数量
    Integer findByDrugIdCount(@Param("storeId") Integer storeId, @Param("goodName") String goodName);

    void delDrug(@Param("drugCode") String drugCode, @Param("storeId") Integer storeId);

    DrugCatalog findNoDrugId(@Param("storeId") Integer storeId, @Param("drugCode") String drugCode);

    DrugCatalogDto findCatalog(@Param("catalogId") Long catalogId);

    //查看库存数量
    Integer findAllStockSum(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId);

    //查看入库库存数量
    Integer findStockInSum(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId);
    //查看出库库存数量
    Integer findStockOutSum(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId);

    List<String> findAllCatalogByKey(@Param("keyword") String keyword, @Param("storeId") Integer storeId,
                                     @Param("zeroNo") Integer zeroNo,
                                     @Param("dropDownListSize") Integer dropDownListSize);

    int findDrugStockInCount(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId);

    int findStockOutCount(@Param("storeId") Integer storeId, @Param("catalogId") Long catalogId);

    List<DrugCatalogDto> exportStock(@Param("keyword") String keyword);

    void updateStock(@Param("stock") BigDecimal stock, @Param("id") Long id);

    List<DrugCatalog> findByStoreId(@Param("storeId") Integer storeId);
}
