package com.spro.service.sys;

import com.github.pagehelper.PageInfo;
import com.spro.common.GlobalConstant;
import com.spro.dao.AttachmentMapper;
import com.spro.entity.sys.Attachment;
import com.spro.service.BaseService;
import com.spro.service.DictionaryEntriesService;
import com.spro.service.UploadService;
import com.spro.util.excel.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * 附件Service层
 */
@Service
public class AttachmentService extends BaseService<Attachment>{

    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    public Map<String,Object> queryListAttachments(Map<String,Object> paramMap){
        try{
            Map<String,Object> result = new HashMap<>();
            //开始分页
            startPage(paramMap);
            //查询附件列表
            List<Attachment> attachmentList = attachmentMapper.queryListAttachments();
            pageInfo = new PageInfo(attachmentList);

            Integer getDataCount = attachmentMapper.queryListAttachmentsCount();
            result.put("tableDataCount",getDataCount);
            result.put("attachmentList",attachmentList);

           try {
               String title = "AttachmentList";
               String[] rowsName = new String[]{"序号","文件名称","真实名称","文件大小","上传文件类型"};
               List<Object[]>  dataList = new ArrayList<Object[]>();
               Object[] objs = null;
               for (int i = 0; i < attachmentList.size(); i++) {
                   Attachment data = attachmentList.get(i);
                   objs = new Object[rowsName.length];
                   objs[0] = data.getId();
                   objs[1] = data.getFileName();
                   objs[2] = data.getFileSaveName();
                   objs[3] = data.getFileSize();
                   objs[4] = data.getAttach().getChineseName();
                   dataList.add(objs);
               }
               ExportExcelUtil ex = new ExportExcelUtil(title, rowsName, dataList);
               ex.export();
           }catch (Exception e){
               e.printStackTrace();
            }

            data = result;
            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
        }catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }

    public Map<String,Object> uploadFile(String fileName,String ext, InputStream is,Integer fileType){
        try{
            //上传文件
            Map<String,Object> result =  uploadService.uploadFile(ext,is);

            Map<String,Object> resultMap = (Map<String,Object>)result.get("data");
            if(Boolean.parseBoolean(result.get("success").toString())){
                //保存到数据库
                Attachment attachment = new Attachment();
                attachment.setFileName(fileName);
                attachment.setFileSaveName(resultMap.get("fileSaveName").toString());
                attachment.setFilePath(resultMap.get("fileSavePath").toString());
                attachment.setFileSize(resultMap.get("fileSize").toString());
                attachment.setFileType(fileType);
                attachment.setUploadTime(new Date());
                attachment.setSlipType("slipType");
                attachment.setIsValid(true);
                attachment.setUploadUser("root");
                insert(attachment);
            }
            success = true;
            message = "上传成功";
        }catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }
}
