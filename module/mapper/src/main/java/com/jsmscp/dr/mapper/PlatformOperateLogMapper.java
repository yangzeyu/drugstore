package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.PlatformOperateLog;

import java.util.List;

@Mapper
@Repository
public interface PlatformOperateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlatformOperateLog record);

    int insertSelective(PlatformOperateLog record);

    PlatformOperateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlatformOperateLog record);

    int updateByPrimaryKey(PlatformOperateLog record);

    List<PlatformOperateLog> list(@Param("operate") String operate, @Param("operator") String operator,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize);

    int findTotal(@Param("operate") String operate, @Param("operator") String operator,
                  @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<PlatformOperateLog> findAllOperator();
}
