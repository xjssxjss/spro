package com.spro.controller.material;

import com.spro.common.BaseController;
import com.spro.common.GlobalConstant;
import com.spro.service.material.InventoryService;
import com.spro.util.FileUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/inventoryController")
@RestController
@EnableSwagger2
public class InventoryController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/queryInventoryByParams")
    @ApiOperation(notes = "根据conditions查询条件获取产品库存信息", value = "获取产品库存信息")
    @ApiImplicitParam(name = "conditions",value = "查询参数列表：<br/> currentPage:1<br/>pageSize:10<br/>productName:产品名称",paramType = "body",dataType = "string",required = true)
    public Map<String,Object> queryInventoryByParams(String conditions){
       return inventoryService.queryInventoryByParams(getSearchMap(conditions));
    }

    @PostMapping("/saveOrUpdateInventory")
    @ApiOperation(value = "新增或保存库存",notes = "新增或保存库存")
    public Map<String,Object> saveOrUpdateInventory(String inventoryForm){
        return inventoryService.saveOrUpdateInventory(inventoryForm);
    }

    @GetMapping("/downloadProductHead")
    @ApiOperation(value = "下载库存上传模板",notes = "下载库存上传模板")
    public void downloadProductHead(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{
        String path = resourceMap.get("download_path");
        String contentType = GlobalConstant.CONTENT_TYPE;
        String fileName = resourceMap.get("inventory_download_heaf");
        FileUtil.download(request, response, path + "\\", fileName, contentType, fileName);
    }

    //@RequestMapping(value = "/inventoryUploadFile",method = RequestMethod.POST)
    //@ApiOperation(value = "上传库存信息",notes = "上传库存信息")
    public Map<String,Object> prouductUploadFile(@RequestParam("file") MultipartFile file[]){
        logger.info("priductFile" + file[0].getOriginalFilename());
        Map<String,Object> resultMap = null;
        try{
            //resultMap = productService.prouductUploadFile(file[0].getInputStream(),file[0].getOriginalFilename());
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping(value = "/queryInventoryByProductId",method = RequestMethod.GET)
    public Map<String,Object> queryInventoryByProductId(@RequestParam("productId") Integer productId){
        return inventoryService.queryInventoryByProductId(productId);
    }
}
