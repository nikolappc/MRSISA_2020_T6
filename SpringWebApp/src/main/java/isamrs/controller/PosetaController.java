package isamrs.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import isamrs.domain.*;
import isamrs.dto.*;
import isamrs.dto.post.PosetaPostDTO;
import isamrs.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import isamrs.service.KlinikaServiceImpl;
import isamrs.service.LekarService;
import isamrs.service.OperacijaService;
import isamrs.service.PacijentService;
import isamrs.service.PosetaService;
import isamrs.service.PregledService;
import isamrs.service.TerminService;
import isamrs.service.ZdravstveniKartonServiceImpl;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/poseta")
public class PosetaController {
	
	@Autowired
	private PosetaService posetaService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private KlinikaServiceImpl klinikaService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private LekarService lekarService;

	@Autowired
	private ZdravstveniKartonServiceImpl zdravstveniKartonService;

	@Autowired
	private ZdravstveniKartonServiceImpl kartonService;
	
	@Autowired
	private MailSender mailSender;
	
//
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Poseta> getPosete(HttpServletRequest req){
//
//	}

	@PutMapping(value = "pregled/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PregledDTO> zavrsiPregled(@PathVariable("id") Integer id, @RequestBody Pregled p) throws NotFoundException {
		Pregled updated = pregledService.update(id, p);
		return new ResponseEntity<>(pregledToPregledDTO(updated), HttpStatus.OK);
	}
	
	@PostMapping(value = "/pregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createPregled(@RequestBody PosetaPostDTO p, HttpServletRequest req) throws Exception, NotFoundException {
		Pregled pregled = DTOtoPregled(p);
		ZdravstveniKarton zdravstveniKarton = zdravstveniKartonService.findByPacijent(p.getPacijentId());
		Integer idLekar =  ((Lekar)req.getSession().getAttribute("user")).getId();
		Lekar lekar = lekarService.findOne(idLekar);
		pregled.setZdravstveniKarton(zdravstveniKarton);
		pregled.setLekar(lekar);
		Pregled savedPregled = pregledService.create(pregled);
		return new ResponseEntity<Pregled>(savedPregled, HttpStatus.CREATED);
	}
	@PostMapping(value = "/operacija", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Operacija> createOperacija(@RequestBody PosetaPostDTO o, HttpServletRequest req) throws Exception, NotFoundException {

		Integer idLekar =  ((Lekar)req.getSession().getAttribute("user")).getId();
		Operacija operacija = DTOtoOperacija(o);
        ZdravstveniKarton zdravstveniKarton = zdravstveniKartonService.findByPacijent(o.getPacijentId());
        operacija.setZdravstveniKarton(zdravstveniKarton);
        Lekar lekar = lekarService.findOne(idLekar);
        Operacija savedOperacija = operacijaService.create(operacija);
        operacija.addLekar(lekar);
        Klinika k = klinikaService.findByLekar(lekar);
        k.getOperacije().add(operacija);
        klinikaService.update(k.getId(), k);
        lekar.addOperacija(savedOperacija);
        lekarService.update(lekar.getId(), lekar);



		return new ResponseEntity<Operacija>(savedOperacija, HttpStatus.CREATED);
	}

	private Operacija DTOtoOperacija(PosetaPostDTO o) {
		return new Operacija(o.getOpis(), o.getTermin(), o.getTipPosete());
	}

	private Pregled DTOtoPregled(PosetaPostDTO p) {
		return new Pregled(p.getOpis(), p.getTermin(), p.getTipPosete());
	}

	@GetMapping(value = "/pacijent/{idPregled}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PacijentDTO> getPacijentPregleda(@PathVariable("idPregled") Integer idPregled) throws Exception {

		Pacijent pacijent = posetaService.findPacijent(idPregled);
		return new ResponseEntity<PacijentDTO>(pacijentToDTO(pacijent), HttpStatus.CREATED);
	}

	@PutMapping(value = "/zdravstveniKarton/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZdravstveniKartonDTO> updateZdravstveniKarton(@RequestBody ZdravstveniKartonDTO zdravstveniKarton, @PathVariable Integer id){
		try{
			ZdravstveniKarton z = zdravstveniKartonService.update(id, DTOtoZKarton(zdravstveniKarton));
			return new ResponseEntity<>(zdravstveniKartonToDTO(z), HttpStatus.OK);
		}catch (NotFoundException nfe){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}


	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createSlobodniTerminiDTO(@RequestBody SlobodniTerminiDTO poseta) throws Exception {

		Pregled savedSlobodniTerminiDTO = posetaService.create(poseta.napraviPregled());
		return new ResponseEntity<Pregled>(savedSlobodniTerminiDTO, HttpStatus.CREATED);
	}

	@GetMapping(value = "zakazani/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ZakazaniPregledDTO>> getZakazaniPregledi(@PathVariable("id") Integer id, HttpServletRequest req){
		System.out.println(((Pacijent)req.getSession().getAttribute("user")).getId());
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != id)) {
			System.out.println("mrs");
			return new ResponseEntity<ArrayList<ZakazaniPregledDTO>>(HttpStatus.FORBIDDEN);
		}
		List<Pregled> buduciPotvrdjeni = pregledService.getBuduciPotvrdjeniPregledi(id);
		System.out.println("proslo servis");
		ArrayList<ZakazaniPregledDTO> zakazani = new ArrayList<ZakazaniPregledDTO>();
		for (Pregled p : buduciPotvrdjeni) {
			zakazani.add(new ZakazaniPregledDTO(p));
		}
		return new ResponseEntity<ArrayList<ZakazaniPregledDTO>>(zakazani, HttpStatus.OK);
	}

	@PostMapping(value = "otkazi/{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> otkaziPregled(@PathVariable("idPregleda") Integer idPregleda, HttpServletRequest req) throws NotFoundException {
		if (req.getSession().getAttribute("user") == null) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Pregled p = pregledService.findOne(idPregleda);
		if (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != p.getZdravstveniKarton().getPacijent().getId()){
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		if (c.getTime().after(p.getTermin().getPocetak())) {
			return new ResponseEntity<String>("Ne možete otkazati pregled koji počinje u naredna 24 sata.", HttpStatus.BAD_REQUEST);
		}
		p.setPotvrdjen(false);  //nije vise potvrdjen jer svi nezakazani su nepotvrdjeni
		p.setZdravstveniKarton(null);   //uklanja se pacijent
		//ako je sala rezervisana ne uklanja se, nego pregled postaje predefinisani
		if (p.getSala() != null) {
			Pregled pr = pregledService.update(idPregleda, p);
		} else {
			//ako sala nije rezervisana
			//OVO NE MOZE?? JER JE POTVRDJEN=FALSE
			/*p.setTermin(null);
			p.setTipPosete(null);
			p.setRecepti(null);
			p.setDijagnoza(null);
			p.setLekar(null);
			Pregled pr = pregledService.update(idPregleda, p);
			pregledService.delete(idPregleda);*/
		}
		return new ResponseEntity<String>("Uspešno otkazan pregled.", HttpStatus.OK);
	}

	@PostMapping(value = "/zakaziPregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> zakaziPregled(@RequestBody ZakazivanjePregledaDTO zahtjev, HttpServletRequest req) throws Exception, NotFoundException {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != zahtjev.getIdPacijenta())) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Pregled pregled = new Pregled();
		Klinika k = klinikaService.findOne(zahtjev.getIdKlinike());

		if (zahtjev.getIdPredefinisanogTermina() == 0) {
			pregled.setPotvrdjen(false);
			Termin termin = new Termin();
			pregled.setRecepti(new ArrayList<Recepti>());
			pregled.setDijagnoza(new ArrayList<Dijagnoza>());
			Lekar l = posetaService.findOneLekar(zahtjev.getIdLekara());
			Pacijent p = pacijentService.findOne(zahtjev.getIdPacijenta());
			pregled.setZdravstveniKarton(p.getZdravstveniKarton());
			pregled.setLekar(l);
			TipPosete tp = posetaService.findTipByNaziv(zahtjev.getNazivTipa());
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
			Pregled pr = pregledService.create(pregled);
			//System.out.println("ZAKAZANOOOOOOO " + pr.getId() + " " + pr.getZdravstveniKarton().getPacijent().getIme());

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
			int idZk = ((Pacijent)req.getSession().getAttribute("user")).getZdravstveniKarton().getId();
			ZdravstveniKarton zk = kartonService.findOne(idZk);
			prDef.setZdravstveniKarton(zk);
			prDef.setPotvrdjen(true);
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

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> getPregled(@PathVariable("id") Integer id) throws NotFoundException {
		Pregled pregled = pregledService.findOne(id);

		if (pregled == null) {
			return new ResponseEntity<Pregled>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Pregled>(pregled, HttpStatus.OK);
	}

	@GetMapping(value = "/predstojeciPregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PosetaDTO>> getBuduciPregledi(@PathVariable("id") Integer idPacijent) throws NotFoundException {
		Collection<PosetaDTO> posete = pacijentService.findBuduciPregled(idPacijent);

		return new ResponseEntity<Collection<PosetaDTO>>(posete, HttpStatus.OK);
	}


	@GetMapping(value = "/getPredefinisaniPregledi/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PredefinisaniPregledDTO>> getPredefinisaniPregledi(HttpServletRequest req, @PathVariable("idKlinike") Integer idKlinike) throws NotFoundException {
		if (req.getSession().getAttribute("user") == null) {
			return new ResponseEntity<Collection<PredefinisaniPregledDTO>>(HttpStatus.FORBIDDEN);
		}
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

	@Transactional
	private PacijentDTO pacijentToDTO(Pacijent pacijent) {
		PacijentDTO pacijentDTO = new PacijentDTO(pacijent);
		pacijentDTO.setZdravstveniKarton(new ZdravstveniKartonDTO(pacijent.getZdravstveniKarton()));
		return pacijentDTO;
	}

	private ZdravstveniKarton DTOtoZKarton(ZdravstveniKartonDTO zdravstveniKarton) {
		return new ZdravstveniKarton(zdravstveniKarton.getId(), zdravstveniKarton.getVisina(), zdravstveniKarton.getTezina(), zdravstveniKarton.getKrvnaGrupa());
	}

	private ZdravstveniKartonDTO zdravstveniKartonToDTO(ZdravstveniKarton z) {
		ZdravstveniKartonDTO dto = new ZdravstveniKartonDTO();
		dto.setId(z.getId());
		dto.setKrvnaGrupa(z.getKrvnaGrupa());
		dto.setTezina(z.getTezina());
		dto.setVisina(z.getVisina());
		return dto;
	}
}
