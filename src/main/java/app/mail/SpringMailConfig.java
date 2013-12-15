package app.mail;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringMailConfig {

    @Bean
    @Lazy
    public JavaMailSenderImpl mailSender() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
        javaMail.setPort(465);
        javaMail.setHost("smtp.gmail.com.");
        javaMail.setUsername("soporte@albatross.pe");
        javaMail.setPassword("lazy song bruno mars 666");
        javaMail.setProtocol("smtp");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.smtps.quitwait", "false");

        javaMail.setJavaMailProperties(properties);
        return javaMail;
    }
}
