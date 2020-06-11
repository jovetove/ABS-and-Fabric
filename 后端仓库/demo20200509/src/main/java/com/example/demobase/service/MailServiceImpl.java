package com.example.demobase.service;

import com.example.demobase.annotation.RunTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

interface MailService {
    /**
     * 发送纯文本邮件
     * @param toAddr 发送给谁
     * @param title 标题
     * @param content 内容
     */
    public void sendTextMail(String toAddr, String title, String content);

    /**
     * 发送 html 邮件
     * @param toAddr
     * @param title
     * @param content 内容（HTML）
     */
    public void sendHtmlMail(String toAddr, String title, String content);

    /**
     *  发送待附件的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param filePath 附件地址
     */
    public void sendAttachmentsMail(String toAddr, String title, String content, String filePath);

    /**
     *  发送文本中有静态资源（图片）的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param rscPath 资源路径
     * @param rscId 资源id (可能有多个图片)
     */
    public void sendInlineResourceMail(String toAddr, String title, String content, String rscPath, String rscId);
}



/**
 * @author Administrator
 */
@Slf4j
@Component
@Service("MailServiceImpl")
public class MailServiceImpl implements MailService {
    /**
     * 自动导入mailSender库
     */
    @Autowired
    private JavaMailSender mailSender;

    /**注入常量*/
    @Value("${mail.fromMail.addr}")
    public String from;

    public MailServiceImpl(){
        log.info(from);
    }


    /**
     * 发送文本邮件
     * @param toAddr 发送目的邮箱
     * @param title  邮件标题
     * @param content 邮件内容
     */
    @Override
    @RunTimer
    @Async(value = "asyncThreadPool")
    public void sendTextMail(String toAddr, String title, String content) {
        log.info("开始发送文本邮件:");
        // 纯文本邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toAddr);
        message.setSubject(title);
        message.setText(content);
        try {
            mailSender.send(message);
            log.info("Text邮件已经发送。");
        } catch (Exception e) {
            log.error("发送Text邮件时发生异常！", e);
        }
    }

    /**
     * 发送html邮件
     * @param toAddr
     * @param title
     * @param content
     */
    @Override
    public void sendHtmlMail(String toAddr, String title, String content) {
        // html 邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }


    /**
     * 发送带附件的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String toAddr, String title, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceMail(String toAddr, String title, String content, String rscPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}
