package com.spro.dao;

import com.spro.entity.DictionaryEntries;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DictionaryEntriesMapper extends BaseMapper<DictionaryEntries>{

    //根据code查询单个对象
    DictionaryEntries queryByCode(Map<String,Object> map);

    //根据Dict_Code 查询多个对象
    List<DictionaryEntries> queryByDictCode(@Param("dictCode") String dictCode);

    //根据中文名称查询单个数据字典对象
    DictionaryEntries queryByDictionaryEntriesName(Map<String,Object> map);
}