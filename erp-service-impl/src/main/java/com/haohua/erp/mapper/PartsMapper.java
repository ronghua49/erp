package com.haohua.erp.mapper;

import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.PartsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    Parts selectByPrimaryKey(Integer id);

    List<Parts> selectWithTypeByParamMap(Map<String, Object> paramMap);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);

    Parts selectByPartsNo(String partsNo);
}