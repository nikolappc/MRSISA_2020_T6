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

import isamrs.domain.TipPosete;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.service.TipPoseteService;
@RestController
@RequestMapping("/tip")
public class TipPoseteController {
	
	@Autowired
	private TipPoseteService tipService;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepo;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipPosete>> getTipovi() {
		Collection<TipPosete> tipovi = tipService.findAll();
		return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
	}
	
	@GetMapping(value = "/tipoviPregleda", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipPosete>> getTipoviPregleda() {
		Collection<TipPosete> tipovi = tipService.findPregledi();
		return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> getTip(@PathVariable("id") Integer id) {
		TipPosete tip = tipService.findOne(id);

		if (tip == null) {
			return new ResponseEntity<TipPosete>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipPosete>(tip, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> createTipPosete(@RequestBody TipPosete tip) throws Exception {
		TipPosete savedTipPosete = tipService.create(tip);
		return new ResponseEntity<TipPosete>(savedTipPosete, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> updateTipPosete(@RequestBody TipPosete tip, @PathVariable Integer id) {
		
		TipPosete updatedTipPosete = null;
		
		try {
			updatedTipPosete = tipService.update(id,tip);
		} catch (Exception e) {
			return new ResponseEntity<TipPosete>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TipPosete>(updatedTipPosete, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TipPosete> deleteTipPosete(@PathVariable("id") Integer id) {
		try {
			tipService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<TipPosete>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TipPosete>(HttpStatus.NO_CONTENT);
	}
	
}
