package bookstore.Service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender mailer;
	
	public boolean sendMail(String content, String toEmail, String subbject) {
		boolean isSent = false;
		try {
			MimeMessage message = mailer.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        helper.setTo(toEmail);
	        helper.setSubject(subbject);
	        helper.setText(content, true);
	        
	        mailer.send(message);
	        
	        isSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSent;
	}
}
