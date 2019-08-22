package com.spro.service;

import com.spro.enums.ResultCode;
import com.spro.service.sys.EmailService;
import com.spro.util.DateUtil;
import com.spro.util.FTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService extends BaseService {

    @Autowired
    private EmailService emailService;

    /**
     * 上传文件方法
     * @param file
     * @return
     */
    @Transactional
    public Map<String,Object> uploadFile(String ext,InputStream file){
        Map<String,Object> map = new HashMap<>();
        String fileSavePath = "/home/ftpuser/www/12345/";
        //说明有文件进行上传
        try{
            double size = file.available()/1024;
            success = FTPUtils.getInstance().uploadFile(fileSavePath,
                    UUID.randomUUID().toString() + ext,
                    file);

            if(success){
                map.put("fileSaveName",UUID.randomUUID().toString() + ext);
                map.put("fileSavePath",fileSavePath);
                if(size > 1024){
                    map.put("fileSize", new BigDecimal(size/1024).setScale(2,BigDecimal.ROUND_HALF_UP) +"MB");
                } else {
                    map.put("fileSize",new BigDecimal(size).setScale(2,BigDecimal.ROUND_HALF_UP) +"KB");
                }

                try{
                    map.put("uploadTime", DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
                } catch (Exception e){
                    e.printStackTrace();
                }
                message = "文件上传成功!!";
                success = true;
            }
        } catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        data = map;
        return result();
    }


    public Map<String,Object> sendEmail(){
        Map map = new HashMap();
        String [] attrs = new String[]{"D:\\psp_script.sql","D:\\qrcode.jpg"};
        String body = "<html><body><h3>来我们一起百度</h3></br></br><a href='www.baidu.com'>百度一下</a></br></br>这是系统生成的邮件，请不要回复或转发!!</h1></body></html>";
        try {
            emailService.createEmail(12345678L,"201903280000001","WORK_SHEET",resourceMap.get("sean"),
                    "1010","众里寻他千百度",body,null);
            message = ResultCode.getNameByToothBrushEnum(201);
            success = true;
            map.put("sendTime",new Date());
            data = map;
        } catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }
}
