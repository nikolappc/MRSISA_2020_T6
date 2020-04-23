package isamrs.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

@RestController
@RequestMapping("/lek")
public class LekController {

	@Autowired
	private LekService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Lek>> getLekovi(){
		Collection<Lek> lekovi = service.findAll();
		
		return new ResponseEntity<Collection<Lek>>(lekovi, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lek> getLek(@PathVariable("id") Integer id){
		try {			
			Lek l = service.findOne(id);
			return new ResponseEntity<Lek>(l, HttpStatus.OK);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lek sa id-jem: "+id+" nije pronadjen", e);
		}
		
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lek> createLek(@RequestBody Lek l){
		Lek l2 = service.create(l);
		return new ResponseEntity<Lek>(l2, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lek> updateLek(@RequestBody Lek l, @PathVariable("id") Integer id){
		Lek l2 = service.update(id, l);
		return new ResponseEntity<Lek>(l2, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Lek> deleteLek(@PathVariable("id") Integer id){
		service.delete(id);
		return new ResponseEntity<Lek>(HttpStatus.NO_CONTENT);
	}

	
}
