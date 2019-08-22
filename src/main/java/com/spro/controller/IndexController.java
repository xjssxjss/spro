package com.spro.controller;

import com.spro.common.BaseController;
import com.spro.common.GlobalConstant;
import com.spro.service.DictionaryEntriesService;
import com.spro.service.DictionaryService;
import com.spro.service.MenuService;
import com.spro.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping(value = "/indexController")
@RestController
public class IndexController extends BaseController{
    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map<String,Object> index(){
        dictionaryService.findByid();
        return dictionaryEntriesService.getDictionaryEntries();
    }

    @RequestMapping(value = "/index1", method = RequestMethod.POST)
    public Map<String,Object> index1(@RequestParam(required = true,name = "type") String type){
        return dictionaryEntriesService.index1(type);
    }

    @RequestMapping(value = "getMenus" ,method = RequestMethod.GET)
    public Map<String,Object> getMenus(){
        return getResultMap(menuService.getMenus());
    }

    @RequestMapping(value = "postMenus" ,method = RequestMethod.POST)
    public Map<String,Object> postMenus(){

        return  getResultMap(menuService.getMenus());
    }

    /**
     * 下载模板
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    public void downLoadDisToothBrushExcel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       String path = resourceMap.get("download_path");
       String contentType = GlobalConstant.CONTENT_TYPE;
       String fileName = resourceMap.get("test_download_name");
       FileUtil.download(request, response, path + "\\", fileName, contentType, fileName);
    }

}
