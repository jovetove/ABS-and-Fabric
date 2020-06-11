package com.example.demobase.utill.mail;

import com.example.demobase.service.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class MailServiceImplTest {

    @Value("${mail.receptionMail.addr}")
    private String receptionMailAddr;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 测试 文本邮件
     * @throws Exception
     */
    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendTextMail(receptionMailAddr,"测试文本邮箱发送","你好你好！");
    }

    /**
     * 测试 html 邮箱
     * @throws Exception
     */
    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(receptionMailAddr,"test simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\\\Users\\\\Administrator\\\\Desktop\\\\java并发学习.txt";
        mailService.sendAttachmentsMail(receptionMailAddr, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\\\Users\\\\Administrator\\\\Desktop\\\\testMail.png";

        mailService.sendInlineResourceMail(receptionMailAddr, "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");

        // 传递 emailTemplate.html 模板需要的值，并将模板转换为 String
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(receptionMailAddr,"主题：这是模板邮件",emailContent);
    }
}