package com.spro.controller;

import com.spro.common.BaseController;
import com.spro.service.UploadService;
import com.spro.service.sys.EmailService;
import com.spro.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/uploadController")
@EnableSwagger2
@RestController
public class UploadController extends BaseController{

    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiOperation(value = "上傳文件",notes = "上傳文件")
    public List<Map<String,Object>> uploadFile(@RequestParam("fileName") MultipartFile file[]) throws IOException {
        logger.info("upload>>>>>>>>>>>>>"+file);

        List<Map<String,Object>> resultMapList = new ArrayList<>();
        //说明上传了文件
        logger.info("length" +file.length);
        if(file.length > 0){
            for (int i = 0; i < file.length; i++) {
                //后缀名
                String ext = file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."),file[i].getOriginalFilename().length());
                if(!StringUtil.isEmpty(ext)){
                    resultMapList.add(uploadService.uploadFile(ext,file[i].getInputStream()));
                }
            }
        }
        return resultMapList;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ApiOperation(value = "发送邮件",notes = "发送邮件")
    public Map<String,Object> upload() throws Exception{
        logger.info("upload>>>>>>>>>>>>>");
        return uploadService.sendEmail();
    }

    @RequestMapping(value = "/countEmail",method = RequestMethod.GET)
    @ApiOperation(value = "统计邮件",notes = "统计邮件")
    public Map<String,Object> countEmail(){
        return emailService.countEmail();
    }

}
