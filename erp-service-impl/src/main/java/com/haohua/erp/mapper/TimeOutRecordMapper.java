package com.haohua.erp.mapper;

import com.haohua.erp.entity.TimeOutRecord;
import com.haohua.erp.entity.TimeOutRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOutRecordMapper {
    long countByExample(TimeOutRecordExample example);

    int deleteByExample(TimeOutRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TimeOutRecord record);

    int insertSelective(TimeOutRecord record);

    List<TimeOutRecord> selectByExample(TimeOutRecordExample example);

    TimeOutRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TimeOutRecord record, @Param("example") TimeOutRecordExample example);

    int updateByExample(@Param("record") TimeOutRecord record, @Param("example") TimeOutRecordExample example);

    int updateByPrimaryKeySelective(TimeOutRecord record);

    int updateByPrimaryKey(TimeOutRecord record);
}