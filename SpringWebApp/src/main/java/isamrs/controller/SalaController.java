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

import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.service.SalaService;
@RestController
@RequestMapping("/sala")
public class SalaController {
	
	@Autowired
	private SalaService salaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Sala>> getSalas() {
		Collection<Sala> sale = salaService.findAll();
		return new ResponseEntity<Collection<Sala>>(sale, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> getSala(@PathVariable("id") Integer id) {
		Sala sala = salaService.findOne(id);

		if (sala == null) {
			return new ResponseEntity<Sala>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Sala>(sala, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> createSala(@RequestBody Sala sala) throws Exception {
		Sala savedSala = salaService.create(sala);
		return new ResponseEntity<Sala>(savedSala, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sala> updateSala(@RequestBody Sala sala, @PathVariable Integer id)
			throws Exception {
		
		Sala updatedSala = salaService.update(id,sala);
		return new ResponseEntity<Sala>(updatedSala, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Sala> deleteSala(@PathVariable("id") Integer id) {
		try {
			salaService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<Sala>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Sala>(HttpStatus.NO_CONTENT);
	}
	
}
