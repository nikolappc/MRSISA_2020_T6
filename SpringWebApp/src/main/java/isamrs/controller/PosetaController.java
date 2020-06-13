package isamrs.controller;


import java.util.ArrayList;
import java.util.Collection;
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
import isamrs.service.PregledServiceImpl;
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
	private PregledServiceImpl pregledService;
	
	@Autowired
	private KlinikaServiceImpl klinikaService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private LekarService lekarService;

	@Autowired
	private ZdravstveniKartonServiceImpl zdravstveniKartonService;
	
	@Autowired
	private MailSender mailSender;
	
//
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Poseta> getPosete(HttpServletRequest req){
//
//	}

	@PutMapping(value = "pregled/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PregledDTO> zavrsiPregled(@PathVariable("id") Integer id, @RequestBody PregledDTO p, HttpServletRequest req) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pregled pregled = new Pregled(p);
		Integer idLekar =  ((Lekar)req.getSession().getAttribute("user")).getId();
		Lekar lekar = lekarService.findOne(idLekar);
		pregled.setLekar(lekar);
		Pregled updated = pregledService.update(id, pregled);
		return new ResponseEntity<>(pregledToPregledDTO(updated), HttpStatus.OK);
	}
	
	@PutMapping(value = "operacija/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Operacija> zavrsiOperaciju(@PathVariable("id") Integer id, @RequestBody OperacijaDTO pregled, HttpServletRequest req) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Integer idLekar =  ((Lekar)req.getSession().getAttribute("user")).getId();
		Operacija updated = operacijaService.update(id, pregled,idLekar);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createPregled(@RequestBody PosetaPostDTO p, HttpServletRequest req) throws Exception, NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pregled pregled = DTOtoNewPregled(p);
		ZdravstveniKarton zdravstveniKarton = zdravstveniKartonService.findByPacijent(p.getPacijentId());
		Integer idLekar =  ((Lekar)req.getSession().getAttribute("user")).getId();
		Lekar lekar = lekarService.findOne(idLekar);
		pregled.setZdravstveniKarton(zdravstveniKarton);
		pregled.setLekar(lekar);
		Pregled savedPregled = pregledService.create(pregled);
		
		String subject1 = "Kreiran novi pregled";
		String message1 = "Obavestavamo Vas da je u Vasoj klinici kreiran novi pregled.";
		SimpleMailMessage email1 = new SimpleMailMessage();
		email1.setSubject(subject1);
		email1.setText(message1);
		for (AdministratorKlinike ak : lekar.getKlinika().getAdministratorKlinike()) {
			String recipient = ak.getEmail();
			email1.setTo(recipient);
			mailSender.send(email1);
		}

		
		return new ResponseEntity<Pregled>(savedPregled, HttpStatus.CREATED);
	}
	@PostMapping(value = "/operacija", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Operacija> createOperacija(@RequestBody PosetaPostDTO o, HttpServletRequest req) throws Exception, NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
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
        
        String subject1 = "Kreirana nova operacija";
		String message1 = "Obavestavamo Vas da je u Vasoj klinici kreirana nova operacija.";
		SimpleMailMessage email1 = new SimpleMailMessage();
		email1.setSubject(subject1);
		email1.setText(message1);
		for (AdministratorKlinike ak : lekar.getKlinika().getAdministratorKlinike()) {
			String recipient = ak.getEmail();
			email1.setTo(recipient);
			mailSender.send(email1);
		}



		return new ResponseEntity<Operacija>(savedOperacija, HttpStatus.CREATED);
	}

	private Operacija DTOtoOperacija(PosetaPostDTO o) {
		return new Operacija(o.getOpis(), o.getTermin(), o.getTipPosete());
	}

	private Pregled DTOtoNewPregled(PosetaPostDTO p) {
		return new Pregled(p.getOpis(), p.getTermin(), p.getTipPosete());
	}
	@GetMapping(value = "/pacijent/{idPregled}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PacijentDTO> getPacijentPregleda(HttpServletRequest req ,@PathVariable("idPregled") Integer idPregled) throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pacijent pacijent = posetaService.findPacijent(idPregled);
		return new ResponseEntity<PacijentDTO>(pacijentToDTO(pacijent), HttpStatus.CREATED);
	}

	@PutMapping(value = "/zdravstveniKarton/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZdravstveniKartonDTO> updateZdravstveniKarton(HttpServletRequest req,@RequestBody ZdravstveniKartonDTO zdravstveniKarton, @PathVariable Integer id){
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try{
			ZdravstveniKarton z = zdravstveniKartonService.update(id, DTOtoZKarton(zdravstveniKarton));
			return new ResponseEntity<>(zdravstveniKartonToDTO(z), HttpStatus.OK);
		}catch (NotFoundException nfe){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}


	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createSlobodniTerminiDTO(HttpServletRequest req,@RequestBody SlobodniTerminiDTO poseta) throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pregled savedSlobodniTerminiDTO = null;
		try {
			
			savedSlobodniTerminiDTO = posetaService.create(poseta.napraviPregled());
		}catch (Exception e) {
			return new ResponseEntity<Pregled>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Pregled>(savedSlobodniTerminiDTO, HttpStatus.CREATED);
	}
	

	@GetMapping(value = "zakazani/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ZakazaniPregledDTO>> getZakazaniPregledi(@PathVariable("id") Integer id, HttpServletRequest req){
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && !((Pacijent)req.getSession().getAttribute("user")).getId().equals(id))) {
			return new ResponseEntity<List<ZakazaniPregledDTO>>(HttpStatus.FORBIDDEN);
		}
		List<ZakazaniPregledDTO> buduciPotvrdjeni = pregledService.getBuduciPotvrdjeniPregledi(id);
		/*ArrayList<ZakazaniPregledDTO> zakazani = new ArrayList<ZakazaniPregledDTO>();
		for (Pregled p : buduciPotvrdjeni) {
			Cenovnik c = p.getLekar().getKlinika().getCenovnik();
			zakazani.add(new ZakazaniPregledDTO(p, c));
		}*/
		return new ResponseEntity<List<ZakazaniPregledDTO>>(buduciPotvrdjeni, HttpStatus.OK);
	}

	@PostMapping(value = "otkazi/{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> otkaziPregled(@PathVariable("idPregleda") Integer idPregleda, HttpServletRequest req) throws NotFoundException {
		if (req.getSession().getAttribute("user") == null) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Boolean response = pregledService.otkaziPregled(idPregleda, ((Pacijent)req.getSession().getAttribute("user")).getId());
		if (response == null) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		} else if (response.equals(false)) {
			return new ResponseEntity<String>("Ne možete otkazati pregled koji počinje u naredna 24 sata.", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>("Uspešno otkazan pregled.", HttpStatus.OK);
		}
		/*if (req.getSession().getAttribute("user") == null) {
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
		}
		return new ResponseEntity<String>("Uspešno otkazan pregled.", HttpStatus.OK);*/
	}

	@PostMapping(value = "/zakaziPregled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> zakaziPregled(@RequestBody ZakazivanjePregledaDTO zahtjev, HttpServletRequest req) throws Exception, NotFoundException {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && !((Pacijent)req.getSession().getAttribute("user")).getId().equals(zahtjev.getIdPacijenta()))) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Boolean response = false;
		if (zahtjev.getIdPredefinisanogTermina() == 0) {
			response = pregledService.zakaziPregled(zahtjev);
		} else {
			int idZk = ((Pacijent)req.getSession().getAttribute("user")).getZdravstveniKarton().getId();
			String recipient1 = ((Pacijent)req.getSession().getAttribute("user")).getEmail();
			response = pregledService.zakaziPredefinisaniPregled(zahtjev, idZk, recipient1);
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++"+response);
		if (response == true) {
			return new ResponseEntity<String>("Uspesno!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Neuspesno!", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/getPredefinisaniPregledi/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PredefinisaniPregledDTO>> getPredefinisaniPregledi(HttpServletRequest req, @PathVariable("idKlinike") Integer idKlinike) throws NotFoundException {
		if (req.getSession().getAttribute("user") == null) {
			return new ResponseEntity<Collection<PredefinisaniPregledDTO>>(HttpStatus.FORBIDDEN);
		}
		Collection<PredefinisaniPregledDTO> posete = pregledService.getPredefinisaniPregledi(idKlinike);
		
		return new ResponseEntity<>(posete, HttpStatus.OK);
	}

	@GetMapping(value = "/pregledi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PregledDTO>> getPregledi(HttpServletRequest request){
		if (!(request.getSession().getAttribute("user") instanceof MedicinskoOsoblje)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
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
	public ResponseEntity<PregledDTO> getPregled(HttpServletRequest req ,@PathVariable("id") Integer id) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike 
				|| req.getSession().getAttribute("user") instanceof MedicinskoOsoblje) ) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pregled pregled = pregledService.findOne(id);

		if (pregled == null) {
			return new ResponseEntity<PregledDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<PregledDTO>(new PregledDTO(pregled), HttpStatus.OK);
	}
	
	@GetMapping(value = "operacija/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperacijaDTO> getOperacija(HttpServletRequest req,@PathVariable("id") Integer id) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Operacija pregled = operacijaService.findOne(id);

		if (pregled == null) {
			return new ResponseEntity<OperacijaDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OperacijaDTO>(new OperacijaDTO(pregled), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pacijent/operacija/{idPregled}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PacijentDTO> getPacijentOperacija(HttpServletRequest req,@PathVariable("idPregled") Integer idPregled) throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		Pacijent pacijent = posetaService.findPacijentOperacija(idPregled);
		return new ResponseEntity<PacijentDTO>(pacijentToDTO(pacijent), HttpStatus.CREATED);
	}

	@GetMapping(value = "/predstojeciPregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PosetaDTO>> getBuduciPregledi(HttpServletRequest req,@PathVariable("id") Integer idPacijent) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<PosetaDTO> posete = pacijentService.findBuduciPregled(idPacijent);
		
		return new ResponseEntity<Collection<PosetaDTO>>(posete, HttpStatus.OK);
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
