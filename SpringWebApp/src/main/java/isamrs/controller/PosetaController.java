package isamrs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.domain.Pregled;
import isamrs.dto.SlobodniTerminiDTO;
import isamrs.service.PosetaService;
@RestController
@RequestMapping("/poseta")
public class PosetaController {
	
	@Autowired
	private PosetaService posetaService;
	
	

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> createSlobodniTerminiDTO(@RequestBody SlobodniTerminiDTO poseta) throws Exception {
		
		Pregled savedSlobodniTerminiDTO = posetaService.create(poseta.napraviPregled());
		return new ResponseEntity<Pregled>(savedSlobodniTerminiDTO, HttpStatus.CREATED);
	}

	
}
