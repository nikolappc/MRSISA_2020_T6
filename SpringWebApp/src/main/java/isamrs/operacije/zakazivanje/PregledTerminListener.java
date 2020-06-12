package isamrs.operacije.zakazivanje;

import isamrs.domain.Pacijent;
import isamrs.service.PacijentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class PregledTerminListener implements ApplicationListener<OnTerminChangedEventPregled>  {
	@Autowired
	private PacijentService userService;
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnTerminChangedEventPregled event) {
		this.confirmChange(event);
	}
	
	private void confirmChange(OnTerminChangedEventPregled event) {
		Pacijent user = event.getUser();
		String naziv = event.getPregled().getTipPosete().getNaziv();
		String recipient = user.getEmail();
		String subject = "Promena termina pregleda: "+ naziv;
		String message = "Termin vaše pregleda:"
				+ naziv
				+ " je promenjen. Pregleda će biti obavljena u terminu "
				+ event.getTermin().getPocetak()
				+ " - "
				+ event.getTermin().getKraj()
				+ ".";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message);
		mailSender.send(email);
	}
	
}
