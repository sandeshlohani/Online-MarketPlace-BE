package edu.miu.cs545.waa.service;

public interface EmailService {

  void sendSimpleMessage(String to,
      String subject,
      String text);
}
