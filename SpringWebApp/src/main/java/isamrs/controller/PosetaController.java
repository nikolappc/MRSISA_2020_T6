package isamrs.controller;


import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Dijagnoza;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.Recepti;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;
import isamrs.dto.SlobodniTerminiDTO;
import isamrs.dto.ZakazivanjePregledaDTO;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.LekarService;
import isamrs.service.PacijentService;
import isamrs.service.PosetaService;
import isamrs.service.PregledService;
import isamrs.service.TerminService;
import isamrs.service.TipPoseteService;
@RestController
@RequestMapping("/poseta")
public class PosetaController {
	
	@Autowired
	private PosetaService posetaService;
	
	@Autowired
	private TipPoseteService tipService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private KlinikaServiceImpl klinikaService;
	
	@Autowired
	private MailSender mailSender;
	

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createSlobodniTerminiDTO(@RequestBody SlobodniTerminiDTO poseta) throws Exception {
		
		Pregled savedSlobodniTerminiDTO = posetaService.create(poseta.napraviPregled());
		return new ResponseEntity<Pregled>(savedSlobodniTerminiDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/zakaziPregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> zakaziPregled(@RequestBody ZakazivanjePregledaDTO zahtjev) throws Exception {
		Lekar l = lekarService.findOne(zahtjev.getIdLekara());
		Klinika k = klinikaService.findOne(zahtjev.getIdKlinike());
		Pacijent p = pacijentService.findOne(zahtjev.getIdPacijenta());
		Pregled pregled = new Pregled();
		pregled.setZdravstveniKarton(p.getZdravstveniKarton());
		pregled.setLekar(l);
		TipPosete tp = tipService.findByNaziv(zahtjev.getNazivTipa());
		pregled.setTipPosete(tp);   //falice sala i opis
		Termin termin = new Termin();
		termin.setPocetak(zahtjev.getTerminPocetak());
		Calendar c = Calendar.getInstance();
		c.setTime(zahtjev.getTerminPocetak());
		c.add(Calendar.MINUTE, 30);
		termin.setKraj(c.getTime());
		Termin ter = terminService.create(termin);
		pregled.setTermin(termin);
		pregled.setRecepti(new ArrayList<Recepti>());
		pregled.setDijagnoza(new ArrayList<Dijagnoza>());
		k.getPregledi().add(pregled);
		l.getPregled().add(pregled);
		p.getZdravstveniKarton().getPregledi().add(pregled);
		//Klinika kl = klinikaService.update(zahtjev.getIdKlinike(), k);
		//Lekar le = lekarService.update(zahtjev.getIdLekara(), l);
		Pregled pr = pregledService.create(pregled);
		
		System.out.println("ZAKAZANOOOOOOO " + pr.getId() + " " + pr.getZdravstveniKarton().getPacijent().getIme());
		
		//send mail
		String subject = "Zahtjev za zakazivanje pregleda";
		String message = "Dobili ste zahtjev za zakazivanje pregleda od strane pacijenta.";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(message);
		for (AdministratorKlinike ak : k.getAdministratorKlinike()) {
			String recipient = ak.getEmail();
			email.setTo(recipient);
			mailSender.send(email);

		}

		return new ResponseEntity<String>("Uspesno!", HttpStatus.OK);
	}

	
}
