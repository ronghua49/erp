package com.haohua.erp.mapper;

import com.haohua.erp.entity.FixTransParts;
import com.haohua.erp.entity.FixTransPartsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixTransPartsMapper {
    long countByExample(FixTransPartsExample example);

    int deleteByExample(FixTransPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FixTransParts record);

    int insertSelective(FixTransParts record);

    List<FixTransParts> selectByExample(FixTransPartsExample example);

    FixTransParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FixTransParts record, @Param("example") FixTransPartsExample example);

    int updateByExample(@Param("record") FixTransParts record, @Param("example") FixTransPartsExample example);

    int updateByPrimaryKeySelective(FixTransParts record);

    int updateByPrimaryKey(FixTransParts record);
}