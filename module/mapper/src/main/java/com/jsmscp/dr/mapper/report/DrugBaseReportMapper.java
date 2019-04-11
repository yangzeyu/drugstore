package com.jsmscp.dr.mapper.report;

import com.jsmscp.dr.dto.DrugBaseReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrugBaseReportMapper {
    List<DrugBaseReportDto> findDrugBaseReport(@Param("goodName") String goodName,
                                               @Param("manufactureId") Long manufactureId,
                                               @Param("platFormCode") String platFormCode,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime,
                                               @Param("pageNo") Integer pageNo,
                                               @Param("pageSize") Integer pageSize);

    Integer findCount(@Param("goodName") String goodName,
                      @Param("manufactureId") Long manufactureId,
                      @Param("platFormCode") String platFormCode,
                      @Param("startTime") String startTime,
                      @Param("endTime") String endTime);
}
