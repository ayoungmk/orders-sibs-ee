package eu.sibs.ordersibs.service;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject, String text);

}
