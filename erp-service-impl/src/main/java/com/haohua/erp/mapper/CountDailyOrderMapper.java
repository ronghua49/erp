package com.haohua.erp.mapper;

import com.haohua.erp.entity.CountDailyOrder;
import com.haohua.erp.entity.CountDailyOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountDailyOrderMapper {
    long countByExample(CountDailyOrderExample example);

    int deleteByExample(CountDailyOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CountDailyOrder record);

    int insertSelective(CountDailyOrder record);

    List<CountDailyOrder> selectByExample(CountDailyOrderExample example);

    CountDailyOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CountDailyOrder record, @Param("example") CountDailyOrderExample example);

    int updateByExample(@Param("record") CountDailyOrder record, @Param("example") CountDailyOrderExample example);

    int updateByPrimaryKeySelective(CountDailyOrder record);

    int updateByPrimaryKey(CountDailyOrder record);
}