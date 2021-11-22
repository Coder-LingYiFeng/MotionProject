package com.ahpu.motion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;


@Slf4j
@Component
public class MailSendUtil {
    @Value("${spring.mail.username}")
    private String form;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(form);
            helper.setTo(to);
            helper.setSubject("情绪检测系统报警邮件");
            helper.setText(content, true);
            mailSender.send(message);
            log.info("邮件发送成功");
        } catch (Exception e) {
            log.info(String.valueOf(e));
            log.info("邮件发送失败");
        }
    }
}
