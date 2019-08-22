package com.spro.service;

import com.spro.entity.Dictionary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DictionaryService extends BaseService<Dictionary>{
    Map resultMap = new HashMap();
    public Map<String,Object> findByid(){
        Dictionary dictionary = null;
       try{
            dictionary =  queryById(10);
           System.out.println(dictionary.toString());
       }catch (Exception e){
           e.printStackTrace();
       }

        resultMap.put("message","数据访问成功");
       resultMap.put("success",true);
       resultMap.put("data",dictionary);
       return resultMap;
    }
}
