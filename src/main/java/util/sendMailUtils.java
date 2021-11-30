package util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class sendMailUtils {
    private String Subject = "subject";
    private String Content = "content";

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setContent(String content) {
        Content = content;
    }

    public sendMailUtils() {
    }

    public void send(final String sendAddress, String receiveAddress, final String authorizedPassword) {
        try {
            Properties properties = new Properties();

            properties.setProperty("mail.host", "smtp.qq.com");

            properties.setProperty("mail.transport.protocol", "smtp");

            properties.setProperty("mail.smtp.auth", "true");


            //QQ存在一个特性设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sendAddress, authorizedPassword);
                }
            });

            //开启debug模式
            session.setDebug(true);

            //获取连接对象
            Transport transport = session.getTransport();

            //连接服务器
            transport.connect("smtp.qq.com", sendAddress, authorizedPassword);

            //创建邮件对象
            MimeMessage mimeMessage = new MimeMessage(session);

            //邮件发送人
            mimeMessage.setFrom(new InternetAddress(sendAddress));

            //邮件接收人
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveAddress));

            //邮件标题
            mimeMessage.setSubject(Subject);

            //邮件内容
            mimeMessage.setContent(Content, "text/html;charset=UTF-8");

            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            //关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


