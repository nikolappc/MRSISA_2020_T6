package isamrs.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import isamrs.domain.*;
import isamrs.dto.PosetaDTO;
import isamrs.dto.PredefinisaniPregledDTO;
import isamrs.dto.PregledDTO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import isamrs.dto.SlobodniTerminiDTO;
import isamrs.dto.ZakazivanjePregledaDTO;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.LekarService;
import isamrs.service.PacijentService;
import isamrs.service.PosetaService;
import isamrs.service.PregledService;
import isamrs.service.TerminService;
import isamrs.service.TipPoseteService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Transactional
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
	
//
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Poseta> getPosete(HttpServletRequest req){
//
//	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createSlobodniTerminiDTO(@RequestBody SlobodniTerminiDTO poseta) throws Exception {
		
		Pregled savedSlobodniTerminiDTO = posetaService.create(poseta.napraviPregled());
		return new ResponseEntity<Pregled>(savedSlobodniTerminiDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/zakaziPregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> zakaziPregled(@RequestBody ZakazivanjePregledaDTO zahtjev, HttpServletRequest req) throws Exception {
		Pregled pregled = new Pregled();
		Klinika k = klinikaService.findOne(zahtjev.getIdKlinike());
		
		
		if (zahtjev.getIdPredefinisanogTermina() == 0) {
			Termin termin = new Termin();
			pregled.setRecepti(new ArrayList<Recepti>());
			pregled.setDijagnoza(new ArrayList<Dijagnoza>());
			Lekar l = lekarService.findOne(zahtjev.getIdLekara());
			Pacijent p = pacijentService.findOne(zahtjev.getIdPacijenta());
			pregled.setZdravstveniKarton(p.getZdravstveniKarton());
			pregled.setLekar(l);
			TipPosete tp = tipService.findByNaziv(zahtjev.getNazivTipa());
			pregled.setTipPosete(tp);   //falice sala
			termin.setPocetak(zahtjev.getTerminPocetak());
			Calendar c = Calendar.getInstance();
			c.setTime(zahtjev.getTerminPocetak());
			c.add(Calendar.MINUTE, 30);
			termin.setKraj(c.getTime());
			Termin ter = terminService.create(termin);
			pregled.setTermin(termin);
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
		} else {
			Pregled prDef = pregledService.findOne(zahtjev.getIdPredefinisanogTermina());
			System.out.println(zahtjev.getIdPredefinisanogTermina());
			System.out.println(prDef);
			prDef.setZdravstveniKarton(((Pacijent)req.getSession().getAttribute("user")).getZdravstveniKarton());
			Pregled prDef1 = pregledService.update(zahtjev.getIdPredefinisanogTermina(), prDef);
			
			//send mail
			String subject1 = "Potvrda o zakazivanju pregleda";
			String message1 = "Vas pregled je zakazan.";
			
			SimpleMailMessage email1 = new SimpleMailMessage();
			email1.setSubject(subject1);
			email1.setText(message1);
			String recipient1 = ((Pacijent)req.getSession().getAttribute("user")).getEmail();
			email1.setTo(recipient1);
			mailSender.send(email1);
		}
		
		
		
		

		return new ResponseEntity<String>("Uspesno!", HttpStatus.OK);
	}


	@GetMapping(value = "/pregledi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PregledDTO>> getPregledi(HttpServletRequest request){
		try{
			MedicinskoOsoblje medicinskoOsoblje = (MedicinskoOsoblje) request.getSession().getAttribute("user");
			Collection<Pregled> pregledi = pregledService.findPreglediKlinike(medicinskoOsoblje.getKlinika().getId());
			Collection<PregledDTO> posete = pregledi.stream().map(this::pregledToPregledDTO).collect(Collectors.toList());
			return new ResponseEntity<>(posete, HttpStatus.OK);
		}catch (ClassCastException c){
			c.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/predstojeciPregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PosetaDTO>> getBuduciPregledi(@PathVariable("id") Integer idPacijent){
		Collection<PosetaDTO> posete = pacijentService.findBuduciPregled(idPacijent);
		
		return new ResponseEntity<Collection<PosetaDTO>>(posete, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getPredefinisaniPregledi/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PredefinisaniPregledDTO>> getPredefinisaniPregledi(HttpServletRequest request, @PathVariable("idKlinike") Integer idKlinike){
		Collection<Pregled> pregledi = pregledService.findPredefinisaniPreglediKlinike(idKlinike);
		Collection<PredefinisaniPregledDTO> posete = new ArrayList<PredefinisaniPregledDTO>();
		Klinika k = klinikaService.findOne(idKlinike);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.HOUR_OF_DAY, 0); c2.set(Calendar.MINUTE, 0); c2.set(Calendar.SECOND, 0); c2.set(Calendar.MILLISECOND, 0);
		for (Pregled p : pregledi) {
			c1.setTime(p.getTermin().getPocetak());
			c1.set(Calendar.HOUR_OF_DAY, 0); c1.set(Calendar.MINUTE, 0); c1.set(Calendar.SECOND, 0); c1.set(Calendar.MILLISECOND, 0);
			if (c1.getTime().after(c2.getTime())) {
				posete.add(new PredefinisaniPregledDTO(p, k));
			}
		}
		return new ResponseEntity<>(posete, HttpStatus.OK);
	}

	PregledDTO pregledToPregledDTO(Pregled p){
		return new PregledDTO(p);
	}


}
