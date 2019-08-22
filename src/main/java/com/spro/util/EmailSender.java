package com.spro.util;

import com.spro.common.BaseObject;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * @Description 邮件发送类
 * @Created Date:2019/3/27
 * @Author yahui.xie
 */
public class EmailSender extends BaseObject {

    /**
     * 邮件发送（普通邮件，附件邮件）
     * @param to      收件地址
     * @param cc      抄送地址
     * @param bcc     暗送地址
     * @param subject 邮件主题
     * @param body    邮件正文
     * @param attachmentFullPath 附件地址
     * @throws Exception
     */
    public static void sendMail(String to, String cc, String bcc, String subject, String body,String attachmentFullPath [],boolean isImpotent) throws Exception {
        boolean isAttachment = false;
        if (to == null || to.length() == 0) {
            return;
        }

        if(null != attachmentFullPath && attachmentFullPath.length > 0){
            isAttachment = true;
        }
        try {
            String smtpServer = resourceMap.get("smtp_server");
            String smtpAccount = resourceMap.get("smtp_account");
            String smtpAccountName = resourceMap.get("smtp_account_name");
            String smtpPwd = resourceMap.get("smtp_pwd");
            String smtpAuthType = resourceMap.get("smtp_auth_type");

            //实例化发送邮件对象
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

            senderImpl.setHost(smtpServer);
            senderImpl.setUsername(smtpAccount);
            senderImpl.setPassword(smtpPwd);

            Properties prop = new Properties();
           // prop.put("mail.smtp.auth", smtpAuthType);
            prop.put("mail.smtp.timeout", "60000");
            senderImpl.setJavaMailProperties(prop);

            MimeMessage mailMessage = senderImpl.createMimeMessage();
            //设置邮件信息的重要性
            mailMessage.addHeader("Importance",isImpotent?"High":"Normal");

            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");

            InternetAddress[] internetAddressTo = InternetAddress.parse(to);
            messageHelper.setTo(internetAddressTo);

            if (cc != null && cc.length() > 0) {
                InternetAddress[] internetAddressCc = InternetAddress.parse(cc);
                messageHelper.setCc(internetAddressCc);
            }

            if (bcc != null && bcc.length() > 0) {
                InternetAddress[] internetAddressBcc = InternetAddress.parse(bcc);
                messageHelper.setBcc(internetAddressBcc);
            }

            messageHelper.setFrom(smtpAccount, smtpAccountName);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);

            //如果包含一个到多个附件附件
            if(isAttachment){
                File file = null;
                for (int i = 0; i < attachmentFullPath.length; i++) {
                    file = new File(attachmentFullPath[i]);
                    messageHelper.addAttachment(file.getName(), file);
                }
            }

            senderImpl.send(mailMessage);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 发送一封普通的邮件
     * @param toList   接收人
     * @param subject  主题
     * @param message   发送body
     * @throws Exception
     */
    public static void sendMail(String toList, String subject, String message)
            throws Exception {
        sendMail(toList,null,null, subject, message,null,false);
    }
}
