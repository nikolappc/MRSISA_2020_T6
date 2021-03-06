package isamrs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import isamrs.domain.*;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.SestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isamrs.dto.OdmorDTO;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OdmorRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;

@Service
public class OdmorService {

	
	@Autowired
	private SestraRepository sestraRepo;
	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private OdmorRepository odmorRepo;
	
	@Autowired
	private KlinikaRepository klinikaRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepository;
	
	@Autowired
	private MailSender mailSender;
	
	
	public Collection<OdmorDTO> findAll(Integer klinikaID) {
		ArrayList<OdmorDTO> odmori = new ArrayList<OdmorDTO>();
		for(Object[] o : odmorRepo.zahteviLekari(klinikaID)) {
			OdmorDTO od = new OdmorDTO((GodisnjiOdmor) o[0],(MedicinskoOsoblje)  o[1]);
			odmori.add(od);
		}
		
		for(Object[] o : odmorRepo.zahteviSestre(klinikaID)) {
			OdmorDTO od = new OdmorDTO((GodisnjiOdmor) o[0],(MedicinskoOsoblje)  o[1]);
			odmori.add(od);
		}
		return odmori;
	}

	

	public GodisnjiOdmor create(GodisnjiOdmor t, Integer lekarID) throws NotFoundException {
		Lekar l = lekarRepo.findById(lekarID).orElseThrow(NotFoundException::new);
		l.addGodisnjiOdmor(t);
		GodisnjiOdmor go = odmorRepo.save(t);
		return go;
	}
	public GodisnjiOdmor createSestra(GodisnjiOdmor t, Integer sestraId) throws NotFoundException {
		MedicinskaSestra med = sestraRepo.findById(sestraId).orElseThrow(NotFoundException::new);
		med.addGodisnjiOdmor(t);
		return odmorRepo.save(t);
	}
	
	@Transactional(readOnly = false)
	public GodisnjiOdmor update(Integer id, GodisnjiOdmor t) throws NotFoundException {
		if(t.isOdobren()) {
			SimpleMailMessage email1 = new SimpleMailMessage();
			email1.setSubject("Godisnji odmor");
			GodisnjiOdmor go = odmorRepo.findById(id).orElseThrow(NotFoundException::new);
			Lekar l = odmorRepo.getLekar(go.getId());
			if (l != null) {
				if(proveriTerminLekara(l, go)) {
					go.setOdobren(true);
					email1.setTo(l.getEmail());
					email1.setText("Vas zahtev za godisnji odmor je odobren.");
					mailSender.send(email1);
					return odmorRepo.save(go);
				}
				else {
					this.delete(id);
					t.setOdobren(false);
					email1.setTo(l.getEmail());
					email1.setText("Vas zahtev za godisnji odmor je odbijen.");
					mailSender.send(email1);
					return t;
				}
			} else {
				MedicinskaSestra ms = odmorRepo.getSestra(go.getId());
				go.setOdobren(true);
				email1.setTo(ms.getEmail());
				email1.setText("Vas zahtev za godisnji odmor je odobren.");
				mailSender.send(email1);
				return odmorRepo.save(go);
			}
		}
		else {
			this.delete(id);
			return t;
		}
	}

	public boolean proveriTerminLekara(Lekar lekar, GodisnjiOdmor termin) {
        Collection<Pregled> pregledi = pregledRepo.findByLekar(lekar);
        for (Pregled p : pregledi) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getSala() == null)
                continue;

            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())  || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }
        Collection<Operacija> operacije = operacijaRepository.findByLekar(lekar);

        for (Operacija p : operacije) {

            if (p.getSala() == null)
                continue;

            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())  || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }
        
        
        
        
        return true;
    }



	public void delete(Integer id) {
		odmorRepo.deleteById(id);
	}

	
}
