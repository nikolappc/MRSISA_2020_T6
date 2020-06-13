package isamrs.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

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

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Lekar;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.service.SalaService;
@RestController
@RequestMapping("/sala")
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sala>> getSalas(HttpServletRequest req) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		AdministratorKlinike ak;
		try {
			ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<Sala> sale = salaService.findAll(ak.getId());
		return new ResponseEntity<Collection<Sala>>(sale, HttpStatus.OK);
	}

	
	@GetMapping(value = "/DTO", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<SalaTerminiDTO>> getSalaDTO(HttpServletRequest req) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		AdministratorKlinike ak;
		try {
			ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<SalaTerminiDTO> sale = salaService.findAllDTO(ak.getId());
		return new ResponseEntity<Collection<SalaTerminiDTO>>(sale, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> getSala(HttpServletRequest req,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Sala sala = salaService.findOne(id);

		if (sala == null) {
			return new ResponseEntity<Sala>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sala>(sala, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> createSala(@RequestBody Sala sala, HttpServletRequest req) throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		AdministratorKlinike ak;
		try {
			ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Sala savedSala = salaService.create(sala, ak.getId());
		return new ResponseEntity<Sala>(savedSala, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> updateSala(HttpServletRequest req,@RequestBody Sala sala, @PathVariable Integer id){
		
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Sala updatedSala = null;
		try {
			updatedSala = salaService.update(id,sala);
		} catch (Exception e) {
			return new ResponseEntity<Sala>(HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Sala>(updatedSala, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Sala> deleteSala(HttpServletRequest req,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			salaService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<Sala>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Sala>(HttpStatus.NO_CONTENT);
	}
	
}
