package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/7/23
 */

import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.FixTransParts;
import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.PartsStream;
import com.haohua.erp.entity.Type;

import java.util.List;
import java.util.Map;

public interface PartService {
    /**
     * 根据id查询零件详情
     * @param id 零件的id
     * @return parts 对象
     * @throws Exception  测试id输入格式不正确或没有找到对应的part 抛出异常
     */
     Parts findById(Integer id);

    /**
     *根据页码和map参数查询 part的分页对象
     * @param pageNo
     * @param paramMap
     * @return
     */
     PageInfo<Parts> findWithTypeByPageNoAndMap(Integer pageNo, Map<String, Object> paramMap) ;

    /**
     * 查询所有的type对象
     * @return 返回type对象集合
     */
    List<Type> findTypeList();

    /**
     * 根据表单提交的part对象 新增part
     * @param parts  封装的part对象
     * @return 受影响的行数
     */
    Integer addNewParts(Parts parts,Integer employeeId);

    /**
     * 根据partsId删除parts
     * @return 受影响的行数
     */
    Integer delPartsById(Integer partsId);

    /**
     * 根据表单值封装的part对象修改 part
     * @param parts 要修改的对象
     * @return 
     */
    Integer editPartsByParts(Parts parts);

    /**
     * 查找type的对象 并分页
     * @p 当前页码
     * @return
     */
    PageInfo<Type> findTypePage(Integer p);

    /**
     * 根据typeId 删除type
     * @param typeId
     * @return 受影响的行数
     */
    Integer delTypeById(Integer typeId);

    /**
     * 根据类型名删除对应类型
     * @param delName
     * @return
     */
    Integer delTypeByTypeName(String delName);

    /**
     * 根据类型名查找类型
     * @param delName
     * @return
     */
    List<Type> findTypeByTypeName(String delName);

    /**
     * 根据类型id查找partsList集合
     * @param id 类型id
     * @return
     */
    List<Parts> findByTypeId(Integer id);

    /**
     * 根据类型名新增类型
     * @param addName 新增的类型名
     * @return
     */
    Integer addTypeByTypeName(String addName);

    /**
     * 根据类型修改 类型
     * @param type
     * @return
     */
    Integer editTypeByType(Type type);

    /**
     * 增加维修下发的配件需求列表到数据库
     * @param text
     */
    void addFixTransPartsList(String text);

    /**
     * 查询维修下发的订单配件需求
     * @return
     */
    List<FixTransParts> findFixTransParts();

    /**
     * 领取配件
     * @param orderId
     */
    void receiveParts(Integer orderId);

    /**
     * 根据订单id查询 维修配件列表
     * @param orderId
     * @return
     */
    List<FixTransParts> findFixTransPartsByOrderId(Integer orderId);

    /**
     * 根据参数查询配件流水
     * @param p
     * @param paramMap
     * @return
     */
    PageInfo<PartsStream> findPartStreamByParamMap(Integer p, Map<String,Object> paramMap);
}
