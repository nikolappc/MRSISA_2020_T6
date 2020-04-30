package isamrs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import isamrs.domain.Operacija;
import isamrs.domain.Osoba;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.OperacijaDTO;
import isamrs.dto.PosetaDTO;
import isamrs.dto.PregledDTO;
import isamrs.dto.UserDTO;
import isamrs.dto.ZdravstveniKartonDTO;
import isamrs.service.OperacijaService;
import isamrs.service.PacijentService;
import isamrs.service.PregledService;
import isamrs.service.ZdravstveniKartonServiceImpl;



	
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
	private ZdravstveniKartonServiceImpl kartonService;
	

	
   
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
		
		//pacijentService.izmijeniUlogovanog(pacijent);
		
		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listaPregleda/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PosetaDTO>> getPregledi(@PathVariable("id") Integer id) {
		Pacijent p = pacijentService.findOne(id);
		List<Pregled> pregledi = pregledService.findByKartonId(p.getZdravstveniKarton().getId());
		List<Operacija> operacije = operacijaService.findByKartonId(p.getZdravstveniKarton().getId());
		
		List<PosetaDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			preglediDTO.add(new PregledDTO(pregled));
			System.out.println(pregled.getTermin().getPocetak().toString());
		}
		for (Operacija operacija : operacije) {
			preglediDTO.add(new OperacijaDTO(operacija));
		}
		
		
		return new ResponseEntity<List<PosetaDTO>>(preglediDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/karton/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZdravstveniKartonDTO> getKarton(@PathVariable("id") Integer id, HttpServletRequest req) {
		Pacijent p = pacijentService.findOne(id);
		System.out.println("KARTON");
		System.out.println(id);
		//ZdravstveniKarton zk = kartonService.findOne(p.getZdravstveniKarton().getId());
		
		ZdravstveniKarton zk = p.getZdravstveniKarton();
		System.out.println(zk.getKrvnaGrupa());
		
		ZdravstveniKartonDTO zk_dto = new ZdravstveniKartonDTO(zk);
		return new ResponseEntity<ZdravstveniKartonDTO>(zk_dto, HttpStatus.OK);
	}
	
	//da li da budu odvojeno pozivi za ulogovanog, karton i listu pregleda ili sve odjednom

}
