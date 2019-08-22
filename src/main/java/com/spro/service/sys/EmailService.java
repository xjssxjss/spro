package com.spro.service.sys;

import com.spro.common.GlobalConstant;
import com.spro.dao.EmailMapper;
import com.spro.entity.DictionaryEntries;
import com.spro.entity.sys.Email;
import com.spro.service.BaseService;
import com.spro.service.DictionaryEntriesService;
import com.spro.util.EmailSender;
import com.spro.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 邮件实现类
 */
@Service
public class EmailService extends BaseService<Email>{

    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    @Autowired
    private EmailMapper emailMapper;

    /**
     * 生成邮件
     *
     * @param slipType        单据类型
     * @param slipId          单据ID
     * @param slipCode        单据编号
     * @param addr            收件人
     * @param emailType       邮件类型（Process，Inform）
     * @param subject         邮件主题
     * @param body            邮件正文
     * @param cc
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void createEmail(Long slipId,String slipCode, String slipType,
                            String addr, String emailType, String subject, String body, String cc) throws Exception {
        Email email = new Email();
        email.setSlipId(slipId);
        email.setSlipCode(slipCode);
        email.setSlipType(slipType);
        email.setAddr(addr);
        email.setBcc(resourceMap.get("email_bcc"));
        email.setCreateTime(new Date());

        DictionaryEntries statusEntries = dictionaryEntriesService.queryByCode(GlobalConstant.EMAIL_WAIT_SEND);

        //设置邮件状态
        email.setStatus(null != statusEntries ? statusEntries.getId():null);
        email.setType(emailType);
        email.setSubject(subject);
        email.setBody(body);
        email.setCc(cc);

        insert(email);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendEmail() throws Exception{

        //查询待发送邮件状态对象
        DictionaryEntries statusEntries = dictionaryEntriesService.queryByCode(GlobalConstant.EMAIL_WAIT_SEND);

        //设置查询参数
        paramMap.put("status",null != statusEntries ? statusEntries.getId():null);
        //首先查看所有待发送邮件
        List<Email> emails = queryByParams(paramMap);

        //获取邮件已发送对象
        DictionaryEntries emailEntries = dictionaryEntriesService.queryByCode(GlobalConstant.EMAIL_ALREADY_SEND);
        //循环发送邮件
        for (Email email : emails) {
            try{
                //邮件发送
                if(!StringUtil.isEmpty(email.getAddr())){
                    //更新邮件状态为已发送
                    email.setStatus(null != emailEntries ? emailEntries.getId():null);
                    email.setSendTime(new Date());
                    EmailSender.sendMail(email.getAddr(),email.getSubject(),email.getBody());
                }
            } catch (Exception e){
                email.setErrorMsg(e.getMessage());
                email.setTryCount(email.getTryCount()+1);
            } finally {
                update(email);
            }
        }
    }

    /**
     * 统计分析email数量
     * @return
     */
    public Map<String,Object> countEmail(){
        try{
            List<Map> emailList = emailMapper.countEmail();

            data = emailList;
            message = GlobalConstant.SUCCESS_MESSAGE;
            success = true;
        }catch (Exception e){
            message = e.getMessage();
            success = false;
        }
        return result();
    }

}
