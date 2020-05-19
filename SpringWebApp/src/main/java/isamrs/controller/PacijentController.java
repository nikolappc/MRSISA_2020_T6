package isamrs.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import isamrs.domain.*;
import isamrs.dto.*;
import isamrs.service.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private PregledService pregledService;

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	KlinikaServiceImpl klinikaService;

	/*
	 * @Autowired private ZdravstveniKartonServiceImpl kartonService;
	 */

	@GetMapping(value ="/klinike", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OsobaDTO>> getPacijents(HttpServletRequest req){
		MedicinskoOsoblje o = (MedicinskoOsoblje)req.getSession().getAttribute("user");
		Klinika k = o.getKlinika();
////		Hibernate.initialize(k.getPacijent());
		Collection<OsobaDTO> finalni = k.getPacijent().stream().map(this::pacijentToOsobaDTO).collect(Collectors.toList());
		return new ResponseEntity<>(finalni, HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Pacijent>> getPacijents() {
		Collection<Pacijent> pacijenti = pacijentService.findAll();
		return new ResponseEntity<Collection<Pacijent>>(pacijenti, HttpStatus.OK);
	}

	@PostMapping(value = "/izmjena", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacijent> updatePacijent(@RequestBody Pacijent pacijent, HttpServletRequest req) {
		Pacijent p = pacijentService.findOne(pacijent.getId());

		if (p == null) {
			return new ResponseEntity<Pacijent>(p, HttpStatus.BAD_REQUEST);
		}

		p.setIme(pacijent.getIme());
		p.setPrezime(pacijent.getPrezime());
		p.setPassword(pacijent.getPassword());
		p.setBrojTelefona(pacijent.getBrojTelefona());
		p.setJbo(pacijent.getJbo());
		p.setAdresa(pacijent.getAdresa());

		p = pacijentService.save(p);
		req.getSession().setAttribute("user", p);

		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	}

	@GetMapping(value = "/listaPregleda/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PosetaDTO>> getPregledi(@PathVariable("id") Integer id) {
		Pacijent p = pacijentService.findOne(id);
		List<Pregled> pregledi = pregledService.findByKartonId(p.getZdravstveniKarton().getId());
		List<Operacija> operacije = operacijaService.findByKartonId(p.getZdravstveniKarton().getId());

		List<PosetaDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			if (pregled.getSala() != null && pregled.getTermin().getPocetak().before(new Date())) {
				preglediDTO.add(new PregledDTO(pregled));
			}
			//System.out.println("pacijent kontroler" + pregled.getTermin().getPocetak().toString());
		}
		for (Operacija operacija : operacije) {
			preglediDTO.add(new OperacijaDTO(operacija));
		}

		return new ResponseEntity<List<PosetaDTO>>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/karton/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZdravstveniKartonDTO> getKarton(@PathVariable("id") Integer id, HttpServletRequest req) {
		Pacijent p = pacijentService.findOne(id);
		//System.out.println("KARTON");
		//System.out.println(id);

		if (p.getZdravstveniKarton() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ZdravstveniKarton zk = p.getZdravstveniKarton();
		///System.out.println(zk.getKrvnaGrupa());

		ZdravstveniKartonDTO zk_dto = new ZdravstveniKartonDTO(zk);
		return new ResponseEntity<ZdravstveniKartonDTO>(zk_dto, HttpStatus.OK);
	}

	OsobaDTO pacijentToOsobaDTO(Pacijent p){
		return new OsobaDTO(p);
	}
}
