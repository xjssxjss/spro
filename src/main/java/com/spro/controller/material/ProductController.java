package com.spro.controller.material;

import com.spro.common.BaseController;
import com.spro.common.GlobalConstant;
import com.spro.service.material.ProductService;
import com.spro.util.FileUtil;
import com.spro.util.GenerateCodeUtil;
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

@RequestMapping(value = "/productController")
@EnableSwagger2
@RestController
public class ProductController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/queryProductsByParams",method = RequestMethod.POST)
    @ApiOperation(notes = "根据conditions条件获取产品信息",value = "根据conditions条件获取产品信息")
    @ApiImplicitParam(name = "conditions",value = "pageSize:10</br>currentPage:1</br>code:产品code</br>productName:产品名称</br>productAliasName:产品别名</br>remark:备注</br>isValid:1",paramType = "body",dataType = "string",required = true)
    public Map<String,Object> queryProductsByParams(String conditions){
        return productService.queryProductsByParams(getSearchMap(conditions));
    }

    /*@RequestMapping(value = "/generateToken",method = RequestMethod.GET)
    public Map<String,Object> generateToken(){
        Map<String,Object> map = new HashMap<>();
        map.put("token",jedisStr.get("token"));
        return map;
    }*/

    /**
     * 新增或者更改产品信息
     * @param productForm
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateProduct",method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdateProduct(String productForm,HttpServletRequest req){
        return productService.saveOrUpdateProduct(productForm);
    }

    /**
     * 获取八位不重复编码
     */
    @RequestMapping(value = "/generateCode",method = RequestMethod.GET)
    @ApiOperation(notes = "获取八位不重复编码",value = "获取八位不重复编码")
    public String generateCode(){
        return GenerateCodeUtil.generateCode();
    }


    @RequestMapping(value = "/downloadProductHead",method = RequestMethod.GET)
    @ApiOperation(value = "下载产品上传模板",notes = "下载产品上传模板")
    public void downloadProductHead(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception{
        String path = resourceMap.get("download_path");
        String contentType = GlobalConstant.CONTENT_TYPE;
        String fileName = resourceMap.get("product_download_heaf");
        FileUtil.download(request, response, path + "\\", fileName, contentType, fileName);
    }


    @RequestMapping(value = "/prouductUploadFile",method = RequestMethod.POST)
    @ApiOperation(value = "上传产品文件",notes = "上传产品文件")
    public Map<String,Object> prouductUploadFile(@RequestParam("file") MultipartFile file[]){
        logger.info("priductFile" + file[0].getOriginalFilename());
        Map<String,Object> resultMap = null;
        try{
            resultMap = productService.prouductUploadFile(file[0].getInputStream(),file[0].getOriginalFilename());
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping(value = "/submitExcelFile",method = RequestMethod.POST)
    @ApiOperation(value = "产品进行持久化",notes = "产品进行持久化")
    public Map<String,Object> submitExcelFile(String uploadProductList){
        return productService.submitExcelFile(uploadProductList);
    }

    @RequestMapping(value = "/onExportProduct",method = RequestMethod.POST)
    @ApiOperation(value = "导出产品数据，参数conditions",notes = "导出产品数据")
    @ApiImplicitParam(name = "conditions",value = "pageSize:10</br>currentPage:1</br>code:产品code</br>productName:产品名称</br>productAliasName:产品别名</br>remark:备注</br>isValid:1",paramType = "body",dataType = "string",required = true)
    public Map<String, Object> onExportProduct(@RequestBody String conditions){
        return productService.onExportProduct(getSearchMap(conditions));
    }

}
