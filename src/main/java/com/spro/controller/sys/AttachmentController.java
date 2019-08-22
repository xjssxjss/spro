package com.spro.controller.sys;

import com.spro.common.BaseController;
import com.spro.service.sys.AttachmentService;
import com.spro.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@Api(tags={"附件相关接口"})
@RequestMapping(value = "/attachmentController")
@RestController
@EnableSwagger2
public class AttachmentController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(AttachmentController.class);
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 查询附件列表
     * @return
     */
    @PostMapping(value = "/queryListAttachments")
    @ApiOperation(notes = "查询所有附件列表描述",value = "查询所有附件列表")
    public Map<String,Object> queryListAttachments(String conditions){
        logger.info("访问成功");
        return attachmentService.queryListAttachments(getSearchMap(conditions));
    }

    /**
     * 上传附件
     * @return
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ApiOperation(notes = "上传附件",value = "上传附件")
    public Map<String,Object> uploadFile(@RequestBody MultipartFile file[],
                                          @RequestParam("fileType") Integer fileType){
        Map<String,Object> resultMap = null;
        if(file[0].getSize()>0){
            //后缀名
            String ext = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."),file[0].getOriginalFilename().length());
            if(!StringUtil.isEmpty(ext)){
               try{
                   resultMap = attachmentService.uploadFile(file[0].getOriginalFilename(),ext,file[0].getInputStream(),fileType);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        } else {
            resultMap = new HashMap<>();
            resultMap.put("message","请上传附件");
            resultMap.put("success",false);
        }
        return resultMap;
    }
}
