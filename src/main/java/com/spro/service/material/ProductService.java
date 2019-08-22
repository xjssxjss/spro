package com.spro.service.material;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.spro.common.GlobalConstant;
import com.spro.dto.material.ProductExcelReadDto;
import com.spro.entity.DictionaryEntries;
import com.spro.entity.material.Product;
import com.spro.service.BaseService;
import com.spro.service.DictionaryEntriesService;
import com.spro.util.GenerateCodeUtil;
import com.spro.util.StringUtil;
import com.spro.util.excel.ExcelReadKit;
import com.spro.util.excel.ExcelReadResultBean;
import com.spro.util.excel.ExcelWriteBean;
import com.spro.util.excel.ExcelWriteKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品Service
 */
@Service
@Transactional
public class ProductService extends BaseService<Product> {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    /**
     * 分页查询产品结果集
     * @param paramMap  查询参数
     * @return
     */
    public Map<String,Object> queryProductsByParams(Map<String,Object> paramMap){

        Map<String,Object> result = new HashMap<>();
        if(null != paramMap){
            String code = (String)paramMap.get("code");
            paramMap.put("code", StringUtil.likeParam(code));
            String productName = (String) paramMap.get("productName");
            paramMap.put("productName",StringUtil.likeParam(productName));
            //paramMap.put("isValid",StringUtil.booleanParam(paramMap.get("isValid")));

            String productAliasName = (String) paramMap.get("productAliasName");
            paramMap.put("productAliasName",StringUtil.likeParam(productAliasName));

            String remark = (String) paramMap.get("remark");
            paramMap.put("remark",StringUtil.likeParam(remark));

            Integer brandId = (Integer) paramMap.get("brandId");
            paramMap.put("brandId",brandId);
            //开启分页
            startPage(paramMap);
        }
        try{
            List<Product> productList = queryByParams(paramMap);
            Integer totalCount = queryCountByParams(paramMap);

            result.put("dataList",productList);
            result.put("totalCount",totalCount);
            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
            data = result;
        } catch (Exception e){
            message = e.getMessage();
            success = false;
        }
        return result();
    }


    /**
     * 新增或更新产品信息
     * @param productForm
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Map<String,Object> saveOrUpdateProduct(String productForm){
        //获取对象参数
        Product product = JSON.parseObject(productForm, new TypeReference<Product>() {});
        //Product paramMap = (Product) JSON.parseObject(productForm);
        if(null != product && null != product.getId()){
            updateIt(product);
            testTran(product);
        } else {
            //insert(product);
        }
//       try {
//           if(null != product && null != product.getId()){
//                updateIt(product);
//                testTran(product);
//           } else {
//               insert(product);
//           }
//           success = true;
//           message = GlobalConstant.SUCCESS_INSERT_MESSAGE;
//       } catch (Exception e){
//           throw new RuntimeException("失败");
//           //message = e.getMessage();
//           //success = false;
//       }

        return result();
    }

    public void updateIt(Product product){
        updateByPrimaryKeySelective(product);
    }

    public void testTran(Product product){
        int a = 1/0;
        System.out.println("我执行了");
        //更新测试
        updateByPrimaryKeySelective(product);
    }

    /**
     * 上传文件模板
     * @param is
     * @return
     */
    public Map<String,Object> prouductUploadFile(InputStream is, String fileName){

        if(!StringUtil.isEmpty(fileName) && fileName.trim().equals(resourceMap.get("product_download_heaf"))){
            List<ProductExcelReadDto> productExcelReadDtoList = new ArrayList<>();
            Map<String,Object> result = new HashMap<>();
            try {
                ExcelReadResultBean excelReadResultBean = ExcelReadKit.readExcel(is,0);
                //说明读取成功
                if(excelReadResultBean.getResult() && StringUtil.isEmpty(excelReadResultBean.getErrMsg())){
                    List readList = excelReadResultBean.getContentList();

                    //获取列表
                    if(null != readList && readList.size()>2){
                        for(int i = 2;i < readList.size();i++){
                            //获取Object数组
                            ArrayList<Object> obj = (ArrayList<Object>) readList.get(i);

                            ProductExcelReadDto productExcelReadDto = checkReadExcelData(obj);
                            productExcelReadDto.setCount((i+1));
                            productExcelReadDtoList.add(productExcelReadDto);
                        }
                    }
                }
                success = true;
                message = GlobalConstant.SUCCESS_UPLOAD_MESSAGE;
                result.put("dataList",productExcelReadDtoList);
                result.put("totalCount",productExcelReadDtoList.size());
                data = result;
            } catch (Exception e){
                message = e.getMessage();
                success = false;

                e.printStackTrace();
            }
        } else {
            message = GlobalConstant.UPLOAD_ERROR_MESSAGE;
            success = false;
        }
       return result();
    }

