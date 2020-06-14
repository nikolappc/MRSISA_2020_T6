package isamrs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import isamrs.domain.*;
import isamrs.dto.*;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.KlinikaRepository;
import isamrs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacijent")
public class PacijentController {
	

	@Autowired
	private PacijentServiceImpl pacijentService;

	@Autowired
	private PregledServiceImpl pregledService;

	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private LekarService lekarService;

	@GetMapping(value ="/klinike", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OsobaDTO>> getPacijents(HttpServletRequest req){
		//autorizacija
		Collection<OsobaDTO> finalni =pacijentService.findAll().stream().map(this::pacijentToOsobaDTO).collect(Collectors.toList());
		return new ResponseEntity<>(finalni, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacijent> getPacijet(@PathVariable("id") Integer id, HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != id)) {
			return new ResponseEntity<Pacijent>(HttpStatus.FORBIDDEN);
		}
		Pacijent pacijent = pacijentService.findOne(id);
		if (pacijent == null) {
			return new ResponseEntity<Pacijent>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pacijent>(pacijent, HttpStatus.OK);
	}
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Pacijent>> getPacijentss(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<Collection<Pacijent>>(HttpStatus.FORBIDDEN);
		}
		Collection<Pacijent> pacijenti = pacijentService.findAll();
		return new ResponseEntity<Collection<Pacijent>>(pacijenti, HttpStatus.OK);
	}

	@PostMapping(value = "/provera/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String,Boolean>> checkKarton(@PathVariable("id") Integer id, HttpServletRequest req) {
		HashMap<String,Boolean> mapa = new HashMap<String, Boolean>(); 
		if ((req.getSession().getAttribute("user") instanceof Pacijent)) {
			mapa.put("provera", true);
			return new ResponseEntity<HashMap<String,Boolean>>(mapa, HttpStatus.OK);
		}
		else if (req.getSession().getAttribute("user") instanceof Lekar) {
			Lekar l = (Lekar)  req.getSession().getAttribute("user");
			
			Boolean provera = lekarService.checkKarton(id,l.getId());
			mapa.put("provera", provera);
			return new ResponseEntity<HashMap<String,Boolean>>(mapa, HttpStatus.OK);
		}


		return new ResponseEntity<HashMap<String,Boolean>>(HttpStatus.FORBIDDEN);
	}
	
	@PostMapping(value = "/izmjena", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacijent> updatePacijent(@RequestBody IzmjenaOsobeDTO pacijent, HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<Pacijent>(HttpStatus.FORBIDDEN);
		}
		Pacijent p = null;
		try {
			p = pacijentService.izmijeni(pacijent.getId(), pacijent);
		} catch (Exception e) {
			return new ResponseEntity<Pacijent>(p, HttpStatus.BAD_REQUEST);
		}
		
		if (p == null) {
			return new ResponseEntity<Pacijent>(p, HttpStatus.BAD_REQUEST);
		}
		
		req.getSession().setAttribute("user", p);

		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	}

	@GetMapping(value = "/listaPregleda/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<PosetaDTO>> getPregledi(@PathVariable("id") Integer id, HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != id)) {
			return new ResponseEntity<ArrayList<PosetaDTO>>(HttpStatus.FORBIDDEN);
		}
		Pacijent p = pacijentService.findOne(id);
		List<Pregled> pregledi = pregledService.findByKartonIdOdradjen(p.getZdravstveniKarton().getId());
		List<Operacija> operacije = operacijaService.findByKartonId(p.getZdravstveniKarton().getId());

		ArrayList<PosetaDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			if (pregled.getSala() != null && pregled.getTermin().getPocetak().before(new Date())) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}
		for (PosetaDTO prpr : preglediDTO) {
			System.out.println(((PregledDTO) prpr).getLekar());
		}
		for (Operacija operacija : operacije) {
			preglediDTO.add(new OperacijaDTO(operacija));
		}

		return new ResponseEntity<ArrayList<PosetaDTO>>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/karton/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZdravstveniKartonDTO> getKarton(@PathVariable("id") Integer id, HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || (req.getSession().getAttribute("user") instanceof Pacijent && ((Pacijent)req.getSession().getAttribute("user")).getId() != id)) {
			return new ResponseEntity<ZdravstveniKartonDTO>(HttpStatus.FORBIDDEN);
		}
		Pacijent p = pacijentService.findOne(id);

		if (p.getZdravstveniKarton() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ZdravstveniKarton zk = p.getZdravstveniKarton();

		ZdravstveniKartonDTO zk_dto = new ZdravstveniKartonDTO(zk);
		return new ResponseEntity<ZdravstveniKartonDTO>(zk_dto, HttpStatus.OK);
	}

	OsobaDTO pacijentToOsobaDTO(Pacijent p){
		return new OsobaDTO(p);
	}
	
	@GetMapping(value = "/potvrdiPregled/{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> potvrdiPregled(@PathVariable("idPregleda") Integer idPregleda, HttpServletRequest req) {
		if (!(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Pregled p = null;
		try {
			p = pregledService.findOne(idPregleda);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Greska", HttpStatus.BAD_REQUEST);
		}
		if (p.getZdravstveniKarton() == null) {
			return new ResponseEntity<String>("Greska", HttpStatus.BAD_REQUEST);
		}
		p.setPotvrdjen(true);
		try {
			Pregled pr = pregledService.update(p.getId(), p);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Uspešno potvrdjen pregled!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/odbijPregled/{idPregleda}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> odbijPregled(HttpServletRequest req, @PathVariable("idPregleda") Integer idPregleda) {
		if (!(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		Pregled p = null;
		try {
			p = pregledService.findOne(idPregleda);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Greska", HttpStatus.BAD_REQUEST);
		}
		if (p.isPotvrdjen()) {
			return new ResponseEntity<String>("Greska", HttpStatus.BAD_REQUEST);
		}
		p.setZdravstveniKarton(null);
		try {
			Pregled pr = pregledService.update(p.getId(), p);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Uspešno odbijen pregled!", HttpStatus.OK);
	}
}
