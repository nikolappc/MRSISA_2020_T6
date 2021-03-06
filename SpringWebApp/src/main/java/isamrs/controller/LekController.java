package isamrs.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.domain.Lekar;
import isamrs.domain.Recepti;
import isamrs.dto.LekDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.service.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isamrs.domain.Lek;
import isamrs.service.LekService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/lek")
public class LekController {

	@Autowired
	private LekService service;

	@Autowired
	private ReceptService recService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<LekDTO>> getLekovi(HttpServletRequest request){
		if (!(request.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)&&!(request.getSession().getAttribute("user") instanceof Lekar)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<Lek> lekovi = service.findAll();
		Collection<LekDTO> lekDTOS = lekovi.stream().map(this::LektoDTO).collect(Collectors.toList());
		return new ResponseEntity<Collection<LekDTO>>(lekDTOS, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekDTO> getLek(@PathVariable("id") Long id, HttpServletRequest request){
		if (!(request.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra )){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {			
			Lek l = service.findOne(id);
			return new ResponseEntity<LekDTO>(LektoDTO(l), HttpStatus.OK);
		}catch (Exception | NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lek sa id-jem: "+id+" nije pronadjen", e);
		}
		
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekDTO> createLek(@RequestBody LekDTO l, HttpServletRequest request){
		if (!(request.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Lek l2 = service.create(new Lek(l));
		return new ResponseEntity<LekDTO>(LektoDTO(l2), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekDTO> updateLek(@RequestBody LekDTO l, @PathVariable("id") Long id, HttpServletRequest request) throws NotFoundException {
		if (!(request.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Lek l2 = service.update(id, new Lek(l));
		return new ResponseEntity<LekDTO>(LektoDTO(l2), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<LekDTO> deleteLek(@PathVariable("id") Long id, HttpServletRequest request){
		if (!(request.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		service.delete(id);
		return new ResponseEntity<LekDTO>(HttpStatus.NO_CONTENT);
	}

	public LekDTO LektoDTO(Lek l){
		Collection<Recepti> rec = recService.getReceptsWithLek(l.getSifraLeka());
		return new LekDTO(l.getId(), l.getSifraLeka(),l.getNazivLeka(),l.getOpisLeka(),!rec.isEmpty());
	}
}
