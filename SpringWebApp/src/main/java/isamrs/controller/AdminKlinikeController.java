package isamrs.controller;

import java.util.Collection;

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

import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.SalaService;
@RestController
@RequestMapping("/admin/zahtevi")
public class AdminKlinikeController {
	
	@Autowired
	private AdministratorKlinikeService adminService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Integer>> getSalas() {
		Collection<Integer> pregledi = adminService.findAllZahtevi();
		return new ResponseEntity<Collection<Integer>>(pregledi, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> getPregled(@PathVariable("id") Integer id) {
		Pregled pregled = adminService.findPregled(id);

		if (pregled == null)
			return new ResponseEntity<Pregled>(HttpStatus.NOT_FOUND);
		
		if (pregled.getSala() != null)
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<Pregled>(pregled, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> updateSala(@RequestBody Pregled pregled, @PathVariable Integer id){
		
		
		Pregled updatePregled = null;
		
		try {
			updatePregled = adminService.update(id,pregled);
		} catch (Exception e) {
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<Pregled>(updatePregled, HttpStatus.OK);
	}
	
}

