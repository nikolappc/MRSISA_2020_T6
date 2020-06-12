package isamrs.operacije.doktori;

import isamrs.domain.Lekar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class DoktorDodatListenerPregled implements ApplicationListener<OnDoktorDodatEventPregled> {
    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(OnDoktorDodatEventPregled event) {
        Lekar user = (Lekar)event.getSource();
        String naziv = event.getPregled().getTipPosete().getNaziv();
        String recipient = user.getEmail();
        System.out.println("SALJEM MEJL "+ recipient);
        String subject = "Odabrani se da ucestvujete u pregledu: "+ naziv+".\n";
        String message = "Termin vašeg pregleda:"
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
