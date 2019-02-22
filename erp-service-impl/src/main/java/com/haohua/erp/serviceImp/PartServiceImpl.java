package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/7/23
 */
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.*;
import com.haohua.erp.entity.dto.OrderTransDto;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.mapper.*;
import com.haohua.erp.service.PartService;
import com.haohua.erp.util.Constant;
import com.haohua.erp.exception.NotFoundException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汽车服务 业务处理层
 * @author ronghua
 */
@Service
public class PartServiceImpl implements PartService {
   @Autowired
    private PartsMapper partsMapper;
   @Autowired
   private TypeMapper typeMapper;

   @Autowired
   private PartsStreamMapper partsStreamMapper;

   @Autowired
   private FixTransPartsMapper fixTransPartsMapper;

   @Autowired
   private EmployeeMapper employeeMapper;

    @Override
    public Parts findById(Integer id)  {
        Parts parts = partsMapper.selectByPrimaryKey(id);
        if (parts!=null){
             return parts;
        }else {
            throw new NotFoundException("未找到零部件");
        }
    }
    /**
     * 根据页码和map参数查询 part的分页对象
     *
     * @param pageNo 当前页码
     * @param paramMap 查询part对象的条件
     * @return part的page对象
     */
    @Override
    public PageInfo<Parts> findWithTypeByPageNoAndMap(Integer pageNo, Map<String, Object> paramMap) {
            //在执行sql前告诉分页条件
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGESIZA);
            //根据条件查询part的集合
        List<Parts> partsList = partsMapper.selectWithTypeByParamMap(paramMap);
            //把分页好的对象集合封装成PageInfo对象
        PageInfo<Parts> pageInfo = new PageInfo<>(partsList);
        return pageInfo;
    }

    /**
     * 查询所有的type对象
     *
     * @return 返回type对象集合
     */
    @Override
    public List<Type> findTypeList() {
        List<Type> typeList = typeMapper.selectByExample(null);
        return typeList;
    }

    /**
     * 根据表单提交的part对象 新增part
     *
     * @param parts 封装的part对象
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class,isolation = Isolation.REPEATABLE_READ)
    public Integer addNewParts(Parts parts,Integer employeeId) {
        //根据零件型号查询 新增零件是否库存中已存在
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andPartsNoEqualTo(parts.getPartsNo());
        List<Parts> partsList = partsMapper.selectByExample(partsExample);
        Integer partsId=null;
        Integer inventoryNum =0;
        if(partsList!=null){
            Parts findParts = partsList.get(0);
            inventoryNum=findParts.getNum();
            findParts.setNum(inventoryNum+parts.getNum());
            partsId=findParts.getId();
        }else{
          partsMapper.insertSelective(parts);
          partsId = parts.getId();
        }
        //增加入库流水记录
        PartsStream partsStream = new PartsStream();
        partsStream.setBeforeNum(inventoryNum);
        partsStream.setEmployeeId(employeeId);
        partsStream.setNum(parts.getNum());
        partsStream.setAfterNum(inventoryNum+parts.getNum());
        partsStream.setType(Parts.IN_PARTS);
        partsStream.setPartsId(partsId);
        Integer res = partsStreamMapper.insertSelective(partsStream);
        return res;
    }

    /**
     * 根据partsId删除parts
     *
     * @return 受影响的行数
     */
    @Override
    public Integer delPartsById(Integer partsId) {
        Integer res = partsMapper.deleteByPrimaryKey(partsId);
        return res;
    }

    /**
     * 根据表单值封装的part对象修改 part
     *
     * @param parts 要修改的对象
     * @return
     */
    @Override
    public Integer editPartsByParts(Parts parts) {
        int res =partsMapper.updateByPrimaryKeySelective(parts);
        return res;
    }

    /**
     * 查找type的对象 并分页
     *
     * @return typelist的page对象
     */
    @Override
    public PageInfo<Type> findTypePage(Integer p) {
        PageHelper.startPage(p,Constant.DEFAULT_PAGESIZA);
        List<Type> typeList = findTypeList();
        return new PageInfo<>(typeList);
    }

    /**
     * 根据typeId 删除type
     *
     * @param typeId
     * @return 受影响的行数
     */
    @Override
    public Integer delTypeById(Integer typeId) {
        return typeMapper.deleteByPrimaryKey(typeId);
    }

    /**
     * 根据类型名删除对应类型
     * @param delName
     * @return
     */
    @Override
    public Integer delTypeByTypeName(String delName) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(delName);
        return typeMapper.deleteByExample(typeExample);
    }

    /**
     * 根据类型名查找类型
     *
     * @param delName
     * @return
     */
    @Override
    public List<Type> findTypeByTypeName(String delName) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(delName);
        return typeMapper.selectByExample(typeExample);
}

    /**
     * 根据类型id查找partsList集合
     *
     * @param id 类型id
     * @return
     */
    @Override
    public List<Parts> findByTypeId(Integer id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        return partsMapper.selectByExample(partsExample);
    }

    /**
     * 根据类型名新增类型
     *
     * @param addName 新增的类型名
     * @return
     */
    @Override
    public Integer addTypeByTypeName(String addName) {
            Type type = new Type();
            type.setTypeName(addName);
            return typeMapper.insert(type);
    }

    /**
     * 根据类型修改 类型
     *
     * @param type
     * @return
     */
    @Override
    public Integer editTypeByType(Type type) {
        return typeMapper.updateByPrimaryKeySelective(type);
    }
    /**
     * 增加维修下发的配件需求列表到数据库
     * @param text
     */
    @Override
    public void addFixTransPartsList(String text) {
        JSONObject jsonObject = JSONObject.fromObject(text);
        Map classMap = new HashMap();
        classMap.put("fixPartsList",FixParts.class);
        OrderTransDto orderTransDto = (OrderTransDto) JSONObject.toBean(jsonObject,OrderTransDto.class,classMap);
        List<FixParts> fixPartsList = orderTransDto.getFixPartsList();
        FixOrder fixOrder = orderTransDto.getFixOrder();
        //维修下发的订单配件需求 写入数据库
        for(FixParts fixParts :fixPartsList){
            FixTransParts fixTransParts = new FixTransParts();
            fixTransParts.setEmployeeName(fixOrder.getFixEmployeeName());
            fixTransParts.setOrderId(fixOrder.getOrderId());
            fixTransParts.setOrderTime(fixOrder.getOrderTime());
            fixTransParts.setOrderType(fixOrder.getOrderType());
            fixTransParts.setPartsName(fixParts.getPartsName());
            fixTransParts.setPartsNo(fixParts.getPartsNo());
            fixTransParts.setEmployeeName(fixParts.getEmployeeName());
            fixTransParts.setPartsNum(fixParts.getNum());
            fixTransParts.setState(FixTransParts.PARTS_STATE_UNRECEIVE);
            //存入等待领取的配件 数据库表中
            fixTransPartsMapper.insertSelective(fixTransParts);
        }
    }
    /**
     * 查询维修下发的订单配件列表
     * @return
     */
    @Override
    public List<FixTransParts> findFixTransParts() {
        return fixTransPartsMapper.selectByExample(null);
    }
    /**
     * 领取配件
     * @param orderId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void receiveParts(Integer orderId) {
        //查询orderId存在
        FixTransPartsExample fixTransPartsExample = new FixTransPartsExample();
        fixTransPartsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<FixTransParts> fixTransPartsList = fixTransPartsMapper.selectByExample(fixTransPartsExample);
        if(!fixTransPartsList.isEmpty()){
       //判断所领取的配件数量是否大于库存
         for(FixTransParts fixTransParts :fixTransPartsList){
            Parts parts = partsMapper.selectByPartsNo(fixTransParts.getPartsNo());
            if (parts.getInventory()<fixTransParts.getPartsNum()){
                throw  new ServiceException("编号为 "+parts.getPartsNo()+"的"+parts.getPartsName()+"库存不足!");
            }else{
                //领取配件
                parts.setNum(parts.getInventory()-fixTransParts.getPartsNum());
                partsMapper.updateByPrimaryKeySelective(parts);
                //改变配件状态
                fixTransParts.setState(FixTransParts.PARTS_STATE_RECEIVED);
                fixTransPartsMapper.updateByPrimaryKeySelective(fixTransParts);
                //增加出库流水
                PartsStream partsStream = new PartsStream();
                partsStream.setPartsId(parts.getId());
                partsStream.setType(Parts.OUT_PARTS);
                partsStream.setBeforeNum(parts.getInventory());
                partsStream.setNum(fixTransParts.getPartsNum());
                partsStream.setAfterNum(parts.getInventory()-fixTransParts.getPartsNum());

                //查询employeeId  (配合数据库的id需求，理论上应该传送id)
                EmployeeExample employeeExample = new EmployeeExample();
                employeeExample.createCriteria().andEmployeeNameEqualTo(fixTransParts.getEmployeeName());
                Employee employee = employeeMapper.selectByExample(employeeExample).get(0);
                partsStream.setEmployeeId(employee.getId());
                partsStreamMapper.insertSelective(partsStream);
            }
         }
        }else{
            throw  new ServiceException("该订单不存在");
        }
    }

    /**
     * 根据订单id查询 维修配件列表
     * @param orderId
     * @return
     */
    @Override
    public List<FixTransParts> findFixTransPartsByOrderId(Integer orderId) {
        FixTransPartsExample fixTransPartsExample = new FixTransPartsExample();
        fixTransPartsExample.createCriteria().andOrderIdEqualTo(orderId);
        return fixTransPartsMapper.selectByExample(fixTransPartsExample);
    }

    @Override
    public PageInfo<PartsStream> findPartStreamByParamMap(Integer p, Map<String, Object> paramMap) {
        PageHelper.startPage(p,Constant.DEFAULT_PAGESIZA);
        List<PartsStream>  partsStreamList = partsStreamMapper.findStreamWithParamMap(paramMap);
        return new PageInfo<>(partsStreamList);
    }
}
