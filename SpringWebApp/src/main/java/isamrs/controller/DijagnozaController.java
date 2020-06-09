package isamrs.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import isamrs.dto.DijagnozaDTO;
import isamrs.exceptions.NotFoundException;
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

import isamrs.domain.Dijagnoza;
import isamrs.service.DijagnozaService;

@RestController
@RequestMapping("/dijagnoza")
public class DijagnozaController {
	@Autowired
	private DijagnozaService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DijagnozaDTO>> getDijagnoze(){
		Collection<DijagnozaDTO> dijagnoze = service.findAll().stream().map(DijagnozaDTO::new).collect(Collectors.toList());
		
		return new ResponseEntity<Collection<DijagnozaDTO>>(dijagnoze, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DijagnozaDTO> getDijagnoza(@PathVariable("id") Long id){
		try {			
			Dijagnoza d = service.findOne(id);
			return new ResponseEntity<DijagnozaDTO>(new DijagnozaDTO(d), HttpStatus.OK);
		}catch (Exception | NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dijagoza sa id-jem: "+id+" nije pronadjen", e);
		}
		
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DijagnozaDTO> createDijagnoza(@RequestBody Dijagnoza d){
		Dijagnoza d2 = service.create(d);
		return new ResponseEntity<DijagnozaDTO>(new DijagnozaDTO(d2), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DijagnozaDTO> updateDijagnoza(@RequestBody Dijagnoza d, @PathVariable("id") Long id) throws NotFoundException {
		Dijagnoza d2 = service.update(id, d);
		return new ResponseEntity<DijagnozaDTO>(new DijagnozaDTO(d2), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DijagnozaDTO> deleteDijagnoza(@PathVariable("id") Long id){
		service.delete(id);
		return new ResponseEntity<DijagnozaDTO>(HttpStatus.NO_CONTENT);
	}

}
