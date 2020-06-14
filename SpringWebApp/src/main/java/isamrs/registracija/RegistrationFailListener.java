package isamrs.registracija;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import isamrs.domain.Pacijent;
import isamrs.service.PacijentService;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFailListener implements ApplicationListener<OnRegistrationFailEvent> {
	@Autowired
	private MailSender mailSender;
	@Override
	public void onApplicationEvent(OnRegistrationFailEvent event) {
		Pacijent user = event.getUser();
		
		String recipient = user.getEmail();
		String subject = "Odbijanje registracije";
		String message = "Sa žalošću vas obaveštavamo vas da je vaša registracija odbijena.\nRazlog odbijanja: "+event.getOpis();
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message);
		mailSender.send(email);		
	}

}
