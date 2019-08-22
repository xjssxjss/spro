package com.spro.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spro.common.BaseObject;
import com.spro.common.GlobalConstant;
import com.spro.dao.BaseMapper;
import com.spro.dao.FiscalMonthMapper;
import com.spro.entity.finance.FiscalMonth;
import com.spro.service.sys.EmailService;
import com.spro.service.sys.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象类
 * @param <T>
 */
public abstract class BaseService<T> extends BaseObject{

    @Autowired
    private SequenceServiceImpl sequenceService;

    @Autowired
    public EmailService emailService;

    @Autowired
    private FiscalMonthMapper fiscalMonthMapper;

    @Autowired
    private BaseMapper<T> baseMapper;

    //分页对
    public static PageInfo<Map<String,Object>> pageInfo = null;

    public static boolean success = false;
    public static Object data = null;
    public static String message = GlobalConstant.ERROR_MESSAGE;

    private static Map<String,Object> resultMap = new HashMap();
    public static Map<String,Object> paramMap = new HashMap();

    public static Map<String,Object> result = new HashMap<>();

    /**
     * 封装返回前台的结果集
     * @return
     */
    public static Map<String,Object> result(){
        resultMap.put("success",success);
        resultMap.put("message",message);
        resultMap.put("data",success ? data: null);
        return resultMap;
    }




    public void startPage(Map<String,Object> pageMap){
        Integer currentPage = 1;
        Integer pageSize = 10;
        if(null != pageMap){
            currentPage = (Integer) pageMap.get("currentPage");
            pageSize = (Integer) pageMap.get("pageSize");
        }
        try{
            PageHelper.startPage(currentPage,pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加记录
     * @param entity
     * @return
     * @throws Exception
     */
    public int insert(T entity) throws Exception{
        int result= baseMapper.insert(entity);
        return result;
    }

    /**
     * 批量添加记录
     * @param entities
     * @return
     * @throws Exception
     */
    public int insertBatch(List<T> entities) throws Exception{
        return baseMapper.insertBatch(entities);
    }


    public int insertSelective(T entity){
        return baseMapper.insertSelective(entity);
    }

    /**
     * 根据参数统计记录数
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public int queryCountByParams(Map map)throws Exception{
        return baseMapper.queryCountByParams(map);
    }

    /**
     * 查询记录通过id
     * @param id
     * @return
     * @throws Exception
     */
    public T queryById(Integer id) throws Exception{
        return (T)baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param baseQuery
     * @return
     * @throws Exception
     *//*
    public Object<T> queryForPage(PageUtil baseQuery)throws Exception{
        PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
        List<T> list= baseMapper.queryForPage(baseQuery);
        PageInfo<T> pageInfo=new PageInfo<T>(list);
        return pageInfo;
    }*/

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public List<T> queryByParams(Map map)throws Exception{
        return baseMapper.queryByParams(map);
    }

    /**
     * 更新记录
     * @param entity
     * @return
     * @throws Exception
     */
    public int update(T entity)throws Exception{
        return baseMapper.updateByPrimaryKey(entity);
    }

    /**
     * 批量更新
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public int updateBatch(Map map) throws Exception{
        return baseMapper.updateBatch(map);
    }

    /**
     * 更新部分字段
     * @param eneity
     * @return
     */
    public int updateByPrimaryKeySelective(T eneity){
        return baseMapper.updateByPrimaryKeySelective(eneity);
    }

    /**
     * 删除记录
     * @param id
     * @return
     * @throws Exception
     */
    public int delete(Integer id) throws Exception{
        return  baseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int deleteBatch(int[] ids) throws Exception{
        return  baseMapper.deleteBatch(ids);
    }

    public String generateCode(String className,String prefixCode){
        return sequenceService.generateCode(className,prefixCode);
    }

    /**
     * 查询当前财务月
     * @return
     */
    public FiscalMonth queryCurrentFiscalMonth(){
        FiscalMonth fiscalMonth = fiscalMonthMapper.queryCurrentFiscalMonth();
        return fiscalMonth;
    }

    public Object parseObject(String userForm,Object obj){
        return JSON.parseObject(userForm, new TypeReference<Object>() {});
    }

}
