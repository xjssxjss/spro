package com.spro.service;

import com.spro.common.GlobalConstant;
import com.spro.dao.DictionaryEntriesMapper;
import com.spro.entity.DictionaryEntries;
import com.spro.util.JedisUtil;
import com.spro.util.SerializeUtil;
import com.spro.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典条目实现类
 */
@Service
@Transactional
public class DictionaryEntriesService extends BaseService<DictionaryEntries> {

    @Autowired
    private DictionaryEntriesMapper dictionaryEntriesMapper;

    Logger logger = LoggerFactory.getLogger(DictionaryEntriesService.class);

    /**
     * 查询数据字典条目所有数据
     * @return
     */
    public Map<String,Object> getDictionaryEntries(){
        Map resultMap = new HashMap();
        List<DictionaryEntries> dictionary = null;
       try{
           Map map = new HashMap();
           dictionary = queryByParams(map);
       } catch (Exception e){
            e.printStackTrace();
       }
       resultMap.put("data",dictionary);
       return resultMap;
    }

    public Map<String,Object> index1(String dictCode){
        Map resultMap = new HashMap();

        List<DictionaryEntries> data = null;
        List<DictionaryEntries> resultList = new ArrayList<>(0);

        JedisUtil jedisUtil = JedisUtil.getInstance();
        JedisUtil.Strings strings = jedisUtil.new Strings();

        if(!StringUtil.isEmpty(dictCode)){
            byte[] personBy = strings.get(SafeEncoder.encode("spro:com.spro:entiry:DictionaryEntries"));
            Map reportMap = (Map) SerializeUtil.unserialize(personBy);
            data =  (List<DictionaryEntries>) reportMap.get("data");

            for(DictionaryEntries dicObj:data){
                if(dictCode.equalsIgnoreCase(dicObj.getDictCode() == null ? "" : dicObj.getDictCode())){
                    resultList.add(dicObj);
                }
            }
            success = true;

            message = GlobalConstant.SUCCESS_MESSAGE;
        } else {
            success = false;
        }

        resultMap.put("data",resultList);
        resultMap.put("success",success);
        resultMap.put("message",message);

        return resultMap;
    }

    /**
     * 根据Code查询单个对象
     * @return
     */
    public DictionaryEntries queryByCode(String param){
        paramMap.put("code", param);
        return dictionaryEntriesMapper.queryByCode(paramMap);
    }

    /**
     * 根据Code查询单个对象
     * @return
     */
    public DictionaryEntries queryByDictionaryEntriesName(String param){
        paramMap.put("chineseName", param);
        return dictionaryEntriesMapper.queryByDictionaryEntriesName(paramMap);
    }

    /**
     * 根据DictCode查询多个对象
     * @return
     */
    public Map<String,Object> querytByDictCode(String param){
        try{
            List<DictionaryEntries> dictionaryEntriesList = dictionaryEntriesMapper.queryByDictCode(param);
            data = dictionaryEntriesList;
            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
        }catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }
}