    //check 读取的Excel是否正确
    public ProductExcelReadDto checkReadExcelData(ArrayList<Object> obj){
        StringBuffer errMsg = new StringBuffer();
        //声明产品结果集对象
        ProductExcelReadDto productExcelReadDto = null;

        if(null != obj){
            productExcelReadDto = new ProductExcelReadDto();
            try {
                if((!StringUtil.isEmpty(null == obj.get(0)?"":obj.get(0).toString()))){
                    productExcelReadDto.setProductName(obj.get(0).toString());  //设置名称
                } else {
                    errMsg.append("产品名称不可为空，");
                }
                if((!StringUtil.isEmpty(null == obj.get(1)?"":obj.get(1).toString()))){
                    productExcelReadDto.setProductAliasName(obj.get(1).toString());  //设置别名
                } else{
                    errMsg.append("产品别名不可为空，");
                }
                if((!StringUtil.isEmpty(null == obj.get(2)?"":obj.get(2).toString()))){
                    productExcelReadDto.setProductEnglishName(obj.get(2).toString());  //设置英文名称
                }
                if(null != obj.get(3)){
                    productExcelReadDto.setStanardPrice(new BigDecimal(obj.get(3).toString()));
                }

                if(null != obj.get(4)){
                    try{
                        if(!StringUtil.isEmpty(obj.get(4).toString()) && obj.get(4).toString().trim().equalsIgnoreCase("是")){
                            productExcelReadDto.setIsValid(true);
                        } else {
                            productExcelReadDto.setIsValid(false);
                        }
                    } catch (Exception e){
                        productExcelReadDto.setIsValid(true);
                    }
                }

                if(null != obj.get(5)){
                   try{
                       if(!StringUtil.isEmpty(obj.get(5).toString()) && obj.get(5).toString().trim().equalsIgnoreCase("是")){
                           productExcelReadDto.setIsSample(true);
                       } else {
                           productExcelReadDto.setIsSample(false);
                       }
                   } catch (Exception e){
                       productExcelReadDto.setIsSample(true);
                   }
                }

                //所属品牌
                if(null != obj.get(6)){
                    DictionaryEntries brand = dictionaryEntriesService.queryByDictionaryEntriesName(obj.get(6).toString());
                    if(null != brand){
                        productExcelReadDto.setBrandName(obj.get(6).toString());
                        productExcelReadDto.setBrandId(brand.getId());
                        productExcelReadDto.setBrand(brand);
                    } else {
                        errMsg.append("产品所属品牌不存在，");
                    }
                } else {
                    errMsg.append("产品所属品牌不可为空，");
                }

                //产品备注
                if(null != obj.get(7)){
                    productExcelReadDto.setRemark(obj.get(7).toString());
                }
            } catch (Exception e){
                errMsg.append(e.getMessage());
            }
        }

       try {
           productExcelReadDto.setErrMsg(errMsg.substring(0,errMsg.lastIndexOf("，")));
       }catch (Exception e){
       }
        return productExcelReadDto;
    }


    @Transactional
    public Map<String,Object> submitExcelFile(String dataJson){
        JSONArray array = JSON.parseArray(dataJson);
        if(null != array && array.size()>0){
            for (int i = 0 ; i < array.size();i++){
               try {
                   Object obj = array.get(i);
                   String jsonStr = JSON.toJSONString(obj);
                   Product product = JSON.parseObject(jsonStr , new TypeReference<Product>() {});
                   product.setCode(GenerateCodeUtil.generateCode());
                   //获取产品
                   insert(product);
                   logger.info("product>>>>>" + product);
               } catch (Exception e){
                   success = false;
                   message = e.getMessage();
               }
            }
        }
        success = true;
        message = "数据录入成功，请至产品详情界面进行查看!!";
        return result();
    }


    public Map<String,Object> onExportProduct(Map<String,Object> paramMap){
        Map<String,Object> result = new HashMap<>();
        String code = (String)paramMap.get("code");
        paramMap.put("code", StringUtil.likeParam(code));
        String productName = (String) paramMap.get("productName");
        paramMap.put("productName",StringUtil.likeParam(productName));
        paramMap.put("isValid",StringUtil.booleanParam(paramMap.get("isValid")));

        String productAliasName = (String) paramMap.get("productAliasName");
        paramMap.put("productAliasName",StringUtil.likeParam(productAliasName));

        String remark = (String) paramMap.get("remark");
        paramMap.put("remark",StringUtil.likeParam(remark));

        Integer brandId = (Integer) paramMap.get("brandId");
        paramMap.put("brandId",brandId);
        try{
            //开启分页
            List<Product> productList = queryByParams(paramMap);
            //定制头信息
            List<String> listHeader = new ArrayList<>();
            listHeader.add("产品名称");
            listHeader.add("产品别名");
            listHeader.add("产品英文名称");
            listHeader.add("产品市场标准价格");
            listHeader.add("是否有效");
            listHeader.add("是否小样");
            listHeader.add("所属品牌");
            listHeader.add("备注");

            List excelExportList = new ArrayList();

            if(null != productList && productList.size()>0){
                for (Product product : productList) {
                    List<Object> listObj = new ArrayList<Object>();
                    listObj.add(product.getProductName());
                    listObj.add(product.getProductAliasName());
                    listObj.add(product.getProductEnglishName());
                    listObj.add(product.getStanardPrice());
                    listObj.add(product.getIsValid()?"是":"否");
                    listObj.add(product.getIsSample()?"是":"否");
                    listObj.add(product.getBrand()==null?"":product.getBrand().getChineseName());
                    listObj.add(product.getRemark());
                    excelExportList.add(listObj);
                }
            }


            ExcelWriteBean excelWriteBean = new ExcelWriteBean();
            excelWriteBean.setFileName("20190407");
            excelWriteBean.setFileType("xls");
            excelWriteBean.setHeaderList(listHeader);
            excelWriteBean.setSheetName("sheet1");
            excelWriteBean.setContentList(excelExportList);

            new ExcelWriteKit().outputExcel(excelWriteBean,"D:\\");
            //导出数据
            result.put("dataList",productList);
            result.put("exportPath","D:\\"+excelWriteBean.getFileName()+"."+excelWriteBean.getFileType());
            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
            data = result;
        } catch (Exception e){
            message = e.getMessage();
            success = false;
        }
        return result();
    }
}
