/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dtos.User;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dell
 */
public class MailUtils {

    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage mailMessage;
    //thêm tài khoản gmail tại đây
    private static String myEmail = "duytdse63047@fpt.edu.vn";
    private static String myPassword = "bulilin63047";

    private static void mailInit() {

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        mailMessage = new MimeMessage(getMailSession);

    }

    public static void sendConfirmedEmail(User to) throws AddressException, MessagingException {
        mailInit();
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to.getEmail()));
        mailMessage.setSubject("Xác nhận tài khoản", "utf-8");
        String content = "<h1>Thông tin tài khoản của bạn</h1></br>";
        content += "<a href=\"http://localhost:8080/CarRental/user/confirm-account\">Nhấn vào link để xác nhận đăng ký tài khoản</a>";
        mailMessage.setContent(content, "text/html; charset=UTF-8");
        try (Transport transport = getMailSession.getTransport("smtp")) {
            transport.connect("smtp.gmail.com", myEmail, myPassword);
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        }
    }
}
