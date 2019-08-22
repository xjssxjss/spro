package com.spro.dao;

import com.spro.entity.sys.Attachment;

import java.util.List;

public interface AttachmentMapper extends BaseMapper<Attachment>{
    List<Attachment> queryListAttachments();

    Integer queryListAttachmentsCount();
}