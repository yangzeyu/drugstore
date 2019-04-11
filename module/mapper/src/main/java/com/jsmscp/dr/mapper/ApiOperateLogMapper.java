package com.jsmscp.dr.mapper;

import com.jsmscp.dr.entity.ApiOperateLog;
import com.jsmscp.dr.entity.PlatformOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApiOperateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlatformOperateLog record);

    int insertSelective(ApiOperateLog record);

    ApiOperateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiOperateLog record);

    int updateByPrimaryKey(ApiOperateLog record);

    List<ApiOperateLog> list(@Param("operator") String operator, @Param("req_url") String req_url,
                             @Param("resp") String resp, @Param("startTime") String startTime,
                             @Param("endTime") String endTime, @Param("pageNo") Integer pageNo,
                             @Param("pageSize") Byte pageSize);

    Integer findCount(@Param("operator") String operator, @Param("req_url") String req_url,
                      @Param("resp") String resp, @Param("startTime") String startTime,
                      @Param("endTime") String endTime);
}
