import com.online.store.onlineshopcommon.entity.User;
import com.online.store.onlineshopcommon.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMail_shouldSendSimpleMailMessage() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "This is a test message.";

        mailService.sendMail(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(text, sentMessage.getText());
    }

    @Test
    void sendWelcomeMail_shouldSendMimeMessage() throws MessagingException {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("mail/welcome.html"), any(Context.class))).thenReturn("<html>Welcome, john</html>");


        mailService.sendWelcomeMail(user);

        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendWelcomeMail_shouldThrowExceptionIfMessagingFails() {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");

        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Simulated failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mailService.sendWelcomeMail(user));

        assertTrue(exception.getMessage().contains("Failed to send welcome email"));
    }

}
