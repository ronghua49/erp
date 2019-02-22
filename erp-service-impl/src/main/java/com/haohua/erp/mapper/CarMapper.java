package com.haohua.erp.mapper;

import com.haohua.erp.entity.Car;
import com.haohua.erp.entity.CarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMapper {
    long countByExample(CarExample example);

    int deleteByExample(CarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Car record);

    int insertSelective(Car record);

    List<Car> selectByExample(CarExample example);

    Car selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Car record, @Param("example") CarExample example);

    int updateByExample(@Param("record") Car record, @Param("example") CarExample example);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    /**
     * 根据车牌号查询车辆和车主信息
     * @param licenseNo
     * @return
     */
    Car findCarWithCustomerInfo(String licenseNo);
}