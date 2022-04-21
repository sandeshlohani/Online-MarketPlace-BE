package edu.miu.cs545.waa.service.impl;

import edu.miu.cs545.waa.constant.EmailConstants;
import edu.miu.cs545.waa.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

  private static final String NO_REPLY_ADDRESS = "test@yopmail.com";

  private final JavaMailSender emailSender;

  @Autowired
  public EmailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  /**
   * @param to
   * @param subject
   * @param text
   */
  public void sendSimpleMessage(String to, String subject, String text) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(NO_REPLY_ADDRESS);
      //for now to is static
      message.setTo(EmailConstants.EMAIL_PROXY_TO);
      message.setSubject(subject);
      message.setText(text);
      emailSender.send(message);
    } catch (MailException exception) {
      log.info(EmailConstants.EMAIL_ERROR, exception.getCause());
    }
  }
}
