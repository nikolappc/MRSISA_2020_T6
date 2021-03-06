package isamrs.registracija;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import isamrs.controller.UserController;
import isamrs.domain.Pacijent;
import isamrs.domain.ZdravstveniKarton;
import isamrs.service.PacijentService;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent>  {
	@Autowired
	private PacijentService userService;
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(OnRegistrationSuccessEvent event) {
		Pacijent user = event.getUser();
		user.setAllowed(true);
		String token = UUID.randomUUID().toString();
		userService.createVerificationToken(user,token);
		
		String recipient = user.getEmail();
		String subject = "Potvrda registracije";
		//String url = "http://localhost:8080" + "/api/confirmRegistration?token=" + token;
		String url = UserController.adresa + "/api/confirmRegistration?token=" + token;
		String message = "Uspjesno ste se registrovali. Kliknite na link da biste aktivirali svoj nalog. ";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message + url);
		mailSender.send(email);
	}
	
}
