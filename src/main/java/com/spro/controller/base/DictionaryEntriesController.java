package com.spro.controller.base;

import com.spro.entity.DictionaryEntries;
import com.spro.service.DictionaryEntriesService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dictionaryEntriesController")
@EnableSwagger2
public class DictionaryEntriesController {
    Logger logger = LoggerFactory.getLogger(DictionaryEntriesController.class);

    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    @RequestMapping(value = "/querytByDictCode",method = RequestMethod.GET)
    @ApiOperation(value = "querytByDictCode",notes = "querytByDictCode")
    public List<DictionaryEntries> querytByDictCode(@RequestParam(value = "dictCode") String dictCode){
        Map<String,Object> resultMap = dictionaryEntriesService.querytByDictCode(dictCode);
//        if(Boolean.parseBoolean(resultMap.get("success").toString())){
//            List<DictionaryEntries> listDictionaryEntries =  (List<DictionaryEntries>)resultMap.get("data");
//        }
        return (List<DictionaryEntries>)resultMap.get("data");
    }
}
