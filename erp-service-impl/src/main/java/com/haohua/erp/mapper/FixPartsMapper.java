package com.haohua.erp.mapper;

import com.haohua.erp.entity.FixParts;
import com.haohua.erp.entity.FixPartsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FixPartsMapper {
    long countByExample(FixPartsExample example);

    int deleteByExample(FixPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FixParts record);

    int insertSelective(FixParts record);

    List<FixParts> selectByExample(FixPartsExample example);

    FixParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FixParts record, @Param("example") FixPartsExample example);

    int updateByExample(@Param("record") FixParts record, @Param("example") FixPartsExample example);

    int updateByPrimaryKeySelective(FixParts record);

    int updateByPrimaryKey(FixParts record);

    List<FixParts> findFixPartsListByOrderId(Integer orderId);
}