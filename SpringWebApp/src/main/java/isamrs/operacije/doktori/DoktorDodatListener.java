package isamrs.operacije.doktori;

import isamrs.domain.Lekar;
import org.apache.tomcat.jni.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DoktorDodatListener implements ApplicationListener<OnDoktorDodatEvent> {
    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(OnDoktorDodatEvent event) {
        Lekar user = (Lekar)event.getSource();
        String naziv = event.getOperacija().getTipPosete().getNaziv();
        String recipient = user.getEmail();
        String subject = "Odabrani se da ucestvujete u operaciji: "+ naziv+".\n";
        String message = "Termin vaše operacije:"
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
