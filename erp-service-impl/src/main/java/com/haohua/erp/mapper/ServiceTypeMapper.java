package com.haohua.erp.mapper;

import com.haohua.erp.entity.ServiceType;
import com.haohua.erp.entity.ServiceTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeMapper {
    long countByExample(ServiceTypeExample example);

    int deleteByExample(ServiceTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceType record);

    int insertSelective(ServiceType record);

    List<ServiceType> selectByExample(ServiceTypeExample example);

    ServiceType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ServiceType record, @Param("example") ServiceTypeExample example);

    int updateByExample(@Param("record") ServiceType record, @Param("example") ServiceTypeExample example);

    int updateByPrimaryKeySelective(ServiceType record);

    int updateByPrimaryKey(ServiceType record);
}