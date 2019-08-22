package com.spro.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * mybatisBaseMapper接口
 * @param <T>
 */
@Mapper
public interface BaseMapper<T> {

    /**
     * 添加记录不返回主键
     * @param entity
     * @return
     * @throws DataAccessException
     */
    int insert(T entity) throws DataAccessException;

    /**
     * 批量录入
     * @param entities
     * @return
     * @throws DataAccessException
     */
    int insertBatch(List<T> entities) throws DataAccessException;


    /**
     * 部分参数进行对象持久化
     * @param entity
     * @return
     * @throws DataAccessException
     */
    int insertSelective(T entity) throws DataAccessException;

    /**
     * 查询总记录数
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    int queryCountByParams(Map map) throws DataAccessException;

    /**
     * 查询记录 通过id
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * 更新记录
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity) throws DataAccessException;

    /**
     * 更新记录
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity) throws DataAccessException;

    /**
     * 查询记录不带分页情况
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    List<T> queryByParams(Map map) throws DataAccessException;

    /**
     * 批量更新
     * @param map
     * @return
     * @throws DataAccessException
     */
    int updateBatch(Map map) throws DataAccessException;

    /**
     * 删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id) throws DataAccessException;

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(int[] ids) throws DataAccessException;
}
